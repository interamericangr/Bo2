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

import static org.junit.Assert.*;

import org.junit.Test;

import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.samples.workers.EmptyWorker;

/**
 * Unit tests for {@link OperationEntitiesQuery}.
 */
public class TestOperationEntitiesQuery {
	
	/**
	 * test for the query.
	 *
	 * @throws UnexpectedException the unexpected exception
	 * @throws DataException the data exception
	 * @throws LogicException the logic exception
	 */
	@Test
	public void testLifecycle() throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {			
			@Override
			public void work() throws LogicException, DataException,
			InitializationException, UnexpectedException {
				@SuppressWarnings("nls") 
				String[] strings = {"one", "two", "three"};
				
				SampleOperationEntitiesQuery q = new SampleOperationEntitiesQuery(strings);
				q.setAvoidLock(false);
				assertTrue(q.isAvoidLock());				
				q.init(this.getProvider());
				q.open();
				q.execute();
				assertEquals(0, q.getRow());
				assertTrue(q.next());
				assertEquals(1, q.getRow());
				assertEquals(strings[0], q.getEntity());
				assertTrue(q.next());
				assertEquals(2, q.getRow());
				assertEquals(strings[1], q.getEntity());
				assertTrue(q.next());
				assertEquals(3, q.getRow());
				assertEquals(strings[2], q.getEntity());
				assertFalse(q.next());
			}
		}.execute();
	}
	
	
	
	/**
	 * Sample implementation of OperationEntitiesQuery.
	 */
	class SampleOperationEntitiesQuery
	extends OperationEntitiesQuery<GetStringOperation, String> {

		/**
		 * Instantiates a new sample operation entities query.
		 *
		 * @param strings the strings
		 */
		public SampleOperationEntitiesQuery(String[] strings) {
			super(new GetStringOperation(strings));
		}

		@Override
		protected String extractEntity(GetStringOperation op) {			
			return op.getString();
		}
		
	}
	
	/**
	 * Operation that increases the index to a string array.
	 */
	class GetStringOperation extends EmptyWorker implements Operation {

		/**
		 * Strings.
		 */
		String[] strings;
		
		/**
		 * Index of current string.
		 */
		int i=-1;
		
		/**
		 * Creates a new GetStringOperation object.
		 *
		 * @param strings the strings
		 */
		public GetStringOperation(String[] strings) {
			super();
			this.strings = strings;
		}

		@Override
		public void execute() throws LogicException, DataException {
			i++;			
		}
		
		/**
		 * Gets the string.
		 * 
		 * @return Returns the string.
		 */
		public String getString() {
			try {
				return strings[i];
			} catch (ArrayIndexOutOfBoundsException e) {
				return null;
			}
		}
		
	}
}