package gr.interamerican.bo2.utils.adapters.number;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link NegativeToZeroLong}.
 */
public class TestNegativeToZeroLong {
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute_withNegative() {
		NegativeToZeroLong calc = new NegativeToZeroLong();
		Long input = -3L;
		Long actual = calc.execute(input);
		Long expected = 0L;
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute_withPositive() {
		NegativeToZeroLong calc = new NegativeToZeroLong();
		Long input = 3L;
		Long actual = calc.execute(input);
		Long expected = input;
		Assert.assertEquals(expected, actual);
	}

}
