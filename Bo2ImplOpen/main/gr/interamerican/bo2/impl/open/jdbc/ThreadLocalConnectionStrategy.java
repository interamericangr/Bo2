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

import java.sql.Connection;


/**
 * Implementation of {@link JdbcConnectionProvider} that expects to 
 * get an open database connection from the current thread.
 * 
 * This connection strategy is provided for use in cases where there
 * is no use of a JTA transaction manager, and the only way to provide
 * an atomic transactions is to use the same JDBC connection transaction
 * mechanism.
 */
public class ThreadLocalConnectionStrategy 
extends ConnectionStrategy {
	/**
	 * Connection of the current thread.
	 */
	public static final ThreadLocal<Connection> THREAD_CONNECTION = new ThreadLocal<Connection>() {
		private Connection connection;
		
		@Override
		public Connection get() {
			return this.connection;
		}		
		@Override
		public void set(Connection value) {
			this.connection = value;
		}
	};
  
	@Override
	public Connection doConnect() throws InitializationException {		
		return THREAD_CONNECTION.get();		
	}
	
	@Override
	public void parseProperties() throws InitializationException {
		/* empty */
	}
	
	@Override
	protected Class<?>[] compatibleTransactionManagerImplementations() {
		return new Class<?>[]{JdbcConnectionsTransactionManager.class};
	}

}
