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

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.utils.StringConstants;

import java.io.File;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link ColumnBasedSplitCsvFileOperation}
 */
public class TestColumnBasedSplitCsvFileOperation {
	
	/**
	 * Logical name of input stream.
	 */
	private String INPUT = "TestColumnBasedSplitCsvFileOperation.csv"; //$NON-NLS-1$
	
	/**
	 * Tests the lifeCycle of the operation.
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException
	 */
	@Test
	public void testLifeCycle() throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {			
			@SuppressWarnings("nls")
			@Override
			public void work() throws LogicException, DataException,
			InitializationException, UnexpectedException {
				ColumnBasedSplitCsvFileOperation op = new ColumnBasedSplitCsvFileOperation(INPUT, 0);
				op.setManagerName("LOCALFS");
				op.init(getProvider());
				op.open();
				op.execute();
				op.close();
				Assert.assertEquals(4, op.getResults().size());
				for(Map.Entry<String, String> result : op.getResults().entrySet()) {
					String fileName = result.getValue().substring(result.getValue().lastIndexOf(File.separator)+1);
					Assert.assertEquals(INPUT+StringConstants.MINUS+result.getKey()+".csv", fileName);
				}
			}
		}.execute();		
	}

}
