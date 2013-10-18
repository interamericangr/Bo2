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
package gr.interamerican.bo2.impl.msoffice.excel;

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link XlsQuery}.
 */
public class TestXlsQuery {
	
	/**
	 * Unit test for the constructors.
	 */	
	@Test
	public void testConstructors() {
		XlsQuery q1 = new XlsQuery();
		assertEquals(0, q1.sheetIndex);		
		
		XlsQuery q2 = new XlsQuery(1);
		assertEquals(1, q2.sheetIndex);		
		
		String streamName = "inmemory"; //$NON-NLS-1$
		
		XlsQuery q3 = new XlsQuery(streamName);
		assertEquals(0, q3.sheetIndex);
		assertEquals(streamName, q3.getStreamName());

		XlsQuery q4 = new XlsQuery(streamName,3);
		assertEquals(3, q4.sheetIndex);
		assertEquals(streamName, q4.getStreamName());		
	}
	
	/**
	 * Tests the whole life cycle.
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
				String manager = "LOCALFS";
				String streamName = "Sample.xls";
				
				XlsQuery q = new XlsQuery(streamName,0);
				q.setManagerName(manager);
				q.init(getProvider());
				q.open();
				q.execute();
								
				while (q.next()) {
					XlsRow row = q.getXlsRow();
					Assert.assertNotNull(row);
				}
				int rows = q.getRow(); 
				q.close();				
				Assert.assertEquals(5, rows);
				
			}
		}.execute();
		
	}

}
