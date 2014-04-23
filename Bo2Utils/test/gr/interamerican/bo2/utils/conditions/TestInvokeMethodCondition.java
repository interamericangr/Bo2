package gr.interamerican.bo2.utils.conditions;

import gr.interamerican.bo2.utils.StringUtils;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Tests for {@link InvokeMethod}.
 */
@SuppressWarnings("nls")
public class TestInvokeMethodCondition {
	
	
	
	/**
	 * tests the constructor.
	 */	
	@Test
	public void testConstructor_forStaticOk() {
		String methodName = "isNullOrBlank";
		Class<?> clazz = StringUtils.class;
		Class<?> argType = String.class;
		InvokeMethodCondition<String> invoke = 
			new InvokeMethodCondition<String>(clazz, methodName, argType);
		Assert.assertNotNull(invoke);
	}
	
	
	/**
	 * tests the constructor.
	 */
	@Test
	public void testConstructor_Ok() {
		@SuppressWarnings("unchecked")
		Condition<Object> target = Mockito.mock(Condition.class);
		String methodName = "check";
		Class<?> clazz = Condition.class;
		Class<?> argType = Object.class;
		InvokeMethodCondition<Object> invoke = 
			new InvokeMethodCondition<Object>(clazz, methodName, argType, target);
		Assert.assertNotNull(invoke);
	}
	
	/**
	 * tests the constructor.
	 */	
	@Test
	public void testExecute() {		
		InvokeMethodCondition<String> invoke = 
			new InvokeMethodCondition<String>(StringUtils.class, "isNullOrBlank", String.class);
		Assert.assertTrue(invoke.check(null));
		Assert.assertFalse(invoke.check("X"));
		
	}

}
