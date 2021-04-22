package com.rirc.OSGI01;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

//Date date = (Date) obj;
//SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
//return simpledateformat.format(date);

public class KDTime {
	
	public static class KDMarshDate implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String xml;

		public KDMarshDate() {
		}
		public KDMarshDate(Date d) {
			xml= toXML(d);
		}
		public Date getDate() {
			return fromXML(xml);
		}
		public String getXML() {
			return xml;
		}
		public java.sql.Date getSQLDate() {
			return sqlDate(getDate());
		}
	};
	
	public static KDMarshDate getMarsh(Date d) {
		return new KDMarshDate(d);
	}

	@SuppressWarnings("deprecation")
	public static int getDay(Date dt) {
		return dt.getDate();
	}
	@SuppressWarnings("deprecation")
	public static int getMonth(Date dt) {
		return dt.getMonth()+1;
	}
	@SuppressWarnings("deprecation")
	public static int getYear(Date dt) {
		return dt.getYear()+1900;
	}

	@SuppressWarnings("deprecation")
	public static int getHours(Date dt) {
		return dt.getHours();
	}
	
	@SuppressWarnings("deprecation")
	public static int getMinutes(Date dt) {
		return dt.getMinutes();
	}

	public static int getKvartal(Date dt) {
		return getKvartal(getMonth(dt));
	}
	
	public static int getKvartal(int month) {
		switch (month) {
		case  1: return 1;
		case  2: return 1;
		case  3: return 1;
		case  4: return 2;
		case  5: return 2;
		case  6: return 2;
		case  7: return 3;
		case  8: return 3;
		case  9: return 3;
		case 10: return 4;
		case 11: return 4;
		case 12: return 4;
		default:
			throw new IllegalArgumentException("Ошибка getKvartal: месяц="+month);
		}
	}

	public static int getKvart2Month(int kv) {
		switch (kv) {
		case  1: return 3;
		case  2: return 6;
		case  3: return 9;
		case  4: return 12;
		default:
			throw new IllegalArgumentException("Ошибка getKvar2Mn: квартал="+kv);
		}
	}

	public static int getKvart2MonthNoEx(int kv) {
		switch (kv) {
		case  1: return 3;
		case  2: return 6;
		case  3: return 9;
		case  4: return 12;
		default: return 0;
		}
	}
	
	@SuppressWarnings("deprecation")
	public static Date getCurDate() {
		Date d= new Date();
		return new Date(d.getYear(), d.getMonth(), d.getDate());
	}

	@SuppressWarnings("deprecation")
	public static Date getCurDateTime() {
		Date d= new Date();
		return new Date(d.getYear(), d.getMonth(), d.getDate(), d.getHours(), d.getMinutes(), d.getSeconds());
	}

	public static int getCurMonth() {
		return Date2Month(getCurDate());
	}
	
	@SuppressWarnings("deprecation")
	public static Date getDate(int year, int month, int date) {
		if (year<1 || 9999<year) return null;
		int y= year-1900;
		int m= month-1;
		Date d= new Date(y, m, date);
		return (y==d.getYear() && m==d.getMonth() && date==d.getDate())? d:null;
	}

	@SuppressWarnings("deprecation")
	public static Date getDate(int year, int month, int date, int hrs, int min) {
		if (year<1 || 9999<year) return null;
		int y= year-1900;
		int m= month-1;
		Date d= new Date(y, m, date, hrs, min);
		return (y==d.getYear() && m==d.getMonth() && date==d.getDate() && hrs==d.getHours() && min==d.getMinutes())? d:null;
	}

	@SuppressWarnings("deprecation")
	public static Date getDate(Date d) {
		return (d==null)? null : new Date(d.getYear(), d.getMonth(), d.getDate());
	}
	
	@SuppressWarnings("deprecation")
	public static int Date2Month(Date dt) {
		return (dt==null)? 0:(dt.getYear()+1900)*12 + (dt.getMonth()+1) -1;
	}

	public static int Date2Month(int year, int month) {
		if (year<0) throw new IllegalArgumentException("year="+year);
		if (month<1 || 12<month) throw new IllegalArgumentException("month="+month);
		return year*12 + month -1;
	}
	
	public static Date Month2Date(int month) {
		if (month<0) throw new IllegalArgumentException("month="+month);
		int y= month/12;
		int m= month- y*12 +1;
		return getDate(y, m, 1);
	}

	public static Date Month2LDate(int month) {
		if (month<0) throw new IllegalArgumentException("month="+month);
		int y= month/12;
		int m= month- y*12 +1;

		int d;
		switch (m) {
			case 1: d= 31; break;
			case 2: {
				if ((y%4)==0 && ( (y%100)!=0 || (y%400)==0 )) {
					d= 29;
				} else {
					d= 28;
				}
				break;
			}
			case 3: d= 31; break;
			case 4: d= 30; break;
			case 5: d= 31; break;
			case 6: d= 30; break;
			case 7: d= 31; break;
			case 8: d= 31; break;
			case 9: d= 30; break;
			case 10: d= 31; break;
			case 11: d= 30; break;
			case 12: d= 31; break;
			default: return null;
		}
		return getDate(y, m, d);
	}

	public static int betweenDays(Date dtBeg, Date dtEnd) {
		LocalDate ldBeg= LocalDate.of(KDTime.getYear(dtBeg), KDTime.getMonth(dtBeg), KDTime.getDay(dtBeg));
		LocalDate ldEnd= LocalDate.of(KDTime.getYear(dtEnd), KDTime.getMonth(dtEnd), KDTime.getDay(dtEnd));
		return ((int)(ChronoUnit.DAYS.between(ldBeg, ldEnd))) +1;
	}
	
	public static Date addDays(Date dt, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}
	
	/*
	public static Date addDaysGWTClient(Date dt, int days) {
		Date rd= CalendarUtil.copyDate(dt);
		CalendarUtil.addDaysToDate(rd, days);
		return rd;
	}
	
	@SuppressWarnings("deprecation")
	public static Date addMinutesGWTClient(Date dt, int minutes) {

		Date rd= new Date(dt.getYear(), dt.getMonth(), dt.getDate());
		int h= dt.getHours();
		int m= dt.getMinutes();
		
		int ma= h*60+m+minutes;
		int ad= ma/(24*60);

		CalendarUtil.addDaysToDate(rd, ad);

		int am= ma%(24*60);
		int sh= am/60;
		int sm= am%60;
		
		return new Date(rd.getYear(), rd.getMonth(), rd.getDate(), sh, sm);
	}
	
	@SuppressWarnings("deprecation")
	public static int getBetweenMinutesGWTClient(Date start, Date finish) {

		Date s= new Date(start.getYear(), start.getMonth(), start.getDate());
		Date f= new Date(finish.getYear(), finish.getMonth(), finish.getDate());

		return CalendarUtil.getDaysBetween(s, f)*24*60 + (finish.getHours()*60+finish.getMinutes()) - (start.getHours()*60+start.getMinutes());
	}
	*/

	public static Date ddMnYyyy(String s) {
		return ddMnYyyy(s, false);
	}
	
	public static Date ddMnYyyy(String s, boolean year4dig) {
		if (s==null || s.isEmpty()) return null;
		try {
			s= s.replace('.', '/');
			String[] ar= KDStr.a_split(s, '/');
			if (ar.length==3) {
				int d= Integer.parseInt(ar[0]);
				int m= Integer.parseInt(ar[1]);
				int y= Integer.parseInt(ar[2]);
				
				if (year4dig) {
					if (y<100) return null;
				} else {
					if (y<50) y+= 2000;
					else if (50<=y && y<100) y+= 1900;
				}
				
				return KDTime.getDate(y, m, d);
			}
		} catch (Exception ex) {
		}
		return null;
	}

	public static Date ddMnYyyyHHmm(String s0) {
		if (s0==null || s0.isEmpty()) return null;
		try {
			s0= s0.replace('.', '/').trim();
			String[] ar0= KDStr.a_split(s0, ' ');
			if (ar0.length!=2) return null;
			
			String[] ar1= KDStr.a_split(ar0[0], '/');
			String[] ar2= KDStr.a_split(ar0[1], ':');
			if (ar1.length==3 && ar2.length==2) {
				int d= Integer.parseInt(ar1[0]);
				int m= Integer.parseInt(ar1[1]);
				int y= Integer.parseInt(ar1[2]);
				
				int hrs= Integer.parseInt(ar2[0]);
				int min= Integer.parseInt(ar2[1]);
				
				if (y<100) return null;
				
				return KDTime.getDate(y, m, d, hrs, min);
			}
		} catch (Exception ex) {
		}
		return null;
	}
	
	public static Date ddMnYyyyNoDelim(String s, boolean year4dig) {
		if (s==null) return null;
		try {
			int d= Integer.parseInt(s.substring(0,2));
			int m= Integer.parseInt(s.substring(2,2+2));
			int y= Integer.parseInt(s.substring(4));
			
			if (year4dig) {
				if (y<100) return null;
			} else {
				if (y<50) y+= 2000;
				else if (50<=y && y<100) y+= 1900;
			}

			return KDTime.getDate(y, m, d);
		} catch (Exception ex) {
			return null;
		}
	}

	public static String ddMnYyyy(Date dt) {
		return ddMnYyyy(dt, '/');
	}
	
	@SuppressWarnings("deprecation")
	public static String ddMnYyyy(Date dt, char delim) {
		if (dt==null) return "";
		return
			Num.ZStr(dt.getDate(),2)+delim+
			Num.ZStr(dt.getMonth()+1,2)+delim+
			Num.ZStr(dt.getYear()+1900,4);
	}

	@SuppressWarnings("deprecation")
	public static String ddMnYy(Date dt, char delim) {
		if (dt==null) return "";
		return
			Num.ZStr(dt.getDate(),2)+delim+
			Num.ZStr(dt.getMonth()+1,2)+delim+
			Num.ZStr(dt.getYear()+1900,4).substring(2);
	}
	
	@SuppressWarnings("deprecation")
	public static String sDate(Date dt) {
		if (dt==null) return "";
		return
			Num.ZStr(dt.getYear()+1900,4)+
			Num.ZStr(dt.getMonth()+1,2)+				
			Num.ZStr(dt.getDate(),2);
	}

	@SuppressWarnings("deprecation")
	public static String sDDMMYY(Date dt) {
		if (dt==null) return "";
		return
			Num.ZStr(dt.getDate(),2)+
			Num.ZStr(dt.getMonth()+1,2)+
			Num.ZStr(dt.getYear()+1900,4).substring(2);
	}

	@SuppressWarnings("deprecation")
	public static String sYYYYMMDDHHMM(Date dt) {
		if (dt==null) return "";
		return
			Num.ZStr(dt.getYear()+1900,4)+
			Num.ZStr(dt.getMonth()+1,2)+
			Num.ZStr(dt.getDate(),2)+
			Num.ZStr(dt.getHours(),2)+
			Num.ZStr(dt.getMinutes(),2);
	}

	@SuppressWarnings("deprecation")
	public static String sYYYYMM(Date dt) {
		if (dt==null) return "";
		return
			Num.ZStr(dt.getYear()+1900,4)+
			Num.ZStr(dt.getMonth()+1,2);
	}

	public static String sYYYYMM(int month) {
		return sYYYYMM(Month2Date(month));
	}
	
	public static String ddMnYyyyHhMm(String s) {
		return ddMnYyyyHhMm(fromXML(s));
	}	
	
	@SuppressWarnings("deprecation")
	public static String ddMnYyyyHhMm(Date dt) {
		if (dt==null) return "";
		return
			Num.ZStr(dt.getDate(),2)+'/'+
			Num.ZStr(dt.getMonth()+1,2)+'/'+
			Num.ZStr(dt.getYear()+1900,4)+' '+
			Num.ZStr(dt.getHours(),2)+':'+
			Num.ZStr(dt.getMinutes(),2);
	}
	
	@SuppressWarnings("deprecation")
	public static String mnYyyy(Date dt) {
		if (dt==null) return "";
		return
			Num.ZStr(dt.getMonth()+1,2)+'/'+
			Num.ZStr(dt.getYear()+1900,4);
	}
	
	@SuppressWarnings("deprecation")
	public static String mnYy(Date dt) {
		if (dt==null) return "";
		return
			Num.ZStr(dt.getMonth()+1,2)+'/'+
			Num.ZStr((dt.getYear()+1900)%100,2);
	}

	public static String mnYyyy(int month) {
		return mnYyyy(Month2Date(month));
	}

	public static String mnYyyy() {
		return mnYyyy( new Date() );
	}
	
	public static int mnYyyy(String s) {
		if (s==null || s.length()!=7) return 0;
		
		try
		{
			return Integer.parseInt(s.substring(3, 7))*12 + Integer.parseInt(s.substring(0, 2)) -1;
		} catch (Exception ex) {
			return 0;
		}
	}

	@SuppressWarnings("deprecation")
	public static String toSQL(Date dt) {
		if (dt==null) return "null";
		return '\''+
			Num.ZStr(dt.getYear()+1900,4)+'-'+
			Num.ZStr(dt.getMonth()+1,2)+'-'+
			Num.ZStr(dt.getDate(),2)+
			'\'';
	}

	public static boolean isBetween(Date dt, Date dtBeg, Date dtEnd) {
		return 0<=dt.compareTo(dtBeg) && dt.compareTo(dtEnd)<=0;
	}
	
	@SuppressWarnings("deprecation")
	public static String toXML(Date dt) {
		if (dt==null) return "";
		else {
			int h= 0;
			int m2= 0;
			try {
				h= dt.getHours();
				m2= dt.getMinutes();
			} catch (Exception ex) {
			}
			
			if (h==0 && m2==0)
				return
					Num.ZStr(dt.getYear()+1900,4)+'-'+
					Num.ZStr(dt.getMonth()+1,2)+'-'+
					Num.ZStr(dt.getDate(),2);
			else
				return
					Num.ZStr(dt.getYear()+1900,4)+'-'+
					Num.ZStr(dt.getMonth()+1,2)+'-'+
					Num.ZStr(dt.getDate(),2)+' '+
					Num.ZStr(h,2)+':'+
					Num.ZStr(m2,2);
		}
	}
	
	@SuppressWarnings("deprecation")
	public static String toXML1(Date dt) {
		if (dt==null) return "";
		else {
			return
				Num.ZStr(dt.getYear()+1900,4)+'-'+
				Num.ZStr(dt.getMonth()+1,2)+'-'+
				Num.ZStr(dt.getDate(),2);
		}
	}

	@SuppressWarnings("deprecation")
	public static String toXML2(Date dt) {
		if (dt==null) return "";
		else {
			int h= 0;
			int m2= 0;
			int s= 0;
			try {
				h= dt.getHours();
				m2= dt.getMinutes();
				s= dt.getSeconds();
			} catch (Exception ex) {
			}
			
			if (h==0 && m2==0 && s==0)
				return
					Num.ZStr(dt.getYear()+1900,4)+'-'+
					Num.ZStr(dt.getMonth()+1,2)+'-'+
					Num.ZStr(dt.getDate(),2);
			else
				return
					Num.ZStr(dt.getYear()+1900,4)+'-'+
					Num.ZStr(dt.getMonth()+1,2)+'-'+
					Num.ZStr(dt.getDate(),2)+' '+
					Num.ZStr(h,2)+':'+
					Num.ZStr(m2,2)+':'+
					Num.ZStr(s,2);
		}
	}
	
	public static Date fromXML(String s) {
		if (s==null || s.isEmpty()) return null;
		try {
			String[] ar1= KDStr.a_split(s, ' ');
			if (ar1.length<=1) {
				String[] ar2= KDStr.a_split(s, '-');
				if (ar2.length==3) {
					int d= Integer.parseInt(ar2[2]);
					int m= Integer.parseInt(ar2[1]);
					int y= Integer.parseInt(ar2[0]);
					return KDTime.getDate(y, m, d);
				} else return null;
			} else {
				String[] ar2= KDStr.a_split(ar1[0], '-');
				String[] ar3= KDStr.a_split(ar1[1], ':');
				
				if (ar2.length==3 && ar3.length>=2) {
					int d= Integer.parseInt(ar2[2]);
					int m1= Integer.parseInt(ar2[1]);
					int y= Integer.parseInt(ar2[0]);
					int h= Integer.parseInt(ar3[0]);
					int m2= Integer.parseInt(ar3[1]);
					return KDTime.getDate(y, m1, d, h, m2);
				} else return null;
			}
		} catch (Exception ex) {
			return null;
		}
	}
	
	public static Object toObj(Date dt) {
		if (dt==null) return null;
		return toXML(dt);
	}
	
	public static java.util.Date fromObj(Object o) {
		if (o==null) return null;
		return fromXML(o.toString());
	}
	
	@SuppressWarnings("deprecation")
	public static java.sql.Date sqlDate(Date d) {
		return (d==null)? null : new java.sql.Date(d.getYear(), d.getMonth(), d.getDate());
	}
	public static java.sql.Date sqlDate(String s) {
		return sqlDate(fromXML(s.substring(0, 10)));
	}
	
	public static java.sql.Timestamp sqlTimestamp(Date d) {
		return (d==null)? null : new java.sql.Timestamp(d.getTime());
	}

	final private static String[] mName= new String[] {
		"Январь",
		"Февраль",
		"Март",
		"Апрель",
		"Май",
		"Июнь",
		"Июль",
		"Август",
		"Сентябрь",
		"Октябрь",
		"Ноябрь",
		"Декабрь"
	};	
	public static String getMonthName(int m) {
		return mName[m-1];
	}

	public static int NalogRepPer(int HZY, int HZPG, boolean H9M, int HZKV, int HZM, boolean HZG) {
		return Date2Month(HZY, (HZG)? 12 : (HZKV==0)? ((HZM==0)? ((HZPG==1)? 6 : ((H9M)? 9:12)):HZM) : getKvart2Month(HZKV));
	}

	public static int NalogRepPerNoEx(int HZY, int HZPG, boolean H9M, int HZKV, int HZM, boolean HZG) {
		if (HZY<0) return 0;
		int m= (HZG)? 12 : (HZKV==0)? ((HZM==0)? ((HZPG==1)? 6 : ((H9M)? 9:12)):HZM) : getKvart2MonthNoEx(HZKV);
		if (m<1 || 12<m) return 0;
		return HZY*12 + m -1;
	}
}
