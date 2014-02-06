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
package gr.interamerican.bo2.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.text.ParseException;

import org.junit.Test;

/**
 * Unit tests for {@link NumberUtils}
 */
@SuppressWarnings("nls")
public class TestNumberUtils {
	
	/**
	 * epsilon for equality check of doubles by assertEquals
	 */
	private static final double EPSILON = 0.0;
	
	/**
	 * tests string2Double
	 */
	@Test
	public void testString2Double() {		
		Object[][] data = {
				{"0", 0.0},
				{"3,14", 3.14},
				{"176,33", 176.33},
				{"1805,302", 1805.302},
				{"1.805,302", 1805.302},
				{"3.031.805,302", 3031805.302},
				{"-3,14", -3.14},
				{"-176,33", -176.33},
				{"-1805,302", -1805.302},
				{"-1.805,302", -1805.302}
		};		
		for (int i = 0; i < data.length; i++) {
			String s = (String) data[i][0];
			Double expected = (Double) data[i][1];				
			double actual = NumberUtils.string2Double(s);				
			String msg = " parsing " + s + " => " + actual;				
			assertEquals(msg, expected, actual, EPSILON);
		}
	}
	
	/**
	 * tests string2Double
	 */
	@Test
	public void testString2Double_FalseValue() {	
		String s = "noNumber";
		Double expected = 0.0;
		Double actual = NumberUtils.string2Double(s);
		assertEquals(expected,actual );
	}
	
	/**
	 * tests string2BigDecimal
	 */
	@Test
	public void testString2BigDecimal() {
		
		Object[][] data = {
				{"0", 0.0},
				{"3,14", 3.14},
				{"176,33", 176.33},
				{"1805,302", 1805.302},
				{"1.805,302", 1805.302},
				{"3.031.805,302", 3031805.302},
				{"-3,14", -3.14},
				{"-176,33", -176.33},
				{"-1805,302", -1805.302},
				{"-1.805,302", -1805.302}
		};		
		for (int i = 0; i < data.length; i++) {
			String s = (String) data[i][0];
			Double expected = (Double) data[i][1];				
			double actual = NumberUtils.string2BigDecimal(s).doubleValue();				
			String msg = " parsing " + s + " => " + actual;				
			assertEquals(msg, expected, actual, EPSILON);
		}
	}
	
	
	/**
	 * tests round
	 */
	@Test
	public void testRound_Double() {		
		Object[][] data = {
				{0.127, 0, 0.0},
				{0.127, 2, 0.13},
				{0.127, 1, 0.1},
				{17.3214, 3, 17.321}
		};		
		for (int i = 0; i < data.length; i++) {
			Double in = (Double) data[i][0];
			Integer digits = (Integer) data[i][1];
			Double expected = (Double) data[i][2];
			
			double actual = NumberUtils.round(in, digits);				
			String msg = " rounding " + in + " to " + digits + " => " + actual;				
			assertEquals(msg, expected, actual, EPSILON);
		}
	}
	
	/**
	 * tests round up
	 */
	@Test
	public void testRoundUp_Double() {		
		Object[][] data = {
				{0.127, 0, 1.0},
				{0.127, 2, 0.13},
				{0.127, 1, 0.2},
				{17.3214, 3, 17.322}
		};		
		for (int i = 0; i < data.length; i++) {
			Double in = (Double) data[i][0];
			Integer digits = (Integer) data[i][1];
			Double expected = (Double) data[i][2];
			
			double actual = NumberUtils.roundUp(in, digits);				
			String msg = " rounding " + in + " to " + digits + " => " + actual;				
			assertEquals(msg, expected, actual, EPSILON);
		}
	}
	

	/**
	 * tests sum
	 */
	@Test
	public void testSum() {
		BigDecimal bd = new BigDecimal("3.14");
		Number[] data = { 0.12, 0.142, 4.1, 2, bd };
		int digits = 2;
		double expected = 9.5;
		double actual = NumberUtils.sum(data, digits);
		assertEquals(expected, actual, EPSILON);		
		
	}
	
	
	/**
	 * tests format
	 */
	@Test	
	public void testFormat() {
		
		Object[][] data = {
				{"0", 0.0},
				{"3,14", 3.14},
				{"176,33", 176.33},
				{"1.521.805,302", 1521805.302},
				{"1.805,302", 1805.302},
				{"3.031.805,302", 3031805.302},
				{"-3,14", -3.14},
				{"-176,33", -176.33},				
				{"-1.805,302", -1805.302}
		};		
		for (int i = 0; i < data.length; i++) {
			String expected = (String) data[i][0];
			Double d = (Double) data[i][1];				
			String actual = NumberUtils.format(d);		
			String msg = " formating " + d + " => " + actual;				
			assertEquals(msg, expected, actual);
		}
	}
	
	/**
	 * tests format with decimals
	 */
	@Test	
	public void testFormatWithDecimal() {
		Object[][] data = {
				{"3,14", 3.14},
				{"176,33", 176.33},
				{"180,56", 180.560},
				{"180,56", 180.562},
				{"180,56", 180.565},
				{"180,57", 180.567},
				{"-3,14", -3.14},
				{"-176,33", -176.33},				
				{"-180,56", -180.560},
				{"-180,56", -180.562},
				{"-180,56", -180.565},
				{"-180,57", -180.567},
		};	
		
		for (int i = 0; i < data.length; i++) {
			String expected = (String) data[i][0];
			Double d = (Double) data[i][1];				
			String actual = NumberUtils.format(d,2);		
			String msg = " formating " + d + " => " + actual;				
			assertEquals(msg, expected, actual);
		}
		
	}
	
	
	
	/**
	 * tests string2Int
	 */	
	@Test
	public void testString2Int() {		
		
		Object[][] data = {
				{" ", 0 },
				{"17", 17 },
				{"7.4", 0 },
				{"-658", -658 },
				{"2000658", 2000658 },
				{"f20", 0 },
				{" 20  ", 20 },
				{" 2 0  ", 0 },
				{null, 0 }
		};		
		for (int i = 0; i < data.length; i++) {
			String s = (String) data[i][0];
			Integer expected = (Integer) data[i][1];						
			Integer actual = NumberUtils.string2Int(s);
			assertEquals(expected, actual);
		}		
	}
	
	/**
	 * tests string2Int
	 */	
	@Test
	public void testString2Long() {		
		
		Object[][] data = {
				{" ", 0L },
				{"17", 17L },
				{"7.4", 0L },
				{"-658", -658L },
				{"20006580012", 20006580012L },
				{"f20", 0L },
				{" 20  ", 20L },
				{" 400  ", 400L },
				{" 2 0  ", 0L },
				{null, 0L }
		};		
		for (int i = 0; i < data.length; i++) {
			String s = (String) data[i][0];
			Long expected = (Long) data[i][1];						
			Long actual = NumberUtils.string2Long(s);
			assertEquals(expected, actual);
		}		
	}
	

	/**
	 * tests isNumeric
	 */		
	@Test
	public void testIsNumeric() {
		assertTrue(NumberUtils.isNumeric("1"));
		assertTrue(NumberUtils.isNumeric(" 14,9 "));
		assertTrue(NumberUtils.isNumeric(" 2.114,9 "));
		assertTrue(NumberUtils.isNumeric("-2.114,9 "));
		assertTrue(NumberUtils.isNumeric(" 14.9 "));
		assertTrue(NumberUtils.isNumeric(" 14.9 "));
		assertTrue(NumberUtils.isNumeric(" 12l "));
		
		assertFalse(NumberUtils.isNumeric(" p12 "));
		assertFalse(NumberUtils.isNumeric(" papaki "));
		
	}	
	
	
	/**
	 * test parses a double
	 * @throws ParseException 
	 */
	@Test
	public void testParseDouble() throws ParseException {		
		Object[][] data = {
				{3.14, "3,14"},
				{176.33, "176,33"},
				{180.560,"180,560"},
				{180.5602,"180,5602"},
				{-3.14,"-3,14"},
				{ -176.33,"-176,33"},				
				{-180.560, "-180,56"},
				{-180.562,"-180,562"}
		};	
		
		for (int i = 0; i < data.length; i++) {
			Double expected = (Double) data[i][0];
			String s = (String) data[i][1];				
			Double actual = NumberUtils.parseDouble(s);						
			assertEquals(expected, actual);
		}
		
		assertNull(NumberUtils.parseDouble(null));
	}
	
	
	/**
	 * test parses a double
	 * @throws ParseException 
	 */
	@Test
	public void testParseFloat() throws ParseException {		
		Object[][] data = {
				{3.14f, "3,14"},
				{176.33f, "176,33"},
				{180.560f,"180,560"},
				{180.5602f,"180,5602"},
				{-3.14f,"-3,14"},
				{ -176.33f,"-176,33"},				
				{-180.560f, "-180,56"},
				{-180.562f,"-180,562"}
		};	
		
		for (int i = 0; i < data.length; i++) {
			Float expected = (Float) data[i][0];
			String s = (String) data[i][1];				
			Float actual = NumberUtils.parseFloat(s);						
			assertEquals(expected, actual);
		}
		
		assertNull(NumberUtils.parseFloat(null));
	}
	
	/**
	 * tests string2Double
	 */
	@Test
	public void testInt2BigDecimal() {		
		Object[][] data = {
				{0 , new BigDecimal(0.0)},
				{186, new BigDecimal(186)},
				{1984, new BigDecimal(1984)},
				{-1, new BigDecimal(-1)},
				{-1500, new BigDecimal(-1500)}
		};		
		for (int i = 0; i < data.length; i++) {
			Integer s = (Integer) data[i][0];
			BigDecimal expected = (BigDecimal) data[i][1];				
			BigDecimal actual = NumberUtils.int2BigDecimal(s);			
			String msg = " parsing " + s + " => " + actual;				
			assertEquals(msg, expected, actual);
		}

	}	
	
	/**
	 * test round 
	 */
	@Test
	public void testRound_BigDecimal() {	
		Integer digits = 2;
		BigDecimal bd =  new BigDecimal(0.127);
		BigDecimal actual = NumberUtils.round(bd, digits);
		BigDecimal expected = new BigDecimal(0.127).setScale(2,BigDecimal.ROUND_HALF_UP);
		assertEquals(expected,actual);
		
		Integer digits2 = 3;
		BigDecimal actual2 = NumberUtils.round(bd, digits2);
		BigDecimal expected2 = new BigDecimal(0.127).setScale(3,BigDecimal.ROUND_HALF_UP);
		assertEquals(expected2,actual2);
	
		
	}
	
	/**
	 * test formatInteger
	 */
	@Test
	public void testFormatInteger() {
		String result = NumberUtils.formatInteger(10, 3);
		assertEquals("010", result);
		result = NumberUtils.formatInteger(999, 3);
		assertEquals("999", result);
		result = NumberUtils.formatInteger(0000, 3);
		assertEquals("000", result);
		result = NumberUtils.formatInteger(19, 1);
		assertEquals("19", result);
	}

	/**
	 * test max
	 */
	@Test(expected = RuntimeException.class)
	public void testMax_Exception() {
		NumberUtils.max();
	}
	
	/**
	 * test max
	 */
	@Test
	public void testMax() {
		
		assertEquals(2,NumberUtils.max(2));
		assertEquals(10,NumberUtils.max(10));
		
		assertEquals(7,NumberUtils.max(4,5,7));
		assertEquals(30,NumberUtils.max(10,20,30));
	}
	
	
	
	/**
	 * test BigDecimal
	 */
	@Test
	public void testNewBigDecimal() {
		Double value = 100000.0;
		int scale = 3;
		BigDecimal bd = NumberUtils.newBigDecimal(value, scale);
		Double actual = new Double(bd.doubleValue());
		assertEquals(value, actual);
		assertEquals(scale, bd.scale());
		
		Double value2 = 10.15;
		int scale2 = 0;
		BigDecimal actual2 = NumberUtils.newBigDecimal(value2, scale2);
		BigDecimal expected2 = new BigDecimal(10);
		assertEquals(expected2, actual2);
		
	}
	
	/**
	 * test randomInt
	 */
	@Test
	public void testRandomInt() {
		int min=1;
		int max=100;
		boolean gotMin=false;
		boolean gotMax=false;
		while(!gotMin || !gotMax) {
			int random = NumberUtils.randomInt(min, max);
			assertTrue(random>=min);
			assertTrue(random<=max);
			if(random==min) {
				gotMin=true;
			}
			if(random==max) {
				gotMax=true;
			}
		}
	}
	
	/**
	 * test isNullOrZero
	 */
	@Test
	public void testIsNullOrZero() {
		assertTrue(NumberUtils.isNullOrZero(null));
		assertTrue(NumberUtils.isNullOrZero(0L));
		assertTrue(NumberUtils.isNullOrZero(0.0));
		assertTrue(NumberUtils.isNullOrZero(BigDecimal.ZERO));
		assertFalse(NumberUtils.isNullOrZero(0.1));
		assertFalse(NumberUtils.isNullOrZero(10L));
	}
}