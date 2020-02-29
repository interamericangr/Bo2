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

import org.junit.Assert;
import org.junit.Test;

import gr.interamerican.bo2.samples.bean.BeanWith2Fields;
import gr.interamerican.bo2.samples.bean.BeanWithNestedBean;
import gr.interamerican.bo2.utils.FunctionalUtils;

/**
 * Unit test for {@link ValueIsWithinRangeDefinedByProperties}.
 */
public class TestValueIsWithinRangeDefinedByProperties {
	
	/**
	 * Test for check.
	 */
	@Deprecated
	@Test
	@SuppressWarnings("nls")
	public void testCheck() {
		ValueIsWithinRangeDefinedByProperties<BeanWithNestedBean, Integer> condition =
			new ValueIsWithinRangeDefinedByProperties<BeanWithNestedBean, Integer>
			("property1", "nested.field2", BeanWithNestedBean.class, 5);
		BeanWithNestedBean bean = new BeanWithNestedBean(null, 10);
		bean.setProperty1(2);
		Assert.assertTrue(condition.check(bean));
		bean.getNested().setField2(3);
		Assert.assertFalse(condition.check(bean));
	}

	/**
	 * Test for check.
	 */
	@Test
	public void testCheck_Functional() {
		ValueIsWithinRangeDefinedByProperties<BeanWithNestedBean, Integer> condition = new ValueIsWithinRangeDefinedByProperties<>(
				BeanWithNestedBean::getProperty1,
				FunctionalUtils.nullSafeSynthesize(BeanWithNestedBean::getNested, BeanWith2Fields::getField2), 5);
		BeanWithNestedBean bean = new BeanWithNestedBean(null, 10);
		bean.setProperty1(2);
		Assert.assertTrue(condition.check(bean));
		bean.getNested().setField2(3);
		Assert.assertFalse(condition.check(bean));
	}
}