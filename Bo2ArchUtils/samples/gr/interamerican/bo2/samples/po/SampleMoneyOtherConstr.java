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
package gr.interamerican.bo2.samples.po;

import gr.interamerican.bo2.arch.utils.beans.MoneyImpl;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Money.
 * 
 */
public class SampleMoneyOtherConstr extends MoneyImpl {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Creates a new MoneyImpl object. 
	 *
	 * @param amount the amount
	 * @param currency the currency
	 */
	public SampleMoneyOtherConstr(BigDecimal amount, Currency currency) {
		super();		
		setAmount(amount);
		setCurrencyCode(currency.getCurrencyCode());
	}
	
	
}
