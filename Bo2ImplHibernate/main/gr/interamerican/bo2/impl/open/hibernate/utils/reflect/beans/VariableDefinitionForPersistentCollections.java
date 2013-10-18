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
package gr.interamerican.bo2.impl.open.hibernate.utils.reflect.beans;

import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.reflect.beans.VariableDefinition;

import org.hibernate.collection.PersistentCollection;

/**
 * Special {@link VariableDefinition} sub-type that overrides to String so that
 * {@link PersistentCollection} unintended initialization is avoided. The collection
 * role (element entity type) and key is printed. 
 * 
 * @param <T>
 *        PersistentCollection sub-type. 
 */
public class VariableDefinitionForPersistentCollections<T extends PersistentCollection> 
extends VariableDefinition<T> {

	/**
	 * Creates a new VariableDefinitionForPersistentCollections object. 
	 *
	 * @param name
	 * @param type
	 */
	public VariableDefinitionForPersistentCollections(String name, Class<T> type) {
		super(name, type);
	}
	
	@Override
	public String toString() {
		PersistentCollection collection = getValue();
		String valueStr = collection.getRole() + StringUtils.toString(collection.getKey());
		String fields = StringUtils.concat(
			StringUtils.squareBracketsWithMandatoryContent(StringUtils.toString(getName())),
			StringUtils.squareBrackets(StringUtils.toString(getType())),
			StringUtils.squareBrackets(valueStr));
		return StringUtils.squareBrackets(fields);
	}

}
