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
package gr.interamerican.bo2.impl.open.jotm;

import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.jdbc.DriverConnectionStrategy;
import gr.interamerican.bo2.utils.StringUtils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.XAConnection;

import org.enhydra.jdbc.standard.StandardXADataSource;

/**
 * Extension of {@link DriverConnectionStrategy} that provides Jdbc Connections
 * enlisted in the {@link JotmTransactionManager} instance of the current thread.
 */
public class JotmConnectionStrategy extends DriverConnectionStrategy {

	@Override
	public Connection doConnect() throws InitializationException {
		try {
			StandardXADataSource ds = new StandardXADataSource();
			JotmUtils.enListTransactionalDataSource(ds);
			ds.setDriverName(dbDriver);
			ds.setUrl(dbUrl);
			
			XAConnection xaconn;
			if (StringUtils.isNullOrBlank(component.getDbUser())) {
				xaconn = ds.getXAConnection();	        	
		    } else {
		    	xaconn = ds.getXAConnection(component.getDbUser(),component.getDbPass());	        	
		    }
			return xaconn.getConnection();
		}catch (SQLException e) {
			throw new InitializationException(e);
		} catch (IllegalStateException e) {
			throw new InitializationException(e);
		} 
	}
	
	@Override
	protected Class<?>[] compatibleTransactionManagerImplementations() {
		return new Class<?>[]{JotmTransactionManager.class};
	}

}
