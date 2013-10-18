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
package gr.interamerican.bo2.creation.beans;

import gr.interamerican.bo2.utils.reflect.beans.VariableDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Definition of a javabean.
 * 
 * This definition is necessary in order to create totally new classes
 * that don't implement an existing interface.
 */
public class JavabeanDefinition {
	
	/**
	 * Type name.
	 */
	String typeName;
	
	/**
	 * Field definitions.
	 */
	Map<String, VariableDefinition<?>> fieldsMap = new HashMap<String, VariableDefinition<?>>();

	/**
	 * Creates a new JavabeanDefinition object. 
	 *
	 * @param typeName
	 * @param fields
	 */
	public JavabeanDefinition(String typeName, List<VariableDefinition<?>> fields) {
		super();
		this.typeName = typeName;
		for (VariableDefinition<?> vd : fields) {
			fieldsMap.put(vd.getName(), vd);			
		}		
	}
	
	/**
	 * Gets the type of the field with the specified name.
	 * 
	 * @param field
	 *        Field name.
	 *        
	 * @return Returns the type of the field. If there is no field with
	 *         that name, returns null.
	 */
	public Class<?> getFieldType(String field) {
		VariableDefinition<?> vd = getDefinition(field);
		if (vd==null) {
			return null;
		}
		return vd.getType();
	}
	
	/**
	 * Gets the definition of the field with the specified name.
	 * 
	 * @param field
	 *        Field name.
	 * 
	 * @return Returns the definition of the field. If there is no field with
	 *         that name, returns null.
	 */
	public VariableDefinition<?> getDefinition(String field) {
		return fieldsMap.get(field);
	}
	
	

}
