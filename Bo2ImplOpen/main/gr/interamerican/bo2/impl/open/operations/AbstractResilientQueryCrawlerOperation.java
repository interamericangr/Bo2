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
package gr.interamerican.bo2.impl.open.operations;

import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.Query;
import gr.interamerican.bo2.arch.exceptions.CouldNotBeginException;
import gr.interamerican.bo2.arch.exceptions.CouldNotRollbackException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcess;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.impl.open.utils.Bo2Deployment;
import gr.interamerican.bo2.impl.open.workers.AbstractResourceConsumer;

/**
 * An {@link AbstractResilientQueryCrawlerOperation} is an Operation that
 * performs work on each row of the result of a Query execution in a manner
 * that if this work fails for a specific row the execution will continue for 
 * the rest of the rows. Each row is processed in an independent Transaction. 
 * For each row that is processed successfully, the transaction is committed.
 * <br/>
 * This operation requires a Query instance and an Operation class. The query is 
 * used to produce the rows which are used in order to provide input for the 
 * Operation. The user must implement {@link #beforeEachRow()} in order to use
 * the current query row to give input to the operation and {@link #afterEachSuccessfulRow()}
 * in order to retrieve results from the operation.
 * <br/>
 * If a row fails, that transaction is rolled back, resources are cleaned up and
 * a new Provider as well as a new Operation instance are created. The number of
 * Providers created are n+1 where n is the number of failing rows.
 * <br/>
 * Note that the Query will use the Provider of the AbstractResilientQueryCrawlerOperation
 * instance, however, the Operation is executed with a different Provider(s). For this
 * reason the user should not rely in the {@link Bo2Session#getProvider()} call for the
 * work performed within the Operation or its child workers. 
 * 
 * @param <O>
 *        Operation 
 * @param <Q>
 *        Query
 *        
 * @deprecated Use {@link BatchProcess} instead.        
 */
public abstract class AbstractResilientQueryCrawlerOperation
<O extends Operation, Q extends Query>
extends AbstractQueryCrawlerOperation<Q> {
	
	/**
	 * Operation to execute.
	 */
	protected O operation;
	
	/**
	 * Operation class.
	 */
	private Class<O> operationClass;
	
	/**
	 * Deployment properties for Provider that handles each row.
	 */
	private String deploymentPathForHandlingRow;
	
	/**
	 * Provider for the row handling Operation.
	 */
	private Provider rowHandlingProvider;
	
	/**
	 * Manager name for row handling Operation.
	 */
	private String rowManager;

	/**
	 * Creates a new AbstractResilientQueryCrawlerOperation object. 
	 * @param query
	 * @param operationClass 
	 */
	public AbstractResilientQueryCrawlerOperation(Q query, Class<O> operationClass) {
		this(query, operationClass, null, null);
	}
	
	/**
	 * Creates a new AbstractResilientQueryCrawlerOperation object. 
	 * @param query
	 * @param operationClass 
	 * @param rowManager 
	 */
	public AbstractResilientQueryCrawlerOperation(Q query, Class<O> operationClass, String rowManager) {
		this(query, operationClass, null, rowManager);
	}
	
	/**
	 * Creates a new AbstractResilientQueryCrawlerOperation object. 
	 * @param query
	 *        Query that fetches rows.
	 * @param operationClass 
	 *        Row handling Operation class.
	 * @param deploymentPathForHandlingRow
	 *        Deployment properties for Provider that handles each row.
	 * @param rowManager
	 *        Manager name for row handling Operation.
	 */
	public AbstractResilientQueryCrawlerOperation(
	Q query, Class<O> operationClass, String deploymentPathForHandlingRow, String rowManager) {
		super(query);
		this.operationClass = operationClass;
		this.deploymentPathForHandlingRow = deploymentPathForHandlingRow;
		this.rowManager = rowManager;
	}
	
	@Override
	public void close() throws DataException {
		if(operation!=null) {
			operation.close();
		}
		if(rowHandlingProvider != null) {
			rowHandlingProvider.close();
		}
		super.close();
	}
	
	@Override
	protected final void handleRow() throws LogicException, DataException {
		boolean exception = false;
		if(rowHandlingProvider == null) {
			try {
				setupRowHandling();
			} catch (InitializationException e) {
				throw new DataException(e);
			}
		}
		try {
			rowHandlingProvider.getTransactionManager().begin();
		} catch (CouldNotBeginException cnbex) {
			throw new DataException(cnbex);
		}
		
		try {
			beforeEachRow();
			/*
			 * Set the operation provider on the ThreadLocal 
			 * for its execution. Reset immediately after.
			 */
			Bo2Session.setProvider(operation.getProvider());
			operation.execute();
			Bo2Session.setProvider(getProvider());
			rowHandlingProvider.getTransactionManager().commit();
			afterEachSuccessfulRow();
		}catch (Exception e) {
			Bo2Session.setProvider(getProvider());
			exception = true;
			onException(e);
		} finally {
			if(exception) {
				try {
					rowHandlingProvider.getTransactionManager().rollback();
				} catch (CouldNotRollbackException e) {
					throw new DataException(e);
				}				
				operation.close();
				operation=null;
				rowHandlingProvider.close();
				rowHandlingProvider = null;
			}
		}
	}
	
	/**
	 * This method is a template for providing functionality that must
	 * take place before executing the operation, for example providing
	 * new input, based on the current row.
	 * 
	 * @throws DataException
	 *         Normally the query row will be fetched here. 
	 */
	protected abstract void beforeEachRow() throws DataException;
	
	/**
	 * This method is a template for providing functionality that must
	 * take place before executing the operation, for example using
	 * the output of the operation.
	 */
	protected abstract void afterEachSuccessfulRow();
	
	/**
	 * Default exception handling implementation.
	 * @param e
	 */
	protected void onException(Exception e) {
		System.err.println(e.getMessage());
	}
	
	/**
	 * Creates a {@link Provider} and the {@link Operation} for the row handling.
	 * @throws InitializationException
	 * @throws DataException 
	 */
	private void setupRowHandling() throws InitializationException, DataException {
		Bo2Deployment depl;
		if (deploymentPathForHandlingRow==null) {
			depl = Bo2.getDefaultDeployment();
		} else {
			depl = Bo2.getDeployment(deploymentPathForHandlingRow);
		}
		rowHandlingProvider = depl.getProvider();
		
		operation = Factory.create(operationClass);
		if(operation instanceof AbstractResourceConsumer) {
			AbstractResourceConsumer arc = (AbstractResourceConsumer) operation;
			if(rowManager!=null) {
				arc.setManagerName(rowManager);
			}
		}
		operation.init(rowHandlingProvider);
		operation.open();
	}
	
}
