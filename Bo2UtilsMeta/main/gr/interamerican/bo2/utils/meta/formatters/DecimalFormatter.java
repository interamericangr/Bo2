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

import java.text.DecimalFormat;

/**
 * Formatter for numbers objects.
 * <br/>
 * This class is thread safe.
 * 
 * @param <T>
 *        Type of Number.
 */
public class DecimalFormatter<T extends Number> 
implements Formatter<T> {
	
	/**
	 * Decimal format.
	 */
	final DecimalFormat df;
	
	/**
	 * Creates a new DateFormatter object. 
	 *
	 * @param df
	 */
	public DecimalFormatter(DecimalFormat df) {
		super();
		this.df = df;
	}
	
	/**
	 * Creates a new DecimalFormatter object.
	 * @param decimalDigits 
	 */
	public DecimalFormatter(int decimalDigits) {
		this.df = new DecimalFormat();
		if (decimalDigits==0) {
			df.setDecimalSeparatorAlwaysShown(false);
		}
		df.setMaximumFractionDigits(decimalDigits);
		df.setMinimumFractionDigits(decimalDigits);
	}

	public synchronized String format(T t) {
		if(t==null) {
			return StringConstants.NULL;
		}
		return df.format(t);
	}
}
