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
package gr.interamerican.bo2.samples.utils.meta.ext;


import gr.interamerican.bo2.arch.Money;

import java.io.Serializable;

/**
 *  Sample bean for SelfDrawnMoneyField test.
 */
public class MoneyOwnerObject implements Serializable {
	/**
	 * serial.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Money moneyField;
	/**
	 * Creates a new MoneyOwnerObject object. 
	 *
	 * @param moneyField
	 */
	public MoneyOwnerObject(Money moneyField) {
		super();
		this.moneyField = moneyField;
	}

	/**
	 * Assigns a new value to the moneyField.
	 *
	 * @param moneyField the moneyField to set
	 */
	public void setMoneyField(Money moneyField) {
		this.moneyField = moneyField;
	}

	/**
	 * Gets the moneyField.
	 *
	 * @return Returns the moneyField
	 */
	public Money getMoneyField() {
		return moneyField;
	}
}
