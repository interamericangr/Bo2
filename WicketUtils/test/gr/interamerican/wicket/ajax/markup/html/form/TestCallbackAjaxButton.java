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
package gr.interamerican.wicket.ajax.markup.html.form;

import static org.junit.Assert.*;

import org.apache.wicket.ajax.AjaxRequestHandler;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.junit.Test;

import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.wicket.callback.LegacyCallbackAction;
import gr.interamerican.wicket.callback.MockedCallback;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Unit tests for {@link CallbackAjaxButton}
 */
@SuppressWarnings("nls")
public class TestCallbackAjaxButton extends WicketTest {
	/** model. */

	/** action. */
	private MockedCallback action = new MockedCallback();

	/**
	 * Test Constructor.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testConstructor() {
		FeedbackPanel panel = new FeedbackPanel("whatever");
		CallbackAjaxButton button = new CallbackAjaxButton("id", new Model<>(),
				(gr.interamerican.wicket.callback.ICallbackAction) action, panel);
		assertEquals(action, button.action);
		assertEquals(panel, button.feedbackPanel);
	}

	/**
	 * Test method for {@link CallbackAjaxButton#onError()}.
	 */
	@Test
	public void testOnError() {
		AjaxRequestTarget target = new AjaxRequestHandler(homePage());
		Form<Void> form = new Form<Void>("id");
		FeedbackPanel panel = new FeedbackPanel("panelId");

		CallbackAjaxButton button = new CallbackAjaxButton("id", new Model<>(), (LegacyCallbackAction) action, panel);
		panel.setOutputMarkupId(true);
		button.onError(target, form);

		assertEquals(1, target.getComponents().size());
		FeedbackPanel expectedFeedbackPanel = Utils.cast(target.getComponents().iterator().next());
		assertEquals(panel, expectedFeedbackPanel);
	}

	/**
	 * Test method for {@link CallbackAjaxButton#onSubmit()}
	 */
	@Test
	public void testOnSubmit() {
		AjaxRequestHandler target = new AjaxRequestHandler(homePage());
		FeedbackPanel panel = new FeedbackPanel("feedback");
		panel.setOutputMarkupId(true);
		Form<Void> form = new Form<>("id");

		CallbackAjaxButton button = new CallbackAjaxButton("id", (LegacyCallbackAction) action, panel);
		button.onSubmit(target, form);

		assertTrue(action.isExecuted());
		assertEquals(1, target.getComponents().size());
	}

	/**
	 * Test method for
	 * {@link CallbackAjaxButton#updateAjaxAttributes(AjaxRequestAttributes)}
	 */
	@Test
	public void testUpdateAjaxAttributes() {
		CallbackAjaxButton button = new CallbackAjaxButton("id", (LegacyCallbackAction) action,
				new FeedbackPanel("feedback"));
		AjaxRequestAttributes attributes = new AjaxRequestAttributes();
		button.updateAjaxAttributes(attributes);
		assertEquals(0, attributes.getAjaxCallListeners().size());
		button = new CallbackAjaxButton("id", null, (LegacyCallbackAction) action, new FeedbackPanel("feedback"),
				"someText");
		button.updateAjaxAttributes(attributes);
		assertEquals(1, attributes.getAjaxCallListeners().size());
	}
}