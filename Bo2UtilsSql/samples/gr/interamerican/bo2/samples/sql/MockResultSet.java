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

	@Override
	public boolean absolute(int row) throws SQLException {		
		return false;
	}

	@Override
	public void afterLast() throws SQLException {
		/* empty  */
		
	}

	@Override
	public void beforeFirst() throws SQLException {
		/* empty  */
		
	}

	@Override
	public void cancelRowUpdates() throws SQLException {
		/* empty  */
		
	}

	@Override
	public void clearWarnings() throws SQLException {
		/* empty  */
		
	}

	@Override
	public void close() throws SQLException {
		/* empty  */		
	}

	@Override
	public void deleteRow() throws SQLException {
		/*empty*/		
	}

	@Override
	public int findColumn(String columnLabel) throws SQLException {		
		return 0;
	}

	@Override
	public boolean first() throws SQLException {		
		return false;
	}

	@Override
	public Array getArray(int columnIndex) throws SQLException {		
		return null;
	}

	@Override
	public Array getArray(String columnLabel) throws SQLException {		
		return null;
	}

	@Override
	public InputStream getAsciiStream(int columnIndex) throws SQLException {		
		return null;
	}

	@Override
	public InputStream getAsciiStream(String columnLabel) throws SQLException {		
		return null;
	}
	
	@Override
	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
		return new BigDecimal("0");
	}

	@Override
	public BigDecimal getBigDecimal(String columnLabel) throws SQLException {		
		return new BigDecimal("0");
	}

	@Override
	@Deprecated
	public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
		BigDecimal bd = new BigDecimal("0");
		bd.setScale(scale);
		return bd;
	}

	@Override
	@Deprecated
	public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
		BigDecimal bd = new BigDecimal("0");
		bd.setScale(scale);
		return bd;
	}

	@Override
	public InputStream getBinaryStream(int columnIndex) throws SQLException {		
		return null;
	}

	@Override
	public InputStream getBinaryStream(String columnLabel) throws SQLException {	
		return null;
	}

	@Override
	public Blob getBlob(int columnIndex) throws SQLException {		
		return null;
	}

	@Override
	public Blob getBlob(String columnLabel) throws SQLException {		
		return null;
	}

	@Override
	public boolean getBoolean(int columnIndex) throws SQLException {		
		return false;
	}

	@Override
	public boolean getBoolean(String columnLabel) throws SQLException {		
		return false;
	}

	@Override
	public byte getByte(int columnIndex) throws SQLException {		
		return 0;
	}

	@Override
	public byte getByte(String columnLabel) throws SQLException {		
		return 0;
	}

	@Override
	public byte[] getBytes(int columnIndex) throws SQLException {		
		byte[] array = new byte [10];
		return array;
	}

	@Override
	public byte[] getBytes(String columnLabel) throws SQLException {		
		byte[] array = new byte [10];
		return array;
	}

	@Override
	public Reader getCharacterStream(int columnIndex) throws SQLException {		
		return null;
	}

	@Override
	public Reader getCharacterStream(String columnLabel) throws SQLException {		
		return null;
	}

	@Override
	public Clob getClob(int columnIndex) throws SQLException {		
		return null;
	}

	@Override
	public Clob getClob(String columnLabel) throws SQLException {		
		return null;
	}

	@Override
	public int getConcurrency() throws SQLException {		
		return 0;
	}

	@Override
	public String getCursorName() throws SQLException {
		return null;
	}

	@Override
	public Date getDate(int columnIndex) throws SQLException {
		return getDate("");
	}

	@Override
	public Date getDate(String columnLabel) throws SQLException {
		Date dt = new Date(System.currentTimeMillis());
		DateUtils.removeTime(dt);
		return dt;
	}

	@Override
	public Date getDate(int columnIndex, Calendar cal) throws SQLException {		
		return getDate(columnIndex);
	}

	@Override
	public Date getDate(String columnLabel, Calendar cal) throws SQLException {		
		return getDate(columnLabel);
	}

	@Override
	public double getDouble(int columnIndex) throws SQLException {
		return 0;
	}

	@Override
	public double getDouble(String columnLabel) throws SQLException {
		return 0;
	}

	@Override
	public int getFetchDirection() throws SQLException {
		
		return 0;
	}

	@Override
	public int getFetchSize() throws SQLException {
		return 0;
	}

	@Override
	public float getFloat(int columnIndex) throws SQLException {
		return 0;
	}

	@Override
	public float getFloat(String columnLabel) throws SQLException {
		return 0;
	}

	@Override
	public int getInt(int columnIndex) throws SQLException {
		return 0;
	}

	@Override
	public int getInt(String columnLabel) throws SQLException {
		return 0;
	}

	@Override
	public long getLong(int columnIndex) throws SQLException {
		return 0;
	}

	@Override
	public long getLong(String columnLabel) throws SQLException {
		return 0;
	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		
		return null;
	}

	@Override
	public Object getObject(int columnIndex) throws SQLException {		
	    String value = "value"; //$NON-NLS-1$
		return value;
	}

	@Override
	public Object getObject(String columnLabel) throws SQLException {		
	    String value = "value"; //$NON-NLS-1$
		return value;
	}

	@Override
	public Object getObject(int columnIndex, Map<String, Class<?>> map)	
	throws SQLException {
		return null;
	}

	@Override
	public Object getObject(String columnLabel, Map<String, Class<?>> map)
	throws SQLException {	
		return null;
	}

	@Override
	public Ref getRef(int columnIndex) throws SQLException {
		return null;
	}

	@Override
	public Ref getRef(String columnLabel) throws SQLException {
		return null;
	}

	@Override
	public int getRow() throws SQLException {
		
		return 0;
	}

	@Override
	public short getShort(int columnIndex) throws SQLException {		
		return 0;
	}

	@Override
	public short getShort(String columnLabel) throws SQLException {		
		return 0;
	}

	@Override
	public Statement getStatement() throws SQLException {		
		return null;
	}

	@Override
	public String getString(int columnIndex) throws SQLException {
		return "value"; 
	}

	@Override
	public String getString(String columnLabel) throws SQLException {
		return "value"; 
	}

	@Override
	public Time getTime(int columnIndex) throws SQLException {
		return null;
	}

	@Override
	public Time getTime(String columnLabel) throws SQLException {
		return null;
	}

	@Override
	public Time getTime(int columnIndex, Calendar cal) throws SQLException {
		return null;
	}

	@Override
	public Time getTime(String columnLabel, Calendar cal) throws SQLException {
		return null;
	}

	@Override
	public Timestamp getTimestamp(int columnIndex) throws SQLException {
		Timestamp timestamp = new Timestamp(2011);
		return timestamp;
	}

	@Override
	public Timestamp getTimestamp(String columnLabel) throws SQLException {
		Timestamp timestamp = new Timestamp(2011);
		return timestamp;
	}

	@Override
	public Timestamp getTimestamp(int columnIndex, Calendar cal)
	throws SQLException {
		return null;
	}

	@Override
	public Timestamp getTimestamp(String columnLabel, Calendar cal)
	throws SQLException {
		return null;
	}

	@Override
	public int getType() throws SQLException {		
		return 0;
	}

	@Override
	public URL getURL(int columnIndex) throws SQLException {
		
		return null;
	}

	@Override
	public URL getURL(String columnLabel) throws SQLException {
		return null;
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		return null;
	}

	@Override
	public void insertRow() throws SQLException {
		/*empty*/
		
	}

	@Override
	public boolean isAfterLast() throws SQLException {
		return false;
	}

	@Override
	public boolean isBeforeFirst() throws SQLException {
		return false;
	}

	@Override
	public boolean isFirst() throws SQLException {
		return false;
	}

	@Override
	public boolean isLast() throws SQLException {
		return false;
	}

	@Override
	public boolean last() throws SQLException {
		return false;
	}

	@Override
	public void moveToCurrentRow() throws SQLException {
		/* empty */		
	}

	@Override
	public void moveToInsertRow() throws SQLException {
		/* empty */		
	}

	@Override
	public boolean next() throws SQLException {		
		return false;
	}

	@Override
	public boolean previous() throws SQLException {
		return false;
	}

	@Override
	public void refreshRow() throws SQLException {
		/* empty */	
		
	}

	@Override
	public boolean relative(int rows) throws SQLException {
		return false;
	}

	@Override
	public boolean rowDeleted() throws SQLException {
		return false;
	}

	@Override
	public boolean rowInserted() throws SQLException {
		return false;
	}

	@Override
	public boolean rowUpdated() throws SQLException {
		return false;
	}

	@Override
	public void setFetchDirection(int direction) throws SQLException {
		/* empty */			
	}

	@Override
	public void setFetchSize(int rows) throws SQLException {
		/* empty */	
		
	}

	@Override
	public void updateArray(int columnIndex, Array x) throws SQLException {
		/* empty */	
		
	}

	@Override
	public void updateArray(String columnLabel, Array x) throws SQLException {
		/* empty */	
		
	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x, int length)
			throws SQLException {
		/* empty */	
		
	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x, int length)
			throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateBigDecimal(int columnIndex, BigDecimal x)
			throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateBigDecimal(String columnLabel, BigDecimal x)
			throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x, int length)
			throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x, int length)
			throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateBlob(String columnLabel, Blob x) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateBlob(int columnIndex, InputStream inputStream)
			throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateBlob(String columnLabel, InputStream inputStream)
			throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateBlob(int columnIndex, InputStream inputStream, long length)
			throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateBlob(String columnLabel, InputStream inputStream,
			long length) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateBoolean(int columnIndex, boolean x) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateBoolean(String columnLabel, boolean x)
			throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateByte(int columnIndex, byte x) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateByte(String columnLabel, byte x) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateBytes(int columnIndex, byte[] x) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateBytes(String columnLabel, byte[] x) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x, int length)
			throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader,
			int length) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateClob(int columnIndex, Clob x) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateClob(String columnLabel, Clob x) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateDate(int columnIndex, Date x) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateDate(String columnLabel, Date x) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateDouble(int columnIndex, double x) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateDouble(String columnLabel, double x) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateFloat(int columnIndex, float x) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateFloat(String columnLabel, float x) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateInt(int columnIndex, int x) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateInt(String columnLabel, int x) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateLong(int columnIndex, long x) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateLong(String columnLabel, long x) throws SQLException {
		/*empty*/
		
	}


	@Override
	public void updateNull(int columnIndex) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateNull(String columnLabel) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateObject(int columnIndex, Object x) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateObject(String columnLabel, Object x) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
		/*empty*/		
	}

	@Override
	public void updateRef(int columnIndex, Ref x) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateRef(String columnLabel, Ref x) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateRow() throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateShort(int columnIndex, short x) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateShort(String columnLabel, short x) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateString(int columnIndex, String x) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateString(String columnLabel, String x) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateTime(int columnIndex, Time x) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateTime(String columnLabel, Time x) throws SQLException {
		/*empty*/		
	}

	@Override
	public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
		/*empty*/		
	}

	@Override
	public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
		/*empty*/	
	}

	@Override
	public boolean wasNull() throws SQLException {		
		return false;
	}

	@Override
	@Deprecated
	public InputStream getUnicodeStream(int columnIndex) throws SQLException {
		return null;
	}	
	
	@Override
	@Deprecated
	public InputStream getUnicodeStream(String columnName) throws SQLException {
		return null;
	}

	
	@Override
	public void updateBlob(int columnIndex, Blob x) throws SQLException {
		// empty
		
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}

	@Override
	public int getHoldability() throws SQLException {
		
		return 0;
	}

	@Override
	public Reader getNCharacterStream(int arg0) throws SQLException {
		
		return null;
	}

	@Override
	public Reader getNCharacterStream(String arg0) throws SQLException {
		
		return null;
	}

	@Override
	public NClob getNClob(int arg0) throws SQLException {
		
		return null;
	}

	@Override
	public NClob getNClob(String arg0) throws SQLException {
		
		return null;
	}

	@Override
	public String getNString(int arg0) throws SQLException {
		
		return null;
	}

	@Override
	public String getNString(String arg0) throws SQLException {
		
		return null;
	}

	@Override
	public RowId getRowId(int arg0) throws SQLException {
		
		return null;
	}

	@Override
	public RowId getRowId(String arg0) throws SQLException {
		
		return null;
	}

	@Override
	public SQLXML getSQLXML(int arg0) throws SQLException {
		
		return null;
	}

	@Override
	public SQLXML getSQLXML(String arg0) throws SQLException {
		
		return null;
	}

	@Override
	public boolean isClosed() throws SQLException {
		
		return false;
	}

	@Override
	public void updateAsciiStream(int arg0, InputStream arg1) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateAsciiStream(String arg0, InputStream arg1) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateAsciiStream(int arg0, InputStream arg1, long arg2) throws SQLException {
		
		// empty
	}

	@Override
	public void updateAsciiStream(String arg0, InputStream arg1, long arg2) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateBinaryStream(int arg0, InputStream arg1) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateBinaryStream(String arg0, InputStream arg1) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateBinaryStream(int arg0, InputStream arg1, long arg2) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateBinaryStream(String arg0, InputStream arg1, long arg2) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateCharacterStream(int arg0, Reader arg1) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateCharacterStream(String arg0, Reader arg1) throws SQLException {
		/* empty */
		
	}

	@Override
	public void updateCharacterStream(int arg0, Reader arg1, long arg2) throws SQLException {
		/* empty */
		
	}

	@Override
	public void updateCharacterStream(String arg0, Reader arg1, long arg2) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateClob(int arg0, Reader arg1) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateClob(String arg0, Reader arg1) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateClob(int arg0, Reader arg1, long arg2) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateClob(String arg0, Reader arg1, long arg2) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateNCharacterStream(int arg0, Reader arg1) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateNCharacterStream(String arg0, Reader arg1) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateNCharacterStream(int arg0, Reader arg1, long arg2) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateNCharacterStream(String arg0, Reader arg1, long arg2) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateNClob(int arg0, NClob arg1) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateNClob(String arg0, NClob arg1) throws SQLException {
		/* empty */
	}

	@Override
	public void updateNClob(int arg0, Reader arg1) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateNClob(String arg0, Reader arg1) throws SQLException {
		/* empty */
	}

	@Override
	public void updateNClob(int arg0, Reader arg1, long arg2) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateNClob(String arg0, Reader arg1, long arg2) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateNString(int arg0, String arg1) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateNString(String arg0, String arg1) throws SQLException {
		/* empty */
	}

	@Override
	public void updateRowId(int arg0, RowId arg1) throws SQLException {
		/*empty*/
	}

	@Override
	public void updateRowId(String arg0, RowId arg1) throws SQLException {
		/*empty*/
	}

	@Override
	public void updateSQLXML(int arg0, SQLXML arg1) throws SQLException {
		/*empty*/
		
	}

	@Override
	public void updateSQLXML(String arg0, SQLXML arg1) throws SQLException {
		/*empty*/
	}

	@Override
	public <T> T getObject(int arg0, Class<T> arg1) throws SQLException {
		return null;
	}

	@Override
	public <T> T getObject(String arg0, Class<T> arg1) throws SQLException {
		return null;
	}
	

}
