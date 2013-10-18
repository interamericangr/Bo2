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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;

/**
 * CallbackAction that executes an action that does not depend
 * on the {@link AjaxRequestTarget} or the {@link Form}. <br/>
 * 
 * This CallbackAction has a method <code>work()</code> that is
 * executed always.
 */
public abstract class SimpleCallbackAction 
extends AbstractCallbackAction {

	
	public void callBack(AjaxRequestTarget target) {
		work();
	}

	public void callBack(AjaxRequestTarget target, Form<?> form) {
		work();
	}
	
	/**
	 * Main method of the CallbackAction.
	 */
	protected abstract void work();
	
}
