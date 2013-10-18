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
package gr.interamerican.bo2.impl.open.operations;

import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.hibernate.operations.PrepareForCopyToOtherSystem;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.impl.open.runtime.CrudCmd;
import gr.interamerican.bo2.impl.open.workers.AbstractResourceConsumer;
import gr.interamerican.bo2.test.def.posamples.Customer;
import gr.interamerican.bo2.test.def.posamples.Invoice;
import gr.interamerican.bo2.test.def.posamples.InvoiceKey;
import gr.interamerican.bo2.test.def.posamples.SamplesFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link CopyToOtherSystemOperation} with Hibernate.
 */
public class TestCopyToOtherSystemOperation_Hibernate {
	
	/**
	 * Invoice No.
	 */
	private static final String INVOICENO = "INV45678"; //$NON-NLS-1$
	
	/**
	 * Customer No.
	 */
	private static final String CUSTOMERNO = "CUST01234"; //$NON-NLS-1$
	
	/**
	 * From manager.
	 */
	private String fromManager = "LOCALDB"; //$NON-NLS-1$
	
	/**
	 * To manager.
	 */
	private String toManager = "OTHERDB"; //$NON-NLS-1$
	
	/**
	 * Sample Invoice
	 */
	private Invoice invoice;
	
	/**
	 * Sample Customer.
	 */
	private Customer customer;

	/**
	 * CRUD command for setup and teardown.
	 */
	CrudCmd<Invoice> crudInvoiceFrom;
	
	/**
	 * CRUD command for setup and teardown.
	 */
	CrudCmd<Invoice> crudInvoiceTo;
	
	/**
	 * CRUD command for setup and teardown.
	 */
	CrudCmd<Customer> crudCustomerFrom;
	
	/**
	 * CRUD command for setup and teardown.
	 */
	CrudCmd<Customer> crudCustomerTo;
	
	/**
	 * Creates a new TestPrepareForCopyToOtherSystem object. 
	 */
	public TestCopyToOtherSystemOperation_Hibernate() {
		PersistenceWorker<Invoice> fromInvoicePw = Factory.createPw(Invoice.class);
		AbstractResourceConsumer fromInvoiceRc = (AbstractResourceConsumer) fromInvoicePw;
		fromInvoiceRc.setManagerName(fromManager);
		crudInvoiceFrom = new CrudCmd<Invoice>(fromInvoicePw,true);
		
		PersistenceWorker<Invoice> toInvoicePw = Factory.createPw(Invoice.class);
		AbstractResourceConsumer toInvoiceRc = (AbstractResourceConsumer) toInvoicePw;
		toInvoiceRc.setManagerName(toManager);
		crudInvoiceTo = new CrudCmd<Invoice>(toInvoicePw,true);
		
		PersistenceWorker<Customer> fromCustomerPw = Factory.createPw(Customer.class);
		AbstractResourceConsumer fromCustomerRc = (AbstractResourceConsumer) fromCustomerPw;
		fromCustomerRc.setManagerName(fromManager);
		crudCustomerFrom = new CrudCmd<Customer>(fromCustomerPw,true);
		
		PersistenceWorker<Customer> toCustomerPw = Factory.createPw(Customer.class);
		AbstractResourceConsumer toCustomerRc = (AbstractResourceConsumer) toCustomerPw;
		toCustomerRc.setManagerName(toManager);
		crudCustomerTo = new CrudCmd<Customer>(toCustomerPw,true);
		
		SamplesFactory factory = SamplesFactory.getBo2Factory();
		invoice = factory.sampleInvoiceFull(3);
		invoice.setInvoiceNo(INVOICENO);
		
		customer = factory.sampleCustomer("AAA"); //$NON-NLS-1$
		customer.setCustomerNo(CUSTOMERNO);
	}
	
	/**
	 * Clear data and setup a sample Invoice. 
	 * 
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	@Before
	public void before() throws UnexpectedException, DataException, LogicException {
		crudInvoiceFrom.delete(invoice);
		crudCustomerFrom.delete(customer);
		crudInvoiceTo.delete(invoice);
		crudCustomerTo.delete(customer);
		
		crudCustomerTo.store(customer);
		customer = SamplesFactory.getBo2Factory().sampleCustomer("AAA"); //$NON-NLS-1$
		customer.setCustomerNo(CUSTOMERNO);
		crudCustomerFrom.store(customer);
		invoice.getCustomer().setCustomer(customer);
		invoice = crudInvoiceFrom.store(invoice);
	}
	
	/**
	 * Test tear down.
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@After
	public void teardown() throws UnexpectedException, DataException, LogicException {
		crudInvoiceFrom.delete(invoice);
		crudCustomerFrom.delete(customer);
		
		invoice = Factory.create(Invoice.class);
		invoice.setInvoiceNo(INVOICENO);
		crudInvoiceTo.delete(invoice);
		crudCustomerTo.delete(customer);
	}
	
	/**
	 * Test execute.
	 * @throws DataException 
	 * @throws LogicException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testExecute() throws LogicException, DataException, UnexpectedException {
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				PrepareForCopyToOtherSystem<Invoice> prepareOp = 
					new PrepareForCopyToOtherSystem<Invoice>(fromManager, toManager);

				CopyToOtherSystemOperation<Invoice, InvoiceKey> op = 
					new CopyToOtherSystemOperation<Invoice, InvoiceKey>(
						Invoice.class, fromManager, toManager, prepareOp);
				
				InvoiceKey key = Factory.create(InvoiceKey.class);
				key.setInvoiceNo(INVOICENO);
				op.setKey(key);
				
				op.init(getProvider());
				op.open();
				/*
				 * dummy manager
				 */
				op.setManagerName("FS"); //$NON-NLS-1$
				op.execute();
				op.close();
			}
		}.execute();
	}
	
}

