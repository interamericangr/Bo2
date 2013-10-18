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
public class TestLongParser {
		
	/**
	 * LongParser
	 */
	LongParser parser = new LongParser();
	
	/**
	 * unit test for parse
	 * @throws ParseException
	 */
	@SuppressWarnings("nls")
	@Test
	public void testParse() throws ParseException{
		test("0", Long.valueOf(0));
		test("15", Long.valueOf(15));
		test("154", Long.valueOf(154));
		test("1578", Long.valueOf(1578));
		test("15968", Long.valueOf(15968));
		test("maxValue", Long.MAX_VALUE);
		test("minValue", Long.MIN_VALUE);		

	}
	
	/**
	 * main test method.
	 * @param string 
	 *        parse input
	 * @param val
	 *        expected output.     
	 * @throws ParseException
	 */
	private void test(String string, Long val) throws ParseException {
		
		Long lg = parser.parse(string);
		Long expected = val;		
		assertEquals(expected, lg);
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
