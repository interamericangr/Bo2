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


import gr.interamerican.bo2.utils.NumberUtils;

import java.util.Comparator;

/**
 * Compares two objects that represent a Date.
 * 
 * 
 */
public class GenericNumberComparator 
implements Comparator<Object> {
	
	/**
	 * Comparator used for numbers.
	 */
	private static final NumberComparator NUMBER_COMPARATOR =
		new NumberComparator();
	
	/**
	 * Converts an object to date.
	 * 
	 * @param o
	 * @return Returns the object that is represented by the date.
	 *         if the object does not represent a date, returns null.
	 */
	private Number convert (Object o) {
		if (Number.class.isInstance(o)) {
			return (Number) o;
		}
		return NumberUtils.string2Double(o.toString());
	}
	
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */	
	public int compare(Object o1, Object o2) {		
		Number n1 = convert(o1);
		Number n2 = convert(o2);
		return NUMBER_COMPARATOR.compare(n1, n2);
	}
	

}
