package gr.interamerican.bo2.utils.adapters.trans;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gr.interamerican.bo2.utils.adapters.Transformation;
import gr.interamerican.bo2.utils.adapters.trans.MethodBasedTransformation;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Tests for {@link MethodBasedTransformation}.
 */
@SuppressWarnings("nls")
public class TestMethodBasedTransformation {
	
	
	/**
	 * tests the constructor.
	 */
	@Test
	public void testConstructor() {
		Transformation<?, ?> owner = Mockito.mock(Transformation.class);
		String methodName = "execute";
		MethodBasedTransformation<Object, Object> mbt = 
			new MethodBasedTransformation<Object, Object>(methodName, owner);
		Assert.assertNotNull(mbt.cmi);
	}
	
	/**
	 * tests the constructor.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testExecute() {
		Transformation<Object, Object> owner = Mockito.mock(Transformation.class);		
		String methodName = "execute";
		MethodBasedTransformation<Object, Object> mbt = 
			new MethodBasedTransformation<Object, Object>(methodName, owner);
		Object arg = new Object();
		Object ret = new Object();
		when(owner.execute(arg)).thenReturn(ret);
		Object actual = mbt.execute(arg);
		Assert.assertEquals(ret, actual);
		verify(owner, times(1)).execute(arg);
	}

}
