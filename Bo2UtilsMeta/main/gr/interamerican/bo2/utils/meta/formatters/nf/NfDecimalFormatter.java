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
package gr.interamerican.bo2.utils.meta.formatters.nf;

import gr.interamerican.bo2.utils.meta.formatters.DecimalFormatter;

import java.text.DecimalFormat;

/**
 * NullFilteringFormatter for Numbers.
 */
public class NfDecimalFormatter extends NullFilteringFormatter<Number> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new NfDecimalFormatter object. 
	 *
	 * @param df
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public NfDecimalFormatter(DecimalFormat df) {
		super(new DecimalFormatter(df));
	}

	/**
	 * Creates a new NfDecimalFormatter object. 
	 *
	 * @param decimalDigits
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public NfDecimalFormatter(int decimalDigits) {
		super(new DecimalFormatter(decimalDigits));
	}

}
