package gr.interamerican.bo2.samples;

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
 * Mock implementation of {@link Query} that throws a {@link DataAccessException}
 * on every data access method.
 * 
 */
public class MockFailingLegacyQuery 
extends MockLegacyWorker 
implements Query {
	
	
	public void execute() throws DataException {		
		/* empty */
	}

	
	public boolean next() throws DataAccessException {		
		return true;
	}

	
	public String getString(String field) throws DataAccessException {		
		throw new DataAccessException();
	}

	
	public double getDouble(String field) throws DataAccessException {
		throw new DataAccessException();
	}

	
	public Date getDate(String field) throws DataAccessException {
		throw new DataAccessException();
	}

	
	public Date getDate(String field, Calendar cal) throws DataAccessException {
		throw new DataAccessException();
	}

	
	public BigDecimal getBigDecimal(String field) throws DataAccessException {
		throw new DataAccessException();
	}

	
	public float getFloat(String field) throws DataAccessException {
		throw new DataAccessException();
	}

	
	public int getInt(String field) throws DataAccessException {
		throw new DataAccessException();
	}

	
	public long getLong(String field) throws DataAccessException {
		throw new DataAccessException();
	}

	
	public Object getObject(String field) throws DataAccessException {
		throw new DataAccessException();
	}

	
	public short getShort(String field) throws DataAccessException {
		throw new DataAccessException();
	}

	
	public Time getTime(String field) throws DataAccessException {
		throw new DataAccessException();
	}

	
	public Time getTime(String field, Calendar cal) throws DataAccessException {
		throw new DataAccessException();
	}

	
	public Timestamp getTimestamp(String field) throws DataAccessException {
		throw new DataAccessException();
	}

	
	public Timestamp getTimestamp(String field, Calendar cal) 
	throws DataAccessException {
		throw new DataAccessException();
	}
	
	public Blob getBlob(String field) throws DataAccessException {
		throw new DataAccessException();
	}

	
	public boolean getBoolean(String field) throws DataAccessException {
		throw new DataAccessException();
	}

	
	public byte getByte(String field) throws DataAccessException {
		throw new DataAccessException();
	}

	
	public byte[] getBytes(String field) throws DataAccessException {
		throw new DataAccessException();
	}

	
	public Reader getCharacterStream(String field) throws DataAccessException {
		throw new DataAccessException();
	}

	
	public Clob getClob(String field) throws DataAccessException {
		throw new DataAccessException();
	}

	public int getRow() throws DataAccessException {
		throw new DataAccessException();
	}

}
