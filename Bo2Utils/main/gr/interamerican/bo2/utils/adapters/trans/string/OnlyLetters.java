package gr.interamerican.bo2.utils.adapters.trans.string;

import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.adapters.Transformation;
import gr.interamerican.bo2.utils.adapters.trans.InvokeMethod;

/**
 * {@link Transformation} that removes any character that is not a letter
 * from a String.
 */
public class OnlyLetters extends InvokeMethod<String,String> {

	/**
	 * Creates a new Length object. 
	 *
	 */
	public OnlyLetters() {
		super(StringUtils.class, "removeAllButLetters", String.class); //$NON-NLS-1$
	}
	
	

}
