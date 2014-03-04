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


import gr.interamerican.bo2.utils.Bo2UtilsEnvironment;

import java.util.Date;

import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;
/**
 * Custom DateTextField.
 */
public class CustomDateTextField extends DateTextField{

	/**
	 * serial.
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Creates a new CustomDateTextField object. 
	 *
	 * @param id
	 */
	public CustomDateTextField(String id) {
		super(id);
		this.setOutputMarkupPlaceholderTag(true);
		this.add(new CustomDatePicker());
	}

	/**
	 * Creates a new CustomDateTextField object. 
	 *
	 * @param id
	 * @param model
	 */
	public CustomDateTextField(String id, IModel<Date> model) {
		super(id, model);
		this.setOutputMarkupPlaceholderTag(true);
		this.add(new CustomDatePicker());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <C> IConverter<C> getConverter(Class<C> type) {
		return (IConverter<C>) new PatternDateConverter 
			(Bo2UtilsEnvironment.getShortDateFormatPattern(), true);
	}
	
}
