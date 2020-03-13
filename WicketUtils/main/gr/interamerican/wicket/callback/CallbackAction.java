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

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.cycle.RequestCycle;

/**
 * Callback action. <br>
 * 
 * Basic interface for the implementation of the command pattern in Wicket
 * components.
 * 
 * @deprecated Implement the specific interface you are required
 */
@SuppressWarnings("rawtypes")
@Deprecated
public interface CallbackAction extends CalledFromComponent, LegacyCallbackAction, ICallbackAction, PickAction,
		MultiplePickAction, Consume, ProcessAction, SearchAction {
	
	/**
	 * Method that provides means for other entities to request a callBack
	 * that performs a specific action.
	 *
	 * @param target the target
	 */
	public void callBack(AjaxRequestTarget target);
	
	/**
	 * Method that provides means for other entities to request a callBack
	 * that performs a specific action.
	 *
	 * @param target the target
	 * @param form the form
	 */
	public void callBack(AjaxRequestTarget target, Form<?> form);

	@Override
	default void invoke(AjaxRequestTarget target) {
		callBack(target);
	}

	@Override
	default void invoke(AjaxRequestTarget target, Form<?> form) {
		callBack(target, form);
	}

	@Override
	default void pick(AjaxRequestTarget target, Object bean) {
		callBack(target);
	}

	@Override
	default void doPick(AjaxRequestTarget target, List bean) {
		callBack(target);
	}

	@Override
	default void consume(Object bean) {
		callBack(RequestCycle.get().find(AjaxRequestTarget.class));
	}

	@Override
	default Object process(Object bean) {
		callBack(RequestCycle.get().find(AjaxRequestTarget.class));
		return bean;
	}

	@Override
	default List<Object> search(Object criteria) {
		callBack(RequestCycle.get().find(AjaxRequestTarget.class));
		return null;
	}
}