package gr.interamerican.bo2.impl.corp;

import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.jdbc.ConnectionStrategy;
import gr.interamerican.bo2.impl.open.jdbc.JdbcConnectionsTransactionManager;

import java.sql.Connection;

import jwf.JdbcProviderForBatch;

/**
 * ConnectionStrategy based on <code>jwf.JdbcProviderForBatch</code>.
 */
public class ZLinuxConnectionStrategy extends ConnectionStrategy {
	
	/**
     * Input properties Property name for database URL.
     */
    static final String KEY_DBURL="DBURL"; //$NON-NLS-1$
    
    /**
     * Input properties Property name for database jdbc driver.
     */
    static final String KEY_DBDRIVER = "DBDRIVER"; //$NON-NLS-1$
    
	/**
     * Input properties Property name for database URL.
     */
    static final String KEY_JDBCPROVIDERCLASS="JWF_JDBCPROVIDER_CLASS"; //$NON-NLS-1$
	
    /**
     * Key for database URL.
     */
	protected String dbUrl;
	
	/**
	 * Key for the class that connects to the database.
	 */
	protected String jdbcProviderClassName;
	
	/**
	 * Database jdbc driver class name.
	 */
	protected String dbDriver;
	
	@Override
	public Connection doConnect() throws InitializationException {
		/*
		 * synchronized statically due to a bug in jdbcconn.jar
		 */
		synchronized (ZLinuxConnectionStrategy.class) {
			try {
				return getJdbcProviderForBatch().doConnect(dbUrl);
			}catch (Exception e) {
				throw new InitializationException(e);
			}
		}
	}
	
	/**
	 * Gets a JdbcProviderForBatch instance.
	 * 
	 * @return JdbcProviderForBatch
	 * 
	 * @throws InitializationException
	 */
	protected JdbcProviderForBatch getJdbcProviderForBatch() throws InitializationException {
		try {
			Class<?> jdbcProviderClass = Class.forName(jdbcProviderClassName);
			return (JdbcProviderForBatch) jdbcProviderClass.newInstance();
		} catch (ClassNotFoundException e) {
			throw new InitializationException(e);
		} catch (InstantiationException e) {
			throw new InitializationException(e);
		} catch (IllegalAccessException e) {
			throw new InitializationException(e);
		} 
	}

	@Override
	public void parseProperties() throws InitializationException {
	    dbUrl = getMandatoryProperty(KEY_DBURL);	
	    jdbcProviderClassName = getMandatoryProperty(KEY_JDBCPROVIDERCLASS);
	    dbDriver=getMandatoryProperty(KEY_DBDRIVER);
	}
	
	@Override
	protected Class<?>[] compatibleTransactionManagerImplementations() {
		return new Class<?>[]{JdbcConnectionsTransactionManager.class};
	}

}
