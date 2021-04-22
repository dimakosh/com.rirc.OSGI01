package com.rirc.OSGI01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class KDConnection {

	static public Connection get(ConnPrms connPrms) throws SQLException {
		return get(connPrms.getServer(), connPrms.getSource(), connPrms.getUser(), connPrms.getPass(), false);
	}
	
	static public Connection get(ConnPrms connPrms, boolean wait) throws SQLException {
		return get(connPrms.getServer(), connPrms.getSource(), connPrms.getUser(), connPrms.getPass(), wait);
	}
	
	static private Connection get(String server, String source, String user, String pass, boolean wait) throws SQLException {
		try {
			Class.forName("org.firebirdsql.jdbc.FBDriver");
		} catch (ClassNotFoundException ex) {
			throw new SQLException(ex);
		}

		String databaseURL= "jdbc:firebirdsql:" + server + ':' + source;

	    Properties connInfo = new Properties();
	    connInfo.put("user", user);
	    connInfo.put("password", pass);
	    connInfo.put("charSet", "Cp1251");
	    connInfo.put("TRANSACTION_READ_COMMITTED",
	    	(wait)? "isc_tpb_read_committed,isc_tpb_rec_version,isc_tpb_write,isc_tpb_wait" : "isc_tpb_read_committed,isc_tpb_rec_version,isc_tpb_write,isc_tpb_nowait");

	    try {
	    	return DriverManager.getConnection(databaseURL, connInfo);
	    } catch (Exception ex1) {
	    	SQLException ex2= new SQLException(databaseURL);
	    	ex2.addSuppressed(ex1);
	    	throw ex2;
	    }
	}
}
