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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract object structure analyzer based on declared fields.
 */
public class DeclaredFieldsAnalyzer 
extends AbstractFieldsAnalyzer {

	/**
	 * List with the fields to include.
	 * 
	 * @param clazz
	 *        Class to analyze.
	 *        
	 * @return Returns the list of fields to include.
	 */
	@Override
	protected List<Field> fieldsToInclude(Class<?> clazz) {
		List<Field> list = new ArrayList<Field>();				
		boolean isCollectionOrArray = ReflectionUtils.isArray(clazz) 
		                            || ReflectionUtils.isCollection(clazz);				
		if (!isCollectionOrArray) {
			List<Field> fields = ReflectionUtils.declaredFields(clazz);
			for (Field field : fields) {
				if (!Modifier.isStatic(field.getModifiers())) {
					list.add(field);
				}
			}
		}
		return list;	
	}
	
	
	

}
