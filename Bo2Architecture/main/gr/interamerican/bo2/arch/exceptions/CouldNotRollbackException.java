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
package gr.interamerican.bo2.arch.exceptions;

import gr.interamerican.bo2.arch.TransactionManager;

/**
 * This Exception is thrown by a {@link TransactionManager}
 * when he fails to rollback a transaction.
 * 
 * This type of exception can be caused by another Throwable
 * that is thrown by the underlying implementation library
 * of the transaction manager. <br/>
 * Even though this is exception comes from the data storage
 * layer, it is not a subtype of {@link DataException}. The
 * CouldNotRollbackException is a different type of exception
 * that requires usually special handling. <br/>
 * Rollback is an action that is usually executed because of an
 * exception. The initial exception that triggered the rollback
 * is stored in the <code>initial</code> member of the 
 * {@link CouldNotRollbackException}. This enables the application
 * to present both throwables, the exception that occured during
 * the rollback, but also the initial Exception. 
 *
 */
public class CouldNotRollbackException 
extends TransactionManagerException {
	
	/**
	 * Initial Exception.
	 */
	private Throwable initial;
	
	/**
	 * Universal version identifier for serialized class. 
	 */
	public static final long serialVersionUID = 2L;


	/**
	 * Creates a new CouldNotRollbackException.
	 * 
	 * The Exception is thrown because another Trhowable
	 * occured while the underlying transaction manager
	 * library attempted to rollback a transaction.
	 * The cause must be passed as a parameter to this
	 * constructor.
	 * 
	 * @param cause Cause of the exception.
	 */
	public CouldNotRollbackException(Throwable cause) {
		super(cause);		
	}
	
	/**
	 * Creates a new CouldNotRollbackException.
	 * 
	 * @param cause 
	 *        Cause of the exception. This is the exception that made the
	 *        rollback operation impossible.
	 * @param initial 
	 *        Initial exception that caused the application to attempt
	 *        rollback.
	 */
	public CouldNotRollbackException(Throwable cause, Throwable initial) {
		super(cause);
		this.initial = initial;
	}

	/**
	 * Gets the initial.
	 *
	 * @return Returns the initial
	 */
	public Throwable getInitial() {
		return initial;
	}

	/**
	 * Sets the Throwable that caused the application to 
	 * attempt rollback.
	 *
	 * @param initial the initial to set
	 */
	public void setInitial(Throwable initial) {
		this.initial = initial;
	}

}
