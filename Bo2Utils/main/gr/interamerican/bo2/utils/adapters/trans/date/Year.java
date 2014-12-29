package gr.interamerican.bo2.utils.adapters.trans.date;

import gr.interamerican.bo2.utils.DateUtils;
import gr.interamerican.bo2.utils.adapters.Transformation;
import gr.interamerican.bo2.utils.adapters.trans.InvokeMethod;

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


