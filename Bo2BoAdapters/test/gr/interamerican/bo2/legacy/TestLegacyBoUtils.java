package gr.interamerican.bo2.legacy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.jdbc.JdbcConnectionProvider;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.samples.providers.EmptyProvider;
import interamerican.archimpl.providers.DatabaseProvider;
import interamerican.architecture.Provider;

import java.sql.Connection;

import org.junit.Test;

/**
 * unit test for CreateLega
 */
public class TestLegacyBoUtils {
	

	/**
	 * tests that a Bo-1.5 operation executes properly in a Bo2 runtime (i.e.
	 * when given a Bo-1.5 provider created using a Bo2 provider)
	 * @throws InitializationException 
	 *
	 */
	@Test
	public void testGetLegacyBoProvider_withNormalProvider() throws InitializationException  {		
		gr.interamerican.bo2.arch.Provider bo2Provider = Bo2.getDefaultDeployment().getProvider();
		Provider legacyBoProvider = LegacyBoUtils.getLegacyProvider("LOCALDB",bo2Provider); //$NON-NLS-1$
		assertNotNull(legacyBoProvider);
		try {			
			JdbcConnectionProvider jdbcMan = 
				bo2Provider.getResource("LOCALDB",JdbcConnectionProvider.class);  //$NON-NLS-1$
			DatabaseProvider dbProv = (DatabaseProvider) legacyBoProvider;		
			Connection bo2Con = jdbcMan.getConnection();
			Connection legCon = dbProv.getConnection();
			assertSame(bo2Con, legCon);
			assertEquals(jdbcMan.getDbSchema(), dbProv.getDbSchema());
		} catch (InitializationException e) {
			/* ignore this case */
		}
	}
	
	/**
	 * tests that a Bo-1.5 provider can be created without a jdbc connection.
	 * In this case, the connection will be null.
	 * @throws InitializationException 
	 *
	 */
	@Test
	public void testGetLegacyBoProvider_withEmptyProvider() throws InitializationException  {
		gr.interamerican.bo2.arch.Provider bo2Provider = new EmptyProvider();
		Provider legacyBoProvider = LegacyBoUtils.getLegacyProvider("LOCALDB",bo2Provider); //$NON-NLS-1$
		assertNotNull(legacyBoProvider);		
		DatabaseProvider dbProv = (DatabaseProvider) legacyBoProvider;
		Connection legCon = dbProv.getConnection();
		assertNull(legCon);
		String dbSchema = dbProv.getDbSchema();
		assertNull(dbSchema);
	}
	
	/**
	 * tests that a Bo-1.5 provider can be created without a jdbc connection.
	 * In this case, the connection will be null.
	 * @throws InitializationException 
	 *
	 */
	@Test
	public void testGetLegacyBoProvider_withDummyResourceManager() throws InitializationException  {
		gr.interamerican.bo2.arch.Provider manager = new EmptyProvider();
		Provider legacyBoProvider = LegacyBoUtils.getLegacyProvider("LOCALDB",manager); //$NON-NLS-1$
		assertNotNull(legacyBoProvider);		
		DatabaseProvider dbProv = (DatabaseProvider) legacyBoProvider;
		Connection legCon = dbProv.getConnection();
		assertNull(legCon);
		String dbSchema = dbProv.getDbSchema();
		assertNull(dbSchema);
	}
		
	
	

}
