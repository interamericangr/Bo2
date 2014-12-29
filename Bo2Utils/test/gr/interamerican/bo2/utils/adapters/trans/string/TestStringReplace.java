package gr.interamerican.bo2.utils.adapters.trans.string;

import static org.junit.Assert.*;
import gr.interamerican.bo2.utils.adapters.trans.string.StringReplace;

import org.junit.Test;

/**
 * Tests {@link StringReplace}.
 */
public class TestStringReplace {
	
	/**
	 * Test for execute().
	 */
	@SuppressWarnings("nls")
	@Test
	public void test() {
		StringReplace calc = new StringReplace("aaa", "w");
		String input = "xcfaaag2525";
		String expected = "xcfwg2525";
		assertNull(calc.execute(null));
		assertEquals(expected, calc.execute(input));
	}
}