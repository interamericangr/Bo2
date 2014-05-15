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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utilities for use of regular expressions.
 */
public class RegexUtils {
	
	/**
	 * Regex for zero or more chars.
	 */
	public static final String ZERO_OR_MORE_CHARS = ".*"; //$NON-NLS-1$
	
	/**
	 * Returns all matches of a pattern in a given string.
	 * 
	 * @param value
	 * @param pattern
	 * 
	 * @return List of matches.
	 */
	public static List<String> getMatches(String value, String pattern) {
		Pattern p = Pattern.compile(pattern);
		return getMatches(value, p);
	}
	
	/**
	 * Returns all matches of a {@link Pattern} in a given string.
	 * 
	 * @param value
	 * @param p
	 * 
	 * @return List of matches.
	 */
	public static List<String> getMatches(String value, Pattern p) {
		List<String> matches = new ArrayList<String>();
		Matcher matcher = p.matcher(value);
		while(matcher.find()) {
			matches.add(matcher.group(0));
		}
		return matches;
	}

}
