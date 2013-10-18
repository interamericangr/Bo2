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
 * Validates a Number based on a maximum length for its integer
 * part. The minimum length is implicitly considered to be zero.
 * 
 * @param <T>
 *        Number to validate against. 
 */
public class NumberIntegerLengthValidator<T extends Number> 
extends AbstractValidator
implements Validator<T> {
	
	/**
	 * Maximum length of integer part.
	 */
	private int maxIntegerLength;
	
	/**
	 * Creates a new NumberIntegerLengthValidator object. 
	 * @param maxIntegerLength 
	 */
	public NumberIntegerLengthValidator(int maxIntegerLength) {
		this.maxIntegerLength = maxIntegerLength;
	}

	public void validate(T value) throws ValidationException {
		if(value==null) {
			return;
		}
		Long longValue = Math.abs(value.longValue());
		String integerPart = String.valueOf(longValue);
		if(integerPart.length() <= maxIntegerLength) {
			return;
		}
		throw invalid();
	}

	/**
	 * Assigns a new value to the maxIntegerLength.
	 *
	 * @param maxIntegerLength the maxIntegerLength to set
	 */
	public void setMaxIntegerLength(int maxIntegerLength) {
		this.maxIntegerLength = maxIntegerLength;
	}

	/**
	 * Gets the maxIntegerLength.
	 *
	 * @return Returns the maxIntegerLength
	 */
	public int getMaxIntegerLength() {
		return maxIntegerLength;
	}

}
