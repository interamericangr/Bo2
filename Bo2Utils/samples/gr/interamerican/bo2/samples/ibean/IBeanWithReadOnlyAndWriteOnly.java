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
 * Java bean interface that has some properties read only,
 * and some others write only.
 * 
 */
public interface IBeanWithReadOnlyAndWriteOnly {
	/**
	 * Is accessible.
	 * 
	 * @return Returns if this is accessible.
	 */
	public boolean isAccessible();
	
	/**
	 * Sets the state.
	 * 
	 * @param state
	 */
	public void setState(String state);
	
	/**
	 * Gets the id.
	 * 
	 * @return id.
	 */
	public Long getId();
	
	/**
	 * Sets the id.
	 * 
	 * @param id
	 */
	public void setId(Long id);
	
	/**
	 * Gets the name.
	 * 
	 * @return Name.
	 */
	public String getName();

}
