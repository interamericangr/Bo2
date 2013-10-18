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

import gr.interamerican.bo2.arch.ext.User;
import gr.interamerican.bo2.samples.archutil.beans.MockAuthorizedUser;
import gr.interamerican.bo2.samples.archutil.beans.MockNotAuthorizedUser;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for SessionImpl.
 */
public class TestSessionImpl {
	
	/**
	 * Unit test for setUser(user)
	 */
	@Test
	public void testSetUser() {
		SessionImpl<Object, Object> session = 
			new SessionImpl<Object, Object>();
		User<Object> user = new MockAuthorizedUser<Object>("X"); //$NON-NLS-1$
		session.setUser(user);
		Assert.assertEquals(user, session.user);		
	}
	
	/**
	 * Unit test for getUser()
	 */
	@Test
	public void testGetUser() {
		SessionImpl<Object, Object> session = 
			new SessionImpl<Object, Object>();
		User<Object> user = new MockAuthorizedUser<Object>("X"); //$NON-NLS-1$
		session.user = user;		
		Assert.assertEquals(session.user, session.getUser());		
	}
	
	/**
	 * Unit test for setLanguageId(languageId)
	 */
	@Test
	public void testSetLanguageId() {
		SessionImpl<Object, Object> session = 
			new SessionImpl<Object, Object>();
		Object languageId = new Object();
		session.setLanguageId(languageId);		
		Assert.assertEquals(languageId, session.languageId);		
	}
	
	/**
	 * Unit test for getLanguageId()
	 */
	@Test
	public void testGetLanguageId() {
		SessionImpl<Object, Object> session = 
			new SessionImpl<Object, Object>();
		Object languageId = new Object();
		session.languageId = languageId;		
		Assert.assertEquals(session.languageId, session.getLanguageId());		
	}
	
	/**
	 * Unit test for isLogin()
	 */
	@Test
	public void testIsLogin() {
		SessionImpl<Object, Object> session = 
			new SessionImpl<Object, Object>();
		Assert.assertFalse(session.isLogin());
		User<Object> user = new MockAuthorizedUser<Object>("X"); //$NON-NLS-1$
		session.setUser(user);
		Assert.assertTrue(session.isLogin());		
	}
	
	/**
	 * Unit test for isLogin()
	 */
	@SuppressWarnings("nls")
	@Test
	public void testIsAuthorized() {
		SessionImpl<Object, Object> session = 
			new SessionImpl<Object, Object>();
		Assert.assertFalse(session.isLogin());
		User<Object> user = new MockAuthorizedUser<Object>("X");
		session.setUser(user);
		Assert.assertTrue(session.isAuthorized(new Object()));
		
		user = new MockNotAuthorizedUser<Object>("X");
		session.setUser(user);
		Assert.assertFalse(session.isAuthorized(new Object()));		

	}

}
