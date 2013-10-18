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
 * Comparator for objects that implement the comparable interface.
 */
public class ComparablesComparator 
implements Comparator<Comparable<?>> {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int compare(Comparable o1, Comparable o2) {
		if (o1==o2) {
			return 0;
		}
		if (o1==null) {
			return -1;
		}
		if (o2==null) {
			return 1;
		}	
		return o1.compareTo(o2);
	}

}
