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
package gr.interamerican.bo2.utils.meta.ext.parsers;

import gr.interamerican.bo2.arch.Money;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link MoneyParser}.
 */
public class TestMoneyParser {
	
	/**
	 * 
	 */
	static final String amount = "1200"; //$NON-NLS-1$
	
	/**
	 * currency.
	 */
	private Currency currency = Currency.getInstance("EUR"); //$NON-NLS-1$
	
	/**
	 * test for {@link MoneyParser#parse(String)}.
	 * @throws ParseException 
	 */
	@Test
	public void test() throws ParseException{
		int decimalDigits = currency.getDefaultFractionDigits();
		BigDecimal expectedValue = 
			new BigDecimal(amount).setScale(decimalDigits, RoundingMode.HALF_EVEN);
		
		MoneyParser moneyParser = new MoneyParser(decimalDigits);
		Money money = moneyParser.parse(amount);
		
		Assert.assertEquals(expectedValue, money.getAmount());
		
	}
}
