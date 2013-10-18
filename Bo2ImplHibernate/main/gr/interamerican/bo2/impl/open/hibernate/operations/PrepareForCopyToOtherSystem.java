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
package gr.interamerican.bo2.impl.open.hibernate.operations;

import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.hibernate.HibernateSessionProvider;
import gr.interamerican.bo2.impl.open.workers.AbstractPoOperation;

import org.hibernate.Session;

/**
 * Prepares a {@link PersistentObject} to be copied to another system.
 * The {@link Session} of origin is closed and the PersistentObject is
 * attached to the target Session.
 * 
 * @param <P>
 *        Type of po 
 */
public class PrepareForCopyToOtherSystem<P extends PersistentObject<?>> 
extends AbstractPoOperation<P> {
	
	/**
	 * Name of manager from whom the po is read.
	 */
	String fromManager;

	/**
	 * Name of manager to whom the po is stored.
	 */
	String toManager;
	
	/**
	 * Creates a new PrepareForCopyToOtherSystem object. 
	 *
	 * @param fromManager
	 * @param toManager
	 */
	public PrepareForCopyToOtherSystem(String fromManager, String toManager) {
		super();
		this.fromManager = fromManager;
		this.toManager = toManager;
	}

	@Override
	public void execute() throws LogicException, DataException {
		HibernateSessionProvider fromHsp;
		try {
			fromHsp = getProvider().getResource(fromManager, HibernateSessionProvider.class);
		} catch (InitializationException e) {
			throw new DataException(e);
		}
		fromHsp.getHibernateSession().close();
	}

}
