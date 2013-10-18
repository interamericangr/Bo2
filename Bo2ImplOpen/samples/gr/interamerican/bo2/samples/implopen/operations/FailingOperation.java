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
package gr.interamerican.bo2.samples.implopen.operations;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.annotations.ManagerName;
import gr.interamerican.bo2.samples.implopen.pw.JdbcPersistenceWorkerImpl;

/**
 * Simple implementation of AbstractTransactionalOperation used in 
 * this unit test. 
 * 
 * This operation should succeed.
 * 
 * This implementation depends on 
 * {@link JdbcPersistenceWorkerImpl}.
 *
 */
@ManagerName("LOCALDB")
public class FailingOperation 
extends UpdateUserOperation {
			
	/**
	 * Exception message
	 */
	public static final String MESSAGE="foo"; //$NON-NLS-1$
		
	@Override
	public void execute() throws LogicException, DataException {
		super.execute();
		throw new RuntimeException(MESSAGE);
	}
}
