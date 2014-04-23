package gr.interamerican.bo2.impl.open.jdbc;

import gr.interamerican.bo2.arch.Question;
import gr.interamerican.bo2.arch.def.NamedFieldsContainer;
import gr.interamerican.bo2.arch.def.OrderedFieldsContainer;
import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * {@link Question} implementation used in cases where:
 * <li>Only one SQL statement is required.
 * <li>The returned type cannot be retrieved from a single column with the JDBC api
 *     and needs to be constructed by the values taken from one or more columns.
 * <br/><br/>
 * The implementor has to implement {@link #createAnswer()} to construct the answer.
 * <br/>
 * 
 * @param <A>
 *        Answer type 
 */
public abstract class JdbcCustomAnswerSingleStatementQuestion<A> 
extends JdbcSingleStatementQuestion<A> 
implements OrderedFieldsContainer, NamedFieldsContainer {
	
	/**
	 * {@link ResultSet}
	 */
	private ResultSet rs;
	
	/**
	 * Result.
	 */
	private A answer;
	
	/**
	 * Extension point that allows the developer to create the
	 * A instance that will encapsulate the answer and extract
	 * data from the {@link ResultSet} {@link #rs} to populate
	 * it with.
	 * <br/>
	 * This will only execute if the result set has at least one
	 * row. If no rows are returned the answer will be null. It
	 * is the responsibility of the implementor to handle this
	 * (e.g. by overriding {@link #getAnswer()}) or document it.
	 * 
	 * @return the created answer
	 * 
	 * @throws DataAccessException 
	 */
	protected abstract A createAnswer() throws DataAccessException;

	@Override
	protected void work() throws DataException, LogicException {
		try {
			rs = executePreparedQuery(sql(), getParamsFromNamedParams());
			if(rs.next()) {
				answer = createAnswer();
			}
		} catch (SQLException e) {
			throw new DataException(e);
		}
	}
	
	@Override
	public A getAnswer() {
		return answer;
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
				throw new DataAccessException();
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

}
