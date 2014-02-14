package gr.interamerican.bo2.utils.commands;

import java.math.BigDecimal;

import org.junit.Assert;

import org.junit.Test;

/**
 * Tests for {@link CalculateAbsoluteValueBigDecimal}.
 */
public class TestCalculateAbsoluteValueBigDecimal {
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute() {
		CalculateAbsoluteValueBigDecimal calc = new CalculateAbsoluteValueBigDecimal();
		BigDecimal input = new BigDecimal(-6).setScale(3); 
		calc.setInput(input);
		calc.execute();
		Assert.assertEquals(input.abs(), calc.numericResult);
	}

}
