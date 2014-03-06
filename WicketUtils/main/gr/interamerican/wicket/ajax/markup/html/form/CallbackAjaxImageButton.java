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
import gr.interamerican.wicket.utils.MarkupConstants;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;

/**
 * An image ajaxButton that executes a callback action. 
 */
public class CallbackAjaxImageButton extends CallbackAjaxButton {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The url of image.
	 */
	private String imageUrl;

	/**
	 * Creates a new CallbackAjaxImageButton object. 
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
	 * @param imageUrl
	 * 		  The specified url of image. 
	 */
	public CallbackAjaxImageButton(String id, CallbackAction action, FeedbackPanel feedbackPanel , String imageUrl) {
		super(id, action, feedbackPanel);
		this.imageUrl = imageUrl;
	}

	/**
	 * Creates a new CallbackAjaxImageButton object. 
	 *
	 * @param id
	 *        Id of the button.
	 * @param model
	 *        Model for the button.
	 * @param action 
	 *        Callback action to be executed on the button press.
	 * @param feedbackPanel 
	 *        Feedback panel for error messages.
	 * @param imageUrl 
	 *        The specified url of image.
	 *        
	 */
	public CallbackAjaxImageButton(String id, IModel<String> model, CallbackAction action, FeedbackPanel feedbackPanel, String imageUrl) {
		super(id, model, action, feedbackPanel);
		this.imageUrl = imageUrl;
	}

	@Override
	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		String contextRelativeUrl = getRequestCycle().getUrlRenderer().renderContextRelativeUrl(imageUrl);
		tag.put(MarkupConstants.SRC,contextRelativeUrl); 
	}

}
