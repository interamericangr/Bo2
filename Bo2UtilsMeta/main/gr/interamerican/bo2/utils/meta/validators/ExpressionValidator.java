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
 * Validates a String against a regular expression.
 */
public class ExpressionValidator 
extends AbstractValidator
implements Validator<String> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Regular expression.
	 */
	private String expression;
	
	/**
	 * Creates a new ExpressionValidator object. 
	 * @param expression 
	 */
	public ExpressionValidator(String expression) {
		this.expression = expression;
	}

	public void validate(String value) throws ValidationException {
		if(value==null) {
			return;
		}
		if(!value.matches(expression)) {
			throw invalid(value);
		}
	}
	
	/**
	 * Throw ValidationException.
	 * @param value
	 *        String value that did not validate.
	 * @return ValidationException 
	 */
	@SuppressWarnings("nls")
	private ValidationException invalid(String value) {
		ValidationException ve = super.invalid();
		String msg = ve.getMessage() + " (" + value + ", " + expression + ")";
		return new ValidationException(msg);
	}

	/**
	 * Assigns a new value to the expression.
	 *
	 * @param expression the expression to set
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}

}
