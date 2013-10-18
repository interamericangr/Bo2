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
 * Compares two object arrays.
 * 
 * The object compares one by one the objects with the same index on both arrays.
 * The first comparison that does not return equality, provides the result of the
 * array comparison. If all corresponding objects of both arrays are equal, then
 * the array that has more elements, is assigned as greater than the other. If 
 * both arrays have the same size and all their corresponding elements are equal, 
 * then the array comparison returns equality.
 *  
 * The {@link GenericComparator} is used for individual object comparison.
 * 
 */
public class ArrayComparator implements Comparator<Object[]> {
	
	
	/**
	 * generic comparator used to compare the elements of the arrays.
	 */	
	private static final Comparator<Object> COMPARATOR = 
		new GenericComparator();

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */	
	public int compare(Object[] array1, Object[] array2) {
		if (array1==array2) {
			return 0;
		}
		if (array1==null) {
			return -1;
		}
		if (array2==null) {
			return 1;
		}
		int minSize = Math.min(array1.length, array2.length);
		for (int i = 0; i < minSize; i++) {
			int res = COMPARATOR.compare(array1[i], array2[i]);
			if (res!=0) {
				return res;
			}
		}
		return new Integer(array1.length).compareTo(array2.length);
	}

}
