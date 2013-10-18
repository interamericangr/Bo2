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
package gr.interamerican.bo2.utils.reflect;

import static gr.interamerican.bo2.utils.reflect.AccessorUtils.getGetter;
import static gr.interamerican.bo2.utils.reflect.AccessorUtils.getSetter;
import static gr.interamerican.bo2.utils.reflect.AccessorUtils.isAssessorName;
import static gr.interamerican.bo2.utils.reflect.AccessorUtils.isGetter;
import static gr.interamerican.bo2.utils.reflect.AccessorUtils.isSetter;
import static gr.interamerican.bo2.utils.reflect.AccessorUtils.propertyName;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.samples.abstractimpl.AbstractSampleInterfaceImpl;
import gr.interamerican.bo2.samples.bean.BeanWith2Fields;
import gr.interamerican.bo2.samples.bean.BeanWithReadOnlyAndWriteOnly;
import gr.interamerican.bo2.samples.ibean.IBeanWithReadOnlyAndWriteOnly;
import gr.interamerican.bo2.utils.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.junit.Test;

/**
 * 
 */
public class TestAccessorUtils {
	
	/**
	 * Unit test for isImplemented
	 */
	@SuppressWarnings("nls")
	@Test
	public void testIsAccessorName() {		
		assertFalse(isAssessorName("get", "getme"));
		assertFalse(isAssessorName("get", "get"));
		assertFalse(isAssessorName("get", "get_me_out"));
		assertTrue(isAssessorName("get", "getA"));
		assertTrue(isAssessorName("get", "getAField"));
		assertTrue(isAssessorName("get", "getField"));
		assertFalse(isAssessorName("get", "init"));
	}
	
	/**
	 * Unit test for isImplemented
	 */
	@SuppressWarnings("nls")
	@Test
	public void testIsGetter() {
		Method m1 = ReflectionUtils.getPublicMethodWithoutParamsByName
			("getField1", BeanWith2Fields.class);
		assertTrue(isGetter(m1));
		
		Method m2 = ReflectionUtils.getPublicMethodWithoutParamsByName
			("isAccessible", BeanWithReadOnlyAndWriteOnly.class);
		assertTrue(isGetter(m2));
		
		Method m3 = ReflectionUtils.getPublicMethod
			("setAccessible", BeanWithReadOnlyAndWriteOnly.class, boolean.class);
		assertFalse(isGetter(m3));
		
		Method m4 = ReflectionUtils.getPublicMethod
			("setField1", BeanWith2Fields.class, String.class);
		assertFalse(isGetter(m4));
		
		Method m5 = ReflectionUtils.getPublicMethodWithoutParamsByName
			("someMethod", AbstractSampleInterfaceImpl.class);
		assertFalse(isGetter(m5));
	
	}
	
	/**
	 * Unit test for isImplemented
	 */
	@SuppressWarnings("nls")
	@Test
	public void testIsSetter() {
		Method m1 = ReflectionUtils.getPublicMethodWithoutParamsByName
			("getField1", BeanWith2Fields.class);
		assertFalse(isSetter(m1));
		
		Method m2 = ReflectionUtils.getPublicMethodWithoutParamsByName
			("isAccessible", BeanWithReadOnlyAndWriteOnly.class);
		assertFalse(isSetter(m2));
		
		Method m3 = ReflectionUtils.getPublicMethod
			("setAccessible", BeanWithReadOnlyAndWriteOnly.class, boolean.class);
		assertFalse(isSetter(m3));
		
		Method m4 = ReflectionUtils.getPublicMethod
			("setField1", BeanWith2Fields.class, String.class);
		assertTrue(isSetter(m4));
		
		Method m5 = ReflectionUtils.getPublicMethodWithoutParamsByName
			("someMethod", AbstractSampleInterfaceImpl.class);
		assertFalse(isSetter(m5));
	
	}
	
	
	/**
	 * tests getGetter(field,class)
	 */
	@Test
	public void testGetGetter_ofField(){		
		List<Field> fields = ReflectionUtils.allFields(BeanWith2Fields.class, BeanWith2Fields.class);
		assertNotNull(getGetter(fields.get(1), BeanWith2Fields.class));
	}
	
	
	/**
	 * tests getGetter(string,class)
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetGetter(){
		assertNotNull(getGetter("field1", String.class, BeanWith2Fields.class));
		assertNotNull(getGetter("id", Long.class, BeanWithReadOnlyAndWriteOnly.class));
		assertNull(getGetter("id", Integer.class, BeanWithReadOnlyAndWriteOnly.class));
		assertNull(getGetter("state", String.class, BeanWithReadOnlyAndWriteOnly.class));
		assertNotNull(getGetter("accessible", boolean.class, BeanWithReadOnlyAndWriteOnly.class));
	}
	
	/**
	 * tests getSetter
	 */
	@Test
	public void testGetSetter_ofField(){
		List<Field> fields = ReflectionUtils.allFields(BeanWith2Fields.class, BeanWith2Fields.class);
		assertNotNull(getSetter(fields.get(1), BeanWith2Fields.class));		
	}
	
	/**
	 * tests getGetter(string,class)
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetSetter(){
		assertNotNull(getSetter("field1", String.class, BeanWith2Fields.class));
		assertNotNull(getSetter("id", Long.class, BeanWithReadOnlyAndWriteOnly.class));
		assertNull(getSetter("id", Integer.class, BeanWithReadOnlyAndWriteOnly.class));
		assertNotNull(getSetter("state", String.class, BeanWithReadOnlyAndWriteOnly.class));
		assertNull(getSetter("accessible", boolean.class, BeanWithReadOnlyAndWriteOnly.class));
		assertNull(getSetter("name", String.class, IBeanWithReadOnlyAndWriteOnly.class));
		assertNotNull(getSetter("name", String.class, BeanWithReadOnlyAndWriteOnly.class));
	}
	
	
	/**
	 * tests getGetter(string,class)
	 */
	@SuppressWarnings("nls")
	@Test
	public void testPropertyName_set(){
		String property = "field1";
		Method setter = getSetter(property, String.class, BeanWith2Fields.class); 
		String actual = propertyName(setter);
		assertEquals(property, actual);
	}
	
	/**
	 * tests getGetter(string,class)
	 */
	@SuppressWarnings("nls")
	@Test
	public void testPropertyName_get(){
		String property = "field1";
		Method getter = getGetter(property, String.class, BeanWith2Fields.class); 
		String actual = propertyName(getter);
		assertEquals(property, actual);		
	}
	
	/**
	 * tests getGetter(string,class)
	 */
	@SuppressWarnings("nls")
	@Test
	public void testPropertyName_is(){		
		String property = "accessible";
		Method getter = getGetter(property, boolean.class, BeanWithReadOnlyAndWriteOnly.class); 
		String actual = propertyName(getter);
		assertEquals(property, actual);
		
		
	}
	
	
	

}
