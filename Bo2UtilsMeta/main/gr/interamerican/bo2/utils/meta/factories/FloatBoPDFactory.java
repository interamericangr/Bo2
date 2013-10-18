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


import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.meta.descriptors.FloatBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.PropertyDefinition;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;

/**
 * Factory for {@link FloatBoPropertyDescriptor}s.
 */
public class FloatBoPDFactory {

	/**
	 * Creates a {@link FloatBoPropertyDescriptor}.
	 * 
	 * @param pd
	 *        PropertyDescriptorDefinition
	 *        
	 * @return FloatBoPropertyDescriptor
	 * 
	 * @throws ParseException 
	 */
	@SuppressWarnings("nls")
	public static FloatBoPropertyDescriptor create(PropertyDefinition pd) throws ParseException {
		FloatBoPropertyDescriptor result = new FloatBoPropertyDescriptor();
		result.setLengthOfDecimalPart(pd.getDecimalLength());
		result.setLengthOfIntegerPart(pd.getIntegerLength());
		result.setZeroAllowed(pd.getZeroAllowed());
		result.setNegativeAllowed(pd.getNegativeAllowed());
		if(pd.getHasDefault()) {
			try {
				Float value = Float.parseFloat(pd.getDefaultValue());
				result.setDefaultValue(value);
			} catch (NumberFormatException nfe) {
				String msg = StringUtils.concat(
						"Could not create a Float given the value: ",
						pd.getDefaultValue() + " for property " + pd.getName());
				throw new ParseException(msg, nfe);
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
	 * Hidden constructor.
	 */
	public FloatBoPDFactory(){ /* empty */ }
	
}
