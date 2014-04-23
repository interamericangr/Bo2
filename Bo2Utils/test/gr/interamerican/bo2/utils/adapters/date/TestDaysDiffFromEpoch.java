package gr.interamerican.bo2.utils.adapters.date;

import gr.interamerican.bo2.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link CalculateDaysDiffFromEpoch}.
 */
public class TestDaysDiffFromEpoch {
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute() {
		DaysDiffFromEpoch calc = new DaysDiffFromEpoch();		
		Date input = DateUtils.getDate(2013, Calendar.JULY, 17); 
		Integer expected = DateUtils.daysDifFromEpoch(input);		
		Integer actual = calc.execute(input);		
		Assert.assertEquals(expected, actual);
	}

}
