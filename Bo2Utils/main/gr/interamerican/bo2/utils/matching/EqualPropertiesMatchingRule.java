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

import java.util.ArrayList;
import java.util.List;

/**
 * Matching rule that checks the values of the properties that
 * are specified in an array.
 * 
 * @param <F> Type of first object.
 * @param <S> Type of second object.
 */
public class EqualPropertiesMatchingRule<F,S> implements MatchingRule<F, S> {
	
	/**
	 * Keeps one rule for each property.
	 */
	AndMatchingRule<F, S> match;
	

	/**
	 * Creates a new EqualPropertiesMatchingRule object. 
	 *
	 * @param firstClass
	 *        
	 * @param secondClass
	 * 
	 * @param properties
	 */
	public EqualPropertiesMatchingRule
	(Class<F> firstClass, Class<S> secondClass, String[] properties) {
		/*
		 * TODO: Make this more efficient.
		 */
		super();
		List<MatchingRule<F, S>> rules = new ArrayList<MatchingRule<F,S>>();
		for (String property : properties) {
			MatchingRule<F, S> rule = 
				new EqualPropertyMatchingRule<F, S>(firstClass, secondClass, property, property);
			rules.add(rule);
		}
		match = new AndMatchingRule<F, S>(rules);
	}

	public boolean isMatch(F first, S second) {		
		return match.isMatch(first, second);
	}

}
