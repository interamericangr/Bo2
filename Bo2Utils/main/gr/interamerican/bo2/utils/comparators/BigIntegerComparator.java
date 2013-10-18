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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Comparator;

/**
 * Compares the BigInteger values of two numbers.
 */
class BigIntegerComparator 
implements Comparator<Number> {
	
	/**
	 * Converts a number to BigInteger.
	 * 
	 * @param n number to convert.
	 * @return Returns the BigInteger value of n
	 */
	private BigInteger toBigInteger(Number n) {
		if (BigInteger.class.isInstance(n)) {
			return (BigInteger) n;
		}
		if (BigDecimal.class.isInstance(n)) {
			return ((BigDecimal)n).toBigInteger();
		}
		BigDecimal bd = new BigDecimal(n.longValue());
		return bd.toBigInteger();
	}
	
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */	
	public int compare(Number o1, Number o2) {
		BigInteger b1 = toBigInteger(o1);
		BigInteger b2 = toBigInteger(o2);
		return b1.compareTo(b2);
	}
	
	

}
