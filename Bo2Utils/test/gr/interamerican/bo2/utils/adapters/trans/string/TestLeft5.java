package gr.interamerican.bo2.utils.adapters.trans.string;

import static org.junit.Assert.*;
import gr.interamerican.bo2.utils.StringConstants;

import org.junit.Test;

/**
 * Tests for {@link Left5}.
 */
public class TestLeft5 {
	
	
	
	/**
	 * Test for execute().
	 */
	@SuppressWarnings("nls")
	@Test
	public void testExecute() {
		Left5 calc = new Left5();
		assertEquals("xcf12", calc.execute("xcf12431g"));
		assertEquals("f24", calc.execute("f24"));
		assertNull(calc.execute(null));
		assertEquals(StringConstants.EMPTY, calc.execute(""));
	}
}