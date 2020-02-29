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

import java.util.Collection;
import java.util.Comparator;
import java.util.function.Function;

import gr.interamerican.bo2.utils.CollectionUtils;

/**
 * Comparator based on the values of properties of two objects.
 * 
 * This comparator compares two objects that belong to the same type 
 * by comparing the values of the specified properties on both objects.
 * Comparison starts with the first property and continues while the 
 * values of the same property on both objects are equal. 
 * 
 * The types of the properties used for comparison must be sub-types
 * of Comparable. <br> 
 *  
 * @param <T> Type of objects being compared by this class.
 * 
 * @deprecated Use {@link CollectionUtils#sort(Collection, Function, Function)} or the java api offered by {@link Comparator}
 */
@Deprecated
public class PropertiesBasedComparator<T> 
implements Comparator<T> {
	
	/**
	 * Property expression. This may describe a composite property.
	 */
	private Comparator<T>[] comparators;

	/**
	 * Creates a new PropertyBasedComparator object. 
	 *
	 * @param type the type
	 * @param properties the properties
	 */
	@SuppressWarnings("unchecked")
	public PropertiesBasedComparator(Class<T> type, String[] properties) {
		super();
		comparators = new Comparator[properties.length];
		for (int i = 0; i < properties.length; i++) {			
			comparators[i] = new PropertyBasedComparator<T>(type, properties[i]);
		}
	}

	@Override	
	public int compare(T o1, T o2) {
		for (int i = 0; i < comparators.length; i++) {
			int result = comparators[i].compare(o1, o2);
			if (result!=0) {
				return result;
			}
		}
		return 0;
	}
}