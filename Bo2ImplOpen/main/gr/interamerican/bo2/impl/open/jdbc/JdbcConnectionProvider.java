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


import gr.interamerican.bo2.arch.ResourceWrapper;

import java.sql.Connection;

/**
 * Database connection provider.
 * 
 * This provider provides a database connection and the schema
 * (table qualifier) of the database. 
 * 
 */
public interface JdbcConnectionProvider extends ResourceWrapper {
	
	/**
	 * Gets a connection to the database.
	 * 
	 * @return Returns a database connection ready to be used.
	 */
    public Connection getConnection();
    
    /**
     * Gets the database schema (tables qualifier) string.
     * 
     * This string will replace the schema key used in SQL 
     * statements. If this method returns null, then there
     * will be no replacement of the schema key and the
     * SQL statement will be executed without any modification. 
     * 
     * @return Returns the schema of the database tables.
     */
	public String getDbSchema();	
	
}
