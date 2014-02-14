package gr.interamerican.bo2.utils.commands;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link CalculateAbsoluteValueDouble}.
 */
public class TestCalculateAbsoluteValueDouble {
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute() {
		CalculateAbsoluteValueDouble calc = new CalculateAbsoluteValueDouble();
		Double input = new Double(-5.6); 
		calc.setInput(input);
		calc.execute();
		Assert.assertEquals(Math.abs(input), calc.numericResult);
	}

}
