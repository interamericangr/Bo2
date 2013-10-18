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

import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.PropertyDefinition;

/**
 * Utilities for creation of {@link BoPropertyDescriptor}s.
 */
public class BoPDFactoryUtils {
	
	/**
	 * name, readOnly, hasDefault and nullAllowed are used on all
	 * BoPropertyDescriptor types. This utility copies these properties
	 * from the {@link PropertyDefinition} to the {@link BoPropertyDescriptor}.
	 * 
	 * @param input
	 *        BoPropertyDescriptor
	 * @param def
	 *        PropertyDefinition
	 */
	public static void addCommonStuff(BoPropertyDescriptor<?> input, PropertyDefinition def) {
		input.setName(def.getName());
		input.setReadOnly(def.getReadOnly());
		input.setHasDefault(def.getHasDefault());
		input.setNullAllowed(def.getNullAllowed());
	}

}
