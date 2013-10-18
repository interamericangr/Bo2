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

import java.io.Serializable;

/**
 * 
 */
public interface IBeanWithIdAndName extends Serializable {
	
	/**
	 * Gets the beanName.
	 *
	 * @return Returns the beanName
	 */
	public String getBeanName();

	/**
	 * Assigns a new value to the beanName.
	 *
	 * @param beanName the beanName to set
	 */
	public void setBeanName(String beanName);

	/**
	 * Gets the beanId.
	 *
	 * @return Returns the beanId
	 */
	public Integer getBeanId();

	/**
	 * Assigns a new value to the beanId.
	 *
	 * @param beanId the beanId to set
	 */
	public void setBeanId(Integer beanId);

}
