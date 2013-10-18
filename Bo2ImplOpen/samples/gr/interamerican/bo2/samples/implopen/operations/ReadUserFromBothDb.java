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
import gr.interamerican.bo2.samples.archutil.po.User;

/**
 * 
 */
public class ReadUserFromBothDb extends AbstractBothDbUserOperation {
	
	/**
	 * Local DB
	 */
	User localUser;
	
	/**
	 * Other db.
	 */
	User otherUser;

	

	/**
	 * Creates a new DeleteUserFromBothDb object. 
	 *
	 * @param hib
	 */
	public ReadUserFromBothDb(boolean hib) {
		super(hib);
	}

	@Override
	public void execute() throws LogicException, DataException {
		localUser = read(LOCALID,local);
		otherUser = read(OTHERID, other);
	}

	/**
	 * Gets the localUser.
	 *
	 * @return Returns the localUser
	 */
	public User getLocalUser() {
		return localUser;
	}

	/**
	 * Gets the otherUser.
	 *
	 * @return Returns the otherUser
	 */
	public User getOtherUser() {
		return otherUser;
	}
	


}
