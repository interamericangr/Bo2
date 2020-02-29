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

import java.nio.charset.Charset;
import java.util.Properties;

/**
 * Runtime environment for Bo2Utils.
 */
public class Bo2UtilsEnvironment {
		
	/**
	 * Singleton instance.
	 */
	private static Bo2UtilsEnvironment env = new Bo2UtilsEnvironment();
	
	/**
	 * Creates a new Bo2UtilsEnvironment object.
	 *
	 * @param properties the properties
	 */
	public static void set (Properties properties) {	
		if (properties==null) {
			env = new Bo2UtilsEnvironment();
		} else {
			env = new Bo2UtilsEnvironment(properties);
		}
	}
	
	
	/**
	 * Gets the environment.
	 *
	 * @return Returns the environment
	 */
	public static Bo2UtilsEnvironment get() {
		return env;
	}
	
	
	/**
	 * Default date format (short format).
	 */
	String shortDateFormat = "dd/MM/yyyy";  //$NON-NLS-1$
	
	 /**
	 * long date format for calendar objets with time 
	 * as well as for timestamp objects.
	 */
	String longDateFormat = "yyyy-MM-dd-HH:mm:ss.SSS"; //$NON-NLS-1$
	
	/**
	 * Default text charset. This is the charset that should be used when reading or writing
	 * text files. Initialized with the default platform Charset.
	 */
	Charset textEncoding = Charset.defaultCharset();
	
	/**
	 * Default resource file charset. This is the charset that should be used when reading
	 * resource files. Initialized with the default platform Charset.
	 */
	Charset resourceFileEncoding = Charset.defaultCharset();
		
	/**
	 * Properties.
	 */
	Properties properties;
	
	
	/**
	 * Creates a new Bo2UtilsEnvironment object.
	 *
	 */
	private Bo2UtilsEnvironment() {
		super();
		this.properties = new Properties();
	}
	
	
	/**
	 * Creates a new Bo2UtilsEnvironment object.
	 *
	 * @param properties the properties
	 */
	@SuppressWarnings("nls")
	private Bo2UtilsEnvironment(Properties properties) {
		this();
		this.properties = properties;
		setString(properties, "shortDateFormat");
		setString(properties, "longDateFormat");
		setCharset(properties, "textEncoding");
		setCharset(properties, "resourceFileEncoding");
	}
	
	/**
	 * Sets a String property.
	 *
	 * @param p the p
	 * @param property the property
	 */
	private void setString(Properties p, String property) {
		String s = p.getProperty(property);
		if (s!=null) {
			ReflectionUtils.set(property, s, this);
		}
	}
	
	/**
	 * Sets a String property.
	 *
	 * @param p the p
	 * @param property the property
	 */
	private void setCharset(Properties p, String property) {
		String s = p.getProperty(property);
		if (s!=null) {
			Charset charset = Charset.forName(s);
			ReflectionUtils.set(property, charset, this);
		}
	}
	

	/**
	 * Gets the dfShortPattern.
	 *
	 * @return Returns the dfShortPattern
	 */
	public String getShortDateFormatPattern() {
		return shortDateFormat;
	}

	/**
	 * Gets the dfLongPattern.
	 *
	 * @return Returns the dfLongPattern
	 */
	public String getLongDateFormatPattern() {
		return longDateFormat;
	}

	/**
	 * Gets the textCharset.
	 *
	 * @return Returns the textCharset
	 */
	public Charset getDefaultTextCharset() {
		return textEncoding;
	}
	
	/**
	 * Gets the resourceFileCharset.
	 *
	 * @return Returns the resourceFileCharset
	 */
	public Charset getDefaultResourceFileCharset() {
		return resourceFileEncoding;
	}


	/**
	 * Gets the properties.
	 *
	 * @return Returns the properties
	 */
	public Properties getProperties() {
		return properties;
	}

	

}
