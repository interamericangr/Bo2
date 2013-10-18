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
package gr.interamerican.bo2.impl.open.po;

import gr.interamerican.bo2.utils.annotations.Child;
import gr.interamerican.bo2.utils.reflect.analyze.AbstractFieldsAnalyzer;
import gr.interamerican.bo2.utils.reflect.analyze.TypeAnalysis;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 
 */
public class PoChildrenAnalyzer 
extends AbstractFieldsAnalyzer {

	@Override
	protected List<Field> fieldsToInclude(Class<?> clazz) {
		TypeAnalysis analysis = TypeAnalysis.analyze(clazz);
		List<Field> fields = new ArrayList<Field>();				
		Set<Field> children = analysis.getAnnotated(Child.class);
		if (children!=null) {
			fields.addAll(children);			
		}		
		return fields;
	}
	
	

}
