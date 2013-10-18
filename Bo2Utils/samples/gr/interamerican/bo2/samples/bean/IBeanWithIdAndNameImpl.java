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
package gr.interamerican.bo2.samples.bean;

import gr.interamerican.bo2.samples.ibean.IBeanWithIdAndName;

/**
 * 
 */
@SuppressWarnings("serial")
public class IBeanWithIdAndNameImpl implements IBeanWithIdAndName {
	
	/**
	 * bean name
	 */
	private String beanName;
	
	/**
	 * bean id
	 */
	private Integer beanId;

	/**
	 * Gets the beanName.
	 *
	 * @return Returns the beanName
	 */
	public String getBeanName() {
		return beanName;
	}

	/**
	 * Assigns a new value to the beanName.
	 *
	 * @param beanName the beanName to set
	 */
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	/**
	 * Gets the beanId.
	 *
	 * @return Returns the beanId
	 */
	public Integer getBeanId() {
		return beanId;
	}

	/**
	 * Assigns a new value to the beanId.
	 *
	 * @param beanId the beanId to set
	 */
	public void setBeanId(Integer beanId) {
		this.beanId = beanId;
	}
	
	

}
