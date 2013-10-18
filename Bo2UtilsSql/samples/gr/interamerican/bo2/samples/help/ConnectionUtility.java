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
package gr.interamerican.bo2.samples.help;

import gr.interamerican.bo2.utils.CollectionUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Utility class that opens a connection.
 */
public class ConnectionUtility {
	
    /**
     * key for DB user.
     */
    private static final String KEY_DBUSER="DBUSER"; //$NON-NLS-1$
    /**
     * key for DB pass.
     */
    private static final String KEY_DBPASS="DBPASS"; //$NON-NLS-1$
    /**
     * key for DB URL.
     */
    private static final String KEY_DBURL="DBURL"; //$NON-NLS-1$
    /**
     * key for DB driver.
     */
    private static final String KEY_DBDRIVER="DBDRIVER"; //$NON-NLS-1$
    
    
	/**
	 * Opens a connection.
	 * 
	 * @return Returns the connection.
	 * 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		String path = "/gr/interamerican/bo2/deployparms/managers/localdb/of.properties";			 //$NON-NLS-1$
		Properties prop = CollectionUtils.readEnhancedProperties(path);
		String dbDriver = prop.getProperty(KEY_DBDRIVER);
		String dbUrl = prop.getProperty(KEY_DBURL);
		String dbUser = prop.getProperty(KEY_DBUSER);
		String dbPass = prop.getProperty(KEY_DBPASS);
		Class.forName(dbDriver);
		return DriverManager.getConnection(dbUrl,dbUser,dbPass);
	}

}
