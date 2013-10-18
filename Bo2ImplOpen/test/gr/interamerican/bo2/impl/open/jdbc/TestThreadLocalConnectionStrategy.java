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

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;

import java.sql.Connection;
import java.util.Properties;

import org.junit.Test;

/**
 * Unit test for {@link ThreadLocalConnectionStrategy}
 */
public class TestThreadLocalConnectionStrategy {
	
	/**
	 * Unit test for the whole lifecycle of the ThreadLocalConnectionStrategy.
	 * 
	 * @throws InitializationException
	 * @throws DataException 
	 */
	@Test
	public void testLifeCycle() throws InitializationException, DataException {
		/*
		 * first open a connection.
		 */
		Provider provider = Bo2.getDeployment(UtilityForBo2Test.BATCH_NO_TRAN).getProvider();
		JdbcConnectionProvider jdbc = provider.getResource("LOCALDB", JdbcConnectionProvider.class); //$NON-NLS-1$
		Connection conn = jdbc.getConnection();
		ThreadLocalConnectionStrategy.THREAD_CONNECTION.set(conn);
		
		assertSame(conn, ThreadLocalConnectionStrategy.THREAD_CONNECTION.get());
		
		Properties properties = UtilityForBo2Test.getSampleJdbcProperties();		
		properties.setProperty("connectionStrategy", "gr.interamerican.bo2.impl.open.jdbc.ThreadLocalConnectionStrategy"); //$NON-NLS-1$ //$NON-NLS-2$
		JdbcConnectionProviderImpl jdbcWithThreadLocal = new JdbcConnectionProviderImpl(properties);
		assertTrue(jdbcWithThreadLocal.connectionStrategy instanceof ThreadLocalConnectionStrategy);
		ThreadLocalConnectionStrategy connectionStrategy = 
			(ThreadLocalConnectionStrategy) jdbcWithThreadLocal.connectionStrategy;
		assertSame(conn, jdbcWithThreadLocal.getConnection());
		assertSame(connectionStrategy.getComponent(), jdbcWithThreadLocal);
		
		provider.close();
	}

}
