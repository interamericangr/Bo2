package gr.interamerican.bo2.utils.greek.adapters;

import gr.interamerican.bo2.utils.adapters.date.DayOfMonth;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link DayOfMonth}.
 */
public class TestContainsNonGreekLetters {
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute() {
		ContainsNonGreekLetters calc = new ContainsNonGreekLetters();
		String input = "xcfg";		  //$NON-NLS-1$
		boolean actual = calc.execute(input);		
		Assert.assertEquals(true, actual);
	}

}
