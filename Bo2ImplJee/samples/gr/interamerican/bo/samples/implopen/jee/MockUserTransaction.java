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
package gr.interamerican.bo.samples.implopen.jee;

import gr.interamerican.bo2.utils.ReflectionUtils;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 * Mock implementation of {@link UserTransaction}.
 */
public class MockUserTransaction 
implements UserTransaction {
	
	/**
	 * Creates a new MockUserTransaction object. 
	 *
	 * @param beginException
	 * @param commitException
	 * @param rollbackException
	 */
	public MockUserTransaction(Exception beginException, Exception commitException, Exception rollbackException) {
		super();
		this.beginException = beginException;
		this.commitException = commitException;
		this.rollbackException = rollbackException;
	}

	/**
	 * Exception thrown on begin.
	 */
	Exception beginException;
	/**
	 * Exception thrown on commit;
	 */
	Exception commitException;
	/**
	 * Exception thrown on rollback.
	 */
	Exception rollbackException;
	
	 
	

	public void begin() throws NotSupportedException, SystemException {
		if (ReflectionUtils.isInstanceOf(beginException, NotSupportedException.class)) {
			throw (NotSupportedException) beginException;
		}
		if (ReflectionUtils.isInstanceOf(beginException, SystemException.class)) {
			throw (SystemException) beginException;
		}
		
	}

	
	public void commit() 
	throws HeuristicMixedException, HeuristicRollbackException, IllegalStateException,
	RollbackException, SecurityException, SystemException {
		if (ReflectionUtils.isInstanceOf(commitException, HeuristicMixedException.class)) {
			throw (HeuristicMixedException) commitException;
		}
		if (ReflectionUtils.isInstanceOf(commitException, HeuristicRollbackException.class)) {
			throw (HeuristicRollbackException) commitException;
		}
		if (ReflectionUtils.isInstanceOf(commitException, IllegalStateException.class)) {
			throw (IllegalStateException) commitException;
		}		
		if (ReflectionUtils.isInstanceOf(commitException, RollbackException.class)) {
			throw (RollbackException) commitException;
		}
		if (ReflectionUtils.isInstanceOf(commitException, SecurityException.class)) {
			throw (SecurityException) commitException;
		}
		if (ReflectionUtils.isInstanceOf(commitException, SystemException.class)) {
			throw (SystemException) commitException;
		}
	}

	
	public int getStatus() throws SystemException {		
		return 0;
	}

	
	public void rollback() throws IllegalStateException, SecurityException, SystemException {
		if (ReflectionUtils.isInstanceOf(rollbackException, IllegalStateException.class)) {
			throw (IllegalStateException) rollbackException;
		}
		if (ReflectionUtils.isInstanceOf(rollbackException, SecurityException.class)) {
			throw (SecurityException) rollbackException;
		}
		if (ReflectionUtils.isInstanceOf(rollbackException, SystemException.class)) {
			throw (SystemException) rollbackException;
		}		
	}

	
	public void setRollbackOnly() throws IllegalStateException, SystemException {
		/* empty */		
	}

	public void setTransactionTimeout(int arg0) throws SystemException {
		/* empty */		
	}
	
	

}
