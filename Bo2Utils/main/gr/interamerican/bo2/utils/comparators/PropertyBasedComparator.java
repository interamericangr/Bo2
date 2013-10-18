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
package gr.interamerican.bo2.utils.comparators;

import gr.interamerican.bo2.utils.JavaBeanUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.Utils;

import java.beans.PropertyDescriptor;
import java.util.Comparator;

/**
 * Comparator based on the value of a property of two objects.
 * 
 * This comparator compares two objects that belong to the same type 
 * by comparing the values of a specified property on both objects.
 * The type of the property used for comparison must be a sub-type
 * of Comparable. <br/> 
 *  
 * @param <T> Type of objects being compared by this class.
 */
public class PropertyBasedComparator<T> 
implements Comparator<T> {
	
	/**
	 * Property expression. This may describe a composite property.
	 */
	private String property;
	
	/**
	 * Property descriptor that describes the property of the class used to
	 * compare two different objects.
	 */
	private PropertyDescriptor pd;
	
	/**
	 * ������� �� ��������� ��� nested property.
	 */
	private boolean isNestedProperty;

	/**
	 * Creates a new PropertyBasedComparator object. 
	 *
	 * @param type
	 * @param property 
	 */
	public PropertyBasedComparator(Class<T> type, String property) {
		super();
		this.property = property;
		this.isNestedProperty = JavaBeanUtils.getResolver().hasNested(property);
		pd = JavaBeanUtils.mandatoryPropertyOfClass(property, type);
		Class<?> propertyType = pd.getPropertyType();
		if (!Comparable.class.isAssignableFrom(propertyType)) {
			@SuppressWarnings("nls")
			String msg = StringUtils.concat(
					"The property ", property, " of type ", type.getName(), 
					" is of type ", propertyType.getName(), " which is not Comparable"); 
			throw new RuntimeException(msg);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int compare(T o1, T o2) {
		Object prop1 = null;
		Object prop2 = null;
		if(isNestedProperty) {
			prop1 = JavaBeanUtils.getNestedProperty(o1, property);
			prop2 = JavaBeanUtils.getNestedProperty(o2, property);
		} else {
			prop1 = JavaBeanUtils.getProperty(pd, o1);
			prop2 = JavaBeanUtils.getProperty(pd, o2);
		}
		Comparable c1 = (Comparable) prop1;
		Comparable c2 = (Comparable) prop2;
		return Utils.nullSafeCompare(c1, c2);
	}
	
}
