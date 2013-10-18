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

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.convert.converters.BigDecimalConverter;

/**
 * BigDecimal TextField.
 */
public class BigDecimalTextField extends TextField<BigDecimal> {
	/**
	 * serial.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private final Integer dec;
	/**
	 * Creates a new BigDecimalTextField object. 
	 *
	 * @param id
	 * @param dec
	 */
	public BigDecimalTextField(String id, Integer dec) {
		super(id);
		this.setOutputMarkupPlaceholderTag(true);
		this.dec = dec;
		this.add(new NumberFormatBehaviour(dec));
		this.add(ValidationStyleBehavior.INSTANCE);
	}	
	
	/**
	 * Creates a new BigDecimalTextField object. 
	 *
	 * @param id
	 * @param model 
	 * @param dec
	 */
	public BigDecimalTextField(String id, IModel<BigDecimal> model,  Integer dec) {
		super(id, model, BigDecimal.class);
		this.setOutputMarkupPlaceholderTag(true);
		this.dec = dec;
		this.add(new NumberFormatBehaviour(dec));
		this.add(ValidationStyleBehavior.INSTANCE);
		
	}

	@Override
	public IConverter getConverter(final Class<?> type) {
		NumberFormat nf = new DecimalFormat();
		nf.setMaximumFractionDigits(dec);
		nf.setMinimumFractionDigits(dec);
		BigDecimalConverter dc = new BigDecimalConverter();
		dc.setNumberFormat(getLocale(), nf);
		return dc;
	}

}
