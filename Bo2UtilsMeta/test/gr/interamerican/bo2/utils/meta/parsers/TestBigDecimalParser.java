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
package gr.interamerican.bo2.utils.meta.parsers;

import static gr.interamerican.bo2.utils.meta.parsers.Constants.MAX_VALUE;
import static gr.interamerican.bo2.utils.meta.parsers.Constants.MIN_VALUE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;

import java.math.BigDecimal;

import org.junit.Test;

/**
 * Unit test for {@link BigDecimalParser}.
 */
public class TestBigDecimalParser {
	
	/**
	 * Parser digits.
	 */
	int scale = 3;
	
	/**
	 * BigDecimalParser
	 */
	BigDecimalParser parser = new BigDecimalParser(scale);
	
	/**
	 * unit test for parse
	 * @throws ParseException
	 */
	@SuppressWarnings("nls")
	@Test
	public void testParse() throws ParseException{
		test("0", 0.);
		test("0,0", 0.);
		test("10,0", 10.);
		test("15,75", 15.75);
		test("23000,075", 23000.075);
		test("23.000,075", 23000.075);
		test("23.000,0755", 23000.075);
	}
	
	/**
	 * main test method.
	 * @param string 
	 *        parse input
	 * @param val
	 *        expected output.     
	 * @throws ParseException
	 */
	private void test(String string, double val) throws ParseException {
		BigDecimal bd = parser.parse(string);
		assertEquals(scale, bd.scale());
		assertEquals(new Double(bd.doubleValue()), new Double(val));
	}
	
	
	/**
	 * Test parse a value that is not number
	 * @throws ParseException
	 */
	@Test(expected=ParseException.class)
	public void testParseFalseValue() throws ParseException{
		parser.parse("noNumber"); //$NON-NLS-1$
	}
	
	/**
	 * test mainParse
	 * @throws ParseException 
	 */
	@Test
	public void testMainParse() throws ParseException{
		assertTrue(parser.mainParse(MIN_VALUE)!= new BigDecimal(0));
		assertTrue(parser.mainParse(MAX_VALUE)!= new BigDecimal(0));
	}
}
