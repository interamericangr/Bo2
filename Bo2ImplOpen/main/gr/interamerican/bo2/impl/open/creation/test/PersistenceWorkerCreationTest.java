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
import gr.interamerican.bo2.arch.exceptions.CouldNotRollbackException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.creation.test.conditions.ClassIsPwCondition;
import gr.interamerican.bo2.impl.open.utils.Bo2;

import java.io.IOException;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;

/**
 * Unit test for creation of {@link PersistenceWorker}.
 */
public class PersistenceWorkerCreationTest 
extends AbstractCreationTest {
	
	/**
	 * manager.
	 */
	private static Provider manager;
	
	/**
	 * Creates a new CreationTestBean object. 
	 *
	 * @param className the class name
	 * @throws ClassNotFoundException the class not found exception
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
	 * Before.
	 *
	 * @throws InitializationException the initialization exception
	 * @throws CouldNotBeginException the could not begin exception
	 */
	@Before
	public void before() throws InitializationException, CouldNotBeginException {
		manager = Bo2.getDefaultDeployment().getProvider();
		manager.getTransactionManager().begin();
	}
	
	/**
	 * After.
	 *
	 * @throws DataException the data exception
	 */
	@After
	public void after() throws DataException {
		Bo2Session.setProvider(null);
		try {
			manager.getTransactionManager().rollback();
		} catch (CouldNotRollbackException e) {
			throw new RuntimeException(e);
		}
		manager.close();
	}
	
	/**
	 * Test creation.
	 *
	 * @param <P> the generic type
	 * @param type the type
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
		}
	}
	
	/**
	 * Fails the test.
	 *
	 * @param t the t
	 * @param type the type
	 */
	void doFail(Throwable t, String type) {
		@SuppressWarnings("nls")
		String message = "Creation of PersistenceWorker for " + type
		               + " failed. Reason of failure:" + t.toString();
		System.err.println(message);
		t.printStackTrace();
		fail(message);
	}

	/**
	 * Test parameters.
	 *
	 * @param path the path
	 * @param excluded the excluded
	 * @return Returns the test parameters.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Collection<?> parameters(String path, String excluded) throws IOException {
		return parameters(path, excluded, new ClassIsPwCondition());
	}
}