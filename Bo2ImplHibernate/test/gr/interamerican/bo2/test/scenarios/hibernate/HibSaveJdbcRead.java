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

import static org.junit.Assert.assertEquals;
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
 * Save a user with HibernatePw, then read it with JdbcPw.
 */
public class HibSaveJdbcRead
extends AbstractOperation {
	
	
	/**
	 * id of user.
	 */
	private int id;
	
	/**
	 * Creates a new HibSaveJdbcRead object. 
	 *
	 * @param id
	 */
	public HibSaveJdbcRead(int id) {
		super();
		this.id = id;
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
	private PersistenceWorker<User> jdbcPw = new UserPwImpl();
	
	
	/* (non-Javadoc)
	 * @see gr.interamerican.bo2.impl.open.workers.AbstractOperation#execute()
	 */
	@Override
	public void execute() throws DataException {
		
		
		String sampleName = "This is my name"; //$NON-NLS-1$
		String sampleUserid = "US00"; //$NON-NLS-1$
		int sampleRoleId = 1;			
	
		/* main test case */
		User user = new User();
		user.setName(sampleName);
		user.setId(id);
		user.setUsrid(sampleUserid);
		user.setRoleId(sampleRoleId);
		
		user = hibPw.store(user);
		
		User user2 = new User();
		user2.setId(user.getId());
		user2 = jdbcPw.read(user2);			
		
		assertEquals(user.getId(), user2.getId());
		assertEquals(user.getUsrid().trim(), user2.getUsrid().trim());
		assertEquals(user.getName().trim(), user2.getName().trim());
		assertEquals(user.getRoleId(), user2.getRoleId());
	}
}
