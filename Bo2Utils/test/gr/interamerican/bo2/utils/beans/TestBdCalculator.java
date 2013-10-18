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
package gr.interamerican.bo2.utils.beans;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link BdCalculator}.
 */
@SuppressWarnings("nls")
public class TestBdCalculator {
	/**
	 * decimal places
	 */
	private static final int DCML = 2;
	/**
	 * Zero.
	 */
	private static final BigDecimal ZERO = BigDecimal.ZERO; 
	/**
	 * Zero with decimal places.
	 */
	private static final BigDecimal ZERO_DCML = ZERO.setScale(DCML);
	
	
	/**
	 * Unit test for the constructor.
	 */
	@Test
	public void testConstructor() {
		BdCalculator calc = new BdCalculator(DCML, true);
		Assert.assertEquals(DCML, calc.decimalPlaces);
		Assert.assertEquals(ZERO, calc.remainder);
		Assert.assertEquals(ZERO_DCML, calc.result);
		Assert.assertTrue(calc.addRemainder);
	}
	
	/**
	 * Unit test for set.
	 */
	@Test
	public void testSet() {
		BdCalculator calc = new BdCalculator(DCML,true);
		BigDecimal remainder = new BigDecimal("0.000004");
		calc.result = calc.round(BigDecimal.TEN);
		calc.remainder = remainder;
		
		BigDecimal value = new BigDecimal("13.234");
		calc.set(value);		
		BigDecimal expected = value.setScale(DCML, RoundingMode.HALF_EVEN);
		Assert.assertEquals(remainder, calc.remainder);		
		Assert.assertEquals(expected, calc.result);
	}
	
	/**
	 * Unit test resetResult.
	 */
	@Test
	public void testResetResult() {
		BdCalculator calc = new BdCalculator(DCML,true);
		calc.result = new BigDecimal("13.23");
		calc.resetResult();				
		Assert.assertEquals(ZERO_DCML, calc.result);
	}
	
	/**
	 * Unit test resetResult.
	 */
	@Test
	public void testResetRemainder() {
		BdCalculator calc = new BdCalculator(DCML,true);
		calc.remainder = new BigDecimal("0.0023");
		calc.resetRemainder();				
		Assert.assertEquals(ZERO, calc.remainder);
	}
	
	/**
	 * Unit test resetResult.
	 */
	@Test
	public void testReset() {
		BdCalculator calc = new BdCalculator(DCML,true);
		calc.result = new BigDecimal("13.23");
		calc.remainder = new BigDecimal("0.0023");
		calc.reset();				
		Assert.assertEquals(ZERO_DCML, calc.result);				
		Assert.assertEquals(ZERO, calc.remainder);
	}
	
	
	
	/**
	 * Unit test for add on a BdCalculator with addRemainder=true.
	 */	
	@Test
	public void testAdd_withRemainder() {
		BdCalculator calc = new BdCalculator(DCML,true);
		calc.result = new BigDecimal("13.23");
		calc.remainder = new BigDecimal("0.004");
		BigDecimal value = new BigDecimal("1.004");
		BigDecimal r = calc.add(value);
		BigDecimal expectedResult = new BigDecimal("14.24");
		BigDecimal expectedRemainder = new BigDecimal("-0.002");
		Assert.assertEquals(expectedResult, calc.result);				
		Assert.assertEquals(expectedRemainder, calc.remainder);
		Assert.assertEquals(calc.result, r);
	}
	
	/**
	 * Unit test for add on a BdCalculator with addRemainder=false.
	 */
	
	@Test
	public void testAdd_withoutRemainder() {
		BdCalculator calc = new BdCalculator(DCML,false);
		calc.result = new BigDecimal("13.23");
		calc.remainder = new BigDecimal("0.004");
		BigDecimal value = new BigDecimal("1.004");
		BigDecimal r = calc.add(value);
		BigDecimal expectedResult = new BigDecimal("14.23");
		BigDecimal expectedRemainder = new BigDecimal("0.004");
		Assert.assertEquals(expectedResult, calc.result);				
		Assert.assertEquals(expectedRemainder, calc.remainder);
		Assert.assertEquals(calc.result, r);
	}
	
	/**
	 * Unit test for add on a BdCalculator with addRemainder=true.
	 */	
	@Test
	public void testSubtract_withRemainder() {
		BdCalculator calc = new BdCalculator(DCML,true);
		calc.result = new BigDecimal("13.23");
		calc.remainder = new BigDecimal("0.004");
		BigDecimal value = new BigDecimal("1.003");
		BigDecimal r = calc.subtract(value);
		BigDecimal expectedResult = new BigDecimal("12.23");
		BigDecimal expectedRemainder = new BigDecimal("0.001");
		Assert.assertEquals(expectedResult, calc.result);				
		Assert.assertEquals(expectedRemainder, calc.remainder);
		Assert.assertEquals(calc.result, r);
	}
	
	/**
	 * Unit test for add on a BdCalculator with addRemainder=true.
	 */	
	@Test
	public void testSubtract_withoutRemainder() {
		BdCalculator calc = new BdCalculator(DCML,false);
		calc.result = new BigDecimal("13.23");
		calc.remainder = new BigDecimal("0.004");
		BigDecimal value = new BigDecimal("1.003");
		BigDecimal r = calc.subtract(value);
		BigDecimal expectedResult = new BigDecimal("12.23");
		BigDecimal expectedRemainder = new BigDecimal("-0.003");
		Assert.assertEquals(expectedResult, calc.result);				
		Assert.assertEquals(expectedRemainder, calc.remainder);
		Assert.assertEquals(calc.result, r);
	}
	
	/**
	 * Unit test for add on a BdCalculator with addRemainder=true.
	 */	
	@Test
	public void testMultiply_withRemainder() {
		BdCalculator calc = new BdCalculator(DCML,true);
		calc.result = new BigDecimal("13.23");
		calc.remainder = new BigDecimal("0.004");
		BigDecimal value = new BigDecimal("1.003");
		BigDecimal r = calc.multiply(value);
		BigDecimal expectedResult = new BigDecimal("13.27");
		BigDecimal expectedRemainder = new BigDecimal("0.00369");
		Assert.assertEquals(expectedResult, calc.result);				
		Assert.assertEquals(expectedRemainder, calc.remainder);
		Assert.assertEquals(calc.result, r);
	}
	
	/**
	 * Unit test for add on a BdCalculator with addRemainder=true.
	 */	
	@Test
	public void testMultiply_withoutRemainder() {
		BdCalculator calc = new BdCalculator(DCML,false);
		calc.result = new BigDecimal("13.23");
		calc.remainder = new BigDecimal("0.004");
		BigDecimal value = new BigDecimal("1.003");
		BigDecimal r = calc.multiply(value);
		BigDecimal expectedResult = new BigDecimal("13.27");
		BigDecimal expectedRemainder = new BigDecimal("-0.00031");
		Assert.assertEquals(expectedResult, calc.result);				
		Assert.assertEquals(expectedRemainder, calc.remainder);
		Assert.assertEquals(calc.result, r);
	}
	
	/**
	 * Unit test for add on a BdCalculator with addRemainder=true.
	 */	
	@Test
	public void testNegate() {
		BdCalculator calc = new BdCalculator(DCML,false);
		calc.result = new BigDecimal("13.23");
		BigDecimal r = calc.negate();
		BigDecimal expectedResult = new BigDecimal("-13.23");
		Assert.assertEquals(expectedResult, calc.result);				
		Assert.assertEquals(calc.result, r);
	}
	
	/**
	 * Unit test for add on a BdCalculator with addRemainder=true.
	 */	
	@Test
	public void testRound() {
		BdCalculator calc = new BdCalculator(DCML,false);
		BigDecimal value = new BigDecimal("13.2325");
		BigDecimal r = calc.round(value);
		BigDecimal expectedResult = new BigDecimal("13.23");
		Assert.assertEquals(expectedResult, r);
		Assert.assertEquals(ZERO, calc.remainder);				
		Assert.assertEquals(ZERO_DCML, calc.result);
	}
	
	/**
	 * Unit test for getResult.
	 */	
	@Test
	public void testGetResult() {
		BdCalculator calc = new BdCalculator(DCML,false);
		BigDecimal value = new BigDecimal("13.2325");
		calc.result = value;		
		Assert.assertEquals(value, calc.getResult());
	}
	
	/**
	 * Unit test for getRemainder.
	 */	
	@Test
	public void testGetRemainder() {
		BdCalculator calc = new BdCalculator(DCML,false);
		BigDecimal value = new BigDecimal("0.0025");
		calc.remainder = value;		
		Assert.assertEquals(value, calc.getRemainder());
	}
	
	

	

}
