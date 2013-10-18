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
package gr.interamerican.bo2.arch.utils.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.samples.queries.TsEntitiesQueryImpl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link QueryResults}.
 */
public class TestQueryResults {
	/**
	 * rows of the query.
	 */
	@SuppressWarnings("nls")
	String[] rows = {"ena","dyo","tria"};
	
	/**
	 * sample for tests
	 */
	QueryResultsSample sample = new QueryResultsSample();
	
	/**
	 *  setup tests
	 * @throws InitializationException
	 * @throws DataException
	 */
	@Before
	public void before() throws InitializationException, DataException{
		TsEntitiesQueryImpl q = new TsEntitiesQueryImpl(rows);
		q.init(null); //this will work for this implementation
		q.open();
		q.execute();
		sample.read(q);
		q.close();	
	}
	
	/**
	 * Test for read and all other methods.
	 */
	@Test
	public void testRead() {
		assertTrue(sample.containsKey(1));
		assertTrue(sample.containsValue(rows[0]));
		assertTrue(sample.containsValue(rows[1]));
		assertTrue(sample.containsValue(rows[2]));		
		assertEquals(sample.size(),3);
		assertEquals(sample.entrySet().size(),3);
		assertEquals(sample.get(1),rows[0]);
		assertEquals(sample.get(2),rows[1]);
		assertEquals(sample.get(3),rows[2]);
		assertFalse(sample.isEmpty());
		assertEquals(sample.keySet().size(),3);
		assertEquals(sample.values().size(),3);
		String tessera = "tessera";  //$NON-NLS-1$
		sample.put(4, tessera);
		assertEquals(sample.get(4),tessera);
		assertTrue(sample.containsValue(tessera));		
		sample.remove(4);
		assertNull(sample.get(4));
	}
	
	/**
	 * Test clear
	 */
	@Test 
	public void testClear(){	
		assertEquals(3,sample.size());
		sample.clear();
		assertEquals(0,sample.size());
	}
	
	/**
	 * Test putAll
	 * ����������� ��� map �� �����
	 */
	@Test
	public void testPutAll()
	{
		Map<Integer,String> results=new HashMap<Integer,String>();
		results.put(1, "value"); //$NON-NLS-1$
		QueryResultsSample newSample = new QueryResultsSample();
		newSample.putAll(results);
		assertEquals(1,newSample.size());	
	}
	
	
	/**
	 * Sample query results implementation.
	 */
	private class QueryResultsSample extends QueryResults<Integer, String, TsEntitiesQueryImpl> {		
		@Override
		public Integer key(TsEntitiesQueryImpl q) throws DataAccessException {			
			return q.getRow();
		}
		@Override
		public String value(TsEntitiesQueryImpl q) throws DataAccessException {			
			return q.getDescription();
		}
	}


}
