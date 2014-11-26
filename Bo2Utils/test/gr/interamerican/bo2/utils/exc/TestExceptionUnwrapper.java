package gr.interamerican.bo2.utils.exc;

import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import org.junit.Test;

import gr.interamerican.bo2.utils.exc.ExceptionUnwrapper;

/**
 * Tests for {@link ExceptionUnwrapper}.
 */
public class TestExceptionUnwrapper {
	
	/**
	 * Tests get(t).
	 */
	@Test
	public void testGet_theSame() {
		ExceptionUnwrapper<MyException> uw = new ExceptionUnwrapper<MyException>(MyException.class);
		MyException ex = new MyException();
		MyException actual = uw.get(ex);
		Assert.assertEquals(ex, actual);
	}
	
	/**
	 * Tests get(t).
	 */
	@Test
	public void testGet_null() {
		ExceptionUnwrapper<MyException> uw = new ExceptionUnwrapper<MyException>(MyException.class);
		MyException actual = uw.get(new Exception());
		Assert.assertNull(actual);
	}
	
	/**
	 * Tests get(t).
	 */
	@Test
	public void testGet_cause() {
		ExceptionUnwrapper<MyException> uw = new ExceptionUnwrapper<MyException>(MyException.class);
		MyException ex = new MyException();
		RuntimeException rte = new RuntimeException(ex);		
		MyException actual = uw.get(rte);
		Assert.assertEquals(ex, actual);
	}
	
	/**
	 * Tests get(t).
	 */
	@Test
	public void testGet_targetOfCause() {
		ExceptionUnwrapper<MyException> uw = new ExceptionUnwrapper<MyException>(MyException.class);
		MyException ex = new MyException();
		InvocationTargetException ite = new InvocationTargetException(ex);
		RuntimeException rte = new RuntimeException(ite);		
		MyException actual = uw.get(rte);
		Assert.assertEquals(ex, actual);
	}
	
	/**
	 * Tests get(t).
	 */
	@Test
	public void testGet_target() {
		ExceptionUnwrapper<MyException> uw = new ExceptionUnwrapper<MyException>(MyException.class);
		MyException ex = new MyException();
		InvocationTargetException ite = new InvocationTargetException(ex);		
		MyException actual = uw.get(ite);
		Assert.assertEquals(ex, actual);
	}
	
	/**
	 * Tests get(t).
	 */
	@Test
	public void testGet_causeOfTarget() {
		ExceptionUnwrapper<MyException> uw = new ExceptionUnwrapper<MyException>(MyException.class);
		MyException ex = new MyException();
		InvocationTargetException ite = new InvocationTargetException(ex);	
		RuntimeException rte = new RuntimeException(ite);
		MyException actual = uw.get(rte);
		Assert.assertEquals(ex, actual);
	}
	
	
	static class MyException extends Exception {};
	
	

}
