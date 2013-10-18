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

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link ThrowingExceptionHandler}.
 */
public class TestThrowingExceptionHandler {
	
	
	/**
	 * Test for handle.
	 */
	@Test
	public void testHandle_withException() {
		assertTheCause(new Exception());		
	}
	
	/**
	 * Test for handle.
	 */
	@Test
	public void testHandle_withRuntimeException() {
		assertTheSame(new RuntimeException());		
	}

	/**
	 * Test for handle.
	 */
	@Test
	public void testHandle_withError() {
		assertTheSame(new Error());		
	}

	
	/**
	 * Assert that the handler rethrows the same exception.
	 * @param t 
	 */
	void assertTheSame(Throwable t) {
		ThrowingExceptionHandler h = new ThrowingExceptionHandler();
		try {
			h.handle(t);			
		} catch (Throwable e) {
			Assert.assertSame(t, e);
		}
	}
	
	/**
	 * Assert that the handler rethrows as the cause
	 * of the thrown exception.
	 * 
	 * @param t 
	 */
	void assertTheCause(Throwable t) {
		ThrowingExceptionHandler h = new ThrowingExceptionHandler();
		try {
			h.handle(t);			
		} catch (Exception e) {
			Assert.assertSame(t, e.getCause());
		}
	}
	
	

}
