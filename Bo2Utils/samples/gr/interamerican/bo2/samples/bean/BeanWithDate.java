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
import java.util.Date;

/**
 * Bean with a date property.
 */
public class BeanWithDate implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Long field.
	 */
	private Date date;
	
	/**
	 * Creates a new BeanWith1Field object. 
	 */
	public BeanWithDate() {
		super();
	}
	
	/**
	 * Creates a new BeanWithDate object. 
	 * @param date 
	 */
	public BeanWithDate(Date date) {
		super();
		this.date = date;
	}

	/**
	 * Gets the date.
	 *
	 * @return Returns the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Assigns a new value to the date.
	 *
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}


}
