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
 * Exception thrown by a Rule.
 * 
 *
 */
public class RuleException extends LogicException {
	
	/**
	 * Universal version identifier for serialized class.
	 */
	public static final long serialVersionUID = 2L;

    /**
     * Creates a new RuleException.
     *
     */
    public RuleException() {    
        super();
    }

    /**
     * Creates a new RuleException with a message.
     * 
     * @param message 
     */
    public RuleException(String message) {
        super(message);    
    }
    
    
    /**
     * Creates a new RuleException that was caused by
     * another Throwable.
     * 
     * @param cause Cause of this RuleException.
     */
    public RuleException(Throwable cause) {
        super(cause);        
    }

    /**
     * Creates a new RuleException that was caused by
     * another Throwable with a message.
     * 
     * @param message Message for this RuleException.
     * @param cause Cause of this RuleException
     */
    public RuleException(String message, Throwable cause) {
        super(message, cause);        
    }


}
