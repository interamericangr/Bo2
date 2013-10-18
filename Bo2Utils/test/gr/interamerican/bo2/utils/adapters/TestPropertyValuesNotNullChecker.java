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

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link PropertyValuesNotNullChecker}.
 */
public class TestPropertyValuesNotNullChecker {
	
	
	/**
	 * Unit test for the adapter.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testExecute() {		
		String[] properties = {"property1", "nested.field1"};
		
		String[] messages = new String[properties.length];
		for (int i = 0; i < messages.length; i++) {
			messages[i] = properties[i] + " is null";
		}
		
		PropertyValuesNotNullChecker<BeanWithNestedBean> checker = 
			new PropertyValuesNotNullChecker<BeanWithNestedBean>(properties, messages);
		
		BeanWithNestedBean bean = new BeanWithNestedBean("string", 0);		

		/*
		 * Expecting an empty list.
		 */		
		List<String> list = checker.execute(bean);
		Assert.assertTrue(list.isEmpty());
		
	}
	
	
	
	


}
