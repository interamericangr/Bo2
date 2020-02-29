package gr.interamerican.bo2.utils.adapters.trans.string;

import gr.interamerican.bo2.utils.adapters.Transformation;

/**
 * {@link Transformation} that converts a number to string
 * after removing its decimal digits.
 */
public class ToIntToString 
implements Transformation<Number, String>{	
	
	@Override
	public String execute(Number a) {
		Integer i = a.intValue();
		return Integer.toString(i).trim();
	}
	
}