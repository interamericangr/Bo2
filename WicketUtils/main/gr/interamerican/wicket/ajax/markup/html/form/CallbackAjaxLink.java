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
import org.apache.wicket.ajax.markup.html.AjaxLink;

/**
 * An {@link AjaxLink} that executes a {@link CallbackAction}.
 * 
 * @param <T> 
 *        Type of model object.
 */
public class CallbackAjaxLink<T> extends AjaxLink<T> {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Action to execute when pressed.
	 */
	 CallbackAction action;
	
	/**
	 * Creates a new CallbackAjaxLink object. 
	 *
	 * @param id
	 *        Wicket id.
	 * @param action
	 *        CallbackAction executed on click. 
	 */
	public CallbackAjaxLink(String id, CallbackAction action) {
		super(id);
		this.action = action;
	}

	@Override
	public void onClick(AjaxRequestTarget target) {
		if(action!=null) {
			action.callBack(target);
		}
	}
	
	

}
