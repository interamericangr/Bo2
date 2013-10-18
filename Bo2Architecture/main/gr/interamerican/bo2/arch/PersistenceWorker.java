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
package gr.interamerican.bo2.arch;

/**
 * Persistence utility of a class. 
 * 
 * The {@link PersistenceWorker} is a {@link Worker} and a 
 * {@link PersistenceUtility} that operates on a type of 
 * {@link PersistentObject}. PersistenceWorker is a PersistenceUtility 
 * with more strict implementation rules.
 * 
 * @param <PO> Class that is persisted by the PersistenceWorker. 
 *   
 */
public interface PersistenceWorker<PO extends PersistentObject<?>> 
extends Worker, PersistenceUtility<PO> {
	
	/**
	 * Gets the {@link DetachStrategy} associated with this 
	 * {@link PersistenceWorker}.
	 *  
	 * The {@link DetachStrategy} is needed in order to re-attach
	 * a {@link PersistentObject} to its {@link Provider}. This is
	 * necessary for persistent objects that need the provider, like
	 * Hibernate disconnected objects.
	 *  
	 * @return Returns the {@link DetachStrategy} associated with
	 *         this {@link PersistenceWorker}. 
	 */
	public DetachStrategy getDetachStrategy();
	
}
