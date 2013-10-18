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
package gr.interamerican.bo2.samples.hibernate;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Map;
import java.util.Set;

import javax.naming.NamingException;
import javax.naming.Reference;

import org.hibernate.Cache;
import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.TypeHelper;
import org.hibernate.classic.Session;
import org.hibernate.engine.FilterDefinition;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.stat.Statistics;

/**
 * MockSessionFactory.
 */
@SuppressWarnings({ "serial", "rawtypes" })
public class MockSessionFactory implements SessionFactory {

	public Reference getReference() throws NamingException {
		return null;
	}

	public Session openSession() throws HibernateException {
		return null;
	}
	
	public Session openSession(Interceptor interceptor)
			throws HibernateException {
		return null;
	}

	public Session openSession(Connection connection) {
		return null;
	}
	
	public Session openSession(Connection connection, Interceptor interceptor) {
		return null;
	}

	public Session getCurrentSession() throws HibernateException {
		return null;
	}

	public StatelessSession openStatelessSession() {
		return null;
	}

	public StatelessSession openStatelessSession(Connection connection) {
		return null;
	}
	
	public ClassMetadata getClassMetadata(Class entityClass) {
		return null;
	}

	
	public ClassMetadata getClassMetadata(String entityName) {
		return null;
	}

	public CollectionMetadata getCollectionMetadata(String roleName) {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public Map getAllClassMetadata() {
		return null;
	}
	
	public Map getAllCollectionMetadata() {
		return null;
	}

	public Statistics getStatistics() {
		return null;
	}

	public void close() throws HibernateException {/* empty */}

	public boolean isClosed() {
		return false;
	}

	public Cache getCache() {
		return null;
	}

	public void evict(Class persistentClass) throws HibernateException {/* empty */}
	
	public void evict(Class persistentClass, Serializable id)
			throws HibernateException {
		// TODO Auto-generated method stub
		
	}

	public void evictEntity(String entityName) throws HibernateException {/* empty */}
	
	public void evictEntity(String entityName, Serializable id) 
	throws HibernateException {/* empty */}
	
	public void evictCollection(String roleName) 
	throws HibernateException {/* empty */}
		
	public void evictCollection(String roleName, Serializable id)
	throws HibernateException {/* empty */}

	public void evictQueries(String cacheRegion) 
	throws HibernateException {/* empty */}

	public void evictQueries() throws HibernateException {/* empty */}

	public Set getDefinedFilterNames() {
		return null;
	}

	public FilterDefinition getFilterDefinition(String filterName)
			throws HibernateException {
		return null;
	}

	public boolean containsFetchProfileDefinition(String name) {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.SessionFactory#getTypeHelper()
	 */
	public TypeHelper getTypeHelper() {
		// TODO Auto-generated method stub
		return null;
	}

}
