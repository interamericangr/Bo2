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
package gr.interamerican.bo2.samples.implopen.beans;

/**
 * The Interface TypedSelectable2.
 */
public interface TypedSelectable2 {
	
	/**
	 * Gets the codebar.
	 *
	 * @return the codebar
	 */
	public Long getCodebar();
	
	/**
	 * Sets the codebar.
	 *
	 * @param codebar the new codebar
	 */
	public void setCodebar(Long codebar);
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public Long getType();
	
	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(Long type);
	
	/**
	 * Sets the descr.
	 *
	 * @param descr the new descr
	 */
	public void setDescr(String descr);
	
	/**
	 * Gets the descr.
	 *
	 * @return the descr
	 */
	public String getDescr(); 

}
