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
package gr.interamerican.bo2.arch.ext;

/**
 * Something that belongs to a registered type.
 * 
 * The type is identified by an integer type id.
 * Typed objects that can't be divided to sub-types
 * can ignore this property.
 */
public interface Typed {
	
	/**
	 * Sets the name of the registered type.
	 * 
	 * @param typeId New id of registered type.
	 */
	public void setTypeId(Long typeId);
	
	/**
	 * Gets the id of the type.
	 * 
	 * @return Returns the id of the type.
	 */
	public Long getTypeId();

}
