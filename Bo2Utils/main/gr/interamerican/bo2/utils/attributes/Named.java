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
package gr.interamerican.bo2.utils.attributes;

/**
 * Something with a name.
 */
public interface Named {
	
	/**
	 * Gets the name.
	 * 
	 * @return Returns the name.
	 */
	String getName();
	
	/**
	 * Sets the name.
	 * 
	 * @param name 
	 *        The name to set.
	 */
	void setName(String name);

}
