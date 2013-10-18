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

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.samples.bean.BeanWith2Fields;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for Stored
 */
public class TestStoredJdbcQuery {
	
	/**
	 * provider.
	 */
	protected Provider provider;
	
	/**
	 * Setup tests.
	 * @throws InitializationException 
	 */
	@Before
	public void setup() throws InitializationException {
		provider = Bo2.getDeployment(UtilityForBo2Test.BATCH_NO_TRAN).getProvider();
	}
	
	
	/**
	 * Unit test for the whole life cycle.
	 * 
	 * @throws InitializationException 
	 * @throws DataException 
	 */
	@Test
	public void testLifecycle() throws InitializationException, DataException {
		String path = "/gr/interamerican/rsrc/sql/SelectUsers.sql"; //$NON-NLS-1$
		StoredJdbcQuery<BeanWith2Fields> q = new StoredJdbcQuery<BeanWith2Fields>(path);
		Assert.assertNotNull(q.statement);
		System.out.println(q.statement);
		q.setManagerName("LOCALDB"); //$NON-NLS-1$
		q.init(provider);
		q.open();
		BeanWith2Fields criteria = new BeanWith2Fields();
		q.setCriteria(criteria);		
		criteria.setField1(null);
		criteria.setField2(1);		
		q.execute();
		while (q.next()) {
			System.out.println(q.getInt("id")); //$NON-NLS-1$
		}
		q.close();
		
	}
	
	/**
	 * Unit test for constructor.
	 */
	@Test(expected=RuntimeException.class)
	public void testConstructor_Fail() {
		String path = "/com/foo/bar.sql"; //$NON-NLS-1$
		new StoredJdbcQuery<BeanWith2Fields>(path);
	}

	

}
