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

import static gr.interamerican.bo2.utils.ReflectionUtils.copyProperties;
import static gr.interamerican.bo2.utils.ReflectionUtils.copyPropertiesExcluding;
import static gr.interamerican.bo2.utils.ReflectionUtils.copyProperty;
import static gr.interamerican.bo2.utils.ReflectionUtils.getProperties;
import static gr.interamerican.bo2.utils.ReflectionUtils.getProperty;
import static gr.interamerican.bo2.utils.ReflectionUtils.setProperty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import gr.interamerican.bo2.samples.SampleBean2;
import gr.interamerican.bo2.samples.bean.BeanWith2Fields;

import java.util.Map;

import org.junit.Test;

/**
 * This class is identical to the class TestCopyPropertiesForReflectionUtils.
 * It contains tests for the copyProperties methods.
 * This class must be copied to the TestCopyPropertiesForReflectionUtils, 
 * after changing the import static gr.interamerican.bo2.utils.JavaBeanUtils.* 
 * to gr.interamerican.bo2.utils.ReflectionUtils.*. 
 */
public class TestCopyPropertiesForReflectionUtils {
	
	/**
	 * test getProperty(string,object)
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetProperty(){
		BeanWith2Fields bean = new BeanWith2Fields();
		String value = "hello"; //$NON-NLS-1$
		bean.setField1(value);
		String actual = (String) getProperty("field1", bean);
		assertEquals(value,actual);
	}
	
	/**
	 * Unit test for setProperty.
	 */
	@Test
	public void testSetProperty() {
		BeanWith2Fields bean = new BeanWith2Fields();		
		String value = "string"; //$NON-NLS-1$
		setProperty("field1", value, bean); //$NON-NLS-1$
		assertEquals(value, bean.getField1());
	}
	
	/**
	 * test getProperties
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetProperties(){
		BeanWith2Fields bean = new BeanWith2Fields();
		String value1 = "value1";
		Integer value2 = 13;
		bean.setField1(value1);
		bean.setField2(value2);
		Map<String,Object> map = getProperties(bean);
		assertEquals(value1,map.get("field1"));
		assertEquals(value2,map.get("field2"));
	}
	
	
	/**
	 * tests Utils.testCopyProperties()
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCopyProperties_ForDifferentType() {
		
		BeanWith2Fields b1 = new BeanWith2Fields();
		b1.setField1("papaki");
		b1.setField2(4);
		SampleBean2 b2 = new SampleBean2();
		
		/* copy one property */
		String[] prop1 = {"field1"};
		copyProperties(b1, b2, prop1);
		assertEquals(b1.getField1(), b2.getField1());
		assertFalse((b1.getField2().equals(b2.getField2())));
		
		
		
	}
	
	/**
	 * tests Utils.testCopyProperties()
	 */
	@SuppressWarnings("nls")
	@Test	
	public void testCopyProperties_ForSameType() {
		
		BeanWith2Fields b1 = new BeanWith2Fields();
		b1.setField1("papaki");
		b1.setField2(4);
		BeanWith2Fields b2 = new BeanWith2Fields();
		
		/* copy one property */
		String[] prop1 = {"field1"};
		copyProperties(b1, b2, prop1);
		assertEquals(b1.getField1(), b2.getField1());
		assertFalse((b1.getField2().equals(b2.getField2())));
		
		/* copy two properties */
		BeanWith2Fields b3 = new BeanWith2Fields();
		String[] prop2 = {"field1", "field2"};
		copyProperties(b1, b3, prop2);
		assertEquals(b1.getField1(), b3.getField1());
		assertEquals(b1.getField2(), b3.getField2());
		
		/* copy all properties */
		BeanWith2Fields b4 = new BeanWith2Fields();
		String[] prop3 = {};
		copyProperties(b1, b4, prop3);
		assertEquals(b1.getField1(), b4.getField1());
		assertEquals(b1.getField2(), b4.getField2());
		
		/* copy all properties with null array */
		BeanWith2Fields b5 = new BeanWith2Fields();		
		copyProperties(b1, b5, null);
		assertEquals(b1.getField1(), b5.getField1());
		assertEquals(b1.getField2(), b5.getField2());
		
	}
	
	/**
	 * tests Utils.testCopyProperties()
	 */
	@SuppressWarnings("nls")
	@Test	
	public void testCopyProperty() {		
		BeanWith2Fields b1 = new BeanWith2Fields();
		String val = "vavava";
		b1.setField1(val);
		b1.setField2(4);
		BeanWith2Fields b2 = new BeanWith2Fields();
		
		copyProperty(b1, b2, "field1");
		assertEquals(val, b1.getField1());
		assertEquals(val, b2.getField1());		
		assertFalse((b1.getField2().equals(b2.getField2())));
	}

	/**
	 * tests Utils.testCopyProperties()
	 */
	@SuppressWarnings("nls")
	@Test	
	public void testCopyPropertiesExcluding() {
		
		BeanWith2Fields b1 = new BeanWith2Fields();
		b1.setField1("hello");
		b1.setField2(4);
		BeanWith2Fields b2 = new BeanWith2Fields();
	
		String[] prop1 = {"field2"};
		copyPropertiesExcluding(b1, b2, prop1);
		assertEquals(b1.getField1(), b2.getField1());
		assertFalse((b1.getField2().equals(b2.getField2())));	
		
		copyPropertiesExcluding(b1, b2, null);
		assertEquals(b1.getField1(), b2.getField1());
		assertEquals(b1.getField2(), b2.getField2());
	}

}
