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

import org.hibernate.LockOptions;
import org.hibernate.Session;

/**
 * Hibernate detach strategy for objects we want to treat as
 * read-only.
 */
public class HibernateReadOnlyDetachStrategy 
extends AbstractHibernateDetachStrategy {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Singleton instance.
	 */
	public static HibernateReadOnlyDetachStrategy INSTANCE = 
		new HibernateReadOnlyDetachStrategy();
	
	@Override
	protected void doReattach(Object object, Session session) {
		session.buildLockRequest(LockOptions.NONE).lock(object);
	}
	
	/**
	 * Use singleton.
	 */
	private HibernateReadOnlyDetachStrategy() { 
		super();
	}

}
