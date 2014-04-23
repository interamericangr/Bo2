package gr.interamerican.bo2.utils.adapters.number;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link AbsoluteValue}.
 */
public class TestAbsoluteValue {
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute_withBigDecimal() {
		AbsoluteValue calc = new AbsoluteValue();
		BigDecimal input = new BigDecimal(-6).setScale(3); 
		Object actual = calc.execute(input);
		Assert.assertEquals(input.abs(), actual);
	}
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute_withFloat() {
		AbsoluteValue calc = new AbsoluteValue();
		Float input = -2.31f; 
		Object actual = calc.execute(input);
		Assert.assertEquals(Math.abs(input), actual);
	}
	
	

}
