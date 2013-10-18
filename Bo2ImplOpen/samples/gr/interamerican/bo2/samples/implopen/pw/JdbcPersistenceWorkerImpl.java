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

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;
import gr.interamerican.bo2.impl.open.annotations.ManagerName;
import gr.interamerican.bo2.impl.open.jdbc.JdbcPersistenceWorker;
import gr.interamerican.bo2.samples.archutil.po.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Simple implementation of {@link JdbcPersistenceWorker}
 * used in this unit test.
 *
 */
@ManagerName("LOCALDB")
public class JdbcPersistenceWorkerImpl 
extends JdbcPersistenceWorker<User>{
	
	/**
	 * SQL statement
	 */	
	private static final String SQL_SELECT = 
		"select * from X__X.USERS where id = ?"; //$NON-NLS-1$
	
	/**
	 * SQL statement
	 */	
	private static final String SQL_DELETE = 
		"delete from X__X.users where id = ? "; //$NON-NLS-1$
	
	/**
	 * SQL statement
	 */	
	private static final String SQL_INSERT =
		"insert into X__X.USERS (id, usr_id, usr_nm, role_id )" + //$NON-NLS-1$
		" values (?, ?, ?, ?)"; //$NON-NLS-1$
	
			
	@Override
	protected void doRead(User o) throws DataException, PoNotFoundException {
		try {
			Object[] parms={o.getId()};
			ResultSet rs=executePreparedQuery(SQL_SELECT,parms);			
			if (!rs.next()) {
				throw new PoNotFoundException();				
			} 
			readUser(rs, o);
			
		} catch (SQLException e) {
			throw new DataException(e);
		}
		
		
	}
	
	@Override
	protected void doStore(User o) throws DataException, PoNotFoundException {			
		try {
			Object[] parms={o.getId(),o.getUsrid(), o.getName(), o.getRoleId()};
			executePreparedUpdate(SQL_INSERT, parms);
		} catch (SQLException e) {
			throw new DataException(e);
		}
		
	}
	
	@Override
	protected void doDelete(User o) throws DataException, PoNotFoundException {
		try {
			Object[] parms={o.getId()};
			executePreparedUpdate(SQL_DELETE, parms);
		} catch (SQLException e) {
			throw new DataException(e);
		}
	
	}
	
	@Override
	public boolean ignoresSomething() {		
		return false;
	}
	
	/**
	 * Reads a user from a ResultSet.
	 * 
	 * @param rs
	 * @param user
	 * @throws SQLException
	 */
	private void readUser(ResultSet rs, User user) 
	throws SQLException {
		user.setId(rs.getInt("ID")); //$NON-NLS-1$
		user.setName(rs.getString("USR_NM").trim()); //$NON-NLS-1$
		user.setUsrid(rs.getString("USR_ID").trim()); //$NON-NLS-1$
		user.setRoleId(rs.getInt("ROLE_ID")); //$NON-NLS-1$
	}
	
}
