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

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.ext.Session;
import gr.interamerican.bo2.arch.ext.User;

import java.util.Locale;

/**
 * Bo2Session keeps a {@link Session} in a ThreadLocal variable.
 * 
 * In the environment of a stateful web application environment, 
 * the application state is connected with the HttpSession. The
 * web application is responsible to set the session at the beginning 
 * of each request processing cycle using the <code>setSession(session)</code>.
 * <br/>
 * In the environment of a batch program, the program is responsible to 
 * create a session and set it. <br/>
 * In a unit test, the test setup should create and set a session, if
 * this is necessary. <br/>
 */
public class Bo2Session {
	
	/**
	 * Threadlocal session.
	 */
	static ThreadLocal<Session<?, ?>> tlSession = 
		new ThreadLocal<Session<?,?>>();
	
	/**
	 * Threadlocal state.
	 */
	static ThreadLocal<Bo2State> tlState =  new ThreadLocal<Bo2State>();
	
	/**
	 * Threadlocal provider.
	 */
	static ThreadLocal<Provider> tlProvider =  new ThreadLocal<Provider>();

	/**
	 * Gets the session.
	 * 
	 * @param <A> 
	 * @param <L> 
	 *
	 * @return Returns the session
	 */
	@SuppressWarnings("unchecked")
	public static <A,L> Session<A,L> getSession() {
		return (Session<A,L>) tlSession.get();
	}

	/**
	 * Assigns a new value to the session.
	 *
	 * @param session the session to set
	 */
	public static void setSession(Session<?,?> session) {		
		tlSession.set(session);
	}
	
	/**
	 * Gets the user of the current session.
	 * 
	 * @param <A> 
	 *
	 * @return Returns the session
	 */
	@SuppressWarnings("unchecked")
	public static <A> User<A> getUser() {
		if(tlSession.get()!=null) {
			return (User<A>) tlSession.get().getUser();
		}
		return null;
	}
	
	/**
	 * Gets the userId of the current session's user.
	 * 
	 * @return Returns the user id.
	 */
	public static String getUserId() {
		User<?> user = getUser();
		if (user==null) {
			return null;
		}
		return user.getUserId();
	}
	
	
	/**
	 * @return Returns the Session {@link Locale}. 
	 */
	public static Locale getLocale() {
		if(tlSession.get()!=null) {
			return tlSession.get().getLocale();
		}
		return null;
	}
	
	/**
	 * Gets the current state.
	 * 
	 * @return Returns the state.
	 */
	public static Bo2State getState() {
		return tlState.get();
	}
	
	/**
	 * Sets the current state.
	 * 
	 * @param state
	 */
	public static void setState(Bo2State state) {
		tlState.set(state);
	}
	
	/**
	 * Sets the current provider.
	 * 
	 * @param provider
	 */
	public static void setProvider(Provider provider) {
		tlProvider.set(provider);
	}
	
	/**
	 * Gets the current provider.
	 * 
	 * @return Returns the current provider. 
	 */
	public static Provider getProvider() {
		return tlProvider.get();
	}
	
	

}
