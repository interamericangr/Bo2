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

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.impl.open.workers.ArrayIteratorQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link MultipleQueriesOperation}.
 */
public class TestMultipleQueriesOperation {
	
	/**
	 * Unit test for the lifecycle.
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testLifecycle() throws UnexpectedException, DataException, LogicException {		
		new AbstractBo2RuntimeCmd() {			
			@SuppressWarnings("nls")
			@Override public void work() 
			throws LogicException, DataException, InitializationException, UnexpectedException {
				
				String[] array1 = {"this", "that", "then"};		
				String[] array2 = {"sunday", "monday" };		
				String[] array3 = {"January" };		
				ArrayIteratorQuery<String> q1 = new ArrayIteratorQuery<String>(array1);		
				ArrayIteratorQuery<String> q2 = new ArrayIteratorQuery<String>(array2);		
				ArrayIteratorQuery<String> q3 = new ArrayIteratorQuery<String>(array3);		
				MultipleQueriesOperation<String> op = new MultipleQueriesOperation<String>();
				Map<String, EntitiesQuery<? extends String>> queries = new HashMap<String, EntitiesQuery<? extends String>>();
				queries.put("1", q1);
				queries.put("2", q2);
				queries.put("3", q3);
				op.setQueries(queries);
				Assert.assertEquals(queries, op.queries);
				Assert.assertEquals(op.getQueries(), queries);
				
				op.init(getProvider());
				op.open();
				op.execute();
				op.close();
				
				Map<String, List<? extends String>> results = op.getResults();
				Assert.assertEquals(op.results, results);
				
				List<? extends String> list1 = results.get("1");
				Assert.assertNotNull(list1);
				Assert.assertEquals(array1.length, list1.size());
				
				List<? extends String> list2 = results.get("2");
				Assert.assertNotNull(list2);
				Assert.assertEquals(array2.length, list2.size());
				
				List<? extends String> list3 = results.get("3");
				Assert.assertNotNull(list3);
				Assert.assertEquals(array3.length, list3.size());				
			}
		}.execute();		
	}
	
	/**
	 * Unit tests for set-get criteria
	 */
	@Test
	public void testGetSetCriteria() {
		MultipleQueriesOperation<String> op = new MultipleQueriesOperation<String>();
		Object criteria = new Object();
		op.setCriteria(criteria);
		Assert.assertEquals(criteria, op.criteria);
		Assert.assertEquals(op.criteria, op.getCriteria());	
	}

}
