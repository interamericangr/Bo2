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
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;

/**
 * {@link Parser} for Integer.
 */
public class LongParser extends AbstractParser<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected Long mainParse(String value) throws ParseException {
		if(MIN_VALUE.equalsIgnoreCase(value)){
			return Long.MIN_VALUE;
		}
		if(MAX_VALUE.equalsIgnoreCase(value)){
			return Long.MAX_VALUE;
		}
		try {
			return Long.valueOf(value);
		} catch (NumberFormatException e) {
			throw new ParseException(e.toString());
		}
	}

}

