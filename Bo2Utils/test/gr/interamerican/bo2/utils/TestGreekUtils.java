package gr.interamerican.bo2.utils;

import static gr.interamerican.bo2.utils.GreekUtils.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * 
 */
public class TestGreekUtils {
	
	/**
	 * test toGreekPlateNo
	 */
	@SuppressWarnings("nls")
	@Test
	public void testRemoveSignsAndReplaceLatinWithSimilarGreekChars() {
		String[] inputs = 
			{"yzx 5248",  "ikn-4789", "abe_5486", "opt3547", "hmg3547", "kkk*2525*"};
		String[] expecteds = 
			{"υζχ5248",   "ικν4789",  "αβε5486",  "ορτ3547", "ημg3547", "κκκ2525"};
		
		for (int i = 0; i < inputs.length; i++) {
			String actual = removeSymbolsAndReplaceLatinWithSimilarGreekChars(inputs[i]);
			assertEquals(expecteds[i].toUpperCase(),actual);
		}
	}

}
