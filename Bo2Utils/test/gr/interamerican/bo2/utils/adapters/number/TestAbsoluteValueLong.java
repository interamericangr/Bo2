package gr.interamerican.bo2.utils.adapters.number;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link AbsoluteValueLong}.
 */
public class TestAbsoluteValueLong {
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute() {
		AbsoluteValueLong calc = new AbsoluteValueLong();
		Long input = -3L; 
		Object actual = calc.execute(input);
		Assert.assertEquals(Math.abs(input), actual);
	}

}
