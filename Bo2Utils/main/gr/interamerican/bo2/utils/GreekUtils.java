package gr.interamerican.bo2.utils;

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
		   if (c=='-'||c=='_'||c==' ' ) {
			   continue;    		   
		   } else if (!Character.isDigit(c)) {
			   strb.append(greek(c));
		   } else {
			   strb.append(result.charAt(i));
		   }
	   }
	   return strb.toString();
	}

}
