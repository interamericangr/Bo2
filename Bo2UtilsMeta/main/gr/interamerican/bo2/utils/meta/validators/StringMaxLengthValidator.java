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
 * Max string length validator.
 * 
 * Checks that the length of a string does not exceed a max length.
 */
public class StringMaxLengthValidator
extends AbstractValidator
implements Validator<String> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * minimum length.
	 */
	private int maxLength=0;
	
	/**
	 * Creates a new StringMinLengthValidator object. 
	 *
	 * @param maxLength
	 */
	public StringMaxLengthValidator(int maxLength) {
		super();
		this.maxLength = maxLength;
	}

	/**
	 * Creates a new NotZeroValidator object.
	 */
	public StringMaxLengthValidator() {
		this(0);
	}

	/**
	 * Gets the maxLength.
	 *
	 * @return Returns the maxLength
	 */
	public int getMaxLength() {
		return maxLength;
	}


	/**
	 * Assigns a new value to the maxLength.
	 *
	 * @param maxLength the maxLength to set
	 */
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}


	public void validate(String value) throws ValidationException {
		if(value==null) {
			return;
		}
		if (value.length()>maxLength) {
			throw invalid();
		}
		
	}






}
