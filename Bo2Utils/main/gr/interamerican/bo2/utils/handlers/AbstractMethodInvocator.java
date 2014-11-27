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

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.attributes.SimpleCommand;
import gr.interamerican.bo2.utils.exc.ExceptionHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * The purpose of a {@link AbstractMethodInvocator} is to invoke a method of the
 * object that owns an EventHandler.
 */
public abstract class AbstractMethodInvocator implements SimpleCommand {
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(AbstractMethodInvocator.class.getName());
	
	/**
	 * key for MDC callback method
	 */
	private static final String MDC_CALLBACKMETHOD = "callback"; //$NON-NLS-1$


	
	/**
	 * method name. 
	 */
	protected String methodName;
	
	/**
	 * method owner
	 */
	protected Object owner;	

	/**
	 * method owner
	 */
	protected Class<?> ownerClass;
	
	/**
	 * Event handler using this method invocator.
	 */
	protected ExceptionHandler handler;
	
	/**
	 * Method of object to be invoked by this Bo2WicketBlock.
	 */
	transient protected Method method;
	
	
	
	

	
	/**
	 * Creates a new MethodInvocator object. 
	 *
	 * @param handler
	 * @param methodName
	 * @param owner
	 */
	public AbstractMethodInvocator(ExceptionHandler handler, String methodName,	Object owner) {
		super();
		this.handler = handler;
		this.methodName = methodName;
		this.owner = owner;
		this.ownerClass = owner.getClass();
		MethodLocator locator = new MethodLocator();
		this.method = locator.getByUniqueName(ownerClass, methodName);
		this.method.setAccessible(true);
	}	
	
	/**
	 * Creates a new MethodInvocator object. 
	 *
	 * @param handler
	 * @param methodName
	 * @param owner
	 * @param argumentTypes 
	 */
	public AbstractMethodInvocator
	(ExceptionHandler handler, String methodName, Object owner, Class<?>... argumentTypes) {
		super();
		this.handler = handler;
		this.methodName = methodName;
		this.owner = owner;
		this.ownerClass = owner.getClass();
		MethodLocator locator = new MethodLocator();		
		this.method = locator.get(ownerClass, methodName, argumentTypes);
	}	
	
	/**
	 * Creates a new MethodInvocator object for a static method of a class. 
	 *
	 * @param handler
	 * @param methodName
	 * @param clazz
	 */
	public AbstractMethodInvocator(ExceptionHandler handler, String methodName,	Class<?> clazz) {
		super();
		this.handler = handler;
		this.methodName = methodName;
		this.owner = null;
		this.ownerClass = clazz;
		MethodLocator locator = new MethodLocator();
		this.method = locator.getStaticByUniqueName(ownerClass, methodName);
		this.method.setAccessible(true);
	}	
	
	/**
	 * Creates a new MethodInvocator object. 
	 *
	 * @param handler
	 * @param methodName
	 * @param clazz
	 * @param argumentTypes 
	 */
	public AbstractMethodInvocator
	(ExceptionHandler handler, String methodName, Class<?> clazz, Class<?>... argumentTypes) {
		super();
		this.handler = handler;
		this.methodName = methodName;
		this.owner = null;
		this.ownerClass = clazz;
		MethodLocator locator = new MethodLocator();		
		this.method = locator.getStatic(ownerClass, methodName, argumentTypes);
	}
	
	
	/**
	 * Gets the arguments for the method invocation.
	 * 
	 * @return Returns the arguments for the method invocation.
	 */
	protected abstract Object[] getArguments();
			
	/**
	 * Invokes the specified method on the owner object.
	 * 
	 * @return Returns the object returned by the method. 
	 */
	public Object invoke() {	
		try {
			logInvocation();
			Object[] args = getArguments();
			return method.invoke(owner, args);
		} catch (IllegalArgumentException ilarex) {
			handler.handle(ilarex);		
		} catch (IllegalAccessException ilacex) {
			handler.handle(ilacex);
		} catch (InvocationTargetException intaex) {
			Throwable cause = intaex.getCause();
			cause = Utils.notNull(cause, intaex.getTargetException());
			cause = Utils.notNull(cause, intaex);
			handler.handle(cause);			
		} finally {
			MDC.remove(MDC_CALLBACKMETHOD);
		}
		return null;
	}

	@Override
	public void execute() {
		invoke();		
	}
	
	
	/**
	 * Logs the method invocation.
	 * 
	 */
	private void logInvocation(){			
		String callback = ownerClass.getSimpleName() + StringConstants.SHARP + methodName;
		MDC.put(MDC_CALLBACKMETHOD, callback);
		LOG.debug("executing callback"); //$NON-NLS-1$
	}

	
	
		
	

}
