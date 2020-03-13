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
package gr.interamerican.wicket.components;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.validation.validator.RangeValidator;

import gr.interamerican.wicket.behavior.ValidationStyleBehavior;

/**
 * A TextField for Double percentage values.<br>
 * It has a RangeValidator that guarantees that the percentage is not grater
 * than a hundred and is equal or greater than zero.
 */
public class PercentageTextField extends TextField<Double> {

	/**
	 * serialize.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The converter in use
	 */
	private final PercentageDoubleConverter converter;

	/**
	 * Public Constructor
	 *
	 * @param id
	 *            the id
	 * @param decimals
	 *            the decimals
	 */
	public PercentageTextField(String id, Integer decimals) {
		super(id);
		this.converter = new PercentageDoubleConverter(decimals);
		add(new RangeValidator<Double>(0.0, 100.0));
		add(ValidationStyleBehavior.INSTANCE);
		setOutputMarkupId(true);
		add(new NumberFormatBehaviour());
	}

	@Override
	@SuppressWarnings("unchecked")
	public <C> IConverter<C> getConverter(final Class<C> type) {
		return (IConverter<C>) converter;
	}
}