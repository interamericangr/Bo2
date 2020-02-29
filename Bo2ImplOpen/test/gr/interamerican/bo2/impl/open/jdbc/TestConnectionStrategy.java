package gr.interamerican.bo2.impl.open.jdbc;

import gr.interamerican.bo2.arch.TransactionManager;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;

import java.sql.Connection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The Class TestConnectionStrategy.
 */
public class TestConnectionStrategy {
	
	/**
	 * tests setup.
	 *
	 * @throws InitializationException the initialization exception
	 */
	@Before
	public void setup() throws InitializationException {
		Bo2Session.setProvider(Bo2.getProvider());
	}
	
	/**
	 * tear down.
	 */
	@After
	public void teardown() {
		Bo2Session.setProvider(null);
	}
	
	/**
	 * tests setup.
	 *
	 * @throws InitializationException the initialization exception
	 */
	@Test(expected=InitializationException.class)
	public void testValidateSetup() throws InitializationException {
		JdbcConnectionProviderImpl jdbc = new JdbcConnectionProviderImpl(UtilityForBo2Test.getSampleJdbcProperties());
		ConnectionStrategy strategy = new ConnectionStrategy() {
			
			@Override
			public void parseProperties() throws InitializationException {
				//EMPTY
			}
			
			@Override
			public Connection doConnect() throws InitializationException {
				return null;
			}
			
			@Override
			protected Class<?>[] compatibleTransactionManagerImplementations() {
				return new Class<?>[]{TransactionManager.class};
			}
		};
		
		strategy.setComponent(jdbc);
		strategy.validateSetup();
	}

}
