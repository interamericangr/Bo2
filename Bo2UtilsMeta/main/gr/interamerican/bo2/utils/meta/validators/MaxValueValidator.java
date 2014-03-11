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
 * Checks that a value is not greater than a maximum.
 * @param <T> Type of value.
 */
public class MaxValueValidator<T extends Comparable<T>>
extends AbstractValidator
implements Validator<T> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * maximum value.
	 */
	private T maximum;
	
	/**
	 * Creates a new StringMinLengthValidator object. 
	 *
	 * @param maximum
	 */
	public MaxValueValidator(T maximum) {
		super();
		this.maximum = maximum;
	}

	/**
	 * Gets the maximum allowed value.
	 *
	 * @return Returns the maximum allowed value
	 */
	public T getMaximum() {
		return maximum;
	}

	/**
	 * Sets the maximum allowed value.
	 *
	 * @param maximum New maximum value
	 */
	public void setMaxLength(T maximum) {
		this.maximum = maximum;
	}

	public void validate(T value) throws ValidationException {
		if(value==null) {
			return;
		}
		if (maximum.compareTo(value)<0) {
			throw invalid();
		}
	}

}
