package gr.interamerican.bo2.arch.utils.adapters.number;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link NegativeToZero}.
 */
public class TestNegativeToZero {
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {
		NegativeToZero av = new NegativeToZero();
		Assert.assertNotNull(av.transformation);
		Assert.assertTrue(av.transformation instanceof gr.interamerican.bo2.utils.adapters.number.NegativeToZero);
	}

}
