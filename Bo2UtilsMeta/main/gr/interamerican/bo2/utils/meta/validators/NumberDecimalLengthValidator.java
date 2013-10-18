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

import java.math.BigDecimal;

/**
 * Validates a Number based on a maximum length for its decimal
 * part. The minimum length is implicitly considered to be zero.
 * 
 * @param <T>
 *        Number to validate against. 
 */
public class NumberDecimalLengthValidator<T extends Number> 
extends AbstractValidator
implements Validator<T> {
	
	/**
	 * Maximum length of decimal part.
	 */
	private int maxDecimalLength;
	
	/**
	 * Creates a new NumberDecimalLengthValidator object. 
	 * @param maxDecimalLength 
	 */
	public NumberDecimalLengthValidator(int maxDecimalLength) {
		this.maxDecimalLength = maxDecimalLength;
	}

	public void validate(T value) throws ValidationException {
		if(value==null) {
			return;
		}
		if(value instanceof Integer || value instanceof Long) {
			return;
		}
		if(value instanceof BigDecimal) {
			BigDecimal bigDecimal = (BigDecimal) value;
			if(bigDecimal.scale() > maxDecimalLength) {
				throw invalid();
			}
		}
		else {
			/*
			 * FIXME
			 * TODO this will not work always, due to precision limitations.
			 *      e.g. try 1.1f.
			 */
			BigDecimal bigDecimal = BigDecimal.valueOf(value.doubleValue());
			if(bigDecimal.scale() > maxDecimalLength) {
				throw invalid();
			}
		}
	}

	/**
	 * Assigns a new value to the maxDecimalLength.
	 *
	 * @param maxDecimalLength the maxDecimalLength to set
	 */
	public void setMaxDecimalLength(int maxDecimalLength) {
		this.maxDecimalLength = maxDecimalLength;
	}

	/**
	 * Gets the maxDecimalLength.
	 *
	 * @return Returns the maxDecimalLength
	 */
	public int getMaxDecimalLength() {
		return maxDecimalLength;
	}

}
