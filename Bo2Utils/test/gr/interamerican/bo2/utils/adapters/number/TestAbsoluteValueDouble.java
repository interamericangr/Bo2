package gr.interamerican.bo2.utils.adapters.number;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link AbsoluteValueDouble}.
 */
public class TestAbsoluteValueDouble {
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute() {
		AbsoluteValueDouble calc = new AbsoluteValueDouble();
		Double input = -3.0; 
		Object actual = calc.execute(input);
		Assert.assertEquals(Math.abs(input), actual);
	}

}
