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

/**
 * Action Performed when something is picked.
 * 
 * @param <B>
 *            Type of picked item
 */
@FunctionalInterface
public interface PickAction<B> extends Serializable {

	/**
	 * The Action
	 * 
	 * @param target
	 *            Current {@link AjaxRequestTarget}
	 * @param bean
	 *            The Picked Item
	 * @throws Exception
	 */
	void pick(AjaxRequestTarget target, B bean) throws Exception;

	/**
	 * Calls {@link #pick(AjaxRequestTarget, Object)} and wraps any
	 * {@link Exception}'s and re-throws the
	 * 
	 * @param target
	 *            Current {@link AjaxRequestTarget}
	 * @param bean
	 *            The Picked Item
	 */
	default void doPick(AjaxRequestTarget target, B bean) {
		try {
			pick(target, bean);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}