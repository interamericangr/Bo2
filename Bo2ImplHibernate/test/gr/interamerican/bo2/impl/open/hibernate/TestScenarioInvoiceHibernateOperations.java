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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gr.interamerican.bo2.arch.DetachStrategy;
import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.annotations.ManagerName;
import gr.interamerican.bo2.impl.open.annotations.Parameter;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.po.PoUtils;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.impl.open.runtime.Execute;
import gr.interamerican.bo2.impl.open.workers.AbstractPersistenceOperation;
import gr.interamerican.bo2.test.def.posamples.Customer;
import gr.interamerican.bo2.test.def.posamples.Invoice;
import gr.interamerican.bo2.test.def.posamples.InvoiceCustomer;
import gr.interamerican.bo2.test.def.posamples.InvoiceLine;
import gr.interamerican.bo2.test.def.posamples.InvoiceRule;
import gr.interamerican.bo2.test.def.posamples.InvoiceSubLine;
import gr.interamerican.bo2.test.def.posamples.InvoiceSubRule;
import gr.interamerican.bo2.test.def.posamples.SamplesFactory;
import gr.interamerican.bo2.test.scenarios.DeleteInvoiceData;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;
import gr.interamerican.bo2.utils.DateUtils;
import gr.interamerican.bo2.utils.Debug;
import gr.interamerican.bo2.utils.annotations.Child;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.LazyInitializationException;
import org.hibernate.LockOptions;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.proxy.HibernateProxy;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests operations of {@link GenericHibernatePersistenceWorker}
 * against a set of {@link PersistentObject} entities that have mainly 
 * composite keys.
 * 
 * The keys of child elements of the persistent objects contain the properties
 * of their parent's key.
 * 
 * TODO: Used an alternate factory with mapped implementations. Why???
 */
public class TestScenarioInvoiceHibernateOperations {

	/**
	 * samples factory
	 */
	private static SamplesFactory factory=SamplesFactory.getBo2Factory();
	
	/**
	 * Clear operation.
	 */
	private DeleteInvoiceData clear;
	
	/**
	 * Invoice being currently tested.
	 */
	private Invoice invoice;
	
	/**
	 * Invoice copy.
	 */
	private Invoice copy;
	
	/**
	 * InvoiceCustomer (one-to-one with Invoice).
	 */
	private InvoiceCustomer iCust;
	
	/**
	 * Customer (many-to-one with InvoiceCustomer).
	 */
	private Customer customer;
	
	/**
	 * Customer no.
	 */
	private String customerNo = "C0"; //$NON-NLS-1$
	
	/**
	 * Customer taxId.
	 */
	private String taxId = "taxId0"; //$NON-NLS-1$
	
	/**
	 * invoiceNo
	 */
	String invoiceNo = "AA2"; //$NON-NLS-1$
	
	/**
	 * copy invoiceNo
	 */
	String copyInvoiceNo = "BB2"; //$NON-NLS-1$
	
	/**
	 * Creates a new TestScenarioInvoiceHibernateOperations object. 
	 */
	public TestScenarioInvoiceHibernateOperations() {
		super();
		clear = new DeleteInvoiceData();
	}
	
	
	
	/**
	 * before each test.
	 * 
	 * @throws DataException
	 * @throws LogicException
	 * @throws UnexpectedException 
	 */
	@Before
	public void beforeEachTest() 
	throws DataException, LogicException, UnexpectedException {
		Execute.transactional(clear);
		Debug.setActiveModule(this);		
	}
	
	/**
	 * after each test.
	 */
	@After
	public void afterEachTest() {
		Debug.resetActiveModule();
	}
	
	/**
	 * Test case for checking store and read.
	 * 
	 * <li>store
	 * <li>compare initial object with saved version. 
	 * <li>commit
	 * <li>read
	 * <li>compare initial object with read version.
	 * 
	 * @throws DataException
	 * @throws LogicException
	 * @throws UnexpectedException 
	 */
	@Test
	public void testStoreAndRead() 
	throws DataException, LogicException, UnexpectedException {
		final String userid = "X"; //$NON-NLS-1$
		UtilityForBo2Test.setCurrentUser(userid);
		
		
		/* Store test scenario */
		AbstractPersistenceOperation<Invoice> storeOp = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override 
			public void execute() throws LogicException, DataException {
				po = pw.store(po);
				assertTrue(PoUtils.deepEquals(invoice, po));
				
				assertEquals(userid, po.getLastModifiedBy());
				for (InvoiceLine line : po.getLines()) {
					assertEquals(userid, line.getLastModifiedBy());
					for (InvoiceSubLine subline : line.getSubLines()) {
						assertEquals(userid, subline.getLastModifiedBy());
					}
				}
			}
		};
		
		/* Read after store test scenario */
		AbstractPersistenceOperation<Invoice> readOp = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				po = pw.read(po);
				assertTrue(PoUtils.deepEquals(invoice, po));
			}
		};
		
		invoice = factory.sampleInvoiceFull(4);
		invoice.setInvoiceNo(invoiceNo);		
		storeOp.setPo(invoice);
		Execute.transactional(storeOp);
		
		invoice = factory.sampleInvoiceFull(4);
		invoice.setInvoiceNo(invoiceNo);
		invoice.tidy();
		
		readOp.setPo(invoice);
		Execute.transactional(readOp);
	}
	
	/**
	 * Test case for checking that collections are indeed lazily initialized.
	 * 
	 * @throws DataException
	 * @throws LogicException
	 * @throws UnexpectedException 
	 */
	@Test (expected = LazyInitializationException.class)
	public void testRead_LazyInitializationOfCollections() 
	throws DataException, LogicException, UnexpectedException {
		
		/* first, store the samples */
		AbstractPersistenceOperation<Invoice> storeOp = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				po = pw.store(po);
			}
		};
		invoice = factory.sampleInvoiceFull(4);
		invoice.setInvoiceNo(invoiceNo);
		storeOp.setPo(invoice);
		Execute.transactional(storeOp);
		
		/* Now read them */
		AbstractPersistenceOperation<Invoice> readOp = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override 
			public void execute() throws LogicException, DataException {
				invoice = pw.read(po);
			}
		};
		invoice = Factory.create(Invoice.class);
		invoice.setInvoiceNo(invoiceNo);
		readOp.setPo(invoice);
		Execute.transactional(readOp);
		
		/* 
		 * now attempt to read elements of a proxied set. Note that this
		 * will throw an exception even if it is executed in a transaction.
		 */
		Set<InvoiceLine> lines = invoice.getLines();
		for(InvoiceLine line : lines) {
			assertNotNull(line.getSubLines());
			assertFalse(Hibernate.isInitialized(line.getSubLines()));
			line.getSubLines().size(); // throws LazyInitializationException
		}
	}
	
	/**
	 * Test case for checking that one-to-one associations are indeed
	 * lazily initialized.
	 * 
	 * The only way to lazily initialize a one-to-one association object
	 * is to specify lazy="proxy" AND constrained="true" in the mapping.
	 * The latter means that the object cannot be null (which is the case
	 * for {@link Child} POs). If we do not specify constrained="true" hibernate
	 * does not know whether to put a null value or a proxy, so it checks
	 * the database and therefore populates the object anyway. 
	 * 
	 * @throws DataException
	 * @throws LogicException
	 * @throws UnexpectedException 
	 */
	@Test
	public void testRead_OneToOne() 
	throws DataException, LogicException, UnexpectedException {
		
		/* first, store the samples */
		AbstractPersistenceOperation<Invoice> storeOp = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				po = pw.store(po);
			}
		};
		invoice = factory.sampleInvoiceFull(4);
		invoice.setInvoiceNo(invoiceNo);
		storeOp.setPo(invoice);
		Execute.transactional(storeOp);
		
		/* Read InvoiceSubLine scenario */
		AbstractPersistenceOperation<Invoice> readOp = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override 
			public void execute() throws LogicException, DataException {
				invoice = pw.read(po);
			}
		};
		invoice = Factory.create(Invoice.class);
		invoice.setInvoiceNo(invoiceNo);
		readOp.setPo(invoice);
		Execute.transactional(readOp);

		/*
		 * Eager fetch of one-to-one
		 */
		InvoiceCustomer cust = invoice.getCustomer();
		assertNotNull(cust);
		assertTrue(Hibernate.isInitialized(cust));
		cust.getRoleId();
	}
	
	/**
	 * Test case for checking delete.
	 * 
	 * <li>store (implicitly reads)
	 * <li>delete
	 * <li>read (expecting PoNotFoundException)
	 * <li>transaction gets rolled back
	 * 
	 * @throws DataException
	 * @throws LogicException
	 * @throws UnexpectedException 
	 */
	@Test (expected=PoNotFoundException.class)
	public void testDelete_Fails() 
	throws DataException, LogicException, UnexpectedException {
		
		/* Store and then delete. Expecting read to throw PoNotFound */
		AbstractPersistenceOperation<Invoice> test1 = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				po = pw.store(po);
				po = pw.delete(po);
				/* throws PoNotFoundException, transaction gets rolled back. */
				po = pw.read(po); 
			}
		};
		invoice = factory.sampleInvoiceFull(4);
		invoice.setInvoiceNo(invoiceNo);
		test1.setPo(invoice);
		Execute.transactional(test1);
	}
	
	/**
	 * Test case for checking delete.
	 * 
	 * <li>store
	 * <li>commit
	 * <li>read
	 * <li>delete
	 * <li>commit
	 * <li>read
	 * <li>catch the PoNotFoundException
	 * 
	 * @throws DataException
	 * @throws LogicException
	 * @throws UnexpectedException 
	 */
	@Test
	public void testDelete_Succeeds() 
	throws DataException, LogicException, UnexpectedException {
		
		/* first, store the samples */
		AbstractPersistenceOperation<Invoice> storeOp = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				po = pw.store(po);
			}
		};
		invoice = factory.sampleInvoiceFull(4);
		invoice.setInvoiceNo(invoiceNo);
		storeOp.setPo(invoice);
		Execute.transactional(storeOp);
		
		/* read and delete */
		AbstractPersistenceOperation<Invoice> deleteOp = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				po = pw.read(po);
				po = pw.delete(po);
			}
		};
		invoice = Factory.create(Invoice.class);
		invoice.setInvoiceNo(invoiceNo);
		deleteOp.setPo(invoice);
		Execute.transactional(deleteOp);
		
		/* confirm */
		AbstractPersistenceOperation<Invoice> readOp = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				boolean caught = false;
				try {
					po = pw.read(po);
				} catch (PoNotFoundException pnfe) {
					assertNotNull(pnfe);
					caught = true;
				} finally {
					assertTrue(caught);
				}
			}
		};
		invoice = Factory.create(Invoice.class);
		invoice.setInvoiceNo(invoiceNo);
		readOp.setPo(invoice);
		Execute.transactional(readOp);
	}
	
	/**
	 * Test case for checking update.
	 * 
	 * <li>store
	 * <li>read
	 * <li>update
	 * <li>read
	 * <li>commit
	 * <li>read
	 * 
	 * @throws DataException
	 * @throws LogicException
	 * @throws UnexpectedException 
	 */
	@Test
	@SuppressWarnings("nls")
	public void testUpdate() 
	throws DataException, LogicException, UnexpectedException {
		
		Debug.setActiveModule(this);
		
		/* Store,read,add line,update,remove 2 lines, remove 1 subLine, read test scenario */
		AbstractPersistenceOperation<Invoice> test1 = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override 
			public void execute() throws LogicException, DataException {
				/* store */
				po = pw.store(po);
				
				/* compare initial with stored */
				assertTrue(PoUtils.deepEquals(invoice, po));

				/* add one line */
				po.setInvoiceDate(DateUtils.today());
				InvoiceLine line5 = factory.sampleInvoiceLine(5);				
				po.getLines().add(line5);
				invoice = po;
				
				/* update */
				po = pw.update(po);
				
				/* compare updated with initial */
				assertTrue(PoUtils.deepEquals(invoice, po));
				
				/* read */
				po = pw.read(po);
				
				/* compare read with initial */
				assertTrue(PoUtils.deepEquals(invoice, po));
				assertEquals(5, po.getLines().size());
				
				/* remove two lines */
				InvoiceLine line1 = factory.sampleInvoiceLine(1);
				line1.setInvoiceNo(po.getInvoiceNo());
				po.getLines().remove(line1);
				po.getLines().remove(line5);
				assertEquals(3, po.getLines().size());
				invoice = po;
				
				/* update */
				po = pw.update(po);
				
				/* compare updated with initial */
				assertTrue(PoUtils.deepEquals(invoice, po));
				
				/* remove a subLine */
				InvoiceSubLine sLine = factory.sampleInvoiceSubLine(2);
				sLine.setInvoiceNo(invoiceNo);
				sLine.setLineNo(2);
				for(InvoiceLine line : po.getLines()) {
					if(line.getLineNo()==2) {
						System.err.println("removed sline");
						line.getSubLines().remove(sLine);
						assertEquals(0, line.getSubLines().size());
					}
				}
				invoice = po;
				po = pw.update(po);
				
				assertTrue(PoUtils.deepEquals(invoice, po));
				
				for(InvoiceLine line : po.getLines()) {
					if(line.getLineNo()==2) {
						System.err.println("removed sline");
						line.getSubLines().remove(sLine);
						assertEquals(0, line.getSubLines().size());
					}
				}
				/* read */
				po = pw.read(po);
				
				/* compare read with initial */
				assertTrue(PoUtils.deepEquals(invoice, po));
				assertEquals(3, po.getLines().size());
				invoice = null;
			}
		};
		invoice = factory.sampleInvoiceFull(4);
		invoice.setInvoiceNo(invoiceNo);
		test1.setPo(invoice);
		Execute.transactional(clear, test1);
	}
	
	/**
	 * Tests many to one relationships and deep-copy on POs that
	 * have many-to-one relationships.
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 * 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testManyToOne() 
	throws DataException, LogicException, UnexpectedException {
		
		/* store the samples, store a customer and setup the sample invoice with him */
		new AbstractBo2RuntimeCmd() {
			@Override public void work() 
			throws LogicException, DataException, InitializationException, UnexpectedException {
				invoice = factory.sampleInvoiceFull(4);
				invoice.setInvoiceNo(invoiceNo);
				PersistenceWorker<Invoice> ipw = openPw(Invoice.class);
				invoice = ipw.store(invoice);
				
				Customer cust = factory.sampleCustomer("tid0");
				cust.setCustomerNo("cust001");
				PersistenceWorker<Customer> cpw = openPw(Customer.class);
				cust = cpw.store(cust);
				
				invoice.getCustomer().setCustomer(cust);
				ipw.update(invoice);
				invoice = null;
			}
		}.execute();
		
		/* read the invoice and get the invoiceCustomer */
		new AbstractBo2RuntimeCmd() {
			@Override public void work() 
			throws LogicException, DataException, InitializationException, UnexpectedException {
				invoice = Factory.create(Invoice.class);
				invoice.setInvoiceNo(invoiceNo);
				PersistenceWorker<Invoice> ipw = openPw(Invoice.class);
				invoice = ipw.read(invoice);
				invoice.getCustomer();
			}
		}.execute();
		
		/* read the customer - a LazyInitializationException is thrown
		 * The exception would be thrown, even if the following code was
		 * running inside an active transaction.
		 */
		boolean caught = false;
		try {
			invoice.getCustomer().getCustomer().getCustomerName();
		} catch(LazyInitializationException lie) {
			assertNotNull(lie);
			caught = true;
		} finally {
			assertTrue(caught);
		}

		/* deep copy invoice */
		new AbstractBo2RuntimeCmd() {
			@Override public void work() 
			throws LogicException, DataException, InitializationException, UnexpectedException {
				invoice = Factory.create(Invoice.class);
				invoice.setInvoiceNo(invoiceNo);
				PersistenceWorker<Invoice> ipw = openPw(Invoice.class);
				invoice = ipw.read(invoice);
				
				copy = PoUtils.deepCopy(invoice);
				assertTrue(PoUtils.deepEquals(invoice, copy));
				
				copy.setInvoiceNo(copyInvoiceNo);
				copy = ipw.store(copy);
			}
		}.execute();
	}
	
	/**
	 * Tests duplicating a PO with PoUtils.deepCopy 
	 * 
	 * Note that copy MUST be performed in the same transaction
	 * as the reading of the original. If this is not the case,
	 * the method will attempt to initialize proxies of a detached
	 * object, which throws an Exception.
	 * 
	 * @throws DataException
	 * @throws LogicException
	 * @throws UnexpectedException 
	 */
	@Test
	@SuppressWarnings("nls")
	public void testReadCopyStore() 
	throws DataException, LogicException, UnexpectedException {
		
		UtilityForBo2Test.setCurrentUser("who"); //current user.

		/* Store test scenario */
		AbstractPersistenceOperation<Invoice> test1 = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				po = pw.store(po);
			}
		};
		invoice = Factory.create(Invoice.class);
		invoice.setInvoiceDate(new Date());
		InvoiceLine line = Factory.create(InvoiceLine.class);
		line.setLineNo(1);
		line.setAmount(100.d);
		invoice.getLines().add(line);
		invoice.setInvoiceNo(invoiceNo);
		InvoiceCustomer iCustomer = Factory.create(InvoiceCustomer.class);
		iCustomer.setRoleId(1);
		invoice.setCustomer(iCustomer);
		test1.setPo(invoice);
		Execute.transactional(test1);
		
		/* Read after store and copy */
		AbstractPersistenceOperation<Invoice> test2 = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				po = pw.read(po);
				invoice = po;
				copy = PoUtils.deepCopy(po);
			}
		};
		test2.setPo(invoice);
		Execute.transactional(test2);
		assertNotNull(copy);
		assertTrue(PoUtils.deepEquals(copy, invoice));
		
		/* store copy test scenario */
		AbstractPersistenceOperation<Invoice> test3 = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				copy = pw.store(po);
			}
		};
		copy.setInvoiceNo(copyInvoiceNo);
		
		
		test3.setPo(copy);
		Execute.transactional(test3);
		assertNotNull(copy);
		assertFalse(copy.getKey().equals(invoice.getKey()));
		
		/* delete copy */
		AbstractPersistenceOperation<Invoice> test4 = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				po = pw.delete(po);
			}
		};
		test4.setPo(copy);
		Execute.transactional(test4);
		invoice = null;
	}
	
	/**
	 * Tests re-attaching a detached UNMODIFIED instance.
	 * 
	 * <li>transaction1: store
	 * <li>transaction2: read
	 * <li>transaction3: re-attach, modify and persist
	 * <li>transaction4: confirm operations
	 * 
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	@Test
	public void testReattachToSession_unmodifiedInstance() 
	throws UnexpectedException, DataException, LogicException{
		
		/* first, store the samples */
		AbstractPersistenceOperation<Invoice> storeOp = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				po = pw.store(po);
			}
		};
		invoice = factory.sampleInvoiceFull(4);
		invoice.setInvoiceNo(invoiceNo);
		storeOp.setPo(invoice);
		Execute.transactional(storeOp);
		
		/* Now read them */
		AbstractPersistenceOperation<Invoice> readOp = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				invoice = pw.read(po);
			}
		};
		invoice = Factory.create(Invoice.class);
		invoice.setInvoiceNo(invoiceNo);
		readOp.setPo(invoice);
		Execute.transactional(readOp);
		
		AbstractPersistenceOperation<Invoice> updateOp = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				/* 
				 * re-attach with lock()
				 * This will only work if the detached instances are unmodified.
				 * session.update() has the same effect, but is unnecessary.
				 */
				try {
					Session session = getProvider().getResource("LOCALDB",HibernateSessionProvider.class).getHibernateSession(); //$NON-NLS-1$
					session.buildLockRequest(LockOptions.NONE).lock(invoice);
				} catch (InitializationException e) {
					fail(e.toString());
				}
				
				/* add a subLine to each line. Note that subLines are proxied */
				Set<InvoiceLine> lines = invoice.getLines();
				for(InvoiceLine line : lines) {
					assertNotNull(line.getSubLines());
					assertFalse(Hibernate.isInitialized(line.getSubLines()));
					line.getSubLines().add(factory.sampleInvoiceSubLine(line.getLineNo()+1));
				}
				
				/* persist the updated invoice */
				invoice = pw.update(invoice);
			}
		};
		updateOp.setPo(invoice);
		Execute.transactional(updateOp);
		
		/* confirm results, now each line has 2 subLines */
		AbstractPersistenceOperation<Invoice> readOp2 = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				invoice = pw.read(po);
				Set<InvoiceLine> lines = invoice.getLines();
				for(InvoiceLine line : lines) {
					assertTrue(line.getSubLines().size() == 2);
				}
			}
		};
		invoice = Factory.create(Invoice.class);
		invoice.setInvoiceNo(invoiceNo);
		readOp2.setPo(invoice);
		Execute.transactional(readOp2);
	}
	
	/**
	 * Tests re-attaching an object graph containing uninitialized HibernateProxy instances
	 * after serializing and de-serializing it with the standard Java serialization
	 * mechanism. After re-attaching the proxies are initialized.
	 * 
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	@Test
	public void testReattachToSession_deSerializedInstance() 
	throws UnexpectedException, DataException, LogicException, IOException, ClassNotFoundException {
		
		/* first, store the samples - including a Customer */
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException,
			DataException, InitializationException, UnexpectedException {
				customer = factory.sampleCustomer(taxId);
				customer.setCustomerNo(customerNo);
				PersistenceWorker<Customer> cpw = openPw(Customer.class);
				cpw.store(customer);
				
				invoice = factory.sampleInvoiceFull(4);
				invoice.setInvoiceNo(invoiceNo);
				invoice.getCustomer().setCustomer(customer);
				PersistenceWorker<Invoice> ipw = openPw(Invoice.class);
				ipw.store(invoice);
			}
		}.execute();
		
		new AbstractBo2RuntimeCmd() {
			@Override
			public void work() throws LogicException, DataException, InitializationException, UnexpectedException {
				invoice = Factory.create(Invoice.class);
				invoice.setInvoiceNo(invoiceNo);
				invoice = openPw(Invoice.class).read(invoice);
			}
		}.execute();
		Customer c = invoice.getCustomer().getCustomer();
		Assert.assertTrue(c instanceof HibernateProxy);
		Assert.assertTrue(((HibernateProxy)c).getHibernateLazyInitializer().isUninitialized());
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(invoice);
        oos.flush();
        baos.flush();
        oos.close();
        baos.close();
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        invoice = (Invoice) ois.readObject();
		
        new AbstractBo2RuntimeCmd() {
			@Override
			public void work() throws LogicException, DataException, InitializationException, UnexpectedException {
				PoUtils.reattach(invoice, getProvider());
				Assert.assertEquals(taxId, invoice.getCustomer().getCustomer().getTaxId());
			}
		}.execute();
		
	}
	
	/**
	 * Tests re-attaching an object graph of a deep copied object. Many-to-one associations
	 * should be reattached for reading.
	 * 
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	@Test
	public void testReattachToSession_deepCopiedInstance() 
	throws UnexpectedException, DataException, LogicException {
		
		/* first, store the samples - including a Customer */
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException,
			DataException, InitializationException, UnexpectedException {
				customer = factory.sampleCustomer(taxId);
				customer.setCustomerNo(customerNo);
				PersistenceWorker<Customer> cpw = openPw(Customer.class);
				cpw.store(customer);
				
				invoice = factory.sampleInvoiceFull(4);
				invoice.setInvoiceNo(invoiceNo);
				invoice.getCustomer().setCustomer(customer);
				PersistenceWorker<Invoice> ipw = openPw(Invoice.class);
				ipw.store(invoice);
			}
		}.execute();
		
		new AbstractBo2RuntimeCmd() {
			@Override
			public void work() throws LogicException, DataException, InitializationException, UnexpectedException {
				invoice = Factory.create(Invoice.class);
				invoice.setInvoiceNo(invoiceNo);
				invoice = openPw(Invoice.class).read(invoice);
				invoice = PoUtils.deepCopy(invoice);
			}
		}.execute();
		
		Customer c = invoice.getCustomer().getCustomer();
		Assert.assertNull(invoice.getLastModified());
		Assert.assertTrue(HibernateBo2Utils.isTransient(invoice));
		Assert.assertTrue(c instanceof HibernateProxy);
		Assert.assertTrue(((HibernateProxy)c).getHibernateLazyInitializer().isUninitialized());
		
        new AbstractBo2RuntimeCmd() {
			@Override
			public void work() throws LogicException, DataException, InitializationException, UnexpectedException {
				PoUtils.reattach(invoice, getProvider());
				Assert.assertEquals(taxId, invoice.getCustomer().getCustomer().getTaxId());
			}
		}.execute();
		
	}
	
	/**
	 * Tests re-attaching a detached MODIFIED instance.
	 * 
	 * <li>transaction1: store
	 * <li>transaction2: read
	 * <li>transaction3: modify, re-attach, modify and persist
	 * <li>transaction4: confirm operations
	 * 
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	@Test
	public void testReattachToSession_modifiedInstance() 
	throws UnexpectedException, DataException, LogicException {
		
		/* first, store the samples */
		AbstractPersistenceOperation<Invoice> storeOp = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				po = pw.store(po);
			}
		};
		invoice = factory.sampleInvoiceFull(4);
		invoice.setInvoiceNo(invoiceNo);
		storeOp.setPo(invoice);
		Execute.transactional(storeOp);
		
		/* Now read them */
		AbstractPersistenceOperation<Invoice> readOp = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				invoice = pw.read(po);
				invoice.getLines().size();
			}
		};
		invoice = Factory.create(Invoice.class);
		invoice.setInvoiceNo(invoiceNo);
		readOp.setPo(invoice);
		Execute.transactional(readOp);
		
		/* add a new line with a subLine */
		InvoiceLine newLine = factory.sampleInvoiceLine(5);
		newLine.getSubLines().add(factory.sampleInvoiceSubLine(newLine.getLineNo()));
		invoice.getLines().add(newLine);
		
		AbstractPersistenceOperation<Invoice> updateOp = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				/* 
				 * re-attach with session.update()
				 * merge() has the same effect, but we call merge in worker.update() anyway.
				 */
				try {
					Session session = getProvider().getResource("LOCALDB",HibernateSessionProvider.class).getHibernateSession(); //$NON-NLS-1$
					session.update(invoice);
				} catch (InitializationException e) {
					fail(e.toString());
				}
				
				/* add a subLine to each line. */
				Set<InvoiceLine> lines = invoice.getLines();
				assertTrue(lines.size()==5);
				for(InvoiceLine line : lines) {
					assertNotNull(line.getSubLines());
					line.getSubLines().add(factory.sampleInvoiceSubLine(line.getLineNo()+1));
				}
				
				/* persist the updated invoice */
				invoice = pw.update(invoice);
			}
		};
		updateOp.setPo(invoice);
		Execute.transactional(updateOp);
		
		/* confirm results, now each line has 2 subLines */
		AbstractPersistenceOperation<Invoice> readOp2 = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				invoice = pw.read(po);
				Set<InvoiceLine> lines = invoice.getLines();
				assertTrue(lines.size()==5);
				for(InvoiceLine line : lines) {
					assertTrue(line.getSubLines().size() == 2);
				}
			}
		};
		invoice = Factory.create(Invoice.class);
		invoice.setInvoiceNo(invoiceNo);
		readOp2.setPo(invoice);
		Execute.transactional(readOp2);
	}
	
	/**
	 * Tests re-attaching a detached MODIFIED instance and then flushing.
	 * The modifications that the object had before the updated are persisted
	 * on flush.
	 * 
	 * <li>transaction1: store
	 * <li>transaction2: read
	 * <li>transaction3: modify, re-attach, flush
	 * <li>transaction4: confirm operations
	 * 
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	@Test
	public void testReattachToSession_modifiedInstanceAndFlushManually() 
	throws UnexpectedException, DataException, LogicException {
		
		/* first, store the samples */
		AbstractPersistenceOperation<Invoice> storeOp = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				po = pw.store(po);
			}
		};
		invoice = factory.sampleInvoiceFull(4);
		invoice.setInvoiceNo(invoiceNo);
		storeOp.setPo(invoice);
		Execute.transactional(storeOp);
		
		/* Now read them */
		AbstractPersistenceOperation<Invoice> readOp = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				invoice = pw.read(po);
				invoice.getLines().size();
			}
		};
		invoice = Factory.create(Invoice.class);
		invoice.setInvoiceNo(invoiceNo);
		readOp.setPo(invoice);
		Execute.transactional(readOp);
		
		/* add a new line with a subLine */
		InvoiceLine newLine = factory.sampleInvoiceLine(5);
		newLine.getSubLines().add(factory.sampleInvoiceSubLine(newLine.getLineNo()));
		invoice.getLines().add(newLine);
		
		AbstractPersistenceOperation<Invoice> updateOp = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				/* 
				 * re-attach with session.update()
				 * and flush() manually (call tidy() first to fix keys).
				 */
				try {
					Session session = getProvider().getResource("LOCALDB",HibernateSessionProvider.class).getHibernateSession(); //$NON-NLS-1$
					session.update(invoice);
					invoice.tidy();
					session.flush();
				} catch (InitializationException e) {
					fail(e.toString());
				}
			}
		};
		updateOp.setPo(invoice);
		Execute.transactional(updateOp);
		
		/* confirm results, now each line has 1 subLine */
		AbstractPersistenceOperation<Invoice> readOp2 = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				invoice = pw.read(po);
				Set<InvoiceLine> lines = invoice.getLines();
				assertTrue(lines.size()==5);
				for(InvoiceLine line : lines) {
					assertTrue(line.getSubLines().size() == 1);
				}
			}
		};
		invoice = Factory.create(Invoice.class);
		invoice.setInvoiceNo(invoiceNo);
		readOp2.setPo(invoice);
		Execute.transactional(readOp2);
	}
	
	/**
	 * Tests evicting an attached instance.
	 * 
	 * <li>transaction1: store
	 * <li>transaction2: read
	 * <li>transaction3: re-attach, modify, evict and persist
	 * <li>transaction4: confirm operations
	 * 
	 * @throws DataException
	 * @throws LogicException
	 * @throws UnexpectedException 
	 */
	@Test
	public void testEvictingFromSession() 
	throws DataException, LogicException, UnexpectedException {
		
		/* first, store the samples */
		AbstractPersistenceOperation<Invoice> storeOp = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				po = pw.store(po);
			}
		};
		invoice = factory.sampleInvoiceFull(4);
		invoice.setInvoiceNo(invoiceNo);
		storeOp.setPo(invoice);
		Execute.transactional(storeOp);
		invoice = storeOp.getPo();
		copy = factory.sampleInvoiceFull(4);
		copy.setInvoiceNo(copyInvoiceNo);
		storeOp.setPo(copy);
		Execute.transactional(storeOp);
		copy = storeOp.getPo();
		
		/* re-attach to session, modify and update */
		AbstractPersistenceOperation<Invoice> updateOp = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				/* 
				 * re-attach with lock()
				 * This will only work if the detached instances are unmodified.
				 */
				try {
					Session session = getProvider().getResource("LOCALDB",HibernateSessionProvider.class).getHibernateSession(); //$NON-NLS-1$
					session.buildLockRequest(LockOptions.NONE).lock(invoice);
					session.buildLockRequest(LockOptions.NONE).lock(copy);
				} catch (InitializationException e) {
					fail(e.toString());
				}
				
				/* now that the objects are re-attached I can modify them */
				invoice.getLines().add(factory.sampleInvoiceLine(5));
				copy.getLines().add(factory.sampleInvoiceLine(5));
				
				/*
				 * evicting copy. Even though I will only explicitly update invoice, flush() will
				 * also send SQL for the modifications of copy to the connection. If I do not want
				 * to persist the modifications of copy, I have to evict it from the session.
				 */
				try {
					Session session = getProvider().getResource("LOCALDB",HibernateSessionProvider.class).getHibernateSession(); //$NON-NLS-1$
					session.evict(copy);
				} catch (InitializationException e) {
					fail(e.toString());
				}
				
				invoice = pw.update(invoice);
			}
		};
		Execute.transactional(updateOp);
		
		/* confirm results, now invoice has 5 lines and copy only 4 */
		AbstractPersistenceOperation<Invoice> readOp2 = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				po = pw.read(po);
				po.getLines().size();
			}
		};
		invoice = Factory.create(Invoice.class);
		invoice.setInvoiceNo(invoiceNo);
		readOp2.setPo(invoice);
		Execute.transactional(readOp2);
		invoice = readOp2.getPo();
		assertTrue(invoice.getLines().size()==5);
		
		copy = Factory.create(Invoice.class);
		copy.setInvoiceNo(copyInvoiceNo);
		readOp2.setPo(copy);
		Execute.transactional(readOp2);
		copy = readOp2.getPo();
		assertTrue(copy.getLines().size()==4);
	}
	
	
	/**
	 * Tests that when re-attaching a detached instance other references of
	 * objects owned by this instance to be re-attached as well.
	 * 
	 * @throws DataException
	 * @throws LogicException
	 * @throws UnexpectedException 
	 */
	@Test
	public void testReattachInvoiceReattachesChildren() 
	throws DataException, LogicException, UnexpectedException {
		
		/* first, store the samples - including a Customer */
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException,
			DataException, InitializationException, UnexpectedException {
				customer = factory.sampleCustomer(taxId);
				customer.setCustomerNo(customerNo);
				PersistenceWorker<Customer> cpw = openPw(Customer.class);
				cpw.store(customer);
				
				invoice = factory.sampleInvoiceFull(4);
				invoice.setInvoiceNo(invoiceNo);
				invoice.getCustomer().setCustomer(customer);
				PersistenceWorker<Invoice> ipw = openPw(Invoice.class);
				ipw.store(invoice);
			}
		}.execute();
		
		/* Now read the invoice and keep a reference to the InvoiceCustomer and the Customer. */
		AbstractPersistenceOperation<Invoice> readOp = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				invoice = pw.read(po);
				iCust = invoice.getCustomer();
				customer = iCust.getCustomer();
				Assert.assertNotNull(iCust);
				Assert.assertNotNull(customer);
			}
		};
		invoice = Factory.create(Invoice.class);
		invoice.setInvoiceNo(invoiceNo);
		readOp.setPo(invoice);
		Execute.transactional(readOp);
		
		/* re-attach to session, check that the InvoiceCustomer and customer
		   references are consistent with the re-attached Invoice. */
		AbstractPersistenceOperation<Invoice> updateOp = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				/* 
				 * re-attach with session.update()
				 */
				try {
					HibernateSessionProvider hib = 
						getProvider().getResource("LOCALDB", HibernateSessionProvider.class); //$NON-NLS-1$
					Session session = hib.getHibernateSession();
					session.update(invoice);
					Assert.assertTrue(session.contains(iCust));
					Assert.assertTrue(session.contains(customer));
				} catch (InitializationException e) {
					fail(e.toString());
				}
				
				Assert.assertSame(iCust, invoice.getCustomer());
				Assert.assertSame(customer, invoice.getCustomer().getCustomer());
			}
		};
		updateOp.setPo(invoice);
		Execute.transactional(updateOp);
		
	}
	
	/**
	 * Tests that when re-attaching a detached instance transient objects
	 * on its graph are not attached.
	 * 
	 * @throws DataException
	 * @throws LogicException
	 * @throws UnexpectedException 
	 */
	@Test
	public void testReattachInvoiceDoesNotAttachTransientChildren() 
	throws DataException, LogicException, UnexpectedException {
		
		/* first, store the samples - including a Customer */
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException,
			DataException, InitializationException, UnexpectedException {
				invoice = factory.sampleInvoiceFull(4);
				invoice.setInvoiceNo(invoiceNo);
				PersistenceWorker<Invoice> ipw = openPw(Invoice.class);
				invoice = ipw.store(invoice);
				invoice.getLines().size(); //force init persistent collection
			}
		}.execute();
		
		/* now add an invoiceline and reattach */
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException,
			DataException, InitializationException, UnexpectedException {
				InvoiceLine line = factory.sampleInvoiceLine(5);
				invoice.getLines().add(line);
				PoUtils.reattach(invoice, getProvider());
				Assert.assertNull(line.getLastModified());
			}
		}.execute();
		
		/* now perform an update */
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException,
			DataException, InitializationException, UnexpectedException {
				/*
				 * INFAMOUS NONTHREADSAFEACCESS TO SESSION ASSERTION ERROR
				 * The following line, if uncommented causes the above error.
				 * The reason is that transient instances are attached and 
				 * then evicted from the session. When the worker update
				 * is performed they are attached again. Hibernate then throws
				 * an AssertionError on flush(), because, this is a fishy
				 * behavior. There is no reason to reattach before a worker
				 * update since Bo2 2.0.0061.
				 */
//				PoUtils.reattach(invoice, getProvider()); 
				PersistenceWorker<Invoice> ipw = openPw(Invoice.class);
				Invoice i = ipw.update(invoice);
				Assert.assertTrue(i == invoice);
			}
		}.execute();
		
	}
	
	/**
	 * Tests that when re-attaching a detached instance transient objects
	 * on its graph are not attached.
	 * 
	 * @throws DataException
	 * @throws LogicException
	 * @throws UnexpectedException 
	 */
	@Test
	public void testReattachForUpdateInvoiceAttachesTransientChildren() 
	throws DataException, LogicException, UnexpectedException {
		
		/* first, store the samples */
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException,
			DataException, InitializationException, UnexpectedException {
				invoice = factory.sampleInvoiceFull(4);
				invoice.setInvoiceNo(invoiceNo);
				PersistenceWorker<Invoice> ipw = openPw(Invoice.class);
				invoice = ipw.store(invoice);
				invoice.getLines().size(); //force init persistent collection
			}
		}.execute();
		
		/* now add an invoiceline and reattach for update */
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException,
			DataException, InitializationException, UnexpectedException {
				InvoiceLine line = factory.sampleInvoiceLine(5);
				invoice.getLines().add(line);
				PoUtils.reattachForUpdate(invoice, getProvider());
				Assert.assertNotNull(line.getLastModified()); //attached
				
				/* perform the db update in this UoW */
				PersistenceWorker<Invoice> ipw = openPw(Invoice.class);
				Invoice i = ipw.update(invoice);
				Assert.assertTrue(i == invoice);
			}
		}.execute();
		
	}
	
	/**
	 * Tests that when re-attaching a detached instance transient objects
	 * on its graph are not attached.
	 * 
	 * @throws DataException
	 * @throws LogicException
	 * @throws UnexpectedException 
	 */
	@Test
	public void testDetachAfterReattachForUpdateInvoiceLeavesTransientChildrenDetached() 
	throws DataException, LogicException, UnexpectedException {
		
		/* first, store the samples */
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException,
			DataException, InitializationException, UnexpectedException {
				invoice = factory.sampleInvoiceFull(4);
				invoice.setInvoiceNo(invoiceNo);
				PersistenceWorker<Invoice> ipw = openPw(Invoice.class);
				invoice = ipw.store(invoice);
				invoice.getLines().size(); //force init persistent collection
			}
		}.execute();
		
		/* now add an invoiceline with a subline and reattach for update */
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException,
			DataException, InitializationException, UnexpectedException {
				InvoiceLine line = factory.sampleInvoiceLine(5);
				InvoiceSubLine sLine = factory.sampleInvoiceSubLine(1);
				line.getSubLines().add(sLine);
				invoice.getLines().add(line);
				
				PoUtils.reattachForUpdate(invoice, getProvider());
				Assert.assertNotNull(line.getLastModified()); //attached
				Assert.assertNotNull(sLine.getLastModified()); //attached
				
				DetachStrategy ds = PoUtils.getDetachStrategy(invoice);
				ds.detach(invoice, getProvider());
				Assert.assertNull(line.getLastModified()); //detached
				Assert.assertNull(sLine.getLastModified()); //detached
			}
		}.execute();
		
		
		
	}
	
	/*---------------------------------------------------------------------------
	  Advanced test cases.
	 ----------------------------------------------------------------------------*/
	
	/**
	 * Temporary alias for a rule.
	 */
	private InvoiceRule ruleRef;
	
	/**
	 * This test case tests that after re-attaching an object possible
	 * occurrences of many-to-one associations in the object graph mapped
	 * with insert="false" update="false" are also re-attached.
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 * 
	 */	
	@Test
	public void testReattachmentOfObjectsThroughManyToOne() throws UnexpectedException, DataException, LogicException {
		/* 1. store an invoice with a line, a subline, a rule and a subrule */
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, DataException, InitializationException, UnexpectedException {
				invoice = factory.sampleInvoice(invoiceNo);
				InvoiceSubLine sLine = factory.sampleInvoiceSubLine(1);
				InvoiceLine line = factory.sampleInvoiceLine(1);
				InvoiceSubRule sRule = factory.sampleInvoiceSubRule(1L);
				InvoiceRule rule = factory.sampleInvoiceRule(1L);
				iCust = factory.sampleInvoiceCustomer();
				
				rule.getSubRules().add(sRule);
				line.getSubLines().add(sLine);
				invoice.getRules().add(rule);
				invoice.getLines().add(line);
				invoice.setCustomer(iCust);
				
				PersistenceWorker<Invoice> ipw = openPw(Invoice.class);
				ipw.store(invoice);
			}
		}.execute();
		
		/* 2. read that invoice and connect the subline to the rule */
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, DataException, InitializationException, UnexpectedException {
				invoice = Factory.create(Invoice.class);
				invoice.setInvoiceNo(invoiceNo);
				PersistenceWorker<Invoice> ipw = openPw(Invoice.class);
				invoice = ipw.read(invoice);
				InvoiceRule rule = invoice.getRules().iterator().next();
				InvoiceSubLine sLine = invoice.getLines().iterator().next().getSubLines().iterator().next();
				sLine.setRule(rule);
				ipw.update(invoice);
			}
		}.execute();
		
		/* 3. read that invoice again */
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, DataException, InitializationException, UnexpectedException {
				invoice = Factory.create(Invoice.class);
				invoice.setInvoiceNo(invoiceNo);
				PersistenceWorker<Invoice> ipw = openPw(Invoice.class);
				invoice = ipw.read(invoice);
				ruleRef = invoice.getRules().iterator().next();
			}
		}.execute();
		
		/* 4. re-attach that invoice and test getting the rule and the subrules from the sline */
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, DataException, InitializationException, UnexpectedException {
				PoUtils.reattach(invoice, getProvider());
				InvoiceSubLine sLine = invoice.getLines().iterator().next().getSubLines().iterator().next();
				InvoiceRule rule = sLine.getRule();
				
				Assert.assertTrue(rule==ruleRef);
				Assert.assertEquals(rule, ruleRef);
				Assert.assertSame(rule, ruleRef);
			}
		}.execute();
		
		/* 5. read that invoice again */
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, DataException, InitializationException, UnexpectedException {
				invoice = Factory.create(Invoice.class);
				invoice.setInvoiceNo(invoiceNo);
				PersistenceWorker<Invoice> ipw = openPw(Invoice.class);
				invoice = ipw.read(invoice);
			}
		}.execute();
		
		/* 6. reattach  and check that we can get to the subrules via a subline */
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, DataException, InitializationException, UnexpectedException {
				PoUtils.reattach(invoice, getProvider());
				InvoiceSubLine sLine = invoice.getLines().iterator().next().getSubLines().iterator().next();
				InvoiceRule rule = sLine.getRule();
				rule.getSubRules().iterator().next();
			}
		}.execute();
		
	}
	
	/**
	 * This test case is the same as the one above. The only
	 * difference is that the Invoice is read using an HQL query.
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 * 
	 */	
	@Test
	public void testReattachmentOfObjectsFromHqlThroughManyToOne() throws UnexpectedException, DataException, LogicException {
		/* 1. store an invoice with a line, a subline, a rule and a subrule */
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, DataException, InitializationException, UnexpectedException {
				invoice = factory.sampleInvoice(invoiceNo);
				InvoiceSubLine sLine = factory.sampleInvoiceSubLine(1);
				InvoiceLine line = factory.sampleInvoiceLine(1);
				InvoiceSubRule sRule = factory.sampleInvoiceSubRule(1L);
				InvoiceRule rule = factory.sampleInvoiceRule(1L);
				iCust = factory.sampleInvoiceCustomer();
				
				rule.getSubRules().add(sRule);
				line.getSubLines().add(sLine);
				invoice.getRules().add(rule);
				invoice.getLines().add(line);
				invoice.setCustomer(iCust);
				
				PersistenceWorker<Invoice> ipw = openPw(Invoice.class);
				ipw.store(invoice);
			}
		}.execute();
		
		/* 2. read that invoice and connect the subline to the rule */
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, DataException, InitializationException, UnexpectedException {
				invoice = Factory.create(Invoice.class);
				invoice.setInvoiceNo(invoiceNo);
				PersistenceWorker<Invoice> ipw = openPw(Invoice.class);
				invoice = ipw.read(invoice);
				InvoiceRule rule = invoice.getRules().iterator().next();
				InvoiceSubLine sLine = invoice.getLines().iterator().next().getSubLines().iterator().next();
				sLine.setRule(rule);
				ipw.update(invoice);
			}
		}.execute();
		
		/* 3. read that invoice again using HQL */
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, DataException, InitializationException, UnexpectedException {
				InvoiceHqlQuery q = new InvoiceHqlQuery();
				q.setInvoiceNumber(invoiceNo);
				q.init(getProvider());
				q.open();
				q.ask();
				invoice = q.getAnswer();
				q.close();
				
				ruleRef = invoice.getRules().iterator().next();
			}
		}.execute();
		
		/* 4. re-attach that invoice and test getting the rule and the subrules from the sline */
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, DataException, InitializationException, UnexpectedException {
				try {
					HibernateSessionProvider hib = getProvider().getResource("LOCALDB",HibernateSessionProvider.class);  //$NON-NLS-1$
					Session session = hib.getHibernateSession();
					session.update(invoice);
				} catch (InitializationException e) {
					fail(e.toString());
				}
				InvoiceSubLine sLine = invoice.getLines().iterator().next().getSubLines().iterator().next();
				InvoiceRule rule = sLine.getRule();
				
				Assert.assertTrue(rule==ruleRef);
				Assert.assertEquals(rule, ruleRef);
				Assert.assertSame(rule, ruleRef);
			}
		}.execute();
	}
	
	/**
	 * This field is a part of the test below.
	 * {@link #testManyToOneReferencesAreNotModified()}
	 */
	Date customerLmd = null;
	
	/**
	 * Tests that the lastModified fields of Entities referenced
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 * 
	 */
	@Test
	@SuppressWarnings("nls")
	public void testManyToOneReferencesAreNotModified() 
	throws UnexpectedException, DataException, LogicException {
		/*
		 * Store a Customer.
		 */
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				Customer c = factory.sampleCustomer("AAA");
				PersistenceWorker<Customer> pw = openPw(Customer.class);
				c.setCustomerNo("No1");
				c = pw.store(c);
				customerLmd = c.getLastModified();
			}
		}.execute();
		
		/*
		 * Store an InvoiceCustomer associated with said Customer.
		 * Read it and get associated Customer. 
		 * Assert that Customer lastModified is intact.
		 */
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				Customer c = Factory.create(Customer.class);
				PersistenceWorker<Customer> pw = openPw(Customer.class);
				c.setCustomerNo("No1");
				c = pw.read(c);
				Invoice inv = factory.sampleInvoiceFull(1);
				inv.setInvoiceNo(invoiceNo);
				inv.getCustomer().setCustomer(c);
				PersistenceWorker<Invoice> icpw = openPw(Invoice.class);
				inv = icpw.store(inv);
				Assert.assertEquals(customerLmd, inv.getCustomer().getCustomer().getLastModified());
			}
		}.execute();
		
		/*
		 * Read the InvoiceCustomer associated with said Customer.
		 * Deep-copy, initialize Customer proxy on copy, store copy, read copy and get associated Customer.
		 * Assert that Customer lastModified is intact.
		 */
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				Invoice inv = Factory.create(Invoice.class);
				inv.setInvoiceNo(invoiceNo);
				PersistenceWorker<Invoice> icpw = openPw(Invoice.class);
				inv = icpw.read(inv);
				Invoice inv2 = PoUtils.deepCopy(inv);
				inv2.setInvoiceNo(copyInvoiceNo);
				inv2.getCustomer().getCustomer().getCustomerName();
				inv2 = icpw.store(inv2);
				Assert.assertEquals(customerLmd, inv2.getCustomer().getCustomer().getLastModified());
			}
		}.execute();
		
		/*
		 * Read the InvoiceCustomer associated with said Customer.
		 * Initialize Customer proxy, modify InvoiceCustomer and update it.
		 * Assert that Customer lastModified is intact.
		 */
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				Invoice inv = Factory.create(Invoice.class);
				inv.setInvoiceNo(invoiceNo);
				PersistenceWorker<Invoice> icpw = openPw(Invoice.class);
				inv = icpw.read(inv);
				inv.getCustomer().getCustomer().getCustomerName();
				inv.getCustomer().setRoleId(3);
				inv = icpw.update(inv);
				Assert.assertEquals(customerLmd, inv.getCustomer().getCustomer().getLastModified());
			}
		}.execute();

	}
	
		
	/*----------------------------------------------------------------------------/
	 The following two tests check if a single re-attachment mechanism can satisfy
	 all of the following conditions:
	 (a) Re-attachment of dirty objects is possible
	 (b) Initialization of their proxies is possible after re-attachment
	 (c) No SQL is generated for these objects upon flush() even if they are dirty
	 (d) Appropriate SQL is generated when explicitly updated with a worker (i.e.
	     when session.merge followed by a flush is called.
	     
	     TODO: delete these tests in the future, when we are sure they are not needed.
	-----------------------------------------------------------------------------*/
	
	/**
	 * THIS TEST IS FOR EXPERIMENTAL USE ONLY. IT IS NOT CERTAIN THAT IT WILL PASS.
	 * 
	 * Tests re-attaching a detached MODIFIED instance, re-attaching it SOMEHOW,
	 * modifying again, checking that lazy proxies can be initialized and finally
	 * flushing the session.
	 * 
	 * The expected outcome is that the changes are not persisted.
	 * 
	 * <li>transaction1: store
	 * <li>transaction2: read
	 * <li>transaction3: modify, re-attach, modify and flush
	 * <li>transaction4: confirm operations
	 * 
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	//@Test
	public void testReattach_Modified_Flush() 
	throws UnexpectedException, DataException, LogicException {
		
		/* first, store the samples */
		AbstractPersistenceOperation<Invoice> storeOp = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				po = pw.store(po);
			}
		};
		invoice = factory.sampleInvoiceFull(4);
		invoice.setInvoiceNo(invoiceNo);
		storeOp.setPo(invoice);
		Execute.transactional(storeOp);
		
		/* Now read them */
		AbstractPersistenceOperation<Invoice> readOp = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				invoice = pw.read(po);
			}
		};
		invoice = Factory.create(Invoice.class);
		invoice.setInvoiceNo(invoiceNo);
		readOp.setPo(invoice);
		Execute.transactional(readOp);
		
		/* add a new line with a subLine */
		InvoiceLine newLine = factory.sampleInvoiceLine(5);
		newLine.getSubLines().add(factory.sampleInvoiceSubLine(newLine.getLineNo()));
		invoice.getLines().add(newLine);
		
		AbstractPersistenceOperation<Invoice> updateOp = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				/* 
				 * re-attach with session.replicate(invoice, ReplicationMode.IGNORE)
				 */
				try {
					Session session = getProvider().getResource("LOCALDB",HibernateSessionProvider.class).getHibernateSession(); //$NON-NLS-1$
					/* DO MAGIC HERE */
					session.replicate(invoice, ReplicationMode.IGNORE); //this doesn't work
					//session.buildLockRequest(LockOptions.NONE).lock(invoice); //this doesn't work
					/* END DO MAGIC */
					assertTrue(invoice.getLines().size() == 5);
					/* add another line */
					InvoiceLine newLine2 = factory.sampleInvoiceLine(6);
					newLine2.getSubLines().add(factory.sampleInvoiceSubLine(newLine2.getLineNo()));
					invoice.getLines().add(newLine2);
					assertTrue(invoice.getLines().size() == 6);
					/* finally, add a subLine to each line. */
					Set<InvoiceLine> lines = invoice.getLines();
					for(InvoiceLine line : lines) {
						assertNotNull(line.getSubLines());
						line.getSubLines().size(); //make sure we can initialize lazy proxies.
						line.getSubLines().add(factory.sampleInvoiceSubLine(line.getLineNo()+1));
					}
					invoice.tidy();
					/* 
					 * The invoice now has 6 lines, each one of which has 2 subLines.
					 * We flush (after fixing keys). We will confirm later on that
					 * the changes were not persisted on flush, i.e. that the persisted
					 * invoice has 4 lines, each one of which has 1 subLine.
					 */
					session.flush();
				} catch (InitializationException e) {
					fail(e.toString());
				}
			}
		};
		updateOp.setPo(invoice);
		Execute.transactional(updateOp);
		
		/* confirm results, only 4 lines in DB. Each one has 1 subLine */
		AbstractPersistenceOperation<Invoice> readOp2 = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				invoice = pw.read(po);
				Set<InvoiceLine> lines = invoice.getLines();
				assertTrue(lines.size()==4);
				for(InvoiceLine line : lines) {
					assertTrue(line.getSubLines().size() == 1);
				}
			}
		};
		invoice = Factory.create(Invoice.class);
		invoice.setInvoiceNo(invoiceNo);
		readOp2.setPo(invoice);
		Execute.transactional(readOp2);
	}
	
	/**
	 * THIS TEST IS FOR EXPERIMENTAL USE ONLY. IT IS NOT CERTAIN THAT IT WILL PASS.
	 * 
	 * Tests re-attaching a detached MODIFIED instance, re-attaching it SOMEHOW,
	 * modifying again, checking that lazy proxies can be initialized and finally
	 * updating it with a {@link GenericHibernatePersistenceWorker}.
	 * 
	 * The expected outcome is that the changes are persisted. The difference
	 * with the previous case, is that the worker will call session.merge() and 
	 * then flush.
	 * 
	 * <li>transaction1: store
	 * <li>transaction2: read
	 * <li>transaction3: modify, re-attach, modify and update with worker
	 * <li>transaction4: confirm operations
	 * 
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	//@Test
	public void testReattach_Modified_WorkerUpdate() 
	throws UnexpectedException, DataException, LogicException {
		
		/* first, store the samples */
		AbstractPersistenceOperation<Invoice> storeOp = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				po = pw.store(po);
			}
		};
		invoice = factory.sampleInvoiceFull(4);
		invoice.setInvoiceNo(invoiceNo);
		storeOp.setPo(invoice);
		Execute.transactional(storeOp);
		
		/* Now read them */
		AbstractPersistenceOperation<Invoice> readOp = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				invoice = pw.read(po);
			}
		};
		invoice = Factory.create(Invoice.class);
		invoice.setInvoiceNo(invoiceNo);
		readOp.setPo(invoice);
		Execute.transactional(readOp);
		
		/* add a new line with a subLine */
		InvoiceLine newLine = factory.sampleInvoiceLine(5);
		newLine.getSubLines().add(factory.sampleInvoiceSubLine(newLine.getLineNo()));
		invoice.getLines().add(newLine);
		
		AbstractPersistenceOperation<Invoice> updateOp = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				/* 
				 * re-attach with session.update()
				 * merge() has the same effect, but we call merge in worker.update() anyway.
				 */
				try {
					Session session = getProvider().getResource("LOCALDB",HibernateSessionProvider.class).getHibernateSession(); //$NON-NLS-1$
					/* DO MAGIC HERE */
					session.replicate(invoice, ReplicationMode.IGNORE); //this doesn't work
					//session.buildLockRequest(LockOptions.NONE).lock(invoice); //this doesn't work
					/* END DO MAGIC */
					assertTrue(invoice.getLines().size() == 5);
					/* add another line */
					InvoiceLine newLine2 = factory.sampleInvoiceLine(6);
					newLine2.getSubLines().add(factory.sampleInvoiceSubLine(newLine2.getLineNo()));
					invoice.getLines().add(newLine2);
					assertTrue(invoice.getLines().size() == 6);
					/* finally, add a subLine to each line. */
					Set<InvoiceLine> lines = invoice.getLines();
					for(InvoiceLine line : lines) {
						assertNotNull(line.getSubLines());
						line.getSubLines().add(factory.sampleInvoiceSubLine(line.getLineNo()+1));
					}
					
					/* 
					 * The invoice now has 6 lines, each one of which has 2 subLines.
					 * We perform an update with a worker. We will confirm later on that
					 * the changes were persisted on update, i.e. that the persisted
					 * invoice has 6 lines, each one of which has 2 subLines.
					 */
					pw.update(invoice);
				} catch (InitializationException e) {
					fail(e.toString());
				}
			}
		};
		updateOp.setPo(invoice);
		Execute.transactional(updateOp);
		
		/* confirm results, 6 lines. Each one has 2 subLine */
		AbstractPersistenceOperation<Invoice> readOp2 = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				invoice = pw.read(po);
				Set<InvoiceLine> lines = invoice.getLines();
				assertTrue(lines.size()==6);
				for(InvoiceLine line : lines) {
					assertTrue(line.getSubLines().size() == 2);
				}
			}
		};
		invoice = Factory.create(Invoice.class);
		invoice.setInvoiceNo(invoiceNo);
		readOp2.setPo(invoice);
		Execute.transactional(readOp2);
	}
	
	/**
	 * HQL query that fetches an Invoice based on an invoiceNo.
	 */
	@ManagerName("LOCALDB")
	private class InvoiceHqlQuery extends AbstractHqlQuery<Invoice> {
		
		/**
		 * InvoiceNo
		 */
		@Parameter String invoiceNumber;
		
		@Override
		protected String hql() {
			return "select i from Invoice i where i.key.invoiceNo = :invoiceNumber"; //$NON-NLS-1$
		}

		/**
		 * Assigns a new value to the invoiceNo.
		 *
		 * @param invoiceNo the invoiceNo to set
		 */
		public void setInvoiceNumber(String invoiceNo) {
			this.invoiceNumber = invoiceNo;
		}

		
	}
	
}
