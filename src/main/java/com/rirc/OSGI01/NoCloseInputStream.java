package com.rirc.OSGI01;

import java.io.IOException;
import java.io.InputStream;

public class NoCloseInputStream extends InputStream {
	final private InputStream inp;
	
	public NoCloseInputStream(InputStream _inp) {
		inp= _inp;
	}
	
	@Override
	public int available() throws IOException {
		return inp.available();
	}
	
	@Override
	public void mark(int readlimit) {
		inp.mark(readlimit);
	}
	
	@Override
	public boolean markSupported() {
		return inp.markSupported();
	}

	@Override
	public int read() throws IOException {
		return inp.read();
	}

	@Override
	public int read(byte[] b) throws IOException {
		return inp.read(b);
	}
	
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		return inp.read(b, off, len);
	}
	
	@Override
	public void reset() throws IOException {
		inp.reset();
	}
	
	@Override
	public long skip(long n) throws IOException {
		return inp.skip(n);
	}
	
	@Override
	public void close() throws IOException {
	}
}
