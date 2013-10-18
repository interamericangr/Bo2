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
package gr.interamerican.bo2.impl.open.hibernate.tuple.resolver;

import gr.interamerican.bo2.impl.open.creation.Factory;

import org.hibernate.EntityNameResolver;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link EntityNameResolver} implementation that resolves the
 * entityName as it appears in Hibernate mapping files for a 
 * given entity instance.
 * 
 * Implementation of hashcode and equals helps the {@link SessionFactory}
 * speed things up, if multiple {@link EntityNameResolver}s are registered.
 */
public class Bo2EntityNameResolver implements EntityNameResolver {
	
	/**
	 * Logger.
	 */
	private static Logger logger = LoggerFactory.getLogger(Bo2EntityNameResolver.class);
	
	/**
	 * Singleton instance.
	 */
	public static final Bo2EntityNameResolver INSTANCE = new Bo2EntityNameResolver();
	
	@SuppressWarnings("nls")
	public String resolveEntityName(Object entity) {
		Class<?> clazz = entity.getClass();
		String entityName = Factory.declarationTypeName(clazz);
		if(logger.isDebugEnabled()) {
			String msg = "Type: " + clazz.getName() + " -----> Entity: " + entityName;
			logger.debug(msg);
		}
		return entityName;
	}
	
	@Override
	public boolean equals(Object obj) {
		return getClass().equals( obj.getClass() );
	}
	
	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
	
	/**
	 * Hidden constructor, this is a singleton.
	 */
	private Bo2EntityNameResolver() { /* hidden, empty */ }

}
