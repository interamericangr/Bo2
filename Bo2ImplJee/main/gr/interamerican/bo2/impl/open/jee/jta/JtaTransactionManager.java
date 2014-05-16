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
package gr.interamerican.bo2.impl.open.jee.jta;

import gr.interamerican.bo2.arch.TransactionManager;
import gr.interamerican.bo2.arch.exceptions.CouldNotBeginException;
import gr.interamerican.bo2.arch.exceptions.CouldNotCommitException;
import gr.interamerican.bo2.arch.exceptions.CouldNotRollbackException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.impl.open.job.JobCapableTransactionManager;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 * JTA based implementation of {@link TransactionManager}.
 */
public class JtaTransactionManager 
extends JobCapableTransactionManager
implements TransactionManager {
	
	/**
	 * JTA user transaction.
	 */
	protected UserTransaction ut;

	
	public void begin() throws CouldNotBeginException {
		try {
			ut.begin();
		} catch (NotSupportedException de) {
			throw new CouldNotBeginException(de);
		} catch (SystemException se) {
			throw new CouldNotBeginException(se);
		}
	}

	
	public void commit() throws CouldNotCommitException {
		try {
			ut.commit();
			submitScheduledJobs();
		} catch (SecurityException se) {
			throw new CouldNotCommitException(se);
		} catch (IllegalStateException ise) {
			throw new CouldNotCommitException(ise);
		} catch (RollbackException re) {
			throw new CouldNotCommitException(re);
		} catch (HeuristicMixedException hme) {
			throw new CouldNotCommitException(hme);
		} catch (HeuristicRollbackException hre) {
			throw new CouldNotCommitException(hre);
		} catch (SystemException syse) {
			throw new CouldNotCommitException(syse);
		} catch (DataException e) { //job submission
			throw new RuntimeException(e);
		} finally {
			clearScheduledJobs();
		}
		
	}

	
	public void rollback() throws CouldNotRollbackException {
		try {
			/*
			 * TODO: avoid most CouldNotRollbackExceptions?
			 */
//			if(ut.getStatus() == Status.STATUS_MARKED_ROLLBACK) {
//				return;
//			}
			ut.rollback();
		} catch (IllegalStateException ise) {
			throw new CouldNotRollbackException(ise);
		} catch (SecurityException se) {
			throw new CouldNotRollbackException(se);
		} catch (SystemException syse) {
			throw new CouldNotRollbackException(syse);
		} finally {
			clearScheduledJobs();
		}
	}

}
