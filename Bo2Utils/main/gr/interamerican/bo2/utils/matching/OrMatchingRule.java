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

import java.util.List;


/**
 * Matching rule that checks an array of matching rules
 * until it finds one match.
 * 
 * If either matching rules of the array match, then the match is true. 
 * 
 * @param <F> Type of first object.
 * @param <S> Type of second object.
 */
public class OrMatchingRule<F,S> implements MatchingRule<F, S> {
	/**
	 * Array of rules to check.
	 */
	List<MatchingRule<F, S>> rules;

	/**
	 * Creates a new AndMatchingRule object. 
	 *
	 * @param rules
	 */
	public OrMatchingRule(List<MatchingRule<F, S>> rules) {
		super();
		this.rules = rules;
	}

	public boolean isMatch(F first, S second) {
		for (MatchingRule<F, S> rule : rules) {
			if (rule.isMatch(first, second)) {
				return true;
			}
		}
		return false;
	}
	

}
