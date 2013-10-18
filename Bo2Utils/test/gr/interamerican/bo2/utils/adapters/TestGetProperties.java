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

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link GetProperty}.
 */
public class TestGetProperties {
	
	
	/**
	 * Unit test for the adapter.
	 */
	@Test
	public void testExecute() {
		String[] properties = {"property1", "nested.field1"}; //$NON-NLS-1$ //$NON-NLS-2$
		
		String s1 = "some string"; //$NON-NLS-1$
		int i1 = 5;
		int i2 = 6;
		
		BeanWithNestedBean bean = new BeanWithNestedBean(s1, i2);
		bean.setProperty1(i1);
		
		Object[] expecteds = {i1, s1};		
		GetProperties gp = new GetProperties(properties);		
		Object[] actuals = gp.execute(bean);
		Assert.assertArrayEquals(expecteds, actuals);
	}
	
	/**
	 * Unit test for the adapter.
	 */
	@Test
	public void testExecute_nullTolerant() {
		String[] properties = {"property1", "nested.field1"}; //$NON-NLS-1$ //$NON-NLS-2$
		
		String s1 = "some string"; //$NON-NLS-1$
		int i1 = 5;
		int i2 = 6;
		
		BeanWithNestedBean bean = new BeanWithNestedBean(s1, i2);
		bean.setProperty1(i1);
		bean.setNested(null);
		
				
		GetProperties gp = new GetProperties(properties, true);		
		Object[] result = gp.execute(bean);
		Assert.assertEquals(result[0], i1);
		Assert.assertNull(result[1]);
	}
	
	/**
	 * Unit test for the adapter.
	 */
	@Test(expected = RuntimeException.class)
	public void testExecute_notNullTolerant() {
		String[] properties = {"property1", "nested.field1"}; //$NON-NLS-1$ //$NON-NLS-2$
		
		String s1 = "some string"; //$NON-NLS-1$
		int i1 = 5;
		int i2 = 6;
		
		BeanWithNestedBean bean = new BeanWithNestedBean(s1, i2);
		bean.setProperty1(i1);
		bean.setNested(null);
		
		GetProperties gp = new GetProperties(properties);
		gp.execute(properties);
	}

}
