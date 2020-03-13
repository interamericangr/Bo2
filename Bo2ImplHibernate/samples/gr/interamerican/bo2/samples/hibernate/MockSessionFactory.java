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

	@Override
	public Reference getReference() throws NamingException {
		return null;
	}

	@Override
	public Session openSession() throws HibernateException {
		return null;
	}

	@Override
	public Session openSession(Interceptor interceptor)
			throws HibernateException {
		return null;
	}

	@Override
	public Session openSession(Connection connection) {
		return null;
	}

	@Override
	public Session openSession(Connection connection, Interceptor interceptor) {
		return null;
	}

	@Override
	public Session getCurrentSession() throws HibernateException {
		return null;
	}

	@Override
	public StatelessSession openStatelessSession() {
		return null;
	}

	@Override
	public StatelessSession openStatelessSession(Connection connection) {
		return null;
	}

	@Override
	public ClassMetadata getClassMetadata(Class entityClass) {
		return null;
	}

	@Override
	public ClassMetadata getClassMetadata(String entityName) {
		return null;
	}

	@Override
	public CollectionMetadata getCollectionMetadata(String roleName) {
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map getAllClassMetadata() {
		return null;
	}

	@Override
	public Map getAllCollectionMetadata() {
		return null;
	}

	@Override
	public Statistics getStatistics() {
		return null;
	}

	@Override
	public void close() throws HibernateException {/* empty */}

	@Override
	public boolean isClosed() {
		return false;
	}

	@Override
	public Cache getCache() {
		return null;
	}

	@Deprecated
	@Override
	public void evict(Class persistentClass) throws HibernateException {/* empty */}

	@Deprecated
	@Override
	public void evict(Class persistentClass, Serializable id)
			throws HibernateException {
		// empty
	}

	@Deprecated
	@Override
	public void evictEntity(String entityName) throws HibernateException {/* empty */}

	@Deprecated
	@Override
	public void evictEntity(String entityName, Serializable id) 
	throws HibernateException {/* empty */}

	@Deprecated
	@Override
	public void evictCollection(String roleName) 
	throws HibernateException {/* empty */}

	@Deprecated
	@Override
	public void evictCollection(String roleName, Serializable id)
	throws HibernateException {/* empty */}

	@Deprecated
	@Override
	public void evictQueries(String cacheRegion) 
	throws HibernateException {/* empty */}

	@Deprecated
	@Override
	public void evictQueries() throws HibernateException {/* empty */}


	@Override
	public Set getDefinedFilterNames() {
		return null;
	}

	@Override
	public FilterDefinition getFilterDefinition(String filterName)
			throws HibernateException {
		return null;
	}

	@Override
	public boolean containsFetchProfileDefinition(String name) {
		return false;
	}

	@Override
	public TypeHelper getTypeHelper() {
		return null;
	}
}