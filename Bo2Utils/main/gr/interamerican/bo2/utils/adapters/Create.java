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
package gr.interamerican.bo2.utils.adapters;

import gr.interamerican.bo2.utils.ReflectionUtils;

import java.lang.reflect.Constructor;

/**
 * Creates a new instance of R by calling its constructor that takes
 * an argument of type A.
 * 
 * @param <A> 
 *        Type of argument.
 * @param <R> 
 *        Type of result.
 */
public class Create<A,R> 
implements AnyOperation<A, R> {
	
	/**
	 * Class of argument.
	 */
	Class<A> argumentType;
	

	/**
	 * Class of result.
	 */
	Class<R> resultType;
	
	/**
	 * Class of result.
	 */
	Constructor<R> constructor;
	
	/**
	 * Creates a new Create object. 
	 * 
	 * The class resultType must have a public constructor that accepts
	 * an argument of type argumentType.
	 *
	 * @param argumentType
	 *        Type of argument.
	 * @param resultType
	 *        Type of result.
	 */
	public Create(Class<A> argumentType, Class<R> resultType) {
		super();
		this.argumentType = argumentType;
		this.resultType = resultType;		
		this.constructor = 
			ReflectionUtils.getConstructor(resultType, argumentType);
		if (constructor==null) {
			String msg = "No constructor with the specified argument types"; //$NON-NLS-1$
			throw new RuntimeException(msg);
		}
		 
	}
	
	public R execute(A a) {
		return ReflectionUtils.newInstance(constructor, a);
	}

}
