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


import gr.interamerican.bo2.utils.ArrayUtils;
import gr.interamerican.bo2.utils.StreamUtils;

import java.io.IOException;
import java.util.Collection;

import org.junit.Test;

/**
 * Unit test for creation.
 */
public abstract class AbstractTestClass {
	
	/**
	 * Creates a new CreationTestBean object. 
	 * 
	 * @param className 
	 * @throws ClassNotFoundException 
	 *
	 */
	public AbstractTestClass(String className) throws ClassNotFoundException {
		super();
		this.clazz = Class.forName(className);
	}
	
	/**
	 * Class being tested.
	 */
	protected Class<?> clazz;
	
	/**
	 * Tests the creation of instances of clazz, where clazz 
	 * can be an interface.
	 */
	@Test
	public void test() {
		testClass(clazz);
	}
	
	/**
	 * Tests the creation of instances of clazz, where clazz 
	 * can be an interface.
	 * @param type 
	 */	
	public abstract void testClass(Class<?> type);

	/**
	 * Test parameters.
	 * 
	 * @param path
	 * 
	 * @return Returns the test parameters.
	 * @throws IOException
	 */
	public static Collection<?> parameters(String path) throws IOException {
		String[] classes = StreamUtils.readResourceFile(path, true, true);
		return ArrayUtils.arrayAsListOfArrays(classes);
	}
}
