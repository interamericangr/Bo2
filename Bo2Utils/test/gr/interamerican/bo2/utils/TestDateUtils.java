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
package gr.interamerican.bo2.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;

import org.junit.Test;

/**
 * Unit tests for {@link DateUtils}
 */
public class TestDateUtils {
	
	/**
	 * tests that getDate for input a string
	 */
	@Test
	public void testGetDate_ForString() {		
		try {
			String str = "30/6/2010"; //$NON-NLS-1$
			Date expected = DateUtils.getDate(2010, Calendar.JUNE, 30);
			Date actual = DateUtils.getDate(str);
			assertEquals(str, expected, actual);
			
			str = "30/05/2010"; //$NON-NLS-1$
			expected = DateUtils.getDate(2010, Calendar.MAY, 30);
			actual = DateUtils.getDate(str);
			assertEquals(str, expected, actual);
			
			str = "2/5/2010"; //$NON-NLS-1$
			expected = DateUtils.getDate(2010, Calendar.MAY, 2);
			actual = DateUtils.getDate(str);
			assertEquals(str, expected, actual);
			
			str = "03/5/2010"; //$NON-NLS-1$
			expected = DateUtils.getDate(2010, Calendar.MAY, 3);
			actual = DateUtils.getDate(str);
			assertEquals(str, expected, actual);
			
			str = "2010-07-21-00:00:00.000"; //$NON-NLS-1$
			expected = DateUtils.getDate(2010, Calendar.JULY, 21);
			actual = DateUtils.getDate(str);
			assertEquals(str, expected, actual);
			
			str = "2010-07-21-04:12:07.013"; //$NON-NLS-1$			
			actual = DateUtils.getDate(str);			
			Calendar cal = Calendar.getInstance();
			cal.setTime(actual);
			assertEquals(4, cal.get(Calendar.HOUR_OF_DAY));
			assertEquals(12, cal.get(Calendar.MINUTE));
			assertEquals(7, cal.get(Calendar.SECOND));
			assertEquals(13, cal.get(Calendar.MILLISECOND));
			
			str = "2010-07-21"; //$NON-NLS-1$
			expected = DateUtils.getDate(2010, Calendar.JULY, 21);
			actual = DateUtils.getDate(str);
			assertEquals(str, expected, actual);
			
			str = null;
			expected = null;
			actual = DateUtils.getDate(str);
			assertEquals(str, expected, actual);
			
		} catch (ParseException e) {
			fail(e.toString());
		}
	}
	
	/**
	 * tests that getDate for input a date
	 */
	@Test
	public void testGetDate_ForDate() {		
		try {
			Date expected=DateUtils.getDate(2010, Calendar.JUNE, 30);
			Object o=new Date(expected.getTime());
			Date actual=DateUtils.getDate(o);
			assertEquals(expected, actual);
		} catch (ParseException e) {
			fail(e.toString());
		}
	}

	/**
	 * tests that getDate for input a calendar
	 */
	@Test
	public void testGetDate_ForCal() {		
		try {
			Date expected=DateUtils.getDate(2010, Calendar.JUNE, 30);
			Calendar cal=Calendar.getInstance();
			cal.setTime(expected);			
			Date actual=DateUtils.getDate(cal);
			assertEquals(expected, actual);
		} catch (ParseException e) {
			fail(e.toString());
		}
	}
	
	/**
	 * tests difDays
	 */
	@Test
	public void testDifDays() {
		@SuppressWarnings("nls")
		Object[][] data = {
				{"13/03/2010", "13/03/2010", 0},
				{"12/03/2010", "13/03/2010", 1},
				{"10/03/2010", "13/03/2010", 3},
				{"13/06/2010", "13/06/2011", 365},
				{"13/06/2011", "13/06/2012", 366},
				{"13/06/2007", "13/06/2009", 731}
		};
		try {
			for (int i = 0; i < data.length; i++) {
				String sd1 = (String) data[i][0];
				String sd2 = (String) data[i][1];
				int expected = (Integer) data[i][2];
				
				Date d1 = DateUtils.getDate(sd1);
				Date d2 = DateUtils.getDate(sd2);
				int actual = DateUtils.difDays(d1,d2);
				String msg = "Datedif from " +sd1+ " to " +sd2+ " => " +actual;  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$				
				assertEquals(msg, expected, actual);
			}
		} catch (ParseException e) {
			fail(e.toString());
		}
	}
	
	/**
	 * tests difMonths
	 */
	@Test
	public void testDifMonths() {
		@SuppressWarnings("nls")
		Object[][] data = {
				{"13/03/2010", "13/03/2010", 0},
				{"01/03/2010", "23/03/2010", 0},
				{"12/03/2010", "13/04/2010", 1},
				{"10/03/2010", "13/03/2010", 0},
				{"13/06/2010", "13/06/2011", 12},
				{"13/06/2007", "13/06/2009", 24}
		};
		try {
			for (int i = 0; i < data.length; i++) {
				String sd1 = (String) data[i][0];
				String sd2 = (String) data[i][1];
				int expected = (Integer) data[i][2];
				
				Date d1 = DateUtils.getDate(sd1);
				Date d2 = DateUtils.getDate(sd2);
				int actual = DateUtils.difMonths(d1,d2);
				String msg = "Month dif from " +sd1+ " to " +sd2+ " => " +actual;  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$				
				assertEquals(msg, expected, actual);
			}
		} catch (ParseException e) {
			fail(e.toString());
		}
	}
	
	/**
	 * tests difYears
	 */
	@Test
	public void testDifYears() {
		@SuppressWarnings("nls")
		Object[][] data = {
				{"13/03/2010", "13/03/2010", 0},
				{"01/03/2010", "23/03/2010", 0},
				{"12/03/2010", "13/04/2010", 0},
				{"10/03/2010", "13/03/2010", 0},
				{"13/06/2010", "13/06/2011", 1},
				{"13/06/2007", "13/06/2009", 2}
		};
		try {
			for (int i = 0; i < data.length; i++) {
				String sd1 = (String) data[i][0];
				String sd2 = (String) data[i][1];
				int expected = (Integer) data[i][2];
				
				Date d1 = DateUtils.getDate(sd1);
				Date d2 = DateUtils.getDate(sd2);
				int actual = DateUtils.difYears(d1,d2);
				String msg = "Month dif from " +sd1+ " to " +sd2+ " => " +actual;  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$				
				assertEquals(msg, expected, actual);
			}
		} catch (ParseException e) {
			fail(e.toString());
		}
	}
	
	
	/**
	 * tests format date
	 */
	@Test
	public void testFormatDate() {
		@SuppressWarnings("nls")
		Object[][] data = {
				{DateUtils.getDate(2010, Calendar.MARCH, 13) , "13/03/2010"},
				{DateUtils.getDate(2008, Calendar.FEBRUARY, 29) , "29/02/2008"},
				{DateUtils.getDate(1453, Calendar.MAY, 29) , "29/05/1453"},
				{DateUtils.getDate(1453, Calendar.MAY, 9) , "09/05/1453"},
				{DateUtils.getDate(453, Calendar.MAY, 29) , "29/05/0453"}
		};
		for (int i = 0; i < data.length; i++) {
			Date dt = (Date) data[i][0];
			String expected = (String) data[i][1];
			String actual = DateUtils.formatDate(dt);
			assertEquals(expected, actual);
		}
	}
	
	/**
	 * tests format calendar
	 */
	@Test
	public void testFormatCalendar() {		
    	Calendar cal=new GregorianCalendar();
    	cal.set(Calendar.YEAR, 2010);
    	cal.set(Calendar.MONTH,Calendar.MAY);
    	cal.set(Calendar.DATE,21);
    	cal.set(Calendar.HOUR_OF_DAY,17);
    	cal.set(Calendar.MINUTE, 13);
    	cal.set(Calendar.SECOND, 42);
    	cal.set(Calendar.MILLISECOND, 621);
    	String expected = "2010-05-21-17:13:42.621"; //$NON-NLS-1$
    	String actual = DateUtils.formatCalendar(cal);
    	assertEquals(expected, actual);
	}
	
	
	/**
	 * tests GetDay1AD
	 */
	@Test
	public void testGetDay1AD(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$
		String expected = "0001-01-01"; //$NON-NLS-1$
		Date dt = DateUtils.getDay1AD();
		String actual = sdf.format(dt);
		assertEquals(expected,actual);	
	}
	
	
	/**
	 * tests GetMonth1st
	 */
	@Test
	public void testGetMonth1st(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$
		Date date =  DateUtils.getMonth1st(2011, 0);
		String expected = "2011-01-01"; //$NON-NLS-1$
		String actual = sdf.format(date);
		assertEquals(expected,actual);	
	}
	
	
	/**
	 * tests removeTime
	 */
	@Test
	public void testRemoveTime(){
		
		Date date = new Date();
		DateUtils.removeTime(date);	
	}
	
	
	/**
	 * tests isZero
	 */
	@Test
	public void testIsZero(){
      Date dt = new Date();
      assertFalse(DateUtils.isZero(dt));
      
      Calendar cal = new GregorianCalendar();
      cal.set(1,Calendar.JANUARY, 1, 0, 0, 0);
	  cal.set(Calendar.MILLISECOND, 0);
	  assertTrue(DateUtils.isZero(cal));
	}
	
	/**
	 * tests difDays
	 */
	@Test
	public void testDifDaysWithCalendar(){
		
		Calendar fromDate = new GregorianCalendar();
		fromDate.set(2010, 7, 20);
		Calendar toDate = new GregorianCalendar();
		toDate.set(2010, 7, 23);
		Integer expected = 3;
		Integer actual = DateUtils.difDays(fromDate, toDate);
		assertEquals(expected,actual);
		
		Calendar fromDate2 = new GregorianCalendar();
		fromDate2.set(2010, 7, 20);
		Calendar toDate2 = new GregorianCalendar();
		toDate2.set(2011, 7, 20);
		Integer expected2 = 365;
		Integer actual2 = DateUtils.difDays(fromDate2, toDate2);
		assertEquals(expected2,actual2);
		
	}
	
	/**
	 * tests DifYears
	 */
	@Test
	public void testDifYearsWithCalendar(){
		
		Calendar fromDate = new GregorianCalendar();
		fromDate.set(2010, 7, 20);
		Calendar toDate = new GregorianCalendar();
		toDate.set(2010, 7, 23);
		Integer expected = 0;
		Integer actual = DateUtils.difYears(fromDate, toDate);
		assertEquals(expected,actual);
		
		Calendar fromDate2 = new GregorianCalendar();
		fromDate2.set(2010, 7, 20);
		Calendar toDate2 = new GregorianCalendar();
		toDate2.set(2011, 7, 20);
		Integer expected2 = 1;
		Integer actual2 = DateUtils.difYears(fromDate2, toDate2);
		assertEquals(expected2,actual2);
		
	}
	
	/**
	 * tests DifMonths
	 */
	@Test
	public void testDifMonthsWithCalendar(){		
		Calendar fromDate = new GregorianCalendar();
		fromDate.set(2010, 7, 20);
		Calendar toDate = new GregorianCalendar();
		toDate.set(2010, 7, 23);
		Integer expected = 0;
		Integer actual = DateUtils.difMonths(fromDate, toDate);
		assertEquals(expected,actual);
		
		Calendar fromDate2 = new GregorianCalendar();
		fromDate2.set(2010, 7, 20);
		Calendar toDate2 = new GregorianCalendar();
		toDate2.set(2011, 7, 20);
		Integer expected2 = 12;
		Integer actual2 = DateUtils.difMonths(fromDate2, toDate2);
		assertEquals(expected2,actual2);
	}
	
	/**
	 * tests addDays
	 * @throws ParseException 
	 */
	@Test
	public void testAddDays() throws ParseException{
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$
		Date dt = sdf.parse("2010-07-20"); //$NON-NLS-1$
        String expected = "2010-07-25"; //$NON-NLS-1$
        String actual = sdf.format(DateUtils.addDays(dt, 5));
        assertEquals(expected,actual);
        
		Date dt2 = sdf.parse("2010-07-20"); //$NON-NLS-1$
        String expected2= "2010-08-20"; //$NON-NLS-1$
        String actual2 = sdf.format(DateUtils.addDays(dt2, 31));
        assertEquals(expected2,actual2);
	}
	
	/**
	 * tests addMonths
	 * @throws ParseException 
	 */
	@Test
	public void testAddMonths() throws ParseException{		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$
		Date dt = sdf.parse("2010-07-20"); //$NON-NLS-1$
        String expected = "2010-09-20"; //$NON-NLS-1$
        String actual = sdf.format(DateUtils.addMonths(dt, 2));
        assertEquals(expected,actual);
        
		Date dt2 = sdf.parse("2010-07-20"); //$NON-NLS-1$
        String expected2= "2011-07-20"; //$NON-NLS-1$
        String actual2 = sdf.format(DateUtils.addMonths(dt2, 12));
        assertEquals(expected2,actual2);
        
        Date dt3 = sdf.parse("2011-01-31"); //$NON-NLS-1$
        String expected3= "2011-02-28"; //$NON-NLS-1$
        String actual3 = sdf.format(DateUtils.addMonths(dt3, 1));
        assertEquals(expected3,actual3);
       
        String expected4= "2011-04-30"; //$NON-NLS-1$
        String actual4 = sdf.format(DateUtils.addMonths(dt3, 3));
        assertEquals(expected4,actual4);
        
        String expected5= "2012-02-29"; //$NON-NLS-1$
        String actual5 = sdf.format(DateUtils.addMonths(dt3, 13));
        assertEquals(expected5,actual5);
	}
	
	/**
	 * tests testMonths
	 * @throws ParseException 
	 */
	@Test
	public void testAddYears() throws ParseException{
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$
		Date dt = sdf.parse("2010-07-20"); //$NON-NLS-1$
        String expected = "2010-07-20"; //$NON-NLS-1$
        String actual = sdf.format(DateUtils.addYears(dt, 0));
        assertEquals(expected,actual);
        
		Date dt2 = sdf.parse("2010-07-20"); //$NON-NLS-1$
        String expected2= "2020-07-20"; //$NON-NLS-1$
        String actual2 = sdf.format(DateUtils.addYears(dt2, 10));
        assertEquals(expected2,actual2);
	}

	
	/**
	 * tests testToday
	 */
	@Test
	public void testToday(){
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
    	cal.set(Calendar.HOUR_OF_DAY, 0);
    	cal.set(Calendar.MINUTE, 0);
    	cal.set(Calendar.SECOND, 0);
    	cal.set(Calendar.MILLISECOND, 0);
    	Date expected = cal.getTime();
    	Date actual = DateUtils.today();
    	assertEquals(expected,actual);
	}
	
	/**
	 * tests formatYYYYMMDD(dt)
	 * @throws ParseException 
	 */
	@Test
	public void testFormatYYYYMMDD() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$
		Date dt = sdf.parse("2010-07-20"); //$NON-NLS-1$
		String actual = DateUtils.formatYYYYMMDD(dt);
		String expected = "20100720"; //$NON-NLS-1$
		assertEquals(expected, actual);
	}

	/**
	 * Tests {@link DateUtils#year(Date)}.
	 */
	@Test
	public void testYear() {
		Date dt = DateUtils.getDate(2011, Calendar.NOVEMBER, 15);
		assertEquals(2011, DateUtils.year(dt));
	}

	/**
	 * Tests {@link DateUtils#days(Date)}.
	 */
	@Test
	public void testDays() {
		Date dt = DateUtils.getDate(2011, Calendar.NOVEMBER, 15);
		assertEquals(15, DateUtils.days(dt));
	}

	/**
	 * Tests {@link DateUtils#month(Date)}.
	 */
	@Test
	public void testMonth() {
		Date dt = DateUtils.getDate(2011, Calendar.NOVEMBER, 15);
		assertEquals(11, DateUtils.month(dt));
	}

	/**
	 * tests {@link DateUtils#daysDifFromEpoch(Date)}.
	 */
	@Test
	public void testDaysDifFromEpoch() {
		Date dt = DateUtils.getDate(2011, Calendar.NOVEMBER, 15);
		assertTrue(DateUtils.daysDifFromEpoch(dt) > 0);
		Date dt2 = DateUtils.getDate(1970, Calendar.JANUARY, 2);
		assertEquals(1, DateUtils.daysDifFromEpoch(dt2));
		Date dt3 = DateUtils.getDate(1970, Calendar.JANUARY, 1);
		assertEquals(0, DateUtils.daysDifFromEpoch(dt3));
		Date dt4 = DateUtils.getDate(1969, Calendar.DECEMBER, 30);
		assertEquals(-2, DateUtils.daysDifFromEpoch(dt4));
	}
	
	/**
	 * tests getDateDDMMYYYY()
	 */
	@Test
	public void testGetDateDDMMYYYY() {
		String input = "01/01/1900"; //$NON-NLS-1$
		Date actual = DateUtils.getDateDDMMYYYY(input);
		Date expected = DateUtils.getDate(1900, Calendar.JANUARY, 1);
		assertEquals(actual.compareTo(expected), 0);
	}

	/**
	 * test method for {@link DateUtils#getDateAtMidnight(Date)}
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testGetNullTimeDate() {
		Date d = new Date();
		Date nullDate = DateUtils.getDateAtMidnight(d);
		assertEquals(d.getDay(), nullDate.getDay());
		assertEquals(d.getMonth(), nullDate.getMonth());
		assertEquals(d.getYear(), nullDate.getYear());
		assertEquals(0, nullDate.getHours());
		assertEquals(0, nullDate.getMinutes());
		assertEquals(0, nullDate.getSeconds());
	}

	/**
	 * test method for {@link DateUtils#getOrthodoxEaster(int)}
	 */
	@Test
	public void testGetOrthodoxEaster() {
		Date d = DateUtils.getDate(2013, Calendar.MAY, 5);
		d = DateUtils.getDateAtMidnight(d);
		assertEquals(d, DateUtils.getOrthodoxEaster(2013));
	}

	/**
	 * test method for {@link DateUtils#getOrthodoxEasterHolidays(int)}
	 */
	@Test
	public void testGetOrthodoxEasterHolidays() {
		Date d1 = DateUtils.getDate(2013, Calendar.MAY, 5);
		Date d2 = DateUtils.getDate(2013, Calendar.MAY, 6);
		Date d3 = DateUtils.getDate(2013, Calendar.MAY, 3);
		Date d4 = DateUtils.getDate(2013, Calendar.JUNE, 24);
		Date d5 = DateUtils.getDate(2013, Calendar.MARCH, 18);
		Set<Date> dates = DateUtils.getOrthodoxEasterHolidays(2013);
		assertEquals(5, dates.size());
		assertTrue(dates.contains(d1));
		assertTrue(dates.contains(d2));
		assertTrue(dates.contains(d3));
		assertTrue(dates.contains(d4));
		assertTrue(dates.contains(d5));
	}

	/**
	 * test method for {@link DateUtils#isWeekend(Date)}
	 */
	@Test
	public void testIsWeekend() {
		Date d1 = DateUtils.getDate(2013, Calendar.MAY, 5);
		Date d2 = DateUtils.getDate(2013, Calendar.MAY, 4);
		Date d3 = DateUtils.getDate(2013, Calendar.MAY, 3);
		assertTrue(DateUtils.isWeekend(d1));
		assertTrue(DateUtils.isWeekend(d2));
		assertFalse(DateUtils.isWeekend(d3));
	}

	/**
	 * mest method for {@link DateUtils#getYear(Date)}
	 */
	@Test
	@SuppressWarnings("deprecation")
	public void testGetyear() {
		assertEquals((new Date()).getYear() + 1900, DateUtils.getYear(new Date()));
	}

	/**
	 * test method for {@link DateUtils#equalDatesIgnoringTime(Date, Date)}
	 */
	@Test
	public void testEqualDatesIgnoringTime() {
		Date d1=DateUtils.today();
		Date d2=new Date();
		assertTrue(DateUtils.equalDatesIgnoringTime(d1, d2));
	}

	/**
	 * test for the known Greek holidays.
	 */
	@Test
	public void testGetKnownGreekHolidays() {
		Set<Date> dates = DateUtils.getKnownGreekHolidays(2013);
		Set<Date> easterDates = DateUtils.getOrthodoxEasterHolidays(2013);
		assertTrue(dates.containsAll(easterDates));
	}

	/**
	 * test method for
	 */
	@Test
	public void TestIsStandardGreekHoliday() {
		Date d3 = DateUtils.getDate(2013, Calendar.MAY, 10);
		for (Date d : DateUtils.getKnownGreekHolidays(2013)) {
			assertTrue(DateUtils.isStandardGreekHoliday(d));
		}
		assertFalse(DateUtils.isStandardGreekHoliday(d3));
	}
}
