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
package gr.interamerican.bo2.impl.open.hibernate.adapters;

import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.utils.adapters.Modification;

import org.hibernate.proxy.HibernateProxy;

/**
 * Initializes a hibernate proxy.
 * <br/>
 * {@link HibernateProxy} implementations created on runtime by hibernate
 * do not delegate equals() and hashCode() to the wrapped entity instance.
 * For this reason, in scenarios where the entities override equals() and
 * hashCode() we have to unwrap the instance manually (e.g. when working 
 * with Collections).
 * <br/>
 * However, note that having to use this something fishy is happening. Most
 * likely there exist simultaneously a HibernateProxy AND an entity instance
 * with the same database id in the scope of your unit of work. In a scenario
 * with detached entities, if everything is reattached properly to the next
 * hibernate Session at the beginning of the unit of work, there should be no
 * problem. 
 */
public class Unproxy implements Modification<PersistentObject<?>> {
	
	/**
	 * Singleton.
	 */
	public static final Unproxy INSTANCE = new Unproxy();

	public PersistentObject<?> execute(PersistentObject<?> a) {
		if (a instanceof HibernateProxy) {
			return DoUnproxy.INSTANCE.execute(a);		
		}
		return a;
	}
	
	
}
