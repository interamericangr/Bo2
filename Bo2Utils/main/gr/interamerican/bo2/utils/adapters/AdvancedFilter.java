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
 * Filters object against a condition. <br/>
 * 
 * The condition is specified in the Filter's constructor.
 * If the object fulfills the condition, then the object is 
 * transformed using an adapter specified in the Filter's 
 * constructor. For objects that do not fulfill the specified c
 * ondition, the Filter's execute method will return null.
 * 
 * @param <T>
 *        Type of objects being filtered. 
 * @param <R> 
 *        Type of output objects.
 */
public class AdvancedFilter<T,R> implements AnyOperation<T, R> {
	/**
	 * Creates a new Filter object. 
	 *
	 * @param condition
	 *        Condition for the filter.
	 * @param transformation 
	 *        Transformation applied on objects that
	 *        fulfill the condition. 
	 */
	public AdvancedFilter(Condition<T> condition, AnyOperation<T, R> transformation) {
		super();
		this.condition = condition;
		this.transformation = transformation;
	}


	/**
	 * Condition being checked.
	 */
	Condition<T> condition;
	
	/**
	 * Transformation executed on objects that fulfill
	 * the condition.
	 */
	AnyOperation<T, R> transformation;

	
	public R execute(T a) {
		boolean check = condition.check(a);
		if (check) {
			return transformation.execute(a);
		}
		return null; 
	}

}
