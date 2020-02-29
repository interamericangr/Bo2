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

import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.reflect.beans.VariableDefinition;

/**
 * {@link VariableDefinition} for {@link PersistentObject}s.
 * 
 * @param <P>
 *        PersistentObject sub-type. 
 */
public class VariableDefinitionForPersistentObjects<P extends PersistentObject<?>>
extends VariableDefinition<P>{

	/**
	 * Creates a new VariableDefinitionForPersistentObjects object. 
	 *
	 * @param name the name
	 * @param type the type
	 */
	public VariableDefinitionForPersistentObjects(String name, Class<P> type) {
		super(name, type);
	}
	
	@Override
	public String toString() {
		Class<?> type = getType();
		String typeStr = type==null ? StringConstants.NULL : StringUtils.stripPkgFromFqcn(type.getName());
		P value = getValue();
		String valueStr = StringConstants.NULL;
		if(value!=null) {
			if(value.getKey()!=null) {
				valueStr = value.getKey().toString(); 
			}
		}
		String fields = StringUtils.concat(
				StringUtils.squareBracketsWithMandatoryContent(StringUtils.toString(getName())),
				StringUtils.squareBrackets(typeStr),
				valueStr);
		return StringUtils.squareBrackets(fields);
	}

}
