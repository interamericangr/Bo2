package gr.interamerican.bo2.utils.greek.adapters;

import gr.interamerican.bo2.utils.adapters.InvokeMethod;
import gr.interamerican.bo2.utils.adapters.Transformation;
import gr.interamerican.bo2.utils.greek.GreekUtils;

/**
 * {@link Transformation} that calculates the length of a String.
 */
public class ContainsNonGreekLetters extends InvokeMethod<String,Boolean> {

	/**
	 * Creates a new Length object. 
	 *
	 */
	public ContainsNonGreekLetters() {
		super(GreekUtils.class, "containsNonGreekLetters", String.class); //$NON-NLS-1$
	}
	
	

}
