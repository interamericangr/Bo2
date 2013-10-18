package gr.interamerican.bo2.legacy;

import gr.interamerican.bo2.arch.Query;
import gr.interamerican.bo2.arch.def.NamedFieldsContainer;
import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.utils.DateUtils;
import interamerican.archimpl.workers.DataQuery;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * Adapts a legacy Bo query to Bo2.
 */
public class LegacyQueryAdapter 
extends LegacyWorkerAdapter 
implements Query, NamedFieldsContainer {
	
	/**
	 * Legacy object.
	 */
	private interamerican.architecture.Query query;

	/**
	 * Creates a new LegacyQueryAdapter object. 
	 *
	 * @param query
	 */
	public LegacyQueryAdapter(interamerican.architecture.Query query) {
		super(query);		
		this.query = query;
	}

	public boolean next() throws DataAccessException {
		try {
			return query.next();
		} catch (interamerican.architecture.exceptions.DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public void execute() throws DataException {
		try {
			query.execute();
		} catch (interamerican.architecture.exceptions.DataException e) {
			throw new DataException(e);
		}		
	}

	public int getRow() throws DataAccessException {
		try {
			return query.getRow();
		} catch (interamerican.architecture.exceptions.DataAccessException e) {
			throw new DataAccessException(e);
		}
	}	

	public boolean isAvoidLock() {
		if (query instanceof DataQuery) {
			DataQuery dq = (DataQuery) query;
			return dq.isWithUR();
		} else {
			return true;
		}
	}

	public void setAvoidLock(boolean avoidLock) {
		if (query instanceof DataQuery) {
			DataQuery dq = (DataQuery) query;
			dq.setWithUR(avoidLock);
		} else {
			/* do nothing */
		}
	}
	
	public String getString(String field) throws DataAccessException {
		try {
			return query.getString(field);
		} catch (interamerican.architecture.exceptions.DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public BigDecimal getBigDecimal(String field) throws DataAccessException {
		try {
			return query.getBigDecimal(field);
		} catch (interamerican.architecture.exceptions.DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public double getDouble(String field) throws DataAccessException {
		try {
			return query.getDouble(field);
		} catch (interamerican.architecture.exceptions.DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public float getFloat(String field) throws DataAccessException {
		try {
			return query.getFloat(field);
		} catch (interamerican.architecture.exceptions.DataAccessException e) {
			throw new DataAccessException(e);
		}

	}
	
	public int getInt(String field) throws DataAccessException {
		try {
			return query.getInt(field);
		} catch (interamerican.architecture.exceptions.DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public long getLong(String field) throws DataAccessException {
		try {
			return query.getLong(field);
		} catch (interamerican.architecture.exceptions.DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public short getShort(String field) throws DataAccessException {
		try {
			return query.getShort(field);
		} catch (interamerican.architecture.exceptions.DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public boolean getBoolean(String field) throws DataAccessException {
		try {
			return query.getBoolean(field);
		} catch (interamerican.architecture.exceptions.DataAccessException e) {
			throw new DataAccessException(e);
		}

	}

	public byte getByte(String field) throws DataAccessException {
		try {
			return query.getByte(field);
		} catch (interamerican.architecture.exceptions.DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public byte[] getBytes(String field) throws DataAccessException {
		try {
			return query.getBytes(field);
		} catch (interamerican.architecture.exceptions.DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public Date getDate(String field) throws DataAccessException {
		try {
			return query.getDate(field);
		} catch (interamerican.architecture.exceptions.DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public Calendar getCalendar(String field) throws DataAccessException {
		try {
			Date dt = query.getDate(field);
			return DateUtils.getCalendar(dt);
		} catch (interamerican.architecture.exceptions.DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public Object getObject(String field) throws DataAccessException {
		try {
			return query.getObject(field);
		} catch (interamerican.architecture.exceptions.DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}
