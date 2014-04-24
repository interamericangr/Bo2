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

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.ResourceWrapper;
import gr.interamerican.bo2.arch.TransactionManager;
import gr.interamerican.bo2.arch.exceptions.CouldNotEnlistException;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.jdbc.JdbcConnectionProvider;
import gr.interamerican.bo2.impl.open.jee.jta.JtaTransactionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.naming.NamingException;
import javax.transaction.SystemException;

import org.enhydra.jdbc.standard.StandardXAConnectionHandle;
import org.objectweb.jotm.Jotm;

/**
 * TransactionManager implementation based on the Jotm api.
 * <br/>
 * The Jotm singleton instance is created when the first instance
 * of this class is created and it is stopped, by invoking {@link Jotm#stop()}
 * when the count of {@link Provider} instances accessing an instance
 * of {@link JotmTransactionManager} and, therefore, the Jotm singleton
 * are reduced to zero.
 * <br/>
 * Note that it is possible that a program that spawns many {@link Provider} 
 * instances will at some point have all its created Providers closed, but
 * has yet to create more Providers. In this case, the Jotm singleton will stop
 * but it will be replaced, as soon as another Provider gets instantiated by
 * a new instance.
 * <br/>
 * XaPool does not close physical pooled JDBC connections when closing
 * the {@link StandardXAConnectionHandle}. This is not compatible with
 * the design of Bo2, which does not provide the concept a connection pool.
 * Bo2 expects the physical connection to close when {@link Connection#close()}
 * is invoked.
 * <br/> 
 * This mismatch is handled here. A collection of pooled connections is maintained 
 * and the physical connections are closed, if still open, when this TransactionManager closes.
 */
public class JotmTransactionManager extends JtaTransactionManager  {
	
	/**
	 * Jotm transaction management service singleton instance.
	 */
	private static Jotm JOTM;
	
	/**
	 * All providers (implicitly) accessing the JOTM singleton instance.
	 */
	private static Set<Provider> PROVIDERS_ACCESSING_JOTM = new HashSet<Provider>();
	
	/**
	 * A collection of pooled connections opened in the scope of this {@link TransactionManager}.
	 */
	private Set<StandardXAConnectionHandle> pooledConnections = new HashSet<StandardXAConnectionHandle>();
	
	/**
	 * Creates a new JotmTransactionManager object.
	 *  
	 * @param owner 
	 *        {@link Provider} instance that initialized this {@link TransactionManager}
	 */
	public JotmTransactionManager(Provider owner) {
		initialize(owner);
		ut = JOTM.getUserTransaction();
		try {
			ut.setTransactionTimeout(Integer.MAX_VALUE);
		} catch (SystemException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Initialize {@link Jotm}, if it has not been initialized yet and register
	 * the provider with the providers accessing the Jotm instance.
	 * 
	 * @param owner 
	 *        {@link Provider} instance that initialized this {@link TransactionManager}
	 */
	private static synchronized void initialize(Provider owner) {
		if(JOTM==null) {
			try {
				JOTM = new Jotm(true, false);
			} catch (NamingException e) {
				throw new RuntimeException(e);
			}
		}
		PROVIDERS_ACCESSING_JOTM.add(owner);
	}

	@Override
	public void enList(ResourceWrapper resource) throws CouldNotEnlistException {
		super.enList(resource);
		if (resource instanceof JdbcConnectionProvider) {
			JdbcConnectionProvider jdbc = (JdbcConnectionProvider) resource;
			Connection connection = jdbc.getConnection();
			if(connection instanceof StandardXAConnectionHandle) {
				pooledConnections.add((StandardXAConnectionHandle) connection);
			}
		}
		/*
		 * Normally, this is where we should demarcate the transaction boundaries
		 * for the specific resource (if it is transactional anyway). 
		 * 
		 * However, the JOTM api handles this implicitly by setting the jdbc connection 
		 * autocommit to false, the first time the connection is going to be used and a 
		 * transaction is active. It also resets the autocommit value to the value the
		 * connection had initially, after the transaction completes. For this reason, 
		 * it is a mistake to change the autocommit here, and we should leave it to whatever
		 * value it has (normally true).
		 */
	}
	
	@Override
	public void close() {
		super.close();
		synchronized (JotmTransactionManager.class) {
			for(StandardXAConnectionHandle c : pooledConnections) {
				closePhysicalConnection(c);
			}
			pooledConnections.clear();
			PROVIDERS_ACCESSING_JOTM.remove(Bo2Session.getProvider());
			if(PROVIDERS_ACCESSING_JOTM.isEmpty()) {
				JOTM.stop();
				JOTM = null;
			}
		}
	}
	
	/**
	 * Closes the physical JDBC connections.
	 * 
	 * @param c
	 */
	void closePhysicalConnection(StandardXAConnectionHandle c) {
		Connection physical = c.con;
		try {
			if(!physical.isClosed()) {
				physical.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * @return Returns the delegate transaction manager.
	 */
	public org.objectweb.transaction.jta.TransactionManager getJotmTransactionManager() {
		return JOTM.getTransactionManager();
	}

}
