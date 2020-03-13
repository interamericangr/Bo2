package gr.interamerican.bo2.impl.open.creation.test.conditions;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit test of {@link StringIsClass}.
 */
public class TestStringIsClass {

	/**
	 * Test method for {@link StringIsClass#check(String)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCheck() {
		StringIsClass cond = new StringIsClass();
		assertTrue(cond.check("java.lang.String"));
		assertFalse(cond.check("java.lang.Srting"));
		assertFalse(cond.check("java.lang"));
	}

}
