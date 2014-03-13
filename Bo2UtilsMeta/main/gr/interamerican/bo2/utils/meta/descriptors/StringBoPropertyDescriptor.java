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
package gr.interamerican.bo2.utils.meta.descriptors;

import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.formatters.FormatterResolver;
import gr.interamerican.bo2.utils.meta.parsers.Parser;
import gr.interamerican.bo2.utils.meta.parsers.ParserResolver;
import gr.interamerican.bo2.utils.meta.validators.ExpressionValidator;
import gr.interamerican.bo2.utils.meta.validators.StringMaxLengthValidator;
import gr.interamerican.bo2.utils.meta.validators.StringMinLengthValidator;

/**
 * {@link BoPropertyDescriptor} implementation for String properties.
 */
public class StringBoPropertyDescriptor 
extends AbstractBoPropertyDescriptor<String> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * min length.
	 */
	int minLength;
	
	/**
	 * Creates a new StringBoPropertyDescriptor object. 
	 *
	 * @param parser
	 */
	public StringBoPropertyDescriptor(Parser<String> parser) {
		super(parser);
		setMinLength(0);
		setMaxLength(10);	
	}

	/**
	 * Expression that validates the String representation of the property value.
	 */
	String expression;

	/**
	 * Creates a new StringBoPropertyDescriptor object.
	 */
	public StringBoPropertyDescriptor() {
		super(ParserResolver.getParser(String.class));
		setMinLength(0);
		setMaxLength(10);	
		validators.put(StringMinLengthValidator.class, new StringMinLengthValidator(minLength));
		validators.put(StringMaxLengthValidator.class, new StringMaxLengthValidator(maxLength));
		if(!StringUtils.isNullOrBlank(expression)) {
			validators.put(ExpressionValidator.class, new ExpressionValidator(expression));
		}
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
		StringMinLengthValidator validator = getRegisteredValidatorWithType(StringMinLengthValidator.class);
		if(validator!=null) {
			validator.setMinLength(minLength);
		} else {
			validators.put(StringMinLengthValidator.class, new StringMinLengthValidator(minLength));
		}
	}
	

	@Override
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
		StringMaxLengthValidator validator = getRegisteredValidatorWithType(StringMaxLengthValidator.class);
		if(validator!=null) {
			validator.setMaxLength(maxLength);
		} else {
			validators.put(StringMaxLengthValidator.class, new StringMaxLengthValidator(maxLength));
		}
	}
	
	/**
	 * @param expression
	 *        Sets an expression to validate the property value with.
	 */
	public void setExpression(String expression) {
		this.expression = expression;
		if(StringUtils.isNullOrBlank(expression)) {
			validators.remove(ExpressionValidator.class);
			return;
		}
		ExpressionValidator validator = getRegisteredValidatorWithType(ExpressionValidator.class);
		if(validator!=null) {
			validator.setExpression(expression);
		} else {
			validators.put(ExpressionValidator.class, new ExpressionValidator(expression));
		}
	}
	
	/**
	 * @return Returns the expression.
	 */
	public String getExpression() {
		return expression;
	}

	@Override
	protected Formatter<String> getFormatter() {
		if(isNullAllowed()) {
			return FormatterResolver.getNrFormatter(String.class);
		}
		return FormatterResolver.getFormatter(String.class);
	}
	
	
}
