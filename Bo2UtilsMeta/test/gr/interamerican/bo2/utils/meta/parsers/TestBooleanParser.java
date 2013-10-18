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
 * Unit test for {@link BigDecimalParser}.
 */
public class TestBooleanParser {
	
	
	/**
	 * Test Parse
	 * @throws ParseException 
	 */
	
	@Test
	public void testParse() throws ParseException{
		
		BooleanParser booleanParser = new BooleanParser();
		assertEquals(true, booleanParser.parse("1")); //$NON-NLS-1$
		assertEquals(true, booleanParser.parse("true")); //$NON-NLS-1$
		assertEquals(false, booleanParser.parse("2")); //$NON-NLS-1$
		assertEquals(false, booleanParser.parse("7")); //$NON-NLS-1$
		assertEquals(false, booleanParser.parse("jnbjhgh")); //$NON-NLS-1$
		booleanParser.parse(null);
	}

}
