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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.CouldNotBeginException;
import gr.interamerican.bo2.arch.exceptions.CouldNotDelistException;
import gr.interamerican.bo2.arch.exceptions.CouldNotEnlistException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.runtime.ProviderTransactionManagerTestBean;
import gr.interamerican.bo2.impl.open.utils.Bo2;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link JdbcConnectionsTransactionManager}.
 */
public class TestJdbcConnectionsTransactionManager {
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
//	public TestJdbcConnectionsTransactionManager()  {
//		super();
//		
//	}
	
	@Before
	public void setup() {
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
		testBeanJdbc.testCommit();
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
		testBeanHib.testCommit();
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
		testBeanJdbc.testRollback();
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
		testBeanHib.testRollback();
	}
	
	/**
	 * Tests that the enList method.
	 * 
	 * @throws InitializationException
	 * @throws DataException
	 * @throws SQLException
	 * @throws CouldNotBeginException 
	 * @throws CouldNotEnlistException 
	 */
	@Test
	public void testEnList() 
	throws InitializationException, DataException, SQLException, CouldNotBeginException, CouldNotEnlistException {
		Provider provider = Bo2.getDefaultDeployment().getProvider();
		JdbcConnectionProvider jdbc = provider.getResource("LOCALDB",JdbcConnectionProvider.class); //$NON-NLS-1$		
		Connection connection = jdbc.getConnection();
		
		JdbcConnectionsTransactionManager manager = new JdbcConnectionsTransactionManager();
		manager.begin();
		manager.enList(jdbc);
		assertFalse(jdbc.getConnection().getAutoCommit());
		assertTrue(manager.connections.contains(connection));
		jdbc.close();		
	}
	
	
	
	
	/**
	 * Tests that the delist method.
	 * 
	 * @throws InitializationException
	 * @throws DataException
	 * @throws SQLException
	 * @throws CouldNotBeginException 
	 * @throws CouldNotDelistException 
	 * @throws CouldNotEnlistException 
	 */
	@Test
	public void testDeList() 
	throws InitializationException, DataException, SQLException, CouldNotBeginException, CouldNotDelistException, CouldNotEnlistException {
		Provider provider = Bo2.getProvider();
		JdbcConnectionProvider jdbc = provider.getResource("LOCALDB",JdbcConnectionProvider.class); //$NON-NLS-1$		
		Connection connection = jdbc.getConnection();
		
		JdbcConnectionsTransactionManager manager = new JdbcConnectionsTransactionManager();
		manager.begin();
		
		manager.deList(jdbc);
		assertTrue(jdbc.getConnection().getAutoCommit());
		assertFalse(manager.connections.contains(connection));
		
		manager.enList(jdbc);
		
		manager.deList(jdbc);
		assertTrue(jdbc.getConnection().getAutoCommit());
		assertFalse(manager.connections.contains(connection));
		
		jdbc.close();		
	}

}
