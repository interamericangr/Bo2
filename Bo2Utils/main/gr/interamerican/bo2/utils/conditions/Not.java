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
 * Condition that creates the negative check of a specified
 * condition.
 * 
 * @param <T>  
 *        Type of object being checked.
 */
public class Not<T> implements Condition<T>{
	/**
	 * Conditions to negate.
	 */
	Condition<T> condition;

	
	public boolean check(T t) {		
		return !condition.check(t);
	}

	/**
	 * Creates a new Not object. 
	 *
	 * @param condition
	 */
	public Not(Condition<T> condition) {
		this.condition = condition;
	}

}
