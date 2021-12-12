package com.rirc.OSGI01;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class KDFile {
	public static File createTemp() throws IOException {
		Path path= Files.createTempFile(null, null);
		File file= path.toFile();
		return file;
	}
	public static File createTemp(String prefix, String suffix) throws IOException {
		Path path= Files.createTempFile(prefix, suffix);
		File file= path.toFile();
		return file;
	}

	public static BufferedReader bufferedReader(InputStream inp, String charset) throws IOException {
		return new BufferedReader(new InputStreamReader(inp, charset));
	}
	public static BufferedReader utf8BufferedReader(InputStream inp) throws IOException {
		return bufferedReader(inp, "UTF-8");
	}
	public static BufferedReader ansiBufferedReader(InputStream inp) throws IOException {
		return bufferedReader(inp, "Cp1251");
	}
	public static BufferedReader oemBufferedReader(InputStream inp) throws IOException {
		return bufferedReader(inp, "Cp866");
	}

	public static BufferedReader bufferedReader(File file, String charset) throws IOException {
		return new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
	}
	public static BufferedReader utf8BufferedReader(File file) throws IOException {
		return bufferedReader(file, "UTF-8");
	}
	public static BufferedReader ansiBufferedReader(File file) throws IOException {
		return bufferedReader(file, "Cp1251");
	}
	public static BufferedReader oemBufferedReader(File file) throws IOException {
		return bufferedReader(file, "Cp866");
	}
	

	public static PrintWriter printWriter(OutputStream out, String charset) throws IOException {
		return new PrintWriter(new OutputStreamWriter(out, charset));
	}
	public static PrintWriter utf8PrintWriter(OutputStream out) throws IOException {
		return printWriter(out, "UTF-8");
	}
	public static PrintWriter ansiPrintWriter(OutputStream out) throws IOException {
		return printWriter(out, "Cp1251");
	}
	public static PrintWriter oemPrintWriter(OutputStream out) throws IOException {
		return printWriter(out, "Cp866");
	}

	public static PrintWriter printWriter(File file, String charset) throws IOException {
		return new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
	}
	public static PrintWriter utf8PrintWriter(File file) throws IOException {
		return printWriter(file, "UTF-8");
	}
	public static PrintWriter ansiPrintWriter(File file) throws IOException {
		return printWriter(file, "Cp1251");
	}
	public static PrintWriter oemPrintWriter(File file) throws IOException {
		return printWriter(file, "Cp866");
	}
}
