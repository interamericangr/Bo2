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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.test.def.posamples.Invoice;
import gr.interamerican.bo2.test.def.posamples.InvoiceKey;
import gr.interamerican.bo2.test.def.posamples.InvoiceLine;
import gr.interamerican.bo2.test.def.posamples.SamplesFactory;
import gr.interamerican.bo2.utils.ReflectionUtils;

/**
 * ���� ��� �� {@link AbstractBasePo} �� ����� �� �� {@link PoUtils}.
 * 
 */
public class PoUtilsTestBean {

	/**
	 * Factory of objects.
	 */
	private SamplesFactory factory;
	
	/**
	 * Creates a new AbstractBasePoTest object. 
	 *
	 * @param factory
	 */
	public PoUtilsTestBean(SamplesFactory factory) {
		super();
		this.factory = factory;
	}
	
	/**
	 * tests equals for a simple Po
	 */
	public void testDeepEqualsForEqualInvoiceLines() {
		InvoiceLine line1a = factory.sampleInvoiceLine(1);
		InvoiceLine line1b = factory.sampleInvoiceLine(1);
		assertTrue(PoUtils.deepEquals(line1a,line1b));
	}
	
	/**
	 * tests equals for a simple Po
	 */
	public void testDeepEqualsForNotEqualInvoiceLines() {
		InvoiceLine line1a = factory.sampleInvoiceLine(1);	
		InvoiceLine line1c = factory.sampleInvoiceLine(1);
		line1c.setAmount(20.0);
		assertFalse(PoUtils.deepEquals(line1a,line1c));
	}
	
	
	
	/**
	 * tests equals for a complex Po
	 */	
	public void testDeepEqualsForEqualInvoices() {
		Invoice invoice1a = factory.sampleInvoiceFull(5);
		Invoice invoice2a = factory.sampleInvoiceFull(5);
		InvoiceKey key1 = Factory.create(InvoiceKey.class);		
		InvoiceKey key2 = Factory.create(InvoiceKey.class);
		key1.setInvoiceNo("1010"); //$NON-NLS-1$
		ReflectionUtils.copyProperties(key1, key2);
		invoice1a.setKey(key1);
		invoice2a.setKey(key2);
		
		assertTrue(PoUtils.deepEquals(invoice1a,invoice2a));	
	}
	
	
	/**
	 * tests equals for a complex Po
	 */	
	public void testDeepEqualsForNonEqualInvoices() {
		Invoice invoice1a = factory.sampleInvoiceFull(5);
		Invoice invoice2a = factory.sampleInvoiceFull(5);
		InvoiceKey key1 = Factory.create(InvoiceKey.class);		
		InvoiceKey key2 = Factory.create(InvoiceKey.class);
		key1.setInvoiceNo("1010"); //$NON-NLS-1$
		ReflectionUtils.copyProperties(key1, key2);
		invoice1a.setKey(key1);
		invoice2a.setKey(key2);
		InvoiceLine newLine = factory.sampleInvoiceLine(6);
		invoice1a.getLines().add(newLine);
		assertFalse(PoUtils.deepEquals(invoice1a,invoice2a));
	}

}
