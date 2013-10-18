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
package gr.interamerican.bo2.utils.matching;

/**
 * Matching rule that checks the value of a property.
 * 
 * @param <T> Type of objects.
 * 
 */
public class SameTypeEqualPropertyMatchingRule<T> 
extends EqualPropertyMatchingRule<T, T> {

	/**
	 * Creates a new SameTypePropertyMatchingRule object. 
	 *
	 * @param clazz 
	 *        Type of objects. 
	 * @param property
	 *        Name of the property being used for matching.
	 */
	public SameTypeEqualPropertyMatchingRule(Class<T> clazz, String property) {
		super(clazz, clazz, property, property);
	}
	
}
