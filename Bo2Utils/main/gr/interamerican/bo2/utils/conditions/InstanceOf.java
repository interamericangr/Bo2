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
 * Checks if the specified object is an instance of the class that is 
 * specified in the Condition's constructor.
 * 
 * If the specified object is null, then the condition will return false.
 */
public class InstanceOf implements Condition<Object> {

	/**
	 * Class being checked.
	 */
	Class<?> clazz;
	
	/**
	 * Creates a new InstanceOf object. 
	 *
	 * @param clazz
	 */
	public InstanceOf(Class<?> clazz) {
		super();
		this.clazz = clazz;
	}

	public boolean check(Object t) {
		if (t==null) {
			return false;
		}
		return clazz.isAssignableFrom(t.getClass());		
	}

}
