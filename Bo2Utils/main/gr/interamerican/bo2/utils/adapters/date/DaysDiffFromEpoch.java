package gr.interamerican.bo2.utils.adapters.date;

import gr.interamerican.bo2.utils.DateUtils;
import gr.interamerican.bo2.utils.adapters.InvokeMethod;
import gr.interamerican.bo2.utils.adapters.Transformation;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * {@link Transformation} that calculates the time passed between a date and <u>epoch</u> 
 * in days.<br>
 * 
 * Epoch is defined in {@link GregorianCalendar} as January 1, 1970. If the
 * date passed as parameter is before <u>epoch</u> then a negative result
 * will be returned.
 */
public class DaysDiffFromEpoch
extends InvokeMethod<Date,Integer> {
	
	/**
	 * Creates a new DaysDiffFromEpoch object. 
	 *
	 */
	public DaysDiffFromEpoch() {
		super(DateUtils.class, "daysDifFromEpoch", Date.class); //$NON-NLS-1$
	}
	
}


