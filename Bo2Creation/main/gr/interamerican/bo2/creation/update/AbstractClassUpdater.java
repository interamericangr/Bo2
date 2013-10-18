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
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

/**
 * Abstract base class for class a updater.
 */
public abstract class AbstractClassUpdater {
	
	/**
	 * Updates a type.
	 * @param typeToUpdate
	 * 
	 * @throws CannotCompileException 
	 * @throws NotFoundException 
	 * @throws ClassCreationException 
	 */
	public abstract void updateType(CtClass typeToUpdate) 
	throws CannotCompileException, NotFoundException, ClassCreationException;
	
	/**
	 * Updates a type.
	 * 
	 * @param typeToUpdate
	 * @param newName
	 * @return Returns the updatedType.
	 * @throws ClassCreationException 
	 */
	public Class<?> update(Class<?> typeToUpdate, String newName) 
	throws ClassCreationException {
 		ClassPool cp = Bo2Creation.getBo2ClassPool();
		cp.appendClassPath(new ClassClassPath(typeToUpdate)); //TODO should we keep this?
		
		try{	
			CtClass cc = cp.get(typeToUpdate.getName());
			updateType(cc);			
			cc.setName(newName);			
			Class<?> updatedType = cc.toClass();
			cc.defrost();
			return updatedType;			
		}catch (NotFoundException e) {
			@SuppressWarnings("nls")
			String message = StringUtils.concat(
					"Could not find in classpool class ",
					typeToUpdate.getName());
			throw new ClassCreationException(message,e);
		} catch (CannotCompileException e) {
			@SuppressWarnings("nls")
			String message = StringUtils.concat(
					"Failed to compile generated code to update class ",
					typeToUpdate.getName());
			throw new ClassCreationException(message,e);
		}
		
	}

}
