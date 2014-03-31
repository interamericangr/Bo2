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
import gr.interamerican.bo2.arch.ModificationRecord;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.po.AbstractBasePo;
import gr.interamerican.bo2.impl.open.po.PoReattachAnalysis;
import gr.interamerican.bo2.impl.open.po.PoReattachAnalysis.PoReattachAnalysisResult;

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
	private static Logger LOGGER = LoggerFactory.getLogger(AbstractHibernateDetachStrategy.class.getName());
	
	/**
	 * Transient Objects found on last {@link #reattach(Object, Provider, boolean)} execution
	 * Needed for proper {@link #detach(Object, Provider)} implementation.
	 */
	protected Set<Object> transientObjectsOnLastReattach = new HashSet<Object>();
	
	public void markExplicitSave(Object object, Provider provider) {
		HibernateSessionProvider hProv = HibernateBo2Utils.getHibernateSessionProvider(object, provider);
		if(hProv!=null) {
			hProv.setFlushStrategy(FlushStrategy.EXCLUDING);
			hProv.setExcluded(object);
		}
	}
	
	public void reattach(Object object, Provider provider) {
		reattach(object, provider, false);
	}
	
	public void reattachForUpdate(Object object, Provider provider) {
		reattach(object, provider, true);
	}
	
	
	/**
	 * Internal implementation
	 * 
	 * @param object
	 * @param provider
	 * @param forUpdate
	 */
	@SuppressWarnings("nls")
	private void reattach(Object object, Provider provider, boolean forUpdate) {
		if(object == null) { 
			return; 
		}
		if (!(object instanceof PersistentObject)) { 
			return; 
		}
		
		boolean objectIsTransient = HibernateBo2Utils.isTransient((PersistentObject<?>) object);
		
		HibernateSessionProvider hProv = HibernateBo2Utils.getHibernateSessionProvider(object, provider);
		if(hProv==null) {
			return;
		}
		Session session = hProv.getHibernateSession();
		
		Set<Object> objectsToReattachManually = new HashSet<Object>();
		Set<Object> transientObjects = new HashSet<Object>();
		if(object instanceof AbstractBasePo) {
			PoReattachAnalysisResult poInspectionResult = PoReattachAnalysis.get().execute((AbstractBasePo<?>) object);
			objectsToReattachManually = poInspectionResult.getPosToReattachManually();
			transientObjectsOnLastReattach = poInspectionResult.getTransientPos();
			if(!objectIsTransient) { //If the original object is transient we will not care for transient Child objects
				transientObjects = poInspectionResult.getTransientPos();
				
				//set lastModifiedBy to all transient children of a non-transient PO being reattached before #doReattach() runs.
				for(Object o : transientObjects) {
					setLastModifiedBy(o);
				}
			}
		}
		
		LOGGER.debug("--->reattach " + object.getClass().getName() + object.toString());
		
		if(!HibernateBo2Utils.isTransient((PersistentObject<?>) object)) {
			try {
				boolean mayLockOrUpdate = mayLockOrUpdate(object, session);
				if(mayLockOrUpdate) {
					doReattach(object, session);
				}
			} catch (HibernateException he) {
				throw new RuntimeException(he);
			}
			hProv.register(object);
		}

		/*
		 * Detach transient objects on the graph that were possibly
		 * attached by a cascade option during #doReattach execution.
		 */
		int j = 0;
		for(Object obj : transientObjects) {
			if(forUpdate) {
				break; //do not detach transient objects if a db update will happen in this unit of work
			}
			boolean detachTransientObjectAttachedByCascade = detachTransientObjectAttachedByCascade(obj, session);
			if(detachTransientObjectAttachedByCascade) {
				LOGGER.debug("detached transient: " + obj.getClass().getName() + obj.toString()); 
				j++;
			}
		}
		
		/*
		 * Re-attach manually not owned associations for which there
		 * was no cascade option during #doReattach execution.
		 */
		int i = 0;
		for(Object obj : objectsToReattachManually) {
			try {
				boolean mayLockOrUpdate = mayLockOrUpdate(obj, session);
				if(mayLockOrUpdate) {
					session.buildLockRequest(LockOptions.NONE).lock(obj); 
					LOGGER.debug("reattached by locking: " + obj.getClass().getName() + obj.toString()); 
					i++;
				}
			}catch (HibernateException he) {
				throw new RuntimeException(he);
			}
		}
		
		LOGGER.debug("Manual reattach candidates: " + objectsToReattachManually.size() + ". Reattached: " + i);
		LOGGER.debug("Transient objects: " + transientObjects.size() + ". Detached manually: " + j);
		LOGGER.debug("reattached " + object.getClass().getName() + object + "<---");
		
	}
	
	/**
	 * Re-attaches the entity depending on its concrete {@link DetachStrategy}.
	 * 
	 * @param object
	 * @param session
	 */
	protected abstract void doReattach(Object object, Session session);
	
	/**
	 * Detaches the entity.
	 * 
	 * @param object
	 * @param session
	 * @return Whether it was indeed detached.
	 */
	private boolean detachTransientObjectAttachedByCascade(Object object, Session session) {
		boolean evicted = false;
		boolean mdfCleared = false;
		
		if(session.contains(object)) {
			session.evict(object);
			evicted = true;
		}
		
		//an object may have been evicted by cascade previously, so we check this separately
		if(object instanceof ModificationRecord) {
			ModificationRecord mdf = (ModificationRecord) object;
			if(mdf.getLastModified()!=null) {
				mdf.setLastModified(null);
				mdfCleared = true;
			}
		}
		
		return evicted || mdfCleared;
	}
	
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
	
	/**
	 * Sets the current user as the last modifier of a ModificationRecord po
	 * if there is no lastModifiedBy set.
	 * @param o
	 */
	void setLastModifiedBy(Object o) {
		if(!(o instanceof ModificationRecord)) {
			return;
		}
		String userId = Bo2Session.getUserId();
		ModificationRecord mdf = (ModificationRecord) o;
		if(userId!=null && mdf.getLastModifiedBy()==null) {
			mdf.setLastModifiedBy(userId);
		}
	}

}
