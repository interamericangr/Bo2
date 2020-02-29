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
package gr.interamerican.bo2.utils.conditions.functional;

import java.util.function.Function;

import gr.interamerican.bo2.utils.conditions.ConditionOnTransformation;
import gr.interamerican.bo2.utils.conditions.IsNull;

/**
 * Checks if the specified property is null.
 *
 * @param <T>
 *            the generic type
 */
public class PropertyIsNull<T> extends ConditionOnTransformation<T, Object> {

	/**
	 * Public Constructor.
	 * 
	 * @param getter
	 *            Getter
	 */
	public PropertyIsNull(Function<T, ?> getter) {
		super(getter::apply, new IsNull<Object>());
	}
}