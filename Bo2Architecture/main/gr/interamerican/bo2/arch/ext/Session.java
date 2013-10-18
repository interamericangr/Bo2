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
package gr.interamerican.bo2.arch.ext;

import java.util.Locale;

/**
 * Interface for the session of a user in an application.
 * 
 * @param <A> Type of authorization id.
 * @param <L> Type of selected language.
 * 
 */
public interface Session<A, L> {
	
	/**
	 * Gets the user.
	 * 
	 * @return Returns the user.
	 */
	public User<A> getUser();
	
	/**
	 * Gets the id of the language used in this session.
	 * 
	 * @return Returns the session language id.
	 */
	public L getLanguageId();
	
	/**
	 * Sets the language id for the session.
	 * 
	 * @param languageId
	 */
	public void setLanguageId(L languageId);
	
	/**
	 * @return Returns the Session {@link Locale}.
	 */
	public Locale getLocale();
	
	/**
	 * Checks if this session is authorized for the action that is controlled 
	 * by an authorization control identified with the authorizationId.
	 * 
	 * This method can delegate to the method of the user, but it exists
	 * also in the session, since a session could exist without a user.
	 * 
	 * @param authorizationId 
	 *        Id of the authorization controlling the action.
	 * 
	 * @return Returns true if the user is authorized for the action 
	 *         controlled by the specified is action.
	 */
	public boolean isAuthorized(A authorizationId); 
	
	/**
	 * Indicates if the user has been logged in.
	 * 
	 * @return Returns true if the user has logged in, otherwise returns false.
	 */
	public boolean isLogin();
	

}
