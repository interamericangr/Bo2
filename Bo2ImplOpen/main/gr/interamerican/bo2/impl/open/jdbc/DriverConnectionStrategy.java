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

import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.utils.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Implementation of {@link JdbcConnectionProvider} that will load
 * a database driver and create a connection.
 * 
 * Information about the driver and the connection is provided by a
 * Properties object.
 *
 */
public class DriverConnectionStrategy 
extends ConnectionStrategy {		
   
    /**
     * Input properties Property name for database URL.
     */
    static final String KEY_DBURL="DBURL"; //$NON-NLS-1$
    /**
     * Input properties Property name for database driver class
     */
    static final String KEY_DBDRIVER="DBDRIVER"; //$NON-NLS-1$    
		
	
	/**
	 * database URL.
	 */
	protected String dbUrl;
	
	/**
	 * Database driver class.
	 */
	protected String dbDriver;		
	
	@Override
	public void parseProperties() throws InitializationException {				
		dbUrl = getMandatoryProperty(KEY_DBURL);
		dbDriver = getMandatoryProperty(KEY_DBDRIVER);		
	}	

	@Override
	public Connection doConnect() throws InitializationException {
		try {
			Class.forName(dbDriver);			
			if (StringUtils.isNullOrBlank(component.getDbUser())) {
				return DriverManager.getConnection(dbUrl);	        	
		    } else {
		    	return DriverManager.getConnection(dbUrl,component.getDbUser(),component.getDbPass());	        	
		    }
		} catch (ClassNotFoundException cnfe) {
			throw new InitializationException(cnfe);
		} catch (SQLException sqle) {
			throw new InitializationException(sqle);
		}
	}

	@Override
	protected Class<?>[] compatibleTransactionManagerImplementations() {
		return new Class<?>[]{JdbcConnectionsTransactionManager.class};
	}

}
