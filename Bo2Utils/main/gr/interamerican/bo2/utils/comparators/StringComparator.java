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
 * Compares two objects as strings.
 * 
 * 
 */
public class StringComparator 
implements Comparator<Object> {	
	
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */	
	public int compare(Object o1, Object o2) {
		if (o1==o2) {
			return 0;
		}
		if (o1==null) {
			return -1;
		}
		if (o2==null) {
			return 1;
		}			
		return o1.toString().compareTo(o2.toString());
	}

}
