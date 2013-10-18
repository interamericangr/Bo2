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
package gr.interamerican.bo2.utils.handlers;

import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.Utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * The purpose of a {@link MethodInvocator} is to invoke a method of the
 * object that owns an EventHandler.
 */
public class MethodInvocator {

	/**
	 * Method of object to be invoked by this Bo2WicketBlock.
	 */
	transient Method method;
	
	/**
	 * Event handler using this method invocator.
	 */
	AbstractEventHandler<?> handler;
	
	/**
	 * method name. 
	 */
	String methodName;
	/**
	 * method owner
	 */
	Object owner;
	
	/**
	 * Creates a new MethodInvocator object. 
	 *
	 * @param handler
	 * @param methodName
	 * @param owner 
	 */
	public MethodInvocator(AbstractEventHandler<?> handler, String methodName,	Object owner) {
		super();
		this.handler = handler;
		this.methodName = methodName;
		this.owner = owner;
		setMethod();
	}	
	
	/**
	 * This method is necessary in order to support serialization.
	 * 
	 * The field <code>method</code> of this class is transient, so
	 * in case an instance of this class is read from a serialization
	 * stream, it is necessary to set the method field.  
	 */
	void setMethod() {
		Class<?> clazz = owner.getClass();
		this.method = ReflectionUtils.getMethodByUniqueName(methodName, clazz);		
		if (this.method==null) {
			@SuppressWarnings("nls")
			String msg = StringUtils.concat(
					"Class ", clazz.getName(), 
					" does not have any method with the name ", methodName);			
			throw new RuntimeException(msg);
		}
		this.method.setAccessible(true);
	}		
			
	/**
	 * Invokes the specified method on the owner object.
	 */
	public void invoke() {	
		try {
			Class<?>[] parameterTypes = method.getParameterTypes();			
			Object[] args = new Object[parameterTypes.length];
			for (int i = 0; i < parameterTypes.length; i++) {
				args[i] = handler.getHandlerParameter(parameterTypes[i]);				
			}
			method.invoke(owner, args);
		} catch (IllegalArgumentException ilarex) {
			handler.handleThrown(ilarex);		
		} catch (IllegalAccessException ilacex) {
			handler.handleThrown(ilacex);
		} catch (InvocationTargetException intaex) {
			Throwable cause = intaex.getCause();
			cause = Utils.notNull(cause, intaex.getTargetException());
			cause = Utils.notNull(cause, intaex);
			handler.handleThrown(cause);			
		}	
	}
	
	
		
	

}
