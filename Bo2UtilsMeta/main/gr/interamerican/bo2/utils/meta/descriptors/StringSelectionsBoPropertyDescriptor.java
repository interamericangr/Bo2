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

import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.TokenUtils;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.exceptions.ValidationException;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.validators.Validator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * {@link BoPropertyDescriptor} for a property that is a list of String values 
 * that are selected from a list of possible String values.
 */
public class StringSelectionsBoPropertyDescriptor 
extends AbstractBoPropertyDescriptor<List<String>> {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * set of values.
	 */
	private List<String> availableValues;
	
	/**
	 * Creates a new SelectionBoPropertyDescriptor object.
	 *  
	 * @param availableValues 
	 */
	public StringSelectionsBoPropertyDescriptor(List<String> availableValues) {
		super(null);
		this.availableValues = availableValues;
		validators.put(StringSelectionsBoPropertyDescriptor.class, new StringSelectionsValidator(availableValues));
	}

	/**
	 * Gets the set of values.
	 * 
	 * @return Returns the set of values.
	 */
	public List<String> getAvailableValues() {
		return availableValues;
	}

	@Override
	public List<String> parse(String formatted) throws ParseException {
		if(StringUtils.isNullOrBlank(formatted)) {
			return Collections.emptyList();
		}
		return Arrays.asList(TokenUtils.splitTrim(formatted, StringConstants.PIPE));
	}

	@Override
	protected Formatter<List<String>> getFormatter() {
		return new StringListFormatter();
	}
	
	/**
	 * Validates that all selected values are valid.
	 */
	static class StringSelectionsValidator implements Validator<List<String>> {
		
		/**
		 * serialVersionUID
		 */
		private static final long serialVersionUID = 1L;
		
		/**
		 * Valid values.
		 */
		private List<String> validValues;

		/**
		 * Creates a new SelectionsValidator object. 
		 *
		 * @param validValues
		 */
		public StringSelectionsValidator(List<String> validValues) {
			this.validValues = validValues;
		}

		@Override
		@SuppressWarnings("nls")
		public void validate(List<String> selections) throws ValidationException {
			if(CollectionUtils.isNullOrEmpty(selections)) {
				return;
			}
			if(!validValues.containsAll(selections)) {
				throw new ValidationException("Some of the " + selections + " are not contained in " + validValues);
			}
		}
		
	}
	
	/**
	 * Formats a list of Strings as a comma separated string.
	 */
	static class StringListFormatter implements Formatter<List<String>> {

		/**
		 * serialVersionUID
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public String format(List<String> values) {
			if(CollectionUtils.isNullOrEmpty(values)) {
				return StringConstants.EMPTY;
			}
			
			StringBuilder sb = new StringBuilder();
			for (String t : values) {
				sb.append(t);
				sb.append(StringConstants.PIPE);
			}
			return sb.substring(0, sb.length() - 1);
		}
	}
}