package com.rirc.OSGI01;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DBFRecord implements Iterable<Entry<String, Object>> {
	final protected byte[] buf;
	final protected List<DBFField> aFields;
	final protected Map<String,DBFField> mFields;
	final protected Charset charset;
	
	protected DBFRecord(short recLen, List<DBFField> _aFields, Map<String,DBFField> _mFields, Charset _charset) {
		buf= new byte[recLen];
		aFields= _aFields;
		mFields= _mFields;
		charset= _charset;
	}
	
	private DBFField getFld(String name) {
		DBFField f= mFields.get(name);
		if (f==null) {
			f= mFields.get(name.toUpperCase());
			if (f==null) throw new RuntimeException("Нет поля: "+name);
			else mFields.put(name, f);
		}
		return f;
	}

	public String getOrigString(int n) {
		DBFField f= aFields.get(n);
		String s= new String(buf, f.getOff(), f.getLength(), charset);
		return s;
	}
	public String getOrigString(DBFField f) {
		String s= new String(buf, f.getOff(), f.getLength(), charset);
		return s;
	}
	public String getOrigString(String name) {
		DBFField f= getFld(name);
		String s= new String(buf, f.getOff(), f.getLength(), charset);
		return s;
	}
	
	public String getString(int n) {
		return getOrigString(n).stripTrailing();
	}
	public String getString(DBFField f) {
		return getOrigString(f).stripTrailing();
	}
	public String getString(String name) {
		return getOrigString(name).stripTrailing();
	}
	
	public int getInt(int n) {
		return pInt(getOrigString(n));
	}
	public int getInt(DBFField f) {
		return pInt(getOrigString(f));
	}
	public int getInt(String name) {
		return pInt(getOrigString(name));
	}

	public double getDouble(int n) {
		return pDouble(getOrigString(n));
	}
	public double getDouble(DBFField f) {
		return pDouble(getOrigString(f));
	}
	public double getDouble(String name) {
		return pDouble(getOrigString(name));
	}

	public Date getDate(int n) {
		String s= getOrigString(n);
		
		int year= pInt(s.substring(0, 4));
		int month= pInt(s.substring(4, 6));;
		int date= pInt(s.substring(6, 8));;
		
		return getDate(year, month, date);
	}
	public Date getDate(DBFField f) {
		String s= getOrigString(f);
		
		int year= pInt(s.substring(0, 4));
		int month= pInt(s.substring(4, 6));;
		int date= pInt(s.substring(6, 8));;
		
		return getDate(year, month, date);
	}
	public Date getDate(String name) {
		String s= getOrigString(name);
		
		int year= pInt(s.substring(0, 4));
		int month= pInt(s.substring(4, 6));;
		int date= pInt(s.substring(6, 8));;
		
		return getDate(year, month, date);
	}

	@Override
	public Iterator<Entry<String, Object>> iterator() {
		return new Iterator<Entry<String, Object>>() {
			Iterator<Entry<String,DBFField>> ie= mFields.entrySet().iterator();

			@Override
			public boolean hasNext() {
				return ie.hasNext();
			}
			@Override
			public Entry<String, Object> next() {
				Entry<String,DBFField> e= ie.next(); 
				
				return new Entry<String, Object>() {
					@Override
					public String getKey() {
						return e.getKey();
					}

					@Override
					public Object getValue() {
						DBFField f= e.getValue();
						
						switch (f.getType()) {
						case 'C': return getString(f);
						case 'N':
						case 'F': return getDouble(f);
						case 'D': return getDate(f);
						default: throw new RuntimeException("Неверный тип: "+f.getType()); 
						}
					}
					@Override
					public Object setValue(Object value) {
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}

	private static int pInt(String s) {
		return (s.isBlank())? 0 : Integer.parseInt(s);
	}
	private static double pDouble(String s) {
		return (s.isBlank())? 0d : Double.parseDouble(s);
	}
	
	@SuppressWarnings("deprecation")
	private static Date getDate(int year, int month, int date) {
		if (year<1 || 9999<year) return null;
		int y= year-1900;
		int m= month-1;
		Date d= new Date(y, m, date);
		return (y==d.getYear() && m==d.getMonth() && date==d.getDate())? d:null;
	}
	
	@Override
	public String toString() {
		return "Record [buf=" + Arrays.toString(buf) + "]";
	}
}
