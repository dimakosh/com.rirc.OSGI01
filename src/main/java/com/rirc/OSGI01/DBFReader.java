package com.rirc.OSGI01;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DBFReader implements Iterable<DBFRecord> {
	final protected InputStream inp;
	
	final private Charset charset;
	final private List<DBFField> aFields= new ArrayList<DBFField>();
	final private Map<String,DBFField> mFields= new HashMap<String,DBFField>();
	final private int recCount;
	final private short recLen;
	
	public static DBFReader open(InputStream inp, String charset) throws IOException {
		return new DBFReader(inp, Charset.forName(charset));
	}
	public static DBFReader ansiOpen(InputStream inp) throws IOException {
		return open(inp, "Cp1251");
	}
	public static DBFReader oemOpen(InputStream inp) throws IOException {
		return open(inp, "Cp866");
	}

	protected DBFReader(InputStream _inp, Charset _charset) throws IOException {
		charset= _charset;
		
		inp= _inp;
		byte[] buf= new byte[32]; // 1+3 + 4+2+2 + 3+13+4

		inp.skip(1+3);
		readFully(inp, buf, 0, 4);
		recCount= Integer.reverseBytes(ByteBuffer.wrap(buf, 0, 4).getInt());
		readFully(inp, buf, 0, 2);
		short headLen= Short.reverseBytes(ByteBuffer.wrap(buf, 0, 2).getShort());
		readFully(inp, buf, 0, 2);
		recLen= Short.reverseBytes(ByteBuffer.wrap(buf, 0, 2).getShort());

		inp.skip(3+13+4);

		short isRead= 4+4+2+2 + 3+13+4;
		
		Charset fldChset= Charset.forName("US-ASCII");
		
		int sz= 1;
		for (;;) {
			readFully(inp, buf, 0, 1);
			isRead+= 1;
			if (buf[0]==13 || headLen<=isRead) break;

			readFully(inp, buf, 1, 31);
			isRead+= 31;
			
			String name= new String(buf, 0, 11, fldChset);
			char t= (char)(buf[11]);
			int l= Byte.toUnsignedInt(buf[16]);
			int d= Byte.toUnsignedInt(buf[17]);
			if (t=='C') l+= d*256;

			DBFField f= new DBFField(name, t, l, d, sz);
			aFields.add(f);
			mFields.put(f.getName(), f);
			
			sz+= l;			
		}
		
		{
			short sk= headLen;
			sk-= isRead;
			if (sk<0) throw new EOFException();
			else
				if (0<sk) inp.skip(sk);
		}
	}

	public int getFieldCount() {
		return aFields.size();
	}
	
	public DBFField getField(int pos) {
		return aFields.get(pos);
	}
	
	public Iterable<DBFField> Fields() {
		return ()-> aFields.iterator();
	}

	@Override
	public Iterator<DBFRecord> iterator() {
		return new Iterator<DBFRecord>() {
			DBFRecord rec= new DBFRecord(recLen, aFields, mFields, charset);
			int nRec;

			@Override
			public boolean hasNext() {
				return nRec<recCount;
			}

			@Override
			public DBFRecord next() {
				try {
					readFully(inp, rec.buf, 0, recLen);
					nRec++;
					return rec;
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}
			}
		};
	}

	private static void readFully(InputStream inp, byte b[], int off, int len) throws IOException {
        if (len < 0)
            throw new IndexOutOfBoundsException();
        int n = 0;
        while (n < len) {
            int count = inp.read(b, off + n, len - n);
            if (count < 0)
                throw new EOFException();
            n += count;
        }
    }
}
