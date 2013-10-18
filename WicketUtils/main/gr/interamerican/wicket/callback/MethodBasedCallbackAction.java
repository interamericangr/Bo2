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
package gr.interamerican.wicket.callback;

import gr.interamerican.bo2.utils.handlers.EventHandlerComponent;
import gr.interamerican.bo2.utils.handlers.MethodInvocator;
import gr.interamerican.bo2.utils.handlers.ThrowingExceptionHandler;

/**
 * A {@link MethodBasedCallbackAction} is a Bo2WicketBlock that 
 * executes a method of its owner object.
 */
public class MethodBasedCallbackAction 
extends AbstractCommandCallback {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Event handler.
	 */
	EventHandlerComponent<Object> handler;
	
	/**
	 * Method invocator.
	 */
	MethodInvocator mi;
	
	/**
	 * method name. 
	 */
	String methodName;
	/**
	 * method owner.
	 */
	Object owner;
			
	/**
	 * Creates a new MethodBasedBo2WicketBlock object. 
	 *
	 * @param methodName
	 * @param owner
	 */
	public MethodBasedCallbackAction(String methodName, Object owner) {
		super();
		this.methodName = methodName;
		this.owner = owner;
		this.handler = new EventHandlerComponent<Object>(ThrowingExceptionHandler.INSTANCE);
		mi = new MethodInvocator(handler, methodName, owner);
	}
	
	@Override
	public void execute() {		
		mi.invoke();
	}
	
	@Override
	public <T> T getHandlerParameter(Class<T> paramType) {	
		return handler.getHandlerParameter(paramType);
	}
	
	@Override
	public <T> void setHandlerParameter(java.lang.Class<T> paramType, T param) {
		handler.setHandlerParameter(paramType, param);
	}

}
