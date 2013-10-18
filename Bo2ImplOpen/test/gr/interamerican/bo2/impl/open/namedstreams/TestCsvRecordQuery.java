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

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.records.CsvRecord;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link CsvRecordQuery}.
 */
public class TestCsvRecordQuery {
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {
		CsvRecordQuery q = new CsvRecordQuery(5,',');
		Assert.assertEquals(q.separator, ',');
		Assert.assertEquals(q.columnCount, 5);
		CsvRecord rec = (CsvRecord) q.getRecord();
		Assert.assertNotNull(rec);
	}
	
	/**
	 * Tests the lifecycle.
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testLifecycle() throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {
			@SuppressWarnings("nls")
			@Override
			public void work() throws LogicException, DataException,
			InitializationException, UnexpectedException {
				String logicalStreamName = "Sample.csv";
				String manager = "LOCALFS";
				
				int recordCount = 0;
				CsvRecordQuery q = new CsvRecordQuery(4, ';');
				q.setManagerName(manager);
				q.setStreamName(logicalStreamName);
				q.init(getProvider());
				q.open();
				q.execute();
				while (q.next()) {
					recordCount++;					
					CsvRecord rec = (CsvRecord) q.getRecord();
					Assert.assertNotNull(rec);
				}				
				q.close();
				Assert.assertEquals(4, recordCount);
				
			}
		}.execute();
	}

}
