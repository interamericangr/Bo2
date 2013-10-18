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
 * This comparator is an wrapper around another comparator,
 * inverting its comparison result.
 * 
 * The real comparator is passed as an argument to the constructor
 * of this comparator. This comparator will use the specified real
 * comparator for comparison, but this comparator will always return
 * the opposite result that the initial comparator. 
 * 
 * @param <T> Type of objects compared by this comparator. 
 */
public class InvertedComparator<T> implements Comparator<T> {
	
	/**
	 * Comparator.
	 */
	private Comparator<T> comparator;

	/**
	 * Creates a new InvertedComparator object. 
	 *
	 * @param comparator
	 */
	public InvertedComparator(Comparator<T> comparator) {
		super();
		this.comparator = comparator;
	}

	public int compare(T o1, T o2) {		
		return (-1) * comparator.compare(o1, o2);
	}

}
