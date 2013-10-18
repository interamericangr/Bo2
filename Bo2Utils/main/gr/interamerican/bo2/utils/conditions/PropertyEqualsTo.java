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
 * @param <T> 
 * 
 */
public class PropertyEqualsTo<T> 
extends ConditionOnProperty<T> {

	/**
	 * Creates a new PropertyEqualsCondition object. 
	 *
	 * @param property
	 * @param clazz
	 * @param value
	 */
	public PropertyEqualsTo(String property, Class<T> clazz, Object value) {
		super(property, clazz, new EqualityCondition<Object>(value));
	}

}
