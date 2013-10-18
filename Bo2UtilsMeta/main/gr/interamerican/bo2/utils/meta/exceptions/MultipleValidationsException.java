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
package gr.interamerican.bo2.utils.meta.exceptions;

import gr.interamerican.bo2.utils.StringConstants;

import java.util.Map;

/**
 * This exception boxes more than one validation failures.
 */
public class MultipleValidationsException extends Exception {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * validation failure messages.
	 */
	private Map<Object, String> validationFailures;

	/**
	 * Creates a new MultipleValidationsException object. 
	 * 
	 * @param message Exception message.
	 * @param validationFailures Map that associates objects with validation failure messages.
	 */
	public MultipleValidationsException(String message, Map<Object, String> validationFailures) {
		super(message);
		this.validationFailures = validationFailures;
	}
	
	/**
	 * Creates a new MultipleValidationsException object.
	 * 
	 * @param validationFailures Map that associates objects with validation failure messages.
	 */
	public MultipleValidationsException(Map<Object, String> validationFailures) {
		super();
		this.validationFailures = validationFailures;
	}

	/**
	 * Gets the validationFailures.
	 *
	 * @return Returns the validationFailures
	 */
	public Map<Object, String> getValidationFailures() {
		return validationFailures;
	}
	
	/**
	 * Returns the list with all validation messages.
	 * 
	 * @return All validation messages.
	 */
	public String getAllMessages() {
		String allMessages = StringConstants.EMPTY;
		for(Map.Entry<Object, String> entry : validationFailures.entrySet()) {
			allMessages += entry.getValue()+StringConstants.NEWLINE;
		}
		return allMessages;
	}

}
