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
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

/**
 * Updates a type by adding fields to it.
 */
public class AddingInterfacesClassUpdater 
extends AbstractClassUpdater {
	
	/**
	 * interfaces
	 */
	private String[] interfaceNames;
	
	/**
	 * Creates a new AddingFieldsClassUpdater object. 
	 *
	 * @param interfaceNames
	 *        Names of the interfaces to add to the class.
	 */
	public AddingInterfacesClassUpdater(String... interfaceNames) {
		super();
		this.interfaceNames = interfaceNames;
	}
	
	@Override
	public void updateType(CtClass typeToUpdate) 
	throws CannotCompileException, NotFoundException, ClassCreationException {
		ClassPool cp = Bo2Creation.getBo2ClassPool();
		for(String interfaceName : interfaceNames) {			
			CtClass interfaceToAdd = cp.get(interfaceName);
			if (!interfaceToAdd.isInterface()) {
				@SuppressWarnings("nls")
				String msg = StringUtils.concat(interfaceName, " is not an interface");
				throw new ClassCreationException(msg);
			}
			typeToUpdate.addInterface(interfaceToAdd);			
		}
	}
}
