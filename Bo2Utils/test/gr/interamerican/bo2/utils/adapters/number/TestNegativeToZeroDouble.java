package gr.interamerican.bo2.utils.adapters.number;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link NegativeToZeroDouble}.
 */
public class TestNegativeToZeroDouble {
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute_withNegative() {
		NegativeToZeroDouble calc = new NegativeToZeroDouble();
		Double input = -3.4d;
		Double actual = calc.execute(input);
		Double expected = 0.0d;
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute_withPositive() {
		NegativeToZeroDouble calc = new NegativeToZeroDouble();
		Double input = 3.4d;
		Double actual = calc.execute(input);
		Double expected = input;
		Assert.assertEquals(expected, actual);
	}

}
