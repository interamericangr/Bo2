package gr.interamerican.bo2.utils.exc;

import org.junit.Assert;

import gr.interamerican.bo2.samples.exceptions.BarException;
import gr.interamerican.bo2.samples.exceptions.FooException;

import org.junit.Test;

/**
 * Tests for {@link MultipleExceptionTranslator}.
 */
@SuppressWarnings("unchecked")
public class TestMultipleExceptionTranslator {
	
	
	
	/**
	 * Tests the constructor.
	 *  
	 */	
	@Test
	public void testConstructor() {
		MultipleExceptionTranslator foobar = 
			new MultipleExceptionTranslator(FooException.class, BarException.class);
		Assert.assertEquals(2, foobar.unwrappers.size());
		Assert.assertNotNull(foobar.unwrappers.get(FooException.class));
		Assert.assertNotNull(foobar.unwrappers.get(BarException.class));
	}
	
	/**
	 * Tests rethrow(t).
	 * @throws Exception 
	 */
	@Test(expected=FooException.class)
	public void testRethrow_foo() throws Exception {
		MultipleExceptionTranslator foobar = 
			new MultipleExceptionTranslator(FooException.class, BarException.class);
		foobar.rethrow(new FooException());
	}
	
	/**
	 * Tests rethrow(t).
	 * @throws Exception 
	 */
	@Test(expected=BarException.class)
	public void testRethrow_bar() throws Exception {
		MultipleExceptionTranslator foobar = 
			new MultipleExceptionTranslator(FooException.class, BarException.class);
		foobar.rethrow(new BarException());
	}
	
	/**
	 * Tests rethrow(t).
	 * 
	 * @throws Exception 
	 */
	@Test(expected=RuntimeException.class)
	public void testRethrow_rte() throws Exception {		
		MultipleExceptionTranslator foobar = 
			new MultipleExceptionTranslator(FooException.class, BarException.class);
		foobar.rethrow(new Exception());
	}
	
	
	
	
	

}
