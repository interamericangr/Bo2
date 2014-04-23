package gr.interamerican.bo2.utils.adapters.number;

import gr.interamerican.bo2.utils.adapters.InvokeMethod;
import gr.interamerican.bo2.utils.adapters.Transformation;

/**
 * {@link Transformation} that returns the absolute value of the argument.
 */
public class AbsoluteValueInt 
extends InvokeMethod<Integer,Integer> {

	/**
	 * Creates a new AbsoluteValueInt object. 
	 *	
	 */
	public AbsoluteValueInt() {
		super(Math.class, "abs", int.class); //$NON-NLS-1$
	}
	
	

	
}



