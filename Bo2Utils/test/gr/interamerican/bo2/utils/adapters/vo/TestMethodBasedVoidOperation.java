package gr.interamerican.bo2.utils.adapters.vo;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import gr.interamerican.bo2.utils.adapters.Transformation;
import gr.interamerican.bo2.utils.adapters.vo.MethodBasedVoidOperation;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Tests for {@link MethodBasedVoidOperation}.
 */
@SuppressWarnings("nls")
public class TestMethodBasedVoidOperation {
	
	
	/**
	 * tests the constructor.
	 */
	@Test
	public void testConstructor() {
		Transformation<?, ?> owner = Mockito.mock(Transformation.class);
		String methodName = "execute";
		MethodBasedVoidOperation<Object> mbvo = 
			new MethodBasedVoidOperation<Object>(methodName, owner);
		Assert.assertNotNull(mbvo.cmi);
	}
	
	/**
	 * tests the constructor.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testExecute() {
		Transformation<Object, Object> owner = Mockito.mock(Transformation.class);		
		String methodName = "execute";
		MethodBasedVoidOperation<Object> mbvo = 
			new MethodBasedVoidOperation<Object>(methodName, owner);
		Object arg = new Object();
		mbvo.execute(arg);		
		verify(owner, times(1)).execute(arg);
	}

}
