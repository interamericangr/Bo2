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
import gr.interamerican.bo2.arch.utils.MockUtils;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.creation.test.Bo2BaseTest;
import gr.interamerican.bo2.samples.implopen.queries.CustomersFromInvoiceCustomerQuery;
import gr.interamerican.bo2.samples.implopen.queries.CustomersFromInvoiceLineQuery;
import gr.interamerican.bo2.test.def.posamples.Customer;
import gr.interamerican.bo2.test.def.posamples.InvoiceKP;

import org.junit.Test;

/**
 * Unit Tests of {@link CopyComplexEntityChildConfiguration}.
 */
@SuppressWarnings("unchecked")
public class TestCopyComplexEntityChildConfiguration extends Bo2BaseTest {

	/**
	 * Test method for
	 * {@link CopyComplexEntityChildConfiguration#addEntityToCopy(Class, Class, Class[])}
	 * .
	 */
	@Test
	public void testAddEntityToCopyClassOfPOClassOfQClassOfQextendsModificationOfPOArray() {
		Factory.registerPwFixture(Customer.class, MockUtils.<Customer> mockPw(null, null, null));
		CopyComplexEntityChildConfiguration<InvoiceKP> conf = new CopyComplexEntityChildConfiguration<InvoiceKP>();
		conf.addEntityToCopy(Customer.class, CustomersFromInvoiceCustomerQuery.class);
		assertEquals(1, conf.childEntities.size());
		ChildEntityInfo<InvoiceKP, ?, ?> info = conf.childEntities.get(0);
		assertNull(info.getCopyMode());
		assertTrue(info.getModifications().isEmpty());
		assertEquals(Customer.class, info.getPoClz());
		assertTrue(info.getQuery() instanceof CustomersFromInvoiceCustomerQuery);
		assertNull(info.getQueryByKey());
	}

	/**
	 * Test method for
	 * {@link CopyComplexEntityChildConfiguration#addEntityToCopy(Class, Class, CopyMode, Class[])}
	 * .
	 */
	@Test
	public void testAddEntityToCopyClassOfPOClassOfQCopyModeClassOfQextendsModificationOfPOArray() {
		Factory.registerPwFixture(Customer.class, MockUtils.<Customer> mockPw(null, null, null));
		CopyComplexEntityChildConfiguration<InvoiceKP> conf = new CopyComplexEntityChildConfiguration<InvoiceKP>();
		conf.addEntityToCopy(Customer.class, CustomersFromInvoiceCustomerQuery.class, CopyMode.KEEP_TARGET,
				CustomerModification.class);
		assertEquals(1, conf.childEntities.size());
		ChildEntityInfo<InvoiceKP, ?, ?> info = conf.childEntities.get(0);
		assertEquals(CopyMode.KEEP_TARGET, info.getCopyMode());
		assertEquals(1, info.getModifications().size());
		assertTrue(info.getModifications().get(0) instanceof CustomerModification);
		assertEquals(Customer.class, info.getPoClz());
		assertTrue(info.getQuery() instanceof CustomersFromInvoiceCustomerQuery);
		assertNull(info.getQueryByKey());
	}

	/**
	 * Test method for
	 * {@link CopyComplexEntityChildConfiguration#addEntityToCopyByKey(Class, Class, Class[])}
	 * .
	 */
	@Test
	public void testAddEntityToCopyByKeyClassOfPOClassOfQClassOfQextendsModificationOfPOArray() {
		CopyComplexEntityChildConfiguration<InvoiceKP> conf = new CopyComplexEntityChildConfiguration<InvoiceKP>();
		conf.addEntityToCopyByKey(Customer.class, CustomersFromInvoiceLineQuery.class, CustomerModification.class);
		assertEquals(1, conf.childEntities.size());
		ChildEntityInfo<InvoiceKP, ?, ?> info = conf.childEntities.get(0);
		assertNull(info.getCopyMode());
		assertEquals(1, info.getModifications().size());
		assertTrue(info.getModifications().get(0) instanceof CustomerModification);
		assertEquals(Customer.class, info.getPoClz());
		assertNull(info.getQuery());
		assertTrue(info.getQueryByKey() instanceof CustomersFromInvoiceLineQuery);
	}

	/**
	 * Test method for
	 * {@link CopyComplexEntityChildConfiguration#addEntityToCopyByKey(Class, Class, CopyMode, Class[])}
	 * .
	 */
	@Test
	public void testAddEntityToCopyByKeyClassOfPOClassOfQCopyModeClassOfQextendsModificationOfPOArray() {
		CopyComplexEntityChildConfiguration<InvoiceKP> conf = new CopyComplexEntityChildConfiguration<InvoiceKP>();
		conf.addEntityToCopyByKey(Customer.class, CustomersFromInvoiceLineQuery.class, CopyMode.OVERWRITE);
		assertEquals(1, conf.childEntities.size());
		ChildEntityInfo<InvoiceKP, ?, ?> info = conf.childEntities.get(0);
		assertEquals(CopyMode.OVERWRITE, info.getCopyMode());
		assertTrue(info.getModifications().isEmpty());
		assertEquals(Customer.class, info.getPoClz());
		assertNull(info.getQuery());
		assertTrue(info.getQueryByKey() instanceof CustomersFromInvoiceLineQuery);
	}

	/**
	 * Test method for
	 * {@link CopyComplexEntityChildConfiguration#commonAdd(Class, CopyMode, Class[])}
	 * .
	 */
	@Test
	public void testCommonAdd() {
		CopyComplexEntityChildConfiguration<InvoiceKP> conf = new CopyComplexEntityChildConfiguration<InvoiceKP>();
		conf.commonAdd(Customer.class, CopyMode.KEEP_TARGET, new Class[] {});
		assertEquals(1, conf.childEntities.size());
		ChildEntityInfo<InvoiceKP, ?, ?> info = conf.childEntities.get(0);
		assertEquals(CopyMode.KEEP_TARGET, info.getCopyMode());
		assertTrue(info.getModifications().isEmpty());
		assertEquals(Customer.class, info.getPoClz());
		assertNull(info.getQuery());
		assertNull(info.getQueryByKey());
	}

	/**
	 * Test method for
	 * {@link CopyComplexEntityChildConfiguration#getChildEntities()}.
	 */
	@Test
	public void testGetChildEntities() {
		CopyComplexEntityChildConfiguration<InvoiceKP> conf = new CopyComplexEntityChildConfiguration<InvoiceKP>();
		assertEquals(conf.childEntities, conf.getChildEntities());
	}
}