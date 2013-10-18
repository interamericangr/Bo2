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
package gr.interamerican.bo2.utils.reflect.beans;

import gr.interamerican.bo2.utils.StringUtils;

/**
 * Definition of a variable.
 * 
 * A variable is defined by a name and a type.
 * 
 * @param <T> Variable definition. 
 */
public class VariableDefinition<T> {
	/**
	 * name.
	 */
	String name;
	
	/**
	 * type.
	 */
	Class<T> type;
	
	/**
	 * value.
	 */
	T value;
	
	
	/**
	 * Creates a new VariableDefinition object with access type READ-WRITE. 
	 *
	 * @param name
	 * @param type 
	 */
	public VariableDefinition(String name, Class<T> type) {
		this.name = name;
		this.type = type;
	}

	/**
	 * Gets the name.
	 *
	 * @return Returns the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the type.
	 *
	 * @return Returns the type
	 */
	public Class<T> getType() {
		return type;
	}
	
	/**
	 * Gets the value.
	 *
	 * @return Returns the value
	 */
	public T getValue() {
		return value;
	}

	/**
	 * Assigns a new value to the value.
	 *
	 * @param value the value to set
	 */
	public void setValue(T value) {
		this.value = value;
	}	
	
	
	@Override
	public String toString() {
		String fields = StringUtils.concat(
			StringUtils.squareBrackets(StringUtils.toString(name)),
			StringUtils.squareBrackets(StringUtils.toString(type)),
			StringUtils.squareBrackets(StringUtils.toString(value)));
		return StringUtils.squareBrackets(fields);
	}
	
}
