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
package gr.interamerican.wicket.bo2.validation;

import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.meta.MetaSession;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.exceptions.ValidationException;
import gr.interamerican.bo2.utils.meta.parsers.Parser;
import gr.interamerican.bo2.utils.meta.validators.Validator;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

/**
 * A wicket validator implementation that wraps the validations
 * performed by {@link BoPropertyDescriptor}s. This is to be used in
 * self-drawn components generated using BoPropertyDescriptors.
 * 
 * @param <T> 
 *        Type of model object of component associated with this validator.
 */
public class BoPropertyDescriptorValidatorWrapper<T> implements IValidator<T> {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Parser
	 */
	Parser<T> parser;
	
	/**
	 * Validator
	 */
	Validator<T> validator;
	
	/**
	 * Creates a new BoPropertyDescriptorValidatorWrapper object. 
	 * @param descriptor 
	 */
	public BoPropertyDescriptorValidatorWrapper(BoPropertyDescriptor<T> descriptor) {
		this.parser = descriptor.getParser();
		this.validator = descriptor.getValidator();
	}

	public void validate(IValidatable<T> validatable) {
		MetaSession.setLocale(Bo2Session.getLocale());
		T value = validatable.getValue();
		try {
			/*
			 * Hack for tests. T value comes as a String in tests.
			 * In normal form processing it is an instance of T.
			 */
			if(value instanceof String) {
				value = parser.parse((String) value);
				validator.validate(value);
			} else {
				/*
				 * This runs in a normal environment. If the value is
				 * indeed a String, it is not much of a problem to
				 * run parseAndValidate() instead, the String parser
				 * should just return the String.
				 */
				validator.validate(value);
			}
		} catch (ValidationException ve) {
			if(!StringUtils.isNullOrBlank(ve.getMessage())) {
				validatable.error(new ValidationError().setMessage(ve.getMessage()));
			}
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

}
