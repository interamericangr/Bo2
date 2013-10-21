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
package gr.interamerican.bo2.impl.open.creation;

import gr.interamerican.bo2.creation.ObjectFactory;
import gr.interamerican.bo2.impl.open.po.AbstractBasePo;
import gr.interamerican.bo2.impl.open.po.BaseTestForAbstractBasePo;
import gr.interamerican.bo2.test.def.posamples.Customer;
import gr.interamerican.bo2.test.def.posamples.CustomerAddress;
import gr.interamerican.bo2.test.def.posamples.Invoice;
import gr.interamerican.bo2.test.def.posamples.InvoiceCustomer;
import gr.interamerican.bo2.test.def.posamples.InvoiceLine;
import gr.interamerican.bo2.test.def.posamples.InvoiceSubLine;
import gr.interamerican.bo2.test.def.posamples.SamplesFactory;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;


/**
 * Tests functionality of {@link AbstractBasePo} using implementations 
 * of the tested type created by the {@link Factory}.
 */
public class TestAbstractBasePo extends BaseTestForAbstractBasePo {

	
	/**
	 * Creates a new TestAbstractBasePo object. 
	 */
	public TestAbstractBasePo() {
		super(SamplesFactory.getFactored());
	}

	/**
	 * tests updatePo
	 */
	@Test
	@SuppressWarnings("unused")
	public void testPoCreation() {		
		ObjectFactory factory = this.getSamplesFactory().getFactory();
		
		Invoice invoice = factory.create(Invoice.class);
		InvoiceLine line = factory.create(InvoiceLine.class);
		InvoiceCustomer invoiceCust = factory.create(InvoiceCustomer.class);
		Customer cust = factory.create(Customer.class);
		CustomerAddress address = factory.create(CustomerAddress.class);
	}
	
	/**
	 * tests updatePo
	 */
	@Test
	@SuppressWarnings("unused")
	public void testTidy() {
		Invoice po = this.getSamplesFactory().sampleInvoiceFull(4);
		String invoiceNo = "X";
		po.getKey().setInvoiceNo(invoiceNo);
		po.tidy();
		Set<InvoiceLine> lines = po.getLines();
		for (InvoiceLine line : lines) {
			Assert.assertEquals(invoiceNo, line.getInvoiceNo());
			Set<InvoiceSubLine> sublines = line.getSubLines();
			for (InvoiceSubLine subline : sublines) {
				Assert.assertEquals(invoiceNo, line.getInvoiceNo());
			}
		}
	}
	


}
