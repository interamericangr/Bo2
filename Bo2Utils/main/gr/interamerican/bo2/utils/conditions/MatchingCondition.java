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
package gr.interamerican.bo2.utils.conditions;

import gr.interamerican.bo2.utils.beans.Pair;
import gr.interamerican.bo2.utils.matching.MatchingRule;

/**
 * Adapts a {@link MatchingRule} to the {@link Condition} interface.
 * 
 * This condition check will return true if the elements of the specified
 * {@link Pair} match according to the specified matching rule specified
 * in this condition's constructor.
 * 
 * @param <L> 
 *        Type of left element of the {@link Pair}.
 * @param <R>
 *        Type of right element of the {@link Pair}. 
 * 
 */
public class MatchingCondition<L,R> 
implements Condition<Pair<L,R>> {
	
	/**
	 * Creates a new MatchingCondition object. 
	 *
	 * @param matchingRule
	 *        Matching rule for the condition.
	 */
	public MatchingCondition(MatchingRule<L, R> matchingRule) {
		super();
		this.matchingRule = matchingRule;
	}

	/**
	 * Matching rule.
	 */
	MatchingRule<L, R> matchingRule;

	public boolean check(Pair<L, R> t) {		
		return matchingRule.isMatch(t.getLeft(), t.getRight());
	}

}
