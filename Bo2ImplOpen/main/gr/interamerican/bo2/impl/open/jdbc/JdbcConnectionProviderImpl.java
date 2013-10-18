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

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.utils.ProviderUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Implementation of {@link JdbcConnectionProvider} that will load
 * a database driver and create a connection.
 * 
 * Information about the driver and the connection is provided by a
 * Properties object.
 *
 */
public class JdbcConnectionProviderImpl 
implements JdbcConnectionProvider {

		
    /**
     * Input properties Property name for database schema
     */
	public static final String KEY_DBSCHEMA = "DBSCHEMA"; //$NON-NLS-1$
    /**
     * Input properties Property name for database user id
     */
	public static final String KEY_DBUSER = "DBUSER"; //$NON-NLS-1$
    /**
     * Input properties Property name for database user's password
     */
	public static final String KEY_DBPASS = "DBPASS"; //$NON-NLS-1$
    /**
     * Input properties Property name for database user's password
     */
	public static final String KEY_CONNECTIONSTRATEGY = "connectionStrategy"; //$NON-NLS-1$
	
	/**
	 * Initialization properties.
	 */
    private Properties properties;
	
	/**
	 * JDBC connection.
	 */
    private Connection connection;
	
	/**
	 * database schema string
	 */
    private String dbSchema;	
	
	/**
	 * user id for connection to the database.
	 */
    private String dbUser; 
	
	/**
	 * Password for connection to the database.
	 */
    private String dbPass; 
    
	/**
	 * Decorator.
	 */
    protected ConnectionStrategy connectionStrategy;
	
	
	
	/**
	 * Creates a JdbcConnectionProviderImpl.
	 * 
	 * @param properties 
	 *        Initialization properties.
	 *        
	 * @throws InitializationException 
	 */
	public JdbcConnectionProviderImpl(Properties properties) 
	throws InitializationException {
		this.properties = properties;
		openConnection();
	}

	public Connection getConnection() {		
		return connection;
	}

	public String getDbSchema() {		
		return dbSchema;
	}
	
	/**
	 * Parses the input properties and gets any information necessary
	 * for this object to be able to connect.
	 * 
	 * @throws InitializationException
	 *         If any mandatory property is missing.
	 */	
	protected void parseProperties() throws InitializationException {
		dbSchema = properties.getProperty(KEY_DBSCHEMA);
		dbUser = properties.getProperty(KEY_DBUSER);
		dbPass = properties.getProperty(KEY_DBPASS);
		String strategyClass = ProviderUtils.getMandatoryProperty(properties,KEY_CONNECTIONSTRATEGY);
		try {
			@SuppressWarnings("unchecked") 
			Class<ConnectionStrategy> clazz =
				(Class<ConnectionStrategy>) Class.forName(strategyClass);
			connectionStrategy = clazz.newInstance();
		} catch (ClassNotFoundException cnfe) {
			throw new InitializationException(cnfe);
		} catch (InstantiationException ie) {
			throw new InitializationException(ie);
		} catch (IllegalAccessException iae) {
			throw new InitializationException(iae);
		}
		connectionStrategy.validateSetup();
		connectionStrategy.setComponent(this);
		connectionStrategy.parseProperties();		
	}

	/**
	 * Opens the connection.
	 * 
	 * @throws InitializationException
	 */
	private void openConnection() throws InitializationException {
		parseProperties();
		connection = connectionStrategy.doConnect();		 
	}
	
	public void close() throws DataException {
		try {
			if (connection!=null) {
				if (!connection.isClosed()) {
					connection.close();
					connection = null;
				}
			}			
		} catch (SQLException e) {
			throw new DataException(e);
		} 		
	}

	/**
	 * Gets the dbUser.
	 *
	 * @return Returns the dbUser
	 */
	public String getDbUser() {
		return dbUser;
	}

	/**
	 * Gets the dbPass.
	 *
	 * @return Returns the dbPass
	 */
	public String getDbPass() {
		return dbPass;
	}

	/**
	 * Sets the connection.
	 *
	 * @param connection the connection to set
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Gets the properties.
	 *
	 * @return Returns the properties
	 */
	public Properties getProperties() {
		return properties;
	}
	

}
