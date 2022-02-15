package com.rirc.OSGI01;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DBFWriter {
	final private Charset charset;
	final private List<DBFField> aFields= new ArrayList<DBFField>();

	public static DBFWriter create(String charset) {
		return new DBFWriter(Charset.forName(charset));
	}
	public static DBFWriter ansiCreate() {
		return create("Cp1251");
	}
	public static DBFWriter oemCreate() {
		return create("Cp866");
	}

	protected DBFWriter(Charset _charset) {
		charset= _charset;
	}
	
	public DBFWriter add(String s, char c, int i) {
		aFields.add(new DBFField(s, c, i, 0, 0));
		return this;
	}
	public DBFWriter add(String s, char c, int i, int j) {
		aFields.add(new DBFField(s, c, i, j, 0));
		return this;
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
	
	public void write(OutputStream out, KDDataSet ds) throws IOException {
		out.write(3);
		skip(out, 3);
		
		int recCount= ds.size();
		short headLen= (short)(1+3 + 4+2+2 + 2+1+1+12+1+1+2 + aFields.size()*32 + 1);
		
		short recLen= 1;
		for (DBFField f : aFields) {
			f.setOff(recLen);
			recLen+= f.getLength();
		}

		final byte[] buf= new byte[(recLen<32)? 32:recLen];
		
		ByteBuffer.wrap(buf, 0, 4).putInt(Integer.reverseBytes(recCount));
		out.write(buf, 0, 4);

		ByteBuffer.wrap(buf, 0, 2).putShort(Short.reverseBytes(headLen));
		out.write(buf, 0, 2);

		ByteBuffer.wrap(buf, 0, 2).putShort(Short.reverseBytes(recLen));
		out.write(buf, 0, 2);
		
		skip(out, 2+1+1+12+1+1+2); // 20

		Charset fldChset= Charset.forName("US-ASCII");
		
		for (DBFField f : aFields) {
			{
				byte[] b= f.getName().getBytes(fldChset);
				int skp= 11-b.length;
				if (skp<1) throw new RuntimeException("Название поля не более 10: "+ f.getName());
				out.write(b);
				skip(out, skp);
			}
			{			
				out.write(f.getType());
				ByteBuffer.wrap(buf, 0, 4).putInt(Integer.reverseBytes(f.getOff()));
				out.write(buf, 0, 4);
				out.write(f.getLength());
				out.write(f.getDec());
	
				skip(out, 13+1);
			}
		}

		out.write(13);
		
		for (KDDataSet.Row row : ds.getIterable()) {
			Arrays.fill(buf, (byte)32);
			
			for (DBFField f : aFields) {
				int ind= row.getInd(f.getName());
				if (ind<0) continue;

				switch (f.getType()) {
				case 'C': {
					String val= row.getString(ind);
					if (val!=null) {
						byte[] b= val.getBytes(charset);
						if (f.getLength()<b.length) throw new RuntimeException("Переполнение в поле: "+f.getName()+": "+val);
						System.arraycopy(b, 0, buf, f.getOff(), b.length);
					}
				} break;
				case 'N':
				case 'F': {
					Double val= row.getDouble(ind);
					if (val!=null) {
						String s= String.format(f.getDecFormat(), val);
						s= s.replace(',', '.');
						byte[] b= s.getBytes(fldChset);
						if (f.getLength()<b.length) throw new RuntimeException("Переполнение в поле: "+f.getName()+": "+s);
						System.arraycopy(b, 0, buf, f.getOff(), b.length);
					}
				} break;
				case 'D': {
					Date val= row.getDate(ind);
					if (val!=null) {
						String s= KDTime.sDate(val); //String.format(f.getDecFormat(), val.getYear(), val.getMonth(), val.getDate());
						byte[] b= s.getBytes(fldChset);
						if (f.getLength()!=8) throw new RuntimeException("Переполнение в поле: "+f.getName()+": "+s);
						System.arraycopy(b, 0, buf, f.getOff(), 8);
					}
				}break;
				}
			}			

			out.write(buf, 0, recLen);
		}

		out.write(26);
	}
	
	private static void skip(OutputStream out, int cnt) throws IOException {
		for (int i= 0; i< cnt; i++) out.write(0);
	}
}
