package com.rirc.OSGI01;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Iterator;

public final class Files {

	public static byte[] copy(InputStream in) throws IOException {
		try (ByteArrayOutputStream ba= new ByteArrayOutputStream()) {
			byte[] buf0= new byte[1024];
			int l;
			while ((l= in.read(buf0))>=0) ba.write(buf0, 0, l);
			return ba.toByteArray();
		}
	}
	
	public static void copy(InputStream in, OutputStream ou) throws IOException {
		byte[] buf0= new byte[1024];
		int l;
		while ((l= in.read(buf0))>=0) ou.write(buf0, 0, l);
	}

	public static Iterable<ByteBuffer> getByteBufferIterable(byte[] ldata, int bsize) {  
		return ()-> new Iterator<ByteBuffer>() {
			int dl= ldata.length;
			int offs;

			@Override
			public boolean hasNext() {
				return offs<dl; 
			}

			@Override
			public ByteBuffer next() {
				int len= dl-offs;
				if (bsize<len) len= bsize; 
				ByteBuffer bb= ByteBuffer.wrap(ldata, offs, len);
				offs+= len;
				return bb;
			}
		};
	}
	
	//public static InputStream getInputStream(ByteBuffer bb) {
	//	return new ByteArrayInputStream(bb.array(), bb.position(), bb.remaining());
		/*
		return new InputStream() {
			@Override
			public int available() throws IOException {
				return bb.remaining();
			}

			@Override
			public void mark(int readlimit) {
				bb.position(readlimit);
			}
			
			@Override
			public boolean markSupported() {
				return true;
			}

			@Override
			public int read() throws IOException {
				return (bb.hasRemaining())? bb.get():-1;
			}

			@Override
			public int read(byte[] b, int off, int len) throws IOException {
				int l= bb.remaining();
				if (l<=0) return -1;
				if (len<l) l= len;
				bb.get(b, off, l);
				return l;
			}
			
			@Override
			public void reset() throws IOException {
				bb.reset();
			}
			
			@Override
			public long skip(long N) throws IOException {
				int n= Math.toIntExact(N);
				int r= bb.remaining();
				int l= r;
				if (n<l) l= n;
				bb.position(r+l);
				return l;
			}
		};
		*/
	//}
}
