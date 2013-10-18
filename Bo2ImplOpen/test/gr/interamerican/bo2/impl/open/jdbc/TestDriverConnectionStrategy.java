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

import static org.junit.Assert.assertNotNull;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.samples.providers.MockTransactionManager;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link DriverConnectionStrategy}
 * 
 *
 */
public class TestDriverConnectionStrategy {
	
	/**
	 * Provider;
	 */
	JdbcConnectionProviderImpl provider;
	/**
	 * object to test.
	 */
	DriverConnectionStrategy strategy;
	
	/**
	 * tests setup.
	 * @throws InitializationException 
	 */
	@Before
	public void setup() throws InitializationException {
		provider = new JdbcConnectionProviderImpl(UtilityForBo2Test.getSampleJdbcProperties());
		strategy = new DriverConnectionStrategy();
		strategy.setComponent(provider);
	}
	
	/**
	 * cleanup.
	 */
	@After
	public void cleanup() {
		Bo2Session.setProvider(null);
	}
	
	/**
	 * Tests that failure throws the appropriate Exception
	 * if a mandatory property DBDRIVER is missing.
	 * 
	 * @throws InitializationException 
	 */
	@Test(expected=InitializationException.class)
	public void testParseProperties_missingDriver() throws InitializationException {		
		provider.getProperties().remove(DriverConnectionStrategy.KEY_DBDRIVER);	
		strategy.parseProperties();
	}
	
	/**
	 * Tests that failure throws the appropriate Exception
	 * if a mandatory property DBURL is missing.
	 * 
	 * @throws InitializationException 
	 */
	@Test(expected=InitializationException.class)
	public void testParseProperties_MissingUrl() throws InitializationException {		
		provider.getProperties().remove(DriverConnectionStrategy.KEY_DBURL);	
		strategy.parseProperties();
	}
	
	/**
	 * Tests that failure throws the appropriate Exception
	 * if the connection fails.
	 * 
	 * @throws InitializationException 
	 */
	@Test(expected=InitializationException.class)
	public void testDoConnect_fail() throws InitializationException {		
		/*
		 * Apache derby 10.6 would connect even with wrong user id
		 * and password. 
		 */		
		provider.parseProperties();
		strategy.dbDriver = "unknown_driver"; //$NON-NLS-1$
		strategy.dbUrl = "unknown_database_url"; //$NON-NLS-1$
		strategy.doConnect();
	}
	
	/**
	 * Tests that failure throws the appropriate Exception
	 * if the connection fails.
	 * 
	 * @throws InitializationException 
	 * @throws SQLException 
	 */
	@Test()
	public void testDoConnect_success() throws InitializationException, SQLException {
		provider.parseProperties();
		strategy.parseProperties();		
		strategy.doConnect();
		assertNotNull(provider.getConnection());
		provider.getConnection().close();
	}
	
	/**
	 * Tests validateSetup
	 * @throws InitializationException 
	 */
	@Test
	public void testValidateSetup_valid() throws InitializationException {
		UtilityForBo2Test.setCurrentTransactionManager(JdbcConnectionsTransactionManager.class);
		strategy.validateSetup();
	}
	
	/**
	 * Tests validateSetup with invalid config.
	 * @throws InitializationException 
	 */
	@Test (expected=InitializationException.class)
	public void testValidateSetup_invalid() throws InitializationException {
		UtilityForBo2Test.setCurrentTransactionManager(MockTransactionManager.class);
		strategy.validateSetup();
	}

}
