package gr.interamerican.bo2.utils.adapters.string;

import gr.interamerican.bo2.utils.GreekUtils;
import gr.interamerican.bo2.utils.adapters.InvokeMethod;
import gr.interamerican.bo2.utils.adapters.Transformation;

/**
 * This {@link Transformation} gets the uppercase form of a string,
 * and then replaces any latin character that has a visually equal
 * greek character with its visually equal greek character.
 */
public class RemoveSymbolsAndReplaceLatinWithSimilarGreekChars extends InvokeMethod<String,String> {

	/**
	 * Creates a new GreekPlateNo object. 
	 *
	 */
	public RemoveSymbolsAndReplaceLatinWithSimilarGreekChars() {
		super(GreekUtils.class, "removeSymbolsAndReplaceLatinWithSimilarGreekChars", String.class); //$NON-NLS-1$
	}
	
	
}
