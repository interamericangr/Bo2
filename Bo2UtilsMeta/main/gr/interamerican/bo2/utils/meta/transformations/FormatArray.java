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
package gr.interamerican.bo2.utils.meta.transformations;

import gr.interamerican.bo2.utils.ArrayUtils;
import gr.interamerican.bo2.utils.adapters.AnyOperation;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;

/**
 * Transforms an array of objects to an array of strings using an array 
 * of Formatters. <br/>
 * 
 * Each object is transformed to string by the formatter with the same
 * index.
 * 
 */
public class FormatArray 
implements AnyOperation<Object[], String[]> {
	
	/**
	 * Formatters.
	 */
	Formatter<?>[] formatters;

	
	public String[] execute(Object[] a) {
		Object[] array = ArrayUtils.enforceCapacity(a, formatters.length);
		String[] strings = new String[formatters.length];
		for (int i = 0; i < formatters.length; i++) {
			@SuppressWarnings("unchecked")
			Formatter<Object> f = (Formatter<Object>) formatters[i];			
			strings[i] = f.format(array[i]);
		}
		return strings;
	}


	/**
	 * Creates a new FormatArray object. 
	 *
	 * @param formatters
	 */
	public FormatArray(Formatter<?>[] formatters) {
		super();
		this.formatters = formatters;
	}

}
