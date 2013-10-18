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
package gr.interamerican.bo2.creation.code.templatebean;


import static gr.interamerican.bo2.creation.util.CodeGenerationUtilities.cleanUpGenericFromType;
import gr.interamerican.bo2.creation.util.CodeGenerationUtilities;
import gr.interamerican.bo2.utils.reflect.beans.BeanPropertyDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class with the variable names.
 */
@SuppressWarnings("nls")
public class Variables {
	
	/**
	 * Hidden constructor.
	 */
	private Variables() { /* empty */ }
	/**
	 * name.
	 */	
	private static final String NAME = "name";
	/**
	 * name.
	 */	
	private static final String OTHERNAME = "othername";
	/**
	 * name.
	 */	
	private static final String OTHERTYPE = "Othertype";
	/**
	 * type.
	 */
	private static final String TYPE = "Type";
	/**
	 * FieldType
	 */
	private static final String FIELD_TYPE = "FieldType";
	/**
	 * DelegateField
	 */
	private static final String DELEGATE_FIELD = "DelegateField";	
	/**
	 * ReturnType
	 */
	private static final String RETURN_TYPE = "ReturnType";
	/**
	 * ReturnType
	 */
	private static final String METHOD_NAME = "MethodName";
	/**
	 * DeclarationParameters
	 */
	private static final String DECLARATION_PARAMETERS = "DeclarationParameters";
	/**
	 * InvocationParameters
	 */
	private static final String INVOCATION_PARAMETERS = "InvocationParameters";
	
	/**
	 * Value
	 */
	private static final String VALUE = "Value";
	/**
	 * Fragment
	 */
	private static final String FRAGMENT = "Fragment";
	
	
	/**
	 * Variables map for property, delegate property, mock property and
	 * delegate to other property.
	 * 
	 * @param name
	 * @param type
	 * @param delegateField 
	 * @param fieldType 
	 * 
	 * @return Returns the map.
	 */
	public static Map<String, String> variablesForProperty
	(String name, Class<?> type, String delegateField, Class<?> fieldType) {
		Map<String, String> variables = new HashMap<String, String>();
		variables.put(NAME, name);
		String typename = type.getCanonicalName();
		variables.put(TYPE, typename);		
		String fieldtypename = fieldType.getCanonicalName();
		variables.put(FIELD_TYPE, cleanUpGenericFromType(fieldtypename));
		variables.put(DELEGATE_FIELD, delegateField);
		variables.put(OTHERNAME, delegateField);
		variables.put(OTHERTYPE, fieldtypename);
		String defaultValue = CodeGenerationUtilities.getDefaultValueString(type);
		variables.put(VALUE, defaultValue);
		
		return variables;
	}
	
	/**
	 * Variables map for property.
	 * 
	 * @param def
	 * 
	 * @return Returns the map.
	 */
	public static Map<String, String> variablesForProperty (BeanPropertyDefinition<?> def) {
		return variablesForProperty(def.getName(), def.getType(), def.getName(), def.getType());
	}
	
	
	/**
	 * Variables map for method implementation.
	 * 
	 * @param methodName 
	 * @param returnType 
	 * @param declarationParams 
	 * 
	 * @return Returns the map.
	 */
	public static Map<String, String> variablesForEmptyMethod
	(String methodName, Class<?> returnType, String declarationParams) {
		Map<String, String> variables = new HashMap<String, String>();
		variables.put(METHOD_NAME, methodName);
		String typename = returnType.getCanonicalName();
		variables.put(RETURN_TYPE, cleanUpGenericFromType(typename));
		variables.put(DECLARATION_PARAMETERS, declarationParams);
		String defaultValue = CodeGenerationUtilities.getDefaultValueString(returnType);
		variables.put(VALUE, defaultValue);
		return variables;
	}
	
	/**
	 * Variables map for method implementation.
	 * 
	 * @param methodName 
	 * @param returnType 
	 * @param declarationParams 
	 * @param fieldType 
	 * @param delegateField 
	 * @param invocationParams 
	 * 
	 * @return Returns the map.
	 */
	public static Map<String, String> variablesForDelegateMethod (
			String methodName, Class<?> returnType, String declarationParams,
			Class<?> fieldType, String delegateField, String invocationParams) {
		Map<String, String> variables = 
			variablesForEmptyMethod(methodName, returnType, declarationParams);
		String fieldTypeName = fieldType.getCanonicalName();
		variables.put(FIELD_TYPE, cleanUpGenericFromType(fieldTypeName));
		variables.put(DELEGATE_FIELD, delegateField);
		variables.put(INVOCATION_PARAMETERS, invocationParams);
		return variables;
	}
	
	/**
	 * Variables map for initialization.
	 * 
	 * @param type
	 * @param name
	 * @param value
	 * 
	 * @return Returns the map.
	 */
	public static Map<String, String> variablesForInitialization
	(Class<?> type, String name, String value) {
		Map<String, String> variables = new HashMap<String, String>();
		variables.put(NAME, name);		
		variables.put(VALUE, value);
		if (type!=null) {
			String typeName = type.getCanonicalName();
			variables.put(TYPE, typeName);
		}		
		return variables;
	}
	

	/**
	 * Sets a fragment.
	 * 
	 * @param variables
	 * @param fragment
	 */
	public static void setFragment(Map<String, String> variables, String fragment) {
		variables.put(FRAGMENT, fragment);	
	}
	
	/**
	 * Sets a fragment.
	 * 
	 * @param variables
	 * @param type
	 */
	public static void setType(Map<String, String> variables, Class<?> type) {
		variables.put(TYPE, type.getCanonicalName());	
	}
	
	

}
