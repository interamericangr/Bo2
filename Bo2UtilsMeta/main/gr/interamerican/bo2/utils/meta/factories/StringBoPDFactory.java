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

import gr.interamerican.bo2.utils.meta.descriptors.PropertyDefinition;
import gr.interamerican.bo2.utils.meta.descriptors.StringBoPropertyDescriptor;

/**
 * Factory for {@link StringBoPropertyDescriptor}s.
 */
public class StringBoPDFactory{

	/**
	 * Creates the StringBoPropertyDescriptor
	 * 
	 * @param pd
	 *        PropertyDefinition
	 *        
	 * @return StringBoPropertyDescriptor
	 */
	public static StringBoPropertyDescriptor create(PropertyDefinition pd) {
		StringBoPropertyDescriptor result = new StringBoPropertyDescriptor();
        result.setMaxLength(pd.getMaxLength());
        result.setMinLength(pd.getMinLength());
        result.setExpression(pd.getExpression());
        if(pd.getHasDefault()) {
        	result.setDefaultValue(pd.getDefaultValue());
        }
		result.setMaxLength(pd.getMaxLength());
        BoPDFactoryUtils.addCommonStuff(result, pd);
		return result;
	}
	
	/**
	 * Hidden constructor. 
	 */
	private StringBoPDFactory(){ /* empty */ }
	
}
