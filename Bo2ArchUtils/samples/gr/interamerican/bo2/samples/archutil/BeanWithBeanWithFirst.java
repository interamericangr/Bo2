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
package gr.interamerican.bo2.samples.archutil;

/**
 * 
 */
public class BeanWithBeanWithFirst {
	/**
	 * first.
	 */
	BeanWithFirst bean;

	/**
	 * Creates a new BeanWithFirst object. 
	 *
	 */
	public BeanWithBeanWithFirst() {
		super();		
	}

	/**
	 * Creates a new BeanWithFirst object. 
	 *
	 * @param bean
	 */
	public BeanWithBeanWithFirst(BeanWithFirst bean) {
		super();
		this.bean = bean;
	}

	/**
	 * Gets the first.
	 *
	 * @return Returns the first
	 */
	public BeanWithFirst getBean() {
		return bean;
	}

	/**
	 * Assigns a new value to the bean.
	 *
	 * @param bean the bean to set
	 */
	public void setBean(BeanWithFirst bean) {
		this.bean = bean;
	}

}
