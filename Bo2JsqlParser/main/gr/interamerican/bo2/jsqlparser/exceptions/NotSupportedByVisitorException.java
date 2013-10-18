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
package gr.interamerican.bo2.jsqlparser.exceptions;

/**
 * Runtime exception to be thrown by Visitors and then caught
 * by the Parser methods in order to be re-thrown as ParseException.  
 * 
 */
public class NotSupportedByVisitorException 
extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new NotSupportedByVisitorException object. 
	 *
	 * @param message
	 */
	public NotSupportedByVisitorException(String message) {
		super(message);
	}

}
