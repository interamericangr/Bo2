package gr.interamerican.bo2.impl.corp;

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.TransactionManager;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.jdbc.JdbcConnectionProviderImpl;
import gr.interamerican.bo2.impl.open.jdbc.JdbcConnectionsTransactionManager;
import gr.interamerican.bo2.impl.open.jotm.JotmConnectionStrategy;
import gr.interamerican.bo2.impl.open.jotm.JotmTransactionManager;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.impl.open.utils.Bo2Deployment;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;
import gr.interamerican.bo2.utils.ReflectionUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit test for {@link ZLinuxConnectionStrategy}
 * 
 *
 */
public class TestJotmZLinuxConnectionStrategy 
extends AbstractTestZLinuxConnectionStrategy<JotmZLinuxConnectionStrategy>{
	
	/**
	 * tests setup.
	 * @throws InitializationException 
	 */
	@Before
	public void setup() throws InitializationException {
		jdbcConnectionProvider = new JdbcConnectionProviderImpl(UtilityForBo2Test.getSampleJdbcProperties());
		strategy = new JotmZLinuxConnectionStrategy();
		strategy.setComponent(jdbcConnectionProvider);		

		Bo2Deployment jta = Bo2.getDeployment(UtilityForBo2Test.BATCH_JTA_TRAN);
		Bo2Session.setProvider(jta.getProvider());
	}
	
	/**
	 * cleanup.
	 */
	@After
	public void cleanup() {
		Bo2Session.setProvider(null);
	}
	
	/**
	 * Tests validateSetup
	 * @throws InitializationException 
	 */
	@Test
	public void testValidateSetup_valid() throws InitializationException {
		Class<? extends TransactionManager> tmClass = JotmTransactionManager.class;
		Provider prov = Mockito.mock(Provider.class);
		JotmTransactionManager tm = (JotmTransactionManager) ReflectionUtils.newInstance(tmClass, prov);
		Mockito.when(prov.getTransactionManager()).thenReturn(tm);
		Bo2Session.setProvider(prov);
		ReflectionUtils.invokeMethodByUniqueName(strategy, "validateSetup", new Object[]{}); //$NON-NLS-1$
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
