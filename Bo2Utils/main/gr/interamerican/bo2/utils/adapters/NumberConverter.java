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
package gr.interamerican.bo2.utils.adapters;

import java.math.BigDecimal;

/**
 * Number converter.
 */
public class NumberConverter 
implements Transformation<Number, Number> {
	
	/**
	 * Output type.
	 */
	Class<? extends Number> outputType;

	/**
	 * Creates a new NumberConverter object. 
	 *
	 * @param outputType
	 */
	public NumberConverter(Class<? extends Number> outputType) {
		super();
		this.outputType = outputType;
	}

	@SuppressWarnings("nls")
	public Number execute(Number a) {
		if(a==null) {
			return null;
		}
		if(Byte.class.equals(outputType)) { 
			return new Byte(a.byteValue());
		} else if(Short.class.equals(outputType)) {
			return new Short(a.shortValue());
		} else if(Integer.class.equals(outputType)) {
			return new Integer(a.intValue());
		} else if(Long.class.equals(outputType)) {
			return new Long(a.longValue());
		} else if(Float.class.equals(outputType)) {
			return new Float(a.floatValue());
		} else if(Double.class.equals(outputType)) {
			return new Double(a.doubleValue());
		} else if(BigDecimal.class.equals(outputType)) {
			return new BigDecimal(a.toString());
		}
		String msg = "Conversion to " + outputType.getName() + " not supported.";
		throw new RuntimeException(msg);
	}

}
