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

import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.impl.open.po.PoUtils;

import java.util.Set;

/**
 * A {@link TransientEntitiesReattacher} is responsible for re-attaching managed entities
 * that are found in the graph of a transient object. This may happen for instance when a
 * managed entity is added in a transient's entities graph in a many-to-one node.
 * <br/>
 * Invoking for a non-transient instance is a no-op.
 * 
 * @param <P>
 *        Type of PersistentObject
 *        
 * @deprecated There is now a unified re-attacching mechanism for both transient and 
 *             detached instances.
 */
@Deprecated
public abstract class TransientEntitiesReattacher<P extends PersistentObject<?>> {
	
	/**
	 * Re-attaches to the current Hibernate Session any PersistentObjects found in
	 * <code>po's</code> graph that may be managed by Hibernate.
	 * 
	 * @param po
	 *        PersistentObject.
	 *        
	 * @param provider
	 *        
	 */
	public void reattach(P po, Provider provider) {
		if(!HibernateBo2Utils.isTransient(po)) {
			return;
		}
		for(PersistentObject<?> entity : findEntitiesToReattach(po)) {
			PoUtils.reattach(entity, provider);
		}
	}
	
	/**
	 * A concrete implementation of this method is responsible for providing a 
	 * Set of PersistentObjects found in the <code>po's</code> graph that may
	 * be, in fact, managed by Hibernate.
	 * 
	 * @param po
	 *        PersistentObject whose object graph is searched.
	 *        
	 * @return A Set of entities that might be managed and need to be re-attached. 
	 */
	protected abstract Set<PersistentObject<?>> findEntitiesToReattach(P po);	

}
