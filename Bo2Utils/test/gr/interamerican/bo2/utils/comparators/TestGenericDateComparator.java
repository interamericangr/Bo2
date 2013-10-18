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
package gr.interamerican.bo2.utils.comparators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

/**
 * tests the gr.interamerican.bo2.utils.comparators.GenericDateComparator class
 */
public class TestGenericDateComparator {

	/**
	 * 
	 */
	@Test
	public void testGenericDateComparator() {
		Long millis = System.currentTimeMillis();

		Date dateO1 = new Date(millis);
		Date dateO2 = new Date(millis + 1000 * 3600 * 25); // 25h later
		Date dateO3 = new Date(millis);
		Date dateO4 = dateO1;
		Calendar calendarO1 = new GregorianCalendar();
		calendarO1.setTime(dateO1);
		Calendar calendarO2 = new GregorianCalendar();
		calendarO2.setTime(dateO2);
		Calendar calendarO3 = new GregorianCalendar();
		calendarO3.setTime(dateO3);
		Calendar calendarO4 = calendarO1;

		GenericDateComparator gdc = new GenericDateComparator();

		assertEquals(gdc.compare(dateO1, dateO3), 0);
		assertEquals(gdc.compare(dateO1, dateO4), 0);
		assertTrue(gdc.compare(dateO2, dateO1) > 0);
		assertTrue(gdc.compare(dateO1, dateO2) < 0);

		assertEquals(gdc.compare(calendarO1, calendarO3), 0);
		assertEquals(gdc.compare(calendarO1, calendarO4), 0);
		assertTrue(gdc.compare(dateO2, dateO1) > 0);
		assertTrue(gdc.compare(dateO1, dateO2) < 0);

		// cross-checks
		assertEquals(gdc.compare(dateO1, calendarO3), 0);
		assertEquals(gdc.compare(dateO1, calendarO4), 0);
		assertTrue(gdc.compare(dateO2, calendarO1) > 0);
		assertTrue(gdc.compare(dateO1, calendarO2) < 0);
		// check comparison with invalid dates
		assertTrue(gdc.compare(dateO1, "no date") > 0); //$NON-NLS-1$
		assertEquals(gdc.compare("nil", "no date"), 0); //$NON-NLS-1$ //$NON-NLS-2$
		

	}

}
