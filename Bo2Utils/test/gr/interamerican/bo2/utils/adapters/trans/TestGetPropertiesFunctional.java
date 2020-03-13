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
package gr.interamerican.bo2.utils.adapters.trans;

import java.util.function.Function;

import org.junit.Assert;
import org.junit.Test;

import gr.interamerican.bo2.samples.bean.BeanWith2Fields;
import gr.interamerican.bo2.samples.bean.BeanWithNestedBean;
import gr.interamerican.bo2.utils.FunctionalUtils;

/**
 * Unit test for {@link GetPropertiesFunctional}.
 */
public class TestGetPropertiesFunctional {

	/**
	 * Unit test for the adapter.
	 */
	@Test
	public void testExecute() {
		String s1 = "some string"; //$NON-NLS-1$
		int i1 = 5;
		int i2 = 6;

		BeanWithNestedBean bean = new BeanWithNestedBean(s1, i2);
		bean.setProperty1(i1);

		Object[] expecteds = { i1, s1 };
		GetPropertiesFunctional<BeanWithNestedBean> gp = new GetPropertiesFunctional<>(BeanWithNestedBean::getProperty1,
				FunctionalUtils.nullSafeSynthesize(BeanWithNestedBean::getNested, BeanWith2Fields::getField1));
		Object[] actuals = gp.execute(bean);
		Assert.assertArrayEquals(expecteds, actuals);
	}

	/**
	 * Unit test for the adapter.
	 */
	@Test
	public void testExecute_nullTolerant() {
		String s1 = "some string"; //$NON-NLS-1$
		int i1 = 5;
		int i2 = 6;

		BeanWithNestedBean bean = new BeanWithNestedBean(s1, i2);
		bean.setProperty1(i1);
		bean.setNested(null);

		GetPropertiesFunctional<BeanWithNestedBean> gp = new GetPropertiesFunctional<>(BeanWithNestedBean::getProperty1,
				FunctionalUtils.nullSafeSynthesize(BeanWithNestedBean::getNested, BeanWith2Fields::getField1));
		Object[] result = gp.execute(bean);
		Assert.assertEquals(result[0], i1);
		Assert.assertNull(result[1]);
	}

	/**
	 * Unit test for the adapter.
	 */
	@Test(expected = NullPointerException.class)
	public void testExecute_notNullTolerant() {
		String s1 = "some string"; //$NON-NLS-1$
		int i1 = 5;
		int i2 = 6;

		BeanWithNestedBean bean = new BeanWithNestedBean(s1, i2);
		bean.setProperty1(i1);
		bean.setNested(null);
		GetPropertiesFunctional<BeanWithNestedBean> gp = new GetPropertiesFunctional<>(BeanWithNestedBean::getProperty1,
				((Function<BeanWithNestedBean, BeanWith2Fields>) BeanWithNestedBean::getNested)
						.andThen(BeanWith2Fields::getField1));
		gp.execute(bean);
	}
}