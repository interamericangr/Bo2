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
package gr.interamerican.bo2.utils;

import gr.interamerican.bo2.utils.conditions.Condition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Utilities for condition checks.
 */
public class ConditionUtils {
	
	/**
	 * Private empty constructor of a utility class. 
	 *
	 */
	private ConditionUtils() { /* empty */ }

	/**
	 * Puts all elements of a collection that fill a specified condition to
	 * another collection.
	 * 
	 * @param superSet
	 *            Collection with all elements being checked.
	 * @param subSet
	 *            Collection where the elements are put that fill the condition.
	 * @param condition
	 *            Condition.
	 * @param <S>
	 *            Type of element that the condition validates
	 * @param <T>
	 *            Type of elements in the collections (must extend <S>)
	 */
	public static <S, T extends S> void fillSubset(Collection<T> superSet, Collection<T> subSet, Condition<S> condition) {
		for (T t : superSet) {
			if (condition.check(t)) {
				subSet.add(t);
			}
		}
	}

	/**
	 * Gets a list with all elements of a collection that fill a specified
	 * condition.
	 * 
	 * @param superSet
	 *            Collection with all elements being checked.
	 * @param condition
	 *            Condition.
	 * @param <S>
	 *            Type of element that the condition validates
	 * @param <T>
	 *            Type of elements in the collections (must extend <S>)
	 * 
	 * @return Returns a list with the elements that fulfill the condition.
	 */
	public static <S, T extends S> List<T> getSubset(Collection<T> superSet, Condition<S> condition) {
		List<T> list = new ArrayList<T>();
		fillSubset(superSet, list, condition);
		return list;
	}
	
}
