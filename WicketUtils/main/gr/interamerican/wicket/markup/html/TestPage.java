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
package gr.interamerican.wicket.markup.html;

import java.io.IOException;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.DefaultMarkupCacheKeyProvider;
import org.apache.wicket.markup.IMarkupCacheKeyProvider;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.tester.WicketTester;

import gr.interamerican.bo2.utils.StreamUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.functions.SerializableUnaryOperator;
import gr.interamerican.wicket.ajax.markup.html.form.CallbackAjaxButton;
import gr.interamerican.wicket.callback.ICallbackAction;
import gr.interamerican.wicket.callback.LegacyCallbackAction;
import gr.interamerican.wicket.util.resource.StringAsResourceStream;

/**
 * Base class for unit test classes of Wicket components.<br>
 * Provides a single argument constructor in order to make easy the usage of
 * {@link WicketTester}.<br>
 */
@SuppressWarnings("deprecation")
public class TestPage
extends BaseTestPage
implements IMarkupResourceStreamProvider, IMarkupCacheKeyProvider {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Wicket id.
	 */
	public static final String SUBMIT_BUTTON_ID = "submitButton"; //$NON-NLS-1$

	/**
	 * {@link Markup} that the {@link Component} passed on the Constructor
	 * requires in order to be rendered correctly.
	 */
	private final SerializableUnaryOperator<String> markup;

	/**
	 * File With markup
	 */
	private static final String MARKUP = "/gr/interamerican/wicket/markup/html/TestPage.html"; //$NON-NLS-1$

	/**
	 * 
	 */
	private final ICallbackAction callbackAction;

	/**
	 * Public Default Constructor.
	 */
	public TestPage() {
		this((ICallbackAction)null, new EmptyPanel(TEST_ID), Markup.div);
	}

	/**
	 * Constructor with an actual {@link LegacyCallbackAction} that is executed on
	 * submit.
	 *
	 * @param callbackAction
	 *            the callback action
	 */
	public TestPage(LegacyCallbackAction callbackAction) {
		this(ICallbackAction.fromLegacy(callbackAction));
	}

	/**
	 * Constructor with an actual {@link ICallbackAction} that is executed on
	 * submit.
	 *
	 * @param callbackAction
	 *            the callback action
	 * @deprecated To be removed on bo2 v4 due to wicket changes
	 */
	@Deprecated
	public TestPage(ICallbackAction callbackAction) {
		this(callbackAction, new EmptyPanel(TEST_ID), Markup.div);
	}

	/**
	 * Constructor with an {@link Component} to render and an
	 * {@link SerializableUnaryOperator} to generate the markup that is required
	 * by this.
	 *
	 * @param testSubject
	 *            Component to test.
	 * @param markup
	 *            {@link SerializableUnaryOperator} for markup that is required
	 *            by this
	 * @see Markup
	 */
	public TestPage(Component testSubject, SerializableUnaryOperator<String> markup) {
		this((ICallbackAction)null, testSubject, markup);
	}

	/**
	 * Constructor with an {@link Component} to render and an
	 * {@link SerializableUnaryOperator} to generate the markup that is required
	 * by this and an {@link LegacyCallbackAction} to invoke on submit.
	 * 
	 * @param callbackAction
	 *            the callback action
	 * @param component
	 *            Component to test.
	 * @param markup
	 *            {@link SerializableUnaryOperator} for markup that is required
	 *            by this
	 * @see Markup
	 */
	public TestPage(LegacyCallbackAction callbackAction, Component component, SerializableUnaryOperator<String> markup) {
		this(ICallbackAction.fromLegacy(callbackAction),component,markup);
	}

	/**
	 * Constructor with an {@link Component} to render and an
	 * {@link SerializableUnaryOperator} to generate the markup that is required
	 * by this and an {@link ICallbackAction} to invoke on submit.
	 * 
	 * @param callbackAction
	 *            the callback action
	 * @param component
	 *            Component to test.
	 * @param markup
	 *            {@link SerializableUnaryOperator} for markup that is required
	 *            by this
	 * @see Markup
	 * @deprecated To be removed on bo2 v4 due to wicket changes
	 */
	@Deprecated
	public TestPage(ICallbackAction callbackAction, Component component, SerializableUnaryOperator<String> markup) {
		this.callbackAction = callbackAction;
		this.markup = markup;
		Form<Void> form = new Form<Void>(FORM_ID);
		add(form);
		form.add(component);
		form.add(new CallbackAjaxButton(SUBMIT_BUTTON_ID, new ResourceModel("submitButtonLabel"), //$NON-NLS-1$
				this::doInvoke, feedbackPanel));
	}

	/**
	 * Same as {@link #TestPage(Component)} but always uses a {@link Markup#div}
	 * as the markup to use.
	 *
	 * @param testSubject
	 *            Component to test.
	 */
	public TestPage(Component testSubject) {
		this(testSubject, Markup.div);
	}

	/**
	 * Action executed when we press the button.
	 * 
	 * @param target
	 * @param form
	 */
	void doInvoke(AjaxRequestTarget target, Form<?> form) {
		if (callbackAction != null) {
			callbackAction.doInvoke(target, form);
		}
	}

	@Override
	public String getCacheKey(MarkupContainer container, Class<?> containerClass) {
		return new DefaultMarkupCacheKeyProvider().getCacheKey(container, containerClass) + markup.toString();
	}

	@Override
	public IResourceStream getMarkupResourceStream(MarkupContainer container, Class<?> containerClass) {
		try {
			String result = StringUtils.concatSeparated(StringConstants.NEWLINE, StreamUtils.readResourceFile(MARKUP));
			return new StringAsResourceStream(result.replace("{0}", markup.apply(TEST_ID))); //$NON-NLS-1$
		} catch (IOException e) {
			// printing stacktrace and returning null - wicket will throw the
			// correct exception if this ever happens
			e.printStackTrace();
			return null;
		}
	}
}