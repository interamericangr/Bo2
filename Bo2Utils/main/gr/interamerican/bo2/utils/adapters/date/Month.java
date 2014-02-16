package gr.interamerican.bo2.utils.adapters.date;

import gr.interamerican.bo2.utils.DateUtils;
import gr.interamerican.bo2.utils.adapters.InvokeMethod;
import gr.interamerican.bo2.utils.adapters.Transformation;

import java.util.Date;

/**
 * {@link Transformation} that calculates the month of a date.
 */
public class Month
extends InvokeMethod<Date,Integer> {
	
	/**
	 * Creates a new Month object. 
	 *
	 */
	public Month() {
		super(DateUtils.class, "month", Date.class); //$NON-NLS-1$
	}
	
}


