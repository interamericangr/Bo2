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
package gr.interamerican.bo2.utils.reflect.beans;

import gr.interamerican.bo2.samples.bean.IBeanWithIdAndNameImpl;
import gr.interamerican.bo2.samples.ibean.IBeanWithIdAndName;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.enums.AccessType;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link BeanPropertyDefinition}.
 */
@SuppressWarnings("nls")
public class TestBeanPropertyDefinition {
	
	
	/**
	 * Tests the constructor.
	 */
	
	@Test
	public void testConstructor() {
		Class<?> clazz = IBeanWithIdAndName.class;
		Method getter = ReflectionUtils.getMethodByUniqueName("getBeanName", clazz);
		Type genericType = getter.getGenericReturnType();		
		BeanPropertyDefinition<String> def = new BeanPropertyDefinition<String>
			("beanName", String.class, genericType, clazz, getter, null);
		Assert.assertEquals("beanName", def.getName());
		Assert.assertEquals(clazz, def.getBeanClass());
		Assert.assertEquals(AccessType.READ_ONLY, def.getAccessType());
		Assert.assertEquals(genericType, def.getGenericType());
	}
	
	/**
	 * Tests getAccessType().
	 */
	@Test
	public void testGetAccessType() {
		Class<?> clazz = IBeanWithIdAndName.class;
		Method getter = ReflectionUtils.getMethodByUniqueName("getBeanName", clazz);
		Method setter = ReflectionUtils.getMethodByUniqueName("setBeanName", clazz);
		
		Type genericType = getter.getGenericReturnType();
		
		BeanPropertyDefinition<String> def = new BeanPropertyDefinition<String>
			("beanName", String.class, genericType, clazz, null, null);
		Assert.assertNull(def.getAccessType());
		
		def.setAccessType(AccessType.READ_ONLY);
		Assert.assertEquals(AccessType.READ_ONLY, def.getAccessType());
				
		def.setGetter(getter);
		def.setSetter(setter);
		Assert.assertEquals(AccessType.READ_WRITE, def.getAccessType());
	}
	
	/**
	 * Tests isAbstractGetter().
	 */
	@Test
	public void testIsAbstractGetter_returnTrue() {
		Class<?> clazz = IBeanWithIdAndName.class;		
		Method getter = ReflectionUtils.getMethodByUniqueName("getBeanName", clazz);
		Method setter = ReflectionUtils.getMethodByUniqueName("setBeanName", clazz);
		Type genericType = getter.getGenericReturnType();
		BeanPropertyDefinition<String> def = new BeanPropertyDefinition<String>
			("beanName", String.class, genericType, clazz, getter, setter);	
		
		Assert.assertTrue(def.isAbstractGetter());
	}
	
	/**
	 * Tests isAbstractSetter().
	 */
	@Test
	public void testIsAbstractSetter_returnTrue() {
		Class<?> clazz = IBeanWithIdAndName.class;		
		Method getter = ReflectionUtils.getMethodByUniqueName("getBeanName", clazz);
		Method setter = ReflectionUtils.getMethodByUniqueName("setBeanName", clazz);
		Type genericType = getter.getGenericReturnType();
		BeanPropertyDefinition<String> def = new BeanPropertyDefinition<String>
			("beanName", String.class, genericType, clazz, getter, setter);	
		
		Assert.assertTrue(def.isAbstractSetter());
	}
	
	/**
	 * Tests isAbstractGetter().
	 */
	@Test
	public void testIsAbstractGetter_returnFalse() {
		Class<?> clazz = IBeanWithIdAndNameImpl.class;		
		Method getter = ReflectionUtils.getMethodByUniqueName("getBeanName", clazz);
		Method setter = ReflectionUtils.getMethodByUniqueName("setBeanName", clazz);
		Type genericType = getter.getGenericReturnType();
		BeanPropertyDefinition<String> def = new BeanPropertyDefinition<String>
			("beanName", String.class, genericType, clazz, getter, setter);	
		Assert.assertFalse(def.isAbstractGetter());
	}
	
	/**
	 * Tests isAbstractSetter().
	 */
	@Test
	public void testIsAbstractSetter_returnFalse() {
		Class<?> clazz = IBeanWithIdAndNameImpl.class;		
		Method getter = ReflectionUtils.getMethodByUniqueName("getBeanName", clazz);
		Method setter = ReflectionUtils.getMethodByUniqueName("setBeanName", clazz);
		Type genericType = getter.getGenericReturnType();
		BeanPropertyDefinition<String> def = new BeanPropertyDefinition<String>
			("beanName", String.class, genericType, clazz, getter, setter);		
		Assert.assertFalse(def.isAbstractSetter());
	}
	
	

}
