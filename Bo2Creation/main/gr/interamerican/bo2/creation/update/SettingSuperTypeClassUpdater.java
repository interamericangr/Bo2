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

import gr.interamerican.bo2.creation.Bo2Creation;
import gr.interamerican.bo2.creation.exception.ClassCreationException;
import gr.interamerican.bo2.utils.StringUtils;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtNewConstructor;
import javassist.NotFoundException;

/**
 * Updates a type by setting its super type.
 * 
 * This updater will add to this class a constructor for each public 
 * constructor of the super type. If the class being updated already has 
 * a constructor with the same argument types, then this constructor will
 * be replaced by a constructor that will just call the super constructor
 * with the same arguments.
 */
public class SettingSuperTypeClassUpdater 
extends AbstractClassUpdater {
	
	/**
	 * super type.
	 */
	private String superType;
	
	/**
	 * Creates a new AddingFieldsClassUpdater object. 
	 *
	 * @param superType
	 *        Names of the super type.
	 */
	public SettingSuperTypeClassUpdater(String superType) {
		super();
		this.superType = superType;
	}
	
	@Override
	public void updateType(CtClass typeToUpdate) 
	throws CannotCompileException, NotFoundException, ClassCreationException {

		try {
			Class<?> superClazz = Class.forName(superType);
			if(Object.class!=superClazz) {
				Bo2Creation.appendClassPath(superClazz);
			}
		} catch (ClassNotFoundException e1) {
			throw new ClassCreationException(e1);
		}
		
		CtClass superClass = Bo2Creation.get(superType);
		if (superClass.isInterface()) {
			@SuppressWarnings("nls")
			String msg = StringUtils.concat(superType, " is an interface");
			throw new ClassCreationException(msg);			
		}
		typeToUpdate.setSuperclass(superClass);		
		
		CtConstructor[] superConstructors = superClass.getConstructors();
		for (CtConstructor superConstructor : superConstructors) {
			CtClass[] args = superConstructor.getParameterTypes();
			
			CtConstructor constructor;
			try {
				constructor = typeToUpdate.getDeclaredConstructor(args);
			} catch (NotFoundException e) {
				constructor = null;
			}
			if (constructor!=null) {
				typeToUpdate.removeConstructor(constructor);
			}			
			CtClass[] exceptions = superConstructor.getExceptionTypes();
			constructor = CtNewConstructor.make(args, exceptions, typeToUpdate);
			typeToUpdate.addConstructor(constructor);
		}
	}
}
