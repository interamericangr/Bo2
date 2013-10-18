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

import java.util.Collection;

/**
 * Condition that checks if a property of an object exists in a collection.
 * 
 * @param <T>
 *        Type of objects being checked by the condition. 
 * 
 */
public class PropertyExistsIn<T> 
extends ConditionOnProperty<T> {

	/**
	 * Creates a new PropertyExistsInSetCondition object. 
	 *
	 * @param property
	 *        Name of property.
	 * @param clazz
	 *        Class of objects being checked by the condition.
	 * @param collection
	 *        Values.
	 */
	public PropertyExistsIn(String property, Class<T> clazz, Collection<?> collection) {
		super(property, clazz, new ExistsIn<Object>(collection));		
	}
	
	/**
	 * Creates a new PropertyExistsInSetCondition object. 
	 *
	 * @param property
	 *        Name of property.
	 * @param clazz
	 *        Class of objects being checked by the condition.
	 * @param collection
	 *        Values.
	 */
	public PropertyExistsIn(String property, Class<T> clazz, Object... collection) {
		super(property, clazz, new ExistsIn<Object>(collection));		
	}

}
