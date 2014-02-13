package gr.interamerican.bo2.utils.commands;


/**
 * Simple command that calculates the day of month of a date.
 */
public class CalculateAbsoluteValueFloat 
extends AbstractNumericCalculator<Float> {
	
	public void execute() {	
		numericResult = Math.abs(input);
	}

	
}



