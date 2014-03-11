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
package gr.interamerican.wicket.samples.actions;

import gr.interamerican.wicket.callback.CallbackAction;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;

/**
 * Dummy callback action.
 * 
 * This action does nothing, however, it has a field that tells if
 * it has been executed.
 */
public class DummyCallback implements CallbackAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Shows if it has been excuted.
	 */
	private boolean executed = false;
	
	public void callBack(AjaxRequestTarget target) {
		executed = true;		
	}

	public void callBack(AjaxRequestTarget target, Form<?> form) {
		executed = true;
	}

	/**
	 * Gets the executed.
	 *
	 * @return Returns the executed
	 */
	public boolean isExecuted() {
		return executed;
	}

	/**
	 * Assigns a new value to the executed.
	 *
	 * @param executed the executed to set
	 */
	public void setExecuted(boolean executed) {
		this.executed = executed;
	}

	public void setCaller(Component caller) { /* empty */ }

	public Component getCaller() {
		return null;
	}

}
