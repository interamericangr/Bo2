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
package gr.interamerican.bo2.arch.ext;

import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.exceptions.DataException;

/**
 * Performer of CRUD operation.
 */
public interface CrudPerformer {
	
	/**
	 * Performs a crud operation on the specified {@link PersistentObject}, 
	 * using the specified {@link PersistenceWorker}.
	 * 
	 * @param <P>
	 *        Type of persistent object.

	 * @param po
	 *        Persistent object.
	 *        
	 * @param pw
	 *        Persistence worker.
	 *        
	 * @return Returns the result of the persistence worker method call.
	 * @throws DataException 
	 */	
	public <P extends PersistentObject<?>> 
	P perform(P po, PersistenceWorker<P> pw) throws DataException;

}
