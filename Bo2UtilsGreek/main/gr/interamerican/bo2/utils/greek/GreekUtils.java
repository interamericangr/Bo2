package gr.interamerican.bo2.utils.greek;

import static gr.interamerican.bo2.utils.StringConstants.EMPTY;
import gr.interamerican.bo2.utils.StringUtils;

/**
 * Utilities for the Greek Language.
 */
public class GreekUtils {

	/**
	 * Converts an english uppercase character to its corresponding greek
	 * that looks exactly the same. <br/>
	 * 
	 * The method is used for the translation of all latin characters
	 * to their corresponding greek character in plate numbers.
	 * 
	 * Examples:  
	 * <li> input: A (english) return ï¿½ (greek) </li>
	 * <li> input: S (english) return S (there is no corresponding greek letter) </li>
	 * 
	 * @param c english character
	 * 
	 * @return greek character
	 */
	private static char greek(char c) {
	  switch (c) {
	  	case 'A':return 'Á';
		case 'B':return 'Â';
	  	case 'E':return 'Å';
	  	case 'H':return 'Ç';
	  	case 'I':return 'É';
	  	case 'K':return 'Ê';
	  	case 'M':return 'Ì';
	  	case 'N':return 'Í';
	  	case 'O':return 'Ï';
	  	case 'P':return 'Ñ';
	  	case 'T':return 'Ô';
	  	case 'X':return '×';  		
	  	case 'Y':return 'Õ';  		
	  	case 'Z':return 'Æ';  			
	  	default: return c;
	  } 
	
	}

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
	   String result=StringUtils.removeAllButLettersAndDigits(str);
	   result = result.toUpperCase();
	   int l=result.length();
	   StringBuilder strb=new StringBuilder();
	   for (int i = 0; i < l; i++) {
		   char c=result.charAt(i);
		   strb.append(greek(c));
	   }
	   return strb.toString();
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
	 * Writes the specified Greek name with Latin characters.
	 * 
	 * Only capital letters have a visually similar character. 
	 * 
	 * @param greek
	 * 
	 * @return Returns the Greek character that is visually similar
	 *         with the specified character. If the specified character 
	 *         is not a Latin capital letter, or if it has not a visually
	 *         similar Greek character, then returns null.
	 */
	public static String toLatin(String greek) {
		
		
		return null;		
	}


}
