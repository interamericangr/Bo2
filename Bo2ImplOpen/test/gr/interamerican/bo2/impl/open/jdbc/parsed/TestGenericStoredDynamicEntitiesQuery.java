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
package gr.interamerican.bo2.impl.open.jdbc.parsed;

import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.samples.implopen.beans.UserIdBean;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link GenericStoredDynamicEntitiesQuery}.
 */
public class TestGenericStoredDynamicEntitiesQuery {
	
	/**
	 * Test getCriteria
	 */
	@Test
	public void testGetCriteria() {
		String path = "/gr/interamerican/rsrc/sql/SelectIdAndNameFromUsers.sql"; //$NON-NLS-1$
		UserIdBean criteria = Factory.create(UserIdBean.class);
		GenericStoredDynamicEntitiesQuery<UserIdBean> q = 
			new GenericStoredDynamicEntitiesQuery<UserIdBean>(path, criteria);
		Assert.assertSame(criteria, q.getCriteria());
		UserIdBean newCriteria = Factory.create(UserIdBean.class);
		q.setCriteria(newCriteria);
		Assert.assertSame(newCriteria, q.getCriteria());
	}

}
