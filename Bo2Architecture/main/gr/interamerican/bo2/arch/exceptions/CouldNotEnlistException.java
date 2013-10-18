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
 * fails to enlist a resource.
 */
public class CouldNotEnlistException extends TransactionManagerException {

	/**
	 * Universal version identifier for serialized class. 
	 */
	public static final long serialVersionUID = 2L;
    
    
    /**
     * Creates a new CouldNotCommitException with a message.
     * 
     * @param message 
     */
    public CouldNotEnlistException(String message) {
        super(message);        
    }
    /**
     * Creates a new CouldNotCommitException with a message.
     * 
     * @param message
     * @param cause
     */
    public CouldNotEnlistException(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     * Creates a new CouldNotCommitException with a message.
     * 
     * @param cause
     */
    public CouldNotEnlistException(Throwable cause) {
        super(cause);
    }
}
