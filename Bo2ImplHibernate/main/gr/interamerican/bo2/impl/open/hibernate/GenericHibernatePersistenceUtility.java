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

import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.meta.validators.Validator;
import gr.interamerican.bo2.utils.reflect.AccessorUtils;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * Generic Hibernate Persistence Utility.
 * 
 * @param <P> 
 *        Type of object being persisted by this utility.
 * 
 */
public class GenericHibernatePersistenceUtility<P> 
extends AbstractHibernatePersistenceUtility<P> {
	/**
	 * Getter method for the id.
	 */
	Method idGetter;
	
	/**
	 * Creates a new GenericHibernatePersistenceUtility object. 
	 *
	 * @param poClass
	 *        Class of persistent object.
	 * @param idClass
	 *        Class of id.  
	 * @param property 
	 *        Name of id property.
	 * @param validator 
	 *        Validator.
	 * @param mode 
	 *        RefreshMode
	 */
	public GenericHibernatePersistenceUtility(Class<P> poClass, Class<?> idClass, 
			String property, Validator<P> validator, RefreshMode mode) {
		super(poClass,validator,mode);
		idGetter = AccessorUtils.getGetter(property, idClass, poClass);
	}
	
	/** 
	 * Creates a new GenericHibernatePersistenceUtility object. 
	 *
	 * @param poClass
	 *        Class of persistent object.
	 * @param idClass
	 *        Class of id.  
	 * @param property 
	 *        Name of id property.
	 * @param mode 
	 *        RefreshMode
	 */
	public GenericHibernatePersistenceUtility(Class<P> poClass, Class<?> idClass, String property, RefreshMode mode) {
		this(poClass,idClass,property,null,mode);
	}
	
	@Override
	protected void prepareObject(P po) {
		/* do nothing */
	}
	
	@Override
	protected Serializable getUniqueId(P po) {
		Object[] args = {};
		Object id = ReflectionUtils.invoke(idGetter, po, args);
		return (Serializable)id;
	}
	
	

}
