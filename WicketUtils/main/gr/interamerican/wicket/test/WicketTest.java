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
package gr.interamerican.wicket.test;

import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.utils.WicketUtils;

import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.apache.wicket.util.tester.WicketTesterHelper;
import org.junit.Assert;


/**
 * Base class for wicket tests.
 */
public class WicketTest {
	
	/**
	 * Creates a new WicketTest object. 
	 */
	public WicketTest() {
		super();
		this.tester=new WicketTester();
	}
	
	/**
	 * Creates a new WicketTest object.
	 * 
	 * Protected constructor to be used by sub-classes.
	 * A sub-class should call this this constructor, from a 
	 * no arguments constructor.
	 *  
	 * @param application
	 *        Web application to test.
	 */
	protected WicketTest(WebApplication application) {
		super();
		this.tester=new WicketTester(application);
	}

	/**
	 * Wicket tester.
	 */
	protected WicketTester tester;
	
	/**
	 * Creates a new Page that contains the component created by {@link #initializeComponent()}.
	 *        
	 * @return Returns the test page.
	 */
	protected Page getTestPage() {
		return new TestPage(initializeComponent());
	}
	
	/**
	 * Creates a new Page that contains the specified component.
	 * 
	 * @param cmp
	 *        The component to test. 
	 *        
	 * @return Returns the test page.
	 */
	protected Page getTestPage(Component cmp) {
		return new TestPage(cmp);
	}
	
	/**
	 * For components that require a provider in order to be initialized,
	 * overriding this hook and using {@link #getTestPage()} might be 
	 * preferable.
	 * 
	 * @return Returns the test page source.
	 */
	protected Component initializeComponent() { return null; /* dummy implementation of hook */ }
	
	/**
	 * Gets the wicket path of a component within the component 
	 * tested with the {@link TestPage}.
	 * 
	 * @param componentPath
	 *        Component path relative to the test subject.
	 *        
	 * @return Returns the component path relative to the TestPage.
	 */
	protected String path(String componentPath) {
		return WicketUtils.wicketPath(TestPage.FORM_ID, TestPage.TEST_ID, componentPath);
	}
	
	/**
	 * Gets the wicket path of the component tested with the {@link TestPage}.
	 *        
	 * @return Returns the component path relative to the TestPage.
	 */
	protected String subjectPath() {
		return WicketUtils.wicketPath(TestPage.FORM_ID, TestPage.TEST_ID);
	}
	
	/**
	 * Gets the wicket path of the {@link TestPage} form.
	 *        
	 * @return Returns the wicket path of the {@link TestPage} form.
	 */
	protected String formPath() {
		return WicketUtils.wicketPath(TestPage.FORM_ID);
	}
	
	/**
	 * Convenience method. Call this after invoking <code>tester.startPage(...)</code>
	 * @return The Form of {@link TestPage}
	 */
	@SuppressWarnings("unchecked")
	protected Form<Void> getForm() {
		return (Form<Void>) tester.getComponentFromLastRenderedPage(TestPage.FORM_ID);
	}
	
	/**
	 * Convenience method. Call this after invoking <code>tester.startPage(...)</code>
	 * @return a FormTester for the Form of TestPage.
	 */
	protected FormTester getFormTester() {
		return tester.newFormTester(TestPage.FORM_ID);
	}
	
	/**
	 * Convenience method. Call this after invoking <code>tester.startPage(...)</code>
	 * @return The AjaxButton of the Form of the {@link TestPage}
	 */
	protected AjaxButton getAjaxButton() {
		return (AjaxButton) tester.getComponentFromLastRenderedPage(WicketUtils.wicketPath(TestPage.FORM_ID, TestPage.SUBMIT_BUTTON_ID));
	}
	
	/**
	 * Convenience method. Call this after invoking <code>tester.startPage(...)</code>
	 * @return The {@link FeedbackPanel} of {@link TestPage}. 
	 */
	protected FeedbackPanel getFeedbackPanel() {
		return (FeedbackPanel) tester.getComponentFromLastRenderedPage(TestPage.FEEDBACK_PANEL_ID);
	}
	
	/**
	 * Convenience method. Call this after invoking <code>tester.startPage(...)</code>
	 * @return The test subject.
	 */
	protected Component getTestSubject() {
		return tester.getComponentFromLastRenderedPage(subjectPath());
	}
	
	/**
	 * Common assertions.
	 */
	protected void commonAssertions_noError() {
		tester.assertNoErrorMessage();
		Assert.assertTrue(tester.getLastRenderedPage() instanceof TestPage);
		Assert.assertEquals(HttpServletResponse.SC_OK, tester.getResponse().getStatus());
	}
	
	/**
	 * Common assertions when error message is expected.
	 * @param errorMessagePortion 
	 */
	protected void commonAssertions_error(String errorMessagePortion) {
		String messages = WicketTesterHelper.asLined(tester.getMessages(FeedbackMessage.ERROR));
		Assert.assertNotNull(messages);
		Assert.assertTrue(messages.contains(errorMessagePortion));
		Assert.assertTrue(tester.getLastRenderedPage() instanceof TestPage);
		Assert.assertEquals(HttpServletResponse.SC_OK, tester.getResponse().getStatus());
	}
	
	/**
	 * Gets an instance of the home page.
	 * 
	 * @return Returns an instance of the home page.
	 */
	protected Page homePage() {		
		try {
			return tester.getApplication().getHomePage().newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
