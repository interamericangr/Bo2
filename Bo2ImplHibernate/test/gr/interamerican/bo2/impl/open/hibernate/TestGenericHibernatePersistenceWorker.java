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


import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;
import gr.interamerican.bo2.impl.open.annotations.Bo2AnnoUtils;
import gr.interamerican.bo2.impl.open.hibernate.refreshmodes.GetAndRefresh;
import gr.interamerican.bo2.impl.open.hibernate.refreshmodes.JustGet;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.impl.open.workers.PersistenceUtilityUserTestbean;
import gr.interamerican.bo2.samples.archutil.po.PoWithIntegerKey;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.test.def.posamples.Invoice;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;
import gr.interamerican.bo2.utils.meta.exceptions.ValidationException;
import gr.interamerican.bo2.utils.meta.validators.Validator;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Tests 
 */
public class TestGenericHibernatePersistenceWorker {
	
	/**
	 * Creates a new TestGenericHibernatePersistenceWorker object. 
	 * @throws InitializationException 
	 *
	 */
	public TestGenericHibernatePersistenceWorker() throws InitializationException {
		super();
		Bo2AnnoUtils.setManagerName(User.class, "LOCALDB"); //$NON-NLS-1$
		userPw = new GenericHibernatePersistenceWorker<User>(User.class, RefreshMode.getDefaultMode());
		bean = new PersistenceUtilityUserTestbean<PersistenceWorker<User>> (userPw);
	}

	/**
	 * Persistence worker.
	 */
	private PersistenceWorker<User> userPw;
	
	/**
	 * Test bean.
	 */
	private PersistenceUtilityUserTestbean<PersistenceWorker<User>> bean;

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
	 * Tests getDetachStrategy()
	 */
	@Test
	public void testGetDetachStrategy() {
		assertTrue(userPw.getDetachStrategy() instanceof HibernateDetachStrategy);
	}
	
	/**
	 * Tests the constructor failur due to missing annotation.
	 * @throws InitializationException 
	 */
	@Test(expected=InitializationException.class)
	public void testInit_failDueToMissingAnnotation() throws InitializationException {
		GenericHibernatePersistenceWorker<PoWithIntegerKey> pw =
			new GenericHibernatePersistenceWorker<PoWithIntegerKey>
			(PoWithIntegerKey.class,new RefreshMode(JustGet.INSTANCE, JustGet.INSTANCE, GetAndRefresh.INSTANCE));
		Provider prov = Bo2.getDeployment(UtilityForBo2Test.BATCH_NO_TRAN).getProvider(); //Non transactional provider.
		pw.init(prov);
	}
	
	/**
	 * Tests newInstance(clazz) 
	 */
	@Test()
	public void testNewInstance() {
		GenericHibernatePersistenceWorker<PoWithIntegerKey> pw =
			GenericHibernatePersistenceWorker.newInstance(PoWithIntegerKey.class);
		Assert.assertNotNull(pw);
	}
	
	/**
	 * Tests newInstance(clazz, validator) 
	 * @throws ValidationException 
	 * @throws InitializationException 
	 */
	@Test()
	public void testNewInstance_withValidator() throws ValidationException, InitializationException {
		@SuppressWarnings("unchecked")
		Validator<Invoice> validator = Mockito.mock(Validator.class);
		GenericHibernatePersistenceWorker<Invoice> pw =
			GenericHibernatePersistenceWorker.newInstance(Invoice.class, validator);
		Assert.assertNotNull(pw);		
		Invoice po = Mockito.mock(Invoice.class);
		ValidationException vex = new ValidationException("VEX"); //$NON-NLS-1$
		Mockito.doThrow(vex).when(validator).validate(po);		
		try {
			Provider prov = Bo2.getDeployment(UtilityForBo2Test.BATCH_NO_TRAN).getProvider(); //Non transactional provider. 
			pw.init(prov);
			pw.open();
			pw.store(po);
			Assert.fail("No Validation Exception thrown"); //$NON-NLS-1$
		} catch (DataException e) {
			Throwable cause = e.getCause();
			Assert.assertEquals(vex, cause);
		}
	}
	
}
