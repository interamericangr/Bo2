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
package gr.interamerican.bo2.impl.open.workers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.samples.archutil.po.UserKey;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * 
 */
public class TestPwWrapperQuery {
	
	/**
	 * query to test
	 */
	PwWrapperQuery<User, UserKey> query = new PwWrapperQuery<User, UserKey>(User.class);
	
	
	/**
	 * Tests execute
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	@Test
	public void testPwWrapperQuery() throws UnexpectedException, DataException, LogicException {
		@SuppressWarnings("unchecked")
		PersistenceWorker<User> pw = Mockito.mock(PersistenceWorker.class);
		final User existingUser = Factory.create(User.class);
		existingUser.setId(1);
		
		Mockito.when(pw.read(existingUser)).thenReturn(existingUser);
		query.pw = pw;
		
		new AbstractBo2RuntimeCmd() {			
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {						
				query.init(getProvider());
				query.open();
				query.setCriteria(existingUser.getKey());
				query.getRow();
				query.execute();
				assertEquals(existingUser, query.getEntity());
			}
		}.execute();
	}
	
	
	

	/**
	 * Tests PwWrapperQuery when Po doesn't exist
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	@Test
	public void testPwWrapperQuery_PoNotFounException() throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {			
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {						
				query.init(getProvider());
				query.open();
				UserKey key = Factory.create(UserKey.class);
				key.setId(-1);
				query.setCriteria(key);
				query.execute();
				query.next();
			}
		}.execute();
	}
	
	
	/**
	 * Tests PwWrapperQuery without giving criteria 
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	@Test(expected = DataException.class)
	public void testPwWrapperQuery_dataException() throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {			
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {						
				query.init(getProvider());
				query.open();
				query.execute();
			}
		}.execute();
	}
	
	
	/**
	 * Tests PwWrapperQuery without giving criteria 
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	@Test(expected = DataException.class)
	public void testGetEntity() throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {			
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {						
				query.init(getProvider());
				query.open();
				query.getEntity();
			}
		}.execute();
	}
	
	/**
	 * Tests getCriteria
	 */
	@Test
	public void testGetCriteria(){
		UserKey key = Factory.create(UserKey.class);
		key.setId(2);
		query.setCriteria(key);
		assertEquals(key,query.getCriteria());
	}
	
	
	/**
	 *Tests isAvoidLock 
	 */
	@Test
	public void testIsAvoidLock(){
		assertTrue(query.isAvoidLock());
	}
	
	
	/**
	 *Tests isAvoidLock 
	 */
	@Test
	public void testSetAvoidLock(){
		query.setAvoidLock(false);
		assertTrue(query.isAvoidLock());
	}
	
}
