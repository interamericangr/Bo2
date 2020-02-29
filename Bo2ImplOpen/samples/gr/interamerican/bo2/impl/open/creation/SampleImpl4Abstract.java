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

package gr.interamerican.bo2.impl.open.creation;

import java.util.Map;
import java.util.Set;

import gr.interamerican.bo2.creation.creators.ImplementorForAbstractClasses;
import gr.interamerican.bo2.utils.reflect.beans.BeanPropertyDefinition;

/**
 * Sample {@link Impl4Abstract} that exposes the list of mocked/supporting properties, used in tests.
 */
public class SampleImpl4Abstract extends Impl4Abstract {
	/**
	 * Gets the set of properties that are declared as mock. Used to expose {@link ImplementorForAbstractClasses} mockProperties list. 
	 * @return the set of mocked properties list.
	 */
	public Set<BeanPropertyDefinition<?>> getMockedProperties() {
		return mockProperties;
	}
	
	/**
	 * Gets the map of properties that are declared as replacement. Used to expose {@link ImplementorForAbstractClasses} propertyReplacement list.
	 * @return the replacement properties map.
	 */
	public Map<BeanPropertyDefinition<?>, BeanPropertyDefinition<?>> getReplacementProperties() {
		return propertyReplacements;
	}
}
