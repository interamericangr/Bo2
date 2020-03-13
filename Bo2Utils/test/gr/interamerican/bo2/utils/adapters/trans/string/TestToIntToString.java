package gr.interamerican.bo2.utils.adapters.trans.string;

import gr.interamerican.bo2.utils.adapters.trans.date.DayOfMonth;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link DayOfMonth}.
 */
public class TestToIntToString {
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute() {
		ToIntToString calc = new ToIntToString();
		Double input = 17.0;		  
		String actual = calc.execute(input);		
		Assert.assertEquals("17", actual); //$NON-NLS-1$
	}

}
