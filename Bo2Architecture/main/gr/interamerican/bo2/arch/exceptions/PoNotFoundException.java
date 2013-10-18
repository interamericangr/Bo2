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
 * Exception thrown by data storage layer operations that failed
 * to find a persistent object.
 *
 */
public class PoNotFoundException extends DataException {
	
	/**
	 * Universal version identifier for serialized class.
	 */
	public static final long serialVersionUID = 2L;

    /**
     * Creates a new PoNotFoundException.
     */
    public PoNotFoundException() {
        super();        
    }
    /**
     * Creates a new PoNotFoundException with a message.
     * 
     * @param message
     */
    public PoNotFoundException(String message) {
        super(message);        
    }
    

    /**
     * Creates a new LogicException that was caused by
     * another Throwable with a message.
     * 
     * @param message Message for this LogicException.
     * @param cause Cause of this LogicException
     */
    public PoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Creates a new PoNotFoundException that was caused by
     * another Throwable.
     * 
     * @param cause Cause of this LogicException.
     */
    public PoNotFoundException(Throwable cause) {
        super(cause);
    }
}
