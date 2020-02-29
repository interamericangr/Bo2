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
package gr.interamerican.bo2.utils.exc;

import java.io.Serializable;

/**
 * Abstraction for an object that handles various types of exceptions
 * and re-throws a specified type of exception.
 * 
 * @param <E>
 *        Type of exception that is re-thrown by this ExceptionTranslator. 
 * 
 */
public interface ExceptionTranslator<E extends Exception> 
extends Serializable {
	
	/**
	 * Handles the specified thrown exception.
	 *
	 * @param t        Exception handler.
	 *        
	 * @throws E the e
	 */
	void rethrow(Throwable t) throws E;

}
