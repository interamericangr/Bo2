package gr.interamerican.bo2.impl.corp;

import static gr.interamerican.bo2.impl.corp.ZLinuxConnectionStrategy.KEY_JDBCPROVIDERCLASS;
import static org.junit.Assert.assertNotNull;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.corp.samples.FakeJdbcProviderForBatch;
import gr.interamerican.bo2.impl.open.jdbc.JdbcConnectionProviderImpl;

import java.sql.SQLException;

import org.junit.Test;

/**
 * Abstract base class for testing {@link ZLinuxConnectionStrategy}
 * and its sub-types.
 * 
 * Concrete implementations must initialize the <code>provider</code>
 * and <code>strategy</code> fields before each test.
 * 
 * @param <T> 
 *        Type of {@link ZLinuxConnectionStrategy} to test.
 */
public abstract class AbstractTestZLinuxConnectionStrategy
<T extends ZLinuxConnectionStrategy> {
	
	/**
	 * Provider;
	 */
	protected JdbcConnectionProviderImpl jdbcConnectionProvider;
	/**
	 * object to test.
	 */
	protected T strategy;
			
	
	/**
	 * Tests that failure throws the appropriate Exception
	 * if the connection fails.
	 * 
	 * @throws InitializationException 
	 * @throws SQLException 
	 */
	@Test()
	public void testDoConnect_success() throws InitializationException, SQLException {
		String jdbcClass = FakeJdbcProviderForBatch.class.getName();
		jdbcConnectionProvider.getProperties().setProperty(KEY_JDBCPROVIDERCLASS, jdbcClass);
		strategy.parseProperties();		
		strategy.doConnect();
		assertNotNull(jdbcConnectionProvider.getConnection());
		jdbcConnectionProvider.getConnection().close();
	}
	


	

}
