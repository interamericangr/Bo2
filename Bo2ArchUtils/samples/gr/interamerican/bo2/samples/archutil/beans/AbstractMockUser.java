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
package gr.interamerican.bo2.samples.archutil.beans;

import gr.interamerican.bo2.arch.ext.User;

/**
 * Abstract mock user.
 * 
 * @param <A> 
 */
@SuppressWarnings("nls")
public abstract class AbstractMockUser<A> 
implements User<A> {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * User Id
	 */
	String userId;
	
	/**
	 * Creates a new AbstractMockUser object. 
	 *
	 * @param userId
	 */
	public AbstractMockUser(String userId) {
		super();
		this.userId = userId;
	}
	
	public String authorizationDescription(A authorizationId) {
		return "Authorization description";
	}

	public String getUserId() {		
		return userId;
	}
	
	public String getUserName() {
		return "Mock";
	}
	
	public String getUserPassword() {
		return "MockPass123!";
	}

}
