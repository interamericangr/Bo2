package gr.interamerican.bo2.utils.adapters.number;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link NegativeToZeroInt}.
 */
public class TestNegativeToZeroInteger {
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute_withNegative() {
		NegativeToZeroInt calc = new NegativeToZeroInt();
		Integer input = -3;
		Integer actual = calc.execute(input);
		Integer expected = 0;
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute_withPositive() {
		NegativeToZeroInt calc = new NegativeToZeroInt();
		Integer input = 3;
		Integer actual = calc.execute(input);
		Integer expected = input;
		Assert.assertEquals(expected, actual);
	}

}
