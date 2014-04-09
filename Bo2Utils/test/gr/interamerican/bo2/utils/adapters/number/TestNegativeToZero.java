package gr.interamerican.bo2.utils.adapters.number;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link NegativeToZero}.
 */
public class TestNegativeToZero {
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute_withNegative() {
		NegativeToZero calc = new NegativeToZero();
		Number input = -3;
		Number actual = calc.execute(input);
		Number expected = 0;
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute_withPositive() {
		NegativeToZero calc = new NegativeToZero();
		Number input = 3.0d;
		Number actual = calc.execute(input);
		Number expected = input;
		Assert.assertEquals(expected, actual);
	}

}
