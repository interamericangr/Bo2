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
package gr.interamerican.bo2.creation.update;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtNewConstructor;
import javassist.NotFoundException;

/**
 * Updates a type by adding code at the end of its default constructor.
 */
public class SettingDefaultConstructorClassUpdater 
extends AbstractClassUpdater {
	
	/**
	 * new code containing constructor.
	 */
	private String constructorCode;

	/**
	 * Creates a new SetDefaultConstructorClassUpdater object. 
	 *
	 * @param constructorCode
	 */
	public SettingDefaultConstructorClassUpdater(String constructorCode) {
		super();
		this.constructorCode = constructorCode;
	}
	
	@Override
	public void updateType(CtClass typeToUpdate) 
	throws CannotCompileException, NotFoundException {			
		CtConstructor defaultConstructor;
		try {
			defaultConstructor = typeToUpdate.getConstructor("()V"); //$NON-NLS-1$
		} catch (NotFoundException e) {
			defaultConstructor = null;
		}
		if (defaultConstructor!=null) {
			typeToUpdate.removeConstructor(defaultConstructor);
		}		
		defaultConstructor = CtNewConstructor.defaultConstructor(typeToUpdate);
		defaultConstructor.setBody(constructorCode);
		typeToUpdate.addConstructor(defaultConstructor);		
	}

}
