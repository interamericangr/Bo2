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
package gr.interamerican.bo2.utils.doc;

/**
 * Exception thrown from a DocumentEngine.
 */
public class DocumentEngineException extends Exception {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a new DocumentEngineException object. 
	 *
	 */
	public DocumentEngineException() {
		super();
	}

	/**
	 * Creates a new DocumentEngineException object. 
	 *
	 * @param message
	 * @param cause
	 */
	public DocumentEngineException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a new DocumentEngineException object. 
	 *
	 * @param message
	 */
	public DocumentEngineException(String message) {
		super(message);
	}

	/**
	 * Creates a new DocumentEngineException object. 
	 *
	 * @param cause
	 */
	public DocumentEngineException(Throwable cause) {
		super(cause);
	}

}
