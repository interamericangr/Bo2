package gr.interamerican.wicket.bo2.protocol.http;

import static org.junit.Assert.*;

import java.io.Serializable;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.cycle.RequestCycle;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import gr.interamerican.bo2.arch.TransactionManager;
import gr.interamerican.bo2.arch.exceptions.CouldNotCommitException;
import gr.interamerican.bo2.arch.exceptions.CouldNotRollbackException;
import gr.interamerican.bo2.arch.exceptions.StaleTransactionException;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.wicket.bo2.markup.html.panel.ErrorPanel;
import gr.interamerican.wicket.bo2.test.Bo2WicketTest;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.samples.pages.MainPage;
import gr.interamerican.wicket.samples.pages.PageWithLinksMedium;
import gr.interamerican.wicket.samples.pages.PageWithLinksNoMedium;
import gr.interamerican.wicket.utils.WicketUtils;

/**
 * Unit tests for {@link Bo2RequestCycleListener}.
 *
 */
public class TestBo2RequestCycleListener extends Bo2WicketTest implements Serializable {
	
	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Before Class to enable debug on {@link ErrorPanel} for easier testing
	 */
	@BeforeClass
	public static void beforeClass() {
		ErrorPanel.debug=true;
	}
	
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
	 * testStaleTransactionExceptionUow.
	 */
	@Test
	public void testStaleTransactionExceptionUow() {
		TestPage page = new TestPage(this::setStaleTransactionExceptionUow);
		Assert.assertFalse(page.isError());
		tester.startPage(page);
		clickButton();
		Assert.assertTrue(page.isError());
	}
	
	/**
	 * testCouldNotRollbackExceptionUow.
	 */
	@Test
	public void testCouldNotRollbackExceptionUow() {
		TestPage page = new TestPage(this::couldNotRollbackExceptionUow);
		Assert.assertFalse(page.isError());
		tester.startPage(page);
		clickButton();
		Assert.assertTrue(page.isError());
	}
	
	/**
	 * testCouldNotCommitExceptionUow.
	 */
	@Test
	public void testCouldNotCommitExceptionUow() {
		TestPage page = new TestPage(this::couldNotCommitExceptionUow);
		Assert.assertFalse(page.isError());
		tester.startPage(page);
		clickButton();
		Assert.assertTrue(page.isError());
	}
	
	/**
	 * testNormalExceptionUow.
	 */
	@Test
	public void testNormalExceptionUow() {
		TestPage page = new TestPage(this::normalExceptionUow);
		Assert.assertFalse(page.isError());
		tester.startPage(page);
		clickButton();
		Assert.assertTrue(page.isError());
	}
	
	/**
	 * testNormalUow.
	 */
	@Test
	public void testNormalUow() {
		TestPage page = new TestPage(this::normalUow);
		Assert.assertFalse(page.isError());
		tester.startPage(page);
		clickButton();
		Assert.assertFalse(page.isError());
	}
	
	/**
	 * Test case 1 for {@link Bo2RequestCycleListener#onException(RequestCycle, Exception)}.
	 */
	@Test(expected=WicketRuntimeException.class)
	public void testException1() {
		tester.startPage(new MainPage());
		commonAssertions_noError(MainPage.class);
		tester.clickLink("failingPageConstructorMedium"); //$NON-NLS-1$
	}
	
	/**
	 * Test case 2 for {@link Bo2RequestCycleListener#onException(RequestCycle, Exception)}.
	 */
	@Test(expected=WicketRuntimeException.class)
	public void testException2() {
		tester.startPage(new MainPage());
		commonAssertions_noError(MainPage.class);
		tester.clickLink("failingPageConstructorNoMedium"); //$NON-NLS-1$
	}

	/**
	 * Test case 3 for {@link Bo2RequestCycleListener#onException(RequestCycle, Exception)}.
	 */
	@Test(expected=WicketRuntimeException.class)
	public void testException3() {
		tester.startPage(new MainPage());
		commonAssertions_noError(MainPage.class);
		tester.clickLink("failingPageMedium"); //$NON-NLS-1$
	}

	/**
	 * Test case 4 for {@link Bo2RequestCycleListener#onException(RequestCycle, Exception)}.
	 */
	@Test(expected=WicketRuntimeException.class)
	public void testException4() {
		tester.startPage(new MainPage());
		commonAssertions_noError(MainPage.class);
		tester.clickLink("failingPageNoMedium"); //$NON-NLS-1$
	}

	/**
	 * Test case 5 for
	 * {@link Bo2RequestCycleListener#onException(RequestCycle, Exception)}.
	 */
	@Test(expected=WicketRuntimeException.class)
	public void testException5() {
		tester.startPage(new PageWithLinksNoMedium());
		commonAssertions_noError(PageWithLinksNoMedium.class);
		tester.clickLink("links:failingLinkNoAjax"); //$NON-NLS-1$
	}

	/**
	 * Test case 6 for
	 * {@link Bo2RequestCycleListener#onException(RequestCycle, Exception)}.
	 */
	@Test(expected=WicketRuntimeException.class)
	public void testException6() {
		tester.startPage(new PageWithLinksNoMedium());
		commonAssertions_noError(PageWithLinksNoMedium.class);
		tester.clickLink("links:failingLinkAjax"); //$NON-NLS-1$
	}

	/**
	 * Test case 7 for
	 * {@link Bo2RequestCycleListener#onException(RequestCycle, Exception)}.
	 */
	@Test
	public void testException7() {
		PageWithLinksMedium page = tester.startPage(new PageWithLinksMedium());
		commonAssertions_noError(PageWithLinksMedium.class);
		assertTrue(StringUtils.isNullOrBlank(((Label) page.get("error:stackTraceMessage")).getDefaultModelObjectAsString())); //$NON-NLS-1$
		tester.clickLink("links:failingLinkNoAjax"); //$NON-NLS-1$
		assertFalse(StringUtils.isNullOrBlank(((Label) page.get("error:stackTraceMessage")).getDefaultModelObjectAsString())); //$NON-NLS-1$
	}

	/**
	 * Test case 8 for
	 * {@link Bo2RequestCycleListener#onException(RequestCycle, Exception)}.
	 */
	@Test
	public void testException8() {
		PageWithLinksMedium page = tester.startPage(new PageWithLinksMedium());
		commonAssertions_noError(PageWithLinksMedium.class);
		assertTrue(StringUtils.isNullOrBlank(((Label) page.get("error:stackTraceMessage")).getDefaultModelObjectAsString())); //$NON-NLS-1$
		tester.clickLink("links:failingLinkAjax"); //$NON-NLS-1$
		assertFalse(StringUtils.isNullOrBlank(((Label) page.get("error:stackTraceMessage")).getDefaultModelObjectAsString())); //$NON-NLS-1$
	}

	/**
	 * CouldNotRollbackException uow.
	 * @param target 
	 * @param form 
	 * @throws CouldNotRollbackException the could not rollback exception
	 */
	@SuppressWarnings("unused") 
	void couldNotRollbackExceptionUow(AjaxRequestTarget target, Form<?> form) throws CouldNotRollbackException {
		TransactionManager tm = Bo2WicketRequestCycle.provider().getTransactionManager();
		TransactionManager spyTm = Mockito.spy(tm);
		Mockito.doThrow(new CouldNotRollbackException(new RuntimeException(StringConstants.EMPTY))).when(spyTm).rollback();
		ReflectionUtils.set("transactionManager", spyTm, Bo2WicketRequestCycle.provider()); //$NON-NLS-1$
		throw new RuntimeException(); //force rollback
	}
	
	/**
	 * CouldNotCommitException uow.
	 * @param target
	 * @param form
	 * @throws CouldNotCommitException the could not commit exception
	 */
	@SuppressWarnings("unused")
	void couldNotCommitExceptionUow(AjaxRequestTarget target, Form<?> form) throws CouldNotCommitException {
		TransactionManager tm = Bo2WicketRequestCycle.provider().getTransactionManager();
		TransactionManager spyTm = Mockito.spy(tm);
		Mockito.doThrow(new CouldNotCommitException(StringConstants.EMPTY)).when(spyTm).commit();
		ReflectionUtils.set("transactionManager", spyTm, Bo2WicketRequestCycle.provider()); //$NON-NLS-1$
	}
	
	/**
	 * StaleTransactionException uow.
	 * 
	 * @param target
	 * @param form
	 * @throws StaleTransactionException the stale transaction exception
	 */
	@SuppressWarnings("unused")
	void setStaleTransactionExceptionUow(AjaxRequestTarget target, Form<?> form) throws StaleTransactionException {
		throw new StaleTransactionException(StringConstants.EMPTY);
	}
	
	/**
	 * normal uow.
	 * @param target
	 * @param form
	 */
	@SuppressWarnings("unused")
	void normalUow(AjaxRequestTarget target, Form<?> form) {
		//EMPTY
	}

	/**
	 * normalException uow.
	 * 
	 * @param target
	 * @param form
	 */
	@SuppressWarnings("unused")
	void normalExceptionUow(AjaxRequestTarget target, Form<?> form) {
		throw new RuntimeException(StringConstants.EMPTY);
	}

	/**
	 * clicks button.
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