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

import java.io.IOException;
import java.util.Collection;

import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.creation.test.conditions.ClassIsInterface;

/**
 * Unit test for creation.
 */
public class ObjectCreationTest 
extends AbstractCreationTest {
	
	/**
	 * Creates a new CreationTestBean object. 
	 *
	 * @param className the class name
	 * @throws ClassNotFoundException the class not found exception
	 */
	public ObjectCreationTest(String className) throws ClassNotFoundException {
		super(className);
	}
	
	@Override
	public void testClass(Class<?> type) {
		Object t = Factory.create(type);
		assertNotNull(type.toString(), t);		
	}

	/**
	 * Test parameters.
	 *
	 * @param path the path
	 * @param excluded the excluded
	 * @return Returns the test parameters.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected static Collection<?> parameters(String path, String excluded) throws IOException {
		return AbstractCreationTest.parameters(path, excluded, new ClassIsInterface());
	}
}
