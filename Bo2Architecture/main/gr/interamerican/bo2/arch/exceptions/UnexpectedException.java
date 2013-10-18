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
package gr.interamerican.bo2.arch.exceptions;

/**
 * This Exception is thrown when an unexpected Throwable
 * is thrown during an operation.
 * 
 *
 */
public class UnexpectedException extends Exception {
	
	/**
	 * Universal version identifier for serialized class. 
	 */
	public static final long serialVersionUID = 2L;


	/**
	 * Creates a new UnexpectedException.
	 * 
	 * The Exception is thrown because another Throwable
	 * occured. The cause must be passed as a parameter 
	 * to this constructor.
	 * 
	 * @param cause Cause of the exception.
	 */
	public UnexpectedException(Throwable cause) {
		super(cause);		
	}

}
