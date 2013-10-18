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


/**
 * Exception thrown when the provider can't be created by a RuntimeCommand.
 */
public class ProviderCreationException extends RuntimeException {
	 
	/**
	 * Universal version identifier for serialized class.
	 */
	public static final long serialVersionUID = 1L;

	/**
	 * Creates a new InitializationException.
	 * 
	 * @param e 
	 *        Cause of this exception.
	 */
	public ProviderCreationException(Throwable e) {
		super(e);		
	}

}
