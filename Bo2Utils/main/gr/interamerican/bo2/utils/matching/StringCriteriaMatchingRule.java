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
package gr.interamerican.bo2.utils.matching;

import gr.interamerican.bo2.utils.StringUtils;

/**
 * This MatchingRule matches two strings, where the first string
 * is given as a criterion and the second string is the value being 
 * matched. <br/>
 * 
 * If the first string is null, then it implied that there was no
 * criterion given, so anything would match. If the first string
 * is not null, then the rule will match only if the second rule
 * contains the criterion (first).
 */
public class StringCriteriaMatchingRule 
implements MatchingRule<String, String>{
	
	public boolean isMatch(String first, String second) {
		if (StringUtils.isNullOrBlank(first)) {
			return true;
		}
		if (second==null) {
			return false;
		}		
		return second.contains(first.trim());
	}

}
