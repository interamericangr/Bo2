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
package gr.interamerican.bo2.samples.implopen.pw;

import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;
import gr.interamerican.bo2.impl.open.annotations.ManagerName;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.jdbc.JdbcPersistenceWorker;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.samples.archutil.po.UserProfile;
import gr.interamerican.bo2.utils.annotations.Child;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A jdbc persistence worker for the User PO.
 */
@ManagerName("LOCALDB")
public class UserPwImpl extends JdbcPersistenceWorker<User> {

	/**
	 * sql query that inserts a new user to the TEST.USERS table
	 */
	private String sqlInsert = "insert into X__X.USERS (ID, USR_ID, USR_NM, ROLE_ID) values (?,?,?,?)"; //$NON-NLS-1$

	/**
	 * sql query that selects a user from the TEST.USERS table
	 */	
	private String sqlSelect = "SELECT * FROM X__X.USERS WHERE ID = ?"; //$NON-NLS-1$
	
	/**
	 * sql query that selects all the user profiles of a user.
	 */	
	private String sqlSelectProfiles = "select * from X__X.USERPROFILE where ID = ? "; //$NON-NLS-1$

	/**
	 * sql query that deletes a user from the TEST.USERS table
	 */	
	private String sqlDelete = "delete from X__X.USERS where ID = ? "; //$NON-NLS-1$
	
	

	/**
	 * Profile pw.
	 */
	@Child PersistenceWorker<UserProfile> upPw = Factory.createPw(UserProfile.class);

	@Override
	public boolean ignoresSomething() {
		return false;
	}

	@Override
	protected void doRead(User o) throws DataException, PoNotFoundException {
		Object[] selectParams = { o.getId() };
		try (ResultSet rs1 = executePreparedQuery(sqlSelect, selectParams);
				ResultSet rs2 = executePreparedQuery(sqlSelectProfiles, selectParams);) {

			if (rs1.next()) {
				o.setName(rs1.getString("USR_NM")); //$NON-NLS-1$
				o.setUsrid(rs1.getString("USR_ID")); //$NON-NLS-1$
				o.setRoleId(Integer.parseInt(rs1.getString("ROLE_ID"))); //$NON-NLS-1$
			} else {
				throw new PoNotFoundException("Not found user with id" + o.getId()); //$NON-NLS-1$
			}

			while (rs2.next()) {
				UserProfile up = new UserProfile();
				up.setUserId(rs2.getInt("ID")); //$NON-NLS-1$
				up.setProfileId(rs2.getInt("PROFILE_ID")); //$NON-NLS-1$
				up.setName(rs2.getString("PROF_NM")); //$NON-NLS-1$
				o.getProfiles().add(up);
			}
		} catch (SQLException e) {
			throw new DataException(e);
		}

	}

	@Override
	protected void doStore(User o) throws DataException, PoNotFoundException {

		Object[] insertParams = { o.getId(), o.getUsrid(), o.getName(), o.getRoleId() };
		int lines = 0;

		try {
			lines = executePreparedUpdate(sqlInsert, insertParams);
			for (UserProfile up : o.getProfiles()) {
				upPw.store(up);
			}
		} catch (SQLException e) {
			throw new DataException(e);
		}
		if (lines == 0) {
			throw new PoNotFoundException("Could not insert user with id" + o.getId()); //$NON-NLS-1$
		}
	}

	@Override
	protected void doDelete(User o) throws DataException, PoNotFoundException {
		doRead(o);
		Object[] deleteParams = { o.getId() };
		try {
			for (UserProfile up : o.getProfiles()) {
				upPw.delete(up);
			}
			executePreparedUpdate(sqlDelete, deleteParams);
		} catch (SQLException e) {
			throw new DataException(e);
		}
	}
}