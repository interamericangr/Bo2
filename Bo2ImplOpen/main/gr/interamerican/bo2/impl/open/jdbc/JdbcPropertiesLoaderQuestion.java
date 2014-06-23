package gr.interamerican.bo2.impl.open.jdbc;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Abstract implementation of a Question that loads a Properties instance from
 * a table. Each row is a single property. The first column of the table is the 
 * key and the second the value. A 3rd column may, optionally, indicate if the 
 * value is obfuscated. In this case, the concrete decoding mechanism is left 
 * to the implementor.
 */
public abstract class JdbcPropertiesLoaderQuestion 
extends JdbcSingleStatementQuestion<Properties>{
	
	/**
	 * Answer
	 */
	private Properties answer;
	
	@Override
	public Properties getAnswer() {
		return answer;
	}
		
	@Override
	protected void work() throws DataException, LogicException {
		answer = new Properties();
		String stmt = sql();
		try {			
			Object[] parms = this.getParamsFromNamedParams();
			ResultSet rs = executePreparedQuery(stmt,parms);
			while(rs.next()) {
				String key = rs.getString(1);
				String value = rs.getString(2);
				
				if(isCurrentRowObfuscated(rs)) {
					value = decode(value);
				}
				
				Object prev = answer.setProperty(key, value);
				if(prev!=null) {
					handleDuplicateKey(key);
				}
			}
		} catch (SQLException e) {
			throw new DataException(e);
		}
	}
	
	/**
	 * Handles a duplicate key value. The default implementation throws
	 * a DataException.
	 * 
	 * @param dulpicateKey
	 * @throws DataException
	 */
	protected void handleDuplicateKey(Object dulpicateKey) throws DataException {
		throw new DataException("Duplicate value " + dulpicateKey); //$NON-NLS-1$
	}
	
	/**
	 * Hook for decoding an obfuscated value.
	 * <br/>
	 * Users of this class may override this to provide a desirable implementation.
	 * 
	 * @param obfuscatedValue
	 * @return The decoded value.
	 */
	protected String decode(String obfuscatedValue) {
		return obfuscatedValue;
	}
	
	/**
	 * @param rs
	 * @return Returns true if the current row is obfuscated.
	 */
	boolean isCurrentRowObfuscated(ResultSet rs) {
		try {
			return rs.getBoolean(3);
		} catch (SQLException e) {
			return false;
		}
	}
	
}
