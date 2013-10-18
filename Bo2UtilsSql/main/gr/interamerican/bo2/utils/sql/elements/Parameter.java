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
package gr.interamerican.bo2.utils.sql.elements;

import gr.interamerican.bo2.utils.sql.types.Type;

/**
 * Parameter of an SQL statement.
 */
public class Parameter {
	
	/**
	 * Parameter name.
	 */
	 String name;
	
	/**
	 * Parameter SQL type.
	 */
	 Type<?> type;
	 
	 
	/**
	 * Gets the name.
	 *
	 * @return Returns the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Assigns a new value to the name.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the type.
	 *
	 * @return Returns the type
	 */
	public Type<?> getType() {
		return type;
	}

	/**
	 * Assigns a new value to the type.
	 *
	 * @param type the type to set
	 */
	public void setType(Type<?> type) {
		this.type = type;
	}
	
	

}
