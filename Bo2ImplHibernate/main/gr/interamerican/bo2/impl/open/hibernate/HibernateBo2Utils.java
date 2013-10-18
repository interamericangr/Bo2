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

import gr.interamerican.bo2.arch.ModificationRecord;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.utils.ProviderUtils;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.po.PoUtils;
import gr.interamerican.bo2.utils.Debug;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringConstants;

import java.io.Serializable;
import java.util.Map;

import org.hibernate.EntityMode;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.EntityKey;
import org.hibernate.engine.PersistenceContext;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.stat.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for Hibernate support.
 */
public class HibernateBo2Utils {
	
	/**
	 * logger.
	 */
	private static Logger logger = LoggerFactory.getLogger(HibernateBo2Utils.class);
	
	
	/**
	 * Gets the {@link HibernateSessionProvider} that manages the specified object.
	 * 
	 * @param object
	 * @param provider
	 * 
	 * @return Returns the HibernateSessionProvider that manages the specified object.
	 *         If the specified object is not managed by a HibernateSessionProvider,
	 *         returns null.
	 */
	public static HibernateSessionProvider getHibernateSessionProvider(Object object, Provider provider) {
		String manager = PoUtils.getManagerName(object);
		if (manager==null) {
			return null;			
		}
		return ProviderUtils.getResourceWrapper(manager, HibernateSessionProvider.class, provider);
	}
	
	/**
	 * Logs {@link SessionFactory} statistics.
	 * 
	 * @param sessionFactory 
	 */
	@SuppressWarnings("nls")
	public static void logSessionFactoryStatistics(SessionFactory sessionFactory) {
		Statistics statistics = sessionFactory.getStatistics();
		Object[] stats = new Object[] {
			statistics.getCollectionFetchCount(),
			statistics.getCollectionLoadCount(),
			statistics.getEntityFetchCount(),
			statistics.getEntityLoadCount(),
			statistics.getFlushCount(),
			statistics.getOptimisticFailureCount(),
			statistics.getQueryExecutionMaxTime(),
			statistics.getQueryExecutionMaxTimeQueryString(),
			statistics.getSessionOpenCount()
		};
		StringBuilder sb = new StringBuilder();
		for(Object o : stats) {
			String s = (o==null) ? "null" : o.toString();
			sb.append(s + StringConstants.COMMA + StringConstants.SPACE);
		}
		sb.setLength(sb.length()-2);
		Debug.debug(logger, "Factory statistics: [ " + sb.toString() + " ]");
	}
	
	/**
	 * @param po
	 *        PersistentObject
	 *        
	 * @return Returns true, if the specified <code>po</code> is transient. 
	 */
	public static boolean isTransient(PersistentObject<?> po) {
		if(po instanceof HibernateProxy) {
			return false;
		}
		Serializable key = po.getKey();
		if(key==null) {
			return true;
		}
		Map<String, Object> keyProperties = ReflectionUtils.getProperties(key);
		for (Map.Entry<String, Object> entry : keyProperties.entrySet()) {
		    if(entry.getValue()==null) { 
		    	return true; 
		    }
		}
		if(po instanceof ModificationRecord) {
			ModificationRecord mdf = (ModificationRecord) po;
			if(Hibernate.isInitialized(mdf)) {
				if(mdf.getLastModified()==null) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Checks if the persistence context contains an object.
	 * 
	 * This utility does not use a strictly public hibernate API.
	 * 
	 * @param object
	 *        Object to check for.
	 * @param s
	 *        Session instance.
	 *        
	 * @return Returns true, if this object is contained in the persistence context.
	 */
	@SuppressWarnings("nls")
	public static boolean sessionContains(Object object, Session s) {
		Serializable identifier = null;
		String entityName = null;
		
		if(object instanceof HibernateProxy) {
			HibernateProxy proxy = (HibernateProxy) object;
			identifier = proxy.getHibernateLazyInitializer().getIdentifier();
			entityName = proxy.getHibernateLazyInitializer().getEntityName();
		} else if(object instanceof PersistentObject) {
			PersistentObject<?> po = (PersistentObject<?>) object;
			identifier = po.getKey();
			entityName = Factory.declarationTypeName(object.getClass());
		}
		
		if(identifier == null) {
			return false;
		}
		
		SessionImplementor sessionImpl = (SessionImplementor) s;
		EntityPersister entityPersister = sessionImpl.getFactory().getEntityPersister(entityName);
        PersistenceContext persistenceContext = sessionImpl.getPersistenceContext();
        EntityKey entityKey = new EntityKey(identifier, entityPersister, EntityMode.POJO);
        Object entity = persistenceContext.getEntity(entityKey);
        
        boolean contains = entity!=null;
        boolean differentInstances = object!=entity;
        
        if(contains && differentInstances && logger.isDebugEnabled()) {
        	logger.warn("Entity with identifier " + identifier + " was already associated with the Session as another instance.");
        }
        
        return contains;
	}
	
	/**
	 * Gets the persistent object with the specified key from the session.
	 * 
	 * This utility does not use a strictly public hibernate API.
	 * 
	 * @param identifier 
	 *        Id of the object to check for.
	 * @param clazz 
	 *        Mapped persistent type of the object being checked for.
	 * @param s
	 *        Session instance.
	 *        
	 * @return Returns true, if this object is contained in the persistence context.
	 */	
	public static Object getFromSession(Serializable identifier, Class<?> clazz, Session s) {		
		String entityName = clazz.getName();
		
		if(identifier == null) {
			return null;
		}
		
		SessionImplementor sessionImpl = (SessionImplementor) s;
		EntityPersister entityPersister = sessionImpl.getFactory().getEntityPersister(entityName);
        PersistenceContext persistenceContext = sessionImpl.getPersistenceContext();
        EntityKey entityKey = new EntityKey(identifier, entityPersister, EntityMode.POJO);
        Object entity = persistenceContext.getEntity(entityKey); 
        return entity;
	}

}
