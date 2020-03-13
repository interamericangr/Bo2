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
 * Abstract implementation of {@link AjaxCondition} that allows to
 * specify a caller component. This can be used to register feedback messages to
 * the component that actually executes the code instead of the component that
 * declares it.
 * 
 * @param <T>
 *            Type of object being checked.
 * @deprecated Switch To {@link AjaxCondition} or {@link SimpleAjaxCondition}
 */
@Deprecated
public abstract class AbstractAjaxEnabledCondition<T> implements AjaxCondition<T> {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Caller component.
	 */
	protected Component caller;

	/**
	 * Checks if the condition is met for the specified object.
	 * 
	 * @param t
	 *        Object being checked against the condition.
	 * @param target
	 *        AjaxRequestTarget.
	 *        
	 * @return Returns true if the condition is fulfilled by the
	 *         specified object.
	 */
	public abstract boolean check(T t, AjaxRequestTarget target);

	@Override
	public boolean check(T t, AjaxRequestTarget target, Component c) {
		this.caller = c;
		return check(t, target);
	}

	/**
	 * Sets the caller.
	 *
	 * @param caller
	 *            the new caller
	 */
	public void setCaller(Component caller) {
		this.caller = caller;
	}
}