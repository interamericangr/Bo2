package gr.interamerican.bo2.utils.commands;

import java.math.BigDecimal;

/**
 * Simple command that calculates the day of month of a date.
 */
public class CalculateAbsoluteValueBigDecimal 
extends AbstractNumericCalculator<BigDecimal> {
	
	public void execute() {	
		numericResult = input.abs();
	}

	
}



