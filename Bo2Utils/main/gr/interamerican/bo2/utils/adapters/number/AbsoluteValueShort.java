package gr.interamerican.bo2.utils.adapters.number;

import gr.interamerican.bo2.utils.adapters.Transformation;

/**
 * {@link Transformation} that returns the absolute value of the argument.
 */
public class AbsoluteValueShort
implements Transformation<Short, Short> {
	
	@Override
	public Short execute(Short a) {
		int abs = Math.abs(a);
		return (short) abs;
	}

	
	

	
}



