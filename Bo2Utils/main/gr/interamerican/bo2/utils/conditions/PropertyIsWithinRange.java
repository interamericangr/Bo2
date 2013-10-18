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

import gr.interamerican.bo2.utils.beans.Range;

/**
 * Condition that checks if a property of an object is within
 * a range.
 * 
 * @param <T>
 *        Type of objects being checked by the condition. 
 * @param <P>
 *        Type of the property. Must be comparable.  
 * 
 */
public class PropertyIsWithinRange<T, P extends Comparable<? super P >> 
extends ConditionOnProperty<T> {

	/**
	 * Creates a new PropertyExistsInSetCondition object. 
	 *
	 * @param property
	 *        Name of property.
	 * @param clazz
	 *        Class of objects being checked by the condition.
	 * @param range
	 *        range.
	 */
	public PropertyIsWithinRange(String property, Class<T> clazz, Range<P> range) {
		super(property, clazz, new IsWithinRange<P>(range));		
	}

}
