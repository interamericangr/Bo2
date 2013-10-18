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

import static gr.interamerican.bo2.utils.StringConstants.COMMA;
import gr.interamerican.bo2.arch.Key;
import gr.interamerican.bo2.creation.code.templatebean.Variables;
import gr.interamerican.bo2.creation.creators.ImplementorForInterfaces;
import gr.interamerican.bo2.creation.exception.ClassCreationException;
import gr.interamerican.bo2.creation.update.AddingMethodsClassUpdater;
import gr.interamerican.bo2.creation.util.CodeGenerationUtilities;
import gr.interamerican.bo2.impl.open.annotations.Bo2AnnoUtils;
import gr.interamerican.bo2.impl.open.creation.templatebean.PoCodeTemplates;
import gr.interamerican.bo2.impl.open.po.AbstractKey;
import gr.interamerican.bo2.utils.StringUtils;

import java.util.Map;

/**
 * TODO: write the real implementor.
 */
public class Impl4Interfaces extends ImplementorForInterfaces {
	/**
	 * Po templates.
	 */
	PoCodeTemplates poTemplates = new PoCodeTemplates();
	
	/**
	 * Checks if the type is key.
	 * 
	 * @return true if it is a key type.
	 */
	boolean isKey() {
		return Key.class.isAssignableFrom(analysis.getClazz());
	}
	
	
	@Override
	protected String supertype() {
		if (isKey()) {
			return AbstractKey.class.getCanonicalName();
		}
		/*
		 * Being ambitious!
		 * if PersistentObject.class.isAssignableFrom(clazz) return AbstractBasePo.class ???
		 */
		return null;
	}
	
	@Override
	protected void validatePossibleImplementation() throws ClassCreationException {
		if (!analysis.getClazz().isInterface()) {
			throw CodeGenerationUtilities.typeNotSupported(analysis.getClazz());
		}			
		if (analysis.isContainsOddProperties()) {
			throw cantSupportOddProperties();
		}
	}
	
	/**
	 * Support for Key.
	 * 
	 * 
	 * TODO: Fix this for AbstractKey.
	 * 
	 * @throws ClassCreationException 
	 */
	void supportKey() throws ClassCreationException {
		if (isKey()) {	
			String[] keyProperties = Bo2AnnoUtils.getKeyProperties(analysis.getClazz());
			if (keyProperties==null) {
				throw noKeyProperties();
			}
			String value = StringUtils.concatSeparated(COMMA, keyProperties);
			Map<String, String> map = 
				Variables.variablesForInitialization(analysis.getClazz(), "key", value); //$NON-NLS-1$
			String code = poTemplates.getAbstractKeyGetElements(map);
			String[] methods = {code};			
			AddingMethodsClassUpdater updater = new AddingMethodsClassUpdater(methods);			
			addUpdater(updater);
		}
	}
	
	
	@Override
	protected void supportType() throws ClassCreationException {
		super.supportType();
		if (isKey()) {
			supportKey();
		}
		
	}
	
	/**
	 * Creates a class creation exception.
	 * @return returns a class creation exception.
	 */
	ClassCreationException noKeyProperties() {
		@SuppressWarnings("nls")
		String msg = StringUtils.concat(
			"Interface ", analysis.getClazz().getName(), 
			" does not have the @KeyProperties annotations");
		return new ClassCreationException(msg);
			
	}
	
	
}
