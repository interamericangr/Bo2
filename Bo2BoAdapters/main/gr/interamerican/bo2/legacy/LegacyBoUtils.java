package gr.interamerican.bo2.legacy;

import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.jdbc.JdbcConnectionProvider;
import gr.interamerican.bo2.impl.open.properties.PropertiesProvider;
import gr.interamerican.bo2.utils.ExceptionUtils;
import interamerican.architecture.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creates a Bo-1.5 provider given a Bo-2 provider type.
 * 
 * TODO: implement other provider types
 */
public class LegacyBoUtils {
	/**
	 * logger.
	 */
	private static Logger logger = LoggerFactory.getLogger(LegacyBoUtils.class);

	/**
	 * @param managerName 
	 *        Name of resource manager.
	 * @param bo2Provider
	 *            a Bo-2 provider
	 * @return returns a Bo-1.5 provider 
	 */
	public static Provider getLegacyProvider(String managerName, gr.interamerican.bo2.arch.Provider bo2Provider) {		
		LegacyBoProviderImpl impl = new LegacyBoProviderImpl();
		setJdbcConnectionProvider(managerName, bo2Provider, impl);
		setPropertiesProvider(managerName, bo2Provider, impl);
		impl.setCurrentUser();
		return impl;
	}
	
	
	/**
	 * Adds support of DatabaseProvider to the specified LegacyBoProviderImpl.
	 * 
	 * @param managerName
	 *        Name of manager.
	 * @param bo2Provider
	 *        Provider that manages the resources injected to the LegacyBoProviderImpl
	 * @param impl
	 *        LegacyBoProviderImpl to add support of DatabaseProvider
	 *        
	 */
	public static void setJdbcConnectionProvider(String managerName, 
			gr.interamerican.bo2.arch.Provider bo2Provider, LegacyBoProviderImpl impl) {		
		try {
			JdbcConnectionProvider jdbcMan = 
				bo2Provider.getResource(managerName, JdbcConnectionProvider.class);
			if (jdbcMan!=null) {
				impl.setConnection(jdbcMan.getConnection());
				impl.setDbSchema(jdbcMan.getDbSchema());				
			}
		} catch (InitializationException e) {	
			String stacktrace = ExceptionUtils.getThrowableStackTrace(e);
			logger.error(stacktrace); 
			logger.warn("Created legacy JdbcConnectionProvider without jdbc connection");				 //$NON-NLS-1$
			
		}		
	}
	
	/**
	 * Adds support of PropertiesProvider to the specified LegacyBoProviderImpl.
	 * 
	 * @param managerName
	 *        Name of manager.
	 * @param bo2Provider
	 *        Provider that manages the resources injected to the LegacyBoProviderImpl
	 * @param impl
	 *        LegacyBoProviderImpl to add support of PropertiesProvider
	 *        
	 */
	public static void setPropertiesProvider(String managerName, 
			gr.interamerican.bo2.arch.Provider bo2Provider, LegacyBoProviderImpl impl) {
		try {
			PropertiesProvider propMan = 
				bo2Provider.getResource(managerName, PropertiesProvider.class);
			if (propMan!=null) {
				impl.setProperties(propMan.getProperties());
			}
		} catch (InitializationException e) {
			String stacktrace = ExceptionUtils.getThrowableStackTrace(e);
			logger.error(stacktrace); 
			logger.warn("Created legacy PropertiesProvider with null properties"); //$NON-NLS-1$
			impl.setProperties(null);
		}
	}

	/**
	 * Worker lifecycle management hack, because certain legacy operation implementations
	 * do not allow multiple worker executions between init/open and close.
	 * <br/>
	 * This assumes that the provided instance has already called 
	 * {@link Operation#init(gr.interamerican.bo2.arch.Provider)} and {@link Operation#open()}.
	 * <br/>
	 * This method makes sure that the operation gets closed regardless of whether execute()
	 * completes successfully.
	 * 
	 * @param operation
	 * @throws DataException
	 * @throws LogicException
	 */
	public static void executeOnceSafely(Operation operation) throws DataException, LogicException {
		try {
			operation.execute();
		} finally {
			operation.close();
		}
	}

}
