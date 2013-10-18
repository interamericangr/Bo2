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

import org.apache.wicket.extensions.yui.calendar.DatePicker;

/**
 * Custom DatePicker.
 */
public class CustomDatePicker extends DatePicker{

	/**
	 * serial
	 */
	private static final long serialVersionUID = 1L;	
	
	/**
	 * Creates a new CustomDatePicker object. 
	 *
	 */
	public CustomDatePicker() {
		super();
	}
	@Override
	protected boolean enableMonthYearSelection( ) {
        return true;
    }
    @Override
	protected String getDatePattern( ) {
        return Bo2UtilsEnvironment.getShortDateFormatPattern();
    }

}
