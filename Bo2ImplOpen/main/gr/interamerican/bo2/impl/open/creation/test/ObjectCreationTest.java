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
package gr.interamerican.bo2.impl.open.creation.test;

import static org.junit.Assert.assertNotNull;
import gr.interamerican.bo2.impl.open.creation.Factory;

/**
 * Unit test for creation.
 */
public class ObjectCreationTest 
extends AbstractTestClass {
	
	/**
	 * Creates a new CreationTestBean object. 
	 * 
	 * @param className
	 *  
	 * @throws ClassNotFoundException
	 */
	public ObjectCreationTest(String className) throws ClassNotFoundException {
		super(className);
	}
	
	@Override
	public void testClass(Class<?> type) {
		Object t = Factory.create(type);
		assertNotNull(type.toString(), t);		
	}
	
	
	

}
