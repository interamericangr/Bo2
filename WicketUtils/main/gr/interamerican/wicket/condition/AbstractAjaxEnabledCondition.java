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
package gr.interamerican.wicket.condition;


import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * Abstract implementation of {@link AjaxEnabledCondition} that
 * allows to specify a caller component. This can be used to
 * register feedback messages to the component that actually
 * executes the code instead of the component that declares it.
 * 
 * @param <T>
 *        Type of object being checked. 
 */
public abstract class AbstractAjaxEnabledCondition<T> 
implements AjaxEnabledCondition<T>{

	/**
	 * Caller component.
	 */
	protected Component caller;
	
	public abstract boolean check(T t, AjaxRequestTarget target);

	/**
	 * Sets the caller.
	 * 
	 * @param caller
	 */
	public void setCaller(Component caller) {
		this.caller = caller;
	}

}
