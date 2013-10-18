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
package gr.interamerican.bo2.impl.open.utils;

import gr.interamerican.bo2.arch.utils.Bo2ArchUtils;
import gr.interamerican.bo2.samples.archutil.beans.IntegerCriteriaDependentBean;
import gr.interamerican.bo2.samples.archutil.beans.SampleCriteriaDependentBean;
import gr.interamerican.bo2.samples.bean.BeanWith2Fields;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for class {@link Bo2ArchUtils}.
 */
public class TestBo2Utils {
	

	/**
	 * tests getCriteria.
	 */
	@Test
	public void testGetCriteria(){
		IntegerCriteriaDependentBean bean = new IntegerCriteriaDependentBean();
		bean.setCriteria(null);
		Integer criteria = Bo2Utils.getCriteria(bean);
		Assert.assertEquals(Integer.valueOf(0), criteria);
	}
	
	/**
	 * tests getCriteria.
	 */
	@Test
	public void testGetCriteria_withBean_withIntegerCriteria(){
		SampleCriteriaDependentBean bean = new SampleCriteriaDependentBean();
		bean.setCriteria(null);
		BeanWith2Fields criteria = Bo2Utils.getCriteria(bean);
		Assert.assertNotNull(criteria);
	}
	

	
	
}
