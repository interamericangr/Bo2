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
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.impl.open.workers.ArrayIteratorQuery;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link AbstractQueryCrawlerOperation}.
 */
public class TestAbstractQueryCrawlerOperation {
	
	
	
	/**
	 * Strings for the query.
	 */
	@SuppressWarnings("nls")
	private String[] strings = {"one", "two", "three"};
	
	/**
	 * Unit test for execute.
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testExecute_withReal() 
	throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {			
			@Override
			public void work() throws LogicException, DataException,
			InitializationException, UnexpectedException {				
				SampleOperation op = new SampleOperation();
				op.init(this.getProvider());
				op.open();
				op.execute();
				Assert.assertEquals(strings.length, op.query.getRow());
				op.close();								
			}
		}.execute();
		
	}
	
	/**
	 * Sample operation.
	 */
	class SampleOperation extends AbstractQueryCrawlerOperation<EntitiesQuery<String>> {

		/**
		 * Creates a new SampleOperation object. 
		 * 
		 */
		public SampleOperation() {
			super(new ArrayIteratorQuery<String>(strings));
		}
		
		@Override
		protected void handleRow() throws DataAccessException {
			int i = query.getRow() - 1;
			Assert.assertEquals(strings[i], query.getEntity());							
		}

		
		
	}
	

	
	
	
	

}
