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

import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.formatters.FormatterResolver;
import gr.interamerican.bo2.utils.meta.parsers.ParserResolver;

/**
 * {@link BoPropertyDescriptor} for Long.
 */
public class LongBoPropertyDescriptor 
extends AbstractIntTypesBoPropertyDescriptor<Long> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new IntegerBoPropertyDescriptor object.
	 */
	public LongBoPropertyDescriptor() {
		super(ParserResolver.getParser(Long.class));
	}
	
	@Override
	protected Formatter<Long> getFormatter() {
		if(isNullAllowed()) {
			return FormatterResolver.<Long>getNrFormatter(Long.class);
		}
		return FormatterResolver.<Long>getFormatter(Long.class);
	}
	
	@Override
	public Long valueOf(Number value) {	
		return value.longValue();
	}
	
}
