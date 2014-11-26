package gr.interamerican.bo2.utils.exc;

import static org.mockito.Mockito.*;
import gr.interamerican.bo2.samples.exceptions.FooException;

import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import org.junit.Test;


/**
 * Tests for {@link ExceptionUnwrapper}.
 */
public class TestExceptionUnwrapper {
	
	/**
	 * Tests get(t).
	 */
	@Test
	public void testGet_theSame() {
		ExceptionUnwrapper<FooException> uw = new ExceptionUnwrapper<FooException>(FooException.class);
		FooException ex = new FooException();
		FooException actual = uw.get(ex);
		Assert.assertEquals(ex, actual);
	}
	
	/**
	 * Tests get(t).
	 */
	@Test
	public void testGet_null() {
		ExceptionUnwrapper<FooException> uw = new ExceptionUnwrapper<FooException>(FooException.class);
		FooException actual = uw.get(new Exception());
		Assert.assertNull(actual);
	}
	
	/**
	 * Tests get(t).
	 */
	@Test
	public void testGet_cause() {
		ExceptionUnwrapper<FooException> uw = new ExceptionUnwrapper<FooException>(FooException.class);
		FooException ex = new FooException();
		RuntimeException rte = new RuntimeException(ex);		
		FooException actual = uw.get(rte);
		Assert.assertEquals(ex, actual);
	}
	
	/**
	 * Tests get(t).
	 */
	@Test
	public void testGet_targetOfCause() {
		ExceptionUnwrapper<FooException> uw = new ExceptionUnwrapper<FooException>(FooException.class);
		FooException ex = new FooException();
		InvocationTargetException ite = new InvocationTargetException(ex);
		RuntimeException rte = new RuntimeException(ite);		
		FooException actual = uw.get(rte);
		Assert.assertEquals(ex, actual);
	}
	
	/**
	 * Tests get(t).
	 */
	@Test
	public void testGet_target() {
		ExceptionUnwrapper<FooException> uw = new ExceptionUnwrapper<FooException>(FooException.class);
		FooException ex = new FooException();
		InvocationTargetException ite = new InvocationTargetException(ex);		
		FooException actual = uw.get(ite);
		Assert.assertEquals(ex, actual);
	}
	
	/**
	 * Tests get(t).
	 */
	@Test
	public void testGet_causeOfTarget() {
		ExceptionUnwrapper<FooException> uw = new ExceptionUnwrapper<FooException>(FooException.class);
		FooException ex = new FooException();
		InvocationTargetException ite = new InvocationTargetException(ex);	
		RuntimeException rte = new RuntimeException(ite);
		FooException actual = uw.get(rte);
		Assert.assertEquals(ex, actual);
	}
	
	/**
	 * Tests getExceptionClass().
	 */
	@Test
	public void testGetClass() {
		ExceptionUnwrapper<FooException> uw = new ExceptionUnwrapper<FooException>(FooException.class);
		Assert.assertEquals(uw.exceptionClass, uw.getExceptionClass());
	}
	
	/**
	 * Tests get(t).
	 * @throws FooException 
	 */
	@SuppressWarnings("unchecked")
	@Test(expected=FooException.class)
	public void testRethrow() throws FooException {
		ExceptionUnwrapper<FooException> uw = mock(ExceptionUnwrapper.class);
		Throwable t = new Throwable();
		when(uw.get(t)).thenReturn(new FooException());
		ExceptionUnwrapper.rethrow(uw, t);
	}
	
	/**
	 * Tests get(t).
	 * @throws FooException 
	 */
	@SuppressWarnings("unchecked")
	@Test()
	public void testRethrow_doNothing() throws FooException {
		ExceptionUnwrapper<FooException> uw = mock(ExceptionUnwrapper.class);
		Throwable t = new Throwable();		
		ExceptionUnwrapper.rethrow(uw, t);
		/* nothing should happen */
	}
	
	
	
	
	

}
