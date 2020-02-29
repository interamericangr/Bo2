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
 * Exception that occurs during data storage operations.
 * 
 * Bo2 architecture is independent of the technology
 * used for implementation of data storage 
 *
 */
public class DataAccessException extends DataException {
	
	/**
	 * Universal version identifier for serialized class. 
	 */
	private static final long serialVersionUID = 2L;

    /**
     * Instantiates a new data access exception.
     */
    public DataAccessException() {
        super();        
    }
    
    /**
     * Instantiates a new data access exception.
     *
     * @param message the message
     */
    public DataAccessException(String message) {
        super(message);        
    }
    
    /**
     * Instantiates a new data access exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Instantiates a new data access exception.
     *
     * @param cause the cause
     */
    public DataAccessException(Throwable cause) {
        super(cause);
    }
}
