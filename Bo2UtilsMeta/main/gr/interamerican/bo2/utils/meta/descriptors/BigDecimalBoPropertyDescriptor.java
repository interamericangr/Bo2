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
package gr.interamerican.bo2.utils.meta.descriptors;

import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.formatters.DecimalFormatter;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.formatters.nr.NrDecimalFormatter;
import gr.interamerican.bo2.utils.meta.parsers.BigDecimalParser;

import java.math.BigDecimal;

/**
 * {@link BoPropertyDescriptor} for BigDecimal.
 */
public class BigDecimalBoPropertyDescriptor 
extends AbstractNumberBoPropertyDescriptor<BigDecimal> {
		
	/**
	 * Creates a new BigDecimalBoPropertyDescriptor object. 
	 */
	public BigDecimalBoPropertyDescriptor() {
		super(null);
	}

	@Override
	public BigDecimal parse(String value) throws ParseException {
		return new BigDecimalParser(getLengthOfDecimalPart()).parse(value);
	}
	
	@Override
	protected Formatter<BigDecimal> getFormatter() {
		if(isNullAllowed()) {
			return new NrDecimalFormatter<BigDecimal>(getLengthOfDecimalPart());
		}
		return new DecimalFormatter<BigDecimal>(getLengthOfDecimalPart());
	}
	
	
}
