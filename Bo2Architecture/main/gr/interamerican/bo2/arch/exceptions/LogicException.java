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
 * LogicException occurs due to the violation of a 
 * logical condition.
 * 
 * Business rules violations can cause a LogicException
 * to be thrown.
 * 
 *
 */
public class LogicException extends Exception {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

    /**
     * Creates a new LogicException.
     *
     */
    public LogicException() {    
        super();
    }

    /**
     * Creates a new LogicException with a message.
     * 
     * @param message
     */
    public LogicException(String message) {        
        super(message);        
    }

    /**
     * Creates a new LogicException that was caused by
     * another Throwable.
     * 
     * @param cause Cause of this LogicException.
     */
    public LogicException(Throwable cause) {
        super(cause);        
    }

    /**
     * Creates a new LogicException that was caused by
     * another Throwable with a message.
     * 
     * @param message Message for this LogicException.
     * @param cause Cause of this LogicException
     */
    public LogicException(String message, Throwable cause) {
        super(message, cause);        
    }

}
