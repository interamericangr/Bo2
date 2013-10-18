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

import gr.interamerican.bo2.arch.DetachStrategy;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.impl.open.hibernate.HibernateDetachStrategy;
import gr.interamerican.bo2.impl.open.hibernate.HibernateReadOnlyDetachStrategy;
import gr.interamerican.bo2.impl.open.po.PoUtils;
import gr.interamerican.bo2.utils.adapters.Modification;

/**
 * Initializes a hibernate proxy.
 */
public class SetReadDetachStrategy implements Modification<PersistentObject<?>>{
	
	/**
	 * Singleton.
	 */
	public static final SetReadDetachStrategy INSTANCE = new SetReadDetachStrategy();

	public PersistentObject<?> execute(PersistentObject<?> a) {
		DetachStrategy d = PoUtils.getDetachStrategy(a);
		if ((d!=null) && (d instanceof HibernateDetachStrategy)) {
			PoUtils.setDetachStrategy(a, HibernateReadOnlyDetachStrategy.INSTANCE);
		}
		return a;			
	}
	
}
