package gr.interamerican.bo2.samples;

import interamerican.archimpl.workers.DataQuery;
import interamerican.architecture.exceptions.DataAccessException;

/**
 * 
 */
public class SampleLegacyBoDataQuery extends DataQuery {

	/**
	 * a simple user select query 
	 */
	private static final String SQL = " select * from X__X.USERS "; //$NON-NLS-1$ 			
	
	/**
	 * the column to extract the result from
	 */
	public static final String USER_NAME_COLUMN="USR_NM"; //$NON-NLS-1$

	@Override
	protected Object[] parameters() {		
		return null;
	}
	
	@Override
	protected String sql() {
		return SQL;
	}	

	/**
	 * @return the name of the user the query returns
	 * @throws DataAccessException
	 */
	public String getUserName() throws DataAccessException {
		return getString(USER_NAME_COLUMN);
	}

}
