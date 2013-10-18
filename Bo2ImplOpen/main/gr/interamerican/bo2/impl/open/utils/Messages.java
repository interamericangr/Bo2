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

import gr.interamerican.bo2.utils.beans.MessageGetter;

/**
 * Class that reads externalized strings from a 
 * resource bundle.
 * 
 *
 */
public class Messages {
	
	/**
	 * message key for invalid type mapping.
	 */
	public static final String CLASS_NOT_FOUND = 
		"Factory.CLASS_NOT_FOUND"; //$NON-NLS-1$

	/**
	 * message key for invalid type mapping.
	 */
	public static final String INVALID_TYPE_MAPPING = 
		"Factory.INVALID_TYPE_MAPPING"; //$NON-NLS-1$
	
	/**
	 * Message key for message stating that a Provider
	 * could not be found.
	 */
	public static final String NO_PROVIDER = 
		"Messages.NO_PROVIDER"; //$NON-NLS-1$
	
	/**
	 * Message key for message stating that a required
	 * resource is null.
	 */
	public static final String NULL_RESOURCE = 
		"Messages.RESOURCE_IS_NULL"; //$NON-NLS-1$	
	
	/**
	 * Message key for message stating that a required
	 * resource could not be found.
	 */
	public static final String RESOURCE_NOT_FOUND = 
		"Messages.RESOURCE_NOT_FOUND"; //$NON-NLS-1$	
	
	/**
	 * Message key for message stating that a required
	 * resource could not be loaded.
	 */
	public static final String RESOURCE_NOT_LOADED = 
		"Messages.RESOURCE_NOT_LOADED"; //$NON-NLS-1$
	
	/**
	 * Message key for invalid annotation message.
	 */
	public static final String INVALID_ANNOTATION = 
		"Messages.INVALID_ANNOTATION"; //$NON-NLS-1$
	
	/**
	 * Message key for property not found.
	 */
	public static final String PROPERTY_NOT_FOUND = 
		"Messages.PROPERTY_NOT_FOUND"; //$NON-NLS-1$	

	/**
	 * Message key for stream not found.
	 */
	public static final String STREAM_NOT_FOUND = 
		"Messages.STREAM_NOT_FOUND"; //$NON-NLS-1$	
	
	/**
	 * message key for no transaction manager 
	 */
	public static final String NO_TRANSACTION_MANAGER =
		"Messages.NO_TRANSACTIONMANAGER"; //$NON-NLS-1$
	
		
	/**
	 * bundle name
	 */
	private static final String BUNDLE_NAME = 
		"gr.interamerican.bo2.impl.open.utils.messages"; //$NON-NLS-1$
	

	/**
	 * constant for space
	 */
	private static final String SPACE=" "; //$NON-NLS-1$	
	
	/**
	 * constant for null
	 */
	private static final String NULL="null"; //$NON-NLS-1$

	/**
	 * resource bundle
	 */
	private static MessageGetter messages = new MessageGetter(BUNDLE_NAME);

	/**
	 * Hidden constructor.
	 * 
	 * This is a utility class having only static methods.
	 * There is no need to create any instance of this class.
	 */
	private Messages() {
		/* empty */
	}

	/**
	 * Gets the literal associated with a key.
	 * 
	 * @param key
	 * @return returns the literal associated with the specified key.
	 */
	public static String getString(String key) {
		return messages.getString(key);
	}
	
	/**
	 * Combines a message retrieved by the resource bundle
	 * with the class name of an object.
	 * 
	 * @param key 
	 *        Message key used to retrieve the message from the resource bundle.
	 * @param o
	 *        Object who's class name will be added to the message.
	 *  
	 * @return Returns a message that combines the message associated with 
	 *         the key with the class of the object.
	 */		
	public static String getString(String key, Object o) {		
		if (o==null) {
			return msg(key,NULL);
		} else if (o instanceof String) {			
			String str=(String) o;
			return msg(key,str);			
		} else if (o instanceof Class) {
			Class<?> c=(Class<?>) o;
			return msg(key,c.getName());			
		} 
		return msg(key,o.getClass().getName());
	}
	
	/**
	 * Combines a message retrieved by the resource bundle
	 * with an other string.
	 * 
	 * @param key 
	 *        Message key used to retrieve the message from the resource bundle.
	 * @param string
	 *        String that will be added to the message.
	 *  
	 * @return Returns the concatenation of a message with the string.
	 */
	private static String msg(String key, String string) {
		return Messages.getString(key) + SPACE + string;
	}
	
	
}
