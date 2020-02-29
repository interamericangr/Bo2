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
package gr.interamerican.bo2.utils.conditions.functional;

import static org.junit.Assert.*;

import org.junit.Test;

import gr.interamerican.bo2.samples.bean.BeanWith2Fields;
import gr.interamerican.bo2.samples.bean.BeanWithNestedBean;
import gr.interamerican.bo2.utils.conditions.Condition;
import gr.interamerican.bo2.utils.conditions.EqualTo;

/**
 * Tests for {@link ConditionOnNestedProperty}.
 */
public class TestConditionOnNestedProperty {

	/**
	 * Is zero condition.
	 */
	Condition<Integer> isZero = new EqualTo<Integer>(0);

	/**
	 * Condition for the tests.
	 */
	ConditionOnNestedProperty<BeanWithNestedBean, Integer> nestedField2IsZero = new ConditionOnNestedProperty<>(
			BeanWithNestedBean::getNested, BeanWith2Fields::getField2, isZero);

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
}