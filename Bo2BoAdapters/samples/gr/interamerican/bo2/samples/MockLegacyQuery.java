package gr.interamerican.bo2.samples;

import gr.interamerican.bo2.utils.DateUtils;
import interamerican.architecture.Query;
import interamerican.architecture.exceptions.DataAccessException;
import interamerican.architecture.exceptions.DataException;

import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Mock implementation of {@link Query}.
 * 
 * The query imitates a query with four rows.
 */
public class MockLegacyQuery 
extends MockLegacyWorker 
implements Query {
	
	/**
	 * Current row;
	 */
	private int row = 0;
	
	
	public void execute() throws DataException {		
		row = 0;
	}

	
	public boolean next() throws DataAccessException {
		row++;
		return row<4;
	}

	
	public String getString(String field) throws DataAccessException {		
		return field;
	}

	
	public double getDouble(String field) throws DataAccessException {
		return 0;
	}

	
	public Date getDate(String field) throws DataAccessException {
		return new Date(DateUtils.getDate(2010, Calendar.JANUARY, 1).getTime());
	}

	
	public Date getDate(String field, Calendar cal) throws DataAccessException {		
		return getDate(field);
	}

	
	public BigDecimal getBigDecimal(String field) throws DataAccessException {
		return new BigDecimal("0"); //$NON-NLS-1$
	}

	
	public float getFloat(String field) throws DataAccessException {
		return 0;
	}

	
	public int getInt(String field) throws DataAccessException {
		return 0;
	}

	
	public long getLong(String field) throws DataAccessException {
		return 0;
	}

	
	public Object getObject(String field) throws DataAccessException {
		return null;
	}

	
	public short getShort(String field) throws DataAccessException {
		return 0;
	}

	
	public Time getTime(String field) throws DataAccessException {
		return null;
	}

	
	public Time getTime(String field, Calendar cal) throws DataAccessException {
		return null;
	}

	
	public Timestamp getTimestamp(String field) throws DataAccessException {
		return null;
	}

	
	public Timestamp getTimestamp(String field, Calendar cal)
			throws DataAccessException {
		return null;
	}

	
	public Blob getBlob(String field) throws DataAccessException {
		return null;
	}

	
	public boolean getBoolean(String field) throws DataAccessException {
		return false;
	}

	
	public byte getByte(String field) throws DataAccessException {
		return 0;
	}

	
	public byte[] getBytes(String field) throws DataAccessException {
		return null;
	}

	
	public Reader getCharacterStream(String field) throws DataAccessException {
		return null;
	}

	
	public Clob getClob(String field) throws DataAccessException {
		return null;
	}

	public int getRow() throws DataAccessException {
		return 0;
	}

}
