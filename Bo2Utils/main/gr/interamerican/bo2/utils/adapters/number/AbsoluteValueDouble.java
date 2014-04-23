package gr.interamerican.bo2.utils.adapters.number;

import gr.interamerican.bo2.utils.adapters.InvokeMethod;
import gr.interamerican.bo2.utils.adapters.Transformation;

/**
 * {@link Transformation} that returns the absolute value of the argument.
 */
public class AbsoluteValueDouble 
extends InvokeMethod<Double,Double> {

	/**
	 * Creates a new AbsoluteValueDouble object. 
	 *	
	 */
	public AbsoluteValueDouble() {
		super(Math.class, "abs", double.class); //$NON-NLS-1$
	}
	
	

	
}



