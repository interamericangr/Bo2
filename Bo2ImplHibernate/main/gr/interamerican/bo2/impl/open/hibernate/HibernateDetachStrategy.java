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

import org.hibernate.Session;

/**
 * Default Hibernate detach strategy for working objects.
 */
public class HibernateDetachStrategy 
extends AbstractHibernateDetachStrategy {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Singleton instance.
	 */
	public static final HibernateDetachStrategy INSTANCE = new HibernateDetachStrategy();
	
	@Override
	protected void doReattach(Object object, Session session) {
		session.update(object);
	}
	
	/**
	 * Use singleton.
	 */
	private HibernateDetachStrategy() { 
		super();
	}

}
