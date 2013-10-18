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

import gr.interamerican.bo2.samples.bean.BeanWith2Fields;
import gr.interamerican.bo2.samples.bean.BeanWithNestedBean;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link GetProperty}.
 */
public class TestGetProperty {
	/**
	 * Unit test for the adapter.
	 */
	@Test
	public void testExecute_withSimpleProperty() {
		BeanWith2Fields bean = new BeanWith2Fields();
		String expected = "value"; //$NON-NLS-1$
		bean.setField1(expected);
		bean.setField2(2);
		GetProperty<BeanWith2Fields, String> getter = 
			new GetProperty<BeanWith2Fields, String>("field1", BeanWith2Fields.class); //$NON-NLS-1$
		String actual = getter.execute(bean);
		Assert.assertEquals(expected, actual);
		Assert.assertNull(getter.execute(null));
	}
	
	/**
	 * Unit test for the adapter.
	 */
	@Test
	public void testExecute_withNestedProperty() {
		String expected = "field1"; //$NON-NLS-1$
		BeanWithNestedBean bean = new BeanWithNestedBean(expected, 3);		
		GetProperty<BeanWithNestedBean, String> getter = 
			new GetProperty<BeanWithNestedBean, String>("nested.field1", BeanWithNestedBean.class); //$NON-NLS-1$
		String actual = getter.execute(bean);
		Assert.assertEquals(expected, actual);
		Assert.assertNull(getter.execute(null));
	}
	


}
