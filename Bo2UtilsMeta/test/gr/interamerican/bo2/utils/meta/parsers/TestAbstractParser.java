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

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link AbstractParser}.
 */
public class TestAbstractParser {
		
	
	/**
	 * IntegerParser
	 */
	AbstractParser<String> parser = new AbstractParser<String>() {
		/**
		 * serialVersionUID
		 */
		private static final long serialVersionUID = 1L;
		@Override
		protected String mainParse(String value) throws ParseException {			
			return value;
		}		
	};
	
		
	/**
	 * Test parse a value that is null
	 * @throws ParseException
	 */
	@Test
	public void testParse_Null() throws ParseException{
		Assert.assertNull(parser.parse(null));  
	}
	
	/**
	 * Test parse a value that is null
	 * @throws ParseException
	 */
	@Test
	public void testParse_NullString() throws ParseException{
		Assert.assertNull(parser.parse(StringConstants.NULL)); 
	}
	
	

}
