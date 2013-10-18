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
 * Comparator for numbers.
 */
public class NumberComparator 
implements Comparator<Number> {
	
	/**
	 * default comparison type used for numbers.
	 */
	private static final int DEFAULT_TYPE=6;
	
	/**
	 * Sub-types of Number for which an appropriate comparator exists.
	 */
	private static final Class<?>[] TYPES = {
		Byte.class,
		Short.class,
		Integer.class,
		Long.class,
		BigInteger.class,
		Float.class,
		Double.class,
		BigDecimal.class
	};
	
	/**
	 * specific comparators.
	 */
	private static final Comparator<?>[] COMPARATORS = {
		new ByteComparator(),
		new ShortComparator(),
		new IntegerComparator(),
		new LongComparator(),
		new BigIntegerComparator(),
		new FloatComparator(),
		new DoubleComparator(),
		new BigDecimalComparator()		
	};
	
	/**
	 * Finds the index of the Number sub-type
	 * @param n Number
	 * 
	 * @return Returns the index of the sub-type of number in 
	 *         the <code>types</code> array.
	 */
	int type(Number n) {
		for (int i = 0; i < TYPES.length; i++) {
			if (TYPES[i].isInstance(n)) {
				return i;
			}
		}
		return DEFAULT_TYPE;
	}
	
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */	
	public int compare(Number n1, Number n2) {
		if (n1==n2) {
			return 0;
		}
		if (n1==null) {
			return -1;
		}
		if (n2==null) {
			return 1;
		}		
		int maxType = Math.max(type(n1), type(n2));
		@SuppressWarnings("unchecked")
		Comparator<Number> comparator= (Comparator<Number>) COMPARATORS[maxType];
		return comparator.compare(n1, n2);		
	}

}
