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
package gr.interamerican.bo2.test.scenarios;

import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.impl.open.workers.AbstractOperation;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.samples.implopen.pw.UserPwImpl;
import gr.interamerican.bo2.utils.annotations.Child;

/**
 * Operation that executes a test case.
 * 
 * Deletes a user with JdbcPw.
 */
public class ReadUser
extends AbstractOperation {
	/**
	 * id of user.
	 */
	private int id;
	
	
	/**
	 * Creates a new DeleteUser object. 
	 *
	 * @param id Id of user to delete.
	 */
	public ReadUser(int id) {
		super();
		this.id = id;
	}

	/**
	 * Jdbc PW.
	 */
	@Child
	private PersistenceWorker<User> jdbcPw =
		new UserPwImpl();
	
	
	/* (non-Javadoc)
	 * @see gr.interamerican.bo2.impl.open.workers.AbstractOperation#execute()
	 */
	@Override
	public void execute() throws DataException {
	
		/* initially delete user */ 
		User user=new User();
		user.setId(id);
		user = jdbcPw.read(user);
		
	}
}
