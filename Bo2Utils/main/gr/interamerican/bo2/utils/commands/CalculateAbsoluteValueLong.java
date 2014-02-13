package gr.interamerican.bo2.utils.commands;


/**
 * Simple command that calculates the day of month of a date.
 */
public class CalculateAbsoluteValueLong 
extends AbstractNumericCalculator<Long> {
	
	public void execute() {	
		numericResult = Math.abs(input);
	}

	
}



