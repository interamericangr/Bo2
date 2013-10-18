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
package gr.interamerican.bo2.utils.adapters;

import gr.interamerican.bo2.samples.bean.BeanWithNestedBean;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link PropertyValuesChecker}.
 */
public class TestPropertyValuesChecker {
	
	
	/**
	 * Unit test for the adapter.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testExecute() {
		String s1 = "some string"; //$NON-NLS-1$
		Integer i1 = null;
		Integer i2 = 6;
		
		String[] properties = {"property1", "nested.field1"};
		Object[] values = {i1, s1};
		String[] messages = new String[properties.length];
		for (int i = 0; i < messages.length; i++) {
			messages[i] = properties[i] + " is " + StringUtils.toString(values[i]);
		}
		
		PropertyValuesChecker<BeanWithNestedBean> checker = 
			new PropertyValuesChecker<BeanWithNestedBean>(properties, values, messages);
		
		BeanWithNestedBean bean = new BeanWithNestedBean(s1, i2);		

		/*
		 * Expecting that list contains both messages.
		 */
		bean.setProperty1(i1);
		List<String> list1 = checker.execute(bean);
		for (int i = 0; i < messages.length; i++) {
			Assert.assertTrue(list1.contains(messages[i]));
		}
		
		/*
		 * Expecting that list contains only message for nested.field
		 */		
		bean.setProperty1(2);
		List<String> list2 = checker.execute(bean);
		Assert.assertFalse(list2.contains(messages[0]));
		Assert.assertTrue(list2.contains(messages[1]));
		
		/*
		 * Expecting an empty list
		 */	
		bean.getNested().setField1(null);
		List<String> list3 = checker.execute(bean);
		Assert.assertTrue(list3.isEmpty());
	}
	
	
	/**
	 * Unit test for the adapter.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testExecute_withNested() {		
		
		String[] properties = {"nested", "nested.field1", "nested.field1", "nested.field2", "nested.field2"};
		Object[] values = {null, null, StringConstants.EMPTY, null, 0};
		String[] messages = new String[properties.length];
		for (int i = 0; i < messages.length; i++) {
			messages[i] = properties[i] + " is " + StringUtils.toString(values[i]);
		}
		
		PropertyValuesChecker<BeanWithNestedBean> checker = 
			new PropertyValuesChecker<BeanWithNestedBean>(properties, values, messages);
		
		BeanWithNestedBean bean = new BeanWithNestedBean(null, null);
		bean.setNested(null);

		/*
		 * Expecting that list contains message only for nested.
		 */		
		List<String> list1 = checker.execute(bean);
		Assert.assertEquals(1, list1.size());
		Assert.assertTrue(list1.contains(messages[0]));		
		
	}
	
	
	/**
	 * Unit test for the adapter.
	 */	
	@Test(expected=RuntimeException.class)
	public void testConstructor_withWrongArgs1() {		
		new PropertyValuesChecker<BeanWithNestedBean>(new String[2], new Object[3], new String[2]);
	}
	
	/**
	 * Unit test for the adapter.
	 */	
	@Test(expected=RuntimeException.class)
	public void testConstructor_withWrongArgs2() {		
		new PropertyValuesChecker<BeanWithNestedBean>(new String[2], new Object[2], new String[5]);
	}
	


}
