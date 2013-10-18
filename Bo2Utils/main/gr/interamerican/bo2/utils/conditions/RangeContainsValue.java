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
 * Checks {@link Range}s to find if they contain a specific value.
 * 
 * @param <P>
 *        Type of value. 
 */
public class RangeContainsValue<P extends Comparable<? super P>>
implements Condition<Range<P>> {
	
	/**
	 * Value used for the check.
	 */
	P value;
	
	/**
	 * Creates a new RangeContains object. 
	 *
	 * @param value
	 */
	public RangeContainsValue(P value) {
		super();
		this.value = value;
	}

	public boolean check(Range<P> t) {		
		return t.contains(value);
	}

}
