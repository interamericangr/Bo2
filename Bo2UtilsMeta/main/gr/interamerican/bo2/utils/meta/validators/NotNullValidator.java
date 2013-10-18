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
 * Validates that an object is not null.
 */
public class NotNullValidator
extends AbstractValidator
implements Validator<Object> {
	
	/**
	 * Singleton.
	 */
	public static final NotNullValidator INSTANCE = new NotNullValidator();
	
	/**
	 * Access Singleton instance.
	 * 
	 * @param <T>
	 *        Object type.
	 *        
	 * @return Singleton instance of {@link NotNullValidator}.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Validator<T> getInstance() {
		return (Validator<T>) INSTANCE;
	}
	
	/**
	 * Hidden, access this only from the Singleton instance.
	 */
	private NotNullValidator() { /* hidden, empty */ }
	
	public void validate(Object value) throws ValidationException {
		if (value==null) {
			throw invalid();
		}
	}

}
