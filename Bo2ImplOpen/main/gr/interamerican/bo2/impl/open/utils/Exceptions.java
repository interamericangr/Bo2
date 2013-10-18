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

import gr.interamerican.bo2.arch.exceptions.DataOperationNotSupportedException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.utils.StringConstants;


/**
 * Utility class that provides methods that create exceptions.
 * 
 * Some exceptions are thrown almost the same way
 * in various different cases across the project.
 * This class provides utility methods that throw these
 * exceptions.
 * 
 * 
 *
 */
public class Exceptions {
	
	/**
	 * Hidden constructor.
	 * 
	 * This is a utility class having only static methods.
	 * There is no need to create any instance of this class.
	 */
	private Exceptions() {
		/* empty hidden constructor */
	}
	
	/**
	 * Creates a new DataOperationNotSupportedException.
	 * 
	 * @param o The object that can't support the data operation.
	 * 
	 * @return returns a new DataOperationNotSupportedException.
	 */	
	public static DataOperationNotSupportedException 
	dataOperationNotSupported(Object o) {
		String msg=
			Messages.getString("Messages.OPERATION_NOT_SUPPORTED_BY", o); //$NON-NLS-1$
		return new DataOperationNotSupportedException(msg);		
	}
	
	/**
	 * Creates a new RuntimeException with a message.
	 * 
	 * @param key Key of message.
	 * 
	 * @return returns a new RuntimeException.
	 */	
	public static RuntimeException 
	runtime(String key) {
		String msg=Messages.getString(key); 
		return new RuntimeException(msg);		
	}
	
	/**
	 * Creates a new RuntimeException with a message.
	 * 
	 * @param key Key of message.
	 * @param o Object associated with the message.
	 *          
	 * @return returns a new RuntimeException.
	 */	
	public static RuntimeException 
	runtime(String key,Object o) {
		String msg=Messages.getString(key,o); 
		return new RuntimeException(msg);		
	}
	
	/**
	 * Creates a new RuntimeException with a message.
	 * 
	 * @param key Key of message.
	 * @param o Object associated with the message.
	 * @param extraInfo extra info
	 *          
	 * @return returns a new RuntimeException.
	 */	
	public static RuntimeException 
	runtime(String key,Object o, String extraInfo) {
		String msg=Messages.getString(key,o); 
		return new RuntimeException(msg + StringConstants.SPACE + extraInfo);		
	}
	
	/**
	 * Creates a new InitializationException with a message.
	 * 
	 * @param key Key of message.
	 * 
	 * @return returns a new InitializationException.
	 */
	public static InitializationException 
	initializationException(String key) {
		String msg=Messages.getString(key); 
		return new InitializationException(msg);		
	}
	
	/**
	 * Creates a new InitializationException with a message.
	 * 
	 * @param key Key of message.
	 * @param o Object associated with the message.
	 *          
	 * @return returns a new InitializationException.
	 */	
	public static InitializationException 
	initializationException(String key,Object o) {
		String msg=Messages.getString(key,o); 
		return new InitializationException(msg);		
	}
	
	/**
	 * Throws a {@link RuntimeException} if the argument is null.
	 * 
	 * @param o
	 *        Object to check against.
	 * @param msg 
	 *        Message for the exception.
	 */
	public static void runtimeExceptionOnNullObject(Object o, String msg) {
		if(o==null) {
			throw new RuntimeException(msg);
		}
	}

}
