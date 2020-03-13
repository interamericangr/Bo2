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
package gr.interamerican.wicket.bo2.callbacks;

import gr.interamerican.wicket.callback.MethodBasedCallbackAction;

import java.io.Serializable;

/**
 * A {@link MethodBasedBo2WicketBlock} is a Bo2WicketBlock that 
 * executes a method of its owner object.
 * 
 * @deprecated use method reference instead
 */
@Deprecated
public class MethodBasedBo2WicketBlock 
extends Bo2WicketBlock {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Callback action.
	 */
	MethodBasedCallbackAction callback;	
			
	/**
	 * Creates a new MethodBasedBo2WicketBlock object. 
	 *
	 * @param methodName the method name
	 * @param owner the owner
	 */
	public MethodBasedBo2WicketBlock(String methodName, Serializable owner) {
		/* don't call super() */
		this.callback = new MethodBasedCallbackAction(methodName, owner);
	}
	
	@Override
	public void work() {		
		callback.execute();
	}
	
	@Override
	public <T> T getHandlerParameter(Class<T> paramType) {	
		return callback.getHandlerParameter(paramType);
	}
	
	@Override
	public <T> void setHandlerParameter(java.lang.Class<T> paramType, T param) {
		callback.setHandlerParameter(paramType, param);
	}
}