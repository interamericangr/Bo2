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
 * Condition that combines other conditions using OR.
 * 
 * The condition is true if any of the conditions specified in the
 * constructors is true.
 * 
 * @param <T>  
 *        Type of object being checked.
 */
public class Or<T> implements Condition<T>{
	/**
	 * Conditions to check.
	 */
	List<Condition<T>> conditions;
	
	@Override
	public boolean check(T t) {
		for (Condition<T> condition : conditions) {
			if (condition.check(t)) {
				return true;
			}
		}
		return false;
	}


	/**
	 * Creates a new And object. 
	 *
	 * @param conditions the conditions
	 */
	@SafeVarargs
	public Or(Condition<T>... conditions) {
		this(Arrays.asList(conditions));
	}
	
	/**
	 * Creates a new And object. 
	 *
	 * @param conditions the conditions
	 */
	public Or(List<Condition<T>> conditions) {
		this.conditions = conditions;
	}
}