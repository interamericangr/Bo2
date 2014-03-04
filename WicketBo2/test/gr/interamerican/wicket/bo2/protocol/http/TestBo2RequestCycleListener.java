package gr.interamerican.wicket.bo2.protocol.http;

import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.wicket.bo2.test.Bo2WicketTest;
import gr.interamerican.wicket.bo2.test.MockApplicationForWicketBo2;

import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link Bo2RequestCycleListener}.
 * 
 */
public class TestBo2RequestCycleListener extends Bo2WicketTest {
	
	/**
	 * the WicketTester
	 */
	public WicketTester wicketTester = new WicketTester(new MockApplicationForWicketBo2());
	
	/**
	 * Unit test for onBeginRequest().
	 */
	@Test
	public void testOnBeginRequest(){
		Assert.assertNull(Bo2Session.getSession());
		Bo2WicketRequestCycle.beginRequest(RequestCycle.get());
		Assert.assertNotNull(Bo2Session.getSession());
		Assert.assertNotNull(Bo2WicketRequestCycle.get().getProvider());
		Assert.assertNotNull(Bo2WicketRequestCycle.get().getProvider().getTransactionManager());
		Assert.assertSame(Bo2WicketRequestCycle.get().getProvider(), Bo2Session.getProvider());
		Bo2WicketRequestCycle.endRequest(RequestCycle.get());
		
	}
	
	/**
	 * Unit test for onRuntimeException().
	 */
	@Test
	public void testOnException(){
		Bo2WicketRequestCycle.beginRequest(RequestCycle.get());
		RequestCycle.get().getListeners().onException(RequestCycle.get(), new RuntimeException());
		Bo2WicketRequestCycle.endRequest(RequestCycle.get());
	}
	
	/**
	 * Unit test for onBeginRequest().
	 */
	@Test
	public void testOnEndRequest(){
		Assert.assertNull(Bo2Session.getSession());
		Bo2WicketRequestCycle.beginRequest(RequestCycle.get());
		Assert.assertNotNull(Bo2Session.getSession());
		Assert.assertNotNull(Bo2WicketRequestCycle.get().getProvider());
		Assert.assertNotNull(Bo2WicketRequestCycle.get().getProvider().getTransactionManager());
		Assert.assertSame(Bo2WicketRequestCycle.get().getProvider(), Bo2Session.getProvider());
		Bo2WicketRequestCycle.endRequest(RequestCycle.get());
		Assert.assertNull(Bo2Session.getSession());
		Assert.assertNull(Bo2Session.getProvider());
	}
		
}
