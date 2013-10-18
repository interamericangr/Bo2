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
package gr.interamerican.bo2.utils.meta.ext.formatters;

import gr.interamerican.bo2.arch.Money;
import gr.interamerican.bo2.utils.meta.formatters.DecimalFormatter;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;

import java.math.BigDecimal;

/**
 * {@link Formatter} for Money.
 */
public class MoneyFormatter implements Formatter<Money> {
	
	/**
	 * Singleton instance
	 */
	public static final MoneyFormatter INSTANCE = new MoneyFormatter();
	
	/**
	 * formatter for the amount.
	 */
	private static final DecimalFormatter<BigDecimal> df = new DecimalFormatter<BigDecimal>(2);
	
	public String format(Money t) {		
		return df.format(t.getAmount());
	}
	
	/**
	 * Use singleton.
	 */
	private MoneyFormatter() { /* hidden, empty */ }

}
