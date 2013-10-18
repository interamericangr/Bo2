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
package gr.interamerican.bo2.utils.sql.exceptions;

/**
 * Exception thrown during the parsing of a statement.
 */
public class SqlParseException extends Exception {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new ParseException object. 
	 *
	 */
	public SqlParseException() {
		super();
	}

	/**
	 * Creates a new ParseException object. 
	 *
	 * @param message
	 * @param cause
	 */
	public SqlParseException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a new ParseException object. 
	 *
	 * @param message
	 */
	public SqlParseException(String message) {
		super(message);
	}

	/**
	 * Creates a new ParseException object. 
	 *
	 * @param cause
	 */
	public SqlParseException(Throwable cause) {
		super(cause);
	}
	
	
	
	

}
