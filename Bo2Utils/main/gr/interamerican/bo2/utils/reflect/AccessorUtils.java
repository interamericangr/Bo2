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
package gr.interamerican.bo2.utils.reflect;

import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Utilities for property accessor methods.
 */
public class AccessorUtils {
	
	/**
	 * Finds if a method is a property getter.
	 * 
	 * @param method
	 * @return Returns true if the method is a getter.
	 */
	public static boolean isGetter(Method method) {
		if (Modifier.isStatic(method.getModifiers())) {
			return false;
		}
		Class<?> returnType = method.getReturnType(); 
		if (returnType==void.class) {
			return false;
		}
		if (method.getParameterTypes().length!=0) {
			return false;
		}
		String name = method.getName();
		if (name.equals("getClass")) { //$NON-NLS-1$
			return false;
		}
		if (isAssessorName("get", name)) { //$NON-NLS-1$
			return true;
		}
		if (returnType.equals(Boolean.class) || returnType.equals(boolean.class)) {
			if (isAssessorName("is", name)) { //$NON-NLS-1$
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Finds if a name is an accessor name.
	 * 
	 * @param prefix
	 *        Prefix for the accessor (get, set, is)
	 * @param name 
	 *        Method name.
	 *        
	 * @return Returns true if the name is a possible accessor name.
	 */
	public static boolean isAssessorName(String prefix, String name) {
		if (!name.startsWith(prefix)) {
			return false;
		}
		int firstFieldCharPos = prefix.length(); 
		if (name.length()<=firstFieldCharPos) {
			return false;			
		}
		char afterPrefix = name.charAt(firstFieldCharPos);
		return Character.isUpperCase(afterPrefix);
	}
	
	/**
	 * Finds if a method is a property setter.
	 * 
	 * @param method
	 * @return Returns true if the method is a setter.
	 */
	public static boolean isSetter(Method method) {
		if (Modifier.isStatic(method.getModifiers())) {
			return false;
		}
		Class<?> returnType = method.getReturnType(); 
		if (returnType!=void.class) {
			return false;
		}
		if (method.getParameterTypes().length!=1) {
			return false;
		}
		String name = method.getName();
		return (isAssessorName("set", name));  //$NON-NLS-1$
	}
	
	/**
	 * Finds the property name for a property accessor method.
	 * 
	 * @param method 
	 *        Property accessor.
	 *        
	 * @return If the specified method is a property accessor, then
	 *         returns the name of the property. If the specified method
	 *         is not a property accessor, returns null..
	 */
	@SuppressWarnings("nls")
	public static String propertyName(Method method) {
		String name = method.getName();		
		if (name.startsWith("get") || name.startsWith("set")) {
			String s = name.substring(3); 
			return StringUtils.firstLowerCase(s);
		}
		if (name.startsWith("is")) {
			String s = name.substring(2);
			return StringUtils.firstLowerCase(s);
		}
		return null; //Not an accessor.	
	}
	
	/**
	 * Gets the getter method of a field.
	 * 
	 * @param field
	 * @param clazz
	 * @return Returns the getter method of a field.
	 */
	public static Method getGetter(Field field, Class<?> clazz) {		
		return getGetter(field.getName(), field.getType(), clazz);
	}
	
	/**
	 * Gets the getter method of a property.
	 * 
	 * @param propertyName
	 * @param propertyType
	 * @param clazz
	 * @return Returns the getter method. 
	 *         If the property is write only, returns null.
	 */
	public static Method getGetter(String propertyName, Class<?> propertyType, Class<?> clazz) {		
		Method getter = getGetter("get", propertyName, propertyType, clazz); //$NON-NLS-1$
		if (getter==null) {
			boolean isBoolean = 
				propertyType.equals(boolean.class) ||
			    propertyType.equals(Boolean.class);
			if (isBoolean) {
				getter = getGetter("is", propertyName, propertyType, clazz); //$NON-NLS-1$
			}
		}
		return getter;
	}
	

	/**
	 * Gets the getter of a method, assuming that the getter's name starts
	 * with the specified prefix.
	 * 
	 * @param prefix
	 *        Prefix, can be get of is.
	 * @param propertyName
	 *        Name of property.
	 * @param propertyType
	 *        Type of property.
	 * @param clazz
	 *        Class on which the property is seeked.
	 *        
	 * @return Returns the getter method if it does not exist,
	 *         returns null.
	 */
	private static Method getGetter(String prefix, String propertyName, Class<?> propertyType, Class<?> clazz) {
		String getterName = prefix + StringUtils.firstCapital(propertyName); 
		Method getter = ReflectionUtils.getPublicMethodWithoutParamsByName(getterName, clazz);
		if (getter!=null) {
			if (!Modifier.isStatic(getter.getModifiers())) {
				boolean isGenericGetter = getter.getReturnType().equals(Object.class);			
				boolean isMatchingReturnTypes =
					isGenericGetter || 
					propertyType.equals(getter.getReturnType()) ||
					propertyType.equals(ReflectionUtils.getWrapperClass(getter.getReturnType()));
				if (isMatchingReturnTypes) {
					return getter;
				}				
			}
		}		
		return null;
	}
	
	/**
	 * Gets the setter method of a field.
	 * 
	 * @param field
	 * @param clazz
	 * @return Returns the setter method of a field.
	 */
	public static Method getSetter(Field field, Class<?> clazz) {		
		return getSetter(field.getName(), field.getType(), clazz);
	}
	
	/**
	 * Gets the setter method of a property.
	 * 
	 * @param propertyName
	 * @param propertyType
	 * @param clazz
	 * 
	 * @return Returns the property setter. If the property is read only, returns null.
	 */
	public static Method getSetter(String propertyName, Class<?> propertyType, Class<?> clazz) {
		String setterName = "set" + StringUtils.firstCapital(propertyName); //$NON-NLS-1$
		Method setter = ReflectionUtils.getPublicMethod(setterName, clazz, propertyType);
		boolean isSetter = 
			setter!=null && 
			setter.getReturnType()==void.class && 
			!Modifier.isStatic(setter.getModifiers());
		if (isSetter) {
			return setter;			
		}
		return null;		
	}
	


}
