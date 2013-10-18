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

import gr.interamerican.bo2.arch.Operation;

import java.util.List;

/**
 * Login operation.
 * 
 * @param <A> 
 *        Type of authorization id.
 */
public interface LoginOperation<A> 
extends Operation {
	/**
	 * Sets the user Id of the user being authenticated.
	 * 
	 * @param userid
	 *        The user id.
	 */
	public void setUserId(String userid);
	
	/**
	 * Sets the password of the user being authenticated.
	 * 
	 * @param password
	 *        Password used for authentication.
	 */
	public void setPassword(String password);
	
	/**
	 * Gets the authenticated user.
	 * 
	 * @return Returns an authenticated user.
	 */
	public User<A> getUser();
	
	/**
	 * Gets a list with messages to the user, after a successful login.
	 * 
	 * @return Returns a list with messages.
	 */
	public List<String> getMessages();

}
