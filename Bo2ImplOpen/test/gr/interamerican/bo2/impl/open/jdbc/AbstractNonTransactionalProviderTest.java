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
package gr.interamerican.bo2.impl.open.jdbc;

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;

import org.junit.After;
import org.junit.Before;

/**
 * Test thst uses an abstract non transactional provider.
 */
public class AbstractNonTransactionalProviderTest {
	
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
	 * tear down tests.
	 * @throws DataException 
	 */
	@After
	public void tearDown() throws DataException {
		provider.close();
	}

}
