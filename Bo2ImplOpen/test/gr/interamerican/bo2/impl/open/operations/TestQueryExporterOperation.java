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

import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.annotations.ManagerName;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.impl.open.workers.ArrayIteratorQuery;
import gr.interamerican.bo2.utils.adapters.AnyOperation;

import org.junit.Test;

/**
 * Unit test for {@link QueryExporterOperation}.
 */
public class TestQueryExporterOperation {
	/**
	 * Query rows.
	 */
	@SuppressWarnings("nls")
	String[] strings = {"one", "two", "three", "four"};
	
	/**
	 * test.
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testLifecycle() throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {			
			@Override
			public void work() throws LogicException, DataException,
			InitializationException, UnexpectedException {
				SampleOperation op = new SampleOperation();
				op.init(getProvider());
				op.open();
				op.execute();
				op.close();
			}
		}.execute();
		
	}
	
	/**
	 * Sample operation.
	 */
	@ManagerName("LOCALFS")
	class SampleOperation extends QueryExporterOperation<String, ArrayIteratorQuery<String>> {

		/**
		 * Creates a new SampleOperation object. 
		 *		
		 */
		@SuppressWarnings("nls")
		public SampleOperation() {
			super(new ArrayIteratorQuery<String>(strings), new GetBytes(), "TestQueryExporterOperation.out");			
		}

		
		@Override
		protected String getCurrentRow() throws DataAccessException {			
			return query.getEntity();
		}
		
	}
	
	/**
	 * Transformation.
	 */
	class GetBytes implements AnyOperation<String, byte[]> {		
		public byte[] execute(String a) {			
			return (a+"\n").getBytes(); //$NON-NLS-1$
		}
		
	}

}
