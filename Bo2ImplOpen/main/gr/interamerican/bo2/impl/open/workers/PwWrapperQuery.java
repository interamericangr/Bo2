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
import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.Query;
import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;
import gr.interamerican.bo2.arch.ext.CriteriaDependent;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.utils.annotations.Child;

import java.io.Serializable;

/**
 * Wrapper query around a persistence worker.
 * 
 * This class is an adapter that transforms the interface of a {@link PersistenceWorker}
 * to {@link Query}. The query is an {@link EntitiesQuery} that fetches the entity read
 * by the persistence worker. The class is also {@link CriteriaDependent}. The type of
 * criteria can't be other than the key of the entity {@link PersistentObject}. 
 * 
 * @param <P> 
 * @param <K> 
 */
public class PwWrapperQuery
<P extends PersistentObject<K>, K extends Serializable & Comparable<? super K>> 
extends AbstractBaseWorker 
implements EntitiesQuery<P>, CriteriaDependent<K> {

	/**
	 * Creates a new PwWrapperQuery object. 
	 * 
	 * @param entityClass
	 *        Type of entity.
	 *
	 */
	public PwWrapperQuery(Class<P> entityClass) {
		super();
		this.entityClass = entityClass;
		this.pw = Factory.createPw(entityClass);		
	}
	
	/**
	 * Persistence worker.
	 */
	@Child PersistenceWorker<P> pw;
	
	/**
	 * Entity class.
	 */
	private Class<P> entityClass;
	
	/**
	 * criteria.
	 */
	private K criteria; 
	
	/**
	 * Persistent object to serve.	
	 */
	private P entity=null;
	
	/**
	 * current row.
	 */
	private int row=0;
	
	public void execute() throws DataException {
		row = 0;
		if (criteria==null) {
			throw new DataException(new NullPointerException());
		}
		entity = Factory.create(entityClass);
		entity.setKey(criteria);
		try {
			entity = pw.read(entity);
		} catch (PoNotFoundException pnfe) {
			entity = null;
		}
	}
	
	public P getEntity() throws DataAccessException {	
		if (entity==null) {
			throw new DataAccessException();
		}
		return entity;
	}
	
	
	public boolean next() throws DataAccessException {
		if (row > 0) {
			entity = null;			
		}
		if (entity==null) {
			return false;
		}
		row++;
		return true; 
	}

	/**
	 * Gets the criteria.
	 *
	 * @return Returns the criteria
	 */
	public K getCriteria() {
		return criteria;
	}

	/**
	 * Assigns a new value to the criteria.
	 *
	 * @param criteria the criteria to set
	 */
	public void setCriteria(K criteria) {
		this.criteria = criteria;
	}
	
	public void setAvoidLock(boolean avoidLock) {
		/* empty */
	}
	
	public boolean isAvoidLock() {		
		return true;
	}
	
	public int getRow() throws DataAccessException {		
		return row;
	}	

}
