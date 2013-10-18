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

import static gr.interamerican.bo2.utils.StringConstants.EMPTY;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.state.CrudStates;
import gr.interamerican.bo2.impl.open.utils.Exceptions;
import gr.interamerican.bo2.impl.open.utils.Messages;
import gr.interamerican.bo2.impl.open.workers.AbstractResourceConsumer;
import gr.interamerican.bo2.utils.Debug;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.Utils;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract base class for workers implementation with Hibernate.
 *
 */
public class AbstractHibernateWorker 
extends AbstractResourceConsumer {
	/**
	 * Message key for message stating that provided session is null.
	 */
	private static final String NULL_SESSION = Messages.NULL_RESOURCE;
	
	/**
	 * Message key for message stating that a HibernateSessionProvider
	 * could not be acquired.
	 */
	private static final String NO_PROVIDER	= Messages.NO_PROVIDER;
	
	/**
	 * Logger.
	 */
	private static Logger logger = LoggerFactory.getLogger(AbstractHibernateWorker.class);
	
	/**
	 * Hibernate session
	 */
	protected Session session;
	
	/**
	 * Hibernate provider
	 */
	private HibernateSessionProvider hiberProv;
	
	@Override
	public void init(Provider parent) throws InitializationException {		
		super.init(parent);			
		hiberProv = getResource(HibernateSessionProvider.class);
		if (hiberProv==null) {
			throw Exceptions.initializationException 
				(NO_PROVIDER, HibernateSessionProvider.class);
		}				
		session = hiberProv.getHibernateSession();
		if (session==null) {
			throw Exceptions.initializationException
				(NULL_SESSION, Session.class);
		}
	}	
		
	/**
	 * Flushes the database changes for the specific object.
	 * 
	 * The operation is delegated to this worker's {@link HibernateSessionProvider}.
	 * Changes to other objects may or may not be flushed, depending to
	 * the {@link HibernateSessionProvider}'s flushing policy.
	 * An optimistic HibernateSessionProvider implementation would flush any
	 * changes. 
	 * 
	 * @param object
	 * 
	 * @see HibernateSessionProvider#flush(Object)
	 */
	void flush(Object object) {			
		hiberProv.flush(object);
	}
	
	/**
	 * Registers an object.
	 * 
	 * @param object
	 */
	void register(Object object) {
		hiberProv.register(object);
	}
	
	/**
	 * Wrapper around session.get(clazz, id).
	 * 
	 * @param clazz
	 * @param id
	 * @return Returns the object returned by session.get.
	 */
	Object getEntity(Class<?> clazz, Serializable id) {		
		try {
			Debug.setActiveModule(session);
			Bo2Session.setState(CrudStates.READ);
			Object o = session.get(clazz, id);
			if (o!=null) {
				register(o); 
			}
			return o;
		} catch (HibernateException e) {
			logHibernateException(e);
			throw e;			
		} finally {
			Bo2Session.setState(null);
			Debug.resetActiveModule();
		}
	}
	
	/**
	 * Wrapper around session.save(object).
	 * 
	 * @param object
	 * @return Returns the object returned by session.save.
	 */
	Serializable saveEntity(Object object) {		
		try {
			Debug.setActiveModule(session);
			Bo2Session.setState(CrudStates.STORE);
			Serializable id = session.save(object);			
			flush(object);
			return id;
		} catch (HibernateException e) {
			logHibernateException(e);
			throw e;
		} finally {
			Bo2Session.setState(null);
			Debug.resetActiveModule();
		}
	}
	
	
	/**
	 * Wrapper around session.delete(object).
	 * 
	 * @param object
	 */
	void deleteEntity(Object object) {		
		try {
			Debug.setActiveModule(session);
			Bo2Session.setState(CrudStates.DELETE);
			session.delete(object);
			flush(object);
			hiberProv.unregister(object);
			Debug.resetActiveModule();
		} catch (HibernateException e) {
			logHibernateException(e);
			throw e;			
		} finally {
			Bo2Session.setState(null);
			Debug.resetActiveModule();
		}
	}
	
	/**
	 * Wrapper around session.merge(object).
	 * 
	 * @param object
	 * @return Returns the object returned by session.merge.
	 */
	Object mergeEntity(Object object) {		
		try {
			Debug.setActiveModule(session);
			Bo2Session.setState(CrudStates.UPDATE);
			Object ret = session.merge(object);
			flush(object);
			return ret;
		} catch (HibernateException e) {
			logHibernateException(e);
			throw e;			
		} finally {
			Bo2Session.setState(null);
			Debug.resetActiveModule();
		}
	}
	
	/**
	 * Logs an exception.
	 * 
	 * @param he
	 */
	protected void logHibernateException(HibernateException he) {
		@SuppressWarnings("nls")
		String msg = StringUtils.concat(
			he.getClass().getName(), ". Message: ",			
			Utils.notNull(he.getMessage(), EMPTY));
		logger.error(msg);
	}
	
	

}
