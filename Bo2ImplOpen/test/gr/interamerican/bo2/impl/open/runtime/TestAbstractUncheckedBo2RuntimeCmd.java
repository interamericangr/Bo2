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

import static org.junit.Assert.fail;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;

import org.junit.Test;

/**
 * 
 */
public class TestAbstractUncheckedBo2RuntimeCmd {

	
	/**
	 * Test execute
	 */
	@Test(expected=RuntimeException.class)
	public void testExecute_withRuntimeException(){
		AssertException cmd = new AssertException(new RuntimeException());
		cmd.execute();
	}

	/**
	 * Test execute
	 */
	@Test(expected=Error.class)
	public void testExecute_withError(){
		AssertException cmd = new AssertException(new Error());
		cmd.execute();
	}
	
	/**
	 * Test execute
	 */
	@Test(expected=RuntimeException.class)
	public void testExecute_withDataException(){
		AssertException cmd = new AssertException(new DataException());
		cmd.execute();
	}
	
	/**
	 * Test execute
	 */
	@Test(expected=RuntimeException.class)
	public void testExecute_withLogicException(){
		AssertException cmd = new AssertException(new LogicException());
		cmd.execute();
	}
	
	/**
	 * Test execute
	 */
	@Test(expected=RuntimeException.class)
	public void testExecute_withInitializationException(){
		AssertException cmd = new AssertException(new InitializationException());
		cmd.execute();
	}
	
	/**
	 * Test execute
	 */
	@Test(expected=RuntimeException.class)
	public void testExecute_withUnexpectedException(){
		UnexpectedException ex = new UnexpectedException(new InitializationException());
		AssertException cmd = new AssertException(ex);
		cmd.execute();
	}
	
	/**
	 * implementation to test
	 */
	class AssertException extends AbstractUncheckedBo2RuntimeCmd {
		/**
		 * throwable to throw;
		 */
		Throwable t;

		/**
		 * Creates a new AssertException object.
		 * 
		 * @param t 
		 */
		public AssertException(Throwable t) {
			super();
			this.t = t;
		}

		@Override
		public void work() 
		throws LogicException, DataException, InitializationException, UnexpectedException {
			if (t instanceof LogicException) {
				throw (LogicException)t;
			}
			if (t instanceof DataException) {
				throw (DataException)t;
			}
			if (t instanceof InitializationException) {
				throw (InitializationException)t;
			}
			if (t instanceof UnexpectedException) {
				throw (UnexpectedException)t;
			}
			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			if (t instanceof Error) {
				throw (Error)t;
			}
			fail("Unexpected exception"); //$NON-NLS-1$
		}
	}
	
	
	

}
