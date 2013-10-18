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
package gr.interamerican.bo2.samples.hibernate.twc;

import gr.interamerican.bo2.impl.open.hibernate.types.TwoWayConverterUserType;
import gr.interamerican.bo2.utils.meta.convert.TwoWayConverter;
import gr.interamerican.bo2.utils.meta.exceptions.ConversionException;

/**
 * Sample {@link TwoWayConverter} implementation for {@link TwoWayConverterUserType} test.
 */
public class VarcharToStringConverter implements TwoWayConverter<String, String> {

	public String convertL(String l) throws ConversionException {
		return l;
	}

	public String convertR(String r) throws ConversionException {
		return r;
	}

}
