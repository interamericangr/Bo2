package gr.interamerican.bo2.utils.greek;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.junit.Assert;

import gr.interamerican.bo2.utils.DateUtils;

import org.junit.Test;

/**
 * Unit tests for {@link GreekHolidays}.
 */
public class TestGreekHolidays {
	
	
	/**
	 * Test for isStandardGreekHoliday(dt).
	 */
	@Test
	public void testIsStandardGreekHoliday_May1() {
		Date dt = DateUtils.getDate(2013, Calendar.MAY, 1);
		Assert.assertTrue(GreekHolidays.isStandardGreekHoliday(dt));
	}
	
	/**
	 * Test for isStandardGreekHoliday(dt).
	 */
	@Test
	public void testIsStandardGreekHoliday_March25() {
		Date dt = DateUtils.getDate(25, Calendar.MARCH, 2013);
		Assert.assertTrue(GreekHolidays.isStandardGreekHoliday(dt));
	}
	
	/**
	 * Test for isStandardGreekHoliday(dt).
	 */
	@Test
	public void testIsStandardGreekHoliday_2014March27() {
		Date dt = DateUtils.getDate(27, Calendar.MARCH, 2014);
		Assert.assertFalse(GreekHolidays.isStandardGreekHoliday(dt));
	}
	
	/**
	 * Test for isStandardGreekHoliday(dt).
	 */
	@Test
	public void testIsStandardGreekHoliday_Easter2015() { 
		Date dt = DateUtils.getDate(2015, Calendar.APRIL, 12);
		Assert.assertTrue(GreekHolidays.isStandardGreekHoliday(dt));
	}
	
	/**
	 * test method for {@link GreekHolidays#getOrthodoxEaster}.
	 */
	@Test
	public void testGetOrthodoxEaster() {
		Date d = DateUtils.getDate(2013, Calendar.MAY, 5);
		d = DateUtils.getDateAtMidnight(d);
		assertEquals(d, GreekHolidays.getOrthodoxEaster(2013));
	}

	/**
	 * test method for {@link GreekHolidays#getOrthodoxEasterHolidays}.
	 */
	@Test
	public void testGetOrthodoxEasterHolidays() {
		Date d1 = DateUtils.getDate(2013, Calendar.MAY, 5);
		Date d2 = DateUtils.getDate(2013, Calendar.MAY, 6);
		Date d3 = DateUtils.getDate(2013, Calendar.MAY, 3);
		Date d4 = DateUtils.getDate(2013, Calendar.JUNE, 24);
		Date d5 = DateUtils.getDate(2013, Calendar.MARCH, 18);
		Set<Date> dates = GreekHolidays.getOrthodoxEasterHolidays(2013);
		assertEquals(5, dates.size());
		assertTrue(dates.contains(d1));
		assertTrue(dates.contains(d2));
		assertTrue(dates.contains(d3));
		assertTrue(dates.contains(d4));
		assertTrue(dates.contains(d5));
	}
	
	/**
	 * test for the known Greek holidays.
	 */
	@Test
	public void testGetKnownGreekHolidays() {
		Set<Date> dates = GreekHolidays.getKnownGreekHolidays(2013);
		Set<Date> easterDates = GreekHolidays.getOrthodoxEasterHolidays(2013);
		assertTrue(dates.containsAll(easterDates));
	}

	/**
	 * test method for.
	 */
	@Test
	public void TestIsStandardGreekHoliday() {
		Date d3 = DateUtils.getDate(2013, Calendar.MAY, 10);
		for (Date d : GreekHolidays.getKnownGreekHolidays(2013)) {
			assertTrue(GreekHolidays.isStandardGreekHoliday(d));
		}
		assertFalse(GreekHolidays.isStandardGreekHoliday(d3));
	}

	

}
