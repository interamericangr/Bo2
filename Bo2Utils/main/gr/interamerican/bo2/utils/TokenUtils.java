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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Utilities for splitting strings into tokens.
 */
public class TokenUtils {

	/**
	 * Hidden private constructor of a utility class. 
	 *
	 */
	private TokenUtils() {
		/* empty */
	}
	
	/**
	 * Splits the given string to an array of strings given the specified
	 * separator character. 
	 * 
	 * @param input
	 *        The input string to split.
	 * @param separator
	 *        String delimiter.
	 *        
	 * @return An array of all tokens produced.
	 */
	public static String[] split(String input, char separator) {
		return split(input, String.valueOf(separator));
	}
	
	/**
	 * Splits the given string to an array of strings given the specified
	 * separator character. 
	 * 
	 * @param input
	 *        The input string to split.
	 * @param separator
	 *        String delimiter.
	 * @param includeEmptyTokens
	 *        Indicates if empty tokens are included in the result. 
	 *        
	 * @return An array of all tokens produced.
	 */
	public static String[] split(String input, char separator, boolean includeEmptyTokens) {
		return split(input, String.valueOf(separator), includeEmptyTokens);
	}
	
	/**
	 * Splits the given string to an array of strings given the specified
	 * separator characters. Empty tokens can be included optionally. 
	 * 
	 * @param input
	 *        The input string to split.
	 * @param separators
	 *        Each character in this string is treated as a delimiter.
	 *        
	 * @return An array of all tokens produced.
	 */
	public static String[] split(String input, String separators) {
		return split(input, separators, true);
	}
	
	/**
	 * Splits the given string to an array of strings given the specified
	 * separator characters. Empty tokens can be included optionally. 
	 * 
	 * @param input
	 *        The input string to split.
	 * @param separators
	 *        Each character in this string is treated as a delimiter.
	 * @param includeEmptyTokens
	 *        Empty tokens are included in the result.
	 *        
	 * @return An array of all tokens produced.
	 */
	public static String[] split(String input, String separators, boolean includeEmptyTokens) {
		String s = input.trim();
        int l = s.length();
        if (l == 0) {
        	return (new String[0]);
        }
        ArrayList<String> tokens = new ArrayList<String>();
        if(!includeEmptyTokens) {
        	StringTokenizer st = new StringTokenizer(s, separators, false);
            while (st.hasMoreTokens()) {
                String token = st.nextToken();
                if(token.length()>0) {
                	tokens.add(token);
                }
            }
        } else {
        	StringTokenizer st = new StringTokenizer(s, separators, true);
        	boolean prevTokenWasSeparator = false;
        	boolean first = true;
        	while (st.hasMoreTokens()) {
        		String token = st.nextToken();
        		/*
        		 * "" is contained in any string
        		 */
        		if(!"".equals(token) && separators.contains(token)) { //$NON-NLS-1$
        			if(prevTokenWasSeparator || first) {
        				tokens.add(StringConstants.EMPTY);
        			}
        			prevTokenWasSeparator = true;
        		} else {
        			tokens.add(token);
        			prevTokenWasSeparator = false;
        		}
        		first = false;
        	}
        	for(char c : separators.toCharArray()) {
        		if(s.trim().endsWith(String.valueOf(c))) {
        			/*
        			 * this should return add a token with the trimmed spaces,
        			 * but an empty String will have to do
        			 */
        			tokens.add(StringConstants.EMPTY);
        		}
        	}
        }
        tokens.trimToSize();        
        return tokens.toArray(new String[tokens.size()]);
	}
	
	/**
	 * Splits the given string to an array of trimmed strings given the specified
	 * delimiter. 
	 * 
	 * @param input
	 *        The input string to split.
	 * @param delimiter
	 *        Input delimiter.
	 *        
	 * @return An array of all tokens produced.
	 */
    public static String[] splitTrim(String input, char delimiter) {
    	return splitTrim(input, String.valueOf(delimiter), true);
    }
    
    /**
	 * Splits the given string to an array of trimmed strings given the specified
	 * separator characters. 
	 * 
	 * @param input
	 *        The input string to split.
	 * @param separators
	 *        Each character in this string is treated as a delimiter.
	 *        
	 * @return An array of all tokens produced.
	 */
    public static String[] splitTrim(String input, String separators) {
    	return splitTrim(input, separators, true);
    }
	
    /**
     * Splits the given string to an array of trimmed strings given the specified
	 * delimiter. Empty tokens (after trimming) can be included optionally. 
     * 
     * @param input
     *        The input string to split.
     * @param delimiter
     *        Input delimiter.
     * @param includeEmptyTokens
     *        Empty tokens (after trimming) are included in the result.
     *        
     * @return An array of all tokens produced.
     */
    public static String[] splitTrim(String input, char delimiter, boolean includeEmptyTokens) {
    	return splitTrim(input, String.valueOf(delimiter), includeEmptyTokens);
    }
    
    /**
     * Splits the given string to an array of trimmed strings given the specified
	 * separator characters. Empty tokens (after trimming) can be included optionally. 
     * 
     * @param input
     *        The input string to split.
     * @param separators
     *        Each character in this string is treated as a delimiter.
     * @param includeEmptyTokens
     *        Empty tokens (after trimming) are included in the result.
     *        
     * @return An array of all tokens produced.
     */
    public static String[] splitTrim(String input, String separators, boolean includeEmptyTokens) {    	
        return trimTokens(split(input, separators, includeEmptyTokens), includeEmptyTokens);
    }
    
    /**
     * Trims the tokens.
     * 
     * @param input
     * @param includeEmptyTokens 
     * @return the input after performing the trimming.
     */
    private static String[] trimTokens(String[] input, boolean includeEmptyTokens) {
    	List<String> tokens = new ArrayList<String>();
    	for(int i=0; i<input.length; i++) {
    		if(includeEmptyTokens || input[i].trim().length()>0) {
    			tokens.add(input[i].trim());
    		}
    	}
    	return tokens.toArray(new String[]{});
    }
    
    /**
     * Splits a string to lines.
     * 
     * @param string
     *        String to split.
     * @param excludeEmptyLines
     *        If set to true, empty lines will be excluded from the result.
     *          
     * @return Returns an array that contains the lines of the string.
     */
    public static String[] toLines(String string, boolean excludeEmptyLines) {
    	ByteArrayInputStream stream = new ByteArrayInputStream(string.getBytes());
    	InputStreamReader in = new InputStreamReader(stream);
    	BufferedReader reader = new BufferedReader(in);
    	try {
			return StreamUtils.consumeBufferedReader(reader, excludeEmptyLines, false);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
    }
    
    /**
	 * Returns the value of an annotation as an array of Strings.
	 * 
	 * If the annotation value is an empty array, or has more than one elements,
	 * then the same array is returned. If the array has only one empty element,
	 * then an empty array is returned. If the array contains only one string
	 * that is contained by other strings separated by commas, then this string
	 * is split to its constituent tokens it and an array with the tokens is 
	 * returned.
	 * 
	 * @param annovalue
	 * 
	 * @return Returns an array of strings.
	 */
	public static String[] tokenize(String[] annovalue) {		
		if (annovalue.length!=1) {
			return annovalue;
		}
		String string = annovalue[0];
		if (StringUtils.isNullOrBlank(string)) {
			return new String[0];
		}
		return TokenUtils.splitTrim(annovalue[0], ',');
	}
	
	
    
    
}
