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
import gr.interamerican.bo2.utils.meta.parsers.Parser;
import gr.interamerican.bo2.utils.meta.parsers.ParserResolver;

import java.sql.Time;

/**
 * {@link BoPropertyDescriptor} for Time objects.
 */
public class TimeBoPropertyDescriptor 
extends AbstractBoPropertyDescriptor<Time> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new DateBoPropertyDescriptor object. 
	 */
	public TimeBoPropertyDescriptor() {
		super(ParserResolver.getParser(Time.class));
	}
	
	@Override
	protected Formatter<Time> getFormatter() {
		if(isNullAllowed()) {
			return FormatterResolver.getNrFormatter(Time.class);
		}
		return FormatterResolver.getFormatter(Time.class);
	}

	/**
	 * Creates a new DateBoPropertyDescriptor object. 
	 *
	 * @param parser the parser
	 */
	public TimeBoPropertyDescriptor(Parser<Time> parser) {
		super(parser);
		setMaxLength(5);
	}
	
	
	

}
