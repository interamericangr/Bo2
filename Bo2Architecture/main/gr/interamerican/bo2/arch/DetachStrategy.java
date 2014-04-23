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

import gr.interamerican.bo2.utils.annotations.Child;

import java.io.Serializable;

/**
 * DetachStrategy is a common interface for strategy objects
 * that undertake the management of detached objects.
 */
public interface DetachStrategy extends Serializable {
	
	/**
	 * Re-attaches an object. 
	 * <br/><br/>
	 * For environments with managed entities, such as ORM implementations, the 
	 * semantics of this are as follows:
	 * <br/><br/>
	 * <li>Re-attaching a detached object includes the re-attaching of referenced
	 * (i.e. not owned) detached entities and collections of detached entities, 
	 * as well as of the object itself. These referenced detached entities are
	 * re-attached as read-only, if this is supported. Transient entities are 
	 * not attached to the session and are manually detached in case they were 
	 * attached by side-effect (because of the existence of cascade options).
	 * <br/><br/>
	 * <li>Re-attaching a transient entities is not supported. Transient entities
	 * should become managed by a normal store(object) call or by updating a
	 * managed entity to which they belong. The algorithm that assesses whether
	 * an entity is detached or transient may leverage the existence of an optimistic
	 * locking version or timestamp existence.
	 * <br/><br/>
	 * <li>Owned entities and collections of entities are assumed to have appropriate
	 * cascade options enabled that re-attach them automatically once their owner
	 * is re-attached.
	 * <br/><br/>
	 * <li>The algorithm that distinguishes owned and referenced entities may leverage
	 * the existence of {@link Child} annotations for owned entity associations. 
	 * 
	 * @see ModificationRecord
	 * @see PersistentObject
	 * @see Child
	 * 
	 * @param object
	 *        Object to re-attach.
	 * @param provider 
	 *        Provider 
	 */
	public void reattach(Object object, Provider provider);
	
	/**
	 * Reattach that will not detach transient objects attached by cascade.
	 * In the unit of work that this is called, it is mandatory to perform
	 * a database update.
	 * 
	 * @see #reattach(Object, Provider)
	 * 
	 * @param object
	 * @param provider
	 */
	public void reattachForUpdate(Object object, Provider provider);
	
	/**
	 * Detaches the object.
	 * <br/><br/>
	 * For environments with managed entities, such as ORM implementations, the 
	 * semantics of this are as follows:
	 * <br/><br/>
	 * <li>A previous call to {@link #reattach(Object, Provider)} or {@link #reattachForUpdate(Object, Provider)}
	 * has been performed.
	 * <br/><br/>
	 * <li>Side-effects of this call on the object are reset here.
	 * <br/><br/>
	 * <li>The goal is to leave the state of the object as if no reattach call was performed.
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
