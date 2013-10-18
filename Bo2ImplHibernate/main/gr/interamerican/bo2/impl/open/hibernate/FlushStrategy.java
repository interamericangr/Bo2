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

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;

/**
 * Defines the flush policy for a session.
 */
public enum FlushStrategy {
	
	/**
	 * The session will be flushed.
	 * 
	 * This is the simplest and fastest approach.
	 */
	SESSION {
		@Override
		public void flush (Object object, Session session, Set<?> managed, 	Set<?> excluded) {
			session.flush();
		}		
	},
	
	/**
	 * Only changes that affect a specific object will be flushed.
	 * 
	 * In order to do so, all objects except from the one specified
	 * will be evicted from the session before flushing and then, they 
	 * will be re-attached to the session. This is expensive behavior,
	 * therefore this flushing policy should be used in rare cases, when 
	 * it is absolutely necessary.
	 * 
	 * The managed parameter is passed on as a new {@link HashSet} 
	 * containing all the managed entities, so that 
	 * {@link #flush(Object, Session, Set, Set)} will not modify it. 
	 */
	OBJECT{
		@Override
		public void flush(Object object, Session session, Set<?> managed, Set<?> excluded) {
			flush(object, session, new HashSet<Object>(managed));			
		}		
	},
	
	/**
	 * Changes to session will be flushed, excluding objects that have
	 * been marked as excluded.
	 * 
	 * This flushing mode is less expensive than {@link #OBJECT}, but still
	 * heavier than {@link #SESSION}. 
	 * 
	 * The managed parameter is passed on as a new {@link HashSet} 
	 * containing all the managed entities, so that 
	 * {@link #flush(Object, Session, Set, Set)} will not modify it. 
	 * 
	 * @see HibernateSessionProvider#setExcluded(Object)
	 * @see HibernateSessionProvider#setNotExcluded(Object)
	 */
	EXCLUDING{
		@Override
		public void flush(Object object, Session session, Set<?> managed, Set<?> excluded) {
			flush(object, session, new HashSet<Object>(excluded));
		}		
	};
	
	/**
	 * Flush the session according to this policy.
	 * 
	 * @param object
	 *        Object relevant with flushing.
	 * @param session
	 *        Session.
	 * @param managed
	 *        Objects managed by this session.
	 * @param excluded
	 *        Objects specifically marked as excluded from flushing.
	 */
	public abstract void flush(Object object, Session session, Set<?> managed, Set<?> excluded);
	
	/**
	 * Flushes the session, excluding the specified objects.
	 * 
	 * @param object
	 * @param session
	 * @param excluded
	 */
	private static void flush(Object object, Session session, Set<?> excluded) {
		if (excluded.isEmpty()) { 
			session.flush();
		} else {
			/*
			 * 1. Remove the specified object from the collection of excluded objects.
			 */
			excluded.remove(object);			
			/*
			 * 2. There is no way to separate dirty from clean objects, so we will
			 *    evict all objects.
			 */
			for (Object objectToEvict : excluded) {
				if(session.contains(objectToEvict)) {
					session.evict(objectToEvict);
				}
			}
			/*
			 * 3. Flush the session.
			 */
			session.flush();
			/*
			 * 4. Add the evicted objects to the session. 
			 */
			for (Object objectToReAttach : excluded) {
				if(!session.contains(objectToReAttach)) {
					session.update(objectToReAttach);
				}
			}		
		}
	}
	

}
