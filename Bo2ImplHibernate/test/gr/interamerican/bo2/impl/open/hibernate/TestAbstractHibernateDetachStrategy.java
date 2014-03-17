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
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.DetachStrategy;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.po.PoUtils;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.impl.open.runtime.Execute;
import gr.interamerican.bo2.impl.open.runtime.RuntimeCommand;
import gr.interamerican.bo2.impl.open.workers.AbstractPersistenceOperation;
import gr.interamerican.bo2.test.def.posamples.Invoice;
import gr.interamerican.bo2.test.def.posamples.SamplesFactory;
import gr.interamerican.bo2.test.scenarios.DeleteInvoiceData;
import gr.interamerican.bo2.utils.Debug;
import gr.interamerican.bo2.utils.ReflectionUtils;

import java.util.Set;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link HibernateDetachStrategy}.
 */
public class TestAbstractHibernateDetachStrategy {
	
	/**
	 * samples factory
	 */
	private static SamplesFactory factory=SamplesFactory.getBo2Factory();
	
	/**
	 * Test sample.
	 */
	private DetachStrategy SUBJECT = new SampleHibernateDetachStrategyImpl();
	
	/**
	 * Clear operation.
	 */
	private DeleteInvoiceData clear;
	
	/**
	 * Invoice being currently tested.
	 */
	private Invoice invoice;
	
	/**
	 * invoiceNo
	 */
	String invoiceNo = "AA2"; //$NON-NLS-1$
	
	/**
	 * Creates a new TestHibernateSessionProviderImpl_Reattach object. 
	 */
	public TestAbstractHibernateDetachStrategy() {
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
	throws DataException,LogicException, UnexpectedException {
		new RuntimeCommand(clear).execute();
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
	 * Unit test for markExplicitSave.
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testMarkExplicitSave() throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				Invoice i = Factory.create(Invoice.class);
				DetachStrategy strategy = SUBJECT;
				strategy.markExplicitSave(i, getProvider());
				HibernateSessionProvider hib = 
					HibernateBo2Utils.getHibernateSessionProvider(i, getProvider());
				@SuppressWarnings("unchecked")
				Set<Object> excluded = (Set<Object>) ReflectionUtils.get("excluded", hib); //$NON-NLS-1$
				assertTrue(excluded.size()==1);
				assertTrue(excluded.contains(i));
			}
		}.execute();
	}
	
	/**
	 * Tests reattach()
	 * 
	 * @throws DataException
	 * @throws LogicException
	 * @throws UnexpectedException 
	 */
	@Test
	public void testReattach() 
	throws DataException, LogicException, UnexpectedException {
		
		/* Store */
		AbstractPersistenceOperation<Invoice> storeOp = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override 
			public void execute() throws LogicException, DataException {
				po = pw.store(po);
				assertTrue(PoUtils.deepEquals(invoice, po));
			}
		};
		invoice = factory.sampleInvoiceFull(4);
		invoice.setInvoiceNo(invoiceNo);
		storeOp.setPo(invoice);
		Execute.transactional(storeOp);
		invoice = storeOp.getPo();
		
		/* Re-attach twice */
		AbstractPersistenceOperation<Invoice> updateOp = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				SUBJECT.reattach(po, getProvider());
				/* test that second reattach is ok */
				SUBJECT.reattach(po, getProvider());
			}
		};
		updateOp.setPo(invoice);
		Execute.transactional(updateOp);
		invoice = updateOp.getPo();
		
		/* Re-attach and update */
		AbstractPersistenceOperation<Invoice> updateOp2 = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				SUBJECT.reattach(po, getProvider());
				po.getLines().clear();
				po = pw.update(po);
			}
		};
		updateOp2.setPo(invoice);
		Execute.transactional(updateOp2);
		invoice = updateOp2.getPo();
		
		/* Confirm operations */
		AbstractPersistenceOperation<Invoice> confirmOp = 
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				po = pw.read(po);
				assertEquals(po.getLines().size(), 0);
			}
		};
		confirmOp.setPo(invoice);
		Execute.transactional(confirmOp);
		invoice = confirmOp.getPo();
		
	}
	
	/**
	 * Tests no-ops. Reattaching an attached object is tested in the previous test.
	 * @throws DataException 
	 * @throws LogicException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testReattach_NoOps() throws LogicException, DataException, UnexpectedException {
		AbstractPersistenceOperation<Invoice> op =
			new AbstractPersistenceOperation<Invoice>(Invoice.class) {			
			@Override public void execute() throws LogicException, DataException {
				SUBJECT.reattach(po, getProvider());
			}
		};
		Execute.transactional(op);
	}
	
	/**
	 * Sample for this test.
	 */
	private class SampleHibernateDetachStrategyImpl extends AbstractHibernateDetachStrategy {

		/**
		 * serialVersionUID.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		protected void doReattach(Object object, Session session) {
			session.update(object);
		}

		public void detach(Object object, Provider provider) {
			/* empty */
		}
		
	}
}
