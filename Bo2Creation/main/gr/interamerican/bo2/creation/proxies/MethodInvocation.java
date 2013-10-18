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

import gr.interamerican.bo2.utils.ArrayUtils;

import java.lang.reflect.Method;

/**
 * Method invocation information.
 */
class MethodInvocation {
	/**
	 * Method.
	 */
	Method method;
	
	/**
	 * Arguments.
	 */
	Object[] args;
	
	/**
	 * Return value.
	 */
	Object returnValue;
	
	/**
	 * Thrown.
	 */
	Throwable thrown;
	
	/**
	 * Creates a {@link MethodInvocation} object for a successful method invocation.
	 * 
	 * @param m
	 * @param arguments
	 * @param retValue
	 * 
	 * @return Returns a new instance of the method invocation.
	 */
	public static MethodInvocation successful(Method m, Object[] arguments, Object retValue) {
		MethodInvocation instance = new MethodInvocation();
		instance.method = m;
		instance.args = arguments;
		instance.returnValue = retValue;
		return instance;
	}
	
	/**
	 * Creates a {@link MethodInvocation} object for a successful method invocation.
	 * 
	 * @param m
	 * @param arguments
	 * @param thrown
	 * 
	 * @return Returns a new instance of the method invocation.
	 */
	public static MethodInvocation thrown(Method m, Object[] arguments, Throwable thrown) {
		MethodInvocation instance = new MethodInvocation();
		instance.method = m;
		instance.args = arguments;
		instance.thrown = thrown;
		return instance;
	}

	/**
	 * Creates a new MethodInvocation object. 
	 *
	 */
	private MethodInvocation() {
		super();
		
	}
	

	/**
	 * Gets the args.
	 *
	 * @return Returns the args
	 */
	public Object[] getArgs() {
		return args;
	}
	
	/**
	 * Gets the name of the method.
	 * 
	 * @return Returns the name of the method.
	 */
	public String getMethodName() {
		return method.getName();
	}
	
	/**
	 * Checks if this method invocation matches with the specified
	 * arguments.
	 * 
	 * @param methodName
	 * @param arguments
	 * 
	 * @return Returns true if this method invocation matches with the 
	 *         specified arguments.
	 */
	public boolean isMatch(String methodName, Object[] arguments) {		
		return this.getMethodName().equals(methodName)
		    && argsMatch(arguments);
	}
	
	/**
	 * Checks for matching arguments.
	 * 
	 * @param arguments
	 * @return Returns true if the arguments match. 
	 */
	private boolean argsMatch(Object[] arguments) {
		if (ArrayUtils.equals(args, arguments)) {
			return true;
		}
		if (args==null) {
			return arguments.length==0;
		}
		if (arguments==null) {
			return args.length==0;
		}
		return false;
	}
	
	
	
}
