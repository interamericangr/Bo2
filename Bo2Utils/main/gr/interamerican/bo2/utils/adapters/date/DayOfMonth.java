package gr.interamerican.bo2.utils.adapters.date;

import gr.interamerican.bo2.utils.DateUtils;
import gr.interamerican.bo2.utils.adapters.InvokeMethod;
import gr.interamerican.bo2.utils.adapters.Transformation;

import java.util.Date;

/**
 * {@link Transformation} that calculates the day of month of a date.
 */
public class DayOfMonth
extends InvokeMethod<Date,Integer> {
	
	/**
	 * Creates a new DayOfMonth object. 
	 *
	 */
	public DayOfMonth() {
		super(DateUtils.class, "days", Date.class); //$NON-NLS-1$
	}
	
}


