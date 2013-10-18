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
package gr.interamerican.bo2.impl.open.jotm;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.runtime.ProviderTransactionManagerTestBean;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;

import org.junit.Test;

/**
 * 
 */
public class TestJotmTransactionManager {
	/**
	 * test for hibernate.
	 */
	ProviderTransactionManagerTestBean testBeanHib;
	/**
	 * Test for jdbc
	 */
	ProviderTransactionManagerTestBean testBeanJdbc;
	
	/**
	 * Creates a new TestJdbcConnectionsTransactionManager object. 
	 *
	 */
	public TestJotmTransactionManager()  {
		super();
		testBeanHib = new ProviderTransactionManagerTestBean(true);
		testBeanJdbc = new ProviderTransactionManagerTestBean(false);
	}
	
	/**
	 * Tests commit with JDBC.
	 * 
	 * @throws DataException
	 * @throws LogicException
	 * @throws UnexpectedException 
	 */
	@Test
	public void testCommit_withJdbc() throws DataException, LogicException, UnexpectedException {
		testBeanJdbc.testCommit(UtilityForBo2Test.BATCH_JTA_TRAN);
	}
	
	/**
	 * Tests commit with Hibernate.
	 * 
	 * @throws DataException
	 * @throws LogicException
	 * @throws UnexpectedException 
	 */	
	@Test
	public void testCommit_withHibernate() throws DataException, LogicException, UnexpectedException {
		testBeanHib.testCommit(UtilityForBo2Test.BATCH_JTA_TRAN);
	}

	/**
	 * Tests rollback with JDBC.
	 * 
	 * @throws DataException
	 * @throws LogicException
	 * @throws UnexpectedException 
	 */
	@Test
	public void testRollback_withJdbc() throws DataException, LogicException, UnexpectedException {
		testBeanJdbc.testRollback(UtilityForBo2Test.BATCH_JTA_TRAN);
	}
	
	/**
	 * Tests rollback with Hibernate.
	 * 
	 * @throws DataException
	 * @throws LogicException
	 * @throws UnexpectedException 
	 */	
	@Test
	public void testRollback_withHibernate() throws DataException, LogicException, UnexpectedException {
		testBeanHib.testRollback(UtilityForBo2Test.BATCH_JTA_TRAN);
	}
	
}
