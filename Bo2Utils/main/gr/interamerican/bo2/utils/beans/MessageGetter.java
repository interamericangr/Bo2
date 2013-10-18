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

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * A message getter fetches messages from a resource bundle. 
 */
public class MessageGetter {
	
	/**
	 * resourceBoundle.
	 */
	private ResourceBundle resourceBundle;
	
	/**
	 * Creates a new MessageGetter object. 
	 *
	 * @param resourceBundleName
	 */
	public MessageGetter(String resourceBundleName) {
		this.resourceBundle = ResourceBundle.getBundle(resourceBundleName);
	}
	
	/**
	 * Creates a new MessageGetter object. 
	 *
	 * @param resourceBundleName
	 * @param locale 
	 */
	public MessageGetter(String resourceBundleName, Locale locale) {
		if(locale==null) {
			this.resourceBundle = ResourceBundle.getBundle(resourceBundleName);
		} else {
			this.resourceBundle = ResourceBundle.getBundle(resourceBundleName, locale);
		}
		
	}
	
	/**
	 * Gets the literal associated with a key.
	 * 
	 * @param key
	 * @return returns the literal associated with the specified key.
	 */
	public String getString(String key) {
		try {
			return resourceBundle.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
	

}
