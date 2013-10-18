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
package gr.interamerican.bo2.gui.properties;

/**
 * Properties of a text field.
 * 
 */
public interface TextFieldProperties 
extends LabelOption, Enabled {
	
	/**
	 * Gets the length of the text field.
	 * 
	 * @return Returns the length. 
	 */
	int getColumns();
	
	/**
	 * Sets the textLength.
	 * 
	 * @param columns
	 */
	void setColumns(int columns);
	


	/**
	 * Assigns a new value to the editable.
	 *
	 * @param editable the editable to set
	 */
	void setEditable(boolean editable);
	
	/**
	 * Gets the editable.
	 *
	 * @return Returns the editable
	 */
	boolean isEditable();



}
