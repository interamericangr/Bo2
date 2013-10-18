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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import gr.interamerican.bo2.arch.PersistenceUtility;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.Worker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;

/**
 * Unit test for a {@link PersistenceUtility}.
 * 
 * @param <PW>
 *         
 *
 */
public class PersistenceUtilityUserTestbean <PW extends PersistenceUtility<User>> {
	
	/**
	 * Persistence worker under test.
	 */
	private PW pw;
	
	/**
	 * Resources manager.
	 */
	private Provider manager;
	
	
	/**
	 * Creates a new PersistenceWorkerUserTestbean object. 
	 * 
	 * @param pw
	 * 
	 * @throws InitializationException 
	 */
	public PersistenceUtilityUserTestbean(PW pw) throws InitializationException {
		super();
		this.pw = pw;
		this.manager =  Bo2.getDeployment(UtilityForBo2Test.BATCH_NO_TRAN).getProvider();
	}

	/**
	 * Setup.
	 * @throws InitializationException
	 * @throws DataException 
	 */	
	public void setUp() throws InitializationException, DataException {
		if (pw instanceof Worker) {
			Worker w = (Worker) pw;
			w.init(manager);
			w.open();
		}
	}
	
	/**
	 * Tear down.
	 * 
	 * @throws DataException
	 */	
	public void tearDown() throws DataException {
		if (pw instanceof Worker) {
			Worker w = (Worker) pw;
			w.close();
		}
		manager.close();
	}
	
	
	
	
	/**
	 * Unit test for throwing a PoNotFoundException.
	 * 
	 * @throws PoNotFoundException
	 * 
	 * @see UtilityForBo2Test#getNotExistingUserId()
	 * 
	 * @Test(expected=PoNotFoundException.class)
	 */
		
	public void testPoNotFoundExceptionOnRead() 
	throws PoNotFoundException {
		try {
			User user = new User();
			user.setId(UtilityForBo2Test.getNotExistingUserId());
			user = pw.read(user);
		} catch (PoNotFoundException pnfe) {
			throw pnfe; 
		} catch (DataException de) {
			de.printStackTrace();
			fail(de.toString());
		}  
	}
	
	/**
	 * Unit test for ignores something.
	 */
	public void testIgnoresSomething() {
		assertFalse(pw.ignoresSomething());
	}
	
	/**
	 * Unit test to check that read does not fail.
	 * @throws DataException 
	 * 
	 * @see UtilityForBo2Test#getExistingUserId()
	 */	
	public void testReadDoesNotFail() 
	throws DataException {
		User user = new User();
		user.setId(UtilityForBo2Test.getExistingUserId());
		user = pw.read(user);
	}
	
	/**
	 * Unit test to check that update does not fail.
	 * @throws DataException 
	 */
	public void testUpdateDoesNotFail() 
	throws DataException {		
		User user=User.getDummy(100);
		try {
			user = pw.read(user);
			pw.update(user);
		} catch (PoNotFoundException pnfe) {
			user = pw.store(user);
			user = pw.update(user);
		}
	}

	/**
	 * Unit test to check that update and read work as they should.
	 * 
	 * An object is created and then saved. Then it is read and
	 * checked that the read object has the same attributes as
	 * the saved.
	 * @throws DataException 
	 */
	public void testUpdateAndReadDontFail() 
	throws DataException {
		
		User user=User.getDummy(101);
		try {
			user = pw.read(user);
			pw.update(user);
		} catch (PoNotFoundException pnfe) {
			user = pw.store(user);
			pw.update(user);
		}		 						
		User user2 = new User();
		user2.setId(user.getId());
		user2 = pw.read(user2);			
		
		assertEquals(user.getId(), user2.getId());
		assertEquals(user.getUsrid().trim(), user2.getUsrid().trim());
		assertEquals(user.getName().trim(), user2.getName().trim());
		assertEquals(user.getRoleId(), user2.getRoleId());			
	}
	
	
	/**
	 * Unit test to check that update and read work as they should.
	 * 
	 * An object is created and then saved. Then it is read and
	 * checked that the read object has the same attributes as
	 * the saved.
	 * @throws DataException 
	 */	
	public void testStoreReadAndDeleteDontFail() 
	throws DataException {
			
		/* User 501 must be deleted before and after this test. */
		User user=User.getDummy(600);
		
		try {
			user = pw.delete(user); //make sure he is deleted.
		} catch (PoNotFoundException e) {
			/* 
			 * if the object did't exist in the database
			 * it is no problem.
			 */
		}		
		user = pw.store(user);  						
		User user2 = new User();
		user2.setId(user.getId());
		user2 = pw.read(user2);			
		
		assertEquals(user.getId(), user2.getId());
		assertEquals(user.getUsrid().trim(), user2.getUsrid().trim());
		assertEquals(user.getName().trim(), user2.getName().trim());
		assertEquals(user.getRoleId(), user2.getRoleId());	
		
		user2 = pw.delete(user2);
		
		try {
			user2 = pw.read(user2);
		} catch (PoNotFoundException e) {
			/* ok, this user has been deleted */
		}
	}
	
	
	
}
