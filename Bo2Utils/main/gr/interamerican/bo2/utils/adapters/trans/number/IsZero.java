package gr.interamerican.bo2.utils.adapters.trans.number;

import gr.interamerican.bo2.utils.adapters.Transformation;

/**
 * {@link Transformation} that returns a boolean value indicating
 * if a Number is equal to zero.
 */
public class IsZero implements Transformation<Number, Boolean> {

	@Override
	public Boolean execute(Number a) {
		return a.doubleValue()==0.0;
	}

	

}
