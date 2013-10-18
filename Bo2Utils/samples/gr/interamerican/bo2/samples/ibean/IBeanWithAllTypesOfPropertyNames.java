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
package gr.interamerican.bo2.samples.ibean;

/**
 * Bean interface with various types of property names. <br/>
 * 
 * Types of property names:
 * <li> boolean old with accessors isOld() and setOld(b) </li>
 * <li> boolean fine with accessors getFine() and setFine(b) </li>
 * <li> Boolean enabled with accessors getEnabled() and setEnabled(b) </li> 
 * <li> Boolean enabled with accessors getEnabled() and setEnabled(b) </li>
 * <li> Object object with accessors getObject() and setObject(o) </li>
 */
public interface IBeanWithAllTypesOfPropertyNames {
	
	/**
	 * Gets the object.
	 * 
	 * @return object
	 */
	Object getObject();
	
	/**
	 * Sets the object.
	 * 
	 * @param object
	 */
	void setObject(Object object);
	
	/**
	 * Gets the id.
	 * 
	 * @return id
	 */
	boolean isOld();
	
	/**
	 * Sets the old.
	 * 
	 * @param old
	 */
	void setOld(boolean old);
	
	/**
	 * Gets the enabled.
	 * 
	 * @return enabled
	 */
	Boolean getEnabled();
	
	/**
	 * Sets the enabled.
	 * 
	 * @param enabled
	 */
	void setEnabled(Boolean enabled);
	
	/**
	 * Gets the fine.
	 * 
	 * @return fine
	 */
	boolean getFine();
	
	/**
	 * Sets the fine.
	 * 
	 * @param fine
	 */
	void setFine(boolean fine);
	
	
}
