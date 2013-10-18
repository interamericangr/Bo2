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
 * Comparator for any sub type of Number.
 * 
 * @param <T>
 *        Type of numbers being compared by this comparator. 
 */
public class SpecificNumberComparator<T extends Number> 
implements Comparator<T> {
	/**
	 * Comparator doing the job.
	 */
	NumberComparator nc = new NumberComparator();
	
	public int compare(T o1, T o2) {		
		return nc.compare(o1, o2);
	}

}
