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
package gr.interamerican.bo2.creation.creators;

import gr.interamerican.bo2.creation.code.templatebean.PropertyWithDirectAccessCodeTemplates;
import gr.interamerican.bo2.creation.code.templatebean.Variables;
import gr.interamerican.bo2.creation.exception.ClassCreationException;
import gr.interamerican.bo2.creation.update.AddingFieldsClassUpdater;
import gr.interamerican.bo2.creation.update.AddingInterfacesClassUpdater;
import gr.interamerican.bo2.creation.update.AddingMethodsClassUpdater;
import gr.interamerican.bo2.creation.update.ClassCompiler;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.reflect.beans.VariableDefinition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Creates a new Javabean type.
 */
public class NewJavabeanClassCreator {
	
	/**
	 * Name of new class.
	 */
	String className;
	
	/**
	 * Properties of new class.
	 */
	VariableDefinition<?>[] properties;
	
	/**
	 * Templates.
	 */
	private PropertyWithDirectAccessCodeTemplates templates =
		new PropertyWithDirectAccessCodeTemplates();

	/**
	 * Creates a new NewJavabeanClassCreator object. 
	 *
	 * @param className
	 * @param properties
	 */
	public NewJavabeanClassCreator
	(String className, VariableDefinition<?>[] properties) {
		super();
		this.className = className;
		this.properties = properties;
	}
	
	/**
	 * Creates a new type.
	 * 
	 * @return Returns the new type.
	 * 
	 * @throws ClassCreationException
	 */
	public Class<?> create() throws ClassCreationException {
		List<String> fields = new ArrayList<String>();
		List<String> methods = new ArrayList<String>();
		
		for (VariableDefinition<?> bpd : properties) {
			String name = bpd.getName();
			Class<?> type = bpd.getType();
			Map<String, String> vars = Variables.variablesForProperty(name, type, name, type);
			String fieldCode = templates.getFieldDeclarationCode(vars);
			fields.add(fieldCode);			
			String getterName = "get" + StringUtils.firstCapital(name); //$NON-NLS-1$
			String getter = templates.getPropertyGetter(vars,getterName);
			methods.add(getter);
			String setter = templates.getPropertySetter(vars);
			methods.add(setter);
		}
		AddingFieldsClassUpdater addFields = 
			new AddingFieldsClassUpdater(fields.toArray(new String[0]));
		AddingMethodsClassUpdater addMethods = 
			new AddingMethodsClassUpdater(methods.toArray(new String[0]));
		AddingInterfacesClassUpdater setSerializable = 
			new AddingInterfacesClassUpdater(Serializable.class.getName());
		ClassCompiler compiler = new ClassCompiler(addFields, addMethods, setSerializable);
		return compiler.createType(className);
	}
	
	
	

}
