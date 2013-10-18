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

import gr.interamerican.bo2.arch.PersistenceUtility;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.Worker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.po.PoUtils;

/**
 * CRUD operations by an {@link AbstractBo2RuntimeCmd}.
 * 
 * @param <P> 
 *        Type of object being operated.
 */
public class CrudCmd<P> {
	
	/**
	 * Indicates a PoNotFoundException on delete is thrown
	 * or ignored.
	 */
	boolean ignorePnfeOnDelete = false;
	
	/**
	 * Persistence utility.
	 */
	PersistenceUtility<P> pw;
	
	
	
	/**
	 * Creates a new CrudCmd object. 
	 *
	 * @param pw
	 *        Persistence worker.
	 * @param ignorePnfeOnDelete 
	 *        Specifies if {@link PoNotFoundException}s thrown by delete
	 *        are ignored. If this is set to <code>true</code>, then if
	 *        a delete operation throws a {@link PoNotFoundException}, it
	 *        will be ignored.
	 */
	public CrudCmd(PersistenceUtility<P> pw, boolean ignorePnfeOnDelete) {
		super();
		this.pw = pw;
		this.ignorePnfeOnDelete = ignorePnfeOnDelete;
	}
	
	/**
	 * Creates a new CrudCmd object. 
	 *
	 * @param pw
	 */
	public CrudCmd(PersistenceUtility<P> pw) {
		this(pw, false);
	}
	
	 
	/**
	 * Performs an operation on P.
	 * 
	 * @param p
	 * @param cmd
	 * @return Returns the result of the operation.
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	P perform(P p, PoCmd cmd) 
	throws UnexpectedException, DataException, LogicException {
		cmd.setPo(p);
		cmd.execute();
		return cmd.getPo();
	}
	
	
	/**
	 * Reads the specified object from the database.
	 * 
	 * @param p
	 * @return Returns the object.
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	public synchronized P read(P p) 
	throws UnexpectedException, DataException, LogicException {		
		Read read = new Read();
		return perform(p, read);
	}
	
	/**
	 * Deletes the specified object from the database.
	 * 
	 * @param p
	 * @return Returns the object.
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	public synchronized P delete(P p) 
	throws UnexpectedException, DataException, LogicException {	
		Delete delete = 
			ignorePnfeOnDelete ? new DeleteIgnoring() : new Delete();
		return perform(p, delete);
	}
	
	/**
	 * Updates the specified object in the database.
	 * 
	 * @param p
	 * @return Returns the object.
	 * 
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	public synchronized P update(P p) 
	throws UnexpectedException, DataException, LogicException {		
		Update update = new Update();
		return perform(p, update);
	}
	
	/**
	 * Stores the specified object in the database.
	 * 
	 * @param p
	 * @return Returns the object.
	 * 
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	public synchronized P store(P p) 
	throws UnexpectedException, DataException, LogicException {
		Store store = new Store();
		return perform(p, store);
	}

	/**
	 * Read command.
	 */
	class Update extends PoCmd {
		@Override
		void task() throws DataException {
			po = pw.update(po);
		}
	}
	

	/**
	 * Read command.
	 */
	class Store extends PoCmd {
		@Override
		void task() throws DataException {
			po = pw.store(po);
		}
	}

	/**
	 * Read command.
	 */
	class Read extends PoCmd {
		@Override
		void task() throws DataException {
			po = pw.read(po);
		}
	}

	/**
	 * Delete command.
	 */
	class Delete extends PoCmd {
		@Override
		void task() throws DataException {
			po = pw.delete(po);
		}
	}
	
	/**
	 * Delete command.
	 */
	class DeleteIgnoring extends Delete {
		@Override
		void task() throws DataException {
			try {
				po = pw.read(po);
				po = pw.delete(po);
			} catch (PoNotFoundException e) {
				/* ignore this */
			}
		}
	}
	
	/**
	 * Operation on an {@link PersistentObject}
	 */
	abstract class PoCmd extends AbstractBo2RuntimeCmd {
		
		/**
		 * Persistent object to operate on.
		 */
		P po;
		
		@Override
		public void work() throws LogicException, DataException, 
		InitializationException, UnexpectedException {
			if (pw instanceof Worker) {
				Worker w = (Worker) pw;
				w.init(getProvider());
				w.open();
			}
			PoUtils.reattach(po, getProvider());
			task();
			if (pw instanceof Worker) {
				Worker w = (Worker) pw;
				w.close();
			}
		}
		
		/**
		 * Gets the po.
		 * 
		 * @return Returns the po.
		 */
		public P getPo() {
			return po;
		}
		
		/**
		 * Sets the po.
		 * 
		 * @param po 
		 */
		public void setPo(P po) {
			this.po = po;
		}
		
		/**
		 * Task.
		 * @throws DataException
		 */
		abstract void task() throws DataException;
		
		
		
	}
	
	

	
	
	
	
	
	
	
	
	
	
	

}
