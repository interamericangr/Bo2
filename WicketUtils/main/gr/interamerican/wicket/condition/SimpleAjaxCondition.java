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

import java.io.Serializable;

/**
 * A more simplified variation of the {@link AjaxCondition}.<br>
 * {@link SimpleAjaxConditionWrapper} is a wrapper between this and an
 * {@link AjaxCondition}.
 * 
 * @param <T>
 *            Type of object being checked.
 */
@FunctionalInterface
public interface SimpleAjaxCondition<T> extends Serializable {

	/**
	 * Does the Check.
	 * 
	 * @param t
	 *            Object to check
	 * @throws Exception
	 *             If the validation failed
	 */
	public void check(T t) throws Exception;
}