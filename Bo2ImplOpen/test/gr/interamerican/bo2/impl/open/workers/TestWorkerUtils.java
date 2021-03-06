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
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.arch.ext.Codified;
import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.samples.archutil.BeanWithBeanWithFirst;
import gr.interamerican.bo2.samples.archutil.BeanWithFirst;
import gr.interamerican.bo2.samples.operations.EmptyOperation;
import gr.interamerican.bo2.samples.providers.EmptyProvider;
import gr.interamerican.bo2.samples.queries.TsEntitiesQueryImpl;
import gr.interamerican.bo2.samples.workers.EmptyRule;
import gr.interamerican.bo2.samples.workers.Increment;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.adapters.Transformation;

import java.util.List;
import java.util.Set;

import org.junit.Test;

/**
 * Unit tests for {@link WorkerUtils}.
 */
public class TestWorkerUtils {

	
	/** WorkerUtils to test. */
	WorkerUtils utils = new WorkerUtils();
	
	/** query that brings CompanyUser. */
	TsEntitiesQueryImpl query = new TsEntitiesQueryImpl();
	
	/**
	 * tests queryResultsAsList.
	 *
	 * @throws UnexpectedException the unexpected exception
	 * @throws DataException the data exception
	 * @throws LogicException the logic exception
	 */
	@Test
	public void testQueryResultsAsList() 
	throws UnexpectedException, DataException, LogicException {		
		new AbstractBo2RuntimeCmd() {

			@SuppressWarnings("static-access")
			@Override
			public void work() throws LogicException, DataException,
					InitializationException, UnexpectedException {
				query.init(this.getProvider());
				query.open();
				query.execute();
				List<TypedSelectable<Long>> list = utils.queryResultsAsList(query);
				int rows = query.getRow()-1;				
				assertEquals(rows, list.size());
			}
		}.execute();
	}
	
	/**
	 * tests queryResultsAsConvertedList.
	 *
	 * @throws UnexpectedException the unexpected exception
	 * @throws DataException the data exception
	 * @throws LogicException the logic exception
	 */
	@Test
	public void testQueryResultsAsConvertedList() 
	throws UnexpectedException, DataException, LogicException {		
		new AbstractBo2RuntimeCmd() {

			@SuppressWarnings("static-access")
			@Override
			public void work() throws LogicException, DataException,
					InitializationException, UnexpectedException {
				query.init(this.getProvider());
				query.open();
				query.execute();
				List<TypedSelectable<Long>> list = utils.queryResultsAsConvertedList(query);
				int rows = query.getRow()-1;				
				assertEquals(rows, list.size());
			}
		}.execute();
	}
	
	/**
	 * tests queryTransformedResultsAsList.
	 *
	 * @throws UnexpectedException the unexpected exception
	 * @throws DataException the data exception
	 * @throws LogicException the logic exception
	 */
	@Test
	public void testQueryTransformedResultsAsList() 
	throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {

			@SuppressWarnings("static-access")
			@Override
			public void work() throws LogicException, DataException,
					InitializationException, UnexpectedException {
				query.init(this.getProvider());
				query.open();
				query.execute();
				List<CodifiedImpl> list = utils.queryTransformedResultsAsList(query, new Adapter());
				int rows = query.getRow()-1;				
				assertEquals(rows, list.size());

			}
		}.execute();
	}
	
	
	
	
	/**
	 * tests queryResultsAsSet.
	 *
	 * @throws UnexpectedException the unexpected exception
	 * @throws DataException the data exception
	 * @throws LogicException the logic exception
	 */
	@Test
	public void testQueryResultsAsSet() 
	throws UnexpectedException, DataException, LogicException {		
		new AbstractBo2RuntimeCmd() {

			@SuppressWarnings("static-access")
			@Override
			public void work() throws LogicException, DataException,
					InitializationException, UnexpectedException {
				query.init(this.getProvider());
				query.open();
				query.execute();
				Set<TypedSelectable<Long>> set = utils.queryResultsAsSet(query);
				int rows = query.getRow()-1;				
				assertEquals(rows, set.size());
			}
		}.execute();
	}
	
	/**
	 * tests queryResultsAsConvertedSet.
	 *
	 * @throws UnexpectedException the unexpected exception
	 * @throws DataException the data exception
	 * @throws LogicException the logic exception
	 */
	@Test
	public void testQueryResultsAsConvertedSet() 
	throws UnexpectedException, DataException, LogicException {		
		new AbstractBo2RuntimeCmd() {

			@SuppressWarnings("static-access")
			@Override
			public void work() throws LogicException, DataException,
					InitializationException, UnexpectedException {
				query.init(this.getProvider());
				query.open();
				query.execute();
				Set<TypedSelectable<Long>> set = utils.queryResultsAsConvertedSet(query);
				int rows = query.getRow()-1;				
				assertEquals(rows, set.size());
			}
		}.execute();
	}
	
	/**
	 * tests queryTransformedResultsAsList.
	 *
	 * @throws UnexpectedException the unexpected exception
	 * @throws DataException the data exception
	 * @throws LogicException the logic exception
	 */
	@Test
	public void testQueryTransformedResultsAsSet() 
	throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {

			@SuppressWarnings("static-access")
			@Override
			public void work() throws LogicException, DataException,
					InitializationException, UnexpectedException {
				query.init(this.getProvider());
				query.open();
				query.execute();
				Set<CodifiedImpl> set = utils.queryTransformedResultsAsSet(query, new Adapter());
				int rows = query.getRow()-1;				
				assertEquals(rows, set.size());

			}
		}.execute();
	}
		
	
	
	
	
	
	
	
	
	
	/**
	 * tests queryTransformedResultsAsList.
	 *
	 * @throws UnexpectedException the unexpected exception
	 * @throws DataException the data exception
	 * @throws LogicException the logic exception
	 */
	@Test
	public void testCreate() 
	throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {
			
			@Override
			public void work() throws LogicException, DataException,
			InitializationException, UnexpectedException {
				BeanWithFirst bean = new BeanWithFirst();
				BeanWithBeanWithFirst input = new BeanWithBeanWithFirst(bean);				
				Increment increment = 
					WorkerUtils.create(Increment.class.getName(), new EmptyProvider(), input);
				assertEquals(bean, increment.getBean());		
			}
		}.execute();
	}
	
	/**
	 * tests queryTransformedResultsAsList.
	 *
	 * @throws UnexpectedException the unexpected exception
	 * @throws DataException the data exception
	 * @throws LogicException the logic exception
	 */
	@Test
	public void testExecute_with3() 
	throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {
			
			@Override
			public void work() throws LogicException, DataException,
			InitializationException, UnexpectedException {
				BeanWithFirst bean = new BeanWithFirst();
				BeanWithBeanWithFirst input = new BeanWithBeanWithFirst(bean);				
				WorkerUtils.execute(Increment.class.getName(), new EmptyProvider(), input);
				assertEquals(Integer.valueOf(1), bean.getFirst());		
			}
		}.execute();
	}
	
	/**
	 * tests queryTransformedResultsAsList.
	 *
	 * @throws UnexpectedException the unexpected exception
	 * @throws DataException the data exception
	 * @throws LogicException the logic exception
	 */
	@Test
	public void testExecute_with2() 
	throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {			
			@Override
			public void work() throws LogicException, DataException,
			InitializationException, UnexpectedException {								
				WorkerUtils.execute(EmptyOperation.class.getName(), new EmptyProvider());						
			}
		}.execute();
	}
	
	/**
	 * tests queryTransformedResultsAsList.
	 *
	 * @throws UnexpectedException the unexpected exception
	 * @throws DataException the data exception
	 * @throws LogicException the logic exception
	 */
	@Test
	public void testApply_with3() 
	throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {
			
			@Override
			public void work() throws LogicException, DataException,
			InitializationException, UnexpectedException {
				BeanWithFirst bean = new BeanWithFirst();
				BeanWithBeanWithFirst input = new BeanWithBeanWithFirst(bean);				
				WorkerUtils.apply(Increment.class.getName(), new EmptyProvider(), input);
				assertEquals(Integer.valueOf(1), bean.getFirst());		
			}
		}.execute();
	}
	
	/**
	 * tests queryTransformedResultsAsList.
	 *
	 * @throws UnexpectedException the unexpected exception
	 * @throws DataException the data exception
	 * @throws LogicException the logic exception
	 */
	@Test
	public void testApply_with2() 
	throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {			
			@Override
			public void work() throws LogicException, DataException,
			InitializationException, UnexpectedException {				
				WorkerUtils.apply(EmptyRule.class.getName(), new EmptyProvider());						
			}
		}.execute();
	}

	
	
	
	/**
	 * Transforms a {@link TypedSelectable} to a {@link CodifiedImpl}.
	 */
	private class Adapter implements Transformation<TypedSelectable<Long>, CodifiedImpl> {
		
		@Override
		public CodifiedImpl execute(TypedSelectable<Long> a) {
			CodifiedImpl codified = new CodifiedImpl();
			codified.setCode(a.getCode());
			return codified;
		}
	}
	
	/**
	 * Implementation of {@link Codified}.
	 */
	private class CodifiedImpl implements Codified<Long> {
		
		/** code. */
		private Long code;

		@Override
		public Long getCode() { return code; }
		
		@Override
		public void setCode(Long code) { this.code = code; }
		
		@Override
		public int compareTo(Codified<Long> o) {
			if(o==null) { return 1; }
			return Utils.nullSafeCompare(o.getCode(), this.getCode());
		}
	}
	
	

}
