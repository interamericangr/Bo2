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

import gr.interamerican.bo2.creation.ClassCreator;
import gr.interamerican.bo2.creation.exception.ClassCreationException;
import gr.interamerican.bo2.creation.util.CodeGenerationUtilities;
import gr.interamerican.bo2.utils.ReflectionUtils;

/**
 * This class imitates a class creator by returning the arguments 
 * as results of its methods. <br/>
 * 
 * This implementation is suitable for use by production object
 * factories in order to deal with concrete classes.
 */
public class CreatorForConcreteClasses 
implements ClassCreator {

	
	public Class<?> create(Class<?> type) throws ClassCreationException {	
		if (!ReflectionUtils.isConcreteClass(type)) {
			throw CodeGenerationUtilities.typeNotSupported(type);
		}
		return type;
	}
	
	public String compileTimeClassName(String runTimeName) {
		return runTimeName;
	}

	public String runTimeClassName(String compileTimeName) {
		return compileTimeName;
	}
	
	

}
