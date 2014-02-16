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
package gr.interamerican.bo2.utils.adapters;

import gr.interamerican.bo2.utils.CollectionUtils;

import java.util.List;

/**
 * Transforms a list to an array.
 * 
 * @param <T> 
 *        Type of elements in the collection.
 */
public class CollectionToArray<T> 
implements Transformation<List<T>, T[]> {
	
	/**
	 * Sample array.
	 */
	T[] sample;
	
	/**
	 * Creates a new ListToArray object. 
	 *
	 * @param sample
	 */
	public CollectionToArray(T[] sample) {
		super();
		this.sample = sample;
	}

	public T[] execute(List<T> a) {	
		return CollectionUtils.toArray(a, sample);
	}
	
	

}
