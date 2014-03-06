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
package gr.interamerican.wicket.bo2.factories;

import gr.interamerican.wicket.ajax.markup.html.form.CallbackAjaxButton;
import gr.interamerican.wicket.ajax.markup.html.form.CallbackAjaxImageButton;
import gr.interamerican.wicket.bo2.callbacks.MethodBasedBo2WicketBlock;
import gr.interamerican.wicket.callback.CallbackAction;
import gr.interamerican.wicket.def.FeedbackOwner;

import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.model.IModel;

/**
 * Factory of Bo2 enabled wicket components. 
 */
public class Bo2ButtonFactory {

	/**
	 * Private empty constructor of a utility class.
	 */
	private Bo2ButtonFactory() {/* empty */}	
	
	/**
	 * creates a new AjaxButton that executes a {@link CallbackAction}.
	 * 
	 * @param id 
	 *        Button id.
	 * @param model
	 *        Button model.
	 * @param messageHandler
	 *        Name of method being executed as message handler for the 
	 *        on submit event.	         
	 * @param owner
	 * 		  Owner of the message handler. 
	 *        
	 * @return Returns a new AjaxButton that will execute the specified
	 *         method.
	 */
	public static AjaxButton createButton 
	(final String id, final IModel<String> model, final String messageHandler, final FeedbackOwner owner) {	
		CallbackAction action = new MethodBasedBo2WicketBlock(messageHandler, owner);
		return new CallbackAjaxButton(id, model,action,owner.getFeedBackPanel());		
	}	
	
	/**
	 * creates a new AjaxButton that executes a {@link CallbackAction}.
	 * 
	 * @param id 
	 *        Button id.
	 * @param messageHandler
	 *        Name of method being executed as message handler for the 
	 *        on submit event.	         
	 * @param owner
	 * 		  Owner of the message handler. 
	 *        
	 * @return Returns a new AjaxButton that will execute the specified
	 *         method.
	 */
	public static AjaxButton createButton 
	(final String id, final String messageHandler, final FeedbackOwner owner) {	
		CallbackAction action = new MethodBasedBo2WicketBlock(messageHandler, owner);
		return new CallbackAjaxButton(id, action,owner.getFeedBackPanel());		
	}
	
	/**
	 * creates a new {@link CallbackAjaxImageButton}.
	 * 
	 * @param id 
	 *        Button id.
	 * @param model
	 *        Button model.
	 * @param messageHandler
	 *        Name of method being executed as message handler for the 
	 *        on submit event.	         
	 * @param owner
	 * 		  Owner of the message handler. 
	 * @param urlImage 
	 *        The specified url of image.
	 *        
	 * @return Returns a new image AjaxButton that will execute the specified
	 *         method.
	 */
	public static AjaxButton createImageButton 
	(final String id, final IModel<String> model, final String messageHandler, final FeedbackOwner owner, String urlImage) {	
		CallbackAction action = new MethodBasedBo2WicketBlock(messageHandler, owner);
		return new CallbackAjaxImageButton(id, model,action,owner.getFeedBackPanel(),urlImage);		
	}	
	
	/**
	 * creates a new {@link CallbackAjaxImageButton}.
	 * 
	 * @param id 
	 *        Button id.
	 * @param messageHandler
	 *        Name of method being executed as message handler for the 
	 *        on submit event.	         
	 * @param owner
	 * 		  Owner of the message handler. 
	 * @param urlImage 
	 *        The specified url of image.
	 *        
	 * @return Returns a new image AjaxButton that will execute the specified
	 *         method.
	 */
	public static AjaxButton createImageButton 
	(final String id, final String messageHandler, final FeedbackOwner owner, String urlImage) {	
		CallbackAction action = new MethodBasedBo2WicketBlock(messageHandler, owner);
		return new CallbackAjaxImageButton(id, action,owner.getFeedBackPanel(),urlImage);		
	}
	
}
