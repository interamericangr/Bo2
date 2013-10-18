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
import gr.interamerican.bo2.utils.beans.Range;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link GetRangeFromProperties}.
 */
public class TestGetRangeFromProperties {
	
	/**
	 * Tests execute.
	 */
	@Test
	public void testExecute() {
		BeanWithNestedBean bean = new BeanWithNestedBean(null, 5);
		bean.setProperty1(0);
		GetRangeFromProperties<BeanWithNestedBean, Integer> getRange = 
			new GetRangeFromProperties<BeanWithNestedBean, Integer>
			("property1", "nested.field2", BeanWithNestedBean.class); //$NON-NLS-1$ //$NON-NLS-2$
		Range<Integer> actual = getRange.execute(bean);
		Range<Integer> expected = new Range<Integer>(0, 5);
		Assert.assertEquals(expected, actual);
	}

}
