package com.rirc.OSGI01;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Num {

	public static boolean in(int v, int ... l) {
		for (int i= 0; i< l.length; i++) if (v==l[i]) return true;
		return false;
	}

	public static boolean BoolVal(String s) {
    	if (s==null) return false;
		
        try
        {
            return Boolean.parseBoolean(s);
        }
        catch (Exception ex)
        {
        	return false;
        }
	}

	public static boolean BoolValEx(String s) {
    	if (s==null) return false;
    	if (s.equalsIgnoreCase("TRUE")) return true;
    	int n= IntVal(s);
    	return n!=0;
	}
	
	public static boolean BoolVal(double s) {
    	return IntVal(s)!=0;
    }

	public static boolean BoolVal_tf_01(String s) {
		if (s==null) return false;
		else
			if ("0".equals(s)) return false;
			else
				if ("1".equals(s)) return true;
				else
					if ("TRUE".equalsIgnoreCase(s)) return true;
					else
						if ("FALSE".equalsIgnoreCase(s)) return false;
						else
							throw new IllegalArgumentException("Error boolean parse: "+s);
    }
	
	public static short ShortVal(String s)
    {
    	if (s==null) return 0;
    	
        try
        {
            return Short.parseShort(s);
        }
        catch (Exception ex)
        {
        	return 0;
        }
    }
	
    public static int IntVal(String s)
    {
    	if (s==null) return 0;
    	
        try
        {
            return Integer.parseInt(s);
        }
        catch (Exception ex)
        {
        	return 0;
        }
    }

    public static int IntVal(double s) {
    	return (int)s;
    }
    
    public static long LongVal(String s)
    {
    	if (s==null) return 0;
    	
        try
        {
            return Long.parseLong(s);
        }
        catch (Exception ex)
        {
        	return 0;
        }
    }

    public static Integer IncInteger(Integer i) {
    	if (i==null) return 1;
    	else {
    		int d= i;
    		return Integer.valueOf(d+1);
    	}
    }
    
    public static double DoubleVal(String s) {
        try {
            return DoubleValOrThrow(s);
        } catch (Exception ex) {
        	return 0d;
        }
    }

    public static double DoubleVal(Double d) {
    	return (d==null)? Double.valueOf(0d):d;
    }
    
    public static double DoubleValOrThrow(String s) {
    	if (s==null) return 0d;
    	s= s.replace(',', '.').trim();
        return (s.isEmpty())? 0d:Double.parseDouble(s);
    }

    public static double DoubleValOrNaN(String s) {
    	if (s==null) return Double.NaN;
    	s= s.replace(',', '.').trim();
    	try {
    		return (s.isEmpty())? Double.NaN:Double.parseDouble(s);
    	} catch (Exception ex) {
    		return Double.NaN;
    	}
    }
    
    public static double Round(double val, int scale) {
    	BigDecimal d= BigDecimal.valueOf(val);
    	d= d.setScale(scale, RoundingMode.HALF_UP);
    	return d.doubleValue();
    }

    /*public static double Round20kop(double val) { // Ж/Д шный маразм
    	val= val*100d/20d;
    	val= Round(val, 0);
    	val= val*20d/100d;
    	return Round(val, 2);
    }*/

    public static double Round10kop2koef(double val) { // Ж/Д шный маразм
    	val= val*100d/10d;
    	val= Round(val, 0);
    	val= val*2*10d/100d;
    	return Round(val, 2);
    }
    
    /*public static double Round20kop2koef(double val) { // Ж/Д шный маразм
    	val= val*100d/20d;
    	val= Round(val, 0);
    	val= val*2*20d/100d;
    	return Round(val, 2);
    }*/
    
    public static double Truncate(double val, int scale) {
    	BigDecimal d= BigDecimal.valueOf(val);
    	d= d.setScale(scale, RoundingMode.DOWN);
    	return d.doubleValue();
    }

    public static String SStr(int d, int l) {
    	return Str(d, l, ' ');
    }
    public static String ZStr(int d, int l) {
    	return Str(d, l, '0');
    }
    public static String Str(int d, int l, char f) {
    	String st= String.valueOf(d);
    	if (l==0 || st.length()>=l) return st;
    	else {
        	StringBuilder sb= new StringBuilder(st);
        	int n= l-sb.length();
        	for (int i= 0; i< n; i++) sb.insert(0, f);
        	return sb.toString();
    	}
    }

    public static String SStr(long d, int l) {
    	return Str(d, l, ' ');
    }
    public static String ZStr(long d, int l) {
    	return Str(d, l, '0');
    }
    public static String Str(long d, int l, char f) {
    	String st= String.valueOf(d);
    	if (l==0 || st.length()>=l) return st;
    	else {
        	StringBuilder sb= new StringBuilder(st);
        	while (sb.length()<l) {
        		sb.insert(0, f);
        	}
        	return sb.toString();
    	}
    }
    
    public static String Str0(Double d) {
    	return Str(d, 0);
    }
    public static String Str2(Double d) {
    	return Str(d, 2);
    }

    public static String ZStr(Double d1, int l, int scale) {
    	String s= Str(d1, scale);
    	s= KDStr.addLeft(s, l, '0');
    	return s;
    }

    public static String SStr(Double d1, int l, int scale) {
    	String s= Str(d1, scale);
    	s= KDStr.addLeft(s, l, ' ');
    	return s;
    }
    
    private static BigDecimal bd10= BigDecimal.valueOf(10);
    
    public static String Str(Double d1, int scale) {

    	// Этот бред надо переписать !!!!!!!!!!!!!
    	
    	d1= Truncate(d1, scale);
    	
    	if (scale==0) return String.valueOf(d1.longValue());

    	//String s= Double.toString(d);

    	BigDecimal d2= BigDecimal.valueOf(d1);
    	for (int i= 0; i< scale; i++) d2= d2.multiply(bd10);
    	d2= d2.setScale(scale, RoundingMode.DOWN);
    	Long d= d2.toBigInteger().longValue();
    	
    	StringBuilder sb= new StringBuilder(d.toString());
    	int l= sb.length();
    	while ((l-scale)<1) {
    		sb.insert(0, '0');
    		l= sb.length();
    	}
    	sb.insert(l-scale, '.');
    	
    	String s= sb.toString();

    	//if (s.charAt(s.length()-2)=='.') s+= '0';
    	
    	return s;
    }

    public static String Str(Double d1) {
    	Double d2= Round(d1, 0);
    	if (Num.isZero(d1-d2)) return String.valueOf(d1.longValue());
    	else return String.valueOf(d1);
    }
    
	public static String getYesNo(boolean yn) {
		return (yn)? "да":"нет";
	}
	
	public static boolean isZero(double v) {
		return -0.00001d<v && v<0.00001d;
	}
	public static boolean isAbove0(double v) {
		return 0.00001d<v;
	}
	public static boolean isAboveOr0(double v) {
		return -0.00001d<v;
	}
	public static boolean isBelow0(double v) {
		return v<-0.00001d;
	}
	public static boolean isBelowOr0(double v) {
		return v<0.00001d;
	}
	public static boolean isEquals(double v1, double v2) {
		return isZero(v1-v2);
	}
}
