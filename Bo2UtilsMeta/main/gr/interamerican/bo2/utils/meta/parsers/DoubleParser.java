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

/**
 * {@link Parser} for Double.
 */
public class DoubleParser extends AbstractParser<Double>{

	/**
	 * Decimal digits.
	 */
	int decimalDigits  = -1;
	
	/**
	 * Creates a new DoubleParser object. 
	 */
	public DoubleParser() {
		super();
	}
	
	/**
	 * Creates a new DoubleParser object. 
	 * @param decimalDigits 
	 */
	public DoubleParser(int decimalDigits) {
		this.decimalDigits = decimalDigits;
	}
	
	@Override
	protected Double mainParse(String value) throws ParseException {
		if(MIN_VALUE.equalsIgnoreCase(value)){
			return Double.MIN_VALUE;
		}	
		if(MAX_VALUE.equalsIgnoreCase(value)){
			return Double.MAX_VALUE;
		}			
		try {
			double dv = NumberUtils.parseDouble(value);
			if(decimalDigits!=-1) {
				dv = NumberUtils.round(dv, decimalDigits);
			}
			return dv;
		} catch (java.text.ParseException e) {
			throw new ParseException(e.toString());
		}
	}

}
