package com.rirc.OSGI01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DataViewStructure {

	static public enum Orientation {
	    Portrait, Landscape
	}
	
	static public enum FieldType {
		N, C, D, DT, Mn, FromSprav
	}

	static public enum Align {
        Left, Center, Right
    }

	static public class KDCommand implements KDResultSetable {
		final public String sql;
		final public Object[] values;
		
		public KDCommand(String _sql, Object... _values) {
			sql= _sql;
			values= _values;
		}
		
	    @Override
		public PreparedStatement getFillPreparedStatement(Connection conn) throws SQLException {
			PreparedStatement pstmt= conn.prepareStatement(sql);
			
			int n= 0;
			for (Object val : values) {
				if (val!=null && val instanceof LSEnumValue lsEnum) {
					pstmt.setInt(++n, lsEnum.Grp);
					pstmt.setString(++n, lsEnum.NumBeg);
					pstmt.setString(++n, lsEnum.NumEnd);
					pstmt.setInt(++n, lsEnum.HsOrStrByLSBeg);
					pstmt.setInt(++n, lsEnum.HsByLSEnd);
					pstmt.setInt(++n, lsEnum.PlatOnly);
					
					continue;
				}

				if (val!=null && val instanceof java.util.Date dt) {
					pstmt.setDate(++n, KDTime.sqlDate(dt));
				} else {
					pstmt.setObject(++n, val);
				}
			}
			
			return pstmt;
		}
		
	    @Override
		public ResultSet getResultSet(Connection conn) throws SQLException {
			PreparedStatement pstmt= getFillPreparedStatement(conn);
			return pstmt.executeQuery();
		}
		
		static public ResultSet getResultSet(Connection conn, String sql, Object... values) throws SQLException {
			KDCommand dataCmd= new KDCommand(sql, values);
			return dataCmd.getResultSet(conn);
		}

		static public int getInt(Connection conn, String sql, Object... values) throws SQLException {
			KDCommand dataCmd= new KDCommand(sql, values);
			try (ResultSet rs= dataCmd.getResultSet(conn)) {
				if (rs.next()) return rs.getInt(1);
				else return 0;
			}
		}

		static public String getString(Connection conn, String sql, Object... values) throws SQLException {
			KDCommand dataCmd= new KDCommand(sql, values);
			try (ResultSet rs= dataCmd.getResultSet(conn)) {
				if (rs.next()) return rs.getString(1);
				else return null;
			}
		}

		static public int execSQL(Connection conn, String sql) throws SQLException {
			try (Statement stmt= conn.createStatement()) {
				return stmt.executeUpdate(sql);
			}
		}

		static public int execSQL(Connection conn, String sql, Object... values) throws SQLException {
			KDCommand dataCmd= new KDCommand(sql, values);
			try (PreparedStatement pstmt= dataCmd.getFillPreparedStatement(conn)) {
				return pstmt.executeUpdate();
			}
		}
		
		static public int getInt(ConnPrms connPrms, String sql, Object... values) {
			try (Connection conn= KDConnection.get(connPrms)) {
				return getInt(conn, sql, values);
			} catch (SQLException ex) {
				throw new IllegalStateException(ex);
			}
		}		
		
		static public String getString(ConnPrms connPrms, String sql, Object... values) {
			try (Connection conn= KDConnection.get(connPrms)) {
				return getString(conn, sql, values);
			} catch (SQLException ex) {
				throw new IllegalStateException(ex);
			}
		}		
	}

	static public class TextLabel {
        final public String Text;
        final public Align Align;
        final public float size;

        public TextLabel(String _Text, Align _Align) {
            Text = _Text;
            Align = _Align;
            size = 0;
        }

        public TextLabel(String _Text, Align _Align, float _size) {
            Text = _Text;
            Align = _Align;
            size = _size;
        }
    }

    static public class TextLabelCollection extends ArrayList<TextLabel> {
		private static final long serialVersionUID = 1L;

		public void Add(String text) {
            add(new TextLabel(text, Align.Center));
        }
    }

    static public enum DetailFlags {
		Npp, RepSum;
	}

    static public class Detail {
    	final public FieldType Tp;
    	final public String Field;
		//public String SumFld;
    	final public String Title;
    	final public int Width;
    	final public int Dec;
    	final public String From;
    	final public String fKod;
    	final public String fName;
    	final public int ColOrd;
    	final public DetailFlags Df;
        //public String UpTtl;
        //public int UpCols;

		public Detail(FieldType _Tp, String _Field, String _Title, int _Width, int _Dec,
			String _From, String _fKod, String _fName) {
			Tp= _Tp;
			Field= _Field;
			Title= _Title;
			Width= _Width;
			Dec= _Dec;
			From= _From;
			fKod= _fKod;
			fName= _fName;
			ColOrd= -1;
			Df= null;
		}
		public Detail(FieldType _Tp, int _ColOrd, String _Title, int _Width, int _Dec,
			String _From, String _fKod, String _fName) {
			Tp= _Tp;
			Field= null;
			Title= _Title;
			Width= _Width;
			Dec= _Dec;
			From= _From;
			fKod= _fKod;
			fName= _fName;
			ColOrd= _ColOrd;
			Df= null;
		}
		public Detail(FieldType _Tp, String _Field, String _Title, int _Width, int _Dec) {
			Tp= _Tp;
			Field= _Field;
			Title= _Title;
			Width= _Width;
			Dec= _Dec;
			From= null;
			fKod= null;
			fName= null;
			ColOrd= -1;
			Df= null;
		}
		public Detail(FieldType _Tp, int _ColOrd, String _Title, int _Width, int _Dec) {
			Tp= _Tp;
			Field= null;
			Title= _Title;
			Width= _Width;
			Dec= _Dec;
			From= null;
			fKod= null;
			fName= null;
			ColOrd= _ColOrd;
			Df= null;
		}

		public Detail(FieldType _Tp, String _Field, String _Title, int _Width, int _Dec, DetailFlags _Df) {
			Tp= _Tp;
			Field= _Field;
			Title= _Title;
			Width= _Width;
			Dec= _Dec;
			From= null;
			fKod= null;
			fName= null;
			ColOrd= -1;
			Df= _Df;
		}
		public Detail(FieldType _Tp, int _ColOrd, String _Title, int _Width, int _Dec, DetailFlags _Df) {
			Tp= _Tp;
			Field= null;
			Title= _Title;
			Width= _Width;
			Dec= _Dec;
			From= null;
			fKod= null;
			fName= null;
			ColOrd= _ColOrd;
			Df= _Df;
		}
		public Detail(FieldType _Tp, String _Field, String _Title, int _Width) {
			Tp= _Tp;
			Field= _Field;
			Title= _Title;
			Width= _Width;
			Dec= 0;
			From= null;
			fKod= null;
			fName= null;
			ColOrd= -1;
			Df= null;
		}
		public Detail(FieldType _Tp, int _ColOrd, String _Title, int _Width) {
			Tp= _Tp;
			Field= null;
			Title= _Title;
			Width= _Width;
			Dec= 0;
			From= null;
			fKod= null;
			fName= null;
			ColOrd= _ColOrd;
			Df= null;
		}
	}

    static public class DetailCollection extends ArrayList<Detail> {
		private static final long serialVersionUID = 1L;
		
		public int Add(Detail value) {
			add(value);
			return 0;
		}
		
    }
}
