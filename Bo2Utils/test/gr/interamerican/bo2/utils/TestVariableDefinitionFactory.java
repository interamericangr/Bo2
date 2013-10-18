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
package gr.interamerican.bo2.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import gr.interamerican.bo2.samples.bean.BeanWith2Fields;
import gr.interamerican.bo2.utils.reflect.beans.VariableDefinition;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

import org.junit.Test;

/**
 * 
 */
public class TestVariableDefinitionFactory {
	
	/**
	 * Unit test for create with field.
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 */
	@Test
	public void testCreate_withField() throws SecurityException, NoSuchFieldException {
		
		Field field1 = BeanWith2Fields.class.getDeclaredField("field1"); //$NON-NLS-1$
		VariableDefinition<?> vd = VariableDefinitionFactory.create(field1);
		assertEquals(field1.getName(), vd.getName());
		assertEquals(field1.getType(), vd.getType());
	}
	
	/**
	 * Unit test for create with fields array.
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 */
	@Test
	public void testCreate_withFields() throws SecurityException, NoSuchFieldException {
		Field field1 = BeanWith2Fields.class.getDeclaredField("field1"); //$NON-NLS-1$
		Field[] fields = {field1};
		VariableDefinition<?>[] vds = VariableDefinitionFactory.create(fields);
		assertEquals(fields.length, vds.length);
		assertEquals(field1.getName(), vds[0].getName());
		assertEquals(field1.getType(), vds[0].getType());
	}
	
	/**
	 * Unit test for create with property.
	 * 
	 * @throws SecurityException 
	 */
	@Test
	public void testCreate_withProperty() throws SecurityException{
		PropertyDescriptor field1 = 
			JavaBeanUtils.getPropertyDescriptor(BeanWith2Fields.class, "field1");  //$NON-NLS-1$
		VariableDefinition<?> vd = VariableDefinitionFactory.create(field1);
		assertEquals(field1.getName(), vd.getName());
		assertEquals(field1.getPropertyType(), vd.getType());
	}
	
	/**
	 * Unit test for create with fields array.
	 * 
	 * @throws SecurityException 
	 */
	@Test
	public void testCreate_withProperties() throws SecurityException {
		PropertyDescriptor field1 = 
			JavaBeanUtils.getPropertyDescriptor(BeanWith2Fields.class, "field1");  //$NON-NLS-1$
		PropertyDescriptor[] fields = {field1};
		VariableDefinition<?>[] vds = VariableDefinitionFactory.create(fields);
		assertEquals(fields.length, vds.length);
		assertEquals(field1.getName(), vds[0].getName());
		assertEquals(field1.getPropertyType(), vds[0].getType());
	}
	
	
	/**
	 * test AsVariableDefinition
	 * @throws SecurityException
	 */
	@Test
	public void testAsVariableDefinition() throws SecurityException {
		VariableDefinition<?> v = VariableDefinitionFactory.asVariableDefinition(null);
		assertEquals(StringConstants.NULL,v.getName());
		assertNull(v.getType());
		
	}

}
