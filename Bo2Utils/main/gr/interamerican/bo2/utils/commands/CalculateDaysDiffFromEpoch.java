package gr.interamerican.bo2.utils.commands;

import gr.interamerican.bo2.utils.DateUtils;

import java.util.Date;

/**
 * Simple command that calculates the day of month of a date.
 */
public class CalculateDaysDiffFromEpoch 
extends AbstractNumericCalculator<Date> {
	
	public void execute() {	
		numericResult = DateUtils.daysDifFromEpoch(input);
	}

	
}



