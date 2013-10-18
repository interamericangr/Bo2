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

import gr.interamerican.bo2.utils.Defaults;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * {@link InvocationHandler} that always returns the default
 * value for the methods return type. 
 */
public class DefaultValueInvocationHandler 
extends AbstractFakeInvocationHandler {

	
	@Override
	protected Object doInvoke(Object proxy, Method method, Object[] args) 
	throws Throwable {
		Class<?> returnType = method.getReturnType();
		Object o = Defaults.getDefaultValue(returnType);
		return o;
	}

}
