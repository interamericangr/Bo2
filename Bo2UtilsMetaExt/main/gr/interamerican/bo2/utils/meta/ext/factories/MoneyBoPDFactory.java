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
package gr.interamerican.bo2.utils.meta.ext.factories;

import gr.interamerican.bo2.arch.Money;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.meta.Meta;
import gr.interamerican.bo2.utils.meta.descriptors.PropertyDefinition;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.ext.descriptors.MoneyBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.factories.BoPDFactoryUtils;

/**
 * Factory for {@link MoneyBoPropertyDescriptor}.
 */
public class MoneyBoPDFactory {
	
	/**
	 * Creates a {@link MoneyBoPropertyDescriptor}.
	 * 
	 * @param pd
	 *        PropertyDefinition
	 *        
	 * @return IntegerBoPropertyDescriptor
	 * 
	 * @throws ParseException 
	 */
	@SuppressWarnings("nls")
	public static MoneyBoPropertyDescriptor create(PropertyDefinition pd) throws ParseException {
		MoneyBoPropertyDescriptor result = new MoneyBoPropertyDescriptor();
		result.setLengthOfDecimalPart(pd.getDecimalLength());
		result.setLengthOfIntegerPart(pd.getIntegerLength());
		result.setZeroAllowed(pd.getZeroAllowed());
		result.setNegativeAllowed(pd.getNegativeAllowed());
		if(pd.getHasDefault()) {
			try {
				Money value = result.parse(pd.getDefaultValue());
				result.setDefaultValue(value);
			} catch (ParseException pe) {
				String msg = StringUtils.concat(
						"Could not create a Money Object given the value: ",
						pd.getDefaultValue() + " for property " + pd.getName());
				throw new ParseException(msg, pe);
			} 
		}
		result.setMaxLength(Meta.getDefaultFormatterLength);
		BoPDFactoryUtils.addCommonStuff(result, pd);
		return result;
	}
	

}
