package com.rirc.OSGI01;

import java.io.Serializable;
import java.util.List;

public class AdrBase implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected int kodNP;
	protected int kodStreet;
	protected int house;
	protected int corpus;
	protected int kodSimvol;
	protected int flat;
	protected int flSim;

	public AdrBase() {
	}

	public AdrBase(int _kodNP, int _kodStreet) {
		kodNP= _kodNP;
		kodStreet= _kodStreet;
		house= 0;
		corpus= 0;
		kodSimvol= 0;
		flat= 0;
		flSim= 0;		
	}

	public AdrBase(int _kodNP, int _kodStreet, int _house, int _corpus, int _kodSimvol, int _flat, int _flSim) {
		kodNP= _kodNP;
		kodStreet= _kodStreet;
		house= _house;
		corpus= _corpus;
		kodSimvol= _kodSimvol;
		flat= _flat;
		flSim= _flSim;		
	}
	
	@Override
    public String toString() {
        return
        		String.valueOf(kodNP)+','+
        		String.valueOf(kodStreet)+','+
        		String.valueOf(house)+','+
        		String.valueOf(corpus)+','+
        		String.valueOf(kodSimvol)+','+
        		String.valueOf(flat)+','+
        		String.valueOf(flSim);
    }	
	
	public int getKodNP() {
		return kodNP;
	}
	public void setKodNP(int kodNP) {
		this.kodNP = kodNP;
	}	
	public int getKodStreet() {
		return kodStreet;
	}
	public void setKodStreet(int kodStreet) {
		this.kodStreet = kodStreet;
	}
	public int getHouse() {
		return house;
	}
	public void setHouse(int house) {
		this.house = house;
	}
	public int getCorpus() {
		return corpus;
	}
	public void setCorpus(int corpus) {
		this.corpus = corpus;
	}
	public int getKodSimvol() {
		return kodSimvol;
	}
	public void setKodSimvol(int kodSimvol) {
		this.kodSimvol = kodSimvol;
	}
	public int getFlat() {
		return flat;
	}
	public void setFlat(int flat) {
		this.flat = flat;
	}
	public int getFlSim() {
		return flSim;
	}
	public void setFlSim(int flSim) {
		this.flSim = flSim;
	}
	
    public static String Repl2UpTrimCir(String adr)
    {
    	adr= KDStr.DelZhir(adr);
        adr = adr.toUpperCase();
        adr= KDStr.Lat2Kir(adr);

        return adr;
    }
 
    public static char KodSim2Sim(int ks)
    {
        char c;

        switch (ks) {
            case 1: c = '??'; break;
            case 2: c = '??'; break;
            case 3: c = '??'; break;
            case 4: c = '??'; break;
            case 5: c = '??'; break;
            case 6: c = '??'; break;
            case 7: c = '??'; break;
            case 8: c = '??'; break;
            case 9: c = '??'; break;
            case 10: c = '??'; break;
            case 11: c = '??'; break;
            case 12: c = '??'; break;
            case 13: c = '??'; break;
            case 14: c = '??'; break;
            case 15: c = '??'; break;
            case 16: c = '??'; break;
            case 17: c = '??'; break;
            case 18: c = '??'; break;
            case 19: c = '??'; break;
            case 20: c = '??'; break;
            case 21: c = '??'; break;
            case 22: c = '??'; break;
            case 23: c = '??'; break;
            case 24: c = '??'; break;
            case 25: c = '??'; break;
            case 26: c = '??'; break;
            case 27: c = '??'; break;
            case 28: c = '??'; break;
            case 29: c = '??'; break;
            case 30: c = '??'; break;
            default: c = ' ';
        }

        return c;
    }
    
    public static int Sim2KodSim(char s)
    {
        int ks;

        switch (s) {
            case '??': ks = 1; break;
            case '??': ks = 2; break;
            case '??': ks = 3; break;
            case '??': ks = 4; break;
            case '??': ks = 5; break;
            case '??': ks = 6; break;
            case '??': ks = 7; break;
            case '??': ks = 8; break;
            case '??': ks = 9; break;
            case '??': ks = 10; break;
            case '??': ks = 11; break;
            case '??': ks = 12; break;
            case '??': ks = 13; break;
            case '??': ks = 14; break;
            case '??': ks = 15; break;
            case '??': ks = 16; break;
            case '??': ks = 17; break;
            case '??': ks = 18; break;
            case '??': ks = 19; break;
            case '??': ks = 20; break;
            case '??': ks = 21; break;
            case '??': ks = 22; break;
            case '??': ks = 23; break;
            case '??': ks = 24; break;
            case '??': ks = 25; break;
            case '??': ks = 26; break;
            case '??': ks = 27; break;
            case '??': ks = 28; break;
            case '??': ks = 29; break;
            case '??': ks = 30; break;
            default: ks = 0;
        }

        return ks;
    }
	
	public boolean setHouse(String p) {
        int h = 0;
        int c = 0;
        int s = 0;

        String ds = Repl2UpTrimCir(p);
        if (ds.length() > 0) {
            String l = ds.substring(ds.length() - 1);
            if (Character.isLetter(l.charAt(0))) {
                ds = ds.substring(0, ds.length() - 1);
                s = Sim2KodSim(l.charAt(0));
                if (s<=0) return false;
            }

            ds = ds.replace('.', '/');
            String[] ar = KDStr.a_split(ds, '/');
            try {
                h = Integer.parseInt(ar[0]);
                if (1 < ar.length)
                    c = Integer.parseInt(ar[1]);

                if (Short.MAX_VALUE<=h || Short.MAX_VALUE<=c || Short.MAX_VALUE<=s) return false;
                
                house= h;
                corpus= c;
                kodSimvol= s;
                
                return true;
            } catch (Exception ex) {
                return false;            	
            }
        }
        return false;
	}

	public boolean setFlat(String p) {
        int f = 0;
        int s = 0;

        String ds = Repl2UpTrimCir(p);
        if (ds.length() > 0) {
            String l = ds.substring(ds.length() - 1);
            if (Character.isLetter(l.charAt(0))) {
                ds = ds.substring(0, ds.length() - 1);
                s = Sim2KodSim(l.charAt(0));
                if (s<=0) return false;
            }

            ds = ds.replace('.', '/');
            String[] ar = KDStr.a_split(ds, '/');
            try {
                f = Integer.parseInt(ar[0]);
                if (1 < ar.length) {
                	if (0<s) return false;
                    s = -Integer.parseInt(ar[1]);
                }

                if (Short.MAX_VALUE<=f || Short.MAX_VALUE<=s) return false;
                
            	flat= f;
            	flSim= s;
                
                return true;
            } catch (Exception ex) {
                return false;            	
            }
        }
        return false;
	}

	static public boolean adrParse(String[] aAdrShb, List<String> aStrElem, AdrBase adrBase) {
		String house= null;
		String flat= null;

		for (int i= aAdrShb.length-1; i >= 0; i--) {
			String el= aAdrShb[i];

			if (house==null && i!=0 && Character.isDigit(el.charAt(0))) {
				if (flat==null) {
					flat= el;
					continue;
				} else {
					house= el;
					continue;
				}
			}
			
			aStrElem.add(el);
		}
		
		if (aStrElem.isEmpty()) return false;
		
		if (house==null && flat!=null) {
	        house = flat;
	        flat = "0";
	    } else {
	    	if (flat==null) flat = "0";
	    }
		
		if (house==null) return false;
		
		return adrBase.setHouse(house) && adrBase.setFlat(flat);
	}
}
