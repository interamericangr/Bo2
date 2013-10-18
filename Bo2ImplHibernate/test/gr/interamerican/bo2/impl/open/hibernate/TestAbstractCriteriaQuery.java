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
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.annotations.ManagerName;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

/**
 * Unit tests for {@link AbstractCriteriaQuery}.
 */
public class TestAbstractCriteriaQuery {
	
	/**
	 * Tests the full lifecycle of an object.
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void AbstractCriteriaQuery() throws UnexpectedException, DataException, LogicException {
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
					User us = q1.getEntity();
					assertEquals(q1.idOfUser, us.getId());
				}
				assertEquals(1, i);
			}
		}.execute();
	}
	
	
	/**
	 * Tests getOrders
	 */
	@Test
	public void testGetOrders(){
		Q1 q1 = new Q1();		
		assertEquals(0,q1.getOrders().length);	
	}
	
	/**
	 * Sample implementation of the query for test.
	 */
	@ManagerName("LOCALDB")
	private class Q1 extends AbstractCriteriaQuery<User> {
		/**
		 * id of the user set as criterion to the query.
		 */
		Integer idOfUser = UtilityForBo2Test.getExistingUserId();
		
		@Override
		public Criterion[] getCriteria() {			
			Criterion[] criteria = {
				Restrictions.eq("key.id", idOfUser) //$NON-NLS-1$					
			};
			return criteria;
		}
		
		@Override
		protected Class<User> rootEntity() {
			return User.class;
		}
	}

}
