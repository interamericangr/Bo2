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
import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.impl.open.runtime.Execute;
import gr.interamerican.bo2.test.def.posamples.Invoice;
import gr.interamerican.bo2.test.def.posamples.SamplesFactory;
import gr.interamerican.bo2.test.scenarios.DeleteInvoiceData;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link FlushStrategy}.
 * 
 * TODO: Used an alternate factory with mapped implementations. Why???
 */
public class TestFlushStrategy {
	
	/**
	 * samples factory
	 */
	private static SamplesFactory factory=SamplesFactory.getBo2Factory();
	
	/**
	 * Clear operation.
	 */
	private DeleteInvoiceData clear = new DeleteInvoiceData();
	
	/**
	 * Sample
	 */
	private Invoice one;
	
	/**
	 * Sample
	 */
	private Invoice two;
	
	/**
	 * Sample
	 */
	private Invoice three;
	
	/**
	 * Sample key
	 */
	private String invoiceNoOne = "Inv1"; //$NON-NLS-1$
	
	/**
	 * Sample key
	 */
	private String invoiceNoTwo = "Inv2"; //$NON-NLS-1$
	
	/**
	 * Sample key
	 */
	private String invoiceNoThree = "Inv3"; //$NON-NLS-1$
	
		
	/**
	 * Deletes old and stores fresh samples.
	 * 
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	@Before
	public void storeSamples() 
	throws UnexpectedException, DataException, LogicException {
		Execute.transactional(clear);
		new AbstractBo2RuntimeCmd() { 			
			@Override public void work() throws LogicException,
			DataException, InitializationException, UnexpectedException {
				one = factory.sampleInvoiceFull(4);
				one.setInvoiceNo(invoiceNoOne);
				two = factory.sampleInvoiceFull(4);
				two.setInvoiceNo(invoiceNoTwo);
				three = factory.sampleInvoiceFull(4);
				three.setInvoiceNo(invoiceNoThree);
				PersistenceWorker<Invoice> pw = openPw(Invoice.class);
				one = pw.store(one);
				two = pw.store(two);
				three = pw.store(three);
			}
		}.execute();
	}
	
	/**
	 * Unit test for FlushPolicy.SESSION.flush()
	 * 
	 * We expect to see all managed entities' modifications
	 * persisted in the DB.
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testFlushPolicy_SESSION() 
	throws UnexpectedException, DataException, LogicException {
		/* flush */
		new AbstractBo2RuntimeCmd() { 			
			@Override public void work() throws LogicException,
			DataException, InitializationException, UnexpectedException {
				PersistenceWorker<Invoice> pw = openPw(Invoice.class);
				one = pw.read(one);
				two = pw.read(two);
				three = pw.read(three);
				one.getLines().clear();
				two.getLines().clear();
				three.getLines().clear();
				Session session = getProvider().getResource("LOCALDB",HibernateSessionProvider.class).getHibernateSession(); //$NON-NLS-1$
				FlushStrategy.SESSION.flush(one, session, null, null);
			}
		}.execute();
		
		/* confirm */
		new AbstractBo2RuntimeCmd() { 			
			@Override public void work() throws LogicException,
			DataException, InitializationException, UnexpectedException {
				PersistenceWorker<Invoice> pw = openPw(Invoice.class);				
				one = pw.read(one);
				two = pw.read(two);
				three = pw.read(three);
				assertEquals(0,one.getLines().size());
				assertEquals(0,two.getLines().size());
				assertEquals(0,three.getLines().size());
			}
		}.execute();
	}
	
	/**
	 * Unit test for FlushPolicy.OBJECT.flush()
	 * 
	 * We expect to see only the specified entity's modifications
	 * persisted in the DB.
	 * 
	 * We will modify only invoice one.
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testFlushPolicy_OBJECT() 
	throws UnexpectedException, DataException, LogicException {
		/* flush */
		new AbstractBo2RuntimeCmd() { 			
			@Override public void work() throws LogicException,
			DataException, InitializationException, UnexpectedException {
				PersistenceWorker<Invoice> pw = openPw(Invoice.class);
				one = pw.read(one);
				two = pw.read(two);
				three = pw.read(three);
				one.getLines().clear();
				two.getLines().clear();
				three.getLines().clear();
				Set<Invoice> managed = new HashSet<Invoice>();
				managed.add(one);
				managed.add(two);
				managed.add(three);
				Session session = getProvider().getResource("LOCALDB",HibernateSessionProvider.class).getHibernateSession(); //$NON-NLS-1$
				FlushStrategy.OBJECT.flush(one, session, managed, null);
			}
		}.execute();
		
		/* confirm */
		new AbstractBo2RuntimeCmd() { 			
			@Override public void work() throws LogicException,
			DataException, InitializationException, UnexpectedException {
				PersistenceWorker<Invoice> pw = openPw(Invoice.class);
				one = pw.read(one);
				two = pw.read(two);
				three = pw.read(three);
				assertEquals(0,one.getLines().size());
				assertEquals(4,two.getLines().size());
				assertEquals(4,three.getLines().size());
			}
		}.execute();
	}
	
	/**
	 * Unit test for FlushPolicy.EXCLUDING.flush()
	 * 
	 * We expect to see all managed entities' modifications
	 * persisted in the DB except for those excluded. If the
	 * specified entity is in the excluded list it gets
	 * persisted anyway.
	 * 
	 * We will modify only invoice one.
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testFlushPolicy_EXCLUDING() 
	throws UnexpectedException, DataException, LogicException {
		/* flush */
		new AbstractBo2RuntimeCmd() { 			
			@Override public void work() throws LogicException,
			DataException, InitializationException, UnexpectedException {
				PersistenceWorker<Invoice> pw = openPw(Invoice.class);
				one = pw.read(one);
				two = pw.read(two);
				three = pw.read(three);
				one.getLines().clear();
				two.getLines().clear();
				three.getLines().clear();
				Set<Invoice> excluded = new HashSet<Invoice>();
				excluded.add(one);
				excluded.add(two);
				Session session = getProvider().getResource("LOCALDB",HibernateSessionProvider.class).getHibernateSession(); //$NON-NLS-1$
				FlushStrategy.EXCLUDING.flush(one, session, null, excluded);
			}
		}.execute();
		
		/* confirm */
		new AbstractBo2RuntimeCmd() { 			
			@Override public void work() throws LogicException,
			DataException, InitializationException, UnexpectedException {
				PersistenceWorker<Invoice> pw = openPw(Invoice.class);
				one = pw.read(one);
				two = pw.read(two);
				three = pw.read(three);
				assertTrue(one.getLines().size()==0);
				assertTrue(two.getLines().size()==4);
				assertTrue(three.getLines().size()==0);
			}
		}.execute();
	}

}
