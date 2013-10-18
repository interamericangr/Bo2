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

import java.math.BigDecimal;

/**
 * Bean with numbers.
 */
public class NumberBean {
	/**
	 * Bd 1.
	 */
	private BigDecimal bd1;
	/**
	 * Bd 2.
	 */
	private BigDecimal bd2;
	
	/**
	 * Gets the bd1.
	 *
	 * @return Returns the bd1
	 */
	public BigDecimal getBd1() {
		return bd1;
	}
	/**
	 * Assigns a new value to the bd1.
	 *
	 * @param bd1 the bd1 to set
	 */
	public void setBd1(BigDecimal bd1) {
		this.bd1 = bd1;
	}
	/**
	 * Gets the bd2.
	 *
	 * @return Returns the bd2
	 */
	public BigDecimal getBd2() {
		return bd2;
	}
	/**
	 * Assigns a new value to the bd2.
	 *
	 * @param bd2 the bd2 to set
	 */
	public void setBd2(BigDecimal bd2) {
		this.bd2 = bd2;
	}

}
