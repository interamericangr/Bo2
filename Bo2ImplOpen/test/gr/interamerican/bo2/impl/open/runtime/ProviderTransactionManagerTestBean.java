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
package gr.interamerican.bo2.impl.open.runtime;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.TransactionManager;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.samples.implopen.operations.DeleteUserFromBothDb;
import gr.interamerican.bo2.samples.implopen.operations.ReadUserFromBothDb;
import gr.interamerican.bo2.samples.implopen.operations.StoreUserToBothDb;

import org.junit.Assert;

/**
 * Unit test for the {@link TransactionManager} of a {@link Provider}.
 */
public class ProviderTransactionManagerTestBean {
	
	/**
	 * Deletes two users.
	 */
	private DeleteUserFromBothDb delete;
	/**
	 * Stores two users.
	 */
	private StoreUserToBothDb storeOk;
	/**
	 * Stores two users and fails.
	 */
	private StoreUserToBothDb storeFail;
	/**
	 * Reads two users.
	 */
	private ReadUserFromBothDb read;
	/**
	 * Creates a new ProviderTransactionManagerTestBean object. 
	 *
	 * @param hib 
	 */
	public ProviderTransactionManagerTestBean(boolean hib) {
		super();
		delete = new DeleteUserFromBothDb(hib);
		storeOk = new StoreUserToBothDb(hib, false);
		storeFail = new StoreUserToBothDb(hib, true);
		read = new ReadUserFromBothDb(hib);
	}	
	
	/**
	 * Tests that commit is successful.
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 * 
	 */
	public void testCommit() throws DataException, LogicException, UnexpectedException {
		Execute.transactional(delete); 
		Execute.transactional(storeOk); 
		Execute.transactional(read); 
		assertNotNull(read.getLocalUser());
		assertNotNull(read.getOtherUser());
		Execute.transactional(delete);  
	}
	
	/**
	 *  Tests that commit is successful.
	 *  
	 * @param deployment
	 * @throws DataException
	 * @throws LogicException
	 * @throws UnexpectedException
	 */
	public void testCommit(String deployment) 
	throws DataException, LogicException, UnexpectedException {
		Execute.transactional(deployment, delete); 
		Execute.transactional(deployment, storeOk); 
		Execute.transactional(deployment, read); 
		assertNotNull(read.getLocalUser());
		assertNotNull(read.getOtherUser());
		Execute.transactional(deployment, delete);  
	}
	
	/**
	 * Tests that rollback is successful.
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 * 
	 */
	public void testRollback() 
	throws DataException, LogicException, UnexpectedException {
		Execute.transactional(delete); 
		try {			
			Execute.transactional(storeFail);
			/*
			 * storeFail throws a RuntimeException that should be caught by 
			 * Execute.transactional() and re-thrown as an UnexpectedException 
			 */
		} catch (LogicException e) {
			Assert.fail("Caught LogicException while expecting an UnexpectedException"); //$NON-NLS-1$
		} catch (DataException e) {
			Assert.fail("Caught LogicException while expecting an UnexpectedException"); //$NON-NLS-1$			
		} catch (UnexpectedException e) {
			/* We caught the exception, now let's see if it rolled back */
		}
		Execute.transactional(read); 
		assertNull(read.getLocalUser());
		assertNull(read.getOtherUser());
		Execute.transactional(delete);  
	}
	
	/**
	 * Tests that rollback is successful.
	 * 
	 * @param deployment 
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 * 
	 */
	public void testRollback(String deployment) 
	throws DataException, LogicException, UnexpectedException {
		Execute.transactional(deployment, delete); 
		try {			
			Execute.transactional(deployment, storeFail);
			/*
			 * storeFail throws a RuntimeException that should be caught by 
			 * Execute.transactional() and re-thrown as an UnexpectedException 
			 * 
			 */
		} catch (LogicException e) {
			Assert.fail("Caught LogicException while expecting an UnexpectedException"); //$NON-NLS-1$
		} catch (DataException e) {
			Assert.fail("Caught DataException while expecting an UnexpectedException"); //$NON-NLS-1$
		} catch (UnexpectedException e) {
			/* We caught the exception, now let's see if it rolled back */
		}
		Execute.transactional(deployment, read); 
		assertNull(read.getLocalUser());
		assertNull(read.getOtherUser());
		Execute.transactional(deployment, delete);  
	}
	
}
