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
package gr.interamerican.bo2.impl.open.workers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.samples.queries.TsEntitiesQueryImpl;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.adapters.Filter;
import gr.interamerican.bo2.utils.adapters.GetProperty;
import gr.interamerican.bo2.utils.adapters.Sequence;
import gr.interamerican.bo2.utils.adapters.Transformation;
import gr.interamerican.bo2.utils.conditions.Condition;
import gr.interamerican.bo2.utils.conditions.EqualityCondition;
import gr.interamerican.bo2.utils.conditions.Not;

import org.junit.Test;

/**
 * Unit tests for {@link TransformedQuery}.
 */
@SuppressWarnings("nls")
public class TestTransformedQuery {
	/**
	 * Test rows for the query.
	 */
	String[] rows = {"ena", "", "", "dyo",  ""};
	
	/**
	 * query.
	 */
	EntitiesQuery<TypedSelectable<Long>> wrapped = 	new TsEntitiesQueryImpl(rows);
	
	/**
	 * Get name.
	 */
	@SuppressWarnings("rawtypes")
	GetProperty<TypedSelectable, String> getName = 
		new GetProperty<TypedSelectable, String>("name", TypedSelectable.class);	
	/**
	 * is empty
	 */
	Condition<String> isEmpty = new EqualityCondition<String>(StringConstants.EMPTY);	
	/**
	 * is not empty
	 */
	Condition<String> isNotEmpty = new Not<String>(isEmpty);
	/**
	 * Filter empty strings.
	 */
	Filter<String> noEmptyStrings = new Filter<String>(isNotEmpty);
	/**
	 * transformation.
	 */
	Transformation<TypedSelectable<Long>, String> transformation =
		new Sequence<TypedSelectable<Long>, String>(getName, noEmptyStrings);
		
	/**
	 * query to test
	 */
	TransformedQuery<TypedSelectable<Long>, String> query = 
		new TransformedQuery<TypedSelectable<Long>, String>(wrapped, transformation);
	
	
		
	
	/**
	 * Test for the qhole lyfe cycle. 
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	@Test()
	public void testLifecycle() 
	throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {			
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {						
				query.init(getProvider());
				query.open();
				query.execute();
				
				boolean n = query.next();
				assertTrue(n);
				String first = query.getEntity();
				assertEquals(rows[0], first);
				assertEquals(1, query.getRow());
				
				n = query.next();
				assertTrue(n);
				String second = query.getEntity();
				assertEquals(rows[3], second);
				
				n = query.next();
				assertFalse(n);
				
				query.close();				
			}
		}.execute();
	}
	
	
	/**
	 *Tests isAvoidLock 
	 */
	@Test
	public void testIsAvoidLock(){
		query.setAvoidLock(true);
		assertTrue(wrapped.isAvoidLock());
	}
	
	
	/**
	 *Tests isAvoidLock 
	 */
	@Test
	public void testSetAvoidLock(){		
		assertEquals(wrapped.isAvoidLock(), query.isAvoidLock());
	}
	
}
