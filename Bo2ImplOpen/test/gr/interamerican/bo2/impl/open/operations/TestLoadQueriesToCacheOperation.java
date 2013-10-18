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
package gr.interamerican.bo2.impl.open.operations;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.arch.ext.Cache;
import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.arch.utils.beans.CacheImpl;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.samples.queries.TsEntitiesQueryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

/**
 * Unit test for {@link LoadQueriesToCacheOperation}.
 */
public class TestLoadQueriesToCacheOperation {
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {		
		List<EntitiesQuery<? extends TypedSelectable<Long>>> queries
			= new ArrayList<EntitiesQuery<? extends TypedSelectable<Long>>>();		
		Cache<Long> cache = new CacheImpl<Long>();
		LoadQueriesToCacheOperation<Long> op = 
			new LoadQueriesToCacheOperation<Long>(queries, cache);
		assertSame(cache, op.cache);
		assertSame(queries, op.queries);		
	}
	
	/**
	 * Tests execute.
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testExecute() throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, DataException,
			InitializationException, UnexpectedException {
				TsEntitiesQueryImpl query = new TsEntitiesQueryImpl();
				List<EntitiesQuery<? extends TypedSelectable<Long>>> queries
					= new ArrayList<EntitiesQuery<? extends TypedSelectable<Long>>>();
				queries.add(query);
				Cache<Long> cache = new CacheImpl<Long>();
				LoadQueriesToCacheOperation<Long> op = 
					new LoadQueriesToCacheOperation<Long>(queries, cache);
				op.init(this.getProvider());
				op.open();
				op.execute();
				op.close();
				Set<TypedSelectable<Long>> values = 
					cache.getSubCache(TsEntitiesQueryImpl.TYPEID, null);
				assertNotNull(values);
				assertTrue(values.size()!=0);
			}
		}.execute();
		
		
	}
	
	
	
	

}
