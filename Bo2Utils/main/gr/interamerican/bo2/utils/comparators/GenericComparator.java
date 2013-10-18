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

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

/**
 * Generic comparator. 
 * 
 * Finds the types of the object it compares and selects the
 * appropriate comparison strategy. The supported types that are
 * handled accordingly are:
 * <li> {@link String} </li>
 * <li> {@link Date} </li>
 * <li> {@link Calendar} </li>
 * <li> {@link Number} </li>
 * Other types are handled as Strings using their <code>toString()</code>
 * method.
 * 
 */
public class GenericComparator 
implements Comparator<Object>{
	
	/**
	 * Comparator used for String
	 */	
	private static final Comparator<Object> COMPARE_STRINGS = 
		new StringComparator();
	
	/**
	 * Comparator used for Number
	 */
	private static final Comparator<Object> COMPARE_NUMBERS = 
		new GenericNumberComparator();
	
	/**
	 * Comparator used for Number
	 */
	private static final Comparator<Object> COMPARE_DATES = 
		new GenericDateComparator();
	
	
	
	/**
	 * Finds the appropriate comparator to use for the comparison
	 * of two objects.
	 * 
	 * @param o1 First object to compare
	 * @param o2 Second object to compare
	 * 
	 * @return Returns the appropriate comparator.
	 */
	private Comparator<Object> findComparator(Object o1, Object o2) {
		if (Number.class.isInstance(o1) && Number.class.isInstance(o2)) {
			return COMPARE_NUMBERS; 
		}
		if ((Date.class.isInstance(o1) || Calendar.class.isInstance(o1)) 
		&&	(Date.class.isInstance(o2) || Calendar.class.isInstance(o2))) {
			return COMPARE_DATES;
		}		
		return COMPARE_STRINGS;
	}
	
		
	public int compare(Object o1, Object o2) {
		if (o1==o2) {
			return 0; //���� ������ ��� ���� ��������� ��� ��� �� ��� ����� null
		}
		if (o1==null) {
			return -1;
		}
		if (o2==null) {
			return 1;
		}
		if (o1.equals(o2)) {
			return 0;
		}
		Comparator<Object> comparator = findComparator(o1, o2);
		return comparator.compare(o1, o2);
	}

}
