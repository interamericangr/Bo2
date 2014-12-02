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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * {@link ResourceBundle} facade.
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
	 *            Path to a resource file in the class path that contains the
	 *            resource bundle.
	 */
	public MessagesBean(String resourceBundlePath) {
		this(resourceBundlePath, null);
	}

	/**
	 * Creates a new Messages object.
	 * 
	 * @param resourceBundlePath
	 *            Path to a resource file in the class path that contains the
	 *            resource bundle.
	 * @param locale
	 *            Current {@link Locale}.
	 */
	public MessagesBean(String resourceBundlePath, Locale locale) {
		if (locale != null) {
			resourceBundle = ResourceBundle.getBundle(resourceBundlePath, locale, new XmlControl());
		} else {
			resourceBundle = ResourceBundle.getBundle(resourceBundlePath, new XmlControl());
		}
	}
	
	/**
	 * Factory method. Uses the default system Locale.
	 * 
	 * @param resourceBundlePath
	 *            Path to a resource file in the class path that contains the
	 *            resource bundle.
	 *            
	 * @return MessagesBean instance.
	 */
	public static MessagesBean get(String resourceBundlePath) {
		return new MessagesBean(resourceBundlePath, null);
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

	/**
	 * ResourceBundle.Control extension for working with XML files also.
	 */
	static class XmlControl extends ResourceBundle.Control {
		
		/**
		 * xml format
		 */
		static final String XML = "xml"; //$NON-NLS-1$

		@Override
		public List<String> getFormats(String baseName) {
			if (baseName == null) {
				throw new NullPointerException();
			}
			List<String> standardFormats = super.getFormats(baseName);
			List<String> allFormats = new ArrayList<String>(standardFormats);
			allFormats.add(XML);
			return allFormats;
		}

		@Override
		public ResourceBundle newBundle(
		String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
		throws IllegalAccessException, InstantiationException, IOException {
			
			if (baseName == null || locale == null || format == null || loader == null) {
				throw new NullPointerException();
			}
			
			ResourceBundle bundle = null;
			if (format.equals(XML)) {
				
				String bundleName = toBundleName(baseName, locale);
				String resourceName = toResourceName(bundleName, format);
				InputStream stream = null;
				
				if (reload) {
					URL url = loader.getResource(resourceName);
					if (url != null) {
						URLConnection connection = url.openConnection();
						if (connection != null) {
							connection.setUseCaches(false); // Disable caches to get fresh data for reloading.
							stream = connection.getInputStream();
						}
					}
				} else {
					stream = loader.getResourceAsStream(resourceName);
				}
				if (stream != null) {
					BufferedInputStream bis = new BufferedInputStream(stream);
					bundle = new XMLResourceBundle(bis);
					bis.close();
				}
			} else {
				bundle = super.newBundle(baseName, locale, format, loader, reload);
			}
			return bundle;
		}
	}

	/**
	 * Loads an XML ResourceBundle
	 */
	static class XMLResourceBundle extends ResourceBundle {
		
		/**
		 * properties
		 */
		private Properties props;

		/**
		 * Creates a new XMLResourceBundle object. 
		 *
		 * @param stream
		 * @throws IOException
		 */
		XMLResourceBundle(InputStream stream) throws IOException {
			props = new Properties();
			props.loadFromXML(stream);
		}

		@Override
		protected Object handleGetObject(String key) {
			return props.getProperty(key);
		}

		@Override
		public Enumeration<String> getKeys() {
			Set<String> handleKeys = props.stringPropertyNames(); 
			return Collections.enumeration(handleKeys); 
		}

	}

}
