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

/**
 * Matching rule.
 * 
 * Defines a rule that checks if two objects match with each other.
 * 
 * @param <F>
 *        Type of first object.
 * @param <S>
 *        Type of second object.
 */
public interface MatchingRule<F,S> {
	
	/**
	 * Checks if the two arguments of the method match with each other.
	 * 
	 * @param first
	 *        First object. 
	 * @param second
	 *        Second object.
	 * 
	 * @return Returns true if the first and the second object match
	 *         according to this {@link MatchingRule}.
	 */
	public boolean isMatch(F first, S second);

}
