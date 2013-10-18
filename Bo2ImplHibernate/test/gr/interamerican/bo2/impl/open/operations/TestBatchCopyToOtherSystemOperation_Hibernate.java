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

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link CopyToOtherSystemOperation} with Hibernate.
 */
public class TestBatchCopyToOtherSystemOperation_Hibernate {
	
	/**
	 * Invoice No 1.
	 */
	private static final String INVOICENO1 = "INV456781"; //$NON-NLS-1$
	
	/**
	 * Invoice No 2.
	 */
	private static final String INVOICENO2 = "INV456782"; //$NON-NLS-1$
	
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
	 * Sample Invoice 1
	 */
	private Invoice invoice1;
	
	/**
	 * Sample Invoice 2
	 */
	private Invoice invoice2;
	
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
	public TestBatchCopyToOtherSystemOperation_Hibernate() {
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
		invoice1 = factory.sampleInvoiceFull(3);
		invoice1.setInvoiceNo(INVOICENO1);
		
		invoice2 = factory.sampleInvoiceFull(2);
		invoice2.setInvoiceNo(INVOICENO2);
		
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
		crudInvoiceFrom.delete(invoice1);
		crudCustomerFrom.delete(customer);
		crudInvoiceTo.delete(invoice1);
		crudCustomerTo.delete(customer);
		
		crudCustomerTo.store(customer);
		customer = SamplesFactory.getBo2Factory().sampleCustomer("AAA"); //$NON-NLS-1$
		customer.setCustomerNo(CUSTOMERNO);
		crudCustomerFrom.store(customer);
		invoice1.getCustomer().setCustomer(customer);
		invoice1 = crudInvoiceFrom.store(invoice1);
		
		invoice2.getCustomer().setCustomer(customer);
		invoice2 = crudInvoiceFrom.store(invoice2);
	}
	
	/**
	 * Test tear down.
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@After
	public void teardown() throws UnexpectedException, DataException, LogicException {
		crudInvoiceFrom.delete(invoice1);
		crudInvoiceFrom.delete(invoice2);
		crudCustomerFrom.delete(customer);
		
		invoice1 = Factory.create(Invoice.class);
		invoice1.setInvoiceNo(INVOICENO1);
		crudInvoiceTo.delete(invoice1);
		
		invoice2 = Factory.create(Invoice.class);
		invoice2.setInvoiceNo(INVOICENO2);
		crudInvoiceTo.delete(invoice2);
		
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

				BatchCopyToOtherSystemOperation<Invoice, InvoiceKey> op = 
					new BatchCopyToOtherSystemOperation<Invoice, InvoiceKey>(
						Invoice.class, fromManager, toManager, prepareOp);
				
				Set<InvoiceKey> keys = new HashSet<InvoiceKey>();
				keys.add(invoice1.getKey());
				keys.add(invoice2.getKey());
				op.setKeys(keys);
				
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

