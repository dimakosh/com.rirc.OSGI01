package com.rirc.OSGI01;

import java.sql.Connection;
import java.sql.SQLException;

public class KDTransact implements AutoCloseable { // KDTransact kdTran= new KDTransact(conn)
	private final Connection conn;
	private final boolean isAutoCommit;
	
	public KDTransact(Connection _conn) throws SQLException {
		conn= _conn;
		isAutoCommit= conn.getAutoCommit();
		conn.setAutoCommit(false);
	}

	@Override
	public void close() throws SQLException {
		try {
			conn.rollback();
		} finally {
			conn.setAutoCommit(isAutoCommit);
		}
	}
}
