package gr.interamerican.bo2.utils.adapters.string;

import gr.interamerican.bo2.utils.adapters.date.DayOfMonth;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link DayOfMonth}.
 */
public class TestLength {
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute() {
		Length calc = new Length();
		String input = "xcfg";		  //$NON-NLS-1$
		Integer actual = calc.execute(input);		
		Assert.assertEquals(Integer.valueOf(input.length()), actual);
	}

}
