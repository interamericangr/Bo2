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
package gr.interamerican.bo2.utils.meta.formatters;

import gr.interamerican.bo2.utils.StringConstants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Formatter for date objects.
 * <br/>
 * This class is thread safe.
 */
public class DateFormatter implements Formatter<Date> {
	/**
	 * Date format.
	 */
	DateFormat df;

	/**
	 * Creates a new DateFormatter object. 
	 *
	 * @param df
	 *        Date format object.
	 */
	public DateFormatter(DateFormat df) {
		super();
		this.df = df;
	}
	
	/**
	 * Creates a new DateFormatter object. 
	 *
	 * @param dateFormat
	 *        Date format string.
	 */
	public DateFormatter(String dateFormat) {
		this(new SimpleDateFormat(dateFormat));
	}

	public synchronized String format(Date t) {
		if(t==null) {
			return StringConstants.NULL;
		}
		return df.format(t);
	}

}
