package com.rirc.OSGI01;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class DBFFileReader extends DBFReader implements Closeable {
	
	public static DBFFileReader open(File file, String charset) throws IOException {
		return new DBFFileReader(file, Charset.forName(charset));
	}
	public static DBFFileReader ansiOpen(File file) throws IOException {
		return open(file, "Cp1251");
	}
	public static DBFFileReader oemOpen(File file) throws IOException {
		return open(file, "Cp866");
	}

	protected DBFFileReader(File file, Charset charset) throws IOException {
		super(new FileInputStream(file), charset);
	}

	@Override
	public void close() throws IOException {
		inp.close();
	}
}
