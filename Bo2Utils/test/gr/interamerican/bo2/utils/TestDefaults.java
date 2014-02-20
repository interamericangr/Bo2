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
import gr.interamerican.bo2.samples.bean.BeanWith3Fields;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link Defaults}.
 */
public class TestDefaults {
	
	
	/**
	 * Unit test for getNestedProperty()
	 */	
	@Test 
	public void testSetDefaults() {
		Map<Class<?>, Object> defaults = new HashMap<Class<?>, Object>();
		String defString = "default"; //$NON-NLS-1$
		Integer defInteger = 23;
		Double defDouble = 3.34;
		defaults.put(String.class, defString);
		defaults.put(Integer.class, defInteger);
		defaults.put(Double.class, defDouble);
		BeanWith3Fields bean = new BeanWith3Fields();
		Defaults.setDefaults(bean, BeanWith3Fields.class, defaults);
		assertEquals(defString, bean.getField1());
		assertEquals(defInteger, bean.getField2());
		assertEquals(defDouble, bean.getField3());
	}
	
	/**
	 * Unit test for getNestedProperty()
	 */	
	@Test 
	public void testSetStandardDefaults() {
		BeanWith3Fields bean = new BeanWith3Fields();
		Defaults.setStandardDefaults(bean, BeanWith3Fields.class);
		assertEquals(StringConstants.EMPTY, bean.getField1());
		assertEquals(new Integer(0), bean.getField2());
		assertEquals(new Double(0.0), bean.getField3());
	}
	
	/**
	 * Unit test for getNestedProperty()
	 */	
	@Test 
	public void testRegisterDefaultValue() {
		BeanWith3Fields bean = new BeanWith3Fields();
		Defaults.setStandardDefaults(bean, BeanWith3Fields.class);
		assertEquals(StringConstants.EMPTY, bean.getField1());
		assertEquals(new Integer(0), bean.getField2());
		assertEquals(new Double(0.0), bean.getField3());
	}
	
	/**
	 * Unit test for getDefaultValue()
	 */	
	@SuppressWarnings("cast")
	@Test 
	public void testGetDefaultValue() {
		Assert.assertTrue(Defaults.getDefaultValue(Byte.class) instanceof Byte);
		Assert.assertTrue(Defaults.getDefaultValue(byte.class) instanceof Byte);
		Assert.assertTrue(Defaults.getDefaultValue(Short.class) instanceof Short);
		Assert.assertTrue(Defaults.getDefaultValue(short.class) instanceof Short);
		Assert.assertTrue(Defaults.getDefaultValue(Integer.class) instanceof Integer);
		Assert.assertTrue(Defaults.getDefaultValue(int.class) instanceof Integer);
		Assert.assertTrue(Defaults.getDefaultValue(Long.class) instanceof Long);
		Assert.assertTrue(Defaults.getDefaultValue(long.class) instanceof Long);
		Assert.assertTrue(Defaults.getDefaultValue(Double.class) instanceof Double);
		Assert.assertTrue(Defaults.getDefaultValue(double.class) instanceof Double);
		Assert.assertTrue(Defaults.getDefaultValue(Float.class) instanceof Float);
		Assert.assertTrue(Defaults.getDefaultValue(float.class) instanceof Float);
		
		BeanWith3Fields expected = new BeanWith3Fields();
		Defaults.STANDARD_DEFAULTS.put(BeanWith3Fields.class, expected);
		BeanWith3Fields actual = Defaults.getDefaultValue(BeanWith3Fields.class);
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Unit test for setDefaultValue()
	 */	
	@Test 
	public void testSetDefaultValue() {
		BeanWith3Fields expected = new BeanWith3Fields();
		Defaults.registerDefaultValue(BeanWith3Fields.class, expected);
		BeanWith3Fields actual = Defaults.getDefaultValue(BeanWith3Fields.class);
		Assert.assertEquals(expected, actual);
	}

}
