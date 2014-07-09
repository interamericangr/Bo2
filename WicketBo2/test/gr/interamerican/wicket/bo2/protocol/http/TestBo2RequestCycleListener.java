package gr.interamerican.wicket.bo2.protocol.http;

import gr.interamerican.bo2.arch.TransactionManager;
import gr.interamerican.bo2.arch.exceptions.CouldNotCommitException;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.wicket.bo2.test.Bo2WicketTest;
import gr.interamerican.wicket.bo2.test.MockApplicationForWicketBo2;

import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.tester.WicketTester;
import org.hibernate.transaction.JOTMTransactionManagerLookup;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

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
	
	@Test
	public void testCouldNotCommitException() {
		//TODO: test this.
	}
	
	
	void setRollBackOnlyUow() throws CouldNotCommitException {
		TransactionManager tm = Bo2WicketRequestCycle.provider().getTransactionManager();
		TransactionManager spyTm = Mockito.spy(tm);
		Mockito.doThrow(new CouldNotCommitException(StringConstants.EMPTY)).when(tm).commit();
		ReflectionUtils.set("transactionManager", spyTm, Bo2WicketRequestCycle.provider());
	}
		
}
