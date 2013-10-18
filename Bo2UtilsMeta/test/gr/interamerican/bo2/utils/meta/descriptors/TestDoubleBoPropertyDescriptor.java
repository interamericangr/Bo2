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
 * Unit test for {@link DoubleBoPropertyDescriptor}.
 */
public class TestDoubleBoPropertyDescriptor {
	
	/**
	 * DoubleBoPropertyDescriptor
	 */
	private DoubleBoPropertyDescriptor doubleDesc = new DoubleBoPropertyDescriptor();
	
	/**
	 * Test Parse
	 * @throws ParseException
	 */
	@Test		
	public void testParse() throws ParseException{
		doubleDesc.setLengthOfDecimalPart(2);
		System.out.println(doubleDesc.parse("15,46748")); //$NON-NLS-1$
		assertEquals(Double.valueOf(15.47), doubleDesc.parse("15,46748")); //$NON-NLS-1$
		doubleDesc.setLengthOfDecimalPart(4);
		assertEquals(Double.valueOf(1.4577), doubleDesc.parse("1,45767789")); //$NON-NLS-1$
	}
	
	/**
	 * Test Parse
	 * @throws ParseException
	 */
	@Test		
	public void testParseNullValue() throws ParseException{
		doubleDesc.setLengthOfDecimalPart(2);
		doubleDesc.parse(null);
	}
	
	/**
	 * Test format
	 */
	@Test
	public void testFormat(){
		Double db = new Double(13.9688);
		doubleDesc.setLengthOfDecimalPart(3);
		assertEquals("13,969", doubleDesc.format(db)); //$NON-NLS-1$
		Double db2 = new Double(5.787512);
		assertEquals("5,788", doubleDesc.format(db2)); //$NON-NLS-1$
		assertEquals(StringConstants.NULL,doubleDesc.format(null));
	}

}
