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
package gr.interamerican.bo2.utils.reflect.analyze;

import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.VariableDefinitionFactory;
import gr.interamerican.bo2.utils.reflect.beans.VariableDefinition;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract object structure analyzer based on fields.
 */
public abstract class AbstractFieldsAnalyzer 
extends AbstractObjectStructureAnalyzer {

	/**
	 * List with the fields to include.
	 * 
	 * @param clazz
	 *        Class to analyze.
	 *        
	 * @return Returns the list of fields to include.
	 */
	protected abstract List<Field> fieldsToInclude(Class<?> clazz);
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected List<VariableDefinition<?>> whichFieldsToInclude(Object object) {
		List<VariableDefinition<?>> list = new ArrayList<VariableDefinition<?>>();
		if (object==null) {
			return list;
		}
		List<Field> fields = fieldsToInclude(object.getClass());		
		for (Field field : fields) {
			field.setAccessible(true);
			Object value = ReflectionUtils.get(field, object);
			VariableDefinition vd = VariableDefinitionFactory.create(field);
			vd.setValue(value);
			list.add(vd);
		}		
		return list;
	}

	

}
