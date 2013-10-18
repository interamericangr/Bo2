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
package gr.interamerican.bo2.impl.open.transformations;

import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.reflect.analyze.AbstractObjectStructureAnalyzer;
import gr.interamerican.bo2.utils.reflect.analyze.TypeAnalysis;
import gr.interamerican.bo2.utils.reflect.beans.BeanPropertyDefinition;
import gr.interamerican.bo2.utils.reflect.beans.VariableDefinition;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * AbstractObjectStructureAnalyzer that uses the object's read properties
 * to analyze the object.
 */
public class ObjectPropertiesAnalyzer
extends AbstractObjectStructureAnalyzer {
	
	/**
	 * Getter args.
	 */
	private static final Object[] GETTER_ARGS = {};

	/**
	 * Properties that are excluded from the analysis.
	 */
	@SuppressWarnings("nls")
	private static final String[] EXCLUDED_PROPERTIES = { "password", "pass" };
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List<VariableDefinition<?>> whichFieldsToInclude(Object object) {
		ArrayList<VariableDefinition<?>> properties = new ArrayList<VariableDefinition<?>>();
		if (object!=null) {			
			Class<?> type = Factory.getCurrentFactory().getDeclarationType(object.getClass());
			TypeAnalysis analysis = TypeAnalysis.analyze(type);
			Set<BeanPropertyDefinition<?>> bpds = analysis.getAllProperties();
			for (BeanPropertyDefinition<?> bpd : bpds) {
				if(shouldBeExcluded(bpd.getName())) {
					continue;
				}
				Method getter = bpd.getGetter(); 
				if (getter!=null) {
					Object value = ReflectionUtils.invoke(getter, object, GETTER_ARGS);				
					VariableDefinition vd = new VariableDefinition(bpd.getName(), bpd.getType());
					vd.setValue(value);
					properties.add(vd);
				}			
			}
		}
		return properties;
	}
	
	/**
	 * @param propertyName
	 * @return If the property should be excluded.
	 */
	private boolean shouldBeExcluded(String propertyName) {
		for(String excludedProperty : EXCLUDED_PROPERTIES) {
			if(excludedProperty.equalsIgnoreCase(propertyName)) {
				return true;
			}
		}
		return false;
	}

}
