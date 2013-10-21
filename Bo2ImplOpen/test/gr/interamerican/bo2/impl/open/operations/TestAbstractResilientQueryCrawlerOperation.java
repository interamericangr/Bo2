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
import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.annotations.ManagerName;
import gr.interamerican.bo2.impl.open.runtime.Execute;
import gr.interamerican.bo2.impl.open.runtime.RuntimeCommand;
import gr.interamerican.bo2.impl.open.workers.IteratorQuery;
import gr.interamerican.bo2.samples.implopen.operations.OperationWithJdbcWorker;
import gr.interamerican.bo2.test.scenarios.DeleteInvoiceData;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit test for {@link AbstractResilientQueryCrawlerOperation}
 */
public class TestAbstractResilientQueryCrawlerOperation {
	
	/**
	 * Sample for query.
	 */
	List<String> ids = new ArrayList<String>();
	{
		for (int i=0; i < 2000; i++) {
			ids.add(String.valueOf(i));
		}
	}
	
	/**
	 * Query.
	 */
	EntitiesQuery<String> querySample = new IteratorQuery<String>(ids.iterator());
	
	/**
	 * Unit test for execute()
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testExecute() throws UnexpectedException, DataException, LogicException {
		TestSubject subject = new TestSubject(querySample, OperationWithJdbcWorker.class);
		new RuntimeCommand(subject).execute();
	}
	
	/**
	 * Sample ResilientQueryCrawlerOperation implementation.
	 */
	@SuppressWarnings("all")
	@ManagerName("LOCALDB")
	private class TestSubject extends AbstractResilientQueryCrawlerOperation<OperationWithJdbcWorker, EntitiesQuery<String>> {
		
		String currentId;
		
		/**
		 * Creates a new TestSubject object. 
		 *
		 * @param query
		 * @param operationClass
		 */
		public TestSubject(EntitiesQuery<String> query, Class<OperationWithJdbcWorker> operationClass) {
			super(query, operationClass);
		}
		
		@Override
		protected void beforeEachRow() throws DataAccessException {
			currentId = query.getEntity();
			operation.setId(currentId);
		}

		@Override
		protected void afterEachSuccessfulRow() {
			if(currentId.equals(OperationWithJdbcWorker.FAILING_ID)) {
				Assert.assertEquals(0, operation.getRowCount());
			} else {
				Assert.assertEquals(1, operation.getRowCount());
			}
		}
		
	}
	
	/**
	 * Clean up.
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws UnexpectedException, DataException, LogicException {
		Execute.transactional(new DeleteInvoiceData());
	}

	/**
	 * Clean up.
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@AfterClass
	public static void tearDownAfterClass() throws UnexpectedException, DataException, LogicException  {
		Execute.transactional(new DeleteInvoiceData());
	}

}
