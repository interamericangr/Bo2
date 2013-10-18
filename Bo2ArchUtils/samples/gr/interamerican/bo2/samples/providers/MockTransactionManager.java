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
package gr.interamerican.bo2.samples.providers;

import gr.interamerican.bo2.arch.ResourceWrapper;
import gr.interamerican.bo2.arch.TransactionManager;
import gr.interamerican.bo2.arch.exceptions.CouldNotBeginException;
import gr.interamerican.bo2.arch.exceptions.CouldNotCommitException;
import gr.interamerican.bo2.arch.exceptions.CouldNotDelistException;
import gr.interamerican.bo2.arch.exceptions.CouldNotEnlistException;
import gr.interamerican.bo2.arch.exceptions.CouldNotRollbackException;

/**
 * Mock {@link TransactionManager}.
 * 
 * This class is used by test fixtures that instantiate it
 * dynamically. It can't be replaced by an object created by
 * a mock objects library.
 */
public class MockTransactionManager implements TransactionManager {
	public void rollback() throws CouldNotRollbackException {/*empty*/}	
	public void commit() throws CouldNotCommitException {/*empty*/}					
	public void begin() throws CouldNotBeginException {/*empty*/}	
	public void enList(ResourceWrapper resource) throws CouldNotEnlistException {/*empty*/}	
	public void deList(ResourceWrapper resource) throws CouldNotDelistException {/*empty*/}
	public void close() {/*empty*/}	

}
