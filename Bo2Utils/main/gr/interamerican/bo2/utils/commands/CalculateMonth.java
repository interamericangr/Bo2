package gr.interamerican.bo2.utils.commands;

import gr.interamerican.bo2.utils.DateUtils;

import java.util.Date;

/**
 * Simple command that calculates the month of a date.
 */
public class CalculateMonth 
extends AbstractNumericCalculator<Date> {
	
	public void execute() {	
		numericResult = DateUtils.month(input);
	}

	
}


