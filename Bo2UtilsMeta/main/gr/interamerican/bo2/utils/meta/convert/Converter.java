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
package gr.interamerican.bo2.utils.meta.convert;

import gr.interamerican.bo2.utils.meta.exceptions.ConversionException;

/**
 * A {@link Converter} converts an object to another of a different \
 * type.
 * 
 * @param <L> Type of objects converted by this converted. 
 * @param <R> Output type of objects, created by this converter.
 */
public interface Converter<L,R> {
	
	/**
	 * Converts the specified object to an object of a different type.
	 * 
	 * @param l Object to convert.
	 * @return Returns the object that corresponds to the conversion
	 *         of the specified object.
	 *         
	 * @throws ConversionException 
	 */
	public R convert(L l) throws ConversionException;

}
