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
package gr.interamerican.bo2.impl.open.hibernate;

import gr.interamerican.bo2.arch.DetachStrategy;
import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;
import gr.interamerican.bo2.impl.open.annotations.Bo2AnnoUtils;
import gr.interamerican.bo2.impl.open.po.PoUtils;
import gr.interamerican.bo2.utils.meta.validators.Validator;

import java.io.Serializable;

/**
 * Implementation of {@link PersistenceWorker} based on Hibernate.
 * 
 * @param <P> Type of {@link PersistentObject} that is persisted
 *            by this worker. A hibernate mapping for this type 
 *            is necessary for this persistence worker.
 *            
 */
public class GenericHibernatePersistenceWorker 
<P extends PersistentObject<?>> 
extends AbstractHibernatePersistenceUtility<P>
implements PersistenceWorker<P> {
	
	/**
	 * Factory method.
	 * 
	 * Creates a new {@link PersistenceWorker} for a persistent class.
	 * 
	 * @param entityType
	 *        Persistent class. 
	 * @param validator 
	 *        Validator.
	 * @param <E>
	 *        Persistent class.
	 * 
	 *
	 *        
	 * @return Returns a new GenericHibernatePersistenceWorker.
	 */	
	public static <E extends PersistentObject<?>> 
	GenericHibernatePersistenceWorker<E> newInstance(Class<E> entityType, Validator<E> validator) {
		RefreshMode mode = RefreshMode.getRefreshMode(entityType);
		return new GenericHibernatePersistenceWorker<E>(entityType,validator,mode);
	}
	
	/**
	 * Factory method.
	 * 
	 * Creates a new {@link PersistenceWorker} for a persistent class.
	 * 
	 * @param entityType
	 *        Persistent class. 
	 * @param <E>
	 *        Persistent class.
	 *        
	 * @return Returns a new GenericHibernatePersistenceWorker.
	 */	
	public static <E extends PersistentObject<?>> 
	GenericHibernatePersistenceWorker<E> newInstance(Class<E> entityType) {
		RefreshMode mode = RefreshMode.getRefreshMode(entityType);
		return new GenericHibernatePersistenceWorker<E>(entityType, mode);
	}
	
	/**
	 * Creates a new GenericHibernatePersistenceWorker object. 
	 *
	 * @param poClass
	 * @param validator 
	 * @param mode 
	 */
	public GenericHibernatePersistenceWorker(Class<P> poClass, Validator<P> validator, RefreshMode mode) {
		super(poClass,validator,mode);
		managerName = Bo2AnnoUtils.getManagerName(poClass);		
	}
	
	/**
	 * Creates a new GenericHibernatePersistenceWorker object. 
	 *
	 * @param poClass
	 * @param mode 
	 */
	public GenericHibernatePersistenceWorker(Class<P> poClass, RefreshMode mode) {
		this(poClass,null,mode);				
	}	
	
	/**
	 * Creates a new GenericHibernatePersistenceWorker object. 
	 *
	 * @param poClass
	 */
	public GenericHibernatePersistenceWorker(Class<P> poClass) {
		this(poClass,null,RefreshMode.getRefreshMode(poClass));				
	}	
	
	@Override
	protected void prepareObject(P po) {
		po.tidy();	
	}
	
	@Override
	protected Serializable getUniqueId(P po) {
		return po.getKey();
	}
	
	public DetachStrategy getDetachStrategy() {		
		return new HibernateDetachStrategy();
	}
	
	@Override
	public P read(P o) 
	throws DataException, PoNotFoundException {		
		P po = super.read(o);
		PoUtils.setDetachStrategy(po, getDetachStrategy());
		return po;
	}
	
	@Override
	public P store(P o) throws DataException, PoNotFoundException {
		P po = super.store(o);
		PoUtils.setDetachStrategy(po, getDetachStrategy());
		return po;
	}
	
}
