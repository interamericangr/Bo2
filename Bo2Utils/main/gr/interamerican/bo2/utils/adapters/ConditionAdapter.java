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
package gr.interamerican.bo2.utils.adapters;

import gr.interamerican.bo2.utils.conditions.Condition;

/**
 * Adapter for a {@link Condition}.
 * 
 * This class adapts the {@link Condition} interface to the
 * {@link AnyOperation} interface. This adapter operation will 
 * return for each object the result of the condition's check 
 * on it.
 * 
 * @param <T> 
 *        Type of object being checked against the condition.
 */
public class ConditionAdapter<T> 
implements AnyOperation<T, Boolean> {
	/**
	 * Adapted condition.
	 */
	Condition<T> condition;

	/**
	 * Creates a new ConditionAdapter object. 
	 *
	 * @param condition
	 */
	public ConditionAdapter(Condition<T> condition) {
		super();
		this.condition = condition;
	}
	
	public Boolean execute(T a) {
		return condition.check(a);		
	}          

}
