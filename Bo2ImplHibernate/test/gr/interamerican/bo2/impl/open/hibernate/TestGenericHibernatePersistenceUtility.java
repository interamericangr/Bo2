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
package gr.interamerican.bo2.impl.open.hibernate;


import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;
import gr.interamerican.bo2.impl.open.workers.PersistenceUtilityUserTestbean;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.samples.archutil.po.UserKey;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests 
 */
public class TestGenericHibernatePersistenceUtility {

	/**
	 * Persistence worker.
	 */
	private GenericHibernatePersistenceUtility<User> userPw;
	
	/**
	 * Test bean.
	 */
	private PersistenceUtilityUserTestbean
	<GenericHibernatePersistenceUtility<User>> bean;
	
	/**
	 * Creates a new TestGenericHibernatePersistenceWorker object. 
	 * @throws InitializationException 
	 *
	 */
	public TestGenericHibernatePersistenceUtility() throws InitializationException {
		super();
		userPw = new GenericHibernatePersistenceUtility<User>(User.class, UserKey.class, "key", RefreshMode.getDefaultMode()); //$NON-NLS-1$
		userPw.setManagerName("LOCALDB"); //$NON-NLS-1$
		bean = new PersistenceUtilityUserTestbean<GenericHibernatePersistenceUtility<User>> (userPw);
	}

	/**
	 * See bean.
	 * @throws InitializationException
	 * @throws DataException
	 */
	@Before
	public void setUp() throws InitializationException, DataException {		
		bean.setUp();
	}

	/**
	 * See bean.
	 * 
	 * @throws DataException
	 */
	@After
	public void tearDown() throws DataException {
		bean.tearDown();
	}

	/**
	 * See bean.
	 * 
	 * @throws PoNotFoundException
	 */
	@Test(expected=PoNotFoundException.class)
	public void testPoNotFoundExceptionOnRead() throws PoNotFoundException {
		bean.testPoNotFoundExceptionOnRead();
	}

	/**
	 * See bean.
	 * @throws DataException 
	 */
	@Test
	public void testReadDoesNotFail() 
	throws DataException {
		bean.testReadDoesNotFail();
	}
	
	/**
	 * See bean.
	 * @throws DataException 
	 */
	@Test
	public void testUpdateDoesNotFail() 
	throws DataException {
		bean.testUpdateDoesNotFail();
	}

	/**
	 * See bean.
	 * @throws DataException 
	 */
	@Test
	public void testUpdateAndReadDontFail() 
	throws DataException {
		bean.testUpdateAndReadDontFail();
	}

	/**
	 * See bean.
	 * @throws DataException 
	 */
	@Test
	public void testStoreReadAndDeleteDontFail() 
	throws DataException {
		bean.testStoreReadAndDeleteDontFail();
	}
	
	/**
	 * See bean.
	 * 
	 */
	@Test
	public void testIgnoresSomething(){
		bean.testIgnoresSomething();
	}
	
	/**
	 * Unit test for getUniqueId.
	 */
	@Test
	public void testGetUniqueId() {
		User user = new User();
		user.setId(87548);
		Object id = userPw.getUniqueId(user);
		Assert.assertEquals(user.getKey(), id);
	}
	
	

}
