package gr.interamerican.bo2.utils.greek.adapters;

import gr.interamerican.bo2.utils.adapters.date.DayOfMonth;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link DayOfMonth}.
 */
public class TestRemoveSymbolsAndReplaceLatinWithSimilarGreekChars {
	
	/**
	 * Test for execute().
	 */
	@SuppressWarnings("nls")
	@Test
	public void testExecute() {
		RemoveSymbolsAndReplaceLatinWithSimilarGreekChars calc = 
				new RemoveSymbolsAndReplaceLatinWithSimilarGreekChars();
		String input = "xcfgh 2525 !";		 
		String actual = calc.execute(input);		
		Assert.assertEquals("÷cfgç2525".toUpperCase(), actual);
	}

}

