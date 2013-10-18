package gr.interamerican.bo2.samples.implopen.pw;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;
import gr.interamerican.bo2.impl.open.jdbc.JdbcPersistenceWorker;
import gr.interamerican.bo2.samples.archutil.po.UserProfile;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A jdbc persistence worker for the UserProfile PO. 
 */
public class UserProfilePwImpl extends JdbcPersistenceWorker<UserProfile>{
	
	/**
	 * sql query that inserts a new user to the TEST.USERPROFILE table
	 */
	private String sqlInsert = "insert into TEST.USERPROFILE (ID, PROFILE_ID, PROF_NM) values (?,?,?)"; //$NON-NLS-1$

	/**
	 * sql query that selects a user from the TEST.USERPROFILE table
	 */	
	private String sqlSelect = "SELECT * FROM TEST.USERPROFILE WHERE ID = ? AND PROFILE_ID = ?"; //$NON-NLS-1$

	/**
	 * sql query that deletes a user from the TEST.USERPROFILE table
	 */	
	private String sqlDelete = "delete from TEST.USERPROFILE where ID = ? AND PROFILE_ID = ?"; //$NON-NLS-1$

	@Override
	public boolean ignoresSomething() {
		return false;
	}

	@Override
	protected void doRead(UserProfile o) throws DataException, PoNotFoundException {
		Object[] selectParams = { o.getUserId(), o.getProfileId() };
		try {
			ResultSet rs = executePreparedQuery(sqlSelect, selectParams);
			boolean found = false;
			if (rs.next()) {
				o.setName(rs.getString("PROF_NM")); //$NON-NLS-1$
				found = true;
			} 
			rs.close();
			if (!found) {
				throw new PoNotFoundException("Not found user with id" + o.getKey()); //$NON-NLS-1$
			}
		} catch (SQLException e) {
			throw new DataException(e);
		}	
		
	}

	@Override
	protected void doStore(UserProfile o) throws DataException, PoNotFoundException {

		Object[] insertParams = { o.getUserId(), o.getProfileId(), o.getName() };
		int lines = 0;

		try {
			lines = executePreparedUpdate(sqlInsert, insertParams);
		} catch (SQLException e) {
			throw new DataException(e);
		}
		if (lines==0) {
			throw new 
			PoNotFoundException("Could not insert user with id" + o.getKey()); //$NON-NLS-1$
		}
		
	}

	@Override
	protected void doDelete(UserProfile o) throws DataException, PoNotFoundException {
		Object[] deleteParams = { o.getUserId(), o.getProfileId() };
		int lines = 0;
		
		try {
			lines = executePreparedUpdate(sqlDelete, deleteParams);
		} catch (SQLException e) {
			throw new DataException(e);
		}
		if (lines==0) {
			throw new 
			PoNotFoundException("Could not delete user with id" + o.getKey()); //$NON-NLS-1$
		}
		
	}

	
}
