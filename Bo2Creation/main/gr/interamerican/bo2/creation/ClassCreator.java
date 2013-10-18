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
package gr.interamerican.bo2.creation;

import gr.interamerican.bo2.creation.exception.ClassCreationException;

/**
 * {@link ClassCreator} creates concrete classes that
 * implement interfaces.
 */
public interface ClassCreator {
	
	/**
	 * Creates a new class that implements the interface of the specified
	 * type.
	 * 
	 * @param type
	 *        Type being instrumented.
	 *        
	 * @return Returns a class that implements the interfaces of the 
	 *         specified type.
	 *         
	 * @throws ClassCreationException 
	 */
	public Class<?> create(Class<?> type) throws ClassCreationException;
	
	/**
	 * Gets the name of the compile time class that was used by this
	 * ClassCreator for the creation of the runTimeClass.
	 * 
	 * @param runTimeName
	 * 
	 * @return Returns the name of the plain class.
	 */
	public String compileTimeClassName(String runTimeName);
	
	/**
	 * Gets the name of the compile time class that was used by this
	 * ClassCreator for the creation of the runTimeClass.
	 * 
	 * @param compileTimeName
	 * 
	 * @return Returns the name of the plain class.
	 */
	public String runTimeClassName(String compileTimeName);
	

}
