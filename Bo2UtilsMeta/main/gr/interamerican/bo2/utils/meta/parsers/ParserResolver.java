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

import gr.interamerican.bo2.utils.beans.TypeBasedSelection;

import java.util.Date;

/**
 * Returns a {@link Parser}, based on the type of the
 * object that is to be produced by the parser.
 */
public class ParserResolver  {
	
	/**
	 * TypeBasedSelection.
	 */
	private static TypeBasedSelection<Parser<?>> parsers;
	
	static {
		parsers = new TypeBasedSelection<Parser<?>>();
		parsers.registerSelection(Boolean.class, new BooleanParser());
		parsers.registerSelection(Date.class, new DateParser());
		parsers.registerSelection(Integer.class, new IntegerParser());
		parsers.registerSelection(Long.class, new LongParser());
		parsers.registerSelection(Short.class, new ShortParser());
		parsers.registerSelection(String.class, new StringParser());
	}

	/**
	 * Gets the parser that can parse a String to an instance
	 * of the specified code type.
	 * 
	 * @param <C>
	 *        Type of instance created by the parser.
	 * @param codeType
	 *        Class of type of instance created by the parser.
	 *        
	 * @return Returns the appropriate Parser<C> implementation.
	 */
	@SuppressWarnings("unchecked")
	public static <C> Parser<C> getParser(Class<C> codeType) {
		return (Parser<C>) parsers.selectionForType(codeType);
	}
	
	/**
	 * Hidden constructor of utility class.
	 */
	private ParserResolver() { /* hidden */	}

}
