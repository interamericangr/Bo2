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

import static org.junit.Assert.assertSame;
import gr.interamerican.bo2.Bo2MavenTestEnvironmentInitialization;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.jdbc.JdbcConnectionProvider;
import gr.interamerican.bo2.impl.open.jdbc.ThreadLocalConnectionStrategy;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;
import gr.interamerican.bo2.utils.Bo2UtilsEnvironment;

import java.nio.charset.Charset;
import java.sql.Connection;

import org.junit.Assert;
import org.junit.Test;

/**
 * The Class TestBo2.
 */
public class TestBo2 {
		
	
	/**
	 * Unit test for setThreadLocalConnection(connection).
	 *
	 * @throws InitializationException the initialization exception
	 * @throws DataException the data exception
	 */
	@Test
	public void testSetThreadLocalConnection() throws InitializationException, DataException {
		Provider p =  Bo2.getDeployment(UtilityForBo2Test.BATCH_NO_TRAN).getProvider();	
		JdbcConnectionProvider localJdbc = p.getResource("LOCALDB",JdbcConnectionProvider.class); //$NON-NLS-1$
		Connection conn = localJdbc.getConnection();		
		Bo2.setThreadLocalConnection(conn);
		assertSame(conn, ThreadLocalConnectionStrategy.THREAD_CONNECTION.get());
		p.close();
		
		Assert.assertTrue(Bo2MavenTestEnvironmentInitialization.initialized);
	}
	
	/**
	 * Tests that Bo2UtilsEnvironment is initialized properly.
	 */
	@Test
	public void testBo2UtilsEnvironmentInit() {
		String resourceFileEncoding = Bo2.getDefaultDeployment().getDeploymentBean().getResourceFileEncoding();
		Charset resourceFileCharset = Bo2UtilsEnvironment.get().getDefaultResourceFileCharset();
		Assert.assertSame(Charset.forName(resourceFileEncoding), resourceFileCharset);
		
		String textEncoding = Bo2.getDefaultDeployment().getDeploymentBean().getTextEncoding();
		Charset textCharset = Bo2UtilsEnvironment.get().getDefaultTextCharset();
		Assert.assertSame(Charset.forName(textEncoding), textCharset);
	}
	
}
