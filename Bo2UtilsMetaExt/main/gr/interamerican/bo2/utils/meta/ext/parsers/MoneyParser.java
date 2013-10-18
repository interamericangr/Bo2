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
package gr.interamerican.bo2.utils.meta.ext.parsers;

import gr.interamerican.bo2.arch.Money;
import gr.interamerican.bo2.arch.utils.beans.MoneyImpl;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.parsers.BigDecimalParser;
import gr.interamerican.bo2.utils.meta.parsers.Parser;

import java.math.BigDecimal;

/**
 * Parser for Money.
 * 
 * This parser will return a MoneyImpl for the default currency.
 */
public class MoneyParser 
implements Parser<Money> {
	
	/**
	 * Big decimal parser
	 */
	private BigDecimalParser bdParser;
	
	/**
	 * Creates a new MoneyParser object. 
	 * @param decimalLength 
	 */
	public MoneyParser(int decimalLength) {
		bdParser = new BigDecimalParser(decimalLength);
	}
	
	public Money parse(String value) throws ParseException {
		BigDecimal bd = bdParser.parse(value);
		Money money = new MoneyImpl(bd);
		return money;
	}

}
