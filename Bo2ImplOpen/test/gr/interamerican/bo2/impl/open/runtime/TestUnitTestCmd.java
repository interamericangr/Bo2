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
package gr.interamerican.bo2.impl.open.runtime;

import static org.junit.Assert.assertNotNull;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.samples.archutil.po.User;

import org.junit.Test;

/**
 * 
 */
public class TestUnitTestCmd {


	/**
	 * impl to test
	 */
	UnitTestCmdImpl impl = new UnitTestCmdImpl(User.class);
	
	
	/**
	 * Tests work with class
	 * @throws UnexpectedException
	 * @throws LogicException
	 * @throws DataException
	 * @throws InitializationException
	 */
	@Test
	public void testWork() throws UnexpectedException, LogicException, DataException, InitializationException{
		impl.work();
		assertNotNull(impl.subject);
		
	}
	
	
	/**
	 * implememtation to test
	 */
	private class UnitTestCmdImpl extends UnitTestCmd<Object>{

	
		/**
		 * Creates a new UnitTestCmdImpl object. 
		 *
		 * @param classUnderTest
		 */
		public UnitTestCmdImpl(Class<? extends Object> classUnderTest) {
			super(classUnderTest);
		}

		@Override
		public void test() throws LogicException, DataException,
				InitializationException, UnexpectedException {
			//empty
		}
		
	}
	
}
