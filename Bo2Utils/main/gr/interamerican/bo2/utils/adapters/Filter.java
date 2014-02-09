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
 * The filter's execute operation will return any object 
 * that fulfills the specified condition. For objects that
 * do not fulfill the specified condition, the Filter's
 * execute method will return null.
 * 
 * @param <T>
 *        Type of objects being filtered. 
 */
public class Filter<T> implements Transformation<T, T> {
	/**
	 * Creates a new Filter object. 
	 *
	 * @param condition
	 *        Condition for the filter.
	 */
	public Filter(Condition<T> condition) {
		super();
		this.condition = condition;
	}


	/**
	 * Condition being checked.
	 */
	Condition<T> condition;

	
	public T execute(T a) {
		boolean check = condition.check(a);
		if (check) {
			return a;
		}
		return null;
	}

}
