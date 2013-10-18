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

import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Utility class.
 */
public class DescriptorUtils {
	/**
	 * Parses a Properties Object and creates a Map that maps 
	 * property names to the values parsed by {@link BoPropertyDescriptor}s.
	 * of the specified {@link BusinessObjectDescriptor}.
	 *  
	 * @param p
	 *        Object to parse.
	 * @param boDesc
	 *        BusinessObject descriptor.
	 *        
	 * @return Returns a map that maps BoPropertyDescriptors with
	 *         the values parsed by them.
	 *         
	 * @throws ParseException 
	 */
	public static Map<BoPropertyDescriptor<?>, Object> parse
	(Properties p, BusinessObjectDescriptor<?> boDesc) throws ParseException {
		Map<BoPropertyDescriptor<?>, Object> map = 
			new HashMap<BoPropertyDescriptor<?>, Object>();
		List<BoPropertyDescriptor<?>> descriptors = boDesc.getPropertyDescriptors();
		for (BoPropertyDescriptor<?> boPD : descriptors) {
			String name = boPD.getName();
			String string = p.getProperty(name);
			Object value = null;
			if (string!=null) {
				value = boPD.parse(string);
			}			
			map.put(boPD, value);
		}
		return map;		
	}
	
	/**
	 * Returns a List with all the names of the {@link BoPropertyDescriptor}s
	 * of a {@link BusinessObjectDescriptor}.
	 * 
	 * @param descriptor
	 * 
	 * @return List of property names
	 */
	public static List<String> getPropertyNames(BusinessObjectDescriptor<?> descriptor) {
		List<String> result = new ArrayList<String>();
		for(BoPropertyDescriptor<?> pd : descriptor.getPropertyDescriptors()) {
			result.add(pd.getName());
		}
		return result;
	}

}
