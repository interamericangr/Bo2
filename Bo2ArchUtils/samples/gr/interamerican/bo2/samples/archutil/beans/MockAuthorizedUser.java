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

import java.util.Set;

/**
 * Mock user, always authorized.
 *
 * @param <A> the generic type
 */
@SuppressWarnings("nls")
public class MockAuthorizedUser<A>  
extends AbstractMockUser<A>{

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new MockAuthorizedUser object. 
	 *
	 * @param userId the user id
	 */
	public MockAuthorizedUser(String userId) {
		super(userId);
	}

	@Override
	public boolean isAuthorized(A authorizationId) {		
		return true;
	}	

	@Override
	public String getAuthorizationToken(A authorizationId) {		
		return "1";
	}

	@Override
	public String getEmailAddress() {	
		return getUserId() + "@bo2.org";
	}

	@Override
	public Set<String> getAuthorizationValues(A authorizationId) {
		return null;
	}
}