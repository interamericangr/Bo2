package gr.interamerican.bo2.utils.adapters.number;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link NegativeToZeroShort}.
 */
public class TestNegativeToZeroShort {
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute_withNegative() {
		NegativeToZeroShort calc = new NegativeToZeroShort();
		Short input = -3;
		Short actual = calc.execute(input);
		Short expected = 0;
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute_withPositive() {
		NegativeToZeroShort calc = new NegativeToZeroShort();
		Short input = 3;
		Short actual = calc.execute(input);
		Short expected = input;
		Assert.assertEquals(expected, actual);
	}

}
