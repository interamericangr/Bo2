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
 * DataException that occurs during data access operations.
 * 
 *  
 *
 */
public class DataException extends Exception {

	/**
	 * Universal version identifier for serialized class. 
	 */
	private static final long serialVersionUID = 2L;
    

    /**
     * Creates a new DataException.
     */
    public DataException() {
        super();        
    }
    
    /**
     * Creates a new DataException with a message.
     * 
     * @param message 
     */
    public DataException(String message) {
        super(message);        
    }
    /**
     * @param message
     * @param cause
     */
    public DataException(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     * @param cause
     */
    public DataException(Throwable cause) {
        super(cause);
    }
}
