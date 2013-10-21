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
package gr.interamerican.bo2.impl.open.jdbc.parsed;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.impl.open.workers.WorkerUtils;
import gr.interamerican.bo2.samples.archutil.po.User;

import java.util.List;

import org.junit.Test;

/**
 * Unit tests for {@link StoredDynamicPoQuery}.
 */
public class TestStoredDynamicPoQuery {
	
	/**
	 * name.
	 */
	private static final String NAME = "name"; //$NON-NLS-1$
	
	/**
	 * @throws DataException 
	 * @throws UnexpectedException 
	 * @throws LogicException 
	 * @throws SecurityException 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testLifeCycle() throws DataException, LogicException, UnexpectedException {
		new AbstractBo2RuntimeCmd() {
			
			@Override
			public void work() throws LogicException, DataException, InitializationException, UnexpectedException {
				String path = "/gr/interamerican/rsrc/sql/SelectIdAndNameFromUsers.sql";
				StoredDynamicEntitiesQuery sdeq = new StoredDynamicEntitiesQuery(path);
				StoredDynamicPoQuery<User> q = new StoredDynamicPoQuery<User>(sdeq, User.class);
				
				UserQueryCriteria criteria = new UserQueryCriteria();
				criteria.setName(NAME);
				q.setCriteria(criteria);
				
				q.setManagerName("LOCALDB");
				open(q);
				q.execute();
				
				List<User> users = WorkerUtils.queryResultsAsList(q);
				assertTrue(users.size()>0);
				
				User subject = users.get(0);
				assertEquals(subject.getName().trim(), NAME);
				assertNotNull(subject.getId());
				
			}
		}.execute();
		
	}
	
	
	
	/**
	 * Criteria for searching a user.
	 */
	private class UserQueryCriteria {
		/**
		 * name
		 */
		private String name;

		/**
		 * Gets the name.
		 *
		 * @return Returns the name
		 */
		@SuppressWarnings("unused")
		public String getName() {
			return name;
		}

		/**
		 * Assigns a new value to the name.
		 *
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
	}

}
