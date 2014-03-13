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
import gr.interamerican.bo2.utils.meta.parsers.DoubleParser;

/**
 * {@link BoPropertyDescriptor} for Double.
 */
public class DoubleBoPropertyDescriptor 
extends AbstractNumberBoPropertyDescriptor<Double> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new DoubleBoPropertyDescriptor object.
	 */
	public DoubleBoPropertyDescriptor() {
		super(null);
	}

	@Override
	public Double parse(String value) throws ParseException {
		return new DoubleParser(getLengthOfDecimalPart()).parse(value);
	}

	@Override
	protected Formatter<Double> getFormatter() {
		return new DecimalFormatter<Double>(getLengthOfDecimalPart());
	}	
	
	@Override
	public Double valueOf(Number value) {	
		return value.doubleValue();
	}

}
