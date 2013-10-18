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
package gr.interamerican.bo2.utils.beans;

import gr.interamerican.bo2.utils.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link DynamicMethod} pairs values with methods of a class of P.
 * 
 * The DynamicMethod can dynamically decide which method to execute
 * according to a key that is specified on time of the execution.
 * 
 * @param <T>
 *        Type of object being checked. 
 * @param <P> 
 *        Type of object on which the methods are invoked.
 */
public class DynamicMethod<T, P> {
	
	/**
	 * Methods.
	 */
	Map<T, Method> methods = new HashMap<T, Method>();
	
	/**
	 * Class of the object that contains the methods.
	 */
	Class<P> clazzP;
	
	/**
	 * Executes the method(s) paired with conditions that evaluate
	 * to true for the specified argument <code>t</code> on the 
	 * specified argument <code>p</code>.
	 * 
	 * @param t
	 *        Object on which the conditions are applied.
	 * @param p
	 *        Object on which the methods are invoked.
	 */
	public void execute(T t, P p) {
		Method method = methods.get(t);		
		if (method==null) {			
			@SuppressWarnings("nls")
			String msg = "No method registered for the specified object";
			throw new RuntimeException(msg);
		}
		Object[] args = null;
		ReflectionUtils.invoke(method, p, args);
		
	}
	
	/**
	 * Creates a new DynamicMethod object. 
	 *
	 * @param clazzP
	 */
	public DynamicMethod(Class<P> clazzP) {
		super();
		this.clazzP = clazzP;
	}

	/**
	 * Registers a pair of key and method name.
	 * 
	 * @param key
	 *        Key associated with the method.
	 * @param methodName
	 *        Name of the method.
	 */
	public void register(T key, String methodName) {
		Method method = ReflectionUtils.getMethodByUniqueName(methodName, clazzP);		
		@SuppressWarnings("rawtypes")
		Class[] argTypes = method.getParameterTypes();
		if (argTypes.length>0) {
			@SuppressWarnings("nls") 
			String msg = "Invalid method with arguments specified "
				       + methodName;
			throw new RuntimeException(msg);
		}
		methods.put(key, method);
	}

}
