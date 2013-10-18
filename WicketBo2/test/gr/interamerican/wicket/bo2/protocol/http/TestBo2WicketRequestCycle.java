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
package gr.interamerican.wicket.bo2.protocol.http;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.TransactionManager;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamsProvider;
import gr.interamerican.bo2.impl.open.streams.StreamsProvider;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.samples.operations.EmptyOperation;
import gr.interamerican.bo2.test.def.posamples.Invoice;
import gr.interamerican.wicket.bo2.test.MockApplicationForWicketBo2;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link Bo2WicketRequestCycle}.
 * 
 */
public class TestBo2WicketRequestCycle {
	
	/**
	 * the WicketTester
	 */
	public WicketTester wicketTester =
		new WicketTester(new MockApplicationForWicketBo2());
	
	/**
	 * Unit test for a request cycle.
	 */
	@Test
	public void testBo2WicketRequestCycle(){
		Bo2WicketRequestCycle rc = new Bo2WicketRequestCycle (
			wicketTester.getApplication(), wicketTester.getWicketRequest(),
			wicketTester.getWicketResponse());
		wicketTester.processRequestCycle(rc);	
	}
	
	
	/**
	 * Unit test for create(clazz).
	 */
	@Test
	public void testCreate(){
		Bo2WicketRequestCycle rc = new Bo2WicketRequestCycle (
			wicketTester.getApplication(), wicketTester.getWicketRequest(),
			wicketTester.getWicketResponse());
		rc.onBeginRequest();
		Invoice invoice = Bo2WicketRequestCycle.create(Invoice.class);
		Assert.assertNotNull(invoice);		
		rc.onEndRequest();
		Assert.assertNull(Bo2Session.getSession());
	}
	
	/**
	 * Unit test for open(clazz).
	 */
	@Test
	public void testOpen(){
		Bo2WicketRequestCycle rc = new Bo2WicketRequestCycle (
			wicketTester.getApplication(), wicketTester.getWicketRequest(),
			wicketTester.getWicketResponse());
		rc.onBeginRequest();
		EmptyOperation op = Bo2WicketRequestCycle.open(EmptyOperation.class);
		Assert.assertNotNull(op);
		rc.onEndRequest();
		Assert.assertNull(Bo2Session.getSession());
	}
	
	/**
	 * Unit test for create(clazz).
	 */
	@Test
	public void testOpenPw(){
		Bo2WicketRequestCycle rc = new Bo2WicketRequestCycle (
			wicketTester.getApplication(), wicketTester.getWicketRequest(),
			wicketTester.getWicketResponse());
		rc.onBeginRequest();
		PersistenceWorker<User> pw = 
			Bo2WicketRequestCycle.openPw(User.class);
		Assert.assertNotNull(pw);		
		rc.onEndRequest();
		Assert.assertNull(Bo2Session.getSession());
	}
	
	/**
	 * Unit test for onRuntimeException().
	 */
	@Test
	public void testOnRuntimeException(){
		Assert.assertNull(Bo2Session.getSession());
		Bo2WicketRequestCycle rc = new Bo2WicketRequestCycle (
			wicketTester.getApplication(), wicketTester.getWicketRequest(),
			wicketTester.getWicketResponse());
		rc.onBeginRequest();
		rc.onRuntimeException(null, new RuntimeException());
		Provider p = rc.getProvider();
		TransactionManager tm = p.getTransactionManager();
		Assert.assertNull(tm);
		Assert.assertNull(Bo2Session.getSession());
		Assert.assertNull(Bo2Session.getProvider());
	}
	
	/**
	 * Unit test for onBeginRequest().
	 */
	@Test
	public void testOnBeginRequest(){
		Assert.assertNull(Bo2Session.getSession());
		Bo2WicketRequestCycle rc = new Bo2WicketRequestCycle (
			wicketTester.getApplication(), wicketTester.getWicketRequest(),
			wicketTester.getWicketResponse());
		rc.onBeginRequest();
		Assert.assertNotNull(Bo2Session.getSession());
		Provider p = rc.getProvider();
		TransactionManager tm = p.getTransactionManager();
		Assert.assertNotNull(tm);
		Assert.assertSame(rc.getProvider(), Bo2Session.getProvider());
		rc.onEndRequest();		
	}
	
	/**
	 * Unit test for onBeginRequest().
	 */
	@Test
	public void testOnEndRequest(){
		Assert.assertNull(Bo2Session.getSession());
		Bo2WicketRequestCycle rc = new Bo2WicketRequestCycle (
			wicketTester.getApplication(), wicketTester.getWicketRequest(),
			wicketTester.getWicketResponse());
		rc.onBeginRequest();
		Assert.assertNotNull(Bo2Session.getSession());
		rc.onEndRequest();
		Provider p = rc.getProvider();
		TransactionManager tm = p.getTransactionManager();
		Assert.assertNull(tm);
		Assert.assertNull(Bo2Session.getSession());
		Assert.assertNull(Bo2Session.getProvider());
	}
	
	/**
	 * Unit test for onBeginRequest().
	 * @throws InitializationException 
	 */
	@Test
	public void testGetDefaultNamedStreamsProvider() throws InitializationException{
		Assert.assertNull(Bo2Session.getSession());
		Bo2WicketRequestCycle rc = new Bo2WicketRequestCycle (
			wicketTester.getApplication(), wicketTester.getWicketRequest(),
			wicketTester.getWicketResponse());
		rc.onBeginRequest();
		NamedStreamsProvider nsp = Bo2WicketRequestCycle.getDefaultNamedStreamsProvider();
		Assert.assertNotNull(nsp);
		rc.onEndRequest();
	}
	
	/**
	 * Unit test for onBeginRequest().
	 * @throws InitializationException 
	 */
	@Test
	public void testGetDefaultStreamsProvider() throws InitializationException{
		Assert.assertNull(Bo2Session.getSession());
		Bo2WicketRequestCycle rc = new Bo2WicketRequestCycle (
			wicketTester.getApplication(), wicketTester.getWicketRequest(),
			wicketTester.getWicketResponse());
		rc.onBeginRequest();
		StreamsProvider sp = Bo2WicketRequestCycle.getDefaultStreamsProvider();
		Assert.assertNotNull(sp);
		rc.onEndRequest();
	}
	
	/**
	 * Unit test for onBeginRequest().
	 * @throws DataException 
	 * @throws LogicException 
	 */
	@Test
	public void testExecute() throws LogicException, DataException{
		Assert.assertNull(Bo2Session.getSession());
		Operation op = mock(Operation.class);
		
		Bo2WicketRequestCycle rc = new Bo2WicketRequestCycle (
			wicketTester.getApplication(), wicketTester.getWicketRequest(),
			wicketTester.getWicketResponse());
		rc.onBeginRequest();
		Bo2WicketRequestCycle.execute(op);
		rc.onEndRequest();
		
		verify(op, times(1)).open();
		verify(op, times(1)).execute();
		verify(op, times(1)).close();
	}

	
	
}
