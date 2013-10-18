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
package gr.interamerican.bo2.arch.utils.ext;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.ext.Session;
import gr.interamerican.bo2.arch.ext.User;
import gr.interamerican.bo2.arch.utils.beans.SessionImpl;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit tests for {@link Bo2Session}.
 */
public class TestBo2Session {
	
	/**
	 * Unit test for set session.
	 */
	@Test
	public void testSetSession() {
		Session<Object, Object> session = 
			new SessionImpl<Object, Object>();
		Bo2Session.setSession(session);
		Assert.assertEquals(session, Bo2Session.tlSession.get());
		Bo2Session.setSession(null);
	}
	
	/**
	 * Unit test for set session.
	 */
	@Test
	public void testGetSession() {
		Session<Object, Object> session = 
			new SessionImpl<Object, Object>();
		Bo2Session.tlSession.set(session);
		Assert.assertEquals(session, Bo2Session.getSession());
		Bo2Session.setSession(null);
	}
	
	/**
	 * Unit test for set session.
	 */
	@Test
	public void testSetState() {
		Bo2State state = Mockito.mock(Bo2State.class);
		Bo2Session.setState(state);
		Assert.assertEquals(state, Bo2Session.tlState.get());
		Bo2Session.setState(null);
	}
	
	/**
	 * Unit test for set session.
	 */
	@Test
	public void testGetState() {
		Bo2State state = Mockito.mock(Bo2State.class);
		Bo2Session.tlState.set(state);		
		Assert.assertEquals(state, Bo2Session.getState());
		Bo2Session.setState(null);
	}
	
	/**
	 * Unit test for set session.
	 */
	@Test
	public void testSetProvider() {
		Provider provider = Mockito.mock(Provider.class);
		Bo2Session.setProvider(provider);
		Assert.assertEquals(provider, Bo2Session.tlProvider.get());
		Bo2Session.setProvider(null);
	}
	
	/**
	 * Unit test for set session.
	 */
	@Test
	public void testGetProvider() {
		Provider provider = Mockito.mock(Provider.class);
		Bo2Session.tlProvider.set(provider);		
		Assert.assertEquals(provider, Bo2Session.getProvider());
		Bo2Session.setProvider(null);
	}
	
	/**
	 * Unit test for set getUser().
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetUser() {
		User<Long> us = mock(User.class);		
		Session<Long, Long> session = mock(Session.class);
		when(session.getUser()).thenReturn(us);		
		Bo2Session.setSession(session);
		Assert.assertEquals(us, Bo2Session.getUser());
		Bo2Session.setSession(null);
	}
	
	/**
	 * Unit test for set getUser().
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetUserId() {
		String string = "ssss"; //$NON-NLS-1$
		User<Long> us = mock(User.class);
		when(us.getUserId()).thenReturn(string);		
		Session<Long, Long> session = mock(Session.class);
		when(session.getUser()).thenReturn(us);		
		Bo2Session.setSession(session);
		Assert.assertEquals(string, Bo2Session.getUserId());
		Bo2Session.setSession(null);
	}
	
	/**
	 * Unit test for set getUser().
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetLocale() {
		Locale locale = Locale.ENGLISH;
		Session<Long, Long> session = mock(Session.class);
		when(session.getLocale()).thenReturn(Locale.ENGLISH);		
		Bo2Session.setSession(session);
		Assert.assertEquals(locale, Bo2Session.getLocale());
		Bo2Session.setSession(null);
	}

}
