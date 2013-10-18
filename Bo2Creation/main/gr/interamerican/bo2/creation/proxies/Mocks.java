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
package gr.interamerican.bo2.creation.proxies;


import java.lang.reflect.Proxy;

/**
 * Factory for fake objects. <br/>
 * 
 * Fakes are dynamic proxies that fake the behavior of the interfaces
 * they implement, with some predefined behavior.
 */
public class Mocks {
	
	/**
	 * Creates a new Proxy instance for the specified interface,
	 * using the specified InvocationHandler and the ClassLoader
	 * of this class.
	 * 
	 * @param <C>
	 * @param type
	 * @param handler
	 * 
	 * @return Returns a proxy instance.
	 */
	@SuppressWarnings("unchecked")
	static <C> C proxy(Class<C> type, AbstractFakeInvocationHandler handler) {
		Class<?>[] interfaces = {type};
		return (C) Proxy.newProxyInstance
			(Mocks.class.getClassLoader(), interfaces, handler);
	}
	
	/**
	 * Creates an implementation of the specified interface
	 * that has all of its methods returning null.
	 * 
	 * @param type
	 *        Interface to implement.
	 * @param <C>
	 *        Type of object.         
	 * 
	 * @return Returns a fake implementation of the interface.
	 */
	public static <C> C empty(Class<C> type) {
		AbstractFakeInvocationHandler handler = new EmptyInvocationHandler();
		return proxy(type, handler);
	}
	
	/**
	 * Creates an implementation of the specified interface
	 * that has all of its methods throw Throwable of the 
	 * specified type.
	 * 
	 * @param type
	 *        Interface to implement.
	 * @param <C>
	 *        Type of object.         
	 * @param throwable 
	 *        Type of Throwable being thrown by the methods
	 *        of the object.
	 * 
	 * @return Returns a fake implementation of the interface.
	 */
	public static <C> C throwing(Class<C> type, Class<? extends Throwable> throwable) {
		AbstractFakeInvocationHandler handler = new ThrowingInvocationHandler(throwable);
		return proxy(type, handler);		
	}
	
	/**
	 * Creates an implementation of the specified interface
	 * that has all of its methods returning the specified
	 * return value.
	 * 
	 * @param type
	 *        Interface to implement.
	 * @param <C>
	 *        Type of object.         
	 * 
	 * @return Returns a fake implementation of the interface.
	 */
	public static <C> C fake(Class<C> type) {
		AbstractFakeInvocationHandler handler = new DefaultValueInvocationHandler();
		return proxy(type, handler);
	}
	
	/**
	 * Creates an implementation of the specified interface
	 * that has all of its methods returning a default value.
	 * 
	 * @param type
	 *        Interface to implement.
	 * @param returnValue 
	 *        Value returned by the object's methods.       
	 *        
	 * @param <C>
	 *        Type of object.         
	 * 
	 * 
	 * @return Returns a fake implementation of the interface.
	 */
	public static <C> C empty(Class<C> type, Object returnValue) {
		EmptyInvocationHandler handler = new EmptyInvocationHandler(returnValue);
		return proxy(type, handler);
	}
	

}
