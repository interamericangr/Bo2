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
 * Something that belongs to a sub-type of a registered type.
 * 
 */
public interface SubTyped {	

	/**
	 * Sets the id of this object's sub-type.
	 * 
	 * If the type described by this object's <code>typeId</code>
	 * property can't be devided into sub-types, then this method 
	 * should have no effect. 
	 * 
	 * @param subTypeId New sub type id.
	 */
	public void setSubTypeId(Long subTypeId);
	
	/**
	 * Gets the id of the sub type.
	 * 
	 * If the type described by this object's <code>typeId</code>
	 * property can't be devided into sub-types, then this method 
	 * should return null.
	 * 
	 * @return Returns the id of the sub type.
	 */
	public Long getSubTypeId();

}
