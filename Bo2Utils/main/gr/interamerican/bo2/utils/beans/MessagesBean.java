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
package gr.interamerican.bo2.utils.beans;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 
 */
public class MessagesBean {
	
	/**
	 * ResourceBundle.
	 */
	ResourceBundle resourceBundle;

	/**
	 * Creates a new Messages object. 
	 * 
	 * @param resourceBundlePath 
	 *        Path to a resource file in the class path that contains
	 *        the resource bundle.
	 *
	 */
	public MessagesBean(String resourceBundlePath) {
		resourceBundle = ResourceBundle.getBundle(resourceBundlePath);		
	}
	
	/**
	 * Creates a new Messages object. 
	 * 
	 * @param resourceBundlePath 
	 *        Path to a resource file in the class path that contains
	 *        the resource bundle.
	 * @param locale
	 *        Current {@link Locale}. 
	 *
	 */
	public MessagesBean(String resourceBundlePath, Locale locale) {
		if(locale != null) {
			resourceBundle = ResourceBundle.getBundle(resourceBundlePath, locale);
		} else {
			resourceBundle = ResourceBundle.getBundle(resourceBundlePath);
		}
	}

	/**
	 * Gets the string that corresponds to the specified key.
	 * 
	 * @param key
	 * 
	 * @return String
	 */
	public String getString(String key) {
		try {
			return resourceBundle.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
	
	/**
	 * Gets the string that corresponds to the specified key.
	 * 
	 * @param key
	 * @param params 
	 * @return String
	 */
	public String getString(String key, Object... params) {
		try {
			String msg = resourceBundle.getString(key);
			String result = MessageFormat.format(msg, params);
			return result;
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
	

}
