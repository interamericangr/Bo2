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

import org.junit.Test;

/**
 * Unit test for {@link StringParser}.
 */
public class TestStringParser {
	
	
	/**
	 * Test Parse
	 * @throws ParseException 
	 */
	
	@Test
	public void testParse() throws ParseException{
		
		StringParser stringParser = new StringParser();
		assertEquals("test", stringParser.parse("test")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("test1", stringParser.parse("test1")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("test2", stringParser.parse("test2")); //$NON-NLS-1$ //$NON-NLS-2$
	}

}
