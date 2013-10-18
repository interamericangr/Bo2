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
package gr.interamerican.bo2.utils.meta.descriptors;

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.samples.utils.meta.Bean1;
import gr.interamerican.bo2.samples.utils.meta.Bean1descriptor;
import gr.interamerican.bo2.utils.DateUtils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 */
public class TestBusinessObjectDescriptor {
	
	/**
	 * Bean1
	 */
	Bean1 bean;
	
	/**
	 * Bean1descriptor
	 */
	Bean1descriptor boDesc = new Bean1descriptor();
	
	/**
	 * setup tests.
	 */	
	@Before
	public void before() {
		bean = new Bean1();
		bean.setId(2L);
		bean.setAmount(new BigDecimal(15000.0));
		bean.setDescription("description"); //$NON-NLS-1$
		bean.setIssueDate(DateUtils.getDay1AD());
		bean.setPercentage(0.256);
		bean.setRenewalDate(DateUtils.getDate(2010, Calendar.JANUARY, 1));
	}
	
	/**
	 * Unit test for Get.
	 */
	@Test
	public void testGet() {
		Map<BoPropertyDescriptor<?>, Object> values = boDesc.get(bean);
		testProperty(values, 0, bean.getId());		
		testProperty(values, 1, bean.getDescription());
		testProperty(values, 2, bean.getIssueDate());
		testProperty(values, 3, bean.getRenewalDate());
		testProperty(values, 4, bean.getPercentage());
		testProperty(values, 5, bean.getAmount());
	}
	
	/**
	 * Tests the value of a property exportd by the business object
	 * descriptor.
	 * 
	 * @param values 
	 *        Exported values.
	 * @param propertyIndex
	 *        Index of property.
	 * @param expected
	 *        Expected value.
	 */
	private void testProperty(
			Map<BoPropertyDescriptor<?>, Object> values, 
			int propertyIndex, 
			Object expected) {
		BoPropertyDescriptor<?> boPD= boDesc.getPropertyDescriptors().get(propertyIndex);
		Object actual = values.get(boPD);
		assertEquals(boPD.getFullyQualifiedName(),expected, actual);
	}
	

}
