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
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.impl.open.workers.SequentialOperation;

/**
 * Utility class with convenience methods for operations execution.
 */
public class Execute {
	
	/**
	 * Executes an array of operations inside a transaction
	 * using the default transactional provider of the current deployment.
	 * 
	 * @param operations
	 *        Operations to execute.
	 *        
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	public static void transactional(Operation... operations) 
	throws UnexpectedException, DataException, LogicException {
		RuntimeCommand cmd = new RuntimeCommand(operations);
		cmd.execute();
	}
	
	/**
	 * Executes an array of operations inside a transaction
	 * using the default transactional provider of the current deployment.
	 * 
	 * @param depl 
	 *        Deployment.
	 * @param operations
	 *        Operations to execute.
	 *        
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	public static void transactional(String depl, Operation... operations) 
	throws UnexpectedException, DataException, LogicException {
		RuntimeCommand cmd = new RuntimeCommand(depl, operations);
		cmd.execute();
	}
	
	/**
	 * Executes a sequence of operations without marking any transaction.
	 *        
	 * @param operations
	 *        Operations to execute.
	 *        
	 * @throws DataException
	 * @throws LogicException
	 * @throws InitializationException 
	 */
	public static void nonTransactional(Operation... operations) 
	throws DataException, LogicException, InitializationException {
		Provider provider = Bo2.getProvider();
		try {	
			SequentialOperation operation = new SequentialOperation(operations);			
			operation.init(provider);
			operation.open();
			operation.execute();
			operation.close();
		} catch (DataException e) {
			provider.close();
			throw e;
		} catch (LogicException e) {
			provider.close();
			throw e;
		} catch (InitializationException e) {
			provider.close();
			throw e;
		}
	}

	
}
