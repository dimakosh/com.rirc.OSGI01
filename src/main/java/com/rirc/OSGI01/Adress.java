package com.rirc.OSGI01;

import java.sql.*;

public class Adress extends AdrBase {
	private static final long serialVersionUID = 1L;

	private Connection conn;

	public Adress(Connection _conn) {
		conn= _conn;
	}
	
	public AdrBase toAdrBase() {
		return new AdrBase(kodNP, kodStreet, house, corpus, kodSimvol, flat, flSim);
	}

	public Adress(Connection _conn, String npAdr) throws Exception {
		this(_conn);
		if (!AdrStr2KodAdr(npAdr, false, false)) throw new Exception("Ошибка разбора "+npAdr);
	}

	public Adress(Connection _conn, String npAdr, boolean lTestOnly, boolean lAddAvenu) throws Exception {
		this(_conn);
		if (!AdrStr2KodAdr(npAdr, lTestOnly, lAddAvenu)) throw new Exception("Ошибка разбора "+npAdr);
	}
	
	public boolean setNP(String name, boolean lApp)
	{
		try {
            if (name.isEmpty())
            {
                setKodNP(0);
                return false;
            }
            else
            {
            	name= Repl2UpTrimCir(name);
            	
            	kodNP= 0;
    			try (PreparedStatement pstmt= conn.prepareStatement(
    					"SELECT Kod FROM NP WHERE Name=?")) {
    				pstmt.setString(1, name);    				
    				try (ResultSet rs = pstmt.executeQuery()) {
    					if (rs.next()) kodNP= rs.getInt(1);
    				}
    			}

    			if (kodNP==0 && lApp) {
    				try (Statement stmt= conn.createStatement()) {
        				try (ResultSet rs = stmt.executeQuery("SELECT Max(Kod) FROM NP")) {
        					if (rs.next()) kodNP= rs.getInt(1);
        				}
    				}
    				kodNP++;

    				try (PreparedStatement pstmt= conn.prepareStatement(
        					"INSERT INTO NP (Kod,Name,Def) VALUES (?,?,0)")) {
        				pstmt.setInt(1, kodNP);
        				pstmt.setString(2, name);

        				pstmt.executeUpdate();
        			}
    			}

                return kodNP != 0;
            }
		} catch (Exception ex) {
        }
    	kodNP= 0;
        return false;
	}
	
	/*public boolean setStrAv(String street, String avenu) {
		kodStreet= 0;
		
		try (PreparedStatement pstmt= conn.prepareStatement(
				"SELECT s.KodStreet " +
			    "FROM Street s" +
			    " INNER JOIN Avenu a ON a.KodAvenu=s.KodAvenu "+
			    "WHERE s.Street=? and a.Avenu=?")) {
			pstmt.setString(1, street);
			pstmt.setString(2, avenu);			
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next())
				{
					kodStreet= rs.getInt(1);
					return true; 
				}
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}*/

	public boolean AdrStr2KodAdr(String npAdr) {
		return AdrStr2KodAdr(npAdr, false, false);
	}
	
	public boolean AdrStr2KodAdr(String npAdr, boolean lTestOnly, boolean lAddAvenu) {
		kodStreet= house= corpus= kodSimvol= flat= flSim= 0;

		try {
			int p= npAdr.indexOf(':');
			String adr;
			if (p==1) return false;
			if (p<0) adr= npAdr;
			else {
				if (!setNP(npAdr.substring(0, p), false)) return false;
				adr= npAdr.substring(p+1);
			}
			adr= Repl2UpTrimCir(adr);
			
			String[] aAdr= KDStr.a_split(adr, ' ');
	        if (aAdr.length == 2 || aAdr.length == 3 || aAdr.length == 4) {

	        	/*if (!setStrAv(aAdr[0], aAdr[1])) return false;
				if (3<=aAdr.length) {
					if (!setHouse(aAdr[2])) return false;
				}
				if (4<=aAdr.length) {
					if (!setFlat(aAdr[3])) return false;
				}*/
	        	
				if (3<=aAdr.length) {
					if (!setHouse(aAdr[2])) return false;
				}
				if (4<=aAdr.length) {
					if (!setFlat(aAdr[3])) return false;
				}

				try (PreparedStatement pstmt= conn.prepareStatement("SELECT KodStreet FROM AdrStr2KodAdr(?,?,?,?,?,?,?,?,?,?)")) {

					pstmt.setInt(1, getKodNP());
					pstmt.setString(2, aAdr[0]);			
					pstmt.setString(3, aAdr[1]);
					
					/*pstmt.setInt(4, getHouse());
					pstmt.setInt(5, getCorpus());
					pstmt.setString(6, ""+KodSim2Sim(getKodSimvol()));
					pstmt.setInt(7, getFlat());
					pstmt.setString(8, ""+KodSim2Sim(getFlSim()));*/

					pstmt.setInt(4, 0);
					pstmt.setInt(5, 0);
					pstmt.setString(6, "");
					pstmt.setInt(7, 0);
					pstmt.setString(8, "");
					
					pstmt.setBoolean(9, lTestOnly);
					pstmt.setBoolean(10, lAddAvenu);

					try (ResultSet rs = pstmt.executeQuery()) {
						if (rs.next()) kodStreet= rs.getInt(1);
					}
				}
	        }
	        
	        return true;
		} catch (Exception ex) {
			return false;
		}
	}
}
