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

import gr.interamerican.bo2.utils.meta.Meta;
import gr.interamerican.bo2.utils.meta.descriptors.BooleanBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.PropertyDefinition;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.parsers.ParserResolver;

/**
 * Factory for {@link BooleanBoPropertyDescriptor}s.
 */
public class BooleanBoPDFactory {

	/**
	 * Creates a {@link BooleanBoPropertyDescriptor}.
	 * 
	 * @param pd
	 *        PropertyDescriptorDefinition
	 *        
	 * @return BooleanBoPropertyDescriptor
	 */
	public static BooleanBoPropertyDescriptor create(PropertyDefinition pd) {
		BooleanBoPropertyDescriptor result = new BooleanBoPropertyDescriptor();
		if(pd.getHasDefault()) {
			try {
				Boolean value = ParserResolver.getParser(Boolean.class).parse(pd.getDefaultValue());
				result.setDefaultValue(value);
			} catch (ParseException e) {
				/*
				 * In the current implementation of BooleanParser a ParseException
				 * is never actually thrown. However, it is re-thrown here as a 
				 * RuntimeException in case the implementation changes in the future.
				 */
				throw new RuntimeException(e);
			}
		}
		result.setMaxLength(Meta.getDefaultFormatterLength);
		BoPDFactoryUtils.addCommonStuff(result, pd);
		return result;
	}
	
	/**
	 * Hidden constructor 
	 */
	private BooleanBoPDFactory() { /* empty */ }
	
}
