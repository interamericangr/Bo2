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

import gr.interamerican.bo2.utils.JavaBeanUtils;

import java.beans.PropertyDescriptor;

/**
 * Checks if an object is not initialized, in the sense that
 * all of its properties are null.
 * 
 * @param <T> 
 *        Type of object being checked. 
 */
public class NotInitialized<T> 
implements Condition<T> {		
	/**
	 * Array with the property descriptors of the properties of 
	 * the class of T.
	 */
	PropertyDescriptor[] properties;
	
	

	
	/**
	 * Creates a new NotInitialized object. 
	 *
	 * @param clazz
	 */
	public NotInitialized(Class<T> clazz) {
		super();
		properties = JavaBeanUtils.getBeansProperties(clazz);
	}


	public boolean check(T t) {
		for (int i = 0; i < properties.length; i++) {
			Object value = JavaBeanUtils.getProperty(properties[i], t);
			if (value!=null) {
				return false;
			}
		}
		return true;
	}

}
