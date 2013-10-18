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

import java.io.Serializable;

/**
 * Common interface for the user of an application.
 * 
 * @param <A> Type of authorization identifier.
 */
public interface User<A> extends Serializable {
	
	/**
	 * Checks if this user is authorized for the action that is controlled 
	 * by an authorization control identified with the authorizationId.
	 * 
	 * @param authorizationId 
	 *        Id of the authorization controlling the action.
	 * 
	 * @return Returns true if the user is authorized for the action 
	 *         controlled by the specified is action.
	 */
	public boolean isAuthorized(A authorizationId);
	
	/**
	 * Gets the authorization token for the specified authorization id.
	 * 
	 * The authorization token is the value specified for the specific 
	 * authorization id. This method returns the String representation
	 * of this value.
	 * 
	 * @param authorizationId 
	 *        Id of the authorization controlling the action.
	 * 
	 * @return Returns the authorization token of this authorization id.
	 */
	public String getAuthorizationToken(A authorizationId);
	
	/**
	 * Gets the description of the user's authorization that is defined by
	 * the specified authorization id.
	 * 
	 * @param authorizationId 
	 *        Id of the authorization controlling the action.
	 * 
	 * @return Returns the description that matches to the authorization
	 *         of this user for the specified authorization id.
	 */
	public String authorizationDescription(A authorizationId);
	
	/**
	 * UserID of this user.
	 * 
	 * @return Returns the userID of this user.
	 */
	public String getUserId();
	
	/**
	 * Gets the user name.
	 * 
	 * @return Returns the name of the user.
	 */
	public String getUserName();
	
	/**
	 * Gets the user password.
	 * 
	 * @return Returns the password of the user.
	 */
	public String getUserPassword();
	
	/**
	 * Gets the email address of the user.
	 * 
	 * @return Returns  the email address of the user.
	 */
	public String getEmailAddress();

}
