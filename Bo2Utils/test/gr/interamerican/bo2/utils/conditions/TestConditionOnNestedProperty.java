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
package gr.interamerican.bo2.utils.conditions;

import gr.interamerican.bo2.samples.bean.BeanWithNestedBean;


import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link ConditionOnNestedProperty}.
 */
public class TestConditionOnNestedProperty {
	
	/**
	 * Is zero condition.
	 */
	Condition<?> isZero = new EqualTo<Integer>(0);
	
	/**
	 * Condition for the tests.
	 */
	ConditionOnNestedProperty<BeanWithNestedBean> nestedField2IsZero = 
		new ConditionOnNestedProperty<BeanWithNestedBean>
		("nested.field2", BeanWithNestedBean.class, isZero);
	
	
	
	/**
	 * Tests check().
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCheck_true() {
		BeanWithNestedBean bean = new BeanWithNestedBean("foo", 0);
		boolean result = nestedField2IsZero.check(bean);
		Assert.assertTrue(result);
	}
	
	/**
	 * Tests check().
	 */
	@Test
	public void testCheck_false() {
		BeanWithNestedBean bean = new BeanWithNestedBean(null, null);
		bean.setNested(null);
		boolean result = nestedField2IsZero.check(bean);
		Assert.assertFalse(result);
	}
	
	/**
	 * Tests check().
	 */	
	@Test
	public void testCheck_nestedNullEx() {		
		BeanWithNestedBean bean = new BeanWithNestedBean(null, null);
		boolean result = nestedField2IsZero.check(bean);
		Assert.assertFalse(result);
	}
	
	/**
	 * Tests if the constructor throws a runtime exception 
	 * for an invalid property.
	 */
	@SuppressWarnings({ "nls", "unused" })
	@Test(expected=RuntimeException.class)
	public void testConstructor_withInvalidProperty() {
		EqualsIgnoreCaseCondition equals = new EqualsIgnoreCaseCondition("vava");
		ConditionOnProperty<BeanWithNestedBean> condition = 
			new ConditionOnProperty<BeanWithNestedBean>
			("nested.nosuchfield", BeanWithNestedBean.class, equals);
	}

}
