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
package gr.interamerican.bo2.arch.def;

/**
 * Container of named fields that have a predefined order.
 * 
 * The fields can be accessed using the integer value 
 * of their order. The order of each field can be found using
 * its name and vise versa.
 * 
 */
public interface OrderedNamedFieldsContainer {
	
	/**
	 * Gets the order (position) of the field.
	 * 
	 * @param field field name
	 * @return order of the field
	 */
	public int getFieldOrder(String field);
	
	
	/**
	 * Gets the name of the field.
	 * 
	 * @param field field order
	 * @return returns the name of the the field
	 */
	public String getFieldName(int field);
	
	/**
	 * Gets the total count of the fields.
	 * 
	 * @return Returns the count of the fields.
	 */
	public int getFieldsCount();
	

}
