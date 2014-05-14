package gr.interamerican.bo2.arch.utils.adapters.number;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link AbsoluteValue}.
 */
public class TestAbsoluteValue {
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {
		AbsoluteValue av = new AbsoluteValue();
		Assert.assertNotNull(av.transformation);
		Assert.assertTrue(av.transformation instanceof gr.interamerican.bo2.utils.adapters.number.AbsoluteValue);
	}

}
