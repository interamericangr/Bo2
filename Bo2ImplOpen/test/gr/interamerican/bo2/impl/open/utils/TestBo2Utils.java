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

import static org.junit.Assert.*;
import gr.interamerican.bo2.creation.ObjectFactory;
import gr.interamerican.bo2.samples.archutil.beans.IntegerCriteriaDependentBean;
import gr.interamerican.bo2.samples.archutil.beans.SampleCriteriaDependentBean;
import gr.interamerican.bo2.samples.bean.BeanWith2Fields;

import java.util.Map;

import org.junit.Test;

/**
 * Unit tests for class {@link Bo2Utils}.
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
		assertEquals(Integer.valueOf(0), criteria);
	}
	
	/**
	 * tests getCriteria.
	 */
	@Test
	public void testGetCriteria_withBean_withIntegerCriteria(){
		SampleCriteriaDependentBean bean = new SampleCriteriaDependentBean();
		bean.setCriteria(null);
		BeanWith2Fields criteria = Bo2Utils.getCriteria(bean);
		assertNotNull(criteria);
	}

	/**
	 * tests getFactoriesMapFromProperties.
	 */
	@Test(expected = RuntimeException.class)
	public void testGetFactoriesMapFromProperties_Fail() {
		@SuppressWarnings("nls")
		String[] managersToLoad = new String[] { "/gr/interamerican/bo2/deployparms/managers/localdb/of.properties",
				"/gr/interamerican/bo2/deployparms/managers/localdbjta/of.properties",
				"/gr/interamerican/bo2/deployparms/managers/odf/of.properties" };
		// same manager name loaded twice - this should fail
		Bo2Utils.getFactoriesMapFromProperties(managersToLoad);
	}

	/**
	 * tests getFactoriesMapFromProperties.
	 */
	@Test
	public void testGetFactoriesMapFromProperties() {
		@SuppressWarnings("nls")
		String[] managersToLoad = new String[] { "/gr/interamerican/bo2/deployparms/managers/localdb/of.properties",
				"/gr/interamerican/bo2/deployparms/managers/localfs/of.properties",
				"/gr/interamerican/bo2/deployparms/managers/otherdb/of.properties",
				"/gr/interamerican/rsrc/managers/mock1/of.properties"};
		Map<String, ObjectFactory> result = Bo2Utils.getFactoriesMapFromProperties(managersToLoad);
		assertEquals(4, result.size());
		ObjectFactory objectFactory = result.get("MOCK1"); //$NON-NLS-1$
		assertNotNull(objectFactory);
	}
}