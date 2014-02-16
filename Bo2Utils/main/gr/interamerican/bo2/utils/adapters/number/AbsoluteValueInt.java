package gr.interamerican.bo2.utils.adapters.number;

import gr.interamerican.bo2.utils.adapters.InvokeMethod;
import gr.interamerican.bo2.utils.adapters.Transformation;

import java.math.BigDecimal;

/**
 * {@link Transformation} that returns the absolute value of the argument.
 */
public class AbsoluteValueInt 
extends InvokeMethod<Integer,Integer> {

	/**
	 * Creates a new AbsoluteValueDouble object. 
	 *	
	 */
	public AbsoluteValueInt() {
		super(Math.class, "abs", int.class); //$NON-NLS-1$
	}
	
	

	
}



