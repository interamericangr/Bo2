package gr.interamerican.bo2.utils.commands;

import java.util.Date;

import gr.interamerican.bo2.utils.DateUtils;
import gr.interamerican.bo2.utils.attributes.NumericCalculator;
import gr.interamerican.bo2.utils.attributes.SimpleCommand;

/**
 * 
 */
public class DayOfMonth 
extends AbstractNumericCalculator<Date> {
	
	public void execute() {	
		numericResult = DateUtils.days(input);
	}

	
}


/*
 * 
DAYS_OF_MONTH; DateUtils.days((Date)input);	
MONTH; DateUtils.month((Date)input);
YEAR; DateUtils.year((Date) input);
DAYS; DateUtils.daysDifFromEpoch((Date)input);
ABSOLUTE_VALUE; Double d = ((Number) input).doubleValue();
			    return Math.abs(d);
 */ 
