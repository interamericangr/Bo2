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
import gr.interamerican.bo2.utils.Bo2UtilsEnvironment;

import org.junit.Test;

/**
 * Unit test for {@link SaveStreamOperation}.
 */
public class TestSaveStreamOperation {
	
	/**
	 * test.
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
				SaveStreamOperation op = new SaveStreamOperation();
				op.setManagerName("LOCALFS");
				op.setFilename("TestSaveStreamOperation.txt");
				String txt = "this is a message\nin two lines\n";
				op.setBytes(txt.getBytes());				
				op.init(getProvider());
				op.open();
				op.execute();
				op.close();
			}
		}.execute();		
	}
	
	

}
