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
/**
 * 
 */
package gr.interamerican.bo2.arch.exceptions;

import gr.interamerican.bo2.arch.Worker;

/**
 * An InitializationException is thrown when a {@link Worker}
 * fails to be initialized.
 * 
 * This type of exception is thrown by the <code>init(provider)</code>
 * method of Worker objects. Failure of the provider to provide the
 * resources required by the Worker object should cause an exception
 * of this type to be thrown.
 */
public class InitializationException extends Exception {
	 
	/**
	 * Universal version identifier for serialized class.
	 */
	public static final long serialVersionUID = 2L;

	/**
	 * Creates a new InitializationException.
	 */
	public InitializationException() {
		super();		
	}

	/**
	 * Creates a new InitializationException that is caused by
	 * another Throwable.
	 * 
	 * An additional explanatory message is passed to the new Exception.
	 * 
	 * @param message 
	 *        Explanatory message.
	 * @param cause
	 *        Throwable that caused this InitializationException. 
	 */
	public InitializationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a new InitializationException with a message.
	 * 
	 * @param message 
	 *        Message explaining the reason of the Exception.
	 */
	public InitializationException(String message) {
		super(message);
	}

	/**
	 * Creates a new InitializationException that is caused by
	 * another Throwable.
	 * 
	 * @param cause
	 *        Throwable that caused this InitializationException. 
	 */
	public InitializationException(Throwable cause) {
		super(cause);
	}

}
