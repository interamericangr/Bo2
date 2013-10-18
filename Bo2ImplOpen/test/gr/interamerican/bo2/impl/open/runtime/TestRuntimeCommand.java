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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.creation.proxies.Mocks;
import gr.interamerican.bo2.impl.open.workers.AbstractOperation;
import gr.interamerican.bo2.impl.open.workers.SequentialOperation;

import org.junit.Test;

/**
 * Unit test for {@link RuntimeCommand}. 
 */
public class TestRuntimeCommand {
	
	
	/**
	 * Tests the constructor. 
	 */
	@Test
	public void testConstructor_withoutArgs() {
		RuntimeCommand cmd = new RuntimeCommand();
		assertNull(cmd.operation);
	}
	
	/**
	 * Tests the constructor. 
	 */
	@Test
	public void testConstructor_withOneArg() {
		Operation op = Mocks.empty(Operation.class);
		RuntimeCommand cmd = new RuntimeCommand(op);
		assertSame(op,cmd.operation);
	}

	/**
	 * Tests the constructor. 
	 */
	@Test
	public void testConstructor_withVararg() {
		Operation[] ops = {
			Mocks.empty(Operation.class),
			Mocks.empty(Operation.class)
		};
		RuntimeCommand cmd = new RuntimeCommand(ops);
		assertTrue(cmd.operation instanceof SequentialOperation);
	}

	/**
	 * Tests that the command runs
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testExecute() 
	throws UnexpectedException, DataException, LogicException {
		RuntimeCommand cmd = 
			new RuntimeCommand(new AssertProviderIsTransactional());		
		cmd.execute();
	}
	
	/**
	 * Test handle
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	@Test(expected=LogicException.class)
	public void testHandle_logicException() 
	throws UnexpectedException, DataException, LogicException{
		RuntimeCommand cmd = new RuntimeCommand();
		LogicException logic = new LogicException();
		cmd.handle(logic);
	}
	
	/**
	 * Test handle
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	@Test(expected=DataException.class)
	public void testHandle_DataException() throws UnexpectedException, DataException, LogicException{
		RuntimeCommand cmd = new RuntimeCommand();
		DataException data = new DataException();
		cmd.handle(data);
	}
	
	/**
	 * Test handle
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	@Test(expected=UnexpectedException.class)
	public void testHandle_UnexpectedException() throws UnexpectedException, DataException, LogicException{
		RuntimeCommand cmd = new RuntimeCommand();
		UnexpectedException unex = new UnexpectedException(new RuntimeException());
		cmd.handle(unex);
	}	
	
	
	
	
	/**
	 * Assertion that the provider is transactional. 
	 */
	class AssertProviderIsTransactional extends AbstractOperation {		
		@Override
		public void execute() throws LogicException, DataException {
			Provider p = this.getProvider();
			assertNotNull(p);
			assertNotNull(p.getTransactionManager());			
			assertSame(Bo2Session.getProvider(), p);
		}
	}
	
	/**
	 * Assertion that the provider is transactional. 
	 */
	class AssertProviderIsNonTransactional extends AbstractOperation {		
		@Override
		public void execute() throws LogicException, DataException {
			Provider p = this.getProvider();
			assertNotNull(p);
			assertNull(p.getTransactionManager());
			assertSame(Bo2Session.getProvider(), p);
		}
	}
	

	
	
	
		

}
