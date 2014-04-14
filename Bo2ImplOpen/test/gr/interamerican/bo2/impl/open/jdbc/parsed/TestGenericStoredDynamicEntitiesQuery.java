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

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.arch.utils.beans.TypedSelectableImpl;
import gr.interamerican.bo2.impl.open.annotations.ManagerName;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.samples.implopen.beans.UserIdBean;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link GenericStoredDynamicEntitiesQuery}.
 */
public class TestGenericStoredDynamicEntitiesQuery {
	
	/**
	 * sql path
	 */
	@SuppressWarnings("nls")
	static final String PATH = "/gr/interamerican/rsrc/sql/SelectIdAndNameFromUsers.sql";
	
	/**
	 * test subject
	 */
	GenericStoredDynamicEntitiesQueryImpl impl;
	
	/**
	 * Test getCriteria
	 */
	@Test
	public void testGetCriteria() {
		String path = PATH;
		UserIdBean criteria = Factory.create(UserIdBean.class);
		GenericStoredDynamicEntitiesQuery<UserIdBean> q = new GenericStoredDynamicEntitiesQuery<UserIdBean>(path, criteria);
		Assert.assertSame(criteria, q.getCriteria());
		UserIdBean newCriteria = Factory.create(UserIdBean.class);
		q.setCriteria(newCriteria);
		Assert.assertSame(newCriteria, q.getCriteria());
	}
	
	/**
	 * Test getCriteria
	 * @throws UnexpectedException 
	 * @throws LogicException 
	 * @throws DataException 
	 */
	@Test
	public void testLifeCycle() throws DataException, LogicException, UnexpectedException {
		
		GenericStoredDynamicEntitiesQuery.reports.clear();
		Assert.assertFalse(GenericStoredDynamicEntitiesQuery.reports.containsKey(PATH));
		
		new AbstractBo2RuntimeCmd() {
			@Override
			public void work() throws LogicException, DataException, InitializationException, UnexpectedException {
				impl = open(new GenericStoredDynamicEntitiesQueryImpl());
				impl.setCriteria(new TypedSelectableImpl<Long>());
				impl.execute();
			}
		}.execute();
		
		Assert.assertTrue(GenericStoredDynamicEntitiesQuery.reports.containsKey(PATH));
		
		/*
		 * execute second time with cached predefined report
		 */
		
		new AbstractBo2RuntimeCmd() {
			@Override
			public void work() throws LogicException, DataException, InitializationException, UnexpectedException {
				impl = open(new GenericStoredDynamicEntitiesQueryImpl());
				impl.setCriteria(new TypedSelectableImpl<Long>());
				impl.execute();
			}
		}.execute();
	}
	
	/**
	 * Implementation for test
	 */
	@ManagerName("LOCALDB")
	public static class GenericStoredDynamicEntitiesQueryImpl extends GenericStoredDynamicEntitiesQuery<TypedSelectableImpl<Long>> {
		/**
		 * Creates a new GenericStoredDynamicEntitiesQueryImpl object. 
		 */
		public GenericStoredDynamicEntitiesQueryImpl() {
			super(PATH, null);
		}
	}

}
