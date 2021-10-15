package com.rirc.OSGI01;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class KDStr {
	
	public static boolean equals(String s1, String s2) {
		return (s1==null)? s2==null : s1.equals(s2);
	}

	public static boolean equalsIgnoreCase(String s1, String s2) {
		return (s1==null)? s2==null : s1.equalsIgnoreCase(s2);
	}

	public static boolean contains(String[] s1, String s2) {
		for (String s : s1) {
			if (s.equals(s2)) return true;
		}
		return false;
	}
	
	public static String getExMessage(Throwable thr) {
		String s= thr.getMessage();
		if (s==null) s= thr.getClass().getName();
		if (s==null) s= "...";
		return s;
	}
	public static String getExMessage(Throwable thr, int maxLen) {
		String s= thr.getMessage();
		if (s==null) s= thr.getClass().getName();
		if (s==null) s= "...";
		else {
			if (s.length()>maxLen) s= s.substring(0, maxLen);
		}
		return s;
	}
	public static String getExAllInfo(Throwable thr) {
		try (ByteArrayOutputStream bw= new ByteArrayOutputStream()) {
			try (PrintStream ps= new PrintStream(bw, false, "UTF-8")) {
				thr.printStackTrace(ps);
			}
			return new String(bw.toByteArray(), "UTF-8");
		} catch (IOException ex) {
			return getExMessage(thr);
		}
	}
	
	public static String getTrimMaxLen(String s, int maxLen) {
		if (s==null) return null;
		if (maxLen<s.length()) s= s.substring(0,maxLen);
		return s;
	}
	
	public static String getNotNull(String s) {
		return (s==null)? "":s;
	}
	
	public static boolean isNullOrEmpty(String s) {
		return s==null || s.isEmpty();
	}
	
	public static String addLeft(String s, int l, char ch) {
		while (s.length()<l) s= ch+s;
		return s;
	}
	
	public static String addRight(String s, int len, char ch) {
		while (s.length()<len) s+= ch;
		return s;
	}

	public static String substring(String s, int b, int e) {
		int l= s.length();
		if (l<=b) return "";
		if (l<e) e= l;
		return (b<=e)? s.substring(b, e):"";
	}
	
	public static boolean isShbEq01(String shb, String s) {
		if (shb.length()!=s.length()) return false;
		
		for (int i= 0; i< shb.length(); i++) {
			char c1= shb.charAt(i);
			if (c1=='?') continue;
			char c2= s.charAt(i);
			if (c1!=c2) return false;
		}
		
		return true;
	}

    public static List<String> l_split(String str, char ch) {
    	List<String> list= new ArrayList<String>();

    	int i= 0;
    	int l= str.length();
    	int b= 0;
    	for (; i< l; i++) {
    		if (str.charAt(i)==ch) {
    			list.add( (b==i)? "": str.substring(b, i));
    			b= i+1;
    		}
    	}
    	if (0<i) list.add( (b>=i)? "": str.substring(b, i));
    	return list;
    }

    /*
    public static Collection<String> fs_plit(String str, char ch) {
    	return ls_plit(str, ch);
    }
    */

    public static Iterable<String> i_split(String str, char ch) {
    	return l_split(str, ch);
    }
    
    public static String[] a_split(String str, char ch) {
    	List<String> list= l_split(str, ch);
        String[] r = new String[list.size()];
    	return list.toArray(r);
    }
	
	public static List<String[]> split_diapasons(String str) {
		List<String[]> aElem= new ArrayList<String[]>();
		for (String s : i_split(str, ' ')) {
			if (!s.isEmpty()) {
				String[] el= KDStr.a_split(s.toUpperCase(), '-');
				aElem.add(el);
			}
		}
		return aElem;
	}

    @SuppressWarnings("deprecation")
	public static boolean isSpace(char c) {
    	return Character.isSpace(c) || c=='\u00A0' || c=='\u2007' || c=='\u202F';
    }

	@SuppressWarnings("deprecation")
	public static String DelSurogSpace(String s) {
		if (s==null) return null;

		try {
			s= new String(s.getBytes("Windows-1251"), "Windows-1251");
		} catch (Exception e) {
			return null;
		}
		
		int l= s.length();
		StringBuilder sb= new StringBuilder(l);
		for (int i= 0; i< l; i++) {
			char c= s.charAt(i);
			if (c<9) sb.append(' ');
			else {
				if (c!='\u00A0' && c!='\u2007' && c!='\u202F') {
					if (Character.isSpace(c)) sb.append(' ');
					else sb.append(c);
				}
			}
		}
		return sb.toString();
	}
    
	public static String DelZhir(String s) {
		if (s==null) return null;
		s= s.trim();
		while (s.indexOf("  ")>=0) s= s.replaceAll("  ", " ");
		return s;
	}

	public static String DelDelimZhir(String st, char ch) {
		if (st==null) return null;

		StringBuilder sb= new StringBuilder();
		for (String s : KDStr.i_split(st, ch)) {
			if (sb.length()>0) sb.append(ch);
			sb.append(KDStr.DelZhir(s));
		}
		return sb.toString().toUpperCase();
	}
	
	public static String TrimUpper(String s) {
		if (s==null) return null;
		s= s.trim().toUpperCase();
		return s;
	}
	
	public static String Lat2Kir(String s) {
		if (s==null) return null;

        s = s.replace('A', 'А');
        s = s.replace('B', 'В');
        s = s.replace('D', 'Д');
        s = s.replace('E', 'Е');
        s = s.replace('K', 'К');
        s = s.replace('M', 'М');
        s = s.replace('O', 'О');
        s = s.replace('K', 'К');
        s = s.replace('H', 'Н');
        s = s.replace('I', 'І');
        s = s.replace('Y', 'У');
        s = s.replace('P', 'Р');
        s = s.replace('C', 'С');
        s = s.replace('T', 'Т');
		
		return s;
	}

	public static String Kir2Lat(String s) {
		StringBuilder b= new StringBuilder();
		
		for (int i= 0; i< s.length(); i++) {
			char c= s.charAt(i);
			int p= "КЕНХВАРОСМИТУ".indexOf(c);
			if (0<=p) c= "KEHXBAPOCMITY".charAt(p);
			b.append(c);
		}
		return b.toString();
	}

	public static String ToUpLat(String s) {
		if (s==null) return "";
		return Kir2Lat(DelZhir(s).toUpperCase());
	}
	
	public static String DelNoLat(String s) {
		StringBuilder b= new StringBuilder();
		
		for (int i= 0; i< s.length(); i++) {
			char c= s.charAt(i);
			int p= "КЕНХВАРОСМИТУ".indexOf(c);
			if (0<=p) {
				c= "KEHXBAPOCMITY".charAt(p);
				b.append(c);
			} else {
				if ("ЁЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ".indexOf(c)<0) b.append(c); 
			}
		}
		return b.toString();
	}

	public static String ToUpKir(String s) {
		if (s==null) return "";
		return Lat2Kir(DelZhir(s).toUpperCase());
	}

	public static String DelNoKir(String s) {
		StringBuilder b= new StringBuilder();
		for (char c : s.toCharArray()) {
			if (0<=" -ЁЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ".indexOf(c)) {
				b.append(c);
			}
		}
		return b.toString();
	}
	
	public static boolean isKir1(char c) {
		return 0<=" -ЁЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ".indexOf(c);
	}

	public static boolean isKir1(String s) {
		for (char c : s.toCharArray()) {
			if (!isKir1(c)) return false;
		}
		return true;
	}
	
	public static void TstKir1(String s) {
		for (char c : s.toCharArray()) {
			if (!isKir1(c)) throw new RuntimeException("Недопустимый символ \'"+c+"\' в "+s);
		}
	}
	
	public static boolean isKir2(char c) {
		return 0<=" .-ЁЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ".indexOf(c);
	}

	public static boolean isKir2(String s) {
		for (char c : s.toCharArray()) {
			if (!isKir2(c)) return false;
		}
		return true;
	}
	
	public static void TstKir2(String s) {
		for (char c : s.toCharArray()) {
			if (!isKir2(c)) throw new RuntimeException("Недопустимый символ \'"+c+"\' в "+s);
		}
	}
	
	public static String DelNoDig(String s) {
		StringBuilder b= new StringBuilder();
		for (char c : s.toCharArray()) {
			if (Character.isDigit(c)) {
				b.append(c);
			}
		}
		return b.toString();
	}

	public static String DelNoNumb(String s) {
		StringBuilder b= new StringBuilder();
		for (char c : s.toCharArray()) {
			if (Character.isDigit(c) || c==',' || c=='.' || c=='-' || c=='+') {
				b.append(c);
			}
		}
		return b.toString();
	}

	public static String fstNumbChar(String s) {
		StringBuilder b= new StringBuilder();
		for (char c : s.toCharArray()) {
			if (Character.isDigit(c) || c==',' || c=='.' || c=='-' || c=='+') {
				b.append(c);
			} else break;
		}
		return b.toString();
	}
	
	public static String ToNoSpaceUpKir(String s) {
		if (s==null) return "";
		return ToUpKir(s).replace(" ", "");
	}
	
	public static String ToOneUpKirWrd(String s) {
		if (s==null) return "";
		return ToUpKir(s).replace(' ', '_');
	}

    /*public static String[] getTrueCharSeqs(String s, String trueChar) {

    	StringBuilder sb= new StringBuilder();
    	for (char c : s.toCharArray()) {
    		sb.append((0<=trueChar.indexOf(c))? c:' ');
    	}
    	List<String> r1= new ArrayList<String>();
    	for (String e : sb.toString().split(" ")) {
    		if (!e.isEmpty()) r1.add(e);
    	}
    	String[] r2= new String[r1.size()];
    	r1.toArray(r2);
    	return r2;
    }
    public static String getTrueCharSeq(String s, String trueChar) {

    	StringBuilder sb= new StringBuilder();
    	for (char c : s.toCharArray()) {
    		sb.append((0<=trueChar.indexOf(c))? c:' ');
    	}
    	for (String e : sb.toString().split(" ")) {
    		if (!e.isEmpty()) return e;
    	}
    	return "";
    }*/

    public static String[] getNumbSeqs(String s, String dopTrueChar) {

    	StringBuilder sb= new StringBuilder();
    	for (char c : s.toCharArray()) {
    		sb.append((Character.isDigit(c) || (dopTrueChar!=null && 0<=dopTrueChar.indexOf(c)))? c:' ');
    	}
    	List<String> r1= new ArrayList<String>();
    	for (String e : sb.toString().split(" ")) {
    		if (!e.isEmpty()) r1.add(e);
    	}
    	String[] r2= new String[r1.size()];
    	r1.toArray(r2);
    	return r2;
    }
    public static String getNumbSeq(String s, String dopTrueChar) {

    	StringBuilder sb= new StringBuilder();
    	for (char c : s.toCharArray()) {
    		sb.append((Character.isDigit(c) || (dopTrueChar!=null && 0<=dopTrueChar.indexOf(c)))? c:' ');
    	}
    	for (String e : sb.toString().split(" ")) {
    		if (!e.isEmpty()) return e;
    	}
    	return "";
    }
    
    public static boolean isLogin(String s) {
    	if (s==null || s.isEmpty()) return false;
    	for (int i= 1; i< s.length(); i++) {
    		char c= Character.toUpperCase(s.charAt(i));
    		if ("QWERTYUIOPASDFGHJKLZXCVBNM1234567890_-".indexOf(c)<0) return false;
    	}
    	return true;
    }
    public static boolean isPasswd(String s) {
    	if (s==null || s.isEmpty()) return false;
    	for (int i= 1; i< s.length(); i++) {
    		char c= Character.toUpperCase(s.charAt(i));
    		if ("QWERTYUIOPASDFGHJKLZXCVBNM1234567890_-~`!@#$%^&*()+{[}]\\|<>?/=.,:'\";".indexOf(c)<0) return false;
    	}
    	return true;
    }
	
	public static String map2String(Map<?,?> map) {
    	StringBuilder mess= new StringBuilder();
    	for (Entry<?,?> e : map.entrySet()) {
    		mess.append(e.getKey());
    		mess.append('=');
    		mess.append(e.getValue());
    		mess.append(';');
    	}
    	return mess.toString();
	}

	public static String toTransliter(String s) {
		if (s==null) return "";

		StringBuilder sb= new StringBuilder();
		for (int i= 0; i< s.length(); i++) sb.append(toTransliter(s.charAt(i)));

		return sb.toString();
	}
	
	public static String toTransliter(char c) {
		switch (c) {
		case 'А': return "A";
		case 'Б': return "B";
		case 'В': return "V";
		case 'Г': return "G";
		case 'Д': return "D";
		case 'Е': return "E";
		case 'Ё': return "E";
		case 'Ж': return "ZH";
		case 'З': return "Z";
		case 'И': return "I";
		case 'Й': return "I";
		case 'К': return "K";
		case 'Л': return "L";
		case 'М': return "M";
		case 'Н': return "N";
		case 'О': return "O";
		case 'П': return "P";
		case 'Р': return "R";
		case 'С': return "S";
		case 'Т': return "T";
		case 'У': return "U";
		case 'Ф': return "F";
		case 'Х': return "KH";
		case 'Ц': return "C";
		case 'Ч': return "CH";
		case 'Ш': return "SH";
		case 'Щ': return "SCH";
		case 'Ь': return "'";
		case 'Ы': return "Y";
		case 'Ъ': return "'";
		case 'Э': return "E";
		case 'Ю': return "YU";
		case 'Я': return "YA";
		case 'а': return "a";
		case 'б': return "b";
		case 'в': return "v";
		case 'г': return "g";
		case 'д': return "d";
		case 'е': return "e";
		case 'ё': return "e";
		case 'ж': return "zh";
		case 'з': return "z";
		case 'и': return "i";
		case 'й': return "i";
		case 'к': return "k";
		case 'л': return "l";
		case 'м': return "m";
		case 'н': return "n";
		case 'о': return "o";
		case 'п': return "p";
		case 'р': return "r";
		case 'с': return "s";
		case 'т': return "t";
		case 'у': return "u";
		case 'ф': return "f";
		case 'х': return "h";
		case 'ц': return "c";
		case 'ч': return "ch";
		case 'ш': return "sh";
		case 'щ': return "sch";
		case 'ь': return "'";
		case 'ы': return "y";
		case 'ъ': return "'";
		case 'э': return "e";
		case 'ю': return "yu";
		case 'я': return "ya";
		default: return String.valueOf(c);
		}
	}
}
