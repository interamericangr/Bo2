package gr.interamerican.bo2.utils.adapters.number;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link AbsoluteValueInt}.
 */
public class TestAbsoluteValueShort {
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute() {
		AbsoluteValueShort calc = new AbsoluteValueShort();
		Short input = -3; 
		Short actual = calc.execute(input);
		Short expected = (short) Math.abs(input);
		Assert.assertEquals(expected, actual);
	}

}
