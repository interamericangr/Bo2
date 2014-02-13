package gr.interamerican.bo2.utils.commands;

import gr.interamerican.bo2.utils.DateUtils;

import java.util.Date;

/**
 * Simple command that calculates the day of month of a date.
 */
public class CalculateDayOfMonth 
extends AbstractNumericCalculator<Date> {
	
	public void execute() {	
		numericResult = DateUtils.days(input);
	}

	
}


