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
 * Compares the Double values of two numbers.
 */
class DoubleComparator 
implements Comparator<Number> {
	
	/**
	 * Converts a number to Double.
	 * 
	 * @param n number to convert.
	 * @return Returns the Double value of n
	 */
	private Double toDouble(Number n) {
		return n.doubleValue();
	}
	
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */	
	public int compare(Number o1, Number o2) {
		Double b1 = toDouble(o1);
		Double b2 = toDouble(o2);
		return b1.compareTo(b2);
	}

}
