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

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * An Action performed with input an {@link AjaxRequestTarget}.
 */
@FunctionalInterface
public interface LegacyCallbackAction extends Serializable {

	/**
	 * Method that provides means for other entities to request a callBack that
	 * performs a specific action.
	 *
	 * @param target
	 *            the target
	 * @throws Exception
	 *             Exception
	 */
	public void invoke(AjaxRequestTarget target) throws Exception;

	/**
	 * Calls {@link #invoke(AjaxRequestTarget)} and wraps any
	 * {@link Exception}'s and re-throws them as {@link RuntimeException}
	 * 
	 * @param target
	 *            the target
	 */
	public default void doInvoke(AjaxRequestTarget target) {
		try {
			invoke(target);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Invokes the CalledFromComponent#setCaller(Component) if this is a
	 * {@link CalledFromComponent}.
	 * 
	 * @param component
	 *            {@link Component} to set
	 * 
	 * @deprecated This is for backwards compatibility only. To be removed
	 */
	@Deprecated
	default void setComponent(Component component) {
		if (this instanceof CalledFromComponent) {
			((CalledFromComponent) this).setCaller(component);
		}
	}
}