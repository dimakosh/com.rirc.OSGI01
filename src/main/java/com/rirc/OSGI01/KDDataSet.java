package com.rirc.OSGI01;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class KDDataSet implements KDResultSetable {
	
	private Map<String,Integer> fldInd= new HashMap<String,Integer>();
	private int curInd;
	private Integer compInd(String fld) {
		return fldInd.computeIfAbsent(fld.toUpperCase(), (k)-> curInd++);
	}
	
	public class Row {
		private Map<Integer,Object> r= new HashMap<Integer,Object>();
		
		public Row add(String fld, String val) {
			r.put(compInd(fld), val);
			return this;
		}
		public Row add(String fld, int val) {
			r.put(compInd(fld), Double.valueOf(val));
			return this;
		}
		public Row add(String fld, Integer val) {
			r.put(compInd(fld), (val==null)? null:Double.valueOf(val));
			return this;
		}
		public Row add(String fld, double val) {
			r.put(compInd(fld), Double.valueOf(val));
			return this;
		}
		public Row add(String fld, Double val) {
			r.put(compInd(fld), val);
			return this;
		}
		public Row add(String fld, java.util.Date val) {
			r.put(compInd(fld), KDTime.sqlDate(val));
			return this;
		}

		public Row addObject(String fld, Object val) {
			if (val!=null && val instanceof Number && !(val instanceof Double)) val= ((Number)val).doubleValue();
			r.put(compInd(fld), val);
			return this;
		}
		
		public String getString(int ind) {
			return (String)r.get(ind);
		}
		public Double getDouble(int ind) {
			Object val= r.get(ind);
			return (Double)val;
		}
		public java.sql.Date getDate(int ind) {
			return (java.sql.Date)r.get(ind);
		}
		
		public int getInd(String fld) {
			Integer ind= fldInd.get(fld.toUpperCase());
			return (ind==null)? -1:ind;
		}
	}
	
	private List<Row> rows= new ArrayList<Row>();
	
	public Row add() {
		Row r= new Row();
		rows.add(r);
		return r;
	}

	public Row add(Iterable<Entry<String, Object>> fd) {
		Row r= new Row();
		for (Entry<String, Object> d : fd) r.addObject(d.getKey(), d.getValue());
		rows.add(r);
		return r;
	}
	
	public int size() {
		return rows.size();
	}
	
	public Iterable<Row> getIterable() {
		return ()->rows.iterator(); 
	}

	@Override
	public PreparedStatement getFillPreparedStatement(Connection conn) throws SQLException {
		throw new UnsupportedOperationException("getFillPreparedStatement");
	}
	
	@Override
	public ResultSet getResultSet(Connection ___conn___) throws SQLException {
		return new ResultSet() {
			
			int ind= -1;
			boolean isNull;

			@Override
			public <T> T unwrap(Class<T> iface) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean isWrapperFor(Class<?> iface) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean next() throws SQLException {
				return ++ind<rows.size();
			}

			@Override
			public void close() throws SQLException {
			}

			@Override
			public boolean wasNull() throws SQLException {
				return isNull;
			}

			@Override
			public String getString(int columnIndex) throws SQLException {
				Row r= rows.get(ind);
				String val= r.getString(columnIndex);
				isNull= val==null;
				return val;
			}

			@Override
			public boolean getBoolean(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public byte getByte(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public short getShort(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getInt(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public long getLong(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public float getFloat(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public double getDouble(int columnIndex) throws SQLException {
				Row r= rows.get(ind);
				Double val= r.getDouble(columnIndex);
				if (val==null) {
					isNull= true;
					return 0;
				} else {
					isNull= false;
					return val;
				}
			}

			@Override
			public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public byte[] getBytes(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public java.sql.Date getDate(int columnIndex) throws SQLException {
				Row r= rows.get(ind);
				java.sql.Date val= r.getDate(columnIndex);
				isNull= val==null;
				return val;
			}

			@Override
			public Time getTime(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Timestamp getTimestamp(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public InputStream getAsciiStream(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public InputStream getUnicodeStream(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public InputStream getBinaryStream(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getString(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean getBoolean(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public byte getByte(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public short getShort(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getInt(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public long getLong(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public float getFloat(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public double getDouble(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public byte[] getBytes(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public java.sql.Date getDate(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Time getTime(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Timestamp getTimestamp(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public InputStream getAsciiStream(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public InputStream getUnicodeStream(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public InputStream getBinaryStream(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public SQLWarning getWarnings() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void clearWarnings() throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public String getCursorName() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public ResultSetMetaData getMetaData() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object getObject(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object getObject(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int findColumn(String columnLabel) throws SQLException {
				Integer ind= fldInd.get(columnLabel.toUpperCase());
				if (ind==null) throw new SQLException("Column error:"+columnLabel);
				return ind;
			}

			@Override
			public Reader getCharacterStream(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Reader getCharacterStream(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean isBeforeFirst() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isAfterLast() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isFirst() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isLast() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void beforeFirst() throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void afterLast() throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean first() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean last() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public int getRow() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public boolean absolute(int row) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean relative(int rows) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean previous() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void setFetchDirection(int direction) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public int getFetchDirection() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public void setFetchSize(int rows) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public int getFetchSize() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getType() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getConcurrency() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public boolean rowUpdated() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean rowInserted() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean rowDeleted() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void updateNull(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateBoolean(int columnIndex, boolean x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateByte(int columnIndex, byte x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateShort(int columnIndex, short x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateInt(int columnIndex, int x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateLong(int columnIndex, long x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateFloat(int columnIndex, float x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateDouble(int columnIndex, double x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateString(int columnIndex, String x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateBytes(int columnIndex, byte[] x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateDate(int columnIndex, java.sql.Date x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateTime(int columnIndex, Time x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateObject(int columnIndex, Object x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateNull(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateBoolean(String columnLabel, boolean x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateByte(String columnLabel, byte x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateShort(String columnLabel, short x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateInt(String columnLabel, int x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateLong(String columnLabel, long x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateFloat(String columnLabel, float x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateDouble(String columnLabel, double x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateString(String columnLabel, String x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateBytes(String columnLabel, byte[] x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateDate(String columnLabel, java.sql.Date x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateTime(String columnLabel, Time x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateObject(String columnLabel, Object x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void insertRow() throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateRow() throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void deleteRow() throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void refreshRow() throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void cancelRowUpdates() throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void moveToInsertRow() throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void moveToCurrentRow() throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public Statement getStatement() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Ref getRef(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Blob getBlob(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Clob getClob(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Array getArray(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Ref getRef(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Blob getBlob(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Clob getClob(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Array getArray(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public java.sql.Date getDate(int columnIndex, Calendar cal) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public java.sql.Date getDate(String columnLabel, Calendar cal) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Time getTime(int columnIndex, Calendar cal) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Time getTime(String columnLabel, Calendar cal) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public URL getURL(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public URL getURL(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void updateRef(int columnIndex, Ref x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateRef(String columnLabel, Ref x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateBlob(int columnIndex, Blob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateBlob(String columnLabel, Blob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateClob(int columnIndex, Clob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateClob(String columnLabel, Clob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateArray(int columnIndex, Array x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateArray(String columnLabel, Array x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public RowId getRowId(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public RowId getRowId(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void updateRowId(int columnIndex, RowId x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateRowId(String columnLabel, RowId x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public int getHoldability() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public boolean isClosed() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void updateNString(int columnIndex, String nString) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateNString(String columnLabel, String nString) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public NClob getNClob(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public NClob getNClob(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public SQLXML getSQLXML(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public SQLXML getSQLXML(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public String getNString(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getNString(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Reader getNCharacterStream(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Reader getNCharacterStream(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateClob(int columnIndex, Reader reader) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateClob(String columnLabel, Reader reader) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateNClob(int columnIndex, Reader reader) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void updateNClob(String columnLabel, Reader reader) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}
}
