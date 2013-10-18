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
 * Checks that all listed properties of an object are not null.
 * 
 * @param <T> 
 *        Type of object being checked.
 */
public class PropertiesAreNotNull<T> 
implements Condition<T> {
	
	/**
	 * Condition to delegate the check.
	 */
	Condition<T> condition;

	/**
	 * Creates a new PropertiesAreEitherFullOrEmpty object.
	 *  
	 * @param clazz 
	 *        Class of object being checked.
	 * @param properties 
	 *        Names of properties that must be not null.
	 *
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PropertiesAreNotNull(Class<T> clazz, String... properties) {
		super();
		Condition[] isNotNull = new Condition[properties.length];
		for (int i = 0; i < properties.length; i++) {
			isNotNull[i] = new PropertyIsNotNull(properties[i], clazz);
		}
		condition = new And<T>(isNotNull);
	}

	public boolean check(T t) {		
		return condition.check(t);
	}
	
	
	
	
	
	
	

}
