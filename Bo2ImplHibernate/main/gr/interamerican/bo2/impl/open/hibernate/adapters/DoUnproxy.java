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
import gr.interamerican.bo2.impl.open.hibernate.HibernateDetachStrategy;
import gr.interamerican.bo2.impl.open.po.PoUtils;
import gr.interamerican.bo2.utils.adapters.Modification;

import org.hibernate.proxy.HibernateProxy;

/**
 * Initializes a hibernate proxy.
 */
public class DoUnproxy implements Modification<PersistentObject<?>>{
	
	/**
	 * Singleton.
	 */
	public static final DoUnproxy INSTANCE = new DoUnproxy();

	public PersistentObject<?> execute(PersistentObject<?> a) {
		HibernateProxy proxy = (HibernateProxy) a;
		Object unproxied = proxy.getHibernateLazyInitializer().getImplementation();
		PoUtils.setDetachStrategy(unproxied, HibernateDetachStrategy.INSTANCE);
		return (PersistentObject<?>) unproxied;						
	}
	
	
}
