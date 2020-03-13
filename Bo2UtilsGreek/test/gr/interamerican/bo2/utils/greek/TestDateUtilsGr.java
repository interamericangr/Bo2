package gr.interamerican.bo2.utils.greek;

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

/**
 * Tests for {@link DateUtilsGr}.
 */
public class TestDateUtilsGr {

	/**
	 * tests getDateDDMMYYYY().
	 */
	@Test
	public void testGetDateDDMMYYYY() {
		String input = "01/02/1900"; //$NON-NLS-1$
		Date actual = DateUtilsGr.getDateDDMMYYYY(input);
		Date expected = DateUtils.getDate(1900, Calendar.FEBRUARY, 1);
		assertEquals(actual.compareTo(expected), 0);
	}


}
