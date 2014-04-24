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
import gr.interamerican.bo2.arch.TransactionManager;
import gr.interamerican.bo2.arch.exceptions.CouldNotBeginException;
import gr.interamerican.bo2.arch.exceptions.CouldNotCommitException;
import gr.interamerican.bo2.arch.exceptions.CouldNotDelistException;
import gr.interamerican.bo2.arch.exceptions.CouldNotEnlistException;
import gr.interamerican.bo2.arch.exceptions.CouldNotRollbackException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.impl.open.job.JobCapableTransactionManager;
import gr.interamerican.bo2.impl.open.job.JobSchedulerProvider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * This is an inadequate implementation of TransactionManager based on an
 * SQL Connection.
 * 
 * This is a temporary solution until a proper JTA implementation is in place.
 */
public class JdbcConnectionsTransactionManager 
extends JobCapableTransactionManager
implements TransactionManager {
	
	/**
	 * SQL connection that manages the transactions.
	 */
	Set<Connection> connections = new HashSet<Connection>();
	
	/**
	 * Indicates if a connection is enlisted.
	 * 
	 * @param connection
	 * @return Returns true if the connection is included in the transaction.
	 */
	public boolean isEnlisted(Connection connection) {
		return connections.contains(connection);
	}
	
	/**
	 * Creates a new JdbcTransactionManager with an sql Connection. 
	 */
	public JdbcConnectionsTransactionManager() {
		/* empty */
	}	

	public void begin() throws CouldNotBeginException {
		try {
			for (Connection connection : connections) {
				connection.setAutoCommit(false);
			}			
		} catch (SQLException e) {
			throw new CouldNotBeginException(e);
		}
	}
	
	public void commit() throws CouldNotCommitException {
		try {
			for (Connection connection : connections) {
				connection.commit();
				connection.setAutoCommit(true);
			}
			
			submitScheduledJobs();
			
		} catch (SQLException e) {
			throw new CouldNotCommitException(e);
		} catch (DataException e) { //job submission
			throw new RuntimeException(e);
		} finally {
			clearScheduledJobs();
		}
		
	}
	
	public void rollback() throws CouldNotRollbackException {
		try {
			for (Connection connection : connections) {
				connection.rollback();
				connection.setAutoCommit(true);
			}
		} catch (SQLException e) {
			throw new CouldNotRollbackException(e);
		} finally {
			clearScheduledJobs();
		}
	}
	
	@Override
	public void enList(ResourceWrapper resource) throws CouldNotEnlistException {
		super.enList(resource);
		if (resource instanceof JdbcConnectionProvider) {
			JdbcConnectionProvider jdbc = (JdbcConnectionProvider) resource;
			Connection connection = jdbc.getConnection();
			try {
				if(!isEnlisted(connection)) {
					connection.setAutoCommit(false);
					connections.add(connection);
				}
			} catch (SQLException e) {
				throw new CouldNotEnlistException(e);
			}
		}
	}

	@Override
	public void deList(ResourceWrapper resource) throws CouldNotDelistException {
		super.deList(resource);
		if (resource instanceof JdbcConnectionProvider) {
			JdbcConnectionProvider jdbc = (JdbcConnectionProvider) resource;
			Connection connection = jdbc.getConnection();
			try {
				connection.setAutoCommit(true);
				connections.remove(connection);
			} catch (SQLException e) {
				throw new CouldNotDelistException(e);
			}
		}
	}

}
