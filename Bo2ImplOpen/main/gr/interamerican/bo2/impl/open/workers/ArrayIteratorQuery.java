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
package gr.interamerican.bo2.impl.open.workers;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.exceptions.DataException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Adapts an array to a query. 
 * 
 * @param <T> 
 *        Type of objects in the array.
 */
public class ArrayIteratorQuery<T> 
extends IteratorQuery<T>
implements EntitiesQuery<T> {
	
	/**
	 * Array.
	 */
	private T[] array;

	/**
	 * Creates a new ArrayIteratorQuery object. 
	 *
	 * @param array
	 */
	public ArrayIteratorQuery(T[] array) {
		super();
		this.array = array;
	}
	
	/**
	 * Creates a new ArrayIteratorQuery object. 
	 * 
	 * This constructor is protected. It can be used only by a sub-class
	 * that overrides the <code>createArray()</code> method.
	 */
	protected ArrayIteratorQuery() {
		super();
	}
	
	@Override
	protected final Iterator<T> createIterator() 
	throws DataException {
		T[] elements = createArray();
		List<T> list;  
		if (elements!=null) {
			list = Arrays.asList(elements);
		} else {
			list = new ArrayList<T>(); 
		}		
		return list.iterator();
	}
	
	/**
	 * This method creates the array.
	 * 
	 * This method is invoked by the <code>execute()</code>
	 * method in order to get the array with the query
	 * data. By default it returns the array defined in
	 * the constructor. It must be overriden in order to
	 * create the array dynamically. 
	 * 
	 * @return Returns the iterator.
	 * 
	 * @throws DataException 
	 */
	protected T[] createArray() throws DataException {
		return array;
	}
	
	
	
	
	
	
}
