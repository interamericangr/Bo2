package gr.interamerican.bo2.utils.adapters.number;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link NegativeToZeroFloat}.
 */
public class TestNegativeToZeroFloat {
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute_withNegative() {
		NegativeToZeroFloat calc = new NegativeToZeroFloat();
		Float input = -3.4f;
		Float actual = calc.execute(input);
		Float expected = 0.0f;
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute_withPositive() {
		NegativeToZeroFloat calc = new NegativeToZeroFloat();
		Float input = 3.4f;
		Float actual = calc.execute(input);
		Float expected = input;
		Assert.assertEquals(expected, actual);
	}

}
