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

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

/**
 * tests the ArrayComparator class
 */
public class TestArrayComparator {
	
	/**
	 * tests the class gr.interamerican.bo2.utils.comparators.ArrayComparator
	 */
	@Test
	public void testArrayComparator() {
		Long millis = System.currentTimeMillis();
		Date date1 = new Date(millis);
		Date date2 = new Date(millis+1000); // one second later
		Calendar calendar1 = new GregorianCalendar();
		calendar1.setTime(date1);
		Object[] array1 = {new Integer(1), date1, calendar1, "string", new String("string")}; //$NON-NLS-1$ //$NON-NLS-2$
		Object[] array2 = {new Long(1), calendar1, date1, new String("string"), "string"}; //$NON-NLS-1$ //$NON-NLS-2$
		Object[] array3 = {new Long(1), calendar1, date1, new String("string")}; //$NON-NLS-1$
		Object[] array4 = {BigDecimal.ONE, calendar1, date2, new String("string")}; //$NON-NLS-1$
		Object[] array5 = null;	
		
		ArrayComparator ac = new ArrayComparator();
		
		assertEquals(ac.compare(array1, array2), 0);
		assertTrue(ac.compare(array1, array3)>0);
		assertTrue(ac.compare(array1, array4)<0);
		assertTrue(ac.compare(array5, array1)<0);
		assertTrue(ac.compare(array1, array5)==1);
		
		assertEquals(ac.compare(array1, array1), 0);
	}

}
