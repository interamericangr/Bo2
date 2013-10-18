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

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;

/**
 * Abstract base class for parsers.
 * 
 * @param <T> 
 *        Type of object being parsed.
 */
public abstract class AbstractParser<T> 
implements Parser<T> {
	
	public T parse(String value) throws ParseException {
		if (value==null || StringConstants.NULL.equalsIgnoreCase(value)) {
			return null;
		}	
		return mainParse(value);
	}
	
	
	/**
	 * Main parse method.
	 * 
	 * @param value
	 *        string to parse.
	 *        
	 * @return Returns the parsed value.
	 * 
	 * @throws ParseException 
	 */
	protected abstract T mainParse(String value) throws ParseException;

}
