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

import java.util.Date;
import java.util.Locale;

import org.apache.wicket.extensions.yui.calendar.DateField;
import org.apache.wicket.model.IModel;

/**
 * Custom DateField.
 */
public class CustomDateField extends DateField{
	/**
	 * serial
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new CustomDateField object. 
	 *
	 * @param id
	 */
	public CustomDateField(String id) {
		super(id);
		this.setOutputMarkupPlaceholderTag(true);
	}

	/**
	 * Creates a new CustomDateField object. 
	 *
	 * @param id
	 * @param model
	 */
	public CustomDateField(String id, IModel<Date> model) {
		super(id, model);
		this.setOutputMarkupPlaceholderTag(true);
	}
	
	@Override
	public Locale getLocale() {
		Locale l = new Locale("el", "GR"); //$NON-NLS-1$ //$NON-NLS-2$
		return l;
	}   

}
