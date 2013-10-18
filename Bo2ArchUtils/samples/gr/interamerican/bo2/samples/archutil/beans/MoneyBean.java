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
package gr.interamerican.bo2.samples.archutil.beans;

import gr.interamerican.bo2.arch.Money;
import gr.interamerican.bo2.arch.utils.beans.MoneyImpl;

import java.math.BigDecimal;

/**
 * Javabean with some BigDecimal and Money properties.
 */
public class MoneyBean {
	
	/**
	 * Big decimal amount 1.
	 */
	BigDecimal bdAmount1;
	/**
	 * Big decimal amount 2.
	 */
	BigDecimal bdAmount2;
	/**
	 * Money amount 1.
	 */
	Money moneyAmount1;
	/**
	 * Money amount 2.
	 */
	Money moneyAmount2;
	
	/**
	 * Creates a new MoneyBean object. 
	 *
	 */
	public MoneyBean() {
		super();
	}
	
	/**
	 * Creates a new MoneyBean object. 
	 *
	 * @param bdAmount1
	 * @param bdAmount2
	 * @param moneyAmount1
	 * @param moneyAmount2
	 */
	public MoneyBean(BigDecimal bdAmount1, BigDecimal bdAmount2, Money moneyAmount1, Money moneyAmount2) {
		super();
		this.bdAmount1 = bdAmount1;
		this.bdAmount2 = bdAmount2;
		this.moneyAmount1 = moneyAmount1;
		this.moneyAmount2 = moneyAmount2;
	}
	
	/**
	 * Creates a new MoneyBean object. 
	 *
	 * @param amount1
	 */
	public MoneyBean(BigDecimal amount1) {
		super();
		this.bdAmount1 = amount1;
		this.moneyAmount1 = new MoneyImpl(amount1);
	}
	
	
	
	/**
	 * Gets the bdAmount1.
	 *
	 * @return Returns the bdAmount1
	 */
	public BigDecimal getBdAmount1() {
		return bdAmount1;
	}
	/**
	 * Assigns a new value to the bdAmount1.
	 *
	 * @param bdAmount1 the bdAmount1 to set
	 */
	public void setBdAmount1(BigDecimal bdAmount1) {
		this.bdAmount1 = bdAmount1;
	}
	/**
	 * Gets the bdAmount2.
	 *
	 * @return Returns the bdAmount2
	 */
	public BigDecimal getBdAmount2() {
		return bdAmount2;
	}
	/**
	 * Assigns a new value to the bdAmount2.
	 *
	 * @param bdAmount2 the bdAmount2 to set
	 */
	public void setBdAmount2(BigDecimal bdAmount2) {
		this.bdAmount2 = bdAmount2;
	}
	/**
	 * Gets the moneyAmount1.
	 *
	 * @return Returns the moneyAmount1
	 */
	public Money getMoneyAmount1() {
		return moneyAmount1;
	}
	/**
	 * Assigns a new value to the moneyAmount1.
	 *
	 * @param moneyAmount1 the moneyAmount1 to set
	 */
	public void setMoneyAmount1(Money moneyAmount1) {
		this.moneyAmount1 = moneyAmount1;
	}
	/**
	 * Gets the moneyAmount2.
	 *
	 * @return Returns the moneyAmount2
	 */
	public Money getMoneyAmount2() {
		return moneyAmount2;
	}
	/**
	 * Assigns a new value to the moneyAmount2.
	 *
	 * @param moneyAmount2 the moneyAmount2 to set
	 */
	public void setMoneyAmount2(Money moneyAmount2) {
		this.moneyAmount2 = moneyAmount2;
	}
	
	

}
