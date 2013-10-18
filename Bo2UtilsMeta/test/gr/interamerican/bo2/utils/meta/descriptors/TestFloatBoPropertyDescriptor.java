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
package gr.interamerican.bo2.utils.meta.descriptors;

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;

import org.junit.Test;

/**
 * Unit test for {@link FloatBoPropertyDescriptor}.
 */
public class TestFloatBoPropertyDescriptor {
	
	/**
	 * FloatBoPropertyDescriptor
	 */
	private FloatBoPropertyDescriptor floatDesc = new FloatBoPropertyDescriptor();
	
	/**
	 * Test Parse
	 * @throws ParseException
	 */
	@Test		
	public void testParse() throws ParseException{
		floatDesc.setLengthOfDecimalPart(2);
		assertEquals(Float.valueOf((float)13000.46), floatDesc.parse("13000,45767")); //$NON-NLS-1$
		floatDesc.setLengthOfDecimalPart(1);
		assertEquals(Float.valueOf((float)13000.5), floatDesc.parse("13000,45767")); //$NON-NLS-1$
	}
	
	/**
	 * Test Parse
	 * @throws ParseException
	 */
	@Test		
	public void testParseNullValue() throws ParseException{
		floatDesc.setLengthOfDecimalPart(2);
		floatDesc.parse(null);
	}
	
	/**
	 * Test format
	 */
	@Test
	public void testFormat(){
		Float fl = new Float(1.789512);
		floatDesc.setLengthOfDecimalPart(2);
		assertEquals("1,79", floatDesc.format(fl)); //$NON-NLS-1$
		Float fl2 = new Float(13.789512);
		assertEquals("13,79", floatDesc.format(fl2)); //$NON-NLS-1$
		assertEquals(StringConstants.NULL,floatDesc.format(null));
	}

}
