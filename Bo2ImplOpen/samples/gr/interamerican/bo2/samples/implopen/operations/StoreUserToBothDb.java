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

/**
 * The Class StoreUserToBothDb.
 */
public class StoreUserToBothDb extends AbstractBothDbUserOperation {

	/**
	 * Indicator for failure.
	 */
	protected boolean operationMustFail;

	/**
	 * Creates a new DeleteUserFromBothDb object. 
	 *
	 * @param hib the hib
	 * @param operationMustFail the operation must fail
	 */
	public StoreUserToBothDb(boolean hib, boolean operationMustFail) {
		super(hib);
		this.operationMustFail = operationMustFail;
	}

	@Override
	public void execute() throws LogicException, DataException {
		store (LOCALID,local);
		store (OTHERID,other);
		if (operationMustFail) {
			throw new RuntimeException();
		}
	}
	


}
