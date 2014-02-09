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
package gr.interamerican.bo2.impl.open.transformations;

import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.adapters.Transformation;

/**
 * Creates a new instance of class R using the default Bo2
 * object factory and then and copies all properties of the
 * specified argument to the result.
 * 
 * @param <A> 
 *        Type of argument.
 * @param <R> 
 *        Type of result.
 */
public class CopyProperties<A,R> 
implements Transformation<A, R> {
	
	/**
	 * Class of argument.
	 */
	Class<A> argumentClass;
	
	/**
	 * Class of result.
	 */
	Class<R> resultClass;

	/**
	 * Creates a new CopyTo object. 
	 *
	 * @param argumentClass
	 * @param resultClass
	 */
	public CopyProperties(Class<A> argumentClass, Class<R> resultClass) {
		super();
		this.argumentClass = argumentClass;
		this.resultClass = resultClass;
	}
	
	public R execute(A a) {
		R r = Factory.create(resultClass);		
		ReflectionUtils.copyProperties(a, r);
		return r;
	}
	
	

}
