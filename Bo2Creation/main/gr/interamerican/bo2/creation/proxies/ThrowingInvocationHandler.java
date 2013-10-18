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


import java.lang.reflect.Method;

/**
 * {@link ThrowingInvocationHandler} will never invoke
 * any method, but it will always throw the runtime exception
 * that is defined in its constructor.
 */
public class ThrowingInvocationHandler 
extends AbstractFakeInvocationHandler {
	
	/**
	 * Class defining the type of Throwable thrown by the 
	 * <code>invoke(object,method,object[])</code> method
	 * of this InvocationHandler.
	 */
	private Class<? extends Throwable> throwable;
	
	/**
	 * Creates a new CustomizedThrowingInvocationHandler object. 
	 *
	 * @param throwable
	 */
	public ThrowingInvocationHandler(Class<? extends Throwable> throwable) {
		super();
		this.throwable = throwable;
	}
	
	@Override
	protected Object doInvoke(Object proxy, Method method, Object[] args) 
	throws Throwable {
		throw throwable.newInstance();
	}

}
