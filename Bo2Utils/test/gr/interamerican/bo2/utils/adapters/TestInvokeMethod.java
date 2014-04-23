package gr.interamerican.bo2.utils.adapters;

import gr.interamerican.bo2.samples.bean.BeanWithString;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Tests for {@link InvokeMethod}.
 */
@SuppressWarnings("nls")
public class TestInvokeMethod {
	
	/**
	 * tests the constructor.
	 */
	@Test(expected=RuntimeException.class)
	public void testConstructor_forStaticWithNoStaticMethod() {		 
		new InvokeMethod<String, Object>(BeanWithString.class, "setString", String.class);		
	}
	
	/**
	 * tests the constructor.
	 */
	@Test(expected=RuntimeException.class)
	public void testConstructor_forStaticWithNotExistingMethod() {		 
		new InvokeMethod<String, Object>(BeanWithString.class, "setString", String.class);		
	}
	
	/**
	 * tests the constructor.
	 */	
	@Test
	public void testConstructor_forStaticOk() {
		String methodName = "abs";
		Class<?> clazz = Math.class;
		Class<?> argType = long.class;
		InvokeMethod<Long, Long> invoke = 
			new InvokeMethod<Long, Long>(clazz, methodName, argType);
		Assert.assertEquals(clazz, invoke.clazz);
		Assert.assertEquals(argType, invoke.argumentType);
		Assert.assertEquals(methodName, invoke.methodName);
		Assert.assertNotNull(invoke.method);
		Assert.assertNull(invoke.target);
	}
	
	/**
	 * tests the constructor.
	 */
	@Test(expected=RuntimeException.class)
	public void testConstructor_WithNotExistingMethod() {		 
		new InvokeMethod<String, Object>(BeanWithString.class, "setDate", String.class);		
	}
	
	/**
	 * tests the constructor.
	 */
	@Test
	public void testConstructor_Ok() {
		Transformation<?, ?> target = Mockito.mock(Transformation.class);
		String methodName = "execute";
		Class<?> clazz = Transformation.class;
		Class<?> argType = Object.class;
		InvokeMethod<Object, Object> invoke = 
			new InvokeMethod<Object, Object>(clazz, methodName, argType, target);
		Assert.assertEquals(clazz, invoke.clazz);
		Assert.assertEquals(argType, invoke.argumentType);
		Assert.assertEquals(methodName, invoke.methodName);
		Assert.assertNotNull(invoke.method);
		Assert.assertEquals(target,invoke.target);
	}
	
	/**
	 * tests the constructor.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testExecute() {
		Transformation<Object, Object> target = Mockito.mock(Transformation.class);
		Object expected = new Object();
		Mockito.when(target.execute(Mockito.any())).thenReturn(expected);
		String methodName = "execute";
		Class<?> clazz = Transformation.class;
		Class<?> argType = Object.class;
		InvokeMethod<Object, Object> invoke = 
			new InvokeMethod<Object, Object>(clazz, methodName, argType, target);
		Object arg = new Object();		
		Object actual = invoke.execute(arg);
		Assert.assertEquals(expected, actual);
		
	}

}
