package gr.interamerican.bo2.utils.adapters.number;

import gr.interamerican.bo2.utils.adapters.InvokeMethod;
import gr.interamerican.bo2.utils.adapters.Transformation;

import java.math.BigDecimal;

/**
 * {@link Transformation} that returns the absolute value of the argument.
 */
public class AbsoluteValueLong 
extends InvokeMethod<Long,Long> {

	/**
	 * Creates a new AbsoluteValueDouble object. 
	 *	
	 */
	public AbsoluteValueLong() {
		super(Math.class, "abs", long.class); //$NON-NLS-1$
	}
	
	

	
}



