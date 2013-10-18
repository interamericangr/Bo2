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
package gr.interamerican.bo2.utils.meta.parsers;

import static gr.interamerican.bo2.utils.meta.parsers.Constants.MAX_VALUE;
import static gr.interamerican.bo2.utils.meta.parsers.Constants.MIN_VALUE;
import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * {@link Parser} for BigDecimals.
 */
public class BigDecimalParser extends AbstractParser<BigDecimal> {

	/**
	 * Decimal digits.
	 */
	int decimalDigits = -1;
	
	/**
	 * Creates a new BigDecimalParser object. 
	 */
	public BigDecimalParser() {
		super();
	}
	
	/**
	 * Creates a new DoubleParser object. 
	 * @param decimalDigits 
	 */
	public BigDecimalParser(int decimalDigits) {
		this.decimalDigits = decimalDigits;
	}
	
	@Override
	protected BigDecimal mainParse(String value) throws ParseException {
		if(MIN_VALUE.equalsIgnoreCase(value)){
			return new BigDecimal(Long.MIN_VALUE);
		}	
		if(MAX_VALUE.equalsIgnoreCase(value)){
			return new BigDecimal(Long.MAX_VALUE);
		}			
		try {
			double d = NumberUtils.parseDouble(value);
			BigDecimal bd = new BigDecimal(d);
			if(decimalDigits!=-1) {
				bd = bd.setScale(decimalDigits, RoundingMode.HALF_EVEN);
			}
			return bd;
		} catch (java.text.ParseException e) {
			throw new ParseException(e.toString());
		}
	}
	
}
