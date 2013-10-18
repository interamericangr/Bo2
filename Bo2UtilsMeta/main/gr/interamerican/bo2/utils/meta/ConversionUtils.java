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
package gr.interamerican.bo2.utils.meta;

import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.meta.convert.Mapper;
import gr.interamerican.bo2.utils.meta.exceptions.ConversionException;

/**
 * Utilities about conversion.
 */
public class ConversionUtils {
	
	/**
	 * Delegates the conversion of the specified object to
	 * the specified mapper. <br/>
	 * 
	 * This method will wrap any {@link ConversionException} that occured
	 * during the conversion inside a {@link RuntimeException}.
	 * 
	 * @param mapper
	 *        Mapper doing the conversion.
	 * @param l
	 *        Object being converted.
	 * @param <L>
	 *        Type of object being converted.
	 * @param <R>
	 *        Type of conversion result.
	 *  
	 * @return Returns the result of the conversion.
	 * 
	 * @throws RuntimeException
	 *         If it catches ConversionException during the conversion.
	 */
	public static <L,R> R convert(Mapper<L, R> mapper, L l) {
		try {
			return mapper.convert(l);
		} catch (ConversionException ce) {
			throw new RuntimeException(ce);
		}
	}
	
	/**
	 * Delegates the mandatory conversion of the specified object to
	 * the specified mapper. <br/>
	 * 
	 * This method will wrap any {@link ConversionException} that occured
	 * during the conversion inside a {@link RuntimeException}. I
	 * 
	 * @param mapper
	 *        Mapper doing the conversion.
	 * @param l
	 *        Object being converted.
	 * @param <L>
	 *        Type of object being converted.
	 * @param <R>
	 *        Type of conversion result.
	 *  
	 * @return Returns the result of the conversion. Never returns null.
	 * 
	 * @throws RuntimeException
	 *         If it catches ConversionException during the conversion.
	 */
	@SuppressWarnings("nls")
	public static <L,R> R mandatoryConvert(Mapper<L, R> mapper, L l) {
		R r = convert(mapper, l);
		if (r==null) {
			String msg = StringUtils.concat(
					"The mapper " +
					mapper.toString(),
					" could not find a mapping for ",
					l.toString());
			throw new RuntimeException(msg);
		}
		return r;
	}

}
