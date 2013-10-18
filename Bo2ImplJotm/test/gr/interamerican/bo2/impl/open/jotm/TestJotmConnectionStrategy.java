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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.TransactionManager;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.jdbc.JdbcConnectionProviderImpl;
import gr.interamerican.bo2.impl.open.jdbc.JdbcConnectionsTransactionManager;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;
import gr.interamerican.bo2.utils.ReflectionUtils;

import java.sql.Connection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * Test {@link JotmConnectionStrategy}
 */
public class TestJotmConnectionStrategy {

	/**
	 * Fixture.
	 */
	JotmTransactionManager jotm;

	/**
	 * Fixtures.
	 */
	@Before
	public void setup() {
		Provider prov = mock(Provider.class);
		jotm = new JotmTransactionManager(prov);
		when(prov.getTransactionManager()).thenAnswer(new Answer<TransactionManager>() {
			public TransactionManager answer(InvocationOnMock invocation) throws Throwable {
				return jotm;
			}
		});
		Bo2Session.setProvider(prov);
	}

	/**
	 * Teardown fixtures.
	 */
	@After
	public void teardown() {
		jotm.close();
		Bo2Session.setProvider(null);
	}

	/**
	 * Test doConnect
	 * @throws InitializationException
	 */
	@Test
	public void testDoConnect() throws InitializationException {
		JotmConnectionStrategy subject = new JotmConnectionStrategy();
		subject.setComponent(new JdbcConnectionProviderImpl(UtilityForBo2Test.getSampleJdbcPropertiesForJta()));
		subject.parseProperties();
		Connection conn = subject.doConnect();
		Assert.assertNotNull(conn);
	}

	/**
	 * Tests validateSetup
	 */
	@Test
	public void testValidateSetup_valid() {
		JotmConnectionStrategy subject = new JotmConnectionStrategy();
		ReflectionUtils.invokeMethodByUniqueName(subject, "validateSetup", new Object[]{}); //$NON-NLS-1$
	}

	/**
	 * Tests validateSetup with invalid config.
	 */
	@Test (expected=RuntimeException.class)
	public void testValidateSetup_invalid() {
		Class<? extends TransactionManager> tmClass = JdbcConnectionsTransactionManager.class;
		UtilityForBo2Test.setCurrentTransactionManager(tmClass);
		JotmConnectionStrategy subject = new JotmConnectionStrategy();
		ReflectionUtils.invokeMethodByUniqueName(subject, "validateSetup", new Object[]{}); //$NON-NLS-1$
	}

}
