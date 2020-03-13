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
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;

import gr.interamerican.wicket.behavior.ValidationStyleBehavior;

/**
 * Double TextField.
 */
public class DoubleTextField extends TextField<Double> {
	/**
	 * serial.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The Converter used in this.
	 */
	private FixedDigitsDoubleConverter converter;

	/**
	 * Creates a new TextFieldExtension object.
	 *
	 * @param id
	 *            Wicket Id
	 * @param dec
	 *            Number of decimal digits to be displayed
	 */
	public DoubleTextField(String id, Integer dec) {
		super(id);
		commonSetup(dec);
	}

	/**
	 * Creates a new TextFieldExtension object.
	 *
	 * @param id
	 *            Wicket Id
	 * @param model
	 *            Model Object
	 * @param dec
	 *            Number of decimal digits to be displayed
	 */
	public DoubleTextField(String id, IModel<Double> model, Integer dec) {
		super(id, model, Double.class);
		commonSetup(dec);
	}

	/**
	 * Does the setup for this {@link TextField}.
	 * 
	 * @param dec
	 *            Number of decimal digits to be displayed
	 */
	void commonSetup(Integer dec) {
		this.converter = new FixedDigitsDoubleConverter(dec);
		setOutputMarkupPlaceholderTag(true);
		add(new NumberFormatBehaviour());
		add(ValidationStyleBehavior.INSTANCE);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <C> IConverter<C> getConverter(final Class<C> type) {
		return (IConverter<C>) converter;
	}
}