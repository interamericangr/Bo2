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
package gr.interamerican.bo2.utils.meta.formatters.nf;

import gr.interamerican.bo2.utils.DateUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 */
public class TestNfObjectFormatter {
	/**
	 * Test for the constructor.
	 */
	@Test
	public void testConstructor() {		
		NfObjectFormatter f = new NfObjectFormatter();
		Assert.assertNotNull(f.formatter);
	}
	
	/**
	 * Unit test for format.
	 * 
	 * NfObjectFormatter replaced Bo2ValueFormatter. This is the test of Bo2ValueFormatter.
	 */
	@Test
	public void testFormat() {
		NfObjectFormatter f = new NfObjectFormatter();
		Object ob = DateUtils.getDate(2010, Calendar.JANUARY, 21);
		String expected = StringUtils.toString(ob, StringConstants.EMPTY);
		String actual = f.format(ob);
		Assert.assertEquals(expected, actual);
	}
	
}
