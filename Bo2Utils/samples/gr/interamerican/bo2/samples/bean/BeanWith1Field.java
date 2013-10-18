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
 * Bean with one field.
 */
public class BeanWith1Field implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Long field.
	 */
	private Long field2;
	
	/**
	 * Creates a new BeanWith1Field object. 
	 */
	public BeanWith1Field() {
		super();
	}
	
	/**
	 * Creates a new BeanWith1Field object. 
	 * @param field2 
	 */
	public BeanWith1Field(Long field2) {
		super();
		this.field2 = field2;
	}

	/**
	 * Gets the field2.
	 *
	 * @return Returns the field2
	 */
	public Long getField2() {
		return field2;
	}

	/**
	 * Assigns a new value to the field2.
	 *
	 * @param field2 the field2 to set
	 */
	public void setField2(Long field2) {
		this.field2 = field2;
	}
}
