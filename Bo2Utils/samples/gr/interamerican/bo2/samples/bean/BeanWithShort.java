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

import java.io.Serializable;

/**
 * Bean with a short property.
 */
public class BeanWithShort implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Short field.
	 */
	private Short first;
	
	/**
	 * Creates a new BeanWith1Field object. 
	 */
	public BeanWithShort() {
		super();
	}
	
	/**
	 * Creates a new BeanWithShort object. 
	 * @param first 
	 */
	public BeanWithShort(Short first) {
		super();
		this.first = first;
	}

	/**
	 * Gets the first.
	 *
	 * @return Returns the first
	 */
	public Short getFirst() {
		return first;
	}

	/**
	 * Assigns a new value to the first.
	 *
	 * @param first the first to set
	 */
	public void setFirst(Short first) {
		this.first = first;
	}


}
