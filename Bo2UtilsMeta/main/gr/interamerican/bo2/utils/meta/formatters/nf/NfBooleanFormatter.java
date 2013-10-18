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

import gr.interamerican.bo2.utils.meta.formatters.BooleanFormatter;

/**
 * NullFilteringFormatter for Boolean.
 */
public class NfBooleanFormatter extends NullFilteringFormatter<Boolean> {


	/**
	 * Creates a new NfBooleanFormatter object. 
	 *
	 * @param trueLiteral
	 * @param falseLiteral
	 */
	public NfBooleanFormatter(String trueLiteral, String falseLiteral) {
		super(new BooleanFormatter(trueLiteral, falseLiteral));
	}
	
	/**
	 * Creates a new NfBooleanFormatter object.
	 */
	public NfBooleanFormatter() {
		super(new BooleanFormatter());
	}

}
