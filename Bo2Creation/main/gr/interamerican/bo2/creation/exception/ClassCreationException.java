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
package gr.interamerican.bo2.creation.exception;

/**
 * Exception thrown during a class creation.
 */
public class ClassCreationException extends Exception {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new ClassCreationException object. 
	 *
	 */
	public ClassCreationException() {
		/* empty */
	}

	/**
	 * Creates a new ClassCreationException object. 
	 *
	 * @param message
	 */
	public ClassCreationException(String message) {
		super(message);
	}

	/**
	 * Creates a new ClassCreationException object. 
	 *
	 * @param cause
	 */
	public ClassCreationException(Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a new ClassCreationException object. 
	 *
	 * @param message
	 * @param cause
	 */
	public ClassCreationException(String message, Throwable cause) {
		super(message, cause);
	}

}
