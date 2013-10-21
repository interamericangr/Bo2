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
package gr.interamerican.bo2.impl.open.modifications;

import gr.interamerican.bo2.samples.archutil.beans.IntegerCriteriaDependentBean;
import gr.interamerican.bo2.samples.archutil.beans.SampleCriteriaDependentBean;
import gr.interamerican.bo2.samples.bean.BeanWith2Fields;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link CriteriaAwareCopyFromProperties}.
 */
public class TestCriteriaAwareCopyFromProperties {
	
	
	
	/**
	 * Tests execute() on a bean that has Integer criteria.
	 * 
	 * This test fails. TODO: Fix this case.
	 */	
	//@Test
	@SuppressWarnings("nls")
	public void testExecute_withIntegerCriteria() {
		Properties p = new Properties();
		Integer criteria = 5;
		String property1 = "p1";
		p.setProperty("criteria", criteria.toString());
		p.setProperty("property1", property1);		
		
		CriteriaAwareCopyFromProperties<Integer, IntegerCriteriaDependentBean> copy = 
			new CriteriaAwareCopyFromProperties<Integer, IntegerCriteriaDependentBean>(p);
		
		IntegerCriteriaDependentBean bean = new IntegerCriteriaDependentBean();
		copy.execute(bean);
		
		Assert.assertEquals(criteria, bean.getCriteria());
		Assert.assertEquals(property1, bean.getProperty1());
	}
	
	/**
	 * Tests execute() on a bean that has a javabean criteria
	 */	
	@Test
	@SuppressWarnings("nls")
	public void testExecute_withBeanCriteria() {
		Properties p = new Properties();
		String property1 = "p1";
		String field1 = "f1";
		Integer field2 = 5;
		
		p.setProperty("field1", field1);
		p.setProperty("field2", field2.toString());		
		p.setProperty("property1", property1);		
		
		CriteriaAwareCopyFromProperties<BeanWith2Fields, SampleCriteriaDependentBean> copy = 
			new CriteriaAwareCopyFromProperties<BeanWith2Fields, SampleCriteriaDependentBean>(p);
		
		SampleCriteriaDependentBean bean = new SampleCriteriaDependentBean();
		copy.execute(bean);
		
		Assert.assertEquals(property1, bean.getProperty1());
		Assert.assertEquals(field1, bean.getCriteria().getField1());
		Assert.assertEquals(field2, bean.getCriteria().getField2());
	}


}
