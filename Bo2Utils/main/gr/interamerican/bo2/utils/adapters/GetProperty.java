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

import gr.interamerican.bo2.utils.JavaBeanUtils;
import gr.interamerican.bo2.utils.StringUtils;

import java.beans.PropertyDescriptor;

/**
 * Applies a property getter.
 * 
 * Gets the property with the specified name from the specified object.
 * If the specified argument is null, then the adapter will return null.
 * 
 * @param <A> 
 *        Type of argument.
 * @param <R> 
 *        Type of result.
 */
public class GetProperty<A,R> implements Transformation<A, R>{
	
	/**
	 * Property to get.
	 */
	PropertyDescriptor property;
	
	/**
	 * Property expression
	 */
	String propertyName;
	
	/**
	 * Indication if this is a nested property.
	 */
	Boolean isNestedProperty = false;
	
	@SuppressWarnings("unchecked")
	public R execute(A a) {		
		if (a==null) {
			return null;
		}
		if (isNestedProperty) {
			return (R) JavaBeanUtils.getNestedProperty(a, propertyName);
		} else {
			return (R) JavaBeanUtils.getProperty(property, a);
		}
		
	}

	/**
	 * Creates a new GetProperty object. 
	 *
	 * @param propertyName
	 *        Name of property to get.
	 * @param clazz 
	 *        Type of argument.
	 */
	public GetProperty(String propertyName, Class<A> clazz) {
		super();
		isNestedProperty = JavaBeanUtils.getResolver().hasNested(propertyName);
		this.property = JavaBeanUtils.getPropertyDescriptor(clazz, propertyName);
		if (this.property==null) {
			@SuppressWarnings("nls")
			String msg = StringUtils.concat
				("Property ", propertyName, " does not exist in class ", clazz.getName());
			throw new RuntimeException(msg);
		}
		this.propertyName = propertyName;
	}

	

}
