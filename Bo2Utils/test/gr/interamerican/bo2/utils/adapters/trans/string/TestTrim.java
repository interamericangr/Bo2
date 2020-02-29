package gr.interamerican.bo2.utils.adapters.trans.string;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit Test of {@link Trim}.
 */
public class TestTrim {

	/**
	 * Test method for {@link Trim#execute(String)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testExecute() {
		Trim calc = new Trim();
		assertEquals("f24d232", calc.execute("f24d232"));
		assertEquals("f24 g33", calc.execute(" f24 g33"));
		assertEquals("v24cf24 11sd x", calc.execute("  v24cf24 11sd x    "));
		assertNull(calc.execute(null));
	}
}