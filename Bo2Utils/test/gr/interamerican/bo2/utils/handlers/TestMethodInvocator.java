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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit tests for {@link MethodInvocator}.
 */
@SuppressWarnings("nls")
public class TestMethodInvocator {
	
	/**
	 * Creates a sample MethodInvocator that invokes methods
	 * on this object.
	 * 
	 * @param methodName
	 * 
	 * @return Returns the method invocator. 
	 */
	MethodInvocator sample(String methodName) {
		EventHandlerComponent<Object> ehc = 
			new EventHandlerComponent<Object>(this,ThrowingExceptionHandler.INSTANCE);
		return new MethodInvocator(ehc, methodName, this);
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
	void ambiguous(@SuppressWarnings("unused") Integer argument) {/* empty */}
	
	/**
	 * Method to be executed and throw an Exception.
	 * 
	 * @throws InstantiationException
	 */
	void doThrow() throws InstantiationException {
		throw new InstantiationException();
	}

	
	/**
	 * Unit test for the creation of a MethodInvocator based on an
	 * existing method.
	 */
	@Test
	public void testConstructor_WithExistingMethod() {
		String method = "onEvent";		
		MethodInvocator block = sample(method);			
		assertSame(block.handler.getCaller(), this);
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
		MethodInvocator block = sample("onEvent");			
		integer = 0;
		block.invoke();
		assertEquals(Integer.valueOf(1), this.integer);
	}
	
	/**
	 * Unit test for execute, throwing an exception.
	 */
	@Test
	public void testExecute_Throwing() {
		MethodInvocator block = sample("doThrow");		
		integer = 0;
		try {
			block.invoke();
		} catch (RuntimeException e) {
			Throwable cause = e.getCause();
			assertTrue(cause instanceof InstantiationException);
		}
	}
	 
	/**
	 * Unit test for execute, when the method being executed requires 
	 * arguments.
	 */
	@Test
	public void testExecute_WithParameters() {
		MethodInvocator block = sample("onEventWithParams");
		integer = 0;
		block.handler.setHandlerParameter(Integer.class, 7);
		block.invoke();
		assertEquals(Integer.valueOf(7), this.integer);
	}

}
