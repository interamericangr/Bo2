package gr.interamerican.bo2.utils.greek;

import static gr.interamerican.bo2.utils.StringConstants.EMPTY;
import gr.interamerican.bo2.utils.StringUtils;

/**
 * Utilities for the Greek Language.
 */
public class GreekUtils {
	
	/**
	 * Transcription743.
	 */
	private static Transcription743 tr743 = Transcription743.getInstance();
	
	/**
	 * VisuallySimilarLatin.
	 */
	private static VisuallySimilarLatin visuallySimilar = VisuallySimilarLatin.getInstance();

	

	/**
	 * Removes all characters that are not letters or digits, converts to 
	 * upper case and then replaces any latin character that has a similar 
	 * greek character with its greek similar (visually equal) character. <br/>
	 * 
	 * @param str
	 *        String to process.
	 * 
	 * @return Returns the result of the process.
	 */
	public static String removeSymbolsAndReplaceLatinWithSimilarGreekChars (String str) {	  
	   return visuallySimilar.removeSymbolsAndReplaceLatinWithSimilarGreekChars(str);
	}
	
	/**
	 * Checks if the specified string contains any letter that
	 * is not a greek capital letter.
	 * 
	 * Characters that are not letters, are ignored from the check. 
	 * 
	 * @param str
	 *        String to process.
	 * 
	 * @return Returns <code>true</code> if the specified string contains
	 *         any character that is a letter of any non greek alphabet.
	 *         Returns <code>false</code> if the specified string contains
	 *         only greek letters and/or any other character that is not
	 *         a letter.
	 */
	public static boolean containsNonGreekLetters (String str) {
	   String onlyLetters = StringUtils.removeAllButLetters(str);
	   if (onlyLetters.length()==0) {
		   return false;
	   }
	   String onlyGreek =removeEverythingButGreekLetters(onlyLetters);	  
	   return onlyGreek.length()!=onlyLetters.length();
	}
	
	/**
	 * Removes any character that is not a greek letter from the
	 * specified string
	 *  
	 * @param string
	 * 
	 * @return Returns the new string after removing from it any
	 *         character that is not a greek letter.
	 */
	public static String removeEverythingButGreekLetters(String string) {
		String isGreekRegex = "[^\\p{InGreek}]"; //$NON-NLS-1$		
		return string.replaceAll(isGreekRegex, EMPTY);
	}
	
	/**
	 * Removes any character that is a greek letter from the
	 * specified string
	 *  
	 * @param string
	 * 
	 * @return Returns the new string after removing from it any
	 *         character that is a greek letter.
	 */
	public static String removeGreekLetters(String string) {
		String isGreekRegex = "[\\p{InGreek}]"; //$NON-NLS-1$		
		return string.replaceAll(isGreekRegex, EMPTY);
	}
	
	/**
	 * Writes the specified Greek string value to Latin, according to the ELOT 743 standard.
	 * 
	 * @param greek 
	 *       Specified Greek string to convert to Latin
	 * 
	 * @return Returns the Greek string value to Latin, according to the ELOT 743 standard.
	 * 
	 */
	public static String toLatin(String greek) {
		return tr743.transcript(greek);	
	}


}
