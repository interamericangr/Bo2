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
 * Wrapper class between an {@link SimpleAjaxCondition} to an
 * {@link AjaxCondition}.<br>
 * This will make a check on the input object and will register an error message
 * on the caller if an exception occurs.<br>
 * Otherwise it assumes that the validation passed.
 * 
 * @param <T>
 *            Type of object being checked
 */
public class SimpleAjaxConditionWrapper<T> implements AjaxCondition<T> {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@link SimpleAjaxCondition} to use
	 */
	private final SimpleAjaxCondition<T> condition;

	/**
	 * Public Constructor
	 * 
	 * @param condition
	 *            {@link SimpleAjaxCondition} to use
	 */
	public SimpleAjaxConditionWrapper(SimpleAjaxCondition<T> condition) {
		this.condition = condition;
	}

	@Override
	public boolean check(T t, AjaxRequestTarget target, Component caller) {
		try {
			condition.check(t);
		} catch (Exception e) {
			caller.error(e.getMessage());
			target.add(caller);
			return false;
		}
		return true;
	}
}