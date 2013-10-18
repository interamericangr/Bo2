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

import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.po.PoUtils;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.test.def.posamples.Customer;
import gr.interamerican.bo2.test.def.posamples.Invoice;
import gr.interamerican.bo2.test.def.posamples.SamplesFactory;
import gr.interamerican.bo2.test.scenarios.DeleteInvoiceData;

import java.util.Date;

import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.proxy.HibernateProxy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests that demonstrate the cases where an initialized proxy
 * of entity B associated with a many-to-one relationship with no
 * cascade options with an entity A will not ontain a session when
 * reattaching A and will need special treatment.
 * 
 * This test case will use the following samples.
 * An Invoice has one InvoiceCustomer (one-to-one, cascade="all" association).
 * An InvoiceCustomer has a Customer (many-to-one, no cascade option association).
 * A Customer has a set of CustomerAddress (one-to-many, cascade="all,delete-orphan" association).
 */
public class TestProxySessionPropagation {
	
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
	 * Sample customer name.
	 */
	private String customerName = "cName"; //$NON-NLS-1$
	
	/**
	 * Sample invoice reference.
	 */
	private Invoice invoice;
	
	/**
	 * Placeholder for lastModified property of the Customer.
	 */
	private Date customerLastModified;
	
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
				invoice = SamplesFactory.getBo2Factory().sampleInvoiceFull(2);
				invoice.setInvoiceNo(invoiceNo);
				invoice.getCustomer().setCustomer(c);
				PersistenceWorker<Invoice> ipw = openPw(Invoice.class);
				invoice = ipw.store(invoice);
			}
		}.execute();
	}
	
	/**
	 * Demonstrates that an uninitialized Customer proxy will get
	 * a Session instance when the Invoice is reattached and it will
	 * be possible to initialize the CustomerAddress set. 
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testUninitializedProxiesReattachOK() 
	throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				invoice = Factory.create(Invoice.class);
				invoice.setInvoiceNo(invoiceNo);
				PersistenceWorker<Invoice> ipw = openPw(Invoice.class);
				invoice = ipw.read(invoice);
			}
		}.execute();
		
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				PoUtils.reattach(invoice, getProvider());
				
				Customer c = invoice.getCustomer().getCustomer();
				Assert.assertTrue(c instanceof HibernateProxy);
				HibernateProxy cProxy = (HibernateProxy) c;
				Assert.assertTrue(cProxy.getHibernateLazyInitializer().isUninitialized());
				Assert.assertNotNull(cProxy.getHibernateLazyInitializer().getSession());
			}
		}.execute();
		
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				PoUtils.reattach(invoice, getProvider());
				
				Customer c = invoice.getCustomer().getCustomer();
				Assert.assertTrue(c instanceof HibernateProxy);
				HibernateProxy cProxy = (HibernateProxy) c;
				Assert.assertTrue(cProxy.getHibernateLazyInitializer().isUninitialized());
				Assert.assertNotNull(cProxy.getHibernateLazyInitializer().getSession());
				
				c.getAddresses().size();
			}
		}.execute();
		
	}
	
	/**
	 * Demonstrates that an initialized Customer proxy will not get
	 * a Session instance when the Invoice is reattached and it will
	 * be impossible to initialize the CustomerAddress set. 
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testInitializedProxiesReattachOK() 
	throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				invoice = Factory.create(Invoice.class);
				invoice.setInvoiceNo(invoiceNo);
				PersistenceWorker<Invoice> ipw = openPw(Invoice.class);
				invoice = ipw.read(invoice);
			}
		}.execute();
		
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				PoUtils.reattach(invoice, getProvider());
				
				Customer c = invoice.getCustomer().getCustomer();
				Assert.assertTrue(c instanceof HibernateProxy);
				HibernateProxy cProxy = (HibernateProxy) c;
				Assert.assertTrue(cProxy.getHibernateLazyInitializer().isUninitialized());
				Assert.assertNotNull(cProxy.getHibernateLazyInitializer().getSession());
				c.getCustomerName();
				Assert.assertFalse(cProxy.getHibernateLazyInitializer().isUninitialized());
			}
		}.execute();
		
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				PoUtils.reattach(invoice, getProvider());
				
				Customer c = invoice.getCustomer().getCustomer();
				Assert.assertTrue(c instanceof HibernateProxy);
				HibernateProxy cProxy = (HibernateProxy) c;
				Assert.assertFalse(cProxy.getHibernateLazyInitializer().isUninitialized());
				Assert.assertNotNull(cProxy.getHibernateLazyInitializer().getSession());
				
				c.getAddresses().size();
			}
		}.execute();
		
	}
	
	/**
	 * Demonstrates that a many-to-one customer instance reattached with 
	 * session.buildLockRequest(LockOptions.NONE).lock(customer) will not
	 * have its modification record altered on the next session.flush, i.e.
	 * no SQL associated with this entity will be sent to the database. 
	 * 
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	@Test
	public void testManyToOneNoCascadeReattachedAreNotFlushed() 
	throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				invoice = Factory.create(Invoice.class);
				invoice.setInvoiceNo(invoiceNo);
				PersistenceWorker<Invoice> ipw = openPw(Invoice.class);
				invoice = ipw.read(invoice);
			}
		}.execute();
		
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				PoUtils.reattach(invoice, getProvider());
				
				Customer c = invoice.getCustomer().getCustomer();
				Assert.assertTrue(c instanceof HibernateProxy);
				HibernateProxy cProxy = (HibernateProxy) c;
				Assert.assertTrue(cProxy.getHibernateLazyInitializer().isUninitialized());
				Assert.assertNotNull(cProxy.getHibernateLazyInitializer().getSession());
				c.getCustomerName();
				Assert.assertFalse(cProxy.getHibernateLazyInitializer().isUninitialized());
			}
		}.execute();
		
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				PoUtils.reattach(invoice, getProvider());
				
				Customer c = invoice.getCustomer().getCustomer();
				Assert.assertTrue(c instanceof HibernateProxy);
				HibernateProxy cProxy = (HibernateProxy) c;
				Assert.assertFalse(cProxy.getHibernateLazyInitializer().isUninitialized());
				Assert.assertNotNull(cProxy.getHibernateLazyInitializer().getSession());

				customerLastModified = c.getLastModified();
				
				Session s = getProvider().getResource("LOCALDB", HibernateSessionProvider.class).getHibernateSession(); //$NON-NLS-1$
				s.buildLockRequest(LockOptions.NONE).lock(c);
				
				Assert.assertEquals(customerLastModified, c.getLastModified());
				
				Assert.assertNotNull(cProxy.getHibernateLazyInitializer().getSession());
				c.getAddresses().size();
				
				PersistenceWorker<Invoice> ipw = openPw(Invoice.class);
				ipw.store(invoice); //flush the session
			}
		}.execute();
		
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				PersistenceWorker<Customer> cpw = openPw(Customer.class);
				Customer c = Factory.create(Customer.class);
				c.setCustomerNo(customerNo);
				c = cpw.read(c);
				Assert.assertEquals(customerLastModified, c.getLastModified());
			}
		}.execute();
		
	}
	
	/**
	 * Demonstrates that a many-to-one customer instance reattached with 
	 * session.buildLockRequest(LockOptions.NONE).lock(customer) will have
	 * SQL sent on the next session.flush if it has been modified. 
	 * 
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	@Test
	public void testDirtyManyToOneNoCascadeReattachedFlush() 
	throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				invoice = Factory.create(Invoice.class);
				invoice.setInvoiceNo(invoiceNo);
				PersistenceWorker<Invoice> ipw = openPw(Invoice.class);
				invoice = ipw.read(invoice);
			}
		}.execute();
		
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				PoUtils.reattach(invoice, getProvider());

				Customer c = invoice.getCustomer().getCustomer();
				Assert.assertTrue(c instanceof HibernateProxy);
				HibernateProxy cProxy = (HibernateProxy) c;
				Assert.assertTrue(cProxy.getHibernateLazyInitializer().isUninitialized());
				Assert.assertNotNull(cProxy.getHibernateLazyInitializer().getSession());
				c.getCustomerName();
				Assert.assertFalse(cProxy.getHibernateLazyInitializer().isUninitialized());
			}
		}.execute();
		
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				PoUtils.reattach(invoice, getProvider());
				
				Customer c = invoice.getCustomer().getCustomer();
				Assert.assertTrue(c instanceof HibernateProxy);
				HibernateProxy cProxy = (HibernateProxy) c;
				Assert.assertFalse(cProxy.getHibernateLazyInitializer().isUninitialized());
				Assert.assertNotNull(cProxy.getHibernateLazyInitializer().getSession());

				customerLastModified = c.getLastModified();
				
				Session s = getProvider().getResource("LOCALDB", HibernateSessionProvider.class).getHibernateSession(); //$NON-NLS-1$
				s.buildLockRequest(LockOptions.NONE).lock(c);
				
				Assert.assertEquals(customerLastModified, c.getLastModified());
				
				Assert.assertNotNull(cProxy.getHibernateLazyInitializer().getSession());
				c.getAddresses().size();
				c.setCustomerName(customerName); //make Customer dirty
				
				PersistenceWorker<Invoice> ipw = openPw(Invoice.class);
				ipw.store(invoice); //flush the session
			}
		}.execute();
		
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				PersistenceWorker<Customer> cpw = openPw(Customer.class);
				Customer c = Factory.create(Customer.class);
				c.setCustomerNo(customerNo);
				c = cpw.read(c);
				Assert.assertEquals(customerName, c.getCustomerName());
			}
		}.execute();
		
	}
	
}
