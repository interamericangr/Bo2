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

import gr.interamerican.bo.samples.implopen.jee.MockJtaTransactionManager;
import gr.interamerican.bo.samples.implopen.jee.MockUserTransaction;
import gr.interamerican.bo2.arch.exceptions.CouldNotBeginException;
import gr.interamerican.bo2.arch.exceptions.CouldNotCommitException;
import gr.interamerican.bo2.arch.exceptions.CouldNotDelistException;
import gr.interamerican.bo2.arch.exceptions.CouldNotEnlistException;
import gr.interamerican.bo2.arch.exceptions.CouldNotRollbackException;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import org.junit.Test;

/**
 * The Class TestJtaTransactionManager.
 */
public class TestJtaTransactionManager {
	
	/**
	 * tests begin.
	 *
	 * @throws CouldNotBeginException the could not begin exception
	 */
	@Test
	public void testBegin_suceed() throws CouldNotBeginException {
		MockUserTransaction ut = new MockUserTransaction(null, null, null);
		MockJtaTransactionManager jta = new MockJtaTransactionManager(ut);
		jta.begin();		
	}
	
	/**
	 * tests begin.
	 *
	 * @throws CouldNotBeginException the could not begin exception
	 */
	@Test(expected=CouldNotBeginException.class)
	public void testBegin_fail1() throws CouldNotBeginException {
		MockUserTransaction ut = new MockUserTransaction(new NotSupportedException(), null, null);
		MockJtaTransactionManager jta = new MockJtaTransactionManager(ut);
		jta.begin();		
	}
	
	/**
	 * tests begin.
	 *
	 * @throws CouldNotBeginException the could not begin exception
	 */
	@Test(expected=CouldNotBeginException.class)
	public void testBegin_fail2() throws CouldNotBeginException {
		MockUserTransaction ut = new MockUserTransaction(new SystemException(), null, null);
		MockJtaTransactionManager jta = new MockJtaTransactionManager(ut);
		jta.begin();		
	}
	
	/**
	 * tests commit.
	 *
	 * @throws CouldNotCommitException the could not commit exception
	 */
	@Test()
	public void testCommit_suceed() throws CouldNotCommitException {
		MockUserTransaction ut = new MockUserTransaction(null, null, null);
		MockJtaTransactionManager jta = new MockJtaTransactionManager(ut);
		jta.commit();		
	}
	
	/**
	 * tests commit.
	 *
	 * @throws CouldNotCommitException the could not commit exception
	 */
	@Test(expected=CouldNotCommitException.class)
	public void testCommit_fail1() throws CouldNotCommitException {
		MockUserTransaction ut = new MockUserTransaction(null, new SecurityException(), null);
		MockJtaTransactionManager jta = new MockJtaTransactionManager(ut);
		jta.commit();		
	}
	
	/**
	 * tests commit.
	 *
	 * @throws CouldNotCommitException the could not commit exception
	 */
	@Test(expected=CouldNotCommitException.class)
	public void testCommit_fail2() throws CouldNotCommitException {
		MockUserTransaction ut = new MockUserTransaction(null, new IllegalStateException(), null);
		MockJtaTransactionManager jta = new MockJtaTransactionManager(ut);
		jta.commit();		
	}
	
	/**
	 * tests commit.
	 *
	 * @throws CouldNotCommitException the could not commit exception
	 */
	@Test(expected=CouldNotCommitException.class)
	public void testCommit_fail3() throws CouldNotCommitException {
		MockUserTransaction ut = new MockUserTransaction(null, new RollbackException(), null);
		MockJtaTransactionManager jta = new MockJtaTransactionManager(ut);
		jta.commit();		
	}
	
	/**
	 * tests commit.
	 *
	 * @throws CouldNotCommitException the could not commit exception
	 */
	@Test(expected=CouldNotCommitException.class)
	public void testCommit_fail4() throws CouldNotCommitException {
		MockUserTransaction ut = new MockUserTransaction(null, new HeuristicMixedException(), null);
		MockJtaTransactionManager jta = new MockJtaTransactionManager(ut);
		jta.commit();		
	}
	
	/**
	 * tests commit.
	 *
	 * @throws CouldNotCommitException the could not commit exception
	 */
	@Test(expected=CouldNotCommitException.class)
	public void testCommit_fail5() throws CouldNotCommitException {
		MockUserTransaction ut = new MockUserTransaction(null, new HeuristicRollbackException(), null);
		MockJtaTransactionManager jta = new MockJtaTransactionManager(ut);
		jta.commit();		
	}
	
	/**
	 * tests commit.
	 *
	 * @throws CouldNotCommitException the could not commit exception
	 */
	@Test(expected=CouldNotCommitException.class)
	public void testCommit_fail6() throws CouldNotCommitException {
		MockUserTransaction ut = new MockUserTransaction(null, new SystemException(), null);
		MockJtaTransactionManager jta = new MockJtaTransactionManager(ut);
		jta.commit();		
	}
	
	/**
	 * tests rollback.
	 *
	 * @throws CouldNotRollbackException the could not rollback exception
	 */
	@Test()
	public void testRollback_succeed() throws CouldNotRollbackException {
		MockUserTransaction ut = new MockUserTransaction(null, null, null);
		MockJtaTransactionManager jta = new MockJtaTransactionManager(ut);
		jta.rollback();		
	}
	
	/**
	 * tests rollback.
	 *
	 * @throws CouldNotRollbackException the could not rollback exception
	 */
	@Test(expected=CouldNotRollbackException.class)
	public void testRollback_fail1() throws CouldNotRollbackException {
		MockUserTransaction ut = new MockUserTransaction(null, null, new IllegalStateException());
		MockJtaTransactionManager jta = new MockJtaTransactionManager(ut);
		jta.rollback();		
	}
	
	/**
	 * tests rollback.
	 *
	 * @throws CouldNotRollbackException the could not rollback exception
	 */
	@Test(expected=CouldNotRollbackException.class)
	public void testRollback_fail2() throws CouldNotRollbackException {
		MockUserTransaction ut = new MockUserTransaction(null, null, new SecurityException());
		MockJtaTransactionManager jta = new MockJtaTransactionManager(ut);
		jta.rollback();		
	}
	
	/**
	 * tests rollback.
	 *
	 * @throws CouldNotRollbackException the could not rollback exception
	 */
	@Test(expected=CouldNotRollbackException.class)
	public void testRollback_fail3() throws CouldNotRollbackException {
		MockUserTransaction ut = new MockUserTransaction(null, null, new SystemException());
		MockJtaTransactionManager jta = new MockJtaTransactionManager(ut);
		jta.rollback();		
	}
	
	/**
	 * tests Enlist .
	 *
	 * @throws CouldNotEnlistException the could not enlist exception
	 */
	@Test
	public void testEnlist() throws CouldNotEnlistException {
		MockJtaTransactionManager jta = new MockJtaTransactionManager(null);
		jta.enList(null);
	}
	
	/**
	 * tests Enlist.
	 *
	 * @throws CouldNotDelistException the could not delist exception
	 */
	@Test
	public void testDelist() throws CouldNotDelistException {
		MockJtaTransactionManager jta = new MockJtaTransactionManager(null);
		jta.deList(null);
	}
	
	

}
