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
package gr.interamerican.bo2.impl.open.jee.jdbc;

import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.jdbc.DriverConnectionStrategy;
import gr.interamerican.bo2.impl.open.jdbc.JdbcConnectionProviderImpl;
import gr.interamerican.bo2.mocks.JavaxSql;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;
import gr.interamerican.bo2.utils.ReflectionUtils;

import java.lang.reflect.Method;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link DriverConnectionStrategy}
 * 
 *
 */
public class TestJndiConnectionStrategy {
	
	/**
	 * Provider;
	 */
	JdbcConnectionProviderImpl provider;
	/**
	 * object to test.
	 */
	JndiConnectionStrategy strategy;
	
	/**
	 * Mock strategy
	 */
	JndiConnectionStrategy mock = new JndiConnectionStrategy() {
		@Override
		javax.sql.DataSource getDatasourceFromJndi(String jndiKey) {			
			return JavaxSql.mockDatasource();
		}
	};
	
	/**
	 * tests setup.
	 * @throws InitializationException 
	 */
	@Before
	public void setup() throws InitializationException {
		provider = new JdbcConnectionProviderImpl(UtilityForBo2Test.getSampleJdbcProperties());
		strategy = new JndiConnectionStrategy();
		strategy.setComponent(provider);
	}	
	
	/**
	 * Tests that failure throws the appropriate Exception
	 * if a mandatory property DBDRIVER is missing.
	 * 
	 * @throws InitializationException 
	 */
	@Test(expected=InitializationException.class)
	public void testParseProperties_missingJndi() throws InitializationException {		
		provider.getProperties().remove(JndiConnectionStrategy.KEY_DBJNDINAME);	
		strategy.parseProperties();
	}
	
	/**
	 * Tests parseProperties.
	 * 
	 * @throws InitializationException 
	 */
	@Test()
	public void testParseProperties_succeed() throws InitializationException {
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
		 * Invoke parseProperties with reflection.
		 */
		Method method = ReflectionUtils.getMethodByUniqueName("parseProperties", provider.getClass()); //$NON-NLS-1$
		Object[] args = null;
		ReflectionUtils.invoke(method, provider, args);
		strategy.dbJndiName = "foo"; //$NON-NLS-1$
		strategy.doConnect();
	}
	
	/**
	 * Tests that failure throws the appropriate Exception
	 * if the connection fails.
	 * 
	 * @throws InitializationException 
	 */
	@Test()
	public void testDoConnect_succeed() throws InitializationException {
		mock.setComponent(provider);
		mock.doConnect();
	}
	
	
	/**
	 * Tests that failure throws the appropriate Exception
	 * if the connection fails.
	 * 
	 * @throws InitializationException 
	 */
	@Test(expected=InitializationException.class)
	public void testDoConnect_failFromDatasource1() throws InitializationException {
		/**
		 * Mock strategy
		 */
		JndiConnectionStrategy md = new JndiConnectionStrategy() {
			@Override
			javax.sql.DataSource getDatasourceFromJndi(String jndiKey) 
			throws InitializationException {
				return JavaxSql.mockDatasourceThatFails(new SQLException());			
			}
		};
		md.setComponent(provider);
		md.doConnect();
	}	
	
	/**
	 * Tests that failure throws the appropriate Exception
	 * if the connection fails.
	 * 
	 * @throws InitializationException 
	 */
	@Test(expected=InitializationException.class)
	public void testDoConnect_failFromDatasource2() throws InitializationException {
		/**
		 * Mock strategy
		 */
		JndiConnectionStrategy md = new JndiConnectionStrategy() {
			@Override
			javax.sql.DataSource getDatasourceFromJndi(String jndiKey) 
			throws InitializationException {
				return JavaxSql.mockDatasourceThatFails(new RuntimeException());		
			}
		};
		md.setComponent(provider);
		md.doConnect();
	}	
	
	
	
	
}
