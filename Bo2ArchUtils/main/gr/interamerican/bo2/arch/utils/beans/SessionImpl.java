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
package gr.interamerican.bo2.arch.utils.beans;

import gr.interamerican.bo2.arch.ext.Session;
import gr.interamerican.bo2.arch.ext.User;

import java.util.Locale;

/**
 * A User session.
 * 
 * @param <A> 
 *        Type of authorization Id.
 * @param <L> 
 *        Type of language Id.
 * 
 */
public class SessionImpl<A, L> 
implements Session <A, L> {
	/**
	 * User
	 */
	User<A> user;
	/**
	 * Language Id.
	 */
	L languageId;
	
	/**
	 * Locale.
	 */
	Locale locale;

	
	public User<A> getUser() {
		return user;
	}

	/**
	 * Sets the current user.
	 * 
	 * @param user
	 */
	public void setUser(User<A> user) {
		this.user = user;
	} 
	
	public L getLanguageId() {		
		return languageId;
	}
	public void setLanguageId(L languageId) {
		this.languageId = languageId;		
	}
	
	public boolean isAuthorized(A authorizationId) {		
		return user.isAuthorized(authorizationId);
	}
	
	public boolean isLogin() {
		return this.user!=null;
	}

	public Locale getLocale() {
		return locale;
	}

}
