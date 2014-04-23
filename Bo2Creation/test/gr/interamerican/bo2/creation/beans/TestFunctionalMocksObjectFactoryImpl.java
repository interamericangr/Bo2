package gr.interamerican.bo2.creation.beans;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link FunctionalMocksObjectFactoryImpl}.
 */
public class TestFunctionalMocksObjectFactoryImpl {
	
	/**
	 * 
	 */
	@Test
	public void testConstructor() {
		FunctionalMocksObjectFactoryImpl impl = new FunctionalMocksObjectFactoryImpl();
		Assert.assertNotNull(impl.assistant);
	}

}
