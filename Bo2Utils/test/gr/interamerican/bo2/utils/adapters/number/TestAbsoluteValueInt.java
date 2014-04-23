package gr.interamerican.bo2.utils.adapters.number;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link AbsoluteValueInt}.
 */
public class TestAbsoluteValueInt {
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute() {
		AbsoluteValueInt calc = new AbsoluteValueInt();
		Integer input = -3; 
		Object actual = calc.execute(input);
		Assert.assertEquals(Math.abs(input), actual);
	}

}
