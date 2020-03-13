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
package gr.interamerican.bo2.utils.conditions;

/**
 * The Class PropertyEqualsTo.
 *
 * @param <T>
 *            the generic type
 * @deprecated Use {@link gr.interamerican.bo2.utils.conditions.functional.PropertyEqualsTo}
 */
@Deprecated
public class PropertyEqualsTo<T> extends ConditionOnProperty<T> {

	/**
	 * Creates a new PropertyEqualsCondition object.
	 *
	 * @param property
	 *            the property
	 * @param clazz
	 *            the clazz
	 * @param value
	 *            the value
	 */
	public PropertyEqualsTo(String property, Class<T> clazz, Object value) {
		super(property, clazz, new EqualityCondition<Object>(value));
	}
}