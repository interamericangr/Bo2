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
package gr.interamerican.bo2.arch.utils.beans;

import gr.interamerican.bo2.arch.Money;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link MoneyCalculator}.
 */
@SuppressWarnings("nls")
public class TestMoneyCalculator {
	
	/**
	 * Euro.
	 */
	private static Currency EURO = Currency.getInstance("EUR");	
	/**
	 * Zero.
	 */
	private static final Money NOMONEY = money("0");
	
	/**
	 * Money factory.
	 * 
	 * @param amount
	 * @return Returns euro money.
	 */
	private static Money money(String amount) {
		BigDecimal bd = new BigDecimal(amount);
		return new MoneyImpl(bd, EURO);
	}
	
	/**
	 * Unit test for the constructor.
	 */
	@Test
	public void testConstructor() {
		MoneyCalculator calc = new MoneyCalculator(Currency.getInstance("EUR"), true);
		Assert.assertNotNull(calc.calc);	
		Assert.assertEquals("EUR", calc.currency.getCurrencyCode());		
	}
	
	/**
	 * Unit test for the constructor.
	 */
	@Test
	public void testSet() {
		MoneyCalculator calc = new MoneyCalculator(EURO,true);
		Money m = money("13.23");
		calc.set(m);
		Assert.assertEquals(m, calc.getResult());
		Assert.assertEquals(BigDecimal.ZERO, calc.getRemainder());		
	}
	
	/**
	 * Unit test resetResult.
	 */
	@Test
	public void testResetResult() {
		MoneyCalculator calc = new MoneyCalculator(EURO,true);
		Money m = money("13.23");
		calc.set(m);
		calc.resetResult();
		Assert.assertEquals(NOMONEY, calc.getResult());
		Assert.assertEquals(BigDecimal.ZERO, calc.getRemainder());	
	}
	
	/**
	 * Unit test resetRemainder.
	 */
	@Test
	public void testResetRemainder() {
		MoneyCalculator calc = new MoneyCalculator(EURO,true);
		BigDecimal bd = new BigDecimal("0.004");
		calc.calc.add(bd);
		Assert.assertEquals(calc.calc.getRemainder(), bd);
		calc.resetRemainder();
		Assert.assertEquals(NOMONEY, calc.getResult());
		Assert.assertEquals(BigDecimal.ZERO, calc.getRemainder());	
	}
	
	/**
	 * Unit test reset.
	 */
	@Test
	public void testReset() {
		MoneyCalculator calc = new MoneyCalculator(EURO,true);
		Money m = money("243.22");
		BigDecimal vat = new BigDecimal(0.23);
		calc.set(m);
		calc.multiply(vat);
		calc.reset();
		Assert.assertEquals(NOMONEY, calc.getResult());
		Assert.assertEquals(BigDecimal.ZERO, calc.getRemainder());	
	}
	
	
	/**
	 * Unit test for add on a BdCalculator with addRemainder=true.
	 */	
	@Test
	public void testAdd() {
		MoneyCalculator calc = new MoneyCalculator(EURO,true);
		Money m = money("13.23");
		Money r = calc.add(m);
		Assert.assertEquals(m, calc.getResult());
		Assert.assertEquals(r, calc.getResult());
	}
	
	/**
	 * Unit test for add on a BdCalculator with addRemainder=true.
	 */	
	@Test
	public void testSubtract() {
		MoneyCalculator calc = new MoneyCalculator(EURO,true);
		Money m1 = money("13.23");
		calc.set(m1);
		Money m2 = money("3.23");		
		Money r = calc.subtract(m2);
		Money expected = money("10");		
		Assert.assertEquals(expected, calc.getResult());
		Assert.assertEquals(expected, r);
	}
	
	/**
	 * Unit test for add on a BdCalculator with addRemainder=true.
	 */	
	@Test
	public void testMultiply() {
		MoneyCalculator calc = new MoneyCalculator(EURO,true);
		Money m1 = money("13.23");
		calc.set(m1);
		BigDecimal value = new BigDecimal("1.003");		
		Money m = calc.multiply(value);		
		Money expectedResult = money("13.27");
		BigDecimal expectedRemainder = new BigDecimal("-0.00031");
		Assert.assertEquals(expectedResult, calc.getResult());				
		Assert.assertEquals(expectedResult, m);
		Assert.assertEquals(expectedRemainder, calc.getRemainder());		
	}
	
	
	
	
	
	
	
	
	
	

	

}
