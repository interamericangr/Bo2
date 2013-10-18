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
 * A {@link Converter} that converts an object to another of a different
 * type and vice-versa. The association MUST be one-to-one, i.e. if
 * an object L can be converted to object R, then R MUST be converted
 * to L using the same {@link TwoWayConverter} instance.
 * 
 * @param <L> Left type. 
 * @param <R> Right type.
 */
public interface TwoWayConverter<L,R> {
	
	/**
	 * Converts the specified object to an object of a different type.
	 * 
	 * @param l Object to convert.
	 * @return Returns the object that corresponds to the conversion
	 *         of the specified object.
	 *         
	 * @throws ConversionException 
	 */
	public R convertL(L l) throws ConversionException;
	
	/**
	 * Converts the specified object to an object of a different type.
	 * 
	 * @param r Object to convert.
	 * @return Returns the object that corresponds to the conversion
	 *         of the specified object.
	 *         
	 * @throws ConversionException 
	 */
	public L convertR(R r) throws ConversionException;

}
