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
 * Checks a condition on a property of the specified object.
 * 
 * This condition delegates the check to a condition that check the
 * value of a property of the specified object.
 * 
 * @param <T> 
 *        Type of object being checked by the condition.
 * 
 */
public class PropertiesEqual<T> 
implements Condition<T>{
	
	/**
	 * Checks equality of all specified properties.
	 */
	And<T> and;
	
	/**
	 * Creates a new PropertiesEqual object. 
	 *
	 * @param properties
	 * @param values
	 * @param clazz
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PropertiesEqual(String[] properties, Object[] values, Class<T> clazz) {
		ConditionOnProperty[] conditions = 
			new ConditionOnProperty[properties.length];
		for (int i = 0; i < conditions.length; i++) {
			conditions[i]  = new PropertyEqualsTo(properties[i], clazz, values[i]);
		}
		and = new And(conditions);		
	}

	public boolean check(T t) {		
		return and.check(t);
	}

}
