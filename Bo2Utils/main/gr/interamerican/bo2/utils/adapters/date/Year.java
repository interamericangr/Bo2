package gr.interamerican.bo2.utils.adapters.date;

import gr.interamerican.bo2.utils.DateUtils;
import gr.interamerican.bo2.utils.adapters.InvokeMethod;
import gr.interamerican.bo2.utils.adapters.Transformation;

import java.util.Date;

/**
 * {@link Transformation} that calculates the year of a date.
 */
public class Year
extends InvokeMethod<Date,Integer> {
	
	/**
	 * Creates a new Month object. 
	 *
	 */
	public Year() {
		super(DateUtils.class, "year", Date.class); //$NON-NLS-1$
	}
	
}


