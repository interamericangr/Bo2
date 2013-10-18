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
/**
 * 
 */
package gr.interamerican.bo2.impl.open.hibernate;

import gr.interamerican.bo2.arch.ResourceWrapper;

import org.hibernate.Session;

/**
 * Provider of a Hibernate session.
 * 
 * The {@link HibernateSessionProvider} also provides a facade that hides
 * some session operations. 
 *
 */
public interface HibernateSessionProvider 
extends ResourceWrapper {
	
	/**
	 * Provides a ready to be used Hibernate Session.
	 * 
	 * @return Returns a Hibernate session.
	 */
	public Session getHibernateSession();
	
	/**
	 * Flushes changes that affect the specific object.
	 * 
	 * The behavior of this method depends on the {@link FlushStrategy} of this
	 * HibernateSesssionProvider. 
	 * 
	 * @param object
	 * 
	 * @see #setFlushStrategy(FlushStrategy)
	 * @see #setExcluded(Object)
	 * @see #setNotExcluded(Object)
	 */
	public void flush(Object object);
	
	/**
	 * Registers an object that is managed by this provider's session.
	 * 
	 * @param object
	 *        Object to add to the register.
	 *        
	 * @see #unregister(Object)
	 */
	public void register(Object object);
	
	/**
	 * Deletes from this provider's register an object. <br/>
	 * 
	 * This method unregisters object that had previously been registered with
	 * <code>register(object)</code>. The most common use for this method is
	 * after the deletion of persistent objects.
	 * 
	 * @param object
	 *        Object to remove from the register.
	 *        
	 * @see #register(Object)
	 */
	public void unregister(Object object);
	
	/**
	 * Marks an object as being excluded from flushing.
	 * 
	 * This method is meaningless unless this provider's flushing policy 
	 * is set to {@link FlushStrategy#EXCLUDING}.
	 * 
	 * @param object
	 *        Object to exclude from this provider's session flushing.

	 * @see #setNotExcluded(Object)
	 */
	public void setExcluded(Object object);
	
	/**
	 * Cancels the exclusion of an object from the session flushing.
	 * 
	 * This method should be used for objects that have been marked for
	 * exclusion from flush, with <code>setExcluded(object)</code>. Calling
	 * this method makes sense only when this provider's flushing policy 
	 * is set to {@link FlushStrategy#EXCLUDING}.
	 * 
	 * @param object
	 *        Object to exclude from this provider's session flushing.
	 *        
	 * @see #setExcluded(Object)       
	 */
	public void setNotExcluded(Object object);
	
	/**
	 * Sets the flush strategy of this provider.
	 * 
	 * The flush strategy specifies the behavior of this provider's
	 * <code>flush(object)</code> method. Setting the flushing strategy
	 * should be done with caution in order not to degrade performance.
	 * 
	 * @param flushStrategy
	 *        The flushStrategy to set.       
	 */
	public void setFlushStrategy (FlushStrategy flushStrategy);
	
}
