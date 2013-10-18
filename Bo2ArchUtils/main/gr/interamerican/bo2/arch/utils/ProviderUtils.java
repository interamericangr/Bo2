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
package gr.interamerican.bo2.arch.utils;

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.ResourceWrapper;
import gr.interamerican.bo2.arch.exceptions.InitializationException;

import java.util.Properties;

/**
 * Utility class for provider relevant functions.
 */
public class ProviderUtils {

	/**
	 * Private constructor of utility class.
	 *
	 */
	private ProviderUtils() {/* empty hidden constructor of utility class */}
	
	/**
	 * Gets a mandatory property from the specified properties object.
	 * 
	 * @param properties 
	 *        Properties
	 * @param key
	 *        Key of the property.
	 *        
	 * @return Returns the value of the property.
	 * 
	 * @throws InitializationException
	 *         If the property does not exist.
	 */
	public static String getMandatoryProperty(Properties properties, String key) 
	throws InitializationException {
		String val = properties.getProperty(key);
		if (val==null) {
			@SuppressWarnings("nls")
			String msg = "Property " + key + " not set";
			throw new InitializationException(msg);
		}
		return val;
	}
	
	/**
	 * Gets the resource wrapper with the specified name and type from
	 * the specified provider.
	 * 
	 * This method will return null, if an {@link InitializationException}
	 * occurs, therefore it should be used preferably when it is certain that 
	 * the resource wrapper managers of the provider have been initialized. 
	 * 
	 * @param resourceName
	 *        Name of resource wrapper manager.
	 * @param wrapperClass
	 *        Class of resource wrapper.
	 * @param provider
	 *        Provider.
	 * @param <C>
	 *        Type of resource wrapper.
	 * 
	 * @return Returns the resource wrapper if it exists. If not, returns null.
	 */
	public static <C extends ResourceWrapper> C getResourceWrapper
	(String resourceName, Class<C> wrapperClass, Provider provider) {
		try {
			return provider.getResource(resourceName, wrapperClass);
		} catch (InitializationException e) {
			return null;
		}
	}
	
}
