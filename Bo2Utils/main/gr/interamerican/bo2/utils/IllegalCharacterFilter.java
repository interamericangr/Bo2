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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides a defense mechanism to disallow the insertion of inappropriate characters
 * to database tables.
 * <br/>
 * This may happen, for instance, when users copy-paste formatted text from word documents
 * to text areas.
 */
public class IllegalCharacterFilter {
	
	/**
	 * Logger.
	 */
	static final Logger logger = LoggerFactory.getLogger(IllegalCharacterFilter.class.getName());
	
	/**
	 * Singleton instance.
	 */
	public static final IllegalCharacterFilter SINGLETON = new IllegalCharacterFilter();
	
	/**
	 * Known associations for undesired characters.
	 */
	private static final Map<Character, String> filterByReplacing = new HashMap<Character, String>();
	static {
		// add associations here
	}
	
	/**
	 * Undesired characters that should simply be omitted.
	 */
	private static final List<Character> filterByOmitting = new ArrayList<Character>();
	static {
		filterByOmitting.add('\u00a0'); //Non-breaking space
		filterByOmitting.add('\u0085'); //Next Line
		filterByOmitting.add('\u009f'); //Application Program Command
		filterByOmitting.add('\u001a'); //SUB
	}
	
	/**
	 * Most common punctuation chars. Copied from {@link Pattern} javadoc.
	 */
	private char[] commonPunctuationChars = "!\"#$%&\'()*+,-./:;<=>?@[\\]^_`{|}~".toCharArray(); //$NON-NLS-1$
	
	/**
	 * Character types for unicode punctuation blocks
	 */
	private static final List<Byte> punctuationBlocks = new ArrayList<Byte>();
	static {
		punctuationBlocks.add(Character.DASH_PUNCTUATION);
		punctuationBlocks.add(Character.START_PUNCTUATION);
		punctuationBlocks.add(Character.END_PUNCTUATION);
		punctuationBlocks.add(Character.CONNECTOR_PUNCTUATION);
		punctuationBlocks.add(Character.OTHER_PUNCTUATION);
		punctuationBlocks.add(Character.INITIAL_QUOTE_PUNCTUATION);
		punctuationBlocks.add(Character.FINAL_QUOTE_PUNCTUATION);
	}
	
	/**
	 * Filters the string argument from unknown characters replacing them if possible.
	 * @param input
	 * @return Filtered string.
	 */
	public String filter(String input) {
		if(input==null) {
			return null;
		}
		List<Character> chars = new ArrayList<Character>();
		for (char ch : input.toCharArray()) {
			if(explicitHandling(ch)) {
				String candidate = filterByReplacing.get(ch);
				if(candidate!=null) {
					char[] substitutes = candidate.toCharArray();
					for(char substitute : substitutes) {
						chars.add(substitute);
					}
				} else if(filterByOmitting.contains(ch)) {
					//ok, just omit the char.
				} 
			} else if(shouldFilter(ch)) {
				logger.warn("Escaped char with no explicit rule defined " + print(ch)); //$NON-NLS-1$
			} else {
				chars.add(ch);
			}
		}
		
		char[] primitives = new char[chars.size()];
		int ctr=0;
		for(Character c : chars) {
			primitives[ctr] = c;
			ctr++;
		}
		String output = String.valueOf(primitives);
		return output;
	}
	
	/**
	 * Extension point. Allows client applications to define new associations
	 * or override default ones. If an association for a Character that is normally
	 * omitted is made, the omission rule is canceled.
	 * 
	 * @param filtered
	 * @param replacement
	 */
	public static void registerAssociation(Character filtered, String replacement) {
		filterByOmitting.remove(filtered);
		filterByReplacing.put(filtered, replacement);
	}
	
	/**
	 * @param c
	 * @return Returns true, if the character should be filtered.
	 */
	private boolean shouldFilter(char c) {
		if(Character.isLetterOrDigit(c)) {
			return false;
		}
		if(Character.isWhitespace(c)) {
			return false;
		}
		if(Character.getType(c) == Character.CURRENCY_SYMBOL) {
			return false;
		}
		if(isPunctuation(c)) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * @param c
	 * @return True, if the character is explicitly omitted or replaced.
	 */
	private boolean explicitHandling(char c) {
		return filterByOmitting.contains(c) || filterByReplacing.containsKey(c);
	}
	
	/**
	 * @param c
	 * @return Returns true, if the character argument is used for punctuation. 
	 */
	private boolean isPunctuation(char c) {
		for(char pc : commonPunctuationChars) {
			if(pc==c) {
				return true;
			}
		}
		if(punctuationBlocks.contains((byte) Character.getType(c))) {
			return true;
		}
		return false;
	}
	
	/**
	 * @param c
	 * @return Character details for debugging.
	 */
	private String print(char c) {
		return "\\u" + Integer.toHexString(c) + " of type " + Character.getType(c); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	/**
	 * Hidden. 
	 */
	private IllegalCharacterFilter() {/*empty*/}

}
