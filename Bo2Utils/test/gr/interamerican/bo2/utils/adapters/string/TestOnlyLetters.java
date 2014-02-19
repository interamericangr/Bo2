package gr.interamerican.bo2.utils.adapters.string;

import gr.interamerican.bo2.utils.adapters.date.DayOfMonth;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link DayOfMonth}.
 */
public class TestOnlyLetters {
	
	/**
	 * Test for execute().
	 */
	@SuppressWarnings("nls")
	@Test
	public void testExecute() {
		OnlyLetters calc = new OnlyLetters();
		String input = "xcfg 2525";		 
		String actual = calc.execute(input);		
		Assert.assertEquals("xcfg", actual);
	}

}
