package gr.interamerican.bo2.utils.adapters.trans.number;

import gr.interamerican.bo2.utils.adapters.Transformation;
import gr.interamerican.bo2.utils.adapters.trans.InvokeMethod;

/**
 * {@link Transformation} that returns the absolute value of the argument.
 */
public class AbsoluteValueFloat 
extends InvokeMethod<Float,Float> {

	/**
	 * Creates a new AbsoluteValueFloat object. 
	 *	
	 */
	public AbsoluteValueFloat() {
		super(Math.class, "abs", float.class); //$NON-NLS-1$
	}
	
	

	
}



