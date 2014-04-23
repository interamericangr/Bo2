package gr.interamerican.bo2.utils.adapters.number;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link AbsoluteValueFloat}.
 */
public class TestAbsoluteValueFloat {
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute() {
		AbsoluteValueFloat calc = new AbsoluteValueFloat();
		Float input = -3.0f; 
		Object actual = calc.execute(input);
		Assert.assertEquals(Math.abs(input), actual);
	}

}
