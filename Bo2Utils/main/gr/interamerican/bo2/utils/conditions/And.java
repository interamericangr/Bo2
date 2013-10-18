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

import java.util.Arrays;
import java.util.List;

/**
 * Condition that combines other conditions using AND.
 * 
 * The condition is true if all conditions specified in the
 * constructors are true.
 * 
 * @param <T>  
 *        Type of object being checked.
 */
public class And<T> implements Condition<T>{
	/**
	 * Conditions to check.
	 */
	List<Condition<T>> conditions;

	
	public boolean check(T t) {
		for (Condition<T> condition : conditions) {
			if (!condition.check(t)) {
				return false;
			}
		}
		return true;
	}


	/**
	 * Creates a new And object. 
	 *
	 * @param conditions
	 */
	public And(Condition<T>... conditions) {
		this(Arrays.asList(conditions));
	}
	
	/**
	 * Creates a new And object. 
	 *
	 * @param conditions
	 */
	public And(List<Condition<T>> conditions) {
		super();
		this.conditions = conditions;
	}

}
