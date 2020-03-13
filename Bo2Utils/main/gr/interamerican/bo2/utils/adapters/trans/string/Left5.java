package gr.interamerican.bo2.utils.adapters.trans.string;

import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.adapters.Transformation;

/**
 * {@link Transformation} that returns the five left characters of a string.
 * 
 */
public class Left5 
implements Transformation<String, String>{
	
	
	@Override
	public String execute(String a) {
		if (a==null) {
			return null;
		}
		return StringUtils.mid(a, 0, 5);
	}
}