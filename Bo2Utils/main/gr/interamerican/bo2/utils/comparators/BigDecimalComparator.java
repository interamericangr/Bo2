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
 * Compares the BigDecimal values of two numbers.
 */
class BigDecimalComparator 
implements Comparator<Number> {
	
	/**
	 * Converts a number to BigDecimal.
	 * 
	 * @param n number to convert.
	 * @return Returns the BigDecimal value of n
	 */
	private BigDecimal toBigDecimal(Number n) {
		if (BigDecimal.class.isInstance(n)) {
			return (BigDecimal) n;
		}
		if (BigInteger.class.isInstance(n)) {
			return new BigDecimal((BigInteger)n);
		}
		if (Long.class.isInstance(n)) {
			return new BigDecimal((Long)n);
		}
		if (Integer.class.isInstance(n)) {
			return new BigDecimal((Integer)n);
		}
		if (Short.class.isInstance(n)) {
			return new BigDecimal((Short)n);
		}
		if (Byte.class.isInstance(n)) {
			return new BigDecimal((Byte)n);
		}
		return new BigDecimal(n.doubleValue());
	}
	
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Number o1, Number o2) {
		BigDecimal b1 = toBigDecimal(o1);
		BigDecimal b2 = toBigDecimal(o2);
		return b1.compareTo(b2);
	}

}
