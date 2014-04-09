package gr.interamerican.bo2.utils.adapters.number;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link NegativeToZeroBigDecimal}.
 */
public class TestNegativeToZeroBigDecimal {
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute_withNegative() {
		NegativeToZeroBigDecimal calc = new NegativeToZeroBigDecimal();
		BigDecimal input = new BigDecimal(-6).setScale(3); 
		BigDecimal actual = calc.execute(input);
		BigDecimal expected = BigDecimal.ZERO.setScale(3);
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute_withPositive() {
		NegativeToZeroBigDecimal calc = new NegativeToZeroBigDecimal();
		BigDecimal input = new BigDecimal(11).setScale(3); 
		BigDecimal actual = calc.execute(input);
		BigDecimal expected = input;
		Assert.assertEquals(expected, actual);
	}

}
