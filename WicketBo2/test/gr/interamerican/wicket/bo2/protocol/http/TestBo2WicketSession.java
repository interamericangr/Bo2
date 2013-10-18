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
package gr.interamerican.wicket.bo2.protocol.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gr.interamerican.bo2.arch.ext.User;

import java.util.Locale;

import org.apache.wicket.Request;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;



/**
 * 
 *  
 */
public class TestBo2WicketSession {
	
	/**
	 * @return request.
	 */
	Request mockRequest() {
		Request request = mock(Request.class);
		when(request.getLocale()).thenReturn(Locale.ENGLISH);
		return request;
	}

	
	


	/**
	 * System test. Creates a real Bo2WicketSession.
	 * 
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testBo2WicketSession(){		
		WicketTester wicketTester = new WicketTester();	
		wicketTester.startPage(Bo2WicketSessionTestPage.class);
		wicketTester.assertRenderedPage(Bo2WicketSessionTestPage.class);	
		wicketTester.startPage(Bo2WicketSessionTestPage.class);
		wicketTester.assertRenderedPage(Bo2WicketSessionTestPage.class);		
		DropDownChoice ddc = (DropDownChoice)wicketTester.
		getComponentFromLastRenderedPage("bo2entriesDrowDown"); //$NON-NLS-1$
		DropDownChoice ddc1 = (DropDownChoice)wicketTester.
		getComponentFromLastRenderedPage("bo2entriesDrowDown1"); //$NON-NLS-1$
		DropDownChoice ddc2 = (DropDownChoice)wicketTester.
		getComponentFromLastRenderedPage("bo2entryOwnerDropDown"); //$NON-NLS-1$
		DropDownChoice ddc3 = (DropDownChoice)wicketTester.
		getComponentFromLastRenderedPage("bo2entryOwnerDropDown1"); //$NON-NLS-1$
		assertEquals(0,ddc.size());
		assertEquals(0,ddc1.size());
		assertEquals(0,ddc2.size());
		assertEquals(0,ddc3.size());			
	}
	
	/**
	 * Test for the constructor.
	 */	
	@Test
	public void testConstructor_oneArg(){
		Request request = mockRequest();		
		Bo2WicketSession<Object, Object> session = 
			new Bo2WicketSession<Object, Object>(request);
		assertNull(session.languageId);
		assertNull(session.user);
	}
	
	/**
	 * Test for the constructor.
	 */	
	@Test
	public void testConstructor_twoArgs(){
		Object languageId = new Object();
		Request request = mockRequest();	
		Bo2WicketSession<Object, Object> session = 
			new Bo2WicketSession<Object, Object>(request,languageId);
		assertEquals(languageId, session.languageId);
		assertNull(session.user);
	}
	
	/**
	 * Test for setUser(user).
	 */	
	@Test
	public void testSetUser(){
		@SuppressWarnings("unchecked") User<Object> user = mock(User.class);
		Bo2WicketSession<Object, Object> session = 
			new Bo2WicketSession<Object, Object>(mockRequest());
		session.setUser(user);
		assertEquals(user, session.user);
	}
	
	/**
	 * Test for getUser().
	 */	
	@Test
	public void testGetUser(){
		@SuppressWarnings("unchecked") User<Object> user = mock(User.class);
		Bo2WicketSession<Object, Object> session = 
			new Bo2WicketSession<Object, Object>(mockRequest());
		session.user = user;
		assertEquals(user, session.getUser());
	}
	
	/**
	 * Test for isLogin().
	 */	
	@Test
	public void testIsLogin(){
		@SuppressWarnings("unchecked") User<Object> user = mock(User.class);
		Bo2WicketSession<Object, Object> session = 
			new Bo2WicketSession<Object, Object>(mockRequest());
		assertFalse(session.isLogin());
		session.user = user;
		assertTrue(session.isLogin());
	}
	
	/**
	 * Test for isAuthorized().
	 */	
	@Test
	public void testIsAuthorized_noLogin(){		
		Bo2WicketSession<Object, Object> session = 
			new Bo2WicketSession<Object, Object>(mockRequest());
		Object authId = new Object();		
		assertFalse(session.isAuthorized(authId));
	}

	/**
	 * Test for isAuthorized().
	 */	
	@Test
	public void testIsAuthorized_isLogin(){
		Object authId = new Object();	
		@SuppressWarnings("unchecked") User<Object> user = mock(User.class);
		when(user.isAuthorized(authId)).thenReturn(true);
		Bo2WicketSession<Object, Object> session = 
			new Bo2WicketSession<Object, Object>(mockRequest());
		session.user = user;
		assertTrue(session.isAuthorized(authId));
	}
	
	

	
	

}
