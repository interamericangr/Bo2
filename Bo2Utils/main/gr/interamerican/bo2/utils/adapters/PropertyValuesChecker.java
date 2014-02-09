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
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Compares for equality property values of an object, against an
 * array of predefined values, and returns a list that contains
 * a predefined message for each property that was equal to the 
 * predefined value. <br/>
 * 
 * This transformation is intended to be used mainly for validation
 * of objects in cases when specific values of some properties are
 * not acceptable. In a typical case, some properties of an object 
 * are being compared against predefined not acceptable values 
 * (usually null) and for each property that has a not acceptable
 * value, a message should be returned by the system to indicate
 * that the property has a not acceptable value. This transformation
 * will do exactly that, and it will return a list that contains
 * the messages that must be returned by the system.<br/>
 * The property expressions can also be nested properties. This
 * class deals with possible nulls in nested properties. <br/>
 * <br/>
 * For example, assume that a class Person has a property named 
 * <code>address</code> of type Address and the class Address 
 * has the properties street and city and zipCode, which are strings.
 * If we want to check that address is not null, and also that
 * street and city of the person's address are not null or empty, then
 * the properties array should contain the property names:<br/>
 * <code>{"address", "address.city", "address.city", "address.street", 
 * "address.street"</code>.<br/>
 * The values array should be:<br/>
 * <code>{null, null, "", null, "",} </code>.<br/>
 * If the transformations finds that address is null, it will
 * omit to check the value of any nested property of address.
 * So in this case, if address is null, the transformation will
 * return a list with only the message for address being null.
 * In order to omit the checks of the nested properties, the
 * property that contains the nested ones should appear in the properties
 * array, before the nested ones. In this example, address must appear
 * if the array before address.street and address.city.
 *  <br/>
 * Note also that the same property can be compared against more
 * than one values. In the example above, address.city and address.street
 * are being check against null and empty string.
 * 
 * @param <T>
 *        Type of object who's properties are being checked. 
 */
public class PropertyValuesChecker<T>
implements Transformation<T, List<String>> {
	
	/**
	 * Array that contains the names of properties to check.
	 */
	String[] properties;
	/**
	 * Array that has the same size as the properties array and contains
	 * the value being compared for equality against the value of the property
	 * with the same index in the properties array.
	 */
	Object[] values;
	/**
	 * Array that contains the message that will included in the output list
	 * for each property that equals to the specified value.
	 */
	String[] messages;
	
	/**
	 * Creates a new PropertyValueChecker object. 
	 *
	 * @param properties
	 *        Array that contains the names of properties to check.
	 * @param values
	 *        Array that has the same size as the properties array and 
	 *        contains the value being compared for equality against 
	 *        the value of the property with the same index in the properties 
	 *        array.
	 * @param messages
	 *        Array with the same size as the properties array, that contains 
	 *        the message that will be included in the output list
	 *        for each property that equals to the specified value. 
	 * 
	 */
	public PropertyValuesChecker(String[] properties, Object[] values, String[] messages) {
		super();
		if (properties.length!=values.length) {
			String msg = "Different count of properties and values"; //$NON-NLS-1$
			throw new RuntimeException(msg);
		}
		if (properties.length!=messages.length) {
			String msg = "Different count of properties and messages"; //$NON-NLS-1$
			throw new RuntimeException(msg);
		}
		this.properties = properties;
		this.values = values;
		this.messages = messages;		
	}


	
	public List<String> execute(T a) {
		Set<String> nulls = new HashSet<String>();
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < properties.length; i++) {
			String property = properties[i];
			if (!StringUtils.startsWith(property + StringConstants.DOT, nulls)) {
				Object value = JavaBeanUtils.getNestedProperty(a, property);
				Object forbidden = values[i];
				if (Utils.equals(value, forbidden)) {				
					list.add(messages[i]);
					if (forbidden==null) {
						nulls.add(property);
					}				
				}				
			}
		}
		return list;
	}

}
