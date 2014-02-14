package gr.interamerican.bo2.utils.commands;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 */
public class TestAbstractNumericCalculator {	
	/**
	 * Class for the test.
	 */
	static class ConcreteNumericCalculator<T> extends AbstractNumericCalculator<T> {		
		public void execute() { /* empty */}
	}
	
	
	/**
	 * Test for getNumericResult().
	 */
	@Test
	public void testGetNumericResult() {
		ConcreteNumericCalculator<Object> calc = new ConcreteNumericCalculator<Object>();		
		calc.numericResult = 5;
		Assert.assertEquals(calc.numericResult, calc.getNumericResult());
	}
	
	/**
	 * Test for getNumericResult().
	 */
	@Test
	public void testGetInput() {
		ConcreteNumericCalculator<Object> calc = new ConcreteNumericCalculator<Object>();		
		calc.input = new Object();
		Assert.assertEquals(calc.input, calc.getInput());
	}
	
	/**
	 * Test for getNumericResult().
	 */
	@Test
	public void testSetInput() {
		ConcreteNumericCalculator<Object> calc = new ConcreteNumericCalculator<Object>();		
		Object expected = new Object();
		calc.setInput(expected);
		Assert.assertEquals(expected, calc.input);
	}
	
	

}
