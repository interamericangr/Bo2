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

import gr.interamerican.bo2.utils.DateUtils;

import java.text.ParseException;
import java.util.Comparator;
import java.util.Date;

/**
 * Compares two objects that represent a Date.
 * 
 * 
 */
public class GenericDateComparator 
implements Comparator<Object> {
	
	/**
	 * Comparator.
	 */
	private static Comparator<Date> comparator = new DateComparator();
	
	/**
	 * Converts an object to date.
	 * 
	 * @param o
	 * @return Returns the object that is represented by the date.
	 *         if the object does not represent a date, returns null.
	 */
	private Date convert (Object o) {
		try {
			return DateUtils.getDate(o);
		} catch (ParseException e) {
			return null;
		}
		
	}	
	
	public int compare(Object o1, Object o2) {	
		Date dt1 = convert(o1);
		Date dt2 = convert(o2);
		return comparator.compare(dt1, dt2);
	}
	

}
