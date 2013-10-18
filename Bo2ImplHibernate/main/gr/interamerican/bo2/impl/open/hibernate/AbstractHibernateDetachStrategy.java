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
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.impl.open.po.AbstractBasePo;
import gr.interamerican.bo2.impl.open.po.ObjectsToReattachManually;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base Hibernate {@link DetachStrategy} implementation.
 */
public abstract class AbstractHibernateDetachStrategy implements DetachStrategy {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * logger.
	 */
	private static Logger logger = LoggerFactory.getLogger(AbstractHibernateDetachStrategy.class);
	
	public void detach(Object object, Provider provider) {
		/* Don't do anything special */
	}

	public void markExplicitSave(Object object, Provider provider) {
		HibernateSessionProvider hProv = HibernateBo2Utils.getHibernateSessionProvider(object, provider);
		if(hProv!=null) {
			hProv.setFlushStrategy(FlushStrategy.EXCLUDING);
			hProv.setExcluded(object);
		}
	}

	public void reattach(Object object, Provider provider) {
		if(object == null) { 
			return; 
		}
		if (!(object instanceof PersistentObject)) { 
			return; 
		}		
		HibernateSessionProvider hProv = HibernateBo2Utils.getHibernateSessionProvider(object, provider);
		if(hProv==null) {
			return;
		}
		Session session = hProv.getHibernateSession();
		
		Set<Object> objectsToReattachManually = null;
		if(object instanceof AbstractBasePo) {
			objectsToReattachManually = ObjectsToReattachManually.INSTANCE.execute((AbstractBasePo<?>) object);
		} else {
			objectsToReattachManually = new HashSet<Object>();
		}
		
		if(!HibernateBo2Utils.isTransient((PersistentObject<?>) object)) {
			try {
				/*
				 * TODO: Do we want to force this, even if the check fails possibly
				 * by evicting from the session first?
				 */
				if(mayLockOrUpdate(object, session)) {
					doReattach(object, session);
				}
			} catch (HibernateException he) {
				throw new RuntimeException(he);
			}
			hProv.register(object);
		}
		
		int i = 0;
		for(Object obj : objectsToReattachManually) {
			try {
				if(mayLockOrUpdate(obj, session)) {
					session.buildLockRequest(LockOptions.NONE).lock(obj); 
					i++;
				}
			}catch (HibernateException he) {
				throw new RuntimeException(he);
			}
		}
		logger.debug("Reattach candidates: " + objectsToReattachManually.size() + ". Reattached: " + i); //$NON-NLS-1$ //$NON-NLS-2$
		
	}
	
	/**
	 * Re-attaches the entity depending on its concrete {@link DetachStrategy}.
	 * 
	 * @param object
	 * @param session
	 */
	protected abstract void doReattach(Object object, Session session);
	
	/**
	 * Indicates if an entity may be locked or updated.
	 * 
	 * @param object
	 * @param session 
	 * @return True, if the entity can be locked.
	 */
	private boolean mayLockOrUpdate(Object object, Session session) {
		if(session.contains(object)) {
			return false;
		}
		if(HibernateBo2Utils.isTransient((PersistentObject<?>) object)) {
			return false;
		}
		if(HibernateBo2Utils.sessionContains(object, session)) {
			return false;
		}
		return true;
	}

}
