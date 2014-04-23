package gr.interamerican.bo2.utils.adapters.date;

import gr.interamerican.bo2.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link DayOfMonth}.
 */
public class TestDayOfMonth {
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute() {
		DayOfMonth calc = new DayOfMonth();
		Integer day = 17;
		Date input = DateUtils.getDate(2013, Calendar.JULY, day); 
		Integer actual = calc.execute(input);		
		Assert.assertEquals(day, actual);
	}

}
