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
package gr.interamerican.bo2.utils.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


import org.junit.Test;

/**
 * Unit tests for {@link AbstractMethodInvocator}.
 */
@SuppressWarnings("nls")
public class TestAbstractMethodInvocator {
	
	/**
	 * Creates a sample MethodInvocator that invokes methods
	 * on this object.
	 * 
	 * @param methodName
	 * 
	 * @return Returns the method invocator. 
	 */
	MI sample(String methodName) {
		return new MI (ThrowingExceptionHandler.INSTANCE, methodName, this);
	}
	
	
	/**
	 * Value used for testing.
	 */
	Integer integer = 0;
	
	/**
	 * Method to be executed by MethodInvocator. 
	 */
	void onEvent() {
		integer = 1;
	}
	
	/**
	 * Method to be executed by MethodInvocator. 
	 * 
	 * @param i
	 *         
	 */
	void onEventWithParams(Integer i) {
		integer = i;
	}
	
	
	/**
	 * Overloaded method.
	 */
	void ambiguous() {/* empty */}

	/**
	 * Overloaded method.
	 * 
	 * @param argument 
	 */
	void ambiguous(Integer argument) {/* empty */}
	
	/**
	 * Method to be executed and throw an Exception.
	 * 
	 * @throws InstantiationException
	 */
	void doThrow(Exception t) throws Exception {
		throw t;
	}

	
	/**
	 * Unit test for the creation of a MethodInvocator based on an
	 * existing method.
	 */
	@Test
	public void testConstructor_WithExistingMethod() {
		this.integer = 0;
		String method = "onEvent";		
		MI block = sample(method);
		assertEquals(block.methodName, method);
		assertNotNull(block.method);
	}
	
	
	
	/**
	 * Unit test for the creation of a MethodInvocator based on a
	 * method that has an ambiguous name.
	 */	
	@Test(expected=RuntimeException.class)
	public void testConstructor_WithAmbiguousMethod() {
		sample("ambiguous");
	}
	
	/**
	 * Unit test for the creation of a MethodInvocator based on a
	 * method that does not exist.
	 */	
	@Test(expected=RuntimeException.class)
	public void testConstructor_WithNotExistingMethod() {
		sample("foo");
	}
	
	
	
	/**
	 * Unit test for execute, when the even handler method 
	 * does not have any parameter.
	 */
	@Test
	public void testInvoke_WithoutParameters() {
		this.integer = 0;
		MI block = sample("onEvent");
		block.invoke();
		assertEquals(Integer.valueOf(1), this.integer);
	}
	
	/**
	 * Unit test for invoke(), throwing an exception.
	 */
	@Test
	public void testExecute_Throwing() {
		this.integer = 0;
		MI block = sample("doThrow");
		Exception t = new Exception();
		
		Object args[] = {t};		
		block.setArguments(args);
		try {
			block.invoke(); 
		} catch (RuntimeException e) {
			Throwable cause = e.getCause();
			assertEquals(t, cause);
		}
	}
	 
	/**
	 * Unit test for invoke(), when the method being executed requires 
	 * arguments.
	 */
	@Test
	public void testExecute_WithParameters() {
		this.integer = 0;
		MI block = sample("onEventWithParams");
		Integer param = 17;
		Object args[] = {param};
		block.setArguments(args);		
		block.invoke();
		assertEquals(param, this.integer);
	}
	
	/**
	 * Concrete implementation of {@link AbstractMethodInvocator}.
	 */
	class MI extends AbstractMethodInvocator {
		/**
		 * Arguments.
		 */
		Object[] arguments;
		
		public MI (ExceptionHandler handler, String methodName, Object owner) {
			super(handler, methodName, owner);
		}

		@Override
		protected Object[] getArguments() {		
			return arguments;
		}
		
		/**
		 * Sets the arguments.
		 * 
		 * @param arguments
		 */
		public void setArguments(Object[] arguments) {
			this.arguments = arguments;
		}
	}

}
