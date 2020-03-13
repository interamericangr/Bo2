package gr.interamerican.bo2.utils.adapters.trans.string;

import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.adapters.Transformation;

/**
 * {@link Transformation} that trims an input String.
 */
public class Trim 
implements Transformation<String, String>{
	
	
	@Override
	public String execute(String a) {
		return StringUtils.trim(a);
	}
}