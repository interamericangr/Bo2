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
import gr.interamerican.bo2.impl.open.hibernate.adapters.SetReadDetachStrategy;
import gr.interamerican.bo2.impl.open.hibernate.adapters.Unproxy;
import gr.interamerican.bo2.impl.open.hibernate.conditions.IsInitialized;
import gr.interamerican.bo2.impl.open.po.PoConditionChecker;
import gr.interamerican.bo2.impl.open.po.PoCopier;
import gr.interamerican.bo2.impl.open.po.PoFetcher;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.adapters.Modification;
import gr.interamerican.bo2.utils.conditions.Not;

/**
 * Initialization of environment.
 */
public class Bo2ImplHibernateInitializer {
	/**
	 * Initialization.
	 */
	public static void initialize() {
		PoCopier.get().setOnCopyChildPo(Unproxy.INSTANCE);
		PoCopier.get().setOnCopyPo(Unproxy.INSTANCE);
		PoConditionChecker.CONDITIONS_TO_SKIP_FIXCHILD.add(new Not<Object>(new IsInitialized()));
		
		@SuppressWarnings("rawtypes")
		Modification<PersistentObject> mod = Utils.cast(SetReadDetachStrategy.INSTANCE);		
		PoFetcher.registerModification(PersistentObject.class, mod);	
	}

}
