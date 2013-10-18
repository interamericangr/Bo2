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
package gr.interamerican.bo2.impl.open.utils;


import gr.interamerican.bo2.utils.StreamUtils;

import java.io.IOException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Utilities class.
 * 
 *
 */
public class Util {
	/**
	 * parameters bundle name
	 */
	private static final String PARAMETERS_BUNDLE_NAME = 
		"gr.interamerican.bo2.impl.open.utils.parameters"; //$NON-NLS-1$
	

	/**
	 * parameters resource bundle
	 */
	private static final ResourceBundle PARAMETERS_BUNDLE = 
		ResourceBundle.getBundle(PARAMETERS_BUNDLE_NAME);

	/**
	 * Hidden constructor.
	 * 
	 * This is a utility class having only static methods.
	 * There is no need to create any instance of this class.
	 */
	private Util() {/* empty */}

	/**
	 * Gets the Bo2 parameter associated with a key.
	 * 
	 * @param key
	 * @return Returns the literal associated with the specified key.
	 */
	public static String getParameter(String key) {
		try {
			return PARAMETERS_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
	
	
	/**
	 * Reads a text file from a resource file in the local classpath
	 * and returns an array with the lines of the file.
	 * 
	 * Empty lines are excluded. Also, everuthing following a '#' on
	 * a line is excluded.
	 * 
	 * @param path Path to the file. 
	 * 
	 * @return Returns an array with names of mappings files.
	 * @throws IOException 
	 */
	public static String[] readFile(String path) 
	throws IOException {
		String[] ret = StreamUtils.readResourceFile(path, true, true);
		if(ret==null){
			throw Exceptions.runtime(Messages.RESOURCE_NOT_FOUND, path);
		}
		return ret;
	}
	
	
	
}
