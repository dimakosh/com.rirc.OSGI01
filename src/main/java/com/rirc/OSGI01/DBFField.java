package com.rirc.OSGI01;

public class DBFField {
	final private String name;
	final private char type;
	final private int length;
	final private int dec;
	private int off;
	
	private String format;

	public DBFField(String s, char c, int i, int j, int o) {
		s= s.trim().toUpperCase();

		if (s.length() > 10) throw new RuntimeException("Название поля не более 10: "+ s);
		if (c != 'C' && c != 'N' && c != 'L' && c != 'D' && c != 'F') throw new RuntimeException("Неверный тип: "+c);

		name= s;
		type= c;
		length= i;
		dec= j;
		off= o;
	}

	public void setOff(int o) {
		off= o;;
	}
	
	public String getName() {
		return name;
	}
	public char getType() {
		return type;
	}
	public int getLength() {
		return length;
	}
	public int getDec() {
		return dec;
	}
	public int getOff() {
		return off;
	}
	
	public String getDecFormat() {
		if (format==null) format= '%'+String.valueOf(length)+'.'+String.valueOf(dec)+'f';
		return format;
	}
}
