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
 * Validates that a number is not negative.
 */
public class NotNegativeValidator
extends AbstractValidator
implements Validator<Number> {
	
	/**
	 * Singleton.
	 */
	public static final NotNegativeValidator INSTANCE = new NotNegativeValidator();
	
	/**
	 * Access Singleton instance.
	 * 
	 * @param <T>
	 *        Number type.
	 *        
	 * @return Singleton instance of {@link NotNegativeValidator}.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Number> Validator<T> getInstance() {
		return (Validator<T>) INSTANCE;
	}
	
	/**
	 * Hidden, access this only from the Singleton instance.
	 */
	private NotNegativeValidator() {
		super();
	}

	public void validate(Number value) throws ValidationException {
		if (value!=null) {
			if (value.doubleValue()<0) {
				throw invalid();
			}
		}
	}

}
