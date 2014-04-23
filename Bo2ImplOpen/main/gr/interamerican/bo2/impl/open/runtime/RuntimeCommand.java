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
package gr.interamerican.bo2.impl.open.runtime;

import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.TransactionManager;
import gr.interamerican.bo2.arch.exceptions.CouldNotBeginException;
import gr.interamerican.bo2.arch.exceptions.CouldNotCommitException;
import gr.interamerican.bo2.arch.exceptions.CouldNotRollbackException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.NoTransactionManagerException;
import gr.interamerican.bo2.arch.exceptions.ProviderCreationException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.arch.utils.Bo2ExceptionUtils;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.impl.open.utils.Bo2Deployment;
import gr.interamerican.bo2.impl.open.workers.SequentialOperation;
import gr.interamerican.bo2.utils.Debug;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;

/**
 * {@link RuntimeCommand} is a runtime layer object that provides
 * a transaction context to a sequence of operations and executes
 * them. <br/>
 * 
 * The RuntimeCommand is a one use object. It can be executed
 * only once. If a new execution is required, then a new RuntimeCommand
 * must be created. <br/>
 **/
public class RuntimeCommand {
	
	/**
	 * Provider.
	 */
	private Provider provider; 
	
	/**
	 * Operation of this command.
	 */
	protected Operation operation;
	
	/**
	 * Transaction manager.
	 */
	private TransactionManager transactionManager;
	
	/**
	 * Creates a new RuntimeCommand object that will use the
	 * default deployment. 
	 *
	 * @param operations
	 *        Operations to execute sequentially.
	 */
	public RuntimeCommand(Operation... operations) {
		this(null,operations);	
	}
	
	/**
	 * Creates a new RuntimeCommand object.
	 *  
	 * @param deploymentPath 
	 *        Path to the resource file that contains the deployment
	 *        properties.
	 * @param operations
	 *        Operations to execute sequentially.
	 */
	public RuntimeCommand(String deploymentPath, Operation... operations) {
		this(deploymentPath);
		if (operations.length==1) {
			this.operation = operations[0];
		} else {
			this.operation = new SequentialOperation(operations);			
		}		
	}
		
	/**
	 * Creates a new RuntimeCommand object.
	 * 
	 * This protected constructor is provided for subclasses that
	 * will give assign a value to operation their own way. 	 * 
	 * 
	 * @param deployment
	 *        Path to the deployment. If set to null, or empty
	 *        then the default deployment will be used.  
	 */
	protected RuntimeCommand(String deployment) {
		super();
		initializeProvider(deployment);
		transactionManager = provider.getTransactionManager();
		if (this.transactionManager==null) {
			throw new NoTransactionManagerException();
		}
	}
	
	/**
	 * Creates a new RuntimeCommand object that will use the
	 * default deployment.
	 * 
	 * This protected constructor is provided for subclasses that
	 * will give assign a value to operation their own way. 
	 */
	protected RuntimeCommand() {
		this(StringConstants.EMPTY);
	}
	
	/**
	 * Preparation before the main execution.
	 * 
	 * @throws DataException 
	 * @throws LogicException 
	 * @throws InitializationException 
	 */
	protected void before() throws DataException, LogicException, InitializationException {
		/* empty */
	}
	
	/**
	 * Work to do after the main execution.
	 * 
	 * @throws DataException 
	 * @throws LogicException 
	 */
	protected void after() throws DataException, LogicException {
		/* empty */
	}
	
	/**
	 * Executable method of the command.
	 * 
	 * @throws DataException
	 * @throws LogicException
	 * @throws UnexpectedException
	 */
	public void execute() 
	throws DataException, LogicException, UnexpectedException {
		try {	
			Debug.setActiveModule(this);
			Bo2Session.setProvider(provider);
			begin();
			mainFlow();
			commit();
			provider.close();	
		} catch (InitializationException ie) {
			handle(ie);
		} catch (DataException de) {
			handle(de);
		} catch (LogicException le) {
			handle(le);
		} catch (RuntimeException rte) {	
			handle(rte);
		} catch (Error err) {
			handle(err);
			rethrow(err);
		} finally {			
			Bo2Session.setProvider(null);
			Debug.resetActiveModule();			
		}
	}
	
	/**
	 * Basic flow of execution.
	 * 
	 * @throws DataException
	 * @throws LogicException
	 * @throws InitializationException
	 */
	private void mainFlow() 
	throws DataException, LogicException, InitializationException  {				
		before();
		operation.init(provider);
		operation.open();
		operation.execute();
		operation.close();
		after();		
	}
	
	/**
	 * Handles an {@link Exception}.
	 * 
	 * The method will log the exception, then it will try to free 
	 * all open resources by calling the <code>getManager().close()</code>
	 * and then it will re-throw the exception it handles. If it is a 
	 * DataException or a LogicException, it will be re-thrown, if it 
	 * is an instance of another type of Exception, it will wrap it 
	 * inside an UnexpectedException.
	 * 
	 * @param ex Exception.
	 * @throws LogicException  
	 * @throws DataException
	 * @throws UnexpectedException 
	 */
	void handle(Throwable ex) throws DataException, LogicException, UnexpectedException {
		rollback(ex);
		rethrow(ex);
	}
	
	/**
	 * Re-throws the specified exception.
	 * 
	 * @param ex
	 *        Exception to rethrow.
	 *        
	 * @throws DataException
	 * @throws LogicException
	 * @throws UnexpectedException
	 */
	void rethrow(Throwable ex) 
	throws DataException, LogicException, UnexpectedException {
		ex.printStackTrace();
		Bo2ExceptionUtils.throwDataLogicOrUnexpectedException(ex);
	}
	
	/**
	 * Begins the transaction.
	 * 
	 * @throws UnexpectedException
	 *         If the transactionManager fails to begin the transaction.
	 */
	void begin() throws UnexpectedException {
		try {
			transactionManager.begin();
		} catch (CouldNotBeginException e) {			
			e.printStackTrace();
			throw new UnexpectedException(e);
		}
	}
	
	/**
	 * Ends the transaction.
	 * 
	 * @throws UnexpectedException
	 *         If the transactionManager fails to commit the transaction.
	 */
	void commit() throws UnexpectedException {
		try {
			transactionManager.commit();
		} catch (CouldNotCommitException e) {			
			e.printStackTrace();
			throw new UnexpectedException(e);
		}
	}
	
	/**
	 * Initializes the provider.
	 * 
	 * @param deploymentPath
	 *        path to the deployment properties.
	 */
	private void initializeProvider(String deploymentPath) {
		Bo2Deployment depl;
		if (StringUtils.isNullOrBlank(deploymentPath)) {
			depl = Bo2.getDefaultDeployment();
		} else {
			depl = Bo2.getDeployment(deploymentPath);
		}
		try {
			provider = depl.getProvider();
		} catch (InitializationException e) {
			throw new ProviderCreationException(e);
		}
	}
	

	/**
	 * Gets the provider.
	 *
	 * @return Returns the provider
	 */
	protected Provider getProvider() {		
		return provider;
	}
	
	/**
	 * Uses the transaction manager for rollback.
	 * 
	 * @param initialCause
	 * @throws UnexpectedException
	 */
	private void rollback(Throwable initialCause) 
	throws UnexpectedException {
		try {
			transactionManager.rollback();
		} catch (CouldNotRollbackException cnrbex) {
			if(initialCause!=null) {
				initialCause.printStackTrace();
			}			
			cnrbex.setInitial(initialCause);
			cnrbex.printStackTrace();
			throw new UnexpectedException(cnrbex);
		}		
	}	

}
