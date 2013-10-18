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
package gr.interamerican.bo2.impl.open.jee.jdbc;

import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.jdbc.ConnectionStrategy;
import gr.interamerican.bo2.impl.open.jdbc.JdbcConnectionProvider;
import gr.interamerican.bo2.impl.open.jee.jta.J2eeJtaTransactionManager;
import gr.interamerican.bo2.utils.StringUtils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


/**
 * Implementation of {@link JdbcConnectionProvider} that will get
 * a database connection from a JNDI DataSource.
 *
 */
public class JndiConnectionStrategy 
extends ConnectionStrategy {
		
   
    /**
     * Input properties Property name for jndi name.
     */
    static final String KEY_DBJNDINAME="DBJNDINAME"; //$NON-NLS-1$  
    
    /**
     * Datasource JNDI name.
     */
    String dbJndiName;
  
	
	@Override
	public void parseProperties() throws InitializationException {		
		dbJndiName = getMandatoryProperty(KEY_DBJNDINAME);
	}
	
	
	@Override
	public Connection doConnect() throws InitializationException {
		return getConnectionFromJndi(dbJndiName);		
	}
	
	/**
	 * Gets a connection from a JNDI reference.
	 * 
	 * If the property DBUSER exists on the properties file,
	 * then it will find the DBPASS property and the method 
	 * will try to connect using the user id and password.
	 * If the property DBUSER is not set or empty, then the
	 * method will try to connect without credentials.
	 * 
	 * @param jndiKey Key for JNDI lookup.
	 * 
	 * @return Returns the SQL connection that is returned by
	 *         the DataSource that is located with the jndiKey.
	 *          
	 * @throws InitializationException
	 */
	private Connection getConnectionFromJndi(String jndiKey) 
	throws InitializationException {
		try {	        	        
	        DataSource ds = getDatasourceFromJndi(jndiKey);	       
	        if (StringUtils.isNullOrBlank(component.getDbUser())) {
	        	return ds.getConnection();	        	
	        } else {
	        	return ds.getConnection(component.getDbUser(),component.getDbPass());	        	
	        }	
	    } catch(SQLException sqle) {
	    	throw new InitializationException(sqle);
	    } catch (RuntimeException re) {
	    	throw new InitializationException(re);
		}
	}
	
	/**
	 * Gets a connection from a JNDI reference.
	 * 
	 * @param jndiKey Key for JNDI lookup.
	 * 
	 * @return Returns the SQL connection that is returned by
	 *         the DataSource that is located with the jndiKey.
	 *          
	 * @throws InitializationException
	 */
	DataSource getDatasourceFromJndi(String jndiKey) 
	throws InitializationException {
		try {
	        InitialContext ctx = new InitialContext();	        
	        return (DataSource) ctx.lookup(jndiKey);	        
	    } catch(NamingException e) {
	        throw new InitializationException(e);
	    }
	}
	
	@Override
	protected Class<?>[] compatibleTransactionManagerImplementations() {
		return new Class<?>[]{J2eeJtaTransactionManager.class};
	}

}
