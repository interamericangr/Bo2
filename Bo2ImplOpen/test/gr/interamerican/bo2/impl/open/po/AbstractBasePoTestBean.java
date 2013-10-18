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
package gr.interamerican.bo2.impl.open.po;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.test.def.posamples.Invoice;
import gr.interamerican.bo2.test.def.posamples.InvoiceCustomer;
import gr.interamerican.bo2.test.def.posamples.InvoiceKey;
import gr.interamerican.bo2.test.def.posamples.InvoiceLine;
import gr.interamerican.bo2.test.def.posamples.SamplesFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * ���� ��� �� {@link AbstractBasePo} ��� �� ���������� ���.
 * 
 */
public class AbstractBasePoTestBean {
	
	/**
	 * default invoice no.
	 */
	private static final String INVOICENO_1 = "000001"; //$NON-NLS-1$
	
	/**
	 * Factory of objects.
	 */
	private SamplesFactory factory;

	
	/**
	 * Creates a new AbstractBasePoTest object. 
	 *
	 * @param factory
	 */
	public AbstractBasePoTestBean(SamplesFactory factory) {
		super();
		this.factory = factory;
	}	
	
	/**
	 * Tests that when setting a property of the key, 
	 * the change is applied also on the key.
	 */	
	public void testCallingKeyDelegate() {
		Invoice invoice = factory.newInvoice();
		invoice.setInvoiceNo(INVOICENO_1);
		assertEquals(INVOICENO_1, invoice.getKey().getInvoiceNo());
	}
	
	/**
	 * Tests that when setting the key of the po, the properties
	 * of the new key will be returned by the delegate methods.
	 */	
	public void testChangingTheKeyIsAppliedOnDelegate() {
		Invoice invoice = factory.newInvoice();
		InvoiceKey key = Factory.create(InvoiceKey.class);
		key.setInvoiceNo(INVOICENO_1);
		invoice.setKey(key);
		assertEquals(INVOICENO_1, invoice.getInvoiceNo());
	}
	
	
	
	/**
	 * Tests that when setting a child element,
	 * its key will be fixed.
	 */	
	public void testSettingChild() {
		Invoice invoice = factory.newInvoice();
		InvoiceKey key = Factory.create(InvoiceKey.class);
		key.setInvoiceNo(INVOICENO_1);
		invoice.setKey(key);
		InvoiceCustomer cust = factory.newInvoiceCustomer();
		invoice.setCustomer(cust);		
		assertEquals(INVOICENO_1, invoice.getCustomer().getInvoiceNo());
	}
	
	
	/**
	 * Tests that when setting the key, the keys 
	 * of the children will be fixed accordingly.
	 */	
	public void testSettingTheKey() {
		Invoice invoice = factory.newInvoice();
		InvoiceCustomer cust = factory.newInvoiceCustomer();
		invoice.setCustomer(cust);		
		InvoiceKey key = Factory.create(InvoiceKey.class);
		key.setInvoiceNo(INVOICENO_1);
		invoice.setKey(key);
		assertEquals(INVOICENO_1, invoice.getCustomer().getInvoiceNo());
		
	}
	
	/**
	 * Tests that when setting a property of the key,
	 * the keys of the children will be fixed accordingly.
	 */	
	public void testModifyingTheKey() {
		Invoice invoice = factory.newInvoice();
		InvoiceCustomer cust = factory.newInvoiceCustomer();
		invoice.setCustomer(cust);		
		invoice.setInvoiceNo(INVOICENO_1);
		assertEquals(INVOICENO_1, invoice.getCustomer().getInvoiceNo());
	}
	
	/**
	 * Tests that when setting a collection, the elements of the
	 * collection will have its key modified according to the 
	 * key of the persistent object.
	 */	
	public void testSettingChildCollection() {
		Invoice invoice = factory.newInvoice();
		invoice.setInvoiceNo(INVOICENO_1);
		InvoiceLine line1 = factory.sampleInvoiceLine(1);
		InvoiceLine line2 = factory.sampleInvoiceLine(2);
		InvoiceLine line3 = factory.sampleInvoiceLine(3);
		Set<InvoiceLine> lines = new HashSet<InvoiceLine>();
		lines.add(line1);
		lines.add(line2);
		lines.add(line3);
		
		invoice.setLines(lines);
		
		assertEquals(INVOICENO_1, line1.getInvoiceNo());
		assertEquals(INVOICENO_1, line2.getInvoiceNo());
		assertEquals(INVOICENO_1, line3.getInvoiceNo());		
	}
	
	
	/**
	 * Tests that the child collections are always initialized.
	 */	
	public void testInitializationOfChildCollection() {
		Invoice invoice = factory.newInvoice();
		Set<InvoiceLine> lines = invoice.getLines();
		assertNotNull(lines);
	}
	
	/**
	 * Tests that the setter of a child collection sets the reference
	 * of the collection.
	 */	
	public void testSetterSetsTheReference() {
		Invoice invoice = factory.newInvoice();
		Set<InvoiceLine> lines = new HashSet<InvoiceLine>();
		invoice.setLines(lines);
		assertSame(lines, invoice.getLines());
	}
	
}
