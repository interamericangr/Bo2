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

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;

/**
 * {@link CallbackWrapper} wraps another {@link CallbackAction}.
 */
public class CallbackWrapper implements CallbackAction {
	
	/**
	 * Callback action wrapped by this wrapper.
	 */
	CallbackAction action;
	
	/**
	 * Creates a new CallbackWrapper object. 
	 *
	 * @param action
	 */
	public CallbackWrapper(CallbackAction action) {
		super();
		this.action = action;
	}
	
	/**
	 * Creates a new CallbackWrapper object. 
	 */
	public CallbackWrapper() {
		this(null);
	}

	public void callBack(AjaxRequestTarget target) {
		before();
		before(target);
		if(action!=null) {
			action.callBack(target);
		}
		after();
		after(target);
	}

	public void callBack(AjaxRequestTarget target, Form<?> form) {
		before();
		before(target);
		if(action!=null) {
			action.callBack(target, form);
		}
		after();
		after(target);
	}	
	
	/**
	 * Things to do before executing the wrapped action.
	 */
	public void before(){ /* empty */ }
	
	/**
	 * Things to do before executing the wrapped action.
	 * @param target 
	 */
	public void before(AjaxRequestTarget target) { /* empty */
	}
	
	/**
	 * Things to do after executing the wrapped action.
	 */	
	public void after(){ /* empty */ }
	
	/**
	 * Things to do after executing the wrapped action.
	 * @param target 
	 */	
	public void after(AjaxRequestTarget target) { /* empty */
	}

	/**
	 * Gets the action wrapped by this one.
	 *
	 * @return Returns the action
	 */
	public CallbackAction getAction() {
		return action;
	}

	/**
	 * Sets the action wrapped by this one.
	 *
	 * @param action the action to set
	 */
	public void setAction(CallbackAction action) {
		this.action = action;
	}

	public void setCaller(Component caller) {
		if(action!=null) {
			action.setCaller(caller);
		}
	}

	public Component getCaller() {
		if(action!=null) {
			return action.getCaller();
		}
		return null;
	}

}
