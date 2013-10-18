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
package gr.interamerican.bo2.impl.open.hibernate.refreshmodes;

import java.io.Serializable;

import org.hibernate.Session;

/**
 * Fast version of GetAndRefresh that expects that the object is 
 * already persistent, so there is no need to check if the session
 * returned null for the specified id.
 */
public class FastGetAndRefresh implements GetFromSession {
	
	/**
	 * Instance.
	 */
	public static final FastGetAndRefresh INSTANCE = new FastGetAndRefresh(); 
	
	public Object get(Session session, Serializable id, Class<?> persistentClass) {
		Object o = session.get(persistentClass, id);		
		session.refresh(o);		
		return o;
	}

}
