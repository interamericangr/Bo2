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
package gr.interamerican.bo2.impl.open.hibernate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.annotations.ManagerName;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.utils.StringConstants;

import java.util.Arrays;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.junit.Test;

/**
 * Unit test class for {@link AbstractHibernateQuery}.
 */
public class TestAbstractHibernateQuery {
	
	/**
	 * Unit test for setAvoidLock
	 */
	@Test
	public void testSetAvoidLock() {
		Q1 q1 = new Q1();
		q1.setAvoidLock(false);
		assertTrue(q1.isAvoidLock());
	}
	
	/**
	 * Unit test for isAvoidLock
	 */
	@Test
	public void testIsAvoidLock() {
		Q1 q1 = new Q1();
		assertTrue(q1.isAvoidLock());
	}
	
	
	/**
	 * Tests the full lifecycle of a query.
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testExecuteAndNext() 
	throws UnexpectedException, DataException, LogicException {
		
		new AbstractBo2RuntimeCmd() {			
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				Q1 q1 = new Q1();
				q1.init(getProvider());
				q1.open();
				q1.execute();				
				int i = 0;
				while (q1.next()) {
					i++;
					int row = q1.getRow();
					assertEquals(i, row);
					assertEquals(q1.rows[i-1], q1.getEntity());					
				}
				assertEquals(q1.rows.length, i);
				q1.close();
			}
		}.execute();
	}
	
	/**
	 * Tests the use of an {@link AbstractHibernateQuery} as a question.
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 * 
	 */
	@Test
	public void testAsk() 
	throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {			
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				Q1 q1 = new Q1();
				q1.init(getProvider());
				q1.open();
				q1.execute();				
				q1.ask();
				Integer answer = q1.getAnswer();
				assertEquals(q1.rows[0], answer);
				q1.close();
			}
		}.execute();
		
		new AbstractBo2RuntimeCmd() {			
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				Q4 q4 = new Q4();
				q4.init(getProvider());
				q4.open();
				q4.execute();				
				q4.ask();
				Integer answer = q4.getAnswer();
				assertEquals(null, answer);
				q4.close();
			}
		}.execute();
	}
	
	/**
	 * Tests execute when a HibernateException is thrown.
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test(expected=DataException.class)
	public void testExecute_throwingException() 
	throws UnexpectedException, DataException, LogicException {
		
		new AbstractBo2RuntimeCmd() {			
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				Q2 q2 = new Q2();
				q2.init(getProvider());
				q2.open();
				q2.execute();				
			}
		}.execute();
	}
	
	/**
	 * Tests execute when a HibernateException is thrown.
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test(expected=DataAccessException.class)
	public void testNext_throwingException() 
	throws UnexpectedException, DataException, LogicException {
		
		new AbstractBo2RuntimeCmd() {			
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				Q3 q3 = new Q3();
				q3.init(getProvider());
				q3.open();
				q3.execute();
				q3.next();
			}
		}.execute();
	}
	

	
	/**
	 * Mock query implementation.
	 */
	@ManagerName("LOCALDB")
	private class Q1 extends AbstractHibernateQuery<Integer> {
		/**
		 * Dummy rows set.
		 */
		Integer[] rows = {1,2,3,4,5};

		@Override
		protected Iterator<?> createIterator() {			
			return Arrays.asList(rows).iterator();
		}		
	}
	
	/**
	 * Mock query implementation that throws a {@link HibernateException}
	 * on execute().
	 */
	@ManagerName("LOCALDB")
	private class Q2 extends AbstractHibernateQuery<String> {

		@Override
		protected Iterator<?> createIterator() {			
			throw new HibernateException(StringConstants.EMPTY);
		}		
	}
	
	/**
	 * Mock query implementation that throws a {@link HibernateException}
	 * on next().
	 */
	@ManagerName("LOCALDB")
	private class Q3 extends AbstractHibernateQuery<String> {

		@Override
		protected Iterator<?> createIterator() {			
			return new FailingIterator();
		}		
	}
	
	/**
	 * Mock query implementation with no results.
	 */
	@ManagerName("LOCALDB")
	private class Q4 extends AbstractHibernateQuery<Integer> {
		/**
		 * Dummy rows set.
		 */
		Integer[] rows = {};

		@Override
		protected Iterator<?> createIterator() {			
			return Arrays.asList(rows).iterator();
		}		
	}
	
	
	/**
	 * Failing iterator used by the query that fails on next.
	 */
	@ManagerName("LOCALDB")
	private class FailingIterator implements Iterator<Object> {
		public boolean hasNext() {			
			return true;
		}
		public Object next() {
			throw new HibernateException(StringConstants.EMPTY);
		}
		public void remove() {/*empty*/}		
	}


}
