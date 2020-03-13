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

import static org.junit.Assert.*;

import org.junit.Test;

import gr.interamerican.bo2.samples.bean.BeanWithNestedBean;

/**
 * Tests for {@link ConditionOnNestedProperty}.
 */
@Deprecated
public class TestConditionOnNestedProperty {

	/**
	 * Is zero condition.
	 */
	Condition<Integer> isZero = new EqualTo<Integer>(0);

	/**
	 * Condition for the tests.
	 */
	ConditionOnNestedProperty<BeanWithNestedBean> nestedField2IsZero = new ConditionOnNestedProperty<>(
			"nested.field2", BeanWithNestedBean.class, isZero); //$NON-NLS-1$

	/**
	 * Tests check().
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCheck_true() {
		BeanWithNestedBean bean = new BeanWithNestedBean("foo", 0);
		assertTrue(nestedField2IsZero.check(bean));
	}

	/**
	 * Tests check().
	 */
	@Test
	public void testCheck_false() {
		BeanWithNestedBean bean = new BeanWithNestedBean(null, null);
		bean.setNested(null);
		assertFalse(nestedField2IsZero.check(bean));
	}

	/**
	 * Tests check().
	 */
	@Test
	public void testCheck_nestedNullEx() {
		BeanWithNestedBean bean = new BeanWithNestedBean(null, null);
		assertFalse(nestedField2IsZero.check(bean));
	}

	/**
	 * Tests if the constructor throws a runtime exception for an invalid
	 * property.
	 */
	@SuppressWarnings({ "nls", "unused" })
	@Test(expected = RuntimeException.class)
	public void testConstructor_withInvalidProperty() {
		EqualsIgnoreCaseCondition equals = new EqualsIgnoreCaseCondition("vava");
		ConditionOnProperty<BeanWithNestedBean> condition = new ConditionOnProperty<>("nested.nosuchfield",
				BeanWithNestedBean.class, equals);
	}
}