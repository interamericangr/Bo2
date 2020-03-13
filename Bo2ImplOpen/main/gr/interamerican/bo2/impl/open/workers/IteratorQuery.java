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
import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.arch.exceptions.DataException;

import java.util.Iterator;

/**
 * Adapts an array to a query.
 * 
 * @param <T> 
 *        Type of objects in the array.
 */
public class IteratorQuery<T> 
extends AbstractBaseWorker
implements EntitiesQuery<T> {
	
	/**
	 * Rows of the query.
	 */
	Iterator<T> iterator;
	
	/**
	 * Current row.
	 */
	int row=0; 
	
	/**
	 * Current entity.
	 */
	T entity = null;

	@Override
	public void execute() throws DataException {
		row = 0;
		entity = null;
		iterator = createIterator();
	}

	@Override
	public boolean next() throws DataAccessException {
		if (!iterator.hasNext()) {
			return false;
		}		
		row++;
		entity = iterator.next();
		return true;
	}

	@Override
	public int getRow() throws DataAccessException {		
		return row;
	}
	
	@Override
	public void setAvoidLock(boolean avoidLock) {/* empty */}
	
	@Override
	public boolean isAvoidLock() {		
		return true;
	}

	@Override
	public T getEntity() throws DataAccessException {
		return entity;
	}

	/**
	 * Creates a new IteratorQuery object. 
	 *
	 * @param iterator the iterator
	 */
	public IteratorQuery(Iterator<T> iterator) {
		super();
		this.iterator = iterator;
	}
	
	/**
	 * Creates a new IteratorQuery object.
	 * 
	 * This constructor is protected. It can be used only
	 * by a sub-class that overrides the <code>createIterator()</code>
	 * method. 
	 *
	 */
	protected IteratorQuery() {
		this(null);
	}
	
	/**
	 * This method creates the iterator.
	 * 
	 * This method is invoked by the <code>execute()</code> method in order to
	 * get the iterator with the query data. By default it returns the iterator
	 * defined in the constructor.<br>
	 * It must be overriden in order to create the iterator dynamically.
	 *
	 * @return Returns the iterator.
	 * @throws DataException the data exception
	 */
	@SuppressWarnings("unused")
	protected Iterator<T> createIterator() throws DataException {
		return iterator;		
	}
}