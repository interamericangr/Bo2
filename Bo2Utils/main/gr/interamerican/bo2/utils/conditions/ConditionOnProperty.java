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

import gr.interamerican.bo2.utils.adapters.GetProperty;

/**
 * Checks a condition on a property of the specified object.
 * 
 * This condition delegates the check to a condition that check the
 * value of a property of the specified object.
 * 
 * @param <T> 
 *        Type of object being checked by the condition.
 * 
 */
public class ConditionOnProperty<T> 
extends ConditionOnTransformation<T, Object>  {

	/**
	 * Creates a new ConditionOnProperty object. 
	 * 
	 * @param property 
	 *        Property name.
	 * @param clazz
	 *        Type of argument.
	 * @param condition
	 *        Condition to check on the property. This condition must be
	 *        applicable to the type of the specified property.
	 */
	@SuppressWarnings("unchecked")
	public ConditionOnProperty(String property, Class<T> clazz, Condition<?> condition) {		
		super(new GetProperty<T, Object>(property,clazz), (Condition<Object>) condition);
	}

}
