package gr.interamerican.bo2.utils.adapters.number;

import gr.interamerican.bo2.utils.adapters.InvokeMethod;
import gr.interamerican.bo2.utils.adapters.Transformation;

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



