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
import gr.interamerican.bo2.impl.open.workers.AbstractOperation;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.samples.implopen.pw.JdbcPersistenceWorkerImpl;
import gr.interamerican.bo2.utils.annotations.Child;

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
public class UpdateUserOperation 
extends AbstractOperation {
	
	/**
	 * user
	 */
	protected User user;
	
	/**
	 * child worker
	 */
	@Child
	protected JdbcPersistenceWorkerImpl pw = 
		new JdbcPersistenceWorkerImpl();
	

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	
	@Override
	public void execute() throws LogicException, DataException {
		pw.update(user);
	}
}
