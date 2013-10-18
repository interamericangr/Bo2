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

import gr.interamerican.wicket.callback.CallbackAction;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;

/**
 * AjaxButton that executes a callback action.
 */
public class CallbackAjaxButton extends AjaxButton {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Action to execute when pressed.
	 */
	 CallbackAction action;
	
	/**
	 * Feedback panel for error messages.
	 */
	 FeedbackPanel feedbackPanel;

	/**
	 * Creates a new CallbackAjaxButton object. 
	 *
	 * @param id
	 *        Id of the button.
	 * @param model
	 *        Model for the button.
	 * @param action 
	 *        Callback action to be executed on the button press.
	 * @param feedbackPanel 
	 *        Feedback panel for error messages.
	 */
	public CallbackAjaxButton(String id, IModel<String> model, CallbackAction action, FeedbackPanel feedbackPanel) {
		super(id, model);
		this.action = action;
		this.feedbackPanel = feedbackPanel;		
	}
	
	/**
	 * Creates a new CallbackAjaxButton object. 
	 * 
	 * This constructor will create a {@link StringResourceModel} for the button
	 * with the value button.id.
	 *
	 * @param id
	 *        Id of the button.
	 * @param action 
	 *        Callback action to be executed on the button press.
	 * @param feedbackPanel 
	 *        Feedback panel for error messages.
	 */
	public CallbackAjaxButton(String id, CallbackAction action, FeedbackPanel feedbackPanel) {
		this(id, new ResourceModel("button."+id), action, feedbackPanel); //$NON-NLS-1$
	}
	
	/**
	 * Creates a new CallbackAjaxButton object. 
	 * 
	 * This constructor will create a {@link StringResourceModel} for the button
	 * with the value button.id.
	 *
	 * @param id
	 *        Id of the button.
	 * @param action 
	 *        Callback action to be executed on the button press.
	 */
	public CallbackAjaxButton(String id, CallbackAction action) {
		this(id, action, null);
	}	
	
	/**
	 * Creates a new CallbackAjaxButton object. 
	 *
	 * @param id
	 *        Id of the button.
	 * @param model
	 *        Model for the button.
	 * @param action 
	 *        Callback action to be executed on the button press.
	 * 
	 */
	public CallbackAjaxButton(String id, IModel<String> model, CallbackAction action) {
		this(id, model, action, null);		
	}
	
	@Override
	protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		action.callBack(target, form);
		renderFeedbackPanel(target);
	}
	
	@Override
	public void onError(AjaxRequestTarget target, Form<?> arg1){
		renderFeedbackPanel(target);
	}

	/**
	 * Render the {@link FeedbackPanel}.
	 * @param target
	 */
	private void renderFeedbackPanel(AjaxRequestTarget target) {
		if(feedbackPanel != null) {
			target.addComponent(feedbackPanel);
		}
	}

	/**
	 * Gets the action.
	 *
	 * @return Returns the action
	 */
	public CallbackAction getAction() {
		return action;
	}

}
