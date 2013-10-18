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
 * Metadata of a database table alias.
 * 
 */
public class Alias {
	
	/**
	 * alias creator.
	 */
	 String creator;
	/**
	 * alias name.
	 */
	 String name;
	/**
	 * table name.
	 */
	 String tbName;
	/**
	 * table qualifier.
	 */
	 String tbCreator;
	
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
	 * Gets the tbName.
	 *
	 * @return Returns the tbName
	 */
	public String getTbName() {
		return tbName;
	}
	/**
	 * Assigns a new value to the tbName.
	 *
	 * @param tbName the tbName to set
	 */
	public void setTbName(String tbName) {
		this.tbName = tbName;
	}
	/**
	 * Gets the tbCreator.
	 *
	 * @return Returns the tbCreator
	 */
	public String getTbCreator() {
		return tbCreator;
	}
	/**
	 * Assigns a new value to the tbCreator.
	 *
	 * @param tbCreator the tbCreator to set
	 */
	public void setTbCreator(String tbCreator) {
		this.tbCreator = tbCreator;
	}
	/**
	 * Gets the creator.
	 *
	 * @return Returns the creator
	 */
	public String getCreator() {
		return creator;
	}
	/**
	 * Assigns a new value to the creator.
	 *
	 * @param creator the creator to set
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	

}
