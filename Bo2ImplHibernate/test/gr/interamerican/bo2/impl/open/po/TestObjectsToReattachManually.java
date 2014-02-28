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

import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.po.PoReattachAnalysis.PoReattachAnalysisResult;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.test.def.posamples.Customer;
import gr.interamerican.bo2.test.def.posamples.Invoice;
import gr.interamerican.bo2.test.def.posamples.SamplesFactory;
import gr.interamerican.bo2.test.scenarios.DeleteInvoiceData;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link PoReattachAnalysis}.
 */
public class TestObjectsToReattachManually {
	
	/**
	 * samples factory
	 */
	private SamplesFactory factory=SamplesFactory.getBo2Factory();
	
	/**
	 * Sample invoice no.
	 */
	private String invoiceNo="AAA"; //$NON-NLS-1$
	
	/**
	 * Sample customer no.
	 */
	private String customerNo = "No1"; //$NON-NLS-1$
	
	/**
	 * Sample invoice reference.
	 */
	private Invoice invoice;
	
	/**
	 * Clear data and setup a sample Invoice. 
	 * 
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	@Before
	public void before() throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				open(DeleteInvoiceData.class).execute();
				Customer c = factory.sampleCustomer("AAA"); //$NON-NLS-1$
				PersistenceWorker<Customer> pw = openPw(Customer.class);
				c.setCustomerNo(customerNo);
				c = pw.store(c);
				invoice = SamplesFactory.getBo2Factory().sampleInvoiceFull(1);
				invoice.setInvoiceNo(invoiceNo);
				invoice.getCustomer().setCustomer(c);
				PersistenceWorker<Invoice> ipw = openPw(Invoice.class);
				invoice = ipw.store(invoice);
			}
		}.execute();
	}
	
	/**
	 * Tests execute.
	 * 
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	@Test
	public void testExecute() throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				invoice = Factory.create(Invoice.class);
				invoice.setInvoiceNo(invoiceNo);
				PersistenceWorker<Invoice> ipw = openPw(Invoice.class);
				invoice = ipw.read(invoice);
				
			}
		}.execute();
		
		PoReattachAnalysisResult analysis = PoReattachAnalysis.get().execute((AbstractBasePo<?>) invoice);
		Assert.assertEquals(1, analysis.getPosToReattachManually().size());
		Assert.assertEquals(0, analysis.getTransientPos().size());
		
		/*
		 * Initialize the InvoiceCustomer.
		 */
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				invoice = Factory.create(Invoice.class);
				invoice.setInvoiceNo(invoiceNo);
				PersistenceWorker<Invoice> ipw = openPw(Invoice.class);
				invoice = ipw.read(invoice);
				invoice.getCustomer().getRoleId();
				invoice.getLines().size();
			}
		}.execute();
		
		analysis = PoReattachAnalysis.get().execute((AbstractBasePo<?>) invoice);
		Assert.assertEquals(1, analysis.getPosToReattachManually().size());
		Assert.assertEquals(0, analysis.getTransientPos().size());
		
		/*
		 * Initialize the Customer as well
		 */
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				PoUtils.reattach(invoice, getProvider());
				invoice = Factory.create(Invoice.class);
				invoice.setInvoiceNo(invoiceNo);
				PersistenceWorker<Invoice> ipw = openPw(Invoice.class);
				invoice = ipw.read(invoice);
				invoice.getCustomer().getCustomer().getCustomerName();
			}
		}.execute();
		
		analysis = PoReattachAnalysis.get().execute((AbstractBasePo<?>) invoice);
		Assert.assertEquals(1, analysis.getPosToReattachManually().size());
		Assert.assertEquals(0, analysis.getTransientPos().size());
		
		/*
		 * Deep copy the invoice
		 */
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				invoice = Factory.create(Invoice.class);
				invoice.setInvoiceNo(invoiceNo);
				PersistenceWorker<Invoice> ipw = openPw(Invoice.class);
				invoice = ipw.read(invoice);
				invoice = PoUtils.deepCopy(invoice);
			}
		}.execute();
		
		analysis = PoReattachAnalysis.get().execute((AbstractBasePo<?>) invoice);
		Assert.assertEquals(1, analysis.getPosToReattachManually().size());
		Assert.assertEquals(9, analysis.getTransientPos().size()); //1 I + 1 IL + 1 ISL + 2 IR + 3 ISR + 1IC 
		
		/*
		 * Deep copy the invoice and initialize the Customer.
		 */
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				invoice = Factory.create(Invoice.class);
				invoice.setInvoiceNo(invoiceNo);
				PersistenceWorker<Invoice> ipw = openPw(Invoice.class);
				invoice = ipw.read(invoice);
				invoice = PoUtils.deepCopy(invoice);
				invoice.getCustomer().getCustomer().getCustomerName();
			}
		}.execute();
		
		analysis = PoReattachAnalysis.get().execute((AbstractBasePo<?>) invoice);
		Assert.assertEquals(1, analysis.getPosToReattachManually().size());
		Assert.assertEquals(9, analysis.getTransientPos().size()); //1 I + 1 IL + 1 ISL + 2 IR + 3 ISR + 1IC 
		
	}

}
