package com.rirc.OSGI01;

import java.io.IOException;
import java.io.OutputStream;

public class NoCloseFlashOutputStream extends OutputStream {
	final private OutputStream out;
	
	public NoCloseFlashOutputStream(OutputStream _out) {
		out= _out;
	}

	@Override
	public void write(int b) throws IOException {
		out.write(b);
	}
	
	@Override
    public void write(byte b[]) throws IOException {
		out.write(b);
	}
	
	@Override
	public void write(byte b[], int off, int len) throws IOException {
		out.write(b, off, len);
	}

	@Override
    public void flush() throws IOException {
    }
	
	@Override
    public void close() throws IOException {
    }
}
