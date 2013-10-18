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
package gr.interamerican.bo2.utils.meta.ext.formatters.nf;

import gr.interamerican.bo2.utils.DateUtils;
import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.bo2.utils.StringConstants;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 */
public class TestNfMultiFormatter {
	
	/**
	 * Object to test.
	 */
	NfMultiFormatter nf = new NfMultiFormatter();
	
	
	/**
	 * Unit test for format.
	 */
	@Test
	public void testFormat_Null() {		
		Object ob = null;
		String expected = StringConstants.EMPTY;
		String actual = nf.format(ob);
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Unit test for format.
	 */
	@Test
	public void testFormat_BigDecimal() {		
		Object ob = new BigDecimal("2500.55200054"); //$NON-NLS-1$
		String expected = NumberUtils.format((BigDecimal)ob,2);
		String actual = nf.format(ob);
		Assert.assertEquals(expected, actual);
	}
	/**
	 * Unit test for format.
	 */
	@Test
	public void testFormat_Double() {
		
		Object ob = new Double(2340.34324D);
		String expected = NumberUtils.format((Double)ob,2);
		String actual = nf.format(ob);
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Unit test for format.
	 */
	@Test
	public void testFormat_DoublePrc() {
		
		Object ob = new Double(0.30D);
		String expected = NumberUtils.format((Double)ob,2);
		String actual = nf.format(ob);
		System.out.println(expected);
		System.out.println(actual);
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Unit test for format.
	 */
	@Test
	public void testFormat_Date() {
		Date ob = DateUtils.getDate(2010, Calendar.JANUARY, 21);
		String expected = DateUtils.formatDate(ob);
		String actual = nf.format(ob);
		Assert.assertEquals(expected, actual);
	}


}
