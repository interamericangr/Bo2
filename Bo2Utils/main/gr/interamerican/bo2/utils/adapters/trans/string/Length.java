package gr.interamerican.bo2.utils.adapters.trans.string;

import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.adapters.Transformation;
import gr.interamerican.bo2.utils.adapters.trans.InvokeMethod;

/**
 * {@link Transformation} that calculates the length of a String.
 */
public class Length extends InvokeMethod<String,Integer> {

	/**
	 * Creates a new Length object. 
	 *
	 */
	public Length() {
		super(StringUtils.class, "length", String.class); //$NON-NLS-1$
	}
	
	

}
