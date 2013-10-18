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

/**
 * Represents a function of a database dialect.
 * 
 * Examples are database functions like concat, left, right etc.
 */
public class Function {
	

	/**
	 * Name of the function.
	 */
	 String name;
	
	/**
	 * Count of function arguments.
	 */
	 int arguments;
	
	/**
	 * Creates a new CustomFunction.
	 * 
	 * @param name name
	 * @param arguments number of arguments
	 */
	public Function(String name, int arguments) {
		this.name=name.toUpperCase();
		this.arguments=arguments; 
	}


	/**
	 * Count of arguments of the function
	 * @return how many arguments needs the function
	 */
	public int getArguments() {
		return arguments;
	}

	/**
	 * function name
	 * @return function name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the count of arguments.
	 * 
	 * @param arguments
	 *        
	 */
	public void setArguments(int arguments) {
		this.arguments = arguments;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

}
