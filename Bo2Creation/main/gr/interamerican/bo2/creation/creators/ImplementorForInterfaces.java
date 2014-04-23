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
import gr.interamerican.bo2.creation.util.CodeGenerationUtilities;
import gr.interamerican.bo2.utils.reflect.beans.BeanPropertyDefinition;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * Basic class composer.
 * 
 * This composer can successfully create an implementation of
 * an interface only if the interface is a java bean interface.
 * If the interface has more methods than accessors, the method
 * will fail.
 * 
 */
public class ImplementorForInterfaces 
extends AbstractClassCreator {
	
	
	@Override
	protected boolean mustImplementSerializable() {	
		return true;
	}
	
	
	
	/**
	 * Handles the properties of the interface.
	 */
	@Override
	protected void supportProperties() {
		PropertyWithDirectAccessCodeTemplates templates = directProperty;
		Set<BeanPropertyDefinition<?>> properties = analysis.getAllProperties();
		for (BeanPropertyDefinition<?> bpd : properties) {
			String name = bpd.getName();
			Class<?> type = bpd.getType();
			Map<String, String> vars = Variables.variablesForProperty(name, type, name, type);
			addFieldCode(templates.getFieldDeclarationCode(vars)); //TODO: initialize field?
			Method getter = bpd.getGetter(); 
			if (getter!=null) {				
				String code = templates.getPropertyGetter(vars,getter.getName());
				implementMethod(getter, code);
			}
			Method setter = bpd.getSetter();
			if (setter!=null) {
				String code = templates.getPropertySetter(vars);
				implementMethod(setter, code);
			}
		}
	}
	
	
	
	
	@Override
	protected String getSuffix() {
		return "_InterfaceImplementationBybo2"; //$NON-NLS-1$
	}
	
	@Override
	protected void validatePossibleImplementation() throws ClassCreationException {
		if (!analysis.getClazz().isInterface()) {
			throw CodeGenerationUtilities.typeNotSupported(analysis.getClazz());
		}
		if (analysis.isContainsAbstractMethods()) {
			throw cantImplementAllMethods(analysis.getAbstractMethods());
		}		
		if (analysis.isContainsOddProperties() && !canSupportOddProperties()) {
			throw cantSupportOddProperties();
		}
	}
	
	
	@Override
	protected String[] interfaces() {	
		String[] types = {
				analysis.getClazz().getCanonicalName(),
				Serializable.class.getCanonicalName(),				
		};
		return types;
	}
	
	@Override
	protected void supportMethods() {
		/* do nothing */
	}
	
	
	
	

}
