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

import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.arch.utils.beans.TypedSelectableImpl;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.parsers.Parser;

/**
 * Parser for TypeSelectableParser for TypedSelectable<Long> .
 * 
 * This parser will return a TypedSelectable<?>.
 */
public class  TypeSelectableLongParser 
implements Parser<TypedSelectable<Long>> {
	
	
	public TypedSelectable<Long> parse(String value) throws ParseException {
		String[] splitValues = value.split(StringConstants.COMMA);
		Long typeId = convertToLong(splitValues[0]) ;
		Long subTypeId = convertToLong(splitValues[1]) ;
		Long code = convertToLong(splitValues[2]) ;
		TypedSelectable<Long> typeSelectable = new TypedSelectableImpl<Long>();
		typeSelectable.setCode(code);
		typeSelectable.setTypeId(typeId);
		typeSelectable.setSubTypeId(subTypeId);
		typeSelectable.setName(StringConstants.SPACE);
		return typeSelectable;
	}

	/**
	 * Check if the input is not null or blank or contains "null" value, 
	 * and returns a Long object that represents the integer value specified 
	 * by the String input.   
	 * 
	 * @param input
	 * @return Long value.
	 */
	private Long convertToLong(String input){
		if(	!StringUtils.isNullOrBlank(input)  && 
			!input.equals(StringConstants.NULL)){
			return Long.valueOf(input);
		}else return null;
	}
	
}
