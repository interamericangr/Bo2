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
package gr.interamerican.bo2.impl.open.jdbc;


import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;
import gr.interamerican.bo2.impl.open.workers.PersistenceUtilityUserTestbean;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.samples.implopen.pw.UserPwImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for JdbcPersistenceWorker.
 *
 */
public class TestJdbcPersistenceWorker {
	
	/**
	 * Persistence worker.
	 */
	private PersistenceWorker<User> userPw;
	
	/**
	 * Test bean.
	 */
	private PersistenceUtilityUserTestbean<PersistenceWorker<User>> bean;

	/**
	 * Creates a new TestJdbcPersistenceWorker object. 
	 * @throws InitializationException 
	 *
	 */
	public TestJdbcPersistenceWorker() throws InitializationException {
		super();
		userPw = new UserPwImpl();
		bean = new PersistenceUtilityUserTestbean<PersistenceWorker<User>> (userPw);
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
	 * Tests getDetachStrategy()
	 */
	@Test
	public void testGetDetachStrategy() {
		assertEquals(JdbcDetachStrategy.INSTANCE, userPw.getDetachStrategy());
	}
	
}
