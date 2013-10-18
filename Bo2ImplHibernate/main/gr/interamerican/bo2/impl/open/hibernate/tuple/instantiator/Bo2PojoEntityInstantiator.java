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
package gr.interamerican.bo2.impl.open.hibernate.tuple.instantiator;

import gr.interamerican.bo2.impl.open.creation.Factory;

import java.io.Serializable;

import org.hibernate.mapping.PersistentClass;
import org.hibernate.tuple.Instantiator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An {@link Instantiator} that knows how to resolve concrete pojo entity
 * implementations given their abstract or interface representation. 
 */
public class Bo2PojoEntityInstantiator implements Instantiator {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Logger.
	 */
	private static Logger logger = LoggerFactory.getLogger(Bo2PojoEntityInstantiator.class);
	
	/**
	 * Mapped entity class.
	 */
	private Class<?> entityClass;
	
	/**
	 * Creates a new MyPojoEntityInstantiator object. 
	 * @param persistentClass 
	 */
	public Bo2PojoEntityInstantiator(PersistentClass persistentClass) {
		this.entityClass = persistentClass.getMappedClass();
	}

	@SuppressWarnings("nls")
	public Object instantiate(Serializable id) {
		if(logger.isDebugEnabled()) {
			String s = id == null ? "null" : id.toString();
			String msg = "Instantiating entity " + entityClass.getName() + " with id " + s;
			logger.debug(msg);
		}
		return Factory.create(entityClass);
	}

	public Object instantiate() {
		@SuppressWarnings("nls")
		String msg = "Attempted to initialize an Entity of type " + entityClass.getName() + " with no id";
		throw new RuntimeException(msg);
	}

	public boolean isInstance(Object object) {
		return entityClass.isInstance(object);
	}

}
