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
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
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
	 * Method of object to be invoked by this Bo2WicketBlock.
	 */
	transient protected Method method;
	
	/**
	 * Event handler using this method invocator.
	 */
	protected ExceptionHandler handler;
	
	/**
	 * method name. 
	 */
	protected String methodName;
	/**
	 * method owner
	 */
	protected Object owner;
	
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
		setMethod();
	}	
	
	/**
	 * This method is necessary in order to support serialization.
	 * 
	 * The field <code>method</code> of this class is transient, so
	 * in case an instance of this class is read from a serialization
	 * stream, it is necessary to set the method field.  
	 */
	protected void setMethod() {
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
			Object[] args = getArguments();
			String callback = owner.getClass().getSimpleName() + StringConstants.SHARP + methodName;
			MDC.put(MDC_CALLBACKMETHOD, callback);
			LOG.debug("executing callback"); //$NON-NLS-1$
			
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
	
	
		
	

}
