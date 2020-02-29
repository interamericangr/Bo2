package gr.interamerican.bo2.utils.adapters.trans.string;

import gr.interamerican.bo2.utils.adapters.Transformation;

/**
 * {@link Transformation} that converts a number to integer.
 */
public class ToInt 
implements Transformation<Number, Integer>{	
	
	@Override
	public Integer execute(Number a) {
		return a.intValue();
	}
	
}