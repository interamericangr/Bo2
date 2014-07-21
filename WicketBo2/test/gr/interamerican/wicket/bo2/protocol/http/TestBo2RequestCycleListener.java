package gr.interamerican.wicket.bo2.protocol.http;

import gr.interamerican.bo2.arch.TransactionManager;
import gr.interamerican.bo2.arch.exceptions.CouldNotCommitException;
import gr.interamerican.bo2.arch.exceptions.CouldNotRollbackException;
import gr.interamerican.bo2.arch.exceptions.StaleTransactionException;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.wicket.bo2.callbacks.MethodBasedBo2WicketBlock;
import gr.interamerican.wicket.bo2.test.Bo2WicketTest;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.utils.WicketUtils;

import java.io.Serializable;

import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.request.cycle.RequestCycle;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit tests for {@link Bo2RequestCycleListener}.
 * 
 */
public class TestBo2RequestCycleListener extends Bo2WicketTest implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
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
	
	/**
	 * testStaleTransactionExceptionUow
	 */
	@Test
	@SuppressWarnings("nls")
	public void testStaleTransactionExceptionUow() {
		TestPage page = new TestPage(new MethodBasedBo2WicketBlock("setStaleTransactionExceptionUow", this));
		Assert.assertFalse(page.isError());
		tester.startPage(page);
		clickButton();
		Assert.assertTrue(page.isError());
	}
	
	/**
	 * testCouldNotRollbackExceptionUow
	 */
	@Test
	@SuppressWarnings("nls")
	public void testCouldNotRollbackExceptionUow() {
		TestPage page = new TestPage(new MethodBasedBo2WicketBlock("couldNotRollbackExceptionUow", this));
		Assert.assertFalse(page.isError());
		tester.startPage(page);
		clickButton();
		Assert.assertTrue(page.isError());
	}
	
	/**
	 * testCouldNotCommitExceptionUow
	 */
	@Test
	@SuppressWarnings("nls")
	public void testCouldNotCommitExceptionUow() {
		TestPage page = new TestPage(new MethodBasedBo2WicketBlock("couldNotCommitExceptionUow", this));
		Assert.assertFalse(page.isError());
		tester.startPage(page);
		clickButton();
		Assert.assertTrue(page.isError());
	}
	
	/**
	 * testNormalExceptionUow
	 */
	@Test
	@SuppressWarnings("nls")
	public void testNormalExceptionUow() {
		TestPage page = new TestPage(new MethodBasedBo2WicketBlock("normalExceptionUow", this));
		Assert.assertFalse(page.isError());
		tester.startPage(page);
		clickButton();
		Assert.assertTrue(page.isError());
	}
	
	/**
	 * testNormalUow
	 */
	@Test
	@SuppressWarnings("nls")
	public void testNormalUow() {
		TestPage page = new TestPage(new MethodBasedBo2WicketBlock("normalUow", this));
		Assert.assertFalse(page.isError());
		tester.startPage(page);
		clickButton();
		Assert.assertFalse(page.isError());
	}
	
	/**
	 * CouldNotRollbackException uow
	 * @throws CouldNotRollbackException
	 */
	void couldNotRollbackExceptionUow() throws CouldNotRollbackException {
		TransactionManager tm = Bo2WicketRequestCycle.provider().getTransactionManager();
		TransactionManager spyTm = Mockito.spy(tm);
		Mockito.doThrow(new CouldNotRollbackException(new RuntimeException(StringConstants.EMPTY))).when(spyTm).rollback();
		ReflectionUtils.set("transactionManager", spyTm, Bo2WicketRequestCycle.provider()); //$NON-NLS-1$
		throw new RuntimeException(); //force rollback
	}
	
	/**
	 * CouldNotCommitException uow
	 * @throws CouldNotCommitException
	 */
	void couldNotCommitExceptionUow() throws CouldNotCommitException {
		TransactionManager tm = Bo2WicketRequestCycle.provider().getTransactionManager();
		TransactionManager spyTm = Mockito.spy(tm);
		Mockito.doThrow(new CouldNotCommitException(StringConstants.EMPTY)).when(spyTm).commit();
		ReflectionUtils.set("transactionManager", spyTm, Bo2WicketRequestCycle.provider()); //$NON-NLS-1$
	}
	
	/**
	 * StaleTransactionException uow
	 * @throws StaleTransactionException
	 */
	void setStaleTransactionExceptionUow() throws StaleTransactionException {
		throw new StaleTransactionException(StringConstants.EMPTY);
	}
	
	/**
	 * normal uow
	 */
	void normalUow() {
		//EMPTY
	}
	
	/**
	 * normalException uow
	 */
	void normalExceptionUow() {
		throw new RuntimeException(StringConstants.EMPTY);
	}
	
	/**
	 * clicks button
	 */
	@SuppressWarnings("nls")
	void clickButton() {
		String buttonPath = WicketUtils.wicketPath(TestPage.FORM_ID, TestPage.SUBMIT_BUTTON_ID);		
		tester.assertComponent(buttonPath, AjaxButton.class);
		AjaxButton button = (AjaxButton)
			tester.getComponentFromLastRenderedPage(buttonPath);
		tester.executeAjaxEvent(button, "onclick");
	}
		
}
