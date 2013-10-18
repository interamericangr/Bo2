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
package gr.interamerican.bo2.utils.meta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gr.interamerican.bo2.utils.meta.descriptors.DoubleBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;

import org.junit.Assert;
import org.junit.Test;

/**
 * MetaTest
 */
public class TestMeta {
	
	/**
	 * Test parse
	 * Creates a range from two string values.
	 * @throws ParseException
	 */
	@Test
	public void testParse() throws ParseException{		
		DoubleBoPropertyDescriptor doubleDesc = new DoubleBoPropertyDescriptor();
		String left = "10,5"; //$NON-NLS-1$
		String right = "15,5"; //$NON-NLS-1$
		Meta.parse(left, right, doubleDesc);
		assertNotNull(Meta.parse(left, right, doubleDesc));
	}
	
	
	/**
	 * Test format
	 * Formats a value.
	 */
	@Test
	public void testformat(){		
		DoubleBoPropertyDescriptor doubleDesc = new DoubleBoPropertyDescriptor();
		doubleDesc.setLengthOfDecimalPart(1);
		Double value = 10.5;
		Meta.format(value, doubleDesc);
		assertEquals("10,5",Meta.format(value, doubleDesc)); //$NON-NLS-1$
	}
	
	/**
	 * Test format
	 * Formats a value.
	 */
	@Test
	public void testGetFormatter(){		
		DoubleBoPropertyDescriptor doubleDesc = new DoubleBoPropertyDescriptor();
		Formatter<Double> formatter = Meta.getFormatter(doubleDesc);
		Assert.assertNotNull(formatter);
		Double value = 10.5;
		String actual = formatter.format(value);
		String expected = doubleDesc.format(value);
		assertEquals(expected, actual);
	}
	
	

}
