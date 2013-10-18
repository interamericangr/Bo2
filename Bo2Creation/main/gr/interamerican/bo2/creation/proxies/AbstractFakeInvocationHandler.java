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

import static gr.interamerican.bo2.creation.util.MethodsUtilities.isEquals;
import static gr.interamerican.bo2.creation.util.MethodsUtilities.isHashCode;
import static gr.interamerican.bo2.creation.util.MethodsUtilities.isToString;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Base class for {@link InvocationHandler}s that fake the method invocations
 * that they do.
 * 
 * Faking a method invocations has the meaning that the method is not really
 * invoked on an object, but the method invocation has a predefined result
 * that depends on the implementation of the sub-class of AbstractFakeHandler.
 * The AbstractFakeInvocationHandler fakes all methods, except from 
 * <code>equals(object)</code>, <code>hashCode()</code> and <code>toString()</code>.
 * These three methods are delegated to the AbstractFakeInvocationHandler
 * itself. Therefore each dynamic proxy that has an AbstractFakeInvocationHandler,
 * must have its own AbstractFakeInvocationHandler. Otherwise, equals, hashCode
 * and toString will not have the expected behavior. 
 */
public abstract class AbstractFakeInvocationHandler 
implements InvocationHandler {
	
	/**
	 * Invocation history.
	 */
	private ArrayList<MethodInvocation> invocations = 
		new ArrayList<MethodInvocation>();
	

	public Object invoke(Object proxy, Method method, Object[] args) 
	throws Throwable {
		
		if (isEquals(method)) {
			Boolean returnValue = (proxy==args[0]);
			return success(method, args, returnValue);			
		}
		if (isHashCode(method)) {			
			int returnValue = this.hashCode();
			return success(method, args, returnValue);
		}
		if (isToString(method)) {			
			String returnValue = this.toString();
			return success(method, args, returnValue);
		}
		try {
			Object returnValue = doInvoke(proxy, method, args);
			return success(method, args, returnValue);
		} catch (Throwable thrown) {
			throw failure(method, args, thrown);
		}
	}
	
	/**
	 * Handles the specified method invocation.
	 * 
	 * @param proxy
	 * @param method
	 * @param args
	 * 
	 * @return Returns the return object of the method.
	 * @throws Throwable
	 */
	protected abstract Object doInvoke(Object proxy, Method method, Object[] args) 
	throws Throwable;
	
	/**
	 * Log  a successful method invocation.
	 * @param method
	 * @param args
	 * @param returnValue
	 * 
	 * @return Returns the returnValue.
	 */
	private Object success(Method method, Object[] args, Object returnValue) {
		MethodInvocation invocation =  MethodInvocation.successful(method, args, returnValue);
		invocations.add(invocation);
		return returnValue;
	}
	
	/**
	 * Log  a successful method invocation.
	 * @param method
	 * @param args
	 * @param thrown
	 * 
	 * @return Returns the thrown.
	 */
	private Throwable failure(Method method, Object[] args, Throwable thrown) {
		MethodInvocation invocation =  MethodInvocation.thrown(method, args, thrown);
		invocations.add(invocation);
		return thrown;
	}
	
}
