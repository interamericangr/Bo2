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
package gr.interamerican.bo2.utils.meta.transformations;

import gr.interamerican.bo2.utils.adapters.AnyOperation;
import gr.interamerican.bo2.utils.meta.BusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;

import java.util.Map;
import java.util.Properties;

/**
 * Transforms an object to a {@link Properties}. 
 * 
 * @param <T> 
 *        Type of object being transformed.
 * 
 */
public class ToProperties<T> 
implements AnyOperation<T, Properties>{
	
	/**
	 * Business object descriptor.
	 */
	BusinessObjectDescriptor<T> descriptor;
	
	/**
	 * Creates a new ToProperties object. 
	 *
	 * @param descriptor
	 */
	public ToProperties(BusinessObjectDescriptor<T> descriptor) {
		super();
		this.descriptor = descriptor;
	}
	
	public Properties execute(T a) {
		Properties p = new Properties();		
		Map<BoPropertyDescriptor<?>, Object> propertyValues = descriptor.get(a);
		for (Map.Entry<BoPropertyDescriptor<?>, Object> entry : propertyValues.entrySet()) {
			@SuppressWarnings("unchecked") BoPropertyDescriptor<Object> boPD = 
				(BoPropertyDescriptor<Object>) entry.getKey();
			Object value = entry.getValue();
			String propertyName = boPD.getName();		
			String propertyValue = boPD.format(value);
			p.setProperty(propertyName, propertyValue);
		}		
		return p;
	}	

}
