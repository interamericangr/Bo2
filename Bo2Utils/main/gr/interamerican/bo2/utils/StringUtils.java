/*******************************************************************************
 * Copyright (c) 2013 INTERAMERICAN PROPERTY AND CASUALTY INSURANCE COMPANY S.A. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/lesser.html
 * 
 * This library is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 ******************************************************************************/
package gr.interamerican.bo2.utils;

import static gr.interamerican.bo2.utils.StringConstants.EMPTY;
import gr.interamerican.bo2.utils.beans.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Utilities for string operations.
 * 
 * 
 */
public class StringUtils {
	
	/**
	 * Hidden constructor.
	 * 
	 * This is a utility class having only static methods.
	 * There is no need to create any instance of this class.
	 */
	private StringUtils() {/* empty */}
	
	/**
	 * Creates a string with all  arguments separated by comma.
	 * 
	 * @param args arguments to present.
	 * 
	 * @return Returns a string consisting by all arguments separated by
	 *         a comma.
	 */
	public static String showArguments(Object... args) {
		StringBuilder sb = new StringBuilder();
		int i=0;
		for (Object object : args) {			
			if (i!=0) {
				sb.append(", "); //$NON-NLS-1$
			}			
			sb.append(object);
			i++;			
		}
		return sb.toString();
	}

    /**
     * 
     * Creates a left justified string of specified length. 
     * Example leftJustify("test",10,'a'); "test" --> "testaaaaaa" |----10----|
     * If the input string is lengthier than the <code>len</code> argument
     * then the result string is not truncated, but rather is the same as
     * the input string.
     * 
     * @param str input String 
     * @param len length of output String
     * @param c character that fills blanks
     * @return a String of length <code>len</code> that has the input String <code>str</code>
     *         in the left part. If length of <code>str</code> is less than <code>len</code>,
     *         then the remaining characters on the riht are filled with character <code>c</code>   
     */
    public static String leftJustify(String str, int len, char c) {
        int l = str.length();
        if (l == len || l > len)
            return str;
        StringBuffer b = new StringBuffer(len);
        b.append(str);
        for (int i = 0; i < len - l; i++)
            b.append(c);
        return b.toString();
    }

    /**
     * 
     * Creates a right justified string of specified length. 
     * Example rightJustify("test",10,'a'); "test" --> "aaaaaatest" |----10----|
     * 
     * @param str input String 
     * @param len length of output String
     * @param c character that fills blanks
     * @return a String of length <code>len</code> that has the input String <code>str</code>
     *         in the right part. If length of <code>str</code> is less than <code>len</code>,
     *         then the remaining characters on the left are filled with character <code>c</code>   
     */
    public static String rightJustify(String str, int len, char c) {
        int l = str.length();
        if (l == len || l > len)
            return str;
        StringBuffer b = new StringBuffer(len);
        b.append(str);
        for (int i = 0; i < len - l; i++)
            b.insert(0, c);
        return b.toString();
    }
    


    



    /**
     * Converts a string to boolean.
     * 
     *  String values 1 and true will result to true.
     *  Any other value will result to false.
     *  
     * @param s
     * @return returns a boolean value for the input string.
     */

    public static boolean string2Bool(String s) {

        if (s == null)
            return false;

        if (s.trim().equals("1"))  //$NON-NLS-1$
            return true;

        if (s.trim().equalsIgnoreCase("true")) //$NON-NLS-1$
            return true;

        return false;

    }


    /**
     * converts a boolean to 1 or 0
     * 
     * @param b
     * @return returns 1 or 0 
     **/
    public static String bool2String(boolean b) {
    	return b ? "1" : "0"; //$NON-NLS-1$ //$NON-NLS-2$ 
    }
    
    /**
     * Removes leading zeros from a string.
     * 
     * The method does not remove any leading spaces,
     * so if the string starts with spaces, and then has
     * leading zeros, they will not be removed.
     * 
     * @param s
     *        String from which the method will remove any leading zeros.
     *        
     * @return returns the string after it is striped from any leading zeros.
     */
    public static String removeLeadingZeros(String s){    	
        int idx=0;
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)=='0'){
               idx++; 
            } else {
            	break;
            }            	
        }
        return s.substring(idx);
    }

    /**
     * Capitalizes the first character of string.
     * 
     * @param string 
     * @return Returns the string with the first character capital.
     */
    public static String firstCapital(String string) {
		char[] chars=string.toCharArray();
		if (chars.length>0) {
			chars[0]=Character.toUpperCase(chars[0]);			
		}
		return new String(chars);
	}  
    
    /**
     * Makes lower case the first character of string.
     * 
     * @param string 
     * @return Returns the string with the first character lower case.
     */
    public static String firstLowerCase(String string) {
		char[] chars=string.toCharArray();
		if (chars.length>0) {
			chars[0]=Character.toLowerCase(chars[0]);			
		}
		return new String(chars);
	} 


    
    /**
     * Prints an integer to a string, adding leading
     * zeroes so that the string has a specified length.
     * 
     * If the length is less than the actual length of the
     * input integer, then the output will be truncated. 
     * <br/>
     * Examples:
     * <li> for integer 3 and length 4 returns 0003
     * <li> for integer 3005 and length 3 returns 300 
     *     (truncates the digits from the right side).
     * 
     * 
     * @param num integer that will be printed to string 
     * @param length length of output string.
     * 
     * @return Returns a string for the int, that has a fixed
     *         length.
     */
    public static String int2str (int num, int length) {
    	boolean negative = (num<0); 
    	int number = negative ? -num : num;    	
    	String str = Integer.toString(number).trim();
    	StringBuilder sb = new StringBuilder();
    	int zeroes = length - str.length();
    	if (negative) {
    		sb.append(StringConstants.MINUS);
    		zeroes--;
    	}
    	for (int i = 0; i < zeroes; i++) {
			sb.append(StringConstants.ZERO);
		}
    	sb.append(str);
    	return sb.toString().substring(0,length); 
    }
    
    /**
     * Creates a string of specified length consisting by the same character.
     * 
     * @param length length of new string
     * @param c character creating the new string.
     * @return a String of defined length that contains only the character c
     */
    public static String sameCharacterString (int length, char c) {	
    	StringBuffer b = new StringBuffer(length);	
    	for (int i=0;i<length;i++) {
    		b.append(c);
    	}
    	return new String (b);
    }
    
    /**
     * padRight the argument, but truncates it to the specified length if the argument exceeds it
     * @param arg
     * @param length
     * @return padRight of the argument
     */
    public static String fixedLengthPadRight (String arg, int length) {
    	String s= padRight(arg.trim(),length);	
    	int i=s.length()-length;
    	if (i>0) return mid(s,i);
    	else return s;
    }
    
    /**
     * same as substring but throws no IndexOutOfBoundsException
     * works exactly as Visual Basic's mid
     * @param s 
     * @param start
     * @param length
     * @return the substring starting from start and until length
     */
    public static String mid(String s,int start, int length) {
    	int len = s.length();
    	if ((start > len) || (length==0)) {
    		return StringConstants.EMPTY;
    	}
    	int l=length;
    	if (l > len - start) {
    		l = len - start;
    	}
    	return s.substring(start,start+l);
    }

    /**
     * same as substring but throws no IndexOutOfBoundsException
     * works well even if start is greater than the string length
     * works exactly as Visual Basic's mid
     * @param s 
     * @param start 
     * @return the substring starting from start 
     */
    public static String mid(String s,int start) {
    	int len = s.length();
    	if (start > len) {
    		return StringConstants.EMPTY;
    	}
    	int length = len - start;
    	return s.substring(start,start+length);
    }
    
    /**
     * Replaces a part of String with another String
     * Same functionality as Visual Basic mid. Throws no runtime exceptions
     * @param arg String to change
     * @param start Part of the String where the new String will be inserted
     * @param len Length of the part that will be replaced by the new String 
     * @param newPart New String to insert inside the old String
     * @return String
     */
    public static String mid (String arg, int start, int len, String newPart) {
    	if (start>arg.length()) return arg;
    	int l=arg.length()-start+1;
    	if (arg.length()<start+len) {
    		String p1=mid(arg,0,start-1);
    		String newP=mid(newPart,0,l);
    		return p1+newP;
    	} else {
    		String newP=fixedLengthPadLeft(newPart,len);
    		String p1=mid(arg,0,start);
    		String p2=mid(arg,start+len);
    		return p1+newP+p2;
    	}
    }
    
    /**
     * padLeft the argument, but truncates it to the specified length if the argument exceeds it
     * @param arg
     * @param length
     * @return padLeft of the argument
     */
    public static String fixedLengthPadLeft (String arg, int length) {
    	String s=padLeft(arg.trim(),length);	
    	if (s.length()>length) return mid(s,0,length);
    	else return s;
    }
    
    /**
     * 
     * @param arg
     * @param length
     * @return a String of defined length with the argument arg justified left and filled with spaces
     */
    public static String padLeft(String arg, int length) {
    	int l = arg.length();
    	if (l>length) return arg;
    	else return arg+spaces(length-l);
    }

    /**
     * 
     * @param arg
     * @param length
     * @return a String of defined length with the argument arg justified right and filled with spaces
     */
    public static String padRight(String arg, int length) {
    	int l = arg.length();
    	if (l>length) return arg;	
    	else return spaces(length-l)+arg;
    }
    
    /**
     * Space String
     * @param length
     * @return a blank string of defined length
     */
    public static String spaces (int length) {
    	return sameCharacterString(length,' ');	
    }
    
    /**
     * Creates a string with the contents of an array.
     * 
     * The array elements are separated by a delimiter.
     * 
     * @param array
     * @param delimiter
     * 
     * @return Returns a string with the contents of the array.
     */
    public static String array2String(Object[] array, String delimiter) {
    	if (array==null) {
    		return StringConstants.NULL;
    	}
    	StringBuilder sb = new StringBuilder();
		int i=0;
		for (Object object : array) {			
			if (i!=0) {
				sb.append(delimiter);
			}			
			sb.append(toString(object));
			i++;			
		}
		return sb.toString();    	
    }
    
    /**
     * Checks if a String is null or blanc.
     * 
     * @param s String examined.
     * @return Returns true if <code>s</code> is null or blank.
     */
	public static boolean isNullOrBlank(String s) {
	  return (s==null || s.trim().length()==0);
	}
	
	/**
	 * Checks if a string is contained in an array of strings.
	 * Returns the index position if it is contained, else -1.
	 * 
	 * @param array the array of strings
	 * @param string the string
	 * @return the index position if it is contained, else -1.
	 */
	public static int arrayContainsString(String[] array, String string) {
		
		int i=0;
		for (String s : array) {
			if (s.equals(string))
				return i;
			i++;
		}
		return -1;

	}
	
	/**
	 * Removes any empty element from an array.
	 * 
	 * Nulls, empty strings and strings containing only spaces are considered
	 * empty and are omitted from the returned array. The strings that are
	 * contained in the returned array are trimmed. 
	 *  
	 * @param array Array 
	 * @return Returns an array containing no empty elements.
	 */
	public static String[] removeEmpty(String[] array) {
		ArrayList<String> list = new ArrayList<String>();
		for (String string : array) {
			if (string!=null && string.trim().length()!=0) {
				list.add(string.trim());
			}
		}
		return list.toArray(new String[0]);
	}
	

	
	/**
	 * Puts a string in single quotes.
	 *  
	 * @param string
	 * @return returns the string in quotes.
	 */
	public static String quotes(String string) {
		return "'"+string+"'";  //$NON-NLS-1$//$NON-NLS-2$
	}
	
	/**
	 * Puts a string in double quotes.
	 *  
	 * @param string
	 * @return returns the string in double quotes.
	 */
	public static String doubleQuotes(String string) {
		return "\""+string+"\"";  //$NON-NLS-1$//$NON-NLS-2$
	}
	
	/**
	 * Puts a string in parenthesis.
	 *  
	 * @param string
	 * @return returns the string in parenthesis.
	 */
	public static String parenthesis(String string) {
		return "("+string+")";  //$NON-NLS-1$//$NON-NLS-2$
	}
	
	/**
	 * Puts a string in square brackets.
	 *  
	 * @param string
	 * @return returns the string in square brackets.
	 */
	public static String squareBrackets(String string) {
		return "["+string+"]";  //$NON-NLS-1$//$NON-NLS-2$
	}
	
	/**
	 * Puts a string in square brackets, as long as the String
	 * is not null or empty.
	 *  
	 * @param string
	 * @return returns the string in square brackets.
	 */
	public static String squareBracketsWithMandatoryContent(String string) {
		if(StringUtils.isNullOrBlank(string)) {
			return StringConstants.EMPTY;
		}
		return squareBrackets(string);  
	}
	
	/**
	 * Puts a string inside the generic signs (<>).
	 * 
	 * Example generics("abc") = "<abc>".
	 *  
	 * @param string
	 * 
	 * @return returns the string inside the inequity signs.
	 */
	public static String generics(String string) {
		return "<"+string+">";  //$NON-NLS-1$//$NON-NLS-2$
	}
	
	/**
	 * Puts a string in square brackets.
	 *  
	 * @param string
	 * @return returns the string in square brackets.
	 */
	public static String curlyBrackets(String string) {
		return "{"+string+"}";  //$NON-NLS-1$//$NON-NLS-2$
	}
	
	/**
	 * Inserts string B in string A after the first occurrence
	 * of a specific char sequence in string A. If the input is not
	 * valid, string A is returned unchanged.
	 * 
	 * @param original the original string
	 * @param mark a string to specify the mark after which the insertion
	 *        takes place.
	 * @param toAdd the string to insert
	 * 
	 * @return the final string after the insertion
	 */
	public static String insertAfter(String original, String mark, String toAdd) {
		
		if(original.indexOf(mark) == -1){
			return original;
		}
		
		int markIndex = original.indexOf(mark);	
		String temp = original.substring(markIndex+mark.length());
		String result = original.replace(temp, toAdd + temp);		
		return result;
	}
	
	/**
	 * Null safe toString method.
	 * 
	 * @param object Object 
	 * 
	 * @return Returns the value returned by the toString() method
	 *         of object. If the object is null, returns the string
	 *         value "null". 
	 */
	public static String toString(Object object) {
		return toString(object, StringConstants.NULL);
	}
	
	/**
	 * Null safe toString method.
	 * 
	 * @param object 
	 *        Object to print as a String. 
	 * @param defaultString 
	 *        String to return if the specified object is null.
	 * 
	 * @return Returns the value returned by the toString() method
	 *         of object. If the object is null, returns the defaultString. 
	 */
	public static String toString(Object object, String defaultString) {
		if (object==null) {
			return defaultString; 
		}			
		return object.toString();
	}
	
	/**
	 * Returns the value of the specified array, that matches the
	 * specified string value, ignoring case.
	 * 
	 * @param <T>
	 * @param values
	 * @param value
	 * 
	 * @return Returns the matching value.
	 */
	public static <T> T ignoreCaseValueOf(T[] values, String value) {
		if (value==null) {
			return null;
		}		
		String v = value.trim();
		for (T t : values) {
			if (t!=null && t.toString().equalsIgnoreCase(v)) {
				return t;
			}			
		}
		return null;
	}
	
	/**
	 * Null safe trim().
	 * 
	 * @param s String to trim.
	 * @return Returns s.trim() if s is not null, otherwise returns null.
	 */
	public static String trim(String s) {
		if (s==null) {
			return null; 
		}
		return s.trim();
	}
	
	/**
	 * Null safe length().
	 * 
	 * @param s String to trim.
	 * @return Returns s.trim() if s is not null, otherwise returns null.
	 */
	public static int length(String s) {
		if (s==null) {
			return 0; 
		}
		return s.length();
	}
	
	/**
	 * Shows if a string starts with an uppercase character.
	 * 
	 * @param string String.
	 * @return Returns true if string starts with an uppercase.
	 */
	public static boolean startsWithUpperCase(String string) {
		if (string==null || string.length()==0) {
			return false;
		}
		Character initial = string.charAt(0);
		return Character.isUpperCase(initial);
	}
	
	/**
	 * Concatenates strings.
	 * 
	 * @param strings Strings to concatenate.
	 * 
	 * @return Return the concatenation of the strings.
	 */
	public static String concat(String...strings) {
		StringBuilder sb = new StringBuilder();
		for (String string : strings) {
			sb.append(string);
		}
		return sb.toString();
	}
	
	/**
	 * Concatenates putting a separator between them.
	 * .
	 * @param separator 
	 *        separator string.
	 * @param strings 
	 *        Strings to concatenate.
	 * 
	 * @return Return the concatenation of the strings.
	 */
	public static String concatSeparated(String separator, String...strings) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < strings.length; i++) {
			sb.append(strings[i]);
			if (i!=strings.length-1) {
				sb.append(separator);
			}
		} 
		return sb.toString();
	}
	
	/**
	 * Adds a string to the left of an other string, only if 
	 * none of the strings is null.
	 * 
	 * @param string
	 *        String on which a prefix is added.
	 * @param prefix
	 *        prefix to add to the string.
	 * 
	 * @return Returns the string with the additional part on its left
	 *         if both strings are not null, otherwise returns the 
	 *         initial string.
	 */
	public static String addPrefix(String string, String prefix) {
		if (string!=null && prefix!=null) {
			return prefix+string;
		}
		return string;
	}
	
	/**
	 * Adds a string to the right of an other string, only if 
	 * none of the strings is null.
	 * 
	 * @param string
	 *        String on which a postfix is added.
	 * @param postfix
	 *        postfix to add to the string.
	 * 
	 * @return Returns the string with the additional part on its right
	 *         if both strings are not null, otherwise returns the 
	 *         initial string.
	 */
	public static String addPostfix(String string, String postfix) {
		if (string!=null && postfix!=null) {
			return string+postfix;
		}
		return string;
	}
	
	/**
	 * Adds a string to the right of an other string, only if 
	 * none of the strings is null.
	 * 
	 * @param string
	 *        String to surround with the specified string.
	 * @param surround
	 *        String that will surround the initial string.
	 * 
	 * @return Returns the string surrounded by the additional part
	 *         if both strings are not null, otherwise returns the 
	 *         initial string.
	 */
	public static String surround(String string, String surround) {
		if (string!=null && surround!=null) {
			return surround+string+surround;
		}
		return string;
	}
	
	/**
	 * Finds the names of the parameters marked with a prefix in a 
	 * string.
	 * 
	 * This method helps the parsing of strings that have some parameters
	 * inside them. Parameters are words that start with the specified 
	 * character. Colon as an example (:) is used as parameter marker
	 * for sql strings.
	 * 
	 * @param string 
	 *        String parsed.
	 * @param parameterPrefix 
	 *        String put to parameters as prefix.
	 * 
	 * @return Returns a Set with the names of the parameters.
	 */
	public static Set<String> findParameters(String string, String parameterPrefix) {		
		Set<String> params = new HashSet<String>();
		Pair<String, String> pair;
		String remainder = string;
		do {			
			pair = findNextParameter(remainder, parameterPrefix);
			if (pair.getLeft()!=null) {
				params.add(pair.getLeft());
			}
			remainder = pair.getRight();
		} while (pair.getRight()!=null);
		return params ;
	}
	

	/**
	 * Finds the first parameter in a string, that is marked with a prefix.
	 * 
	 * @param string
	 *        String in which the parameter is searched.
	 * @param parameterPrefix
	 *        Prefix that marks the parameter.
	 *        
	 * @return Returns a pair that contains the name of the first parameter
	 *         on the left and the remainder of the string on the right. If 
	 *         the string ends with this parameter, or there is no parameter
	 *         in the string then, then the right element of the pair is null.
	 */
	public static Pair<String, String> findNextParameter(String string, String parameterPrefix) {	
		if (string==null) {
			return new Pair<String, String>(null,null);
		}
		int firstOccurence = string.indexOf(parameterPrefix);
		if (firstOccurence==-1) {
			return new Pair<String, String>(null,null);
		}
		String strAfterPrefix = string.substring(firstOccurence+parameterPrefix.length());
		int endOfParam = strAfterPrefix.indexOf(StringConstants.SPACE);
		if (endOfParam==-1) {
			return new Pair<String, String>(strAfterPrefix.trim(), null);
		}
		String param = strAfterPrefix.substring(0, endOfParam);
		String remainder = strAfterPrefix.substring(endOfParam);
		remainder = remainder.trim();
		if (remainder.length()==0) {
			remainder = null;
		}
		return new Pair<String, String>(param,remainder);
	}
	
	/**
	 * Gets the first character of a string.
	 * 
	 * @param string
	 *        String to get the first character from.
	 *        
	 * @return Returns the first character of the specified string, if it is
	 *         not null or empty. Otherwise returns null.
	 *  
	 */
	public static Character firstChar(String string) {
		if (string==null) {
			return null;
		}
		if (string.length()==0) {
			return null;
		}
		return string.charAt(0);
	}
	
	/**
	 * Adds a space before each occurence of the specified character.
	 * 
	 * @param string
	 *        Parsed string.
	 * @param character
	 *        Character after who's occurences a space is added.
	 *        
	 * @return Returns the string with the added characters.
	 */
	public static String addSpaceBeforeChar(String string, char character) {
		char[] chars = string.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (char c : chars) {
			if (c==character) {
				sb.append(' ');
			}
			sb.append(c);
		}		
		return sb.toString();
	}
	
	/**
	 * Adds a space before each occurence of the specified character.
	 * 
	 * @param string
	 *        Parsed string.
	 * @param character
	 *        Character after who's occurences a space is added.
	 *        
	 * @return Returns the string with the added characters.
	 */
	public static String addSpaceAfterChar(String string, char character) {
		char[] chars = string.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (char c : chars) {
			sb.append(c);
			if (c==character) {
				sb.append(' ');
			}			
		}		
		return sb.toString();
	}
	
	
	/**
	 * Removes any parentheses from string.
	 * 
	 * @param string
	 * 
	 * @return Returns the string having removed its parenthesis.
	 */	
	public static String removeParenthesis(String string) {		
		return removeCharacters(string, '(', ')');
	}
	
	/**
	 * Removes a character from a string.
	 * 
	 * @param string
	 *        String to clear from any appearance of the specified character.
	 * @param character 
	 *        Character to remove.
	 * 
	 * @return Returns the string having removed the character.
	 */
	public static String removeCharacter(String string, char character) {
		StringBuilder sb = new StringBuilder();
		
		for (char c : string.toCharArray()) {
			if (c!=character) {
				sb.append(c);
			}			
		}		
		return sb.toString();
	}
	
	/**
	 * Removes all characters except those that are letters.
	 * 
	 * @param string
	 *        String to clear from any appearance of non letter characters.
	 * 
	 * @return Returns the string having removed the character.
	 */
	public static String removeAllButLetters(String string) {
		String isLetterRegex = "[^\\p{L}]"; //$NON-NLS-1$
		return string.replaceAll(isLetterRegex, EMPTY);
	}
	
	/**
	 * Removes all characters except those that are letters.
	 * 
	 * @param string
	 *        String to clear from any appearance of non letter characters.
	 * 
	 * @return Returns the string having removed the character.
	 */
	public static String removeAllButLettersAndDigits(String string) {
		String isLetterOrDigitRegex = "[^\\p{L}+^\\p{Digit}]"; //$NON-NLS-1$
		return string.replaceAll(isLetterOrDigitRegex, EMPTY);
	}
	
	/**
	 * Removes all characters except those that are letters.
	 * 
	 * @param string
	 *        String to clear from any appearance of non letter characters.
	 * 
	 * @return Returns the string having removed the character.
	 */
	public static String removeAllButDigits(String string) {
		String isDigitRegex = "[^\\p{Digit}]"; //$NON-NLS-1$
		return string.replaceAll(isDigitRegex, EMPTY);
	}
	
	/**
	 * Removes a string from any appearance of specified characters.
	 * 
	 * @param string
	 *        String to clear from any appearance of the specified character.
	 * @param characters 
	 *        Character to remove.
	 * 
	 * @return Returns the string having removed the character.
	 */
	public static String removeCharacters(String string, Character... characters) {
		String s = string;
		for (Character character : characters) {
			s = removeCharacter(s, character);
		}
		return s;
	}
	
	/**
	 * Replaces all multiple spaces char sequences with a single space.
	 * Also trims the input string.
	 * 
	 * @param s input string
	 * 
	 * @return the normalized string.
	 */
	public static String normalizeSpaces(String s) {
		if(s==null){
			return null;
		}
		boolean normalized = false;
		String newLine = StringConstants.NEWLINE;
		String tab = StringConstants.TAB;
		String space = StringConstants.SPACE;
		String doubleSpace = StringConstants.SPACE + StringConstants.SPACE;
		String result = s.trim();
		result = result.replaceAll(tab, space);
		result = result.replaceAll(newLine, space);
		while(!normalized) {
			result = result.replaceAll(doubleSpace, space);
			normalized = !result.contains(doubleSpace);
		}
		return result;
	}
	
	/**
	 * Replaces a specified char sequence (CS) in a given String (GS) with a
	 * given replacement String (RS). The case of the input GS
	 * and the input CS is ignored when matching the CS to the GS.
	 * 
	 * @param s
	 *        Given string.
	 * @param subString
	 *        subString to match on s.
	 * @param replacement
	 *        replacement string 
	 *        
	 * @return s where subString is replaced with replacement.
	 */
	public static String replaceIgnoringCase(String s, String subString, String replacement) {
		String result = s;
		String sUpper = s.toUpperCase();
		String replacementUpper = subString.toUpperCase();
		int beginIndex = sUpper.indexOf(replacementUpper);
		if(beginIndex == -1) {
			return result;
		}
		int endIndex = beginIndex + subString.length();
		result = result.substring(0, beginIndex) + replacement + result.substring(endIndex);
		return result.trim();
	}
	
	/**
	 * Converts a string that has words separated with underscore to a 
	 * camel case string. <br/>
	 * 
	 * Example: invoice_no -> invoiceNo.
	 * 
	 * @param string
	 *        String to convert.
	 *        
	 * @return Returns the converted string.
	 */
	public static String uScore2camelCase(String string) {		
		String s = string.toLowerCase();
		StringBuilder sb = new StringBuilder();
		char[] chars = s.toCharArray();
		boolean toUcase = false;
		for (int i = 0; i < chars.length; i++) {			
			if (chars[i]=='_') {
				toUcase = true;				
			} else {
				if (toUcase) {
					sb.append(Character.toUpperCase(chars[i]));
					toUcase = false;
				} else {
					sb.append(chars[i]);
				}
			}			
		}
		return sb.toString();
	}
	
	
	/**
	 * Cuts a string to the first occurence of the specified period string.
	 * 
	 * @param string
	 *        String to cut.
	 * @param period
	 *        String marking that the string must be cut.
	 *        
	 * @return Returns the cut string.
	 */
	public static String cutTo(String string, String period) {
		int i = string.indexOf(period);
		if (i!=-1) {
			return string.substring(0,i);
		}
		return string;
	}
	
	/**
	 * Gets the xml start tag for a string. <br/>
	 * 
	 * 
	 * @param tag
	 * 
	 * @return Returns the start xml tag for the specified tag.
	 */
	@SuppressWarnings("nls")
	public static String xmlStartTag(String tag) {
		return "<" + tag + ">"; 		
	}
	
	/**
	 * Gets the xml start tag for a string. <br/>
	 * 
	 * 
	 * @param tag
	 * 
	 * @return Returns the start xml tag for the specified tag.
	 */
	@SuppressWarnings("nls")
	public static String xmlEndTag(String tag) {
		return "</" + tag + ">"; 		
	}
	
	/**
	 * Checks if the specified string starts with any of the prefixes in the
	 * specified set.
	 * 
	 * @param string
	 *        String to check.
	 * @param prefixes
	 *        Set with prefixes
	 *      
	 * @return Returns true if the specified string starts with any of the 
	 *         strings in the prefixes set. Otherwise returns false.
	 */
	public static boolean startsWith(String string, Set<String> prefixes) {
		for (String prefix : prefixes) {
			if (string.startsWith(prefix)) {
				return true;
			}			
		}
		return false;		
	}
	
	
	/** 
	 * Removes the part of a String that is contained between two
	 * elements.
	 * 
	 * @param string 
	 *        String from which a part will be removed.
	 * @param startElement 
	 *        String that marks the start of the part that has to be removed.
	 * @param endElement 
	 *        String that marks the end of the part that has to be removed.
	 *       
	 * @return the string without the part that is contained between the start and
	 *         elements.
	 */
	public static String removePartBetweenElements
	(String string, String startElement, String endElement) {
		String result = string;
		int start = string.indexOf(startElement);		
		int end = string.indexOf(endElement, start);
		if(start!=-1 && end!=-1) {
			String tag = string.substring(start, end + endElement.length());
			result = string.replace(tag, StringConstants.EMPTY);
		}
		return result;
	}
	
	
	/**
	 * Checks if the specified string contains only characters that
	 * represent Letters of any language, Numbers and spaces.
	 * 
	 * @param s
	 * 
	 * @return True, if s contains only characters that represent 
	 *         Greek and Latin letters and numbers.
	 */
	@SuppressWarnings("nls")
	public static boolean containsOnlyLettersNumbersSpaces(String s) {
		return s.matches("[ *\\p{L}*\\p{Digit}*]+");
	}
	
	/**
	 * Truncates n characters from the end of a String
	 * 
	 * @param s
	 *        String
	 * @param n
	 *        Number of characters to truncate.
	 *        
	 * @return Returns the truncated String.
	 */
	public static String truncateCharsFromEnd(String s, int n) {
		if(s==null) {
			return null;
		}
		if(s.length()<n) {
			return StringConstants.EMPTY;
		}
		return s.substring(0, s.length()-n);
	}
	
	/**
	 * Strips the package from a fully qualified class name.
	 * @param fqcn
	 * @return simple class name
	 */
	public static String stripPkgFromFqcn(String fqcn) {
		if(fqcn.indexOf(StringConstants.DOT) == -1)
			return fqcn;
		else
			return fqcn.substring(fqcn.lastIndexOf(StringConstants.DOT)+1, fqcn.length());
	}
	
	/**
	 * Truncates the specified string if necessary to the specified
	 * maximum length.
	 * 
	 * @param string
	 * @param length
	 * 
	 * @return If the string is null, returns an empty string. 
	 *         If the string has a length that excceeds the specified maximum
	 *         length, then it is truncated to the specified maximum length.
	 */
	public static String truncateToLength(String string, int length) {
		if (string==null) {
			return StringConstants.EMPTY;
		}
		if (string.length()>length) {
			return string.substring(0,length);
		}
		return string;
	}
	
	/**
	 * Splits a string into strings by length.
	 * 
	 * @param string
	 * @param length
	 * 
	 * @return If the string is null, returns an empty array
	 *         If the string is not null, returns an array of strings splitted by the length
	 *         
	 */         
	@SuppressWarnings("nls")
	public static String[] splitByLength(String string,int length){
		if (string==null) {
			return new String[0];
		}
		
		if(string.length() > 0){
			return string.split("(?<=\\G.{"+length+"})");
		}
		return new String[0];
	}
	
	/**
	 * This utility is meant to help identify the unicode codepoint
	 * of all chars in a supplied String
	 * @param s
	 */
	public static void mapStringCharsToUnicodeCodepoints(String s) {
		if(s==null) {
			return;
		}
		for (char c : s.toCharArray()) {
			System.out.println(String.valueOf(c) + StringConstants.TAB + Integer.toHexString(c));
		}
	}
	
}
