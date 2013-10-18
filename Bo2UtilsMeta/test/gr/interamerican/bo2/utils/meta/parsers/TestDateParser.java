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

import gr.interamerican.bo2.utils.meta.exceptions.ParseException;

import org.junit.Test;

/**
 * Unit test for {@link BigDecimalParser}.
 */
public class TestDateParser {
	

	/**
	 * DateParser
	 */
	DateParser dateParser = new DateParser();	
	
	/**
	 * Test Parse
	 * @throws ParseException 
	 */
	
	@Test
	public void testParse() throws ParseException{
		
		dateParser.parse("27/01/2011"); //$NON-NLS-1$
	    dateParser.parse("2011-01-27"); //$NON-NLS-1$
        dateParser.parse("2011-01-27-15:20:10.154"); //$NON-NLS-1$
	}
	
	/**
	 * Test Parse
	 * @throws ParseException 
	 */
	
	@Test(expected = ParseException.class)
	public void testParseFalseValue() throws ParseException{
		dateParser.parse("27012011"); //$NON-NLS-1$
	}

}
