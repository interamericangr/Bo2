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
	public static final long serialVersionUID = 2L;

   
    

    /**
     * 
     */
    public DataAccessException() {
        super();        
    }
    /**
     * @param message
     */
    public DataAccessException(String message) {
        super(message);        
    }
    /**
     * @param message
     * @param cause
     */
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     * @param cause
     */
    public DataAccessException(Throwable cause) {
        super(cause);
    }
}
