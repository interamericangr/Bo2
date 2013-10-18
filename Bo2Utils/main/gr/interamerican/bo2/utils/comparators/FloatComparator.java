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
package gr.interamerican.bo2.utils.comparators;

import java.util.Comparator;

/**
 * Compares the Float values of two numbers.
 */
class FloatComparator 
implements Comparator<Number> {
	
	/**
	 * Converts a number to Float.
	 * 
	 * @param n number to convert.
	 * @return Returns the Float value of n
	 */
	private Float toFloat(Number n) {
		return n.floatValue();
	}
	
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */	
	public int compare(Number o1, Number o2) {
		Float b1 = toFloat(o1);
		Float b2 = toFloat(o2);
		return b1.compareTo(b2);
	}

}
