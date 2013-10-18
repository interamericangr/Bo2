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
import gr.interamerican.bo2.utils.adapters.AnyOperation;
import gr.interamerican.bo2.utils.annotations.Child;

/**
 * Query that wraps another query and transforms the entities
 * fetched by the wrapped query using a transformation.
 * 
 * @param <F> 
 *        Type of entity fetched by the wrapped query.
 * @param <T>
 *        Type of entity fetched by this query.  
 * 
 */
public class TransformedQuery<F,T> 
extends AbstractBaseWorker
implements EntitiesQuery<T> {
	/**
	 * Wrapped query.
	 */
	@Child protected EntitiesQuery<F> query;
	
	/**
	 * Transformation.
	 */
	protected AnyOperation<F, T> transformation;
	
	/**
	 * Current row.
	 */
	private int row = 0;
	/**
	 * Current entity.
	 */
	private T entity = null;
	
	/**
	 * Creates a new TransformedQuery object. 
	 *
	 * @param query
	 *        Query wrapped inside this query.
	 * @param transformation
	 *        Transformation to run.
	 */
	public TransformedQuery(EntitiesQuery<F> query, AnyOperation<F, T> transformation) {
		super();
		this.query = query;
		this.transformation = transformation;
	}
	

	
	public void execute() throws DataException {
		row = 0;
		entity = null;
		query.execute();		
	}

	
	public boolean next() throws DataAccessException {
		if (query.next()) {
			F wrapped = query.getEntity();
			entity = transformation.execute(wrapped);
			if (entity!=null) {
				row++;
				return true;
			} else {
				return next();				
			}			
		} else {
			return false;			
		}		
	}
	
	public int getRow() throws DataAccessException {		
		return row;
	}
	
	public void setAvoidLock(boolean avoidLock) {
		query.setAvoidLock(avoidLock);		
	}

	public boolean isAvoidLock() {		
		return query.isAvoidLock();
	}
	
	public T getEntity() throws DataAccessException {	
		return entity;
	}

}
