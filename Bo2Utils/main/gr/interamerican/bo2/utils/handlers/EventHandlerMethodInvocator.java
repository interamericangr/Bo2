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

import gr.interamerican.bo2.utils.Utils;

/**
 * The purpose of a {@link EventHandlerMethodInvocator} is to invoke a method of the
 * object that owns an EventHandler.
 */
public class EventHandlerMethodInvocator 
extends AbstractMethodInvocator {
	
	/**
	 * Creates a new MethodInvocator object. 
	 *
	 * @param handler the handler
	 * @param methodName the method name
	 * @param owner the owner
	 */
	public EventHandlerMethodInvocator(AbstractEventHandler<?> handler, String methodName,	Object owner) {
		super(handler, methodName, owner);
	}
	
	@Override
	protected Object[] getArguments() {
		Class<?>[] parameterTypes = method.getParameterTypes();			
		Object[] args = new Object[parameterTypes.length];
		AbstractEventHandler<?> eventHandler = Utils.cast(handler);		
		for (int i = 0; i < parameterTypes.length; i++) {
			args[i] = eventHandler.getHandlerParameter(parameterTypes[i]);				
		}
		return args;
	}
}