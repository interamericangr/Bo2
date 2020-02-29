package gr.interamerican.bo2.utils.adapters.trans.string;

import gr.interamerican.bo2.utils.adapters.trans.date.DayOfMonth;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link DayOfMonth}.
 */
public class TestToInt {
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute() {
		ToInt calc = new ToInt();
		Double input = 17.3;		  
		Integer actual = calc.execute(input);		
		Assert.assertEquals(Integer.valueOf(input.intValue()), actual);
	}

}
