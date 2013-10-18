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
 * Max value length validator.
 * 
 * Checks that a value is not less than a maximum.
 * @param <T> Type of value.
 */
public class MinValueValidator<T extends Comparable<T>>
extends AbstractValidator
implements Validator<T> {
	
	/**
	 * minimum value.
	 */
	private T minimum;
	
	/**
	 * Creates a new StringMinLengthValidator object. 
	 *
	 * @param minimum
	 */
	public MinValueValidator(T minimum) {
		super();
		this.minimum = minimum;
	}

	/**
	 * Gets the minimum allowed value.
	 *
	 * @return Returns the minimum allowed value
	 */
	public T getMinimum() {
		return minimum;
	}

	/**
	 * Sets the minimum allowed value.
	 *
	 * @param minimum New minimum value
	 */
	public void setMinimumLength(T minimum) {
		this.minimum = minimum;
	}

	public void validate(T value) throws ValidationException {
		if(value==null) {
			return;
		}
		if (minimum.compareTo(value)>0) {
			throw invalid();
		}
	}

}
