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
package gr.interamerican.bo2.test.scenarios.hibernate;

import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.impl.open.hibernate.GenericHibernatePersistenceWorker;
import gr.interamerican.bo2.impl.open.workers.AbstractOperation;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.samples.implopen.pw.UserPwImpl;
import gr.interamerican.bo2.utils.annotations.Child;

/**
 * Operation that executes a test case.
 * 
 * Save a user with HibernatePw, then saves another user with a JdbcPw.
 */
public class JdbcSave1HibSave2
extends AbstractOperation {
	
	
	/**
	 * id of user1 .
	 */
	private int id1;
	
	/**
	 * id of user1 .
	 */
	private int id2;

	
	/**
	 * Creates a new HibSaveJdbcRead object.
	 *  
	 * @param id1 
	 * @param id2 
	 */
	public JdbcSave1HibSave2(int id1, int id2) {
		super();
		this.id1 = id1;
		this.id2 = id2;
	}


	/**
	 * Hibernate PW.
	 */
	@Child
	private PersistenceWorker<User> hibPw =
		GenericHibernatePersistenceWorker.newInstance(User.class);
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
		
		String sampleName = "This is my name"; //$NON-NLS-1$
		String sampleUserid = "US00"; //$NON-NLS-1$
		int sampleRoleId = 1;
		
		User user1 = new User();
		user1.setName(sampleName);
		user1.setId(id1);
		user1.setUsrid(sampleUserid);
		user1.setRoleId(sampleRoleId);		
		user1 = hibPw.store(user1);
		
		User user2 = new User();
		user2.setName(sampleName);
		user2.setId(id2);
		user2.setUsrid(sampleUserid);
		user2.setRoleId(sampleRoleId);
		user2 = jdbcPw.store(user2);
	}
}
