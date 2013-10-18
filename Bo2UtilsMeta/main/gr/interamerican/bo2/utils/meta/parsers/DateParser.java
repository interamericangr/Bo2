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

import gr.interamerican.bo2.utils.DateUtils;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;

import java.util.Date;

/**
 * Parser for Date.
 */
public class DateParser extends AbstractParser<Date>{
	
	@Override
	protected Date mainParse(String value) throws ParseException {		
		try {
			Date dt = DateUtils.getDate(value);		
			return dt;
		} catch (java.text.ParseException e) {
			throw new ParseException(e.toString());
		}
	}

}
