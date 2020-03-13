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

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.cycle.RequestCycle;

/**
 * Action Performed when something is processed.
 * 
 * @param <B>
 *            Type of picked item
 */
@FunctionalInterface
public interface ProcessAction<B> extends Serializable {

	/**
	 * The Action
	 * 
	 * @param bean
	 *            The Item
	 * @return The processed item
	 * @throws Exception
	 */
	B process(B bean) throws Exception;

	/**
	 * Returns a {@link ProcessAction} that will just return the input argument.
	 * 
	 * @return {@link ProcessAction} that does nothing
	 */
	static <B> ProcessAction<B> identity() {
		return t -> t;
	}

	/**
	 * Returns a new instance of this that will invoke the input
	 * {@link LegacyCallbackAction} before this is executed.
	 * 
	 * @param action
	 *            Action to Chain After
	 * @return New {@link ProcessAction}
	 */
	default ProcessAction<B> chainBefore(LegacyCallbackAction action) {
		return in -> {
			action.doInvoke(RequestCycle.get().find(AjaxRequestTarget.class));
			return process(in);
		};
	}

	/**
	 * Returns a new instance of this that will invoke the input
	 * {@link LegacyCallbackAction} after this is executed.
	 * 
	 * @param action
	 *            Action to Chain After
	 * @return New {@link ProcessAction}
	 */
	default ProcessAction<B> chainAfter(LegacyCallbackAction action) {
		return in -> {
			B out = process(in);
			action.doInvoke(RequestCycle.get().find(AjaxRequestTarget.class));
			return out;
		};
	}
}