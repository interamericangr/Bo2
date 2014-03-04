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

import gr.interamerican.wicket.behavior.ValidationStyleBehavior;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.convert.converter.DoubleConverter;

/**
 * Double TextField.
 */
public class DoubleTextField extends TextField<Double> {
	/**
	 * serial.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private final Integer dec;
	/**
	 * Creates a new TextFieldExtension object. 
	 *
	 * @param id
	 * @param dec
	 */
	public DoubleTextField(String id, Integer dec) {
		super(id);
		this.setOutputMarkupPlaceholderTag(true);
		this.dec = dec;
		this.add(new NumberFormatBehaviour(dec));
		this.add(ValidationStyleBehavior.INSTANCE);
	}	
	
	/**
	 * Creates a new TextFieldExtension object. 
	 *
	 * @param id
	 * @param model 
	 * @param dec
	 */
	public DoubleTextField(String id, IModel<Double> model,  Integer dec) {
		super(id, model, Double.class);
		this.setOutputMarkupPlaceholderTag(true);
		this.dec = dec;
		this.add(new NumberFormatBehaviour(dec));
		this.add(ValidationStyleBehavior.INSTANCE);
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public <C> IConverter<C> getConverter(final Class<C> type) {
		NumberFormat nf = new DecimalFormat();
		nf.setMaximumFractionDigits(dec);
		nf.setMinimumFractionDigits(dec);
		DoubleConverter dc = new DoubleConverter();
		dc.setNumberFormat(getLocale(), nf);
		return (IConverter<C>) dc;
	}

}
