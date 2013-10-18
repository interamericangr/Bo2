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

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.meta.exceptions.ValidationException;

import java.util.Collection;

/**
 * Validates an object with multiple {@link Validator}s and throws a single
 * {@link ValidationException} with all relevant messages.
 * 
 * @param <T> 
 *        Type of object to validate.
 */
public class MultipleValidatorsValidator<T> implements Validator<T> {
	
	/**
	 * {@link Validator}s.
	 */
	private Collection<Validator<T>> validators;
	
	/**
	 * Label of the descriptor.
	 */
	private String label;
	
	/**
	 * Creates a new MultipleValidatorsValidator object. 
	 * @param validators 
	 * @param label 
	 */
	public MultipleValidatorsValidator(Collection<Validator<T>> validators, String label) {
		this.validators = validators;
		this.label = label;
	}

	public void validate(T value) throws ValidationException {
		String msg = StringConstants.EMPTY;
		if(validators == null) {
			return;
		}
		for(Validator<T> validator : validators) {
			try {
				validator.validate(value);
			}catch (ValidationException ve) {
				msg += ve.getMessage() + StringConstants.SPACE + StringConstants.SHARP + StringConstants.SPACE;
			}
		}
		if(!StringConstants.EMPTY.equals(msg)) {
			msg += StringConstants.QUOTE + label + StringConstants.QUOTE + StringConstants.COLON +
			       StringConstants.SPACE + StringUtils.truncateCharsFromEnd(msg, 3);
			throw new ValidationException(msg);
		}
	}

}
