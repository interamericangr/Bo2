package gr.interamerican.bo2.utils.adapters.trans;

import gr.interamerican.bo2.samples.bean.BeanWithString;
import gr.interamerican.bo2.utils.adapters.Transformation;
import gr.interamerican.bo2.utils.adapters.trans.InvokeMethod;

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
	@SuppressWarnings("unused")
	@Test(expected=RuntimeException.class)
	public void testConstructor_forStaticWithNoStaticMethod() {		 
		new InvokeMethod<String, Object>(BeanWithString.class, "setString", String.class);		
	}
	
	/**
	 * tests the constructor.
	 */
	@SuppressWarnings("unused")
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
		@SuppressWarnings("unused") InvokeMethod<Long, Long> invoke = 
			new InvokeMethod<Long, Long>(clazz, methodName, argType);		
	}
	
	/**
	 * tests the constructor.
	 */
	@SuppressWarnings("unused")
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
		Class<?> argType = Object.class;
		@SuppressWarnings("unused") InvokeMethod<Object, Object> invoke = 
			new InvokeMethod<Object, Object>(target, methodName, argType);
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
		Class<?> argType = Object.class;
		InvokeMethod<Object, Object> invoke = 
			new InvokeMethod<Object, Object>(target, methodName, argType);
		Object arg = new Object();		
		Object actual = invoke.execute(arg);
		Assert.assertEquals(expected, actual);
		
	}

}
