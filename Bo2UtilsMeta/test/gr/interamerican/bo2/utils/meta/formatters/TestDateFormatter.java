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

import gr.interamerican.bo2.utils.DateUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 */
public class TestDateFormatter {
	/**
	 * Date
	 */
	Date dt = DateUtils.getDate(2011, Calendar.JANUARY, 25);
	
	/**
	 * Test
	 */
	@Test
	public void testFormat_createdWithDateFormat() {
		DateFormat df = new SimpleDateFormat();
		DateFormatter formatter = new DateFormatter(df);
		String actual = formatter.format(dt);
		String expected = df.format(dt);
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Test
	 */
	@Test
	@SuppressWarnings("nls")
	public void testFormat_createdWithString() {		
		DateFormatter formatter = new DateFormatter("yyyyMMdd");
		String actual = formatter.format(dt);
		String expected = "20110125";
		Assert.assertEquals(expected, actual);
	}

}
