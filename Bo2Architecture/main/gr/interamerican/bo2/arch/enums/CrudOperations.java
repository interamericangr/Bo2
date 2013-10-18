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
package gr.interamerican.bo2.arch.enums;

import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.ext.CrudPerformer;

/**
 * Enum that lists the CRUD operations that can be performed on a {@link PersistentObject}.
 * 
 * Useful for components that need to report the type of db operation performed on a 
 * {@link PersistentObject}.
 */
public enum CrudOperations 
implements CrudPerformer {
	
	/**
	 * indicates that a new {@link PersistentObject} was stored.
	 */
	STORE {		

		@Override
		public <P extends PersistentObject<?>> 
		P perform(P po, PersistenceWorker<P> pw) throws DataException {
			return pw.store(po);
		}
	},
	
	/**
	 * indicates that a {@link PersistentObject} was read.
	 */
	READ{		

		@Override
		public <P extends PersistentObject<?>> 
		P perform(P po, PersistenceWorker<P> pw) throws DataException {
			return pw.read(po);
		}
	},
	
	/**
	 * indicates that a {@link PersistentObject} was updated.
	 */
	UPDATE{		

		@Override
		public <P extends PersistentObject<?>> 
		P perform(P po, PersistenceWorker<P> pw) throws DataException {
			return pw.update(po);
		}
	},
	
	/**
	 * indicates that a {@link PersistentObject} was deleted.
	 */
	DELETE{		

		@Override
		public <P extends PersistentObject<?>> 
		P perform(P po, PersistenceWorker<P> pw) throws DataException {
			return pw.delete(po);
		}
	};
	
}
