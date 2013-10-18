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
package gr.interamerican.bo2.creation.util;

import gr.interamerican.bo2.utils.ArrayUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * 
 */
public class MethodsUtilities {

	/**
	 * Hidden.
	 */
	private MethodsUtilities() {/* empty */}
	
	/**
	 * Gets the compareTo method of a comparable class.
	 * 
	 * @param clazz
	 * 
	 * @return Returns the compare to method.
	 */
	public static Method compareTo(Class<?> clazz) {
		if (Comparable.class.isAssignableFrom(clazz)) {
			List<Method> methods = ReflectionUtils.getPublicMethodsByName("compareTo", clazz); //$NON-NLS-1$
			for (Method method : methods) {
				if (!Modifier.isStatic(method.getModifiers())) {
					if (method.getReturnType().equals(int.class)) {
						Class<?>[] args = method.getParameterTypes();
						if (args.length==1) {						
							return method;						
						}
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Finds if the specified method is the <code>equals(object)</code> method.
	 * 
	 * @param method
	 * 
	 * @return Returns true if the specified method is equals.
	 */
	public static boolean isEquals(Method method) {
		return is(method,"equals",Object.class); //$NON-NLS-1$
	}
	
	/**
	 * Finds if the specified method is the <code>hashcode()</code> method.
	 * 
	 * @param method
	 * 
	 * @return Returns true if the specified method is hashcode.
	 */
	public static boolean isHashCode(Method method) {
		return is(method,"hashCode"); //$NON-NLS-1$
	}
	
	/**
	 * Finds if the specified method is the <code>hashcode()</code> method.
	 * 
	 * @param method
	 * 
	 * @return Returns true if the specified method is hashcode.
	 */
	public static boolean isToString(Method method) {
		return is(method,"toString"); //$NON-NLS-1$
	}
	

	/**
	 * Finds if the specified method has the specified name
	 * and argument types.
	 * 
	 * @param method
	 * @param name
	 * @param argumentTypes
	 * 
	 * @return Returns true if the specified method has the specified name, 
	 *         and argument types.
	 */
	static boolean is(Method method, String name, Class<?>... argumentTypes) {
		return method.getName().equals(name)
			&& ArrayUtils.equals(method.getParameterTypes(), argumentTypes);
	}
	
	

}
