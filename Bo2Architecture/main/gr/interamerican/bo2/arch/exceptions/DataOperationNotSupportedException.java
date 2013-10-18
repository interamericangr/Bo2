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
 * This exception should be thrown upon invocation of any
 * method of a data storage layer class that has not been
 * implemented.
 * 
 * It is possible to develop a class that implements an interface
 * but not implement all of its methods. In this case the 
 * methods that are not implemented should throw a 
 * DataOperationNotSupportedException.
 */
public class DataOperationNotSupportedException extends RuntimeException {

 
	/**
	 * Universal version identifier for serialized class.
	 */
	public static final long serialVersionUID = 2L;
    
    
    /**
     * default constructor.
     */
    public DataOperationNotSupportedException() {
        super();
    }
    /**
     * constructor with message
     * @param message
     */
    public DataOperationNotSupportedException(String message) {
        super(message);
    }
}
