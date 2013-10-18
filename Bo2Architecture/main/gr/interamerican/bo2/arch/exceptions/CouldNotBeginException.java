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

import gr.interamerican.bo2.arch.TransactionManager;

/**
 * Exception thrown by a {@link TransactionManager} when it
 * fails to begin a transaction.
 */
public class CouldNotBeginException extends TransactionManagerException {

	/**
	 * Universal version identifier for serialized class. 
	 */
	public static final long serialVersionUID = 2L;

    /**
     * Creates a new DataException with a message.
     * 
     * @param message 
     */
    public CouldNotBeginException(String message) {
        super(message);        
    }
    /**
     * @param message
     * @param cause
     */
    public CouldNotBeginException(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     * @param cause
     */
    public CouldNotBeginException(Throwable cause) {
        super(cause);
    }
}
