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
package gr.interamerican.bo2.utils.conditions;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Condition that checks if an object exists in a collection.
 * 
 * @param <T>
 *        Type of objects being checked by the condition. 
 */
public class ExistsIn<T> 
implements Condition<T> {
	/**
	 * Set containing the values.
	 */
	Set<T> set;

	/**
	 * Creates a new ExistsInSetCondition object. 
	 *
	 * @param collection
	 */
	public ExistsIn(Collection<T> collection) {
		super();
		this.set = new HashSet<T>(collection);
	}
	
	/**
	 * Creates a new ExistsInSetCondition object. 
	 *
	 * @param collection
	 */
	public ExistsIn(T... collection) {
		super();
		this.set = new HashSet<T>(Arrays.asList(collection));
	}

	
	public boolean check(T t) {		
		return set.contains(t);
	}

}
