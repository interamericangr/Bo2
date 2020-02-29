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
import gr.interamerican.bo2.arch.Worker;

/**
 * Exception that occurs when a Bo2 {@link Worker} implementation detects
 * that the current unit of work is stale. This may happen, for instance
 * if {@link TransactionManager#hasBeenMarkedRollbackOnly()} returns true.
 */
public class StaleTransactionException extends DataException {
	
	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
    /**
     * Creates a new StaleTransactionException object. 
     *
     * @param message the message
     */
    public StaleTransactionException(String message) {
        super(message);
    }
    
    /**
     * Creates a new StaleTransactionException object. 
     *
     * @param message the message
     * @param cause the cause
     */
    public StaleTransactionException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Creates a new StaleTransactionException object. 
     *
     * @param cause the cause
     */
    public StaleTransactionException(Throwable cause) {
        super(cause);
    }
    
}
