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

import org.junit.Test;

/**
 * Unit test for {@link CsvQuery2HtmlTableOperation}.
 */
public class TestCsvQuery2HtmlTableOperation {
		
	/**
	 * test.
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
				CsvQuery2HtmlTableOperation op = 
					new CsvQuery2HtmlTableOperation (6,';', "CsvQuery2HtmlTableOperation");
				op.setManagerName("LOCALFS");
				op.init(getProvider());
				op.open();
				op.execute();
				op.close();
			}
		}.execute();		
	}

}
