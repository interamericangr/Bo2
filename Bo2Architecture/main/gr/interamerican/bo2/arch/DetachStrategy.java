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

import java.io.Serializable;

/**
 * DetachStrategy is a common interface for strategy objects
 * that undertake the management of detached objects.
 */
public interface DetachStrategy extends Serializable {
	
	/**
	 * Attaches an object. For environments with managed entities, such as ORM
	 * implementations, the semantics of this are as follows.
	 * <br/>
	 * Re-attaching a detached object includes the re-attaching of referenced
	 * (i.e. not owned) detached entities and collections of detached entities, 
	 * as well as of the object itself.
	 * <br/>
	 * Re-attaching a transient entity includes the re-attaching of referenced
	 * (i.e. not owned) detached entities and collections of detached entities.
	 * <br/>
	 * Owned entities and collections of entities are assumed to have appropriate
	 * cascade options enabled that re-attach them automatically once, their owner
	 * is re-attached.
	 * 
	 * @param object
	 *        Object to attach.
	 * @param provider 
	 *        Provider 
	 */
	public void reattach(Object object, Provider provider);
	
	/**
	 * Detaches the object.
	 * 
	 * @param object
	 *        Object to detach.
	 * @param provider 
	 *        Provider.
	 */
	public void detach(Object object, Provider provider);
	
	/**
	 * Marks the object as excluded from accidental saves due to
	 * any automation of the underlying persistence technology.
	 * 
	 * @param object
	 *        Object to mark.
	 * @param provider
	 *        Provider. 
	 */
	public void markExplicitSave(Object object, Provider provider);
	
}
