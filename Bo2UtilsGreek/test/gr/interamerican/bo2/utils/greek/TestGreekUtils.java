package gr.interamerican.bo2.utils.greek;

import static gr.interamerican.bo2.utils.greek.GreekUtils.containsNonGreekLetters;
import static gr.interamerican.bo2.utils.greek.GreekUtils.removeEverythingButGreekLetters;
import static gr.interamerican.bo2.utils.greek.GreekUtils.removeGreekLetters;
import static gr.interamerican.bo2.utils.greek.GreekUtils.removeSymbolsAndReplaceLatinWithSimilarGreekChars;
import static gr.interamerican.bo2.utils.greek.GreekUtils.toLatin;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * 
 */
public class TestGreekUtils {
	
	/**
	 * test removeSymbolsAndReplaceLatinWithSimilarGreekChars
	 */
	@SuppressWarnings("nls")
	@Test
	public void testRemoveSignsAndReplaceLatinWithSimilarGreekChars() {
		String[] inputs = 
			{"yzx 5248", "ikn-4789", "abe_5486", "opt3547", "hmg3547", "kkk*2525*"};
		String[] expecteds = 
			{"υζχ5248",  "ικν4789",  "αβε5486",  "ορτ3547", "ημg3547", "κκκ2525"};
		
		for (int i = 0; i < inputs.length; i++) {
			String actual = removeSymbolsAndReplaceLatinWithSimilarGreekChars(inputs[i]);
			assertEquals(expecteds[i],actual.toLowerCase());
		}
	}
	
	/**
	 * test containsNonGreekLetters
	 */
	@SuppressWarnings("nls")
	@Test
	public void testContainsNonGreekLetters() {
		String[] inputs = 
			{"υζχ-5248!", "ibm4789", "γγκ5486", "* ***", "/ //f", "\u0428-2525"};
		boolean[] expecteds = 
			{false,        true,      false,    false,   true,   true };
		
		for (int i = 0; i < inputs.length; i++) {
			System.out.println(inputs[i]);
			boolean actual = containsNonGreekLetters(inputs[i]);
			assertEquals("Input string="+inputs[i], expecteds[i],actual);
		}
	}
	
	/**
	 * test removeNonGreekLetters
	 */
	@SuppressWarnings("nls")
	@Test
	public void testRemoveEverythingButGreekLetters() {
		String[] inputs = 
			{"υζχ-5248!", "ibm4789", "γγκ5486", "* ***", "/ //f", "\u0428-2525"};
		String[] expecteds = 
			{"υζχ", "", "γγκ", "", "", ""};
		
		for (int i = 0; i < inputs.length; i++) {
			System.out.println(inputs[i]);
			String actual = removeEverythingButGreekLetters(inputs[i]);
			assertEquals(expecteds[i], actual);
		}
	}
	
	/**
	 * test removeNonGreekLetters
	 */
	@SuppressWarnings("nls")
	@Test
	public void testRemoveGreekLetters() {
		String[] inputs = 
			{"υζχ-5248!", "ibm4789", "γγκ5486", "* ***", "/ //f", "\u0428-2525"};
		String[] expecteds = 
			{"-5248!", "ibm4789", "5486", "* ***", "/ //f", "\u0428-2525"};
		
		for (int i = 0; i < inputs.length; i++) {
			System.out.println(inputs[i]);
			String actual = removeGreekLetters(inputs[i]);
			assertEquals(expecteds[i], actual);
		}
	}
	
	/**
	 * test removeNonGreekLetters
	 */
	@SuppressWarnings("nls")
	@Test
	public void testToLatin() {
		String[] inputs = 
			{"Παπαδοπουλος", "Σπυρος Νακος and John", "Ουρανοκατεβατος",};
		String[] expecteds = 
			{"Papadopoulos", "Spyros Nakos and John", "Ouranokatevatos",};
		
		for (int i = 0; i < inputs.length; i++) {
			System.out.println(inputs[i]);
			String actual = toLatin(inputs[i]);
			assertEquals(expecteds[i], actual);
		}
	}

}
