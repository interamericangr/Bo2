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



import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.annotations.ManagerName;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.impl.open.runtime.CrudCmd;
import gr.interamerican.bo2.impl.open.workers.PersistenceUtilityUserTestbean;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.test.def.posamples.Customer;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link AbstractHibernatePersistenceUtility}.
 */
public class TestAbstractHibernatePersistenceUtility {

	/**
	 * Persistence worker.
	 */
	private UserAHPU userPw;
	
	/**
	 * Test bean.
	 */
	private PersistenceUtilityUserTestbean<UserAHPU> bean;
	
	/**
	 * Creates a new TestGenericHibernatePersistenceWorker object. 
	 * @throws InitializationException 
	 *
	 */
	public TestAbstractHibernatePersistenceUtility() throws InitializationException {
		super();
		userPw = new UserAHPU();
		userPw.setManagerName("LOCALDB"); //$NON-NLS-1$
		bean = new PersistenceUtilityUserTestbean<UserAHPU> (userPw);
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
	 * test getPoClass().
	 * 
	 */
	@Test
	public void testGetPoClass(){
		Assert.assertEquals(User.class, userPw.getPoClass());
	}
	
	
	/**
	 * Deletes a customer
	 * 
	 * @param cust
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	void cleanDatabase(Customer cust) 
	throws UnexpectedException, DataException, LogicException {
		CrudCmd<Customer> crud = new CrudCmd<Customer>(new CustomerAHPU());
		Customer c;
		try {
			c = crud.read(cust);
		} catch (PoNotFoundException e1) {
			c=null;
		}
		if (c!=null) {
			crud.delete(c);
		}
	}
	
	/**
	 * test logStaleObjectException().
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 * 
	 */
	@Test()
	@SuppressWarnings("nls")
	public void testLogStaleObjectException() throws UnexpectedException, DataException, LogicException {		
		String custNo = "CustSoe";
		Customer cust = Factory.create(Customer.class);
		cust.setCustomerNo(custNo);
		cust.setCustomerName("Customer Name");
		
		CrudCmd<Customer> crud = new CrudCmd<Customer>(new CustomerAHPU());
		
		cleanDatabase(cust);
		
		crud.store(cust);
		Customer other = crud.read(cust);
		
		cust.setLastModifiedBy("X");		
		crud.update(cust);
		
		other.setLastModifiedBy("Y");
		
		try {
			crud.update(other);
		} catch (DataException e) {
			/*
			 *  this exception means the test passed.
			 */
		}
	}
	
	
	
	/**
	 * test logStaleObjectException().
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 * 
	 */
	@Test()
	@SuppressWarnings("nls")
	public void testDelete() throws UnexpectedException, DataException, LogicException {
		final String custNo = "TestDelete1";
		Customer cust = Factory.create(Customer.class);
		cust.setCustomerNo(custNo);		
		cleanDatabase(cust);
		CrudCmd<Customer> crud = new CrudCmd<Customer>(new CustomerAHPU());
		crud.store(cust);
		
		new AbstractBo2RuntimeCmd() {			
			@Override
			public void work() throws LogicException, DataException, InitializationException, UnexpectedException {
				CustomerAHPU pw = new CustomerAHPU();
				pw.init(getProvider());
				pw.open();
				Customer c = Factory.create(Customer.class);
				c.setCustomerNo(custNo);
				pw.delete(c);
				pw.close();
			}
		}.execute();
		Customer read;
		try {
			read = crud.read(cust);
		} catch (PoNotFoundException e) {
			read = null;
		}
		Assert.assertNull("Object was not deleted", read);
	}
	
	/**
	 * test newDataException().
	 * 
	 */
	@Test()
	@SuppressWarnings("nls")
	public void testNewDataException() {
		UserAHPU pu = new UserAHPU();
		HibernateException hex = new HibernateException("msg");
		User u = new User();
		DataException dex = pu.newDataException(hex, u);
		Assert.assertNotNull(dex);
		Assert.assertEquals(hex, dex.getCause());
	}
	
	
	/**
	 * Implementation of AbstractHibernatePersistenceUtility.
	 * 
	 * @param <P> 
	 */
	class PoAHPU<P extends PersistentObject<?>> 
	extends AbstractHibernatePersistenceUtility<P> {

		/**
		 * Creates a new InvoiceAHPU object.
		 * 
		 * @param poClass 
		 */
		public PoAHPU(Class<P> poClass) {
			super(poClass,null,RefreshMode.getDefaultMode());
		}
		
		@Override
		protected void prepareObject(P po) {
			po.tidy();
		}
		@Override
		protected Serializable getUniqueId(P po) {
			return po.getKey();
		}
		
	}
	
	
	
	/**
	 * Implementation of AbstractHibernatePersistenceUtility.
	 */
	class UserAHPU extends PoAHPU<User> {
		/**
		 * Creates a new UserAHPU object.
		 */
		public UserAHPU() {
			super(User.class);
		}		
	}

	
	/**
	 * Implementation of AbstractHibernatePersistenceUtility.
	 */
	@ManagerName("LOCALDB")
	class CustomerAHPU extends PoAHPU<Customer> {
		/**
		 * Creates a new InvoiceCustomerAHPU object.
		 */
		public CustomerAHPU() {
			super(Customer.class);
		}
	}
	


	
	

}
