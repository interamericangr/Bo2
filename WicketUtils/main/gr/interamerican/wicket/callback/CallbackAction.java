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
package gr.interamerican.wicket.callback;

import gr.interamerican.bo2.utils.handlers.Called;

import java.io.Serializable;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;

/**
 * Callback action. Basic interface for the implementation of
 * the command pattern in Wicket components.
 */
public interface CallbackAction
extends Called<Component>, Serializable {
	
	/**
	 * Method that provides means for other entities to request a callBack
	 * that performs a specific action.
	 * 
	 * @param target 
	 */
	public void callBack(AjaxRequestTarget target);
	
	/**
	 * Method that provides means for other entities to request a callBack
	 * that performs a specific action.
	 * 
	 * @param target 
	 * @param form 
	 */
	public void callBack(AjaxRequestTarget target, Form<?> form);

}
