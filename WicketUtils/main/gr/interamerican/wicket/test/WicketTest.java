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

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.IRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.cycle.RequestCycleListenerCollection;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.apache.wicket.util.tester.WicketTesterHelper;

import gr.interamerican.bo2.utils.ExceptionUtils;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.functions.SerializableUnaryOperator;
import gr.interamerican.wicket.markup.html.Markup;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.utils.WicketUtils;

/**
 * Base class for wicket tests.
 */
public class WicketTest {

	/**
	 * Error message registered.
	 */
	private Exception error;

	/**
	 * Wicket tester.
	 */
	protected final WicketTester tester;

	/**
	 * Creates a new WicketTest object.
	 */
	public WicketTest() {
		this.tester = new WicketTester();
		this.tester.getApplication().getRequestCycleListeners().add(new MonitoringCycleListener());
	}

	/**
	 * Creates a new WicketTest object.
	 * 
	 * Protected constructor to be used by sub-classes. A sub-class should call
	 * this this constructor, from a no arguments constructor.
	 * 
	 * @param application
	 *            Web application to test.
	 */
	protected WicketTest(WebApplication application) {
		application.getRequestCycleListeners().add(new MonitoringCycleListener());
		this.tester = new WicketTester(application);
	}

	/**
	 * Creates a new Page that contains the component created by
	 * {@link #initializeComponent(String)}.<br>
	 * Do note that the {@link Component} created by this will be enclosed in a
	 * 'div' tag as defined by {@link Markup#div}.<br>
	 * If for some reason you require a different mark-up use
	 * {@link #getTestPage(SerializableUnaryOperator)}.
	 * 
	 * @return Returns the test page.
	 */
	protected final Page getTestPage() {
		return getTestPage(Markup.div);
	}

	/**
	 * Creates a new Page that contains the component created by
	 * {@link #initializeComponent(String)}.
	 * 
	 * @param markup
	 *            {@link SerializableUnaryOperator} for the markup the Component
	 *            returned by {@link #initializeComponent()} requires
	 * 
	 * @return Returns the test page.
	 * @see Markup For some ready implimentations
	 */
	protected Page getTestPage(SerializableUnaryOperator<String> markup) {
		return new TestPage(initializeComponent(), markup);
	}

	/**
	 * Creates a new Page that contains the specified component.<br>
	 * Do note that the {@link Component} created by this will be enclosed in a
	 * 'div' tag as defined by {@link Markup#div}.<br>
	 * If for some reason you require a different mark-up call {@link TestPage}
	 * directly.
	 * 
	 * @param cmp
	 *            The component to test.
	 * 
	 * @return Returns the test page.
	 */
	protected Page getTestPage(Component cmp) {
		return new TestPage(cmp, Markup.div);
	}

	/**
	 * Hook method for components that require a provider in order to be
	 * initialized, overriding this hook and using {@link #getTestPage()} might
	 * be preferable.
	 * 
	 * @return Returns the test page source.
	 * 
	 * @deprecated implement {@link #initializeComponent(String)} instead
	 */
	@Deprecated
	protected Component initializeComponent() {
		return initializeComponent(TestPage.TEST_ID);
	}

	/**
	 * Hook method for testing of components.<br>
	 * Overriding this hook and using {@link #startAndTestComponent(Class)}
	 * might be convenient for fast testing of components creation.
	 * 
	 * @param wicketId
	 *            Wicket Id We expected the returned component to use
	 * 
	 * @return Returns the component to be tested
	 */
	protected Component initializeComponent(@SuppressWarnings("unused") String wicketId) {
		return null;
	}

	/**
	 * Gets the wicket path of a component within the component tested with the
	 * {@link TestPage}.
	 * 
	 * @param componentPath
	 *            Component path relative to the test subject.
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
	 * Convenience method. Call this after invoking
	 * <code>tester.startPage(...)</code>
	 * 
	 * @return The Form of {@link TestPage}
	 */
	@SuppressWarnings("unchecked")
	protected Form<Void> getForm() {
		return (Form<Void>) tester.getComponentFromLastRenderedPage(TestPage.FORM_ID);
	}

	/**
	 * Convenience method. Call this after invoking
	 * <code>tester.startPage(...)</code>
	 * 
	 * @return a FormTester for the Form of TestPage.
	 */
	protected FormTester getFormTester() {
		return tester.newFormTester(TestPage.FORM_ID);
	}

	/**
	 * Convenience method. Call this after invoking
	 * <code>tester.startPage(...)</code>
	 * 
	 * @return The AjaxButton of the Form of the {@link TestPage}
	 */
	protected AjaxButton getAjaxButton() {
		return (AjaxButton) tester
				.getComponentFromLastRenderedPage(WicketUtils.wicketPath(TestPage.FORM_ID, TestPage.SUBMIT_BUTTON_ID));
	}

	/**
	 * Convenience method. Call this after invoking
	 * <code>tester.startPage(...)</code>
	 * 
	 * @return The {@link FeedbackPanel} of {@link TestPage}.
	 */
	protected FeedbackPanel getFeedbackPanel() {
		return (FeedbackPanel) tester.getComponentFromLastRenderedPage(TestPage.FEEDBACK_PANEL_ID);
	}

	/**
	 * Convenience method. Call this after invoking
	 * <code>tester.startPage(...)</code>
	 * 
	 * @return The test subject.
	 */
	protected Component getTestSubject() {
		return tester.getComponentFromLastRenderedPage(subjectPath());
	}

	/**
	 * Starts the {@link WicketTest} with the page returned by
	 * {@link #getTestPage()}, does the {@link #commonAssertions_noError()} and
	 * then it returns the created {@link Component} after confirming it is
	 * assigned from the input class.<br>
	 * Do note that the {@link Component} created by this will be enclosed in a
	 * 'div' tag as defined by {@link Markup#div}.<br>
	 * If for some reason you require a different mark-up use
	 * {@link #startAndTestComponent(Class, SerializableUnaryOperator)}.
	 * 
	 * @param clz
	 *            Expected Class of Component
	 * @return The Rendered {@link Component}
	 */
	protected final <T extends Component> T startAndTestComponent(Class<T> clz) {
		return startAndTestComponent(clz, Markup.div);
	}

	/**
	 * Starts the {@link WicketTest} with the page returned by
	 * {@link #getTestPage()}, does the {@link #commonAssertions_noError()} and
	 * then it returns the created {@link Component} after confirming it is
	 * assigned from the input class.
	 * 
	 * @param clz
	 *            Expected Class of Component
	 * @param markup
	 *            {@link SerializableUnaryOperator} for the mark-up the
	 *            Component returned by {@link #initializeComponent()} requires
	 * @return The Rendered {@link Component}
	 * @see Markup For some ready implimentations
	 */
	protected <T extends Component> T startAndTestComponent(Class<T> clz, SerializableUnaryOperator<String> markup) {
		tester.startPage(getTestPage(markup));
		commonAssertions_noError();
		Component c = getTestSubject();
		if (!clz.isAssignableFrom(c.getClass())) {
			fail(MessageFormat.format("Expected component of type {0} but it was {1}", clz, c.getClass())); //$NON-NLS-1$
		}
		return Utils.cast(c);
	}

	/**
	 * Common assertions where expected page is {@link TestPage}.
	 */
	protected void commonAssertions_noError() {
		commonAssertions_noError(TestPage.class);
	}

	/**
	 * Common assertions where expected page is passed as argument.
	 * 
	 * @param clz
	 *            Class of Page
	 */
	protected void commonAssertions_noError(Class<? extends Page> clz) {
		tester.assertNoErrorMessage();
		tester.assertRenderedPage(clz);
		assertEquals(HttpServletResponse.SC_OK, tester.getResponse().getStatus());
		verifyNoException();
		verifySerializable();
	}

	/**
	 * 
	 */
	private void verifySerializable() {
		try (OutputStream bytes = new ByteArrayOutputStream(); ObjectOutputStream out = new ObjectOutputStream(bytes)) {
			out.writeObject(tester.getLastRenderedPage());
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/**
	 * Retrieves the {@link Locale} used during the last Request
	 * 
	 * @return Locale Used
	 */
	protected Locale getLocale() {
		return tester.getRequest().getLocale();
	}

	/**
	 * Common assertions when error message is expected.
	 *
	 * @param errorMessagePortion
	 *            the error message portion
	 */
	protected void commonAssertions_error(String errorMessagePortion) {
		String messages = WicketTesterHelper.asLined(tester.getMessages(FeedbackMessage.ERROR));
		assertNotNull(messages);
		assertTrue(messages.contains(errorMessagePortion));
		assertTrue(tester.getLastRenderedPage() instanceof TestPage);
		assertEquals(HttpServletResponse.SC_OK, tester.getResponse().getStatus());
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

	/**
	 * Gets the monitoring cycle listener.
	 *
	 * @return Returns the MonitoringCycleListener
	 */
	MonitoringCycleListener getMonitoringCycleListener() {
		RequestCycleListenerCollection requestCycleListeners = tester.getApplication().getRequestCycleListeners();
		for (IRequestCycleListener listener : requestCycleListeners) {
			if (listener instanceof MonitoringCycleListener) {
				return (MonitoringCycleListener) listener;
			}
		}
		throw new RuntimeException("No error monitoring listener registered!"); //$NON-NLS-1$
	}

	/**
	 * Verifies that no exception occurred during the last request.
	 */
	protected final void verifyNoException() {
		if (error != null) {
			error.printStackTrace();
			fail(error.getMessage());
		}
	}

	/**
	 * Verifies that a specific type of exception occurred during the last
	 * request.
	 * 
	 * @param causeType
	 *            Type of Exception expcted
	 */
	protected final void verifyException(Class<? extends Throwable> causeType) {
		assertNotNull("No Exception Occured", error); //$NON-NLS-1$
		assertTrue("Exception was not of type " + causeType, ExceptionUtils.isCausedBy(error, causeType)); //$NON-NLS-1$
	}

	/**
	 * An {@link IRequestCycleListener} implementation that indicates if an
	 * error was registered in this cycle.
	 *
	 */
	class MonitoringCycleListener extends AbstractRequestCycleListener {

		@Override
		public void onBeginRequest(RequestCycle cycle) {
			error = null;
		}

		@Override
		public IRequestHandler onException(RequestCycle cycle, Exception ex) {
			error = ex;
			return null;
		}
	}
}