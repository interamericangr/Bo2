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

/**
 * {@link PropertyValuesChecker} that checks that the specified
 * properties are not null.
 * 
 * @param <T> 
 *        Type of object being checked.
 */
public class PropertyValuesNotNullChecker<T> 
extends PropertyValuesChecker<T>{

	/**
	 * Creates a new PropertyValuesNotNullChecker object. 
	 *
	 * @param properties
	 * @param messages
	 */
	public PropertyValuesNotNullChecker(String[] properties, String[] messages) {
		super(properties, new Object[properties.length], messages);		
	}

}
