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
package gr.interamerican.bo2.arch;

import gr.interamerican.bo2.arch.exceptions.CouldNotBeginException;
import gr.interamerican.bo2.arch.exceptions.CouldNotCommitException;
import gr.interamerican.bo2.arch.exceptions.CouldNotDelistException;
import gr.interamerican.bo2.arch.exceptions.CouldNotEnlistException;
import gr.interamerican.bo2.arch.exceptions.CouldNotRollbackException;


/**
 * Abstraction of transaction manager.
 * 
 * The TransactionManager interface is a wrapper around 
 * a runtime layer resource that handles transactions. 
 * The interface is very simple, it has only the basic
 * methods of transaction, begin(), commit() and rollback().
 * The implementation of TransactionManager should deal
 * with any other technical details of the underlying
 * transaction technology, and reveal to the business 
 * layer only these three methods.
 *  
 */
public interface TransactionManager {
	
	/**
	 * Starts a transaction.
	 * 
	 * @throws CouldNotBeginException
	 */
	public void begin() throws CouldNotBeginException;
	
	/**
	 * Commits the current transaction.
	 * 
	 * @throws CouldNotCommitException
	 */
	public void commit() throws CouldNotCommitException;
	
	/**
	 * Rolls back the current transaction.
	 * 
	 * @throws CouldNotRollbackException 
	 */
	public void rollback() throws CouldNotRollbackException;
	
	/**
	 * Includes a resource in the transaction.
	 * 
	 * @param resource
	 *        Resource to add.
	 *        
	 * @throws CouldNotEnlistException 
	 */
	public void enList(ResourceWrapper resource) throws CouldNotEnlistException; 
	
	/**
	 * Removes a resource from the transaction.
	 * 
	 * @param resource
	 *        Resource to remove.
	 *        
	 * @throws CouldNotDelistException 
	 */
	public void deList(ResourceWrapper resource) throws CouldNotDelistException; 
	
	/**
	 * Cleans up any resources the TransactionManager created/modified
	 * upon its initialization. The creator of the TransactionManager
	 * is normally responsible for calling this, when there is no more
	 * need for the TransactionManager.
	 */
	public void close();
	
}
