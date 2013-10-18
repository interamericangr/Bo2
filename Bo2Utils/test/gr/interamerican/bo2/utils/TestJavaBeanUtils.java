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

import static gr.interamerican.bo2.utils.JavaBeanUtils.copyProperties;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gr.interamerican.bo2.samples.BeanWithNoFieldGetter;
import gr.interamerican.bo2.samples.Child;
import gr.interamerican.bo2.samples.SampleBean2;
import gr.interamerican.bo2.samples.abstractimpl.SampleAbstractClass2;
import gr.interamerican.bo2.samples.bean.BeanWith1Field;
import gr.interamerican.bo2.samples.bean.BeanWith2Fields;
import gr.interamerican.bo2.samples.bean.BeanWith3Fields;
import gr.interamerican.bo2.samples.bean.BeanWithDate;
import gr.interamerican.bo2.samples.bean.BeanWithNestedBean;
import gr.interamerican.bo2.samples.ibean.SubSampleInterface;
import gr.interamerican.bo2.samples.ibean.SuperSampleInterface;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 */
public class TestJavaBeanUtils {
	
	/**
	 * Unit test for getNestedProperty()
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetNestedProperty() {
		String s = "a";
		Integer i = 1;
		BeanWithNestedBean bean = new BeanWithNestedBean(s, i);
		/*
		 * simple property
		 */
		Object value = JavaBeanUtils.getNestedProperty(bean, "property1");
		assertEquals(value, BeanWithNestedBean.PROPERTY1);
		/*
		 * composite properties.
		 */
		value = JavaBeanUtils.getNestedProperty(bean, "nested.field1");
		assertEquals(value, s);
		value = JavaBeanUtils.getNestedProperty(bean, "nested.field2");
		assertEquals(value, i);
	}
	
	/**
	 * Unit test for getNestedProperty()
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetNestedPropertyNullTolerant() {
		String s = "a";
		Integer i = 1;
		BeanWithNestedBean bean = new BeanWithNestedBean(s, i);
		bean.setNested(null);
		
		Object value = JavaBeanUtils.getNestedPropertyNullTolerant(bean, "nested.field1");
		assertNull(value);
		
		bean.setProperty1(2);
		Object property1 = JavaBeanUtils.getNestedPropertyNullTolerant(bean, "property1");
		assertEquals(bean.getProperty1(), property1);
	}
	
	/**
	 * Unit test for getProperty.
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@Test
	public void testGetProperty() 
	throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		BeanWith2Fields bean = new BeanWith2Fields();
		PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(bean, "field1"); //$NON-NLS-1$
		String value = "string"; //$NON-NLS-1$
		bean.setField1(value);
		String actual = (String) JavaBeanUtils.getProperty(pd, bean);
		assertEquals(value, actual);
	}
		
	/**
	 * Unit test for getProperty.
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@Test
	public void testGetProperty_noGetter() 
	throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		BeanWithNoFieldGetter bean = new BeanWithNoFieldGetter();
		String value = "hello"; //$NON-NLS-1$
		bean.setField1(value);
		PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(bean, "field1"); //$NON-NLS-1$
		String actual = (String) JavaBeanUtils.getProperty(pd, bean);
		assertEquals(value,actual);
	}
	
	/**
	 * Unit test for setProperty.
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@Test
	public void testSetProperty() 
	throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		BeanWith2Fields bean = new BeanWith2Fields();
		PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(bean, "field1"); //$NON-NLS-1$
		String value = "string"; //$NON-NLS-1$
		JavaBeanUtils.setProperty(pd, value, bean);
		assertEquals(value, bean.getField1());
	}
	
	/**
	 * Unit test for setProperty with java.util.Date.
	 * 
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@Test
	public void testSetProperty_date() 
	throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		BeanWithDate bean = new BeanWithDate();
		Date dt = new Date();
		DateUtils.removeTime(dt);
		
		String string = DateUtils.formatDateIso(dt);
		JavaBeanUtils.setProperty(PropertyUtils.getPropertyDescriptor(bean, "date"), string, bean); //$NON-NLS-1$
		Assert.assertEquals(dt, bean.getDate());
	}
	
	/**
	 * Unit test for setProperty with java.util.Date.
	 * 
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@Test
	public void testSetProperty_double() 
	throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		BeanWith3Fields bean = new BeanWith3Fields();
		Double d = 2.1;
		String string = "2.1"; //$NON-NLS-1$
		
		JavaBeanUtils.setProperty(PropertyUtils.getPropertyDescriptor(bean, "field3"), string, bean); //$NON-NLS-1$
		Assert.assertEquals(d, bean.getField3());
	}
	
	/**
	 * Unit test for setProperty.
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@Test
	public void testSetPropertyTyped() 
	throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		BeanWith2Fields bean = new BeanWith2Fields();
		PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(bean, "field2");  //$NON-NLS-1$
		Integer value = 88; 
		JavaBeanUtils.setPropertyTyped(pd, value, bean);
		assertEquals(value, bean.getField2());
	}
	
	/**
	 * Unit test for setProperty.
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@Test
	public void testSetPropertyUntyped() 
	throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		BeanWith2Fields bean = new BeanWith2Fields();
		PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(bean, "field2");  //$NON-NLS-1$
		String value = "88";  //$NON-NLS-1$
		JavaBeanUtils.setPropertyUntyped(pd, value, bean);
		assertEquals(Integer.valueOf(88), bean.getField2());
	}
	
	/**
	 * Unit test for setProperty.
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@Test
	public void testIsValidValue() 
	throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		BeanWith2Fields bean = new BeanWith2Fields();
		PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(bean, "field2");  //$NON-NLS-1$		
		Assert.assertFalse(JavaBeanUtils.isValidValue(pd, "X")); //$NON-NLS-1$
		Assert.assertTrue(JavaBeanUtils.isValidValue(pd, 12));
	}
	
	
	
	/**
	 * tests Utils.testCopyProperties()
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCopyProperties_SamePropertyName_differentTypes() {
		BeanWith2Fields b1 = new BeanWith2Fields();
		b1.setField1("papaki");
		b1.setField2(4);		
		/* copy two properties */
		SampleBean2 b3 = new SampleBean2();
		String[] prop2 = {"field1", "field2"};
		copyProperties(b1, b3, prop2);
		assertEquals(b1.getField1(), b3.getField1());
		assertFalse((b1.getField2().equals(b3.getField2())));
		
		/* copy all properties */
		SampleBean2 b4 = new SampleBean2();
		String[] prop3 = {};
		copyProperties(b1, b4, prop3);
		assertEquals(b1.getField1(), b4.getField1());
		assertFalse((b1.getField2().equals(b4.getField2())));
		

		/* copy all properties with null array */
		SampleBean2 b5 = new SampleBean2();		
		copyProperties(b1, b5, null);
		assertEquals(b1.getField1(), b5.getField1());
		assertFalse((b1.getField2().equals(b5.getField2())));
	}
		
	/**
	 * Unit test for getPropertyDescriptor.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetPropertyDescriptor() {
		
		//class
		PropertyDescriptor[] props = JavaBeanUtils.getBeansProperties(BeanWith2Fields.class);
		for (PropertyDescriptor p : props) {
			PropertyDescriptor pd = JavaBeanUtils.getPropertyDescriptor(BeanWith2Fields.class, p.getName());
			assertNotNull(pd);
			assertEquals(pd.getName(), p.getName());
			assertEquals(pd.getPropertyType(), p.getPropertyType());
		}
		
		//class with inheritance
		props = JavaBeanUtils.getBeansProperties(Child.class);
		for (PropertyDescriptor p : props) {
			PropertyDescriptor pd = JavaBeanUtils.getPropertyDescriptor(Child.class, p.getName());
			assertNotNull(pd);
			assertEquals(pd.getName(), p.getName());
			assertEquals(pd.getPropertyType(), p.getPropertyType());
		}
		
		//abstract class
		PropertyDescriptor pd1 = JavaBeanUtils.getPropertyDescriptor(SampleAbstractClass2.class, "field1"); //$NON-NLS-1$
		assertNull(pd1);
		
		//interface
		props = JavaBeanUtils.getBeansProperties(SuperSampleInterface.class);
		for (PropertyDescriptor p : props) {
			PropertyDescriptor pd = JavaBeanUtils.getPropertyDescriptor(SuperSampleInterface.class, p.getName());
			assertNotNull(pd);
			assertEquals(pd.getName(), p.getName());
			assertEquals(pd.getPropertyType(), p.getPropertyType());
		}
		
		//interface with inheritance
		props = JavaBeanUtils.getBeansProperties(SubSampleInterface.class);
		for (PropertyDescriptor p : props) {
			PropertyDescriptor pd = JavaBeanUtils.getPropertyDescriptor(SubSampleInterface.class, p.getName());
			assertNotNull(pd);
			assertEquals(pd.getName(), p.getName());
			assertEquals(pd.getPropertyType(), p.getPropertyType());
		}
		
		//nested property
		PropertyDescriptor nestedPd = JavaBeanUtils.getPropertyDescriptor(BeanWithNestedBean.class, "nested.field1");
		PropertyDescriptor directPd = JavaBeanUtils.getPropertyDescriptor(BeanWith2Fields.class, "field1");
		assertNotNull(nestedPd);
		assertNotNull(directPd);
		assertEquals(nestedPd.getName(), directPd.getName());
		assertEquals(nestedPd.getPropertyType(), directPd.getPropertyType());

	}
	
	/**
	 * Unit test for setAsMapUsingPropertyAsKey.
	 */
	@Test
	public void testSetAsMapUsingPropertyAsKey() {
		BeanWith2Fields sb1 = new BeanWith2Fields();
		sb1.setField2(1);
		Set<BeanWith2Fields> set = new HashSet<BeanWith2Fields>();
		for (int i=0; i<10; i++) {
			BeanWith2Fields sb = new BeanWith2Fields();
			sb.setField2(i);
			set.add(sb);
		}
		PropertyDescriptor property = JavaBeanUtils.getPropertyDescriptor(BeanWith2Fields.class, "field2"); //$NON-NLS-1$
		Map<Integer, BeanWith2Fields> map = JavaBeanUtils.setAsMapUsingPropertyAsKey(set, property);
		for (int i=0; i<10; i++) {
			assertEquals(map.get(i).getField2(), new Integer(i));
		}
		assertEquals(map.size(), 10);
	}
	
	
	/**
	 * tests Utils.testCopyProperties() when the source and
	 * target properties are {@link Number} subtypes but not
	 * the same class.
	 */
	@Test	
	public void testCopyProperties_Numbers() {
		BeanWith2Fields b1 = new BeanWith2Fields();
		b1.setField2(4);
		BeanWith1Field b2 = new BeanWith1Field();
		JavaBeanUtils.copyProperties(b1, b2);
		
		assertEquals(new Long(4), b2.getField2());
	}
	
	/**
	 * Utils.getBeansProperties()
	 * 
	 */
	@Test
	public void testGetBeansProperties() {
		//testing a simple class
		PropertyDescriptor[] pd = JavaBeanUtils.getBeansProperties(BeanWith2Fields.class);
		assertEquals(2, pd.length);
		Class<?>[] expecteds = {String.class, Integer.class};
		List<Class<?>> actualsList = new ArrayList<Class<?>>();
		for(int i=0; i<pd.length; i++)
			actualsList.add(pd[i].getPropertyType());
		Class<?>[] actuals = actualsList.toArray(new Class<?>[0]);
		assertArrayEquals(expecteds, actuals);
		
		//testing inheritance
		PropertyDescriptor[] pd2 = JavaBeanUtils.getBeansProperties(Child.class);
		assertEquals(2, pd2.length);
		//first Object.class is the field4 of the Father class
		Class<?>[] expecteds2 = {Object.class, Object.class};
		actualsList = new ArrayList<Class<?>>();
		for(int i=0; i<pd2.length; i++)
			actualsList.add(pd2[i].getPropertyType());
		Class<?>[] actuals2 = actualsList.toArray(new Class<?>[0]);
		assertArrayEquals(expecteds2, actuals2);
		
		//testing an abstract class
		PropertyDescriptor[] pd5 = JavaBeanUtils.getBeansProperties(SampleAbstractClass2.class);
		assertEquals(0, pd5.length);
		
		//testing an interface
		PropertyDescriptor[] pd3 = JavaBeanUtils.getBeansProperties(SuperSampleInterface.class);
		assertEquals(3, pd3.length);
		Class<?>[] expecteds3 = {String.class, Integer.class, Object.class};
		actualsList = new ArrayList<Class<?>>();
		for(int i=0; i<pd3.length; i++)
			actualsList.add(pd3[i].getPropertyType());
		Class<?>[] actuals3 = actualsList.toArray(new Class<?>[0]);
		assertArrayEquals(expecteds3, actuals3);
		
		//testing an interface with super-types
		PropertyDescriptor[] pd4 = JavaBeanUtils.getBeansProperties(SubSampleInterface.class);
		assertEquals(7, pd4.length);
		Class<?>[] expecteds4 = { String.class, String.class, Integer.class, Object.class, String.class, Integer.class, Object.class};
		actualsList = new ArrayList<Class<?>>();
		for(int i=0; i<pd4.length; i++)
			actualsList.add(pd4[i].getPropertyType());
		Class<?>[] actuals4 = actualsList.toArray(new Class<?>[0]);
		assertArrayEquals(expecteds4, actuals4);
		
	}
	


	/**
	 * Tests mandatoryProperty()
	 */
	@Test
	public void testMandatoryProperty_ok() {
		Child bean = new Child();
		PropertyDescriptor pd = JavaBeanUtils.mandatoryProperty("field6", bean); //$NON-NLS-1$
		assertNotNull(pd);
	}
	
	/**
	 * Tests mandatoryProperty() throwing runtime exception
	 */
	@Test(expected=RuntimeException.class)
	public void testMandatoryProperty_throwingException() {		
		JavaBeanUtils.mandatoryProperty("nonop", new Child()); //$NON-NLS-1$
	}

}
