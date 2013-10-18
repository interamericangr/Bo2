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
package gr.interamerican.bo2.impl.open.jdbc;

import gr.interamerican.bo2.arch.Query;
import gr.interamerican.bo2.arch.def.NamedFieldsContainer;
import gr.interamerican.bo2.arch.def.OrderedFieldsContainer;
import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.utils.StreamUtils;
import gr.interamerican.bo2.utils.sql.SqlProcessor;
import gr.interamerican.bo2.utils.sql.SqlUtils;
import gr.interamerican.bo2.utils.sql.types.Type;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * Abstract implementation of {@link Query} based on JDBC.
 *
 */
public abstract class JdbcQuery 
extends AbstractJdbcWorker
implements Query, NamedFieldsContainer, OrderedFieldsContainer {
	
	/**
	 * Postfix for select with UR
	 */
	static final String WITH_UR = " with UR "; //$NON-NLS-1$
	
	/**
	 * Message key for Blob too long
	 */
	private static final String BLOB_TO_LONG = "JdbcQuery.BLOB_TO_LONG"; //$NON-NLS-1$
	
	/**
	 * ResultSet of the query
	 */
	protected ResultSet rs;
	
	/**
	 * If true sets the query to be executed with isolation level
	 * uncommitted read
	 */
	boolean withUR;	
	
	/**
	 * SQL statement of the query.
	 * 
	 * @return Returns the SQL string for the query.
	 */	
	protected abstract String sql();
	
	/**
	 * Utility method meant to get an SQL statement from a resource
	 * file. This is meant for use in the implementation of {@link #sql()}
	 * 
	 * @param path
	 *        Resource path.
	 *        
	 * @return SQL statement.
	 */
	protected String getSqlFromResourceFile(String path) {
		String sqlFromFile = StreamUtils.getStringFromResourceFile(path); 
		return SqlProcessor.normalizeSql(sqlFromFile);
	}
	
	/**
	 * Parameters for the query.
	 * <br/>
	 * If the query does not need parameters,
	 * this method should return null or an empty array.
	 * <br/>
	 * The default implementation fetches an array with the named parameters.
	 * 
	 * @return Returns an array with parameters for the query.
	 */
	protected Object[] parameters() {
		return getParamsFromNamedParams();
	}
	
	@Override
	protected String[] getParameterNamesArray() {
		String[] names = super.getParameterNamesArray();
		if (names!=null) {
			return names;
		}
		
		String stmt = sql();
		List<String> paramNames = SqlUtils.getParameterNames(stmt);
		if (paramNames.isEmpty()) {
			return null;
		}
		return paramNames.toArray(new String[0]);
	}
	
	/**
	 * Sets the cursor isolation level to a select statement.
	 *  
	 * @param stmt
	 *        SQL query statement.
	 *        
	 * @return If <code>avoidLock</code> is true, adds with UR to the statement.
	 *         Otherwise returns the statement as is. 
	 *         
	 */
	final String cil(String stmt) {
		if (withUR) {
			return stmt + WITH_UR;
		}
		return stmt;
	}
	
	@Override
	public final void execute() throws DataException {
		validateOpen();
		try {
			String stmt = cil(sql());
			PreparedStatement ps = getPreparedStatement(stmt);
			Object[] params = parameters();
			if (params!=null) {
				setParameters(ps, params);
			}				
			rs = executeQueryPs(ps);
		} catch (SQLException e) {
			throw new DataException(e);
		}
	}
	
	@Override
	public void open() throws DataException {		
		super.open();		
	}
	
	@Override
	public int getRow() throws DataAccessException {
	    try {
	        return rs.getRow();
	    } catch (SQLException e) {
	        throw new DataAccessException(e);
	    }
	}

	@Override
	public boolean next() throws DataAccessException {
		try{
			return rs.next();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}
	
	@Override
	public BigDecimal getBigDecimal(String field) throws DataAccessException {
		try {
			return rs.getBigDecimal(field);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public boolean getBoolean(String field) throws DataAccessException {
		try {
			return rs.getBoolean(field);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public byte getByte(String field) throws DataAccessException {
		try {
			return rs.getByte(field);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public byte[] getBytes(String field) throws DataAccessException {
		try {
			Blob blob = rs.getBlob(field);
			Long length = blob.length();
			if (length > Integer.MAX_VALUE) {
				throw new DataAccessException(BLOB_TO_LONG);
			}
			int len =length.intValue();
			return blob.getBytes(1, len);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public Calendar getCalendar(String field) throws DataAccessException {
		try {
			Timestamp ts = rs.getTimestamp(field);
			Calendar cal = new GregorianCalendar();
			cal.setTime(ts);
			return cal;
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public Date getDate(String field) throws DataAccessException {
		try {
			return rs.getDate(field);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public double getDouble(String field) throws DataAccessException {
		try {
			return rs.getDouble(field);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public float getFloat(String field) throws DataAccessException {
		try {
			return rs.getFloat(field);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public int getInt(String field) throws DataAccessException {
		try {
			return rs.getInt(field);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public long getLong(String field) throws DataAccessException {
		try {
			return rs.getLong(field);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}
	
	@Override
	public Object getObject(String field) throws DataAccessException {
		try {
			return rs.getObject(field);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public short getShort(String field) throws DataAccessException {
		try {
			return rs.getShort(field);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public String getString(String field) throws DataAccessException {
		try {
			return rs.getString(field);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public void setAvoidLock(boolean avoidLock) {
		withUR = avoidLock;		
	}

	@Override
	public boolean isAvoidLock() {		
		return withUR;
	}
	
	/**
	 * Gets the value of the specified column.
	 * 
	 * @param field
	 *        Column name.
	 * @param type
	 *        SQL type of column.
	 * @param <T>
	 *        Java type of column.
	 * 
	 * @return Returns the value of the specified column.
	 * 
	 * @throws DataAccessException
	 */
	public <T> T get(String field, Type<T> type) throws DataAccessException {
		try {
			return type.get(rs, field);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}
	
    
	@Override
	public String getString(int field) throws DataAccessException {
    	try {
			return rs.getString(field);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
    }
    
	@Override
	public BigDecimal getBigDecimal(int field) throws DataAccessException {
    	try {
			return rs.getBigDecimal(field);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
    }
   
	@Override
	public double getDouble(int field) throws DataAccessException {
    	try {
			return rs.getDouble(field);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
    }
 
    
	@Override
	public float getFloat(int field) throws DataAccessException {
		try {
			return rs.getFloat(field);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public int getInt(int field) throws DataAccessException {
		try {
			return rs.getInt(field);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public long getLong(int field) throws DataAccessException {
		try {
			return rs.getLong(field);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}
    
	@Override
	public short getShort(int field) throws DataAccessException {
		try {
			return rs.getShort(field);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public boolean getBoolean(int field) throws DataAccessException {
		try {
			return rs.getBoolean(field);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}
  
	@Override
	public byte getByte(int field) throws DataAccessException {
		try {
			return rs.getByte(field);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}
	
	@Override
	public byte[] getBytes(int field) throws DataAccessException {
		try {
			return rs.getBytes(field);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}	
   
	@Override
	public Date getDate(int field) throws DataAccessException {
    	try {
			return rs.getDate(field);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
    }
    
	@Override
	public Calendar getCalendar(int field) throws DataAccessException {
    	try {
			Timestamp ts = rs.getTimestamp(field);
			Calendar cal = new GregorianCalendar();
			cal.setTime(ts);
			return cal;
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
    }
  
	@Override
	public Object getObject(int field) throws DataAccessException {
		try {
			return rs.getObject(field);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}
	
	/**
	 * @return Returns the (ordered) column names of the ResultSet.
	 * @throws DataAccessException
	 * @throws SQLException
	 */
	public String[] getColumnNames() throws DataAccessException, SQLException {
		if(rs==null || rs.isClosed()) {
			throw new DataAccessException("No active resultset."); //$NON-NLS-1$
		}
		
		String[] columnNames = new String[rs.getMetaData().getColumnCount()];
		for(int i=1; i<= rs.getMetaData().getColumnCount(); i++) {
			columnNames[i-1] = rs.getMetaData().getColumnName(i);
		}
		return columnNames;
	}
	
}
