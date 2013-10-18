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


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * {@link InvocationHandler} that always returns the value
 * specified on it constructor. 
 */
public class EmptyInvocationHandler 
extends AbstractFakeInvocationHandler {
	
	/**
	 * Value returned by the <code>invoke(proxy,method,args)</code>
	 * method.
	 */
	private Object returnValue;
	

	/**
	 * Creates a new CustomValueHandler object. 
	 *
	 * @param returnValue
	 */
	public EmptyInvocationHandler(Object returnValue) {
		super();
		this.returnValue = returnValue;
	}
	
	/**
	 * Creates a new CustomValueHandler object. 
	 */
	public EmptyInvocationHandler() {
		this(null);
	}

	
	@Override
	protected Object doInvoke(Object proxy, Method method, Object[] args) 
	throws Throwable {
		return returnValue;
	}



}
