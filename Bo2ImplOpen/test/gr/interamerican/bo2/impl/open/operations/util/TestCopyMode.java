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
package gr.interamerican.bo2.impl.open.operations.util;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.po.utils.DefaultPoAnalyzerResolver;
import gr.interamerican.bo2.test.def.posamples.Customer;
import gr.interamerican.bo2.test.def.posamples.SamplesFactory;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * Tests {@link CopyMode}.
 */
public class TestCopyMode {

	/**
	 * For the sake of not forgetting tests (if a value is added this will break
	 * - so the developer might also write a test as well for the new
	 * enumeration value).
	 */
	@Test
	public void testCopyMode() {
		assertEquals(5, CopyMode.values().length);
	}

	/**
	 * Test method for handle when {@link CopyMode#DEFAULT}.
	 * 
	 * @throws DataException
	 */
	@Test(expected = DataException.class)
	public void testHandle_DEFAULT() throws DataException {
		Customer customer = Factory.create(Customer.class);
		CopyMode.DEFAULT.handle(null, customer, null, null, null);
	}

	/**
	 * Test method for handle when {@link CopyMode#OVERWRITE}.
	 * 
	 * @throws DataException
	 */
	@SuppressWarnings("nls")
	@Test
	public void testHandle_OVERWRITE() throws DataException {
		Customer from = SamplesFactory.getBo2Factory().sampleCustomer("taxNo", 0);
		Customer to = SamplesFactory.getBo2Factory().sampleCustomer("tax", 2);
		@SuppressWarnings("unchecked")
		PersistenceWorker<Customer> worker = mock(PersistenceWorker.class);
		DefaultPoAnalyzerResolver.INSTANCE.registerAnalyzer(Customer.class, "manager", new CustomCustomerAnalyzer());
		CopyMode.OVERWRITE.handle(from, to, worker, Customer.class, "manager");
		verify(worker, times(1)).update(Mockito.<Customer>eq(to));
		assertEquals("taxNo", to.getTaxId());
		assertTrue(to.getAddresses().isEmpty());
	}

	/**
	 * Test method for handle when {@link CopyMode#KEEP_TARGET}.
	 * 
	 * @throws DataException
	 */
	@Test
	public void testHandle_KEEP_TARGET() throws DataException {
		@SuppressWarnings("unchecked")
		PersistenceWorker<PersistentObject<?>> worker = mock(PersistenceWorker.class);
		CopyMode.KEEP_TARGET.handle(null, null, worker, null, null);
		verify(worker, times(0)).read(Mockito.any(PersistentObject.class));
		verify(worker, times(0)).update(Mockito.any(PersistentObject.class));
		verify(worker, times(0)).store(Mockito.any(PersistentObject.class));
	}

	/**
	 * Test method for handle when {@link CopyMode#MERGE_FAVOR_SOURCE}.
	 * 
	 * @throws DataException
	 */
	@SuppressWarnings("nls")
	@Test
	public void testHandle_MERGE_FAVOR_SOURCE() throws DataException {
		Customer from = SamplesFactory.getBo2Factory().sampleCustomer("taxNo", 0);
		Customer to = SamplesFactory.getBo2Factory().sampleCustomer("tax", 2);
		@SuppressWarnings("unchecked")
		PersistenceWorker<Customer> worker = mock(PersistenceWorker.class);
		DefaultPoAnalyzerResolver.INSTANCE.registerAnalyzer(Customer.class, "manager", new CustomCustomerAnalyzer());
		CopyMode.MERGE_FAVOR_SOURCE.handle(from, to, worker, Customer.class, "manager");
		verify(worker, times(1)).update(Mockito.<Customer>eq(to));
		assertEquals("taxNo", to.getTaxId());
		assertEquals(2, to.getAddresses().size());
	}

	/**
	 * Test method for handle when {@link CopyMode#MERGE_FAVOR_TARGET}.
	 * 
	 * @throws DataException
	 */
	@SuppressWarnings("nls")
	@Test
	public void testHandle_MERGE_FAVOR_TARGET() throws DataException {
		Customer from = SamplesFactory.getBo2Factory().sampleCustomer("taxNo", 1);
		Customer to = SamplesFactory.getBo2Factory().sampleCustomer("tax", 2);
		@SuppressWarnings("unchecked")
		PersistenceWorker<Customer> worker = mock(PersistenceWorker.class);
		DefaultPoAnalyzerResolver.INSTANCE.registerAnalyzer(Customer.class, "manager", new CustomCustomerAnalyzer());
		CopyMode.MERGE_FAVOR_TARGET.handle(from, to, worker, Customer.class, "manager");
		verify(worker, times(1)).update(Mockito.<Customer>eq(to));
		assertEquals("tax", to.getTaxId());
		assertEquals(2, to.getAddresses().size());
	}
}