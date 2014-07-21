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

import gr.interamerican.wicket.callback.CallbackAction;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.util.tester.WicketTester;
import org.mockito.Mockito;

/**
 * Base class for unit test classes of Wicket components.
 * 
 * Provides a single argument constructor in order to make easy the
 * usage of {@link WicketTester}.
 */
public class TestPage 
extends BaseTestPage  {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Wicket id.
	 */
	public static final String SUBMIT_BUTTON_ID = "submitButton"; //$NON-NLS-1$
	
	/**
	 * Creates a new TestPage object. 
	 */
	public TestPage() {
		Form<Void> form = new Form<Void>(FORM_ID);
		add(form);		
		form.add(new EmptyPanel(TEST_ID));
		form.add(new TestPageAjaxButton(SUBMIT_BUTTON_ID));
	}
	
	/**
	 * Creates a new TestPage object. 
	 * @param callbackAction 
	 */
	public TestPage(CallbackAction callbackAction) {
		Form<Void> form = new Form<Void>(FORM_ID);
		add(form);		
		form.add(new EmptyPanel(TEST_ID));
		form.add(new TestPageAjaxButton(SUBMIT_BUTTON_ID, callbackAction));
	}

	/**
	 * Creates a new WrapperPanel object.
	 * 
	 * @param testSubject
	 *        Component to test.  
	 */
	public TestPage(Component testSubject) {
		Form<Void> form = new Form<Void>(FORM_ID);
		add(form);		
		form.add(testSubject);
		form.add(new TestPageAjaxButton(SUBMIT_BUTTON_ID));
	}
	
	/**
	 * AjaxButton that does nothing other than submitting the form.
	 */
	private class TestPageAjaxButton extends AjaxButton {

		/**
		 * serialVersionUID.
		 */
		private static final long serialVersionUID = 1L;
		
		/**
		 * Callback
		 */
		private CallbackAction callbackAction = Mockito.mock(CallbackAction.class);
		
		/**
		 * Creates a new TestPageAjaxButton object. 
		 * @param id
		 */
		public TestPageAjaxButton(String id) {
			super(id);
		}
		
		/**
		 * Creates a new TestPageAjaxButton object. 
		 * @param id
		 * @param callbackAction 
		 */
		public TestPageAjaxButton(String id, CallbackAction callbackAction) {
			super(id);
			this.callbackAction = callbackAction;
		}
    	
		@Override
		protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
			callbackAction.callBack(target);
		}
		
		@Override
		protected void onError(AjaxRequestTarget target, Form<?> form) {
			target.add(feedbackPanel);
		}
		
    }
	

}
