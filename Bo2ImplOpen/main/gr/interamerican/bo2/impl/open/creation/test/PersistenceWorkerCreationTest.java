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
package gr.interamerican.bo2.impl.open.creation.test;

import static org.junit.Assert.fail;
import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.CouldNotBeginException;
import gr.interamerican.bo2.arch.exceptions.CouldNotCommitException;
import gr.interamerican.bo2.arch.exceptions.CouldNotRollbackException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.utils.Bo2;

import org.junit.After;
import org.junit.Before;

/**
 * Unit test for creation of {@link PersistenceWorker}.
 */
public class PersistenceWorkerCreationTest 
extends AbstractTestClass {
	
	/**
	 * manager.
	 */
	private static Provider manager;
	
	/**
	 * Did the current test fail?
	 */
	private boolean failed = false;
	
	
	/**
	 * Creates a new CreationTestBean object. 
	 * 
	 * @param className
	 *  
	 * @throws ClassNotFoundException
	 */
	public PersistenceWorkerCreationTest(String className) 
	throws ClassNotFoundException {
		super(className);
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public void testClass(Class<?> type) {
		if (PersistentObject.class.isAssignableFrom(type)) {
			Class<? extends PersistentObject<?>> poClass =
				(Class<? extends PersistentObject<?>>) type;
			testCreation(poClass);
		} else {
			fail(type.getName() + " is not a persistent object"); //$NON-NLS-1$
		}
	}
	
	/**
	 * @throws InitializationException 
	 * @throws CouldNotBeginException 
	 * 
	 */
	@Before
	public void before() throws InitializationException, CouldNotBeginException {
		failed = false;
		manager = Bo2.getDefaultDeployment().getProvider();
		manager.getTransactionManager().begin();
	}
	
	/**
	 * @throws DataException
	 * @throws CouldNotCommitException 
	 */
	@After
	public void after() throws DataException, CouldNotCommitException {
		if(!failed) {
			manager.getTransactionManager().commit();
		}
		manager.close();
	}
	
	/**
	 * @param <P>
	 * @param type
	 */
	private <P extends PersistentObject<?>> 
	void testCreation(Class<P> type) {
		try {
			Bo2Session.setProvider(manager);
			P po = Factory.create(type);
			PersistenceWorker<P> pw = Factory.createPw(type);
			pw.init(manager);
			pw.open();			
			po = pw.read(po);
			pw.close();
		} catch (PoNotFoundException pnfe) {
			/* ok */
		} catch (DataException de) {
			doFail(de, type.getName());			
		} catch (InitializationException ie) {
			doFail(ie, type.getName());
		} catch (RuntimeException rte) {
			doFail(rte, type.getName());
		} finally {
			Bo2Session.setProvider(null);
		}
	}
	
	/**
	 * Fails the test.
	 * 
	 * @param t
	 * @param type
	 */
	void doFail(Throwable t, String type) {
		@SuppressWarnings("nls")
		String message = "Creation of PersistenceWorker for " + type
		               + " failed. Reason of failure:" + t.toString();
		System.err.println(message);
		t.printStackTrace();
		fail(message);	
		
		try {
			manager.getTransactionManager().rollback();
		} catch (CouldNotRollbackException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	

}
