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
 * Action Performed when something is consumed.
 * 
 * @param <B>
 *            Type of picked item
 */
@FunctionalInterface
public interface Consume<B> extends Serializable {

	/**
	 * The Action
	 * 
	 * @param bean
	 *            The Item
	 * @throws Exception
	 */
	void consume(B bean) throws Exception;

	/**
	 * Returns a new instance of this that will invoke the input
	 * {@link LegacyCallbackAction} after this is executed.
	 * 
	 * @param action
	 *            Action to Chain After
	 * @return New {@link Consume}
	 */
	default Consume<B> doChainAfter(LegacyCallbackAction action) {
		return in -> {
			consume(in);
			action.doInvoke(RequestCycle.get().find(AjaxRequestTarget.class));
		};
	}
}