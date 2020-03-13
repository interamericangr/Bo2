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
import org.apache.wicket.markup.html.form.Form;

/**
 * An Action performed with input an {@link AjaxRequestTarget} and a
 * {@link Form}.
 * 
 * @deprecated To be removed on bo2 v4 due to wicket changes
 */
@Deprecated
@FunctionalInterface
public interface ICallbackAction extends Serializable {

	/**
	 * Method that provides means for other entities to request a callBack that
	 * performs a specific action.
	 *
	 * @param target
	 *            the target
	 * @param form
	 *            the form
	 * @throws Exception
	 */
	public void invoke(AjaxRequestTarget target, Form<?> form) throws Exception;

	/**
	 * Calls {@link #invoke(AjaxRequestTarget, Form)} and wraps any
	 * {@link Exception}'s and re-throws them as {@link RuntimeException}
	 * 
	 * @param target
	 *            the target
	 * @param form
	 */
	public default void doInvoke(AjaxRequestTarget target, Form<?> form) {
		try {
			invoke(target, form);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Wrapper that returns a {@link ICallbackAction} from a
	 * {@link LegacyCallbackAction}.
	 * 
	 * @param legacy
	 *            The {@link LegacyCallbackAction}
	 * @return The corresponding {@link ICallbackAction}.
	 */
	static ICallbackAction fromLegacy(LegacyCallbackAction legacy) {
		return (t, f) -> legacy.invoke(t);
	}
}