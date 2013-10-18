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
package gr.interamerican.bo2.utils.meta.validators;

import gr.interamerican.bo2.utils.meta.exceptions.ValidationException;

/**
 * Minimum string length validator.
 * 
 * Checks that the length of a string is not less than a minimum length.
 */
public class StringMinLengthValidator
extends AbstractValidator
implements Validator<String> {
	
	/**
	 * minimum length.
	 */
	private int minLength=0;
	
	/**
	 * Creates a new StringMinLengthValidator object. 
	 *
	 * @param minLength
	 */
	public StringMinLengthValidator(int minLength) {
		super();
		this.minLength = minLength;
	}

	/**
	 * Creates a new NotZeroValidator object.
	 */
	public StringMinLengthValidator() {
		this(0);
	}

	/**
	 * Gets the minLength.
	 *
	 * @return Returns the minLength
	 */
	public int getMinLength() {
		return minLength;
	}

	/**
	 * Assigns a new value to the minLength.
	 *
	 * @param minLength the minLength to set
	 */
	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public void validate(String value) throws ValidationException {
		if(value==null) {
			return;
		}
		if (value.length()<minLength) {
			throw invalid();
		}
	}

}
