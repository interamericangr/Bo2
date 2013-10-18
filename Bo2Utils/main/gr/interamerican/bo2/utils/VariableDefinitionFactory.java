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
package gr.interamerican.bo2.utils;

import gr.interamerican.bo2.utils.reflect.beans.VariableDefinition;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

/**
 * Factory for {@link VariableDefinition} objects.
 */
public class VariableDefinitionFactory {
	/**
	 * Creates a VariableDefinition from a Field.
	 * 
	 * @param field
	 * @return Returns the VariableDefinition.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static VariableDefinition<?> create(Field field) {
		return new VariableDefinition(field.getName(), field.getType());
	}
	
	/**
	 * Creates a VariableDefinition from a PropertyDescriptor..
	 * 
	 * @param property
	 *        PropertyDescriptor. 
	 * @return Returns the VariableDefinition.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static VariableDefinition<?> create(PropertyDescriptor property) {
		return new VariableDefinition(property.getName(), property.getPropertyType());
	}
	
	/**
	 * Creates an array of VariableDefinition objects form an array of
	 * PropertyDescriptor objects.
	 * 
	 * @param properties
	 *        Array of PropertyDescriptors.
	 * @return Returns the array.
	 */	
	public static VariableDefinition<?>[] create(PropertyDescriptor[] properties) {
		VariableDefinition<?>[] defs = new VariableDefinition<?>[properties.length];
		for (int i = 0; i < defs.length; i++) {
			defs[i] = create(properties[i]);
		}
		return defs;
	}
	
	/**
	 * Creates an array of VariableDefinition objects form an array of Field
	 * objects.
	 * 
	 * @param fields
	 *        Array of fields.
	 * @return Returns the array.
	 */	
	public static VariableDefinition<?>[] create(Field[] fields) {
		VariableDefinition<?>[] defs = new VariableDefinition<?>[fields.length];
		for (int i = 0; i < defs.length; i++) {
			defs[i] = create(fields[i]);
		}
		return defs;
	}
	
	/**
	 * Creates VariableDefinition object for an object.
	 * 
	 * @param object
	 *        Object to represent as {@link VariableDefinition}.
	 *        
	 * @return Returns the VariableDefinition.
	 */	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static VariableDefinition<?> asVariableDefinition(Object object) {
		if (object==null) {
			return new VariableDefinition(StringConstants.NULL, null);			
		}
		VariableDefinition def = new VariableDefinition(StringUtils.toString(object), object.getClass());
		def.setValue(object);
		return def;
	}

}
