package gr.interamerican.bo2.impl.open.namedstreams;

import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.utils.StringUtils;


/**
 * The Class InvalidNsdUtility.
 */
public class InvalidNsdUtility {

	/**
	 * Creates an initialization exception.
	 *
	 * @param problem the problem
	 * @param name the name
	 * @return Returns the exception.
	 */
	public static InitializationException 
	invalid(String problem, String name) {		
		return new InitializationException(invalidMsg(problem,name));
	}
	
	/**
	 * Creates an initialization exception.
	 *
	 * @param problem the problem
	 * @param name the name
	 * @return Returns the exception.
	 */
	public static String 
	invalidMsg(String problem, String name) {
		@SuppressWarnings("nls")
		String msg = StringUtils.concat(
			problem, " for the named stream ", name);
		return msg;
	}

}
