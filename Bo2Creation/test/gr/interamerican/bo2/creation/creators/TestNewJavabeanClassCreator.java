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

import gr.interamerican.bo2.creation.exception.ClassCreationException;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.reflect.beans.VariableDefinition;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 */
public class TestNewJavabeanClassCreator {
	
	/**
	 * Unit test for the constructor.
	 */
	@Test
	@SuppressWarnings({"nls","rawtypes"})
	public void testConstructor() {
		String name = "com.foo.Javabean1";		
		VariableDefinition[] vd = new VariableDefinition[0];
		NewJavabeanClassCreator creator = new NewJavabeanClassCreator(name, vd);
		Assert.assertEquals(name, creator.className);
		Assert.assertArrayEquals(vd, creator.properties);
	}
	
	/**
	 * Unit test for the constructor.
	 * @throws ClassCreationException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@Test
	@SuppressWarnings({"nls","rawtypes"})
	public void testCreate() 
	throws ClassCreationException, InstantiationException, IllegalAccessException {
		String classname = "com.foo.Javabean2";	
		String field0 = "field1";
		String value0 = "value of field1";
		String field1 = "field2";
		int value1 = 0;
		
		VariableDefinition vd0 = new VariableDefinition<String>(field0,String.class);
		VariableDefinition vd1 = new VariableDefinition<Integer>(field1,int.class);
		VariableDefinition[] vd = {vd0, vd1};
		NewJavabeanClassCreator creator = new NewJavabeanClassCreator(classname, vd);
		Class<?> clazz = creator.create();
		Assert.assertNotNull(clazz);
		Object o = clazz.newInstance();
		ReflectionUtils.setProperty(field0, value0, o);
		String actual0 = (String) ReflectionUtils.getProperty(field0, o);
		Assert.assertEquals(value0, actual0);
		ReflectionUtils.setProperty(field1, value1, o);
		Integer actual1 = (Integer) ReflectionUtils.getProperty(field1, o);
		Assert.assertEquals(value1, actual1.intValue());

	}

}
