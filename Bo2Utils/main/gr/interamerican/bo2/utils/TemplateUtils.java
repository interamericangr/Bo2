/*******************************************************************************
 * Copyright (c) 2013 INTERAMERICAN PROPERTY AND CASUALTY INSURANCE COMPANY S.A. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/lesser.html
 * 
 * This library is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 ******************************************************************************/
package gr.interamerican.bo2.utils;

import java.util.Map;

/**
 * Utility class that provides utilities that make it easier
 * to modify template files.
 * 
 * A template file is a file that has some placeholders for values.
 */
public class TemplateUtils {
	
	/**
	 * char sequence to signify variables
	 */
	public static final String PREFIX = "_:";  //$NON-NLS-1$
	
	/**
	 * Converts a name to a variable.
	 * 
	 * @param name
	 * @return the variable
	 */
	public static String variable(String name) {
		return PREFIX + name;
	}
	
	/**
	 * Replaces a variable. 
	 * 
	 * If the name of the variable starts with a lower case character, 
	 * then we also search for instances of the variable starting with
	 * a capital letter, and we replace the value with the first letter capital
	 * as well. 
	 * 
	 * @param template
	 * @param name name of the variable
	 * @param value
	 * 
	 * @return Returns the text of the template.
	 */
	public static String replace
	(String template, String name, String value) {
		String var = variable(name.trim());
		String temp = template.replaceAll(var, value);
		if (!StringUtils.startsWithUpperCase(name)) {
			String uname = StringUtils.firstCapital(name);
			String uValue = StringUtils.firstCapital(value);
			temp = replace(temp, uname, uValue);
		}
		return temp;
	}
	
	
	/**
	 * Fills the specified template, by replacing the variables with their
	 * values, as defined in the specified map.
	 * 
	 * @param template
	 *        Template.
	 * @param variables
	 *        Map with variable names being the keys and values being the 
	 *        corresponding value of each variable.
	 *        
	 * @return Returns the template filled.
	 */
	public static String fill(String template, Map<String, String> variables) {		
		String temp = template;		
		for (Map.Entry<String, String> entry : variables.entrySet()) {
			String var = entry.getKey();
			String val = entry.getValue();
			temp = replace(temp, var, val);
		}
		return temp;
	}
	
	/**
	 * Fills the specified template, by replacing the variables with their
	 * values, as defined in the specified map. If the template is null,
	 * returns null.
	 * 
	 * @param template
	 *        Template.
	 * @param variables
	 *        Map with variable names being the keys and values being the 
	 *        corresponding value of each variable.
	 *        
	 * @return Returns the template filled.
	 */
	public static String nullSafeFill(String template, Map<String, String> variables) {
		if (template==null) {
			return null;
		}
		return TemplateUtils.fill(template, variables);
	}

}
