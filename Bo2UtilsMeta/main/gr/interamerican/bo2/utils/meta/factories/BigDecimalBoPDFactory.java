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
package gr.interamerican.bo2.utils.meta.factories;

import gr.interamerican.bo2.utils.meta.descriptors.BigDecimalBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.PropertyDefinition;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;

import java.math.BigDecimal;

/**
 * Factory for {@link BigDecimalBoPDFactory}.
 */
public class BigDecimalBoPDFactory {
	
	/**
	 * Creates a {@link BigDecimalBoPDFactory}.
	 * 
	 * @param pd
	 *        PropertyDescriptorDefinition
	 *        
	 * @return BigDecimalBoPropertyDescriptor
	 */
	@SuppressWarnings("nls")
	public static BigDecimalBoPropertyDescriptor create(PropertyDefinition pd){
		BigDecimalBoPropertyDescriptor result = new BigDecimalBoPropertyDescriptor();
		result.setLengthOfDecimalPart(pd.getDecimalLength());
		result.setLengthOfIntegerPart(pd.getIntegerLength());
		result.setZeroAllowed(pd.getZeroAllowed());
		result.setNegativeAllowed(pd.getNegativeAllowed());
		if(pd.getHasDefault()) {
			try {
				BigDecimal bd = result.parse(pd.getDefaultValue());
				result.setDefaultValue(bd);
			} catch (ParseException e) {
				String msg = "Invalid default value specified for BigDecimalBoPropertyDescriptor "
					       + pd.getDefaultValue();
				throw new RuntimeException(msg);
			}
		}
		result.setMaxLength(pd.getIntegerLength());
		if (pd.getNegativeAllowed()) {
			result.setMaxLength(result.getMaxLength()+1);
		}
		if (pd.getDecimalLength()>0){
			result.setMaxLength(result.getMaxLength()+1+pd.getDecimalLength());
		}
		BoPDFactoryUtils.addCommonStuff(result, pd);
		return result;
	}
	
	/**
	 * empty hidden constructor 
	 */
	private BigDecimalBoPDFactory() { /* empty */ }

}
