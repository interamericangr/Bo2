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
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamsProvider;
import gr.interamerican.bo2.impl.open.streams.StreamsProvider;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.test.def.posamples.Invoice;
import gr.interamerican.wicket.bo2.test.MockApplicationForWicketBo2;

import org.apache.wicket.request.cycle.RequestCycle;
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
		wicketTester.startPage(wicketTester.getApplication().getHomePage());
	}
	
	/**
	 * Unit test for create(clazz).
	 */
	@Test
	public void testCreate(){
		Bo2WicketRequestCycle.beginRequest(RequestCycle.get());
		Invoice invoice = Bo2WicketRequestCycle.create(Invoice.class);
		Assert.assertNotNull(invoice);		
		Bo2WicketRequestCycle.endRequest(RequestCycle.get());
		Assert.assertNull(Bo2Session.getSession());
	}
	
	/**
	 * Unit test for createPw(clazz).
	 */
	@Test
	public void testOpenPw(){
		Bo2WicketRequestCycle.beginRequest(RequestCycle.get());
		PersistenceWorker<User> pw = Bo2WicketRequestCycle.openPw(User.class);
		Assert.assertNotNull(pw);
		Bo2WicketRequestCycle.endRequest(RequestCycle.get());
		Assert.assertNull(Bo2Session.getSession());
	}
	
	/**
	 * Unit test for onBeginRequest().
	 * @throws InitializationException 
	 */
	@Test
	public void testGetDefaultNamedStreamsProvider() throws InitializationException{
		Assert.assertNull(Bo2Session.getSession());
		Bo2WicketRequestCycle.beginRequest(RequestCycle.get());
		NamedStreamsProvider nsp = Bo2WicketRequestCycle.getDefaultNamedStreamsProvider();
		Assert.assertNotNull(nsp);
		Bo2WicketRequestCycle.endRequest(RequestCycle.get());
	}
	
	/**
	 * Unit test for onBeginRequest().
	 * @throws InitializationException 
	 */
	@Test
	public void testGetDefaultStreamsProvider() throws InitializationException{
		Assert.assertNull(Bo2Session.getSession());
		Bo2WicketRequestCycle.beginRequest(RequestCycle.get());
		StreamsProvider sp = Bo2WicketRequestCycle.getDefaultStreamsProvider();
		Assert.assertNotNull(sp);
		Bo2WicketRequestCycle.endRequest(RequestCycle.get());
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
		
		Bo2WicketRequestCycle.beginRequest(RequestCycle.get());
		Bo2WicketRequestCycle.execute(op);
		Bo2WicketRequestCycle.endRequest(RequestCycle.get());
		
		verify(op, times(1)).open();
		verify(op, times(1)).execute();
		verify(op, times(1)).close();
	}	
}
