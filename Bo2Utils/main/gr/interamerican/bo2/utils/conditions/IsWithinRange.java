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

import gr.interamerican.bo2.utils.beans.Range;

/**
 * Condition that checks if a comparable object is within a range.
 * 
 * @param <T>
 *        Type of objects being checked by the condition. 
 */
public class IsWithinRange<T extends Comparable<? super T>> 
implements Condition<T> {
	/**
	 * Range.
	 */
	Range<T> range;

	/**
	 * Creates a new IsWithinRangeCondition object. 
	 *
	 * @param range
	 */
	public IsWithinRange(Range<T> range) {
		super();
		this.range = range;
	}	
	
	public boolean check(T t) {		
		return range.contains(t);
	}

}
