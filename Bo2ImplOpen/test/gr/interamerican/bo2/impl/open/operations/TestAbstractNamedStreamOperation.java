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
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.annotations.ManagerName;
import gr.interamerican.bo2.impl.open.runtime.RuntimeCommand;

import java.io.BufferedReader;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link AbstractNamedStreamOperation}.
 */
public class TestAbstractNamedStreamOperation {
	
	
	
	/**
	 * Test.
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	@Test
	public void testLifeCycle() 
	throws UnexpectedException, DataException, LogicException {
		RuntimeCommand cmd = new RuntimeCommand(new Op());
		cmd.execute();
	}
	
	
	/**
	 * Sample operation.
	 */
	@ManagerName("LOCALFS")
	class Op extends AbstractNamedStreamOperation<BufferedReader> {

		/**
		 * Creates a new Sample object. 
		 */
		protected Op() {
			super("Sample.csv"); //$NON-NLS-1$
		}
		
		@Override
		public void execute() throws LogicException, DataException {
			Assert.assertNotNull(stream);
			String s = stream.readString();
			System.out.println(s);
			Assert.assertNotNull(s);
		}
		
	}
	

}
