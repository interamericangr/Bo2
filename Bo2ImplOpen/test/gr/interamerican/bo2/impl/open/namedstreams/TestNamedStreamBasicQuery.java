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
package gr.interamerican.bo2.impl.open.namedstreams;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.records.Record;
import gr.interamerican.bo2.impl.open.records.CsvRecord;

import org.junit.Test;

/**
 * 
 */
public class TestNamedStreamBasicQuery {

	
	/**
	 * NamedStreamQuery to test
	 */
	StreamQuery query = new StreamQuery();

	/**
	 * Number of colums
	 */
	private static final int COLUMNS = 14;
	
	
	
	/**
	 * Test getGetObject
	 */
	@Test
	public void testGetRecord(){
		assertNotNull(query.getRecord()); 
	}
	
	/**
	 * Test getGetStream
	 */
	@Test
	public void testGetStream(){
		String streamName = "streamName"; //$NON-NLS-1$
		query.setStreamName(streamName);
		assertEquals(streamName,query.getStreamName());
	}
	
	/**
	 * Test execute
	 * @throws DataException 
	 */
	@Test
	public void testExecute() throws DataException{
		query.execute();
		assertEquals(0,query.getRow());
	}	
	
	/**
	 * tests IsAvoidLock
	 */
	@Test
	public void testIsAvoidLock(){
		assertTrue(query.isAvoidLock());
		
	}
	
	/**
	 * implementation to test
	 */
	private class StreamQuery extends NamedStreamBasicQuery{

		/**
		 * Creates a new StreamQuery object. 
		 */
		public StreamQuery(){
			emptyRecord();
		}
		
		@Override
		protected Record emptyRecord() {
			return new CsvRecord(COLUMNS);
		}
		
	}
	
}
