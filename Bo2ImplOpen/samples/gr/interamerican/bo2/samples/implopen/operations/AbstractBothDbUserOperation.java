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

import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.workers.AbstractOperation;
import gr.interamerican.bo2.impl.open.workers.AbstractResourceConsumer;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.samples.implopen.pw.UserPwImpl;
import gr.interamerican.bo2.utils.annotations.Child;

/**
 * 
 */
public abstract class AbstractBothDbUserOperation 
extends AbstractOperation {
	/**
	 * localDB user id.
	 */
	public static Integer LOCALID = 18694;
	/**
	 * otherDB user id.
	 */
	public static Integer OTHERID = 21687;
	
	/**
	 * PW for localDB
	 */	
	@Child protected PersistenceWorker<User> local;
	
	/**
	 * PW for otherDB
	 */
	@Child protected PersistenceWorker<User> other;
	

	/**
	 * Creates a new AbstractLocalAndOtherUserOperation object. 
	 * 
	 * @param hib 
	 *        Indicates if persistence is done with hibernate.
	 *
	 */
	public AbstractBothDbUserOperation(boolean hib) {
		super();
		if (hib) {
			local = Factory.createPw(User.class);
			other = Factory.createPw(User.class);
		} else {
			local = new UserPwImpl();
			other = new UserPwImpl();
		}
		((AbstractResourceConsumer) local).setManagerName("LOCALDB"); //$NON-NLS-1$
		((AbstractResourceConsumer) other).setManagerName("OTHERDB"); //$NON-NLS-1$
	}
	
	/**
	 * Deletes the user.
	 * 
	 * @param id
	 * @param pw
	 * 
	 * @throws DataException 
	 */
	protected void delete(int id, PersistenceWorker<User> pw) throws DataException {
		User user = new User();
		user.setId(id);
		try {
			pw.delete(user);
		} catch (PoNotFoundException e) {
			/* no problem */
		}
	}
	
	/**
	 * Stores the user.
	 * 
	 * @param id
	 * @param pw
	 * 
	 * @throws DataException 
	 */
	protected void store(int id, PersistenceWorker<User> pw) throws DataException {
		User user = new User();
		user.setId(id);
		user.setName("name"); //$NON-NLS-1$
		user.setRoleId(1);
		user.setUsrid("usrid"); //$NON-NLS-1$
		pw.store(user);
		
	}
	
	/**
	 * Reads the user.
	 * 
	 * @param id
	 * @param pw
	 * @return Returns the user.
	 * 
	 * @throws DataException 
	 */
	protected User read(int id, PersistenceWorker<User> pw) throws DataException {
		User user = new User();
		user.setId(id);
		try {
			return pw.read(user);
		} catch (PoNotFoundException e) {
			return null;
		}
	}
	
	

}
