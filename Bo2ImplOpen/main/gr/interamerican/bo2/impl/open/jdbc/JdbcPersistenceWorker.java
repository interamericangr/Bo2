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
package gr.interamerican.bo2.impl.open.jdbc;

import gr.interamerican.bo2.arch.DetachStrategy;
import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.po.PoUtils;
import gr.interamerican.bo2.impl.open.state.CrudStates;
import gr.interamerican.bo2.impl.open.utils.Exceptions;
import gr.interamerican.bo2.utils.Debug;

/**
 * Abstract implementation of {@link PersistenceWorker} based on 
 * JDBC.
 *
 * @param <PO>
 */
public abstract class JdbcPersistenceWorker <PO extends PersistentObject<?>>
extends AbstractJdbcWorker
implements PersistenceWorker<PO> {
	
	/**
	 * Message key for worker ignores a part of the object message.
	 */
	private static final String IGNORES = "JdbcPersistenceWorker.IGNORES"; //$NON-NLS-1$
	
	/**
	 * Validates the object's state and makes sure that 
	 * the operation can be performed.
	 * @throws DataException 
	 * 
	 */
	private void validateStateOnStoreDelete() throws DataException {
		validateOpen();
		if (ignoresSomething()) {
			throw Exceptions.runtime(IGNORES);
		}
	}
	
	public final PO read(PO o) throws DataException, PoNotFoundException {
		try {
			Debug.setActiveModule(this);
			validateOpen();
			Bo2Session.setState(CrudStates.READ);
			doRead(o);
			PoUtils.setDetachStrategy(o, JdbcDetachStrategy.INSTANCE);
			return o;			
		} finally {
			Debug.resetActiveModule();
			Bo2Session.setState(null);
		}

	}
	
	public final PO delete(PO o) 
	throws DataException, PoNotFoundException {
		try {
			Debug.setActiveModule(this);			
			validateStateOnStoreDelete();
			Bo2Session.setState(CrudStates.PRE_DELETE);
			o.tidy();
			Bo2Session.setState(CrudStates.DELETE);
			doDelete(o);
			return o;			
		} finally {
			Debug.resetActiveModule();
			Bo2Session.setState(null);
		}		
	}
	
	public PO store(PO o) throws DataException, PoNotFoundException {
		try {
			Debug.setActiveModule(this);
			validateStateOnStoreDelete();	
			Bo2Session.setState(CrudStates.PRE_STORE);
			o.tidy();
			Bo2Session.setState(CrudStates.STORE);
			doStore(o);
			PoUtils.setDetachStrategy(o, JdbcDetachStrategy.INSTANCE);
			return o;			
		} finally {
			Debug.resetActiveModule();
			Bo2Session.setState(null);
		}
	}
	
	public final PO update(PO o) 
	throws DataException, PoNotFoundException {
		try {
			Debug.setActiveModule(this);
			validateOpen();
			Bo2Session.setState(CrudStates.PRE_UPDATE);
			o.tidy();
			Bo2Session.setState(CrudStates.UPDATE);
			doUpdate(o);
			PoUtils.setDetachStrategy(o, JdbcDetachStrategy.INSTANCE);
			return o;			
		} finally {
			Debug.resetActiveModule();
			Bo2Session.setState(null);
		}
	}
	
	public abstract boolean ignoresSomething();

	
	/**
	 * Reads the object o.
	 * 
	 * @param o Object to be read.
	 * 
	 * @throws DataException
	 * @throws PoNotFoundException
	 */
	protected abstract void doRead(PO o) 
	throws DataException, PoNotFoundException;
	
	/**
	 * Stores the object o.
	 * 
	 * @param o Object to be stored.
	 * 
	 * @throws DataException
	 * @throws PoNotFoundException
	 */
	protected abstract void doStore(PO o)
	throws DataException, PoNotFoundException;
	
	/**
	 * Deletes the object o.
	 * 
	 * @param o Object to be deleted.
	 * 
	 * @throws DataException
	 * @throws PoNotFoundException
	 */
	protected abstract void doDelete(PO o) 
	throws DataException, PoNotFoundException;
	

	/**
	 * Updates the object o.
	 * 
	 * @param o Object to be updated.
	 * 
	 * @throws DataException
	 * @throws PoNotFoundException
	 */
	protected void doUpdate(PO o) 
	throws DataException, PoNotFoundException {
		delete(o);
		store(o);
	}
	
	public DetachStrategy getDetachStrategy() {
		return JdbcDetachStrategy.INSTANCE;
	}
	
}
