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
import java.util.Date;

/**
 * Compares two Dates.
 * 
 * 
 */
public class DateComparator 
implements Comparator<Date> {
	
	
	
	public int compare(Date o1, Date o2) {	
		if (o1==null) {
			return o2==null ? 0 : -1;
		}
		if (o2 == null) {
			return 1;
		}
		return o1.compareTo(o2);
	}
	

}
