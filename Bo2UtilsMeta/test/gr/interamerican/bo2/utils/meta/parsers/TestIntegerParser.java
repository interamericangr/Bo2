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

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link DoubleParser}.
 */
public class TestIntegerParser {
		
	
	/**
	 * IntegerParser
	 */
	IntegerParser parser = new IntegerParser();
	
	/**
	 * unit test for parse
	 * @throws ParseException
	 */
	@SuppressWarnings("nls")
	@Test
	public void testParse() throws ParseException{
		test("0", 0);
		test("10", 10);
		test("15", 15);
		test("238", 238);
		test("14578", 14578);
		test("maxValue", Integer.MAX_VALUE);
		test("minValue", Integer.MIN_VALUE);		
	}
	
	/**
	 * main test method.
	 * @param string 
	 *        parse input
	 * @param val
	 *        expected output.     
	 * @throws ParseException
	 */
	private void test(String string, Integer val) throws ParseException {
		Integer intg = parser.parse(string);
		Integer expected = val;		
		assertEquals(expected, intg);
	}
	
	
	/**
	 * Test parse a value that is null
	 * @throws ParseException
	 */
	@Test
	public void testParseNullValue() throws ParseException{
		Assert.assertNull(parser.parse(null)); 
	}
	
	/**
	 * Test parse a value that is not number
	 * @throws ParseException
	 */
	@Test(expected=ParseException.class)
	public void testParseFalseValue() throws ParseException{
		parser.parse("noNumber"); //$NON-NLS-1$
	}

}
