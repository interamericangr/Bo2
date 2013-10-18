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
package gr.interamerican.bo2.utils.meta.formatters;

import java.text.DecimalFormat;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 */
public class TestDecimalFormatter {
	/**
	 * Double to test
	 */
	Double d = 1897.3;
	
	/**
	 * Test
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testFormat_createdWithDecimalFormat() {
		DecimalFormat df = new DecimalFormat();		
		DecimalFormatter formatter = new DecimalFormatter(df);
		String actual = formatter.format(d);
		String expected = df.format(d);
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Test
	 */
	@Test
	@SuppressWarnings({ "nls", "rawtypes", "unchecked" })
	public void testFormat_createdWithDecimalDigits() {		
		DecimalFormatter formatter = new DecimalFormatter(3);
		String actual = formatter.format(d);
		String expected = "1.897,300";
		Assert.assertEquals(expected, actual);
	}

}
