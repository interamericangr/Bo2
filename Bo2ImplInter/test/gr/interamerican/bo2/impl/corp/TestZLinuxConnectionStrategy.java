package gr.interamerican.bo2.impl.corp;

import static gr.interamerican.bo2.impl.corp.ZLinuxConnectionStrategy.KEY_DBURL;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.jdbc.JdbcConnectionProviderImpl;
import gr.interamerican.bo2.impl.open.jdbc.JdbcConnectionsTransactionManager;
import gr.interamerican.bo2.samples.providers.MockTransactionManager;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;
import gr.interamerican.bo2.utils.ReflectionUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link ZLinuxConnectionStrategy}
 * 
 *
 */
public class TestZLinuxConnectionStrategy 
extends AbstractTestZLinuxConnectionStrategy<ZLinuxConnectionStrategy> {
	
	/**
	 * tests setup.
	 * @throws InitializationException 
	 */
	@Before
	public void setup() throws InitializationException {
		jdbcConnectionProvider = new JdbcConnectionProviderImpl(UtilityForBo2Test.getSampleJdbcProperties());
		strategy = new ZLinuxConnectionStrategy();
		strategy.setComponent(jdbcConnectionProvider);
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
	 * if a mandatory property DBURL is missing.
	 * 
	 * @throws InitializationException 
	 */
	@Test(expected=InitializationException.class)
	public void testParseProperties_MissingUrl() throws InitializationException {		
		jdbcConnectionProvider.getProperties().remove(KEY_DBURL);	
		strategy.parseProperties();
	}
	
	/**
	 * Tests validateSetup
	 */
	@Test
	public void testValidateSetup_valid() {
		UtilityForBo2Test.setCurrentTransactionManager(JdbcConnectionsTransactionManager.class);
		ReflectionUtils.invokeMethodByUniqueName(strategy, "validateSetup", new Object[]{}); //$NON-NLS-1$
	}
	
	/**
	 * Tests validateSetup with invalid config.
	 */
	@Test (expected=RuntimeException.class)
	public void testValidateSetup_invalid() {
		UtilityForBo2Test.setCurrentTransactionManager(MockTransactionManager.class);
		ReflectionUtils.invokeMethodByUniqueName(strategy, "validateSetup", new Object[]{}); //$NON-NLS-1$
	}

}
