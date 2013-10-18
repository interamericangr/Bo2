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
package gr.interamerican.wicket.factories;

import gr.interamerican.wicket.ajax.markup.html.form.CallbackAjaxButton;
import gr.interamerican.wicket.callback.CallbackAction;

import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;

/**
 * Factory of Bo2 enabled wicket components. 
 */
public class ButtonFactory {

	/**
	 * Private empty constructor of a utility class.
	 */
	private ButtonFactory() {/* empty */}
	
	
	/**
	 * creates a new AjaxButton that executes a {@link CallbackAction}.
	 * 
	 * @param id 
	 *        Button id.
	 * @param model
	 *        Button model.
	 * @param action
	 *        CallbackAction that will be executed on button press.
	 * @param feedbackPanel
	 * 		  the feedbackPanel that will be rendered. 
	 *        
	 * @return Returns a new AjaxButton that will execute the specified
	 *         Bo2WicketBlock.
	 */
	public static AjaxButton createButton 
	(final String id, final IModel<String> model, final CallbackAction action, final FeedbackPanel feedbackPanel) {		
		return new CallbackAjaxButton(id, model,action,feedbackPanel);		
	}
	
	/**
	 * Creates a new AjaxButton that executes a {@link CallbackAction}.
	 * 
	 * The button will be created with a ResourceModel for the expression
	 * button.id , where id is the id specified. 
	 * 
	 * @param id 
	 *        Button id.
	 * @param action
	 *        CallbackAction that will be executed on button press.
	 *        
	 * @return Returns a new AjaxButton that will execute the specified
	 *         Bo2WicketBlock.
	 */
	public static AjaxButton createButton 
	(final String id, final CallbackAction action) {
		return new CallbackAjaxButton(id,action,null);
	}
	
	/**
	 * Creates a new AjaxButton that executes a {@link CallbackAction}.
	 * 
	 * The button will be created with a ResourceModel for the expression
	 * button.id , where id is the id specified. 
	 * 
	 * @param id 
	 *        Button id.
	 * @param action
	 *        Callback action that will be executed on button press.
	 * @param feedbackPanel the panel that will be rendered, onError.
	 *        
	 * @return Returns a new AjaxButton that will execute the specified
	 *         Bo2WicketBlock.
	 */
	public static AjaxButton createButton 
	(final String id, final CallbackAction action , final FeedbackPanel feedbackPanel) {
		return new CallbackAjaxButton(id, action,feedbackPanel);
	}
	

		
	
	
}
