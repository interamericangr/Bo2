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
package gr.interamerican.bo2.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link ExceptionUtils}.
 */
public class TestExceptionUtils {
	
	/**
	 * Tests initialCause. 
	 */
	@Test
	public void testInitialCause() {
		Exception ex1 = new ClassNotFoundException();
		Exception ex2 = new RuntimeException(ex1);
		Exception ex3 = new Exception(ex2);
		Throwable cause3 = ExceptionUtils.initialCause(ex3);
		assertTrue(cause3==ex1);
		
		Throwable cause1 = ExceptionUtils.initialCause(ex1);
		assertTrue(cause1==ex1);
	} 
	
	/**
	 * Tests isCausedBy. 
	 */
	@Test
	public void testIsCausedBy() {
		Exception ex1 = new ClassNotFoundException();
		Exception ex2 = new RuntimeException(ex1);
		Exception ex3 = new Exception(ex2);
			
		assertTrue(ExceptionUtils.isCausedBy(ex3, ClassNotFoundException.class));
		assertTrue(ExceptionUtils.isCausedBy(ex3, Exception.class));		
		assertTrue(ExceptionUtils.isCausedBy(ex1, ClassNotFoundException.class));		
		assertTrue(ExceptionUtils.isCausedBy(ex3, Exception.class));
	}
	
	/**
	 * Tests isCausedBy.
	 */
	@Test
	public void testCauseInTheChain() {
		Exception ex1 = new ClassNotFoundException();
		Exception ex2 = new RuntimeException(ex1);
		Exception ex3 = new Exception(ex2);
		
		Throwable t1 = ExceptionUtils.causeInTheChain(ex3, ClassNotFoundException.class);
		assertNotNull(t1);
		assertSame(ex1, t1);
		Throwable t2 = ExceptionUtils.causeInTheChain(ex3, RuntimeException.class);
		assertNotNull(t2);
		assertSame(ex2, t2);
		Throwable t3 = ExceptionUtils.causeInTheChain(ex3, Exception.class);
		assertNotNull(t3);
		assertSame(ex3, t3);		
		Throwable t = ExceptionUtils.causeInTheChain(ex1, Exception.class);
		assertNotNull(t);
		assertSame(ex1, t);
		Throwable tNull = ExceptionUtils.causeInTheChain(ex1, IllegalAccessException.class);
		assertNull(tNull);
	}
	
	/**
	 * Tests getThrowableStackTrace. 
	 */
	@Test
	public void testGetThrowableStackTrace() {
		Exception ex1 = new ClassNotFoundException();
		Exception ex2 = new RuntimeException(ex1);
		Exception ex3 = new Exception(ex2);
		String str = ExceptionUtils.getThrowableStackTrace(ex3);
		assertNotNull(str);
	}
		
	
	/**
	 * tests runtimeException(t).
	 */	
	@Test
	public void testRuntimeException_withRte(){
		RuntimeException expected = new RuntimeException();
		RuntimeException actual = ExceptionUtils.runtimeException(expected);
		assertEquals(expected,actual);
	}
	
	/**
	 * tests runtimeException(t).
	 */	
	@Test
	public void testRuntimeException_withOtherEx(){
		Throwable t = new Throwable();		
		RuntimeException actual = ExceptionUtils.runtimeException(t);
		Assert.assertNotEquals(t, actual);
		Assert.assertEquals(t, actual.getCause());
	}
	
	
	/**
	 * tests unwrap(t).
	 */	
	@Test
	public void testUnwrap_withRte(){
		Exception cause = new Exception();
		RuntimeException rtex = new RuntimeException(cause);		
		Throwable actual = ExceptionUtils.unwrap(rtex);
		Assert.assertEquals(cause, actual);
	}
	
	/**
	 * tests unwrap(ex).
	 */	
	@Test
	public void testUnwrap_withOther(){
		Throwable t = new Throwable();
		RuntimeException wrapper = new RuntimeException(t);
		Throwable actual = ExceptionUtils.unwrap(wrapper);
		Assert.assertEquals(t, actual);		
	}
	
	/**
	 * tests unwrap(ex).
	 */	
	@Test
	public void testUnwrap_withRteThatHasnoCause(){
		RuntimeException npex = new NullPointerException();
		Throwable actual = ExceptionUtils.unwrap(npex);
		Assert.assertEquals(npex, actual);		
	}
	
	/**
	 * tests unwrap(ex).
	 */	
	@Test
	public void testUnwrap_withRteThatHasInvocationtargetEx(){
		Exception initial = new Exception();		
		InvocationTargetException itex = new InvocationTargetException(initial);		
		RuntimeException npex = new RuntimeException(itex);
		Throwable actual = ExceptionUtils.unwrap(npex);
		Assert.assertEquals(initial, actual);		
	}
	
	/**
	 * tests unwrap(ex).
	 */	
	@Test
	public void testUnwrap_withRteThatHasInvocationtargetExAnotherRte(){
		Exception initial = new Exception();	
		RuntimeException targetEx = new RuntimeException(initial);		
		InvocationTargetException itex = new InvocationTargetException(targetEx);		
		RuntimeException npex = new RuntimeException(itex);
		Throwable actual = ExceptionUtils.unwrap(npex);
		Assert.assertEquals(initial, actual);		
	}
	

	

}
