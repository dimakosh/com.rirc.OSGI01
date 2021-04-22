package com.rirc.OSGI01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface KDResultSetable {
	PreparedStatement getFillPreparedStatement(Connection conn) throws SQLException;
	ResultSet getResultSet(Connection conn) throws SQLException;
}
