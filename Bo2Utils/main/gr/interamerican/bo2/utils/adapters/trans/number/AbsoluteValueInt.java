package gr.interamerican.bo2.utils.adapters.trans.number;

import gr.interamerican.bo2.utils.adapters.Transformation;
import gr.interamerican.bo2.utils.adapters.trans.InvokeMethod;

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



