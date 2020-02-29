package gr.interamerican.bo2.utils.adapters.trans.string;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.adapters.trans.date.DayOfMonth;
import gr.interamerican.bo2.utils.adapters.trans.string.Left3;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link DayOfMonth}.
 */
public class TestLeft3 {
	
	/** The calc. */
	Left3 calc = new Left3();
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute_large() {
		String input = "xcfg";		  //$NON-NLS-1$
		String actual = calc.execute(input);		
		Assert.assertEquals("xcf", actual); //$NON-NLS-1$
	}
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute_small() {
		String input = "xc";		  //$NON-NLS-1$
		String actual = calc.execute(input);		
		Assert.assertEquals("xc", actual); //$NON-NLS-1$
	}
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute_null() {
		String actual = calc.execute(null);		
		Assert.assertNull(actual);
	}

	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute_empty() {
		String actual = calc.execute(StringConstants.EMPTY);		
		Assert.assertEquals(StringConstants.EMPTY, actual);
	}

}
