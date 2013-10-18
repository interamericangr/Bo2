/*******************************************************************************
 * Copyright (c) 2013 INTERAMERICAN PROPERTY AND CASUALTY INSURANCE COMPANY S.A. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/lesser.html
 * 
 * This library is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 ******************************************************************************/
package gr.interamerican.bo2.samples.sql;

import gr.interamerican.bo2.utils.DateUtils;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
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
import java.util.Calendar;
import java.util.Map;

/**
 * Mock {@link ResultSet}.
 */
@SuppressWarnings("nls")
public class MockResultSet implements ResultSet{


	public boolean absolute(int row) throws SQLException {		
		return false;
	}

	public void afterLast() throws SQLException {
		/* empty  */
		
	}

	public void beforeFirst() throws SQLException {
		/* empty  */
		
	}

	public void cancelRowUpdates() throws SQLException {
		/* empty  */
		
	}

	public void clearWarnings() throws SQLException {
		/* empty  */
		
	}

	public void close() throws SQLException {
		/* empty  */		
	}

	public void deleteRow() throws SQLException {
		/*empty*/		
	}

	public int findColumn(String columnLabel) throws SQLException {		
		return 0;
	}

	public boolean first() throws SQLException {		
		return false;
	}

	public Array getArray(int columnIndex) throws SQLException {		
		return null;
	}

	public Array getArray(String columnLabel) throws SQLException {		
		return null;
	}

	public InputStream getAsciiStream(int columnIndex) throws SQLException {		
		return null;
	}

	public InputStream getAsciiStream(String columnLabel) throws SQLException {		
		return null;
	}
	
	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
		return new BigDecimal("0");
	}

	public BigDecimal getBigDecimal(String columnLabel) throws SQLException {		
		return new BigDecimal("0");
	}

	public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
		BigDecimal bd = new BigDecimal("0");
		bd.setScale(scale);
		return bd;
	}

	public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
		BigDecimal bd = new BigDecimal("0");
		bd.setScale(scale);
		return bd;
	}

	public InputStream getBinaryStream(int columnIndex) throws SQLException {		
		return null;
	}

	public InputStream getBinaryStream(String columnLabel) throws SQLException {	
		return null;
	}

	public Blob getBlob(int columnIndex) throws SQLException {		
		return null;
	}

	public Blob getBlob(String columnLabel) throws SQLException {		
		return null;
	}

	public boolean getBoolean(int columnIndex) throws SQLException {		
		return false;
	}

	public boolean getBoolean(String columnLabel) throws SQLException {		
		return false;
	}

	public byte getByte(int columnIndex) throws SQLException {		
		return 0;
	}

	public byte getByte(String columnLabel) throws SQLException {		
		return 0;
	}

	public byte[] getBytes(int columnIndex) throws SQLException {		
		byte[] array = new byte [10];
		return array;
	}

	public byte[] getBytes(String columnLabel) throws SQLException {		
		byte[] array = new byte [10];
		return array;
	}

	public Reader getCharacterStream(int columnIndex) throws SQLException {		
		return null;
	}

	public Reader getCharacterStream(String columnLabel) throws SQLException {		
		return null;
	}

	public Clob getClob(int columnIndex) throws SQLException {		
		return null;
	}

	public Clob getClob(String columnLabel) throws SQLException {		
		return null;
	}

	public int getConcurrency() throws SQLException {		
		return 0;
	}

	public String getCursorName() throws SQLException {
		return null;
	}

	public Date getDate(int columnIndex) throws SQLException {
		return getDate("");
	}

	public Date getDate(String columnLabel) throws SQLException {
		Date dt = new Date(System.currentTimeMillis());
		DateUtils.removeTime(dt);
		return dt;
	}

	public Date getDate(int columnIndex, Calendar cal) throws SQLException {		
		return getDate(columnIndex);
	}

	public Date getDate(String columnLabel, Calendar cal) throws SQLException {		
		return getDate(columnLabel);
	}

	public double getDouble(int columnIndex) throws SQLException {
		return 0;
	}

	public double getDouble(String columnLabel) throws SQLException {
		return 0;
	}

	public int getFetchDirection() throws SQLException {
		
		return 0;
	}

	public int getFetchSize() throws SQLException {
		return 0;
	}

	public float getFloat(int columnIndex) throws SQLException {
		return 0;
	}

	public float getFloat(String columnLabel) throws SQLException {
		return 0;
	}

	public int getInt(int columnIndex) throws SQLException {
		return 0;
	}

	public int getInt(String columnLabel) throws SQLException {
		return 0;
	}

	public long getLong(int columnIndex) throws SQLException {
		return 0;
	}

	public long getLong(String columnLabel) throws SQLException {
		return 0;
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		
		return null;
	}

	public Object getObject(int columnIndex) throws SQLException {		
	    String value = "value"; //$NON-NLS-1$
		return value;
	}

	public Object getObject(String columnLabel) throws SQLException {		
	    String value = "value"; //$NON-NLS-1$
		return value;
	}

	public Object getObject(int columnIndex, Map<String, Class<?>> map)	
	throws SQLException {
		return null;
	}

	public Object getObject(String columnLabel, Map<String, Class<?>> map)
	throws SQLException {	
		return null;
	}

	public Ref getRef(int columnIndex) throws SQLException {
		return null;
	}

	public Ref getRef(String columnLabel) throws SQLException {
		return null;
	}

	public int getRow() throws SQLException {
		
		return 0;
	}

	public short getShort(int columnIndex) throws SQLException {		
		return 0;
	}

	public short getShort(String columnLabel) throws SQLException {		
		return 0;
	}

	public Statement getStatement() throws SQLException {		
		return null;
	}

	public String getString(int columnIndex) throws SQLException {
		return "value"; 
	}

	public String getString(String columnLabel) throws SQLException {
		return "value"; 
	}

	public Time getTime(int columnIndex) throws SQLException {
		return null;
	}

	public Time getTime(String columnLabel) throws SQLException {
		return null;
	}

	public Time getTime(int columnIndex, Calendar cal) throws SQLException {
		return null;
	}

	public Time getTime(String columnLabel, Calendar cal) throws SQLException {
		return null;
	}

	public Timestamp getTimestamp(int columnIndex) throws SQLException {
		Timestamp timestamp = new Timestamp(2011);
		return timestamp;
	}

	public Timestamp getTimestamp(String columnLabel) throws SQLException {
		Timestamp timestamp = new Timestamp(2011);
		return timestamp;
	}

	public Timestamp getTimestamp(int columnIndex, Calendar cal)
	throws SQLException {
		return null;
	}

	public Timestamp getTimestamp(String columnLabel, Calendar cal)
	throws SQLException {
		return null;
	}

	public int getType() throws SQLException {		
		return 0;
	}

	public URL getURL(int columnIndex) throws SQLException {
		
		return null;
	}

	public URL getURL(String columnLabel) throws SQLException {
		return null;
	}

	public SQLWarning getWarnings() throws SQLException {
		return null;
	}

	public void insertRow() throws SQLException {
		/*empty*/
		
	}

	public boolean isAfterLast() throws SQLException {
		return false;
	}

	public boolean isBeforeFirst() throws SQLException {
		return false;
	}

	public boolean isFirst() throws SQLException {
		return false;
	}

	public boolean isLast() throws SQLException {
		return false;
	}

	public boolean last() throws SQLException {
		return false;
	}

	public void moveToCurrentRow() throws SQLException {
		/* empty */		
	}

	public void moveToInsertRow() throws SQLException {
		/* empty */		
	}

	public boolean next() throws SQLException {		
		return false;
	}

	public boolean previous() throws SQLException {
		return false;
	}

	public void refreshRow() throws SQLException {
		/* empty */	
		
	}

	public boolean relative(int rows) throws SQLException {
		return false;
	}

	public boolean rowDeleted() throws SQLException {
		return false;
	}

	public boolean rowInserted() throws SQLException {
		return false;
	}

	public boolean rowUpdated() throws SQLException {
		return false;
	}

	public void setFetchDirection(int direction) throws SQLException {
		/* empty */			
	}

	public void setFetchSize(int rows) throws SQLException {
		/* empty */	
		
	}

	public void updateArray(int columnIndex, Array x) throws SQLException {
		/* empty */	
		
	}

	public void updateArray(String columnLabel, Array x) throws SQLException {
		/* empty */	
		
	}

	public void updateAsciiStream(int columnIndex, InputStream x, int length)
			throws SQLException {
		/* empty */	
		
	}

	public void updateAsciiStream(String columnLabel, InputStream x, int length)
			throws SQLException {
		/*empty*/
		
	}

	public void updateBigDecimal(int columnIndex, BigDecimal x)
			throws SQLException {
		/*empty*/
		
	}

	public void updateBigDecimal(String columnLabel, BigDecimal x)
			throws SQLException {
		/*empty*/
		
	}

	public void updateBinaryStream(int columnIndex, InputStream x, int length)
			throws SQLException {
		/*empty*/
		
	}

	public void updateBinaryStream(String columnLabel, InputStream x, int length)
			throws SQLException {
		/*empty*/
		
	}

	public void updateBlob(String columnLabel, Blob x) throws SQLException {
		/*empty*/
		
	}

	public void updateBlob(int columnIndex, InputStream inputStream)
			throws SQLException {
		/*empty*/
		
	}

	public void updateBlob(String columnLabel, InputStream inputStream)
			throws SQLException {
		/*empty*/
		
	}

	public void updateBlob(int columnIndex, InputStream inputStream, long length)
			throws SQLException {
		/*empty*/
		
	}

	public void updateBlob(String columnLabel, InputStream inputStream,
			long length) throws SQLException {
		/*empty*/
		
	}

	public void updateBoolean(int columnIndex, boolean x) throws SQLException {
		/*empty*/
		
	}

	public void updateBoolean(String columnLabel, boolean x)
			throws SQLException {
		/*empty*/
		
	}

	public void updateByte(int columnIndex, byte x) throws SQLException {
		/*empty*/
		
	}

	public void updateByte(String columnLabel, byte x) throws SQLException {
		/*empty*/
		
	}

	public void updateBytes(int columnIndex, byte[] x) throws SQLException {
		/*empty*/
		
	}

	public void updateBytes(String columnLabel, byte[] x) throws SQLException {
		/*empty*/
		
	}

	public void updateCharacterStream(int columnIndex, Reader x, int length)
			throws SQLException {
		/*empty*/
		
	}

	public void updateCharacterStream(String columnLabel, Reader reader,
			int length) throws SQLException {
		/*empty*/
		
	}

	public void updateClob(int columnIndex, Clob x) throws SQLException {
		/*empty*/
		
	}

	public void updateClob(String columnLabel, Clob x) throws SQLException {
		/*empty*/
		
	}

	public void updateDate(int columnIndex, Date x) throws SQLException {
		/*empty*/
		
	}

	public void updateDate(String columnLabel, Date x) throws SQLException {
		/*empty*/
		
	}

	public void updateDouble(int columnIndex, double x) throws SQLException {
		/*empty*/
		
	}

	public void updateDouble(String columnLabel, double x) throws SQLException {
		/*empty*/
		
	}

	public void updateFloat(int columnIndex, float x) throws SQLException {
		/*empty*/
		
	}

	public void updateFloat(String columnLabel, float x) throws SQLException {
		/*empty*/
		
	}

	public void updateInt(int columnIndex, int x) throws SQLException {
		/*empty*/
		
	}

	public void updateInt(String columnLabel, int x) throws SQLException {
		/*empty*/
		
	}

	public void updateLong(int columnIndex, long x) throws SQLException {
		/*empty*/
		
	}

	public void updateLong(String columnLabel, long x) throws SQLException {
		/*empty*/
		
	}


	public void updateNull(int columnIndex) throws SQLException {
		/*empty*/
		
	}

	public void updateNull(String columnLabel) throws SQLException {
		/*empty*/
		
	}

	public void updateObject(int columnIndex, Object x) throws SQLException {
		/*empty*/
		
	}

	public void updateObject(String columnLabel, Object x) throws SQLException {
		/*empty*/
		
	}

	public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
		/*empty*/
		
	}

	public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
		/*empty*/		
	}

	public void updateRef(int columnIndex, Ref x) throws SQLException {
		/*empty*/
		
	}

	public void updateRef(String columnLabel, Ref x) throws SQLException {
		/*empty*/
		
	}

	public void updateRow() throws SQLException {
		/*empty*/
		
	}

	public void updateShort(int columnIndex, short x) throws SQLException {
		/*empty*/
		
	}

	public void updateShort(String columnLabel, short x) throws SQLException {
		/*empty*/
		
	}

	public void updateString(int columnIndex, String x) throws SQLException {
		/*empty*/
		
	}

	public void updateString(String columnLabel, String x) throws SQLException {
		/*empty*/
		
	}

	public void updateTime(int columnIndex, Time x) throws SQLException {
		/*empty*/
		
	}

	public void updateTime(String columnLabel, Time x) throws SQLException {
		/*empty*/		
	}

	public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
		/*empty*/		
	}

	public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
		/*empty*/	
	}

	public boolean wasNull() throws SQLException {		
		return false;
	}

	public InputStream getUnicodeStream(int columnIndex) throws SQLException {
		return null;
	}	
	
	public InputStream getUnicodeStream(String columnName) throws SQLException {
		return null;
	}

	
	public void updateBlob(int columnIndex, Blob x) throws SQLException {
		// empty
		
	}

	/* (non-Javadoc)
	 * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
	 */
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	/* (non-Javadoc)
	 * @see java.sql.Wrapper#unwrap(java.lang.Class)
	 */
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}

	/* (non-Javadoc)
	 * @see java.sql.ResultSet#getHoldability()
	 */
	public int getHoldability() throws SQLException {
		
		return 0;
	}

	/* (non-Javadoc)
	 * @see java.sql.ResultSet#getNCharacterStream(int)
	 */
	public Reader getNCharacterStream(int arg0) throws SQLException {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see java.sql.ResultSet#getNCharacterStream(java.lang.String)
	 */
	public Reader getNCharacterStream(String arg0) throws SQLException {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see java.sql.ResultSet#getNClob(int)
	 */
	public NClob getNClob(int arg0) throws SQLException {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see java.sql.ResultSet#getNClob(java.lang.String)
	 */
	public NClob getNClob(String arg0) throws SQLException {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see java.sql.ResultSet#getNString(int)
	 */
	public String getNString(int arg0) throws SQLException {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see java.sql.ResultSet#getNString(java.lang.String)
	 */
	public String getNString(String arg0) throws SQLException {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see java.sql.ResultSet#getRowId(int)
	 */
	public RowId getRowId(int arg0) throws SQLException {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see java.sql.ResultSet#getRowId(java.lang.String)
	 */
	public RowId getRowId(String arg0) throws SQLException {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see java.sql.ResultSet#getSQLXML(int)
	 */
	public SQLXML getSQLXML(int arg0) throws SQLException {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see java.sql.ResultSet#getSQLXML(java.lang.String)
	 */
	public SQLXML getSQLXML(String arg0) throws SQLException {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see java.sql.ResultSet#isClosed()
	 */
	public boolean isClosed() throws SQLException {
		
		return false;
	}

	/* (non-Javadoc)
	 * @see java.sql.ResultSet#updateAsciiStream(int, java.io.InputStream)
	 */
	public void updateAsciiStream(int arg0, InputStream arg1) throws SQLException {
		/*empty*/
		
	}

	public void updateAsciiStream(String arg0, InputStream arg1) throws SQLException {
		/*empty*/
		
	}

	public void updateAsciiStream(int arg0, InputStream arg1, long arg2) throws SQLException {
		
		// empty
	}

	public void updateAsciiStream(String arg0, InputStream arg1, long arg2) throws SQLException {
		/*empty*/
		
	}

	public void updateBinaryStream(int arg0, InputStream arg1) throws SQLException {
		/*empty*/
		
	}

	public void updateBinaryStream(String arg0, InputStream arg1) throws SQLException {
		/*empty*/
		
	}

	public void updateBinaryStream(int arg0, InputStream arg1, long arg2) throws SQLException {
		/*empty*/
		
	}

	public void updateBinaryStream(String arg0, InputStream arg1, long arg2) throws SQLException {
		/*empty*/
		
	}

	public void updateCharacterStream(int arg0, Reader arg1) throws SQLException {
		/*empty*/
		
	}

	public void updateCharacterStream(String arg0, Reader arg1) throws SQLException {
		/* empty */
		
	}

	public void updateCharacterStream(int arg0, Reader arg1, long arg2) throws SQLException {
		/* empty */
		
	}

	public void updateCharacterStream(String arg0, Reader arg1, long arg2) throws SQLException {
		/*empty*/
		
	}

	public void updateClob(int arg0, Reader arg1) throws SQLException {
		/*empty*/
		
	}

	public void updateClob(String arg0, Reader arg1) throws SQLException {
		/*empty*/
		
	}

	public void updateClob(int arg0, Reader arg1, long arg2) throws SQLException {
		/*empty*/
		
	}

	public void updateClob(String arg0, Reader arg1, long arg2) throws SQLException {
		/*empty*/
		
	}

	public void updateNCharacterStream(int arg0, Reader arg1) throws SQLException {
		/*empty*/
		
	}

	public void updateNCharacterStream(String arg0, Reader arg1) throws SQLException {
		/*empty*/
		
	}

	public void updateNCharacterStream(int arg0, Reader arg1, long arg2) throws SQLException {
		/*empty*/
		
	}

	public void updateNCharacterStream(String arg0, Reader arg1, long arg2) throws SQLException {
		/*empty*/
		
	}

	public void updateNClob(int arg0, NClob arg1) throws SQLException {
		/*empty*/
		
	}

	public void updateNClob(String arg0, NClob arg1) throws SQLException {
		/* empty */
	}

	public void updateNClob(int arg0, Reader arg1) throws SQLException {
		/*empty*/
		
	}

	public void updateNClob(String arg0, Reader arg1) throws SQLException {
		/* empty */
	}

	public void updateNClob(int arg0, Reader arg1, long arg2) throws SQLException {
		/*empty*/
		
	}

	public void updateNClob(String arg0, Reader arg1, long arg2) throws SQLException {
		/*empty*/
		
	}

	public void updateNString(int arg0, String arg1) throws SQLException {
		/*empty*/
		
	}

	public void updateNString(String arg0, String arg1) throws SQLException {
		/* empty */
	}

	public void updateRowId(int arg0, RowId arg1) throws SQLException {
		/*empty*/
	}

	public void updateRowId(String arg0, RowId arg1) throws SQLException {
		/*empty*/
	}

	public void updateSQLXML(int arg0, SQLXML arg1) throws SQLException {
		/*empty*/
		
	}

	public void updateSQLXML(String arg0, SQLXML arg1) throws SQLException {
		/*empty*/
	}

	

}
