package gr.interamerican.bo2.utils.handlers;

import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;


/**
 * {@link MethodLocator} finds a method of a class by its name.
 * 
 * 

 */
public class MethodLocator {
	
	
	
	/**
	 * Finds the method of the specified class with the specified 
	 * name and arguments. 
	 * 
	 * Public and declared methods are included in the search.
	 *
	 * @param clazz
	 *        Class that has a public method that will be invoked.
	 * @param methodName
	 *        Name of the method.
	 * @param argumentTypes
	 *        Argument types of the method. 
	 *        
	 * @return Returns the specified method.
	 */
	public Method get(Class<?> clazz, String methodName, Class<?>... argumentTypes) {
		List<Method> methods = ReflectionUtils.getMethodsByUniqueName(methodName, clazz);
		for (Method method : methods) {
			Class<?>[] paramTypes = method.getParameterTypes();
			if (ReflectionUtils.argumentTypesMatchParameterTypes(paramTypes, argumentTypes)) {
				ReflectionUtils.setAccessible(method);
				return method;
			}
		}
		@SuppressWarnings("nls")
		String msg = StringUtils.concat(		
			"Class ", clazz.getName(),
			" does not have a method with name ", methodName,
			" and the specified argument types"); 
		throw new RuntimeException(msg);
	}
	
	

	/**
	 * Finds the method of the specified class with the specified name
	 * and no arguments. 
	 *
	 * @param clazz
	 *        Class that has must have a public or declared method with the 
	 *        specified name.
	 * @param methodName
	 *        name of public method.
	 *        
	 * @return Returns the specified method.
	 * 
	 *        
	 */
	public Method get(Class<?> clazz, String methodName) {
		Class<?>[] argumentTypes = {};
		return get(clazz, methodName, argumentTypes);
	}
	
	/**
	 * Finds the static method of the specified class with the specified arguments. 
	 * 
	 * Public and declared methods are included in the search.
	 *
	 * @param clazz
	 *        Class that has must have a public or declared static method with the 
	 *        specified name.
	 * @param methodName
	 *        Name of the method.
	 * @param argumentTypes
	 *        Argument types of the method. 
	 *        
	 * @return Returns the specified method.
	 */
	public Method getStatic(Class<?> clazz, String methodName, Class<?>... argumentTypes) {
		Method method = get(clazz, methodName, argumentTypes);
		validateStatic(method);
		return method;
	}
	
	/**
	 * Finds the static method of the specified class that takes no arguments. 
	 * 
	 * Public and declared methods are included in the search.
	 *
	 * @param clazz
	 *        Class that has must have a public or declared static method with the 
	 *        specified name.
	 * @param methodName
	 *        Name of the method.
	 *        
	 * @return Returns the specified method.
	 */
	public Method getStatic(Class<?> clazz, String methodName) {
		Method method = get(clazz, methodName);
		validateStatic(method);
		return method;
	}
	
	/**
	 * Finds the method of the specified class with the specified name.
	 * 
	 * There must be only one method with the specified name, otherwise
	 * a RuntimeException will be thrown. 
	 *
	 * @param clazz
	 *        Class that has must have a public or declared method with the 
	 *        specified name.
	 * @param methodName
	 *        name of method.
	 *        
	 * @return Returns the specified method.
	 * 
	 *        
	 */
	public Method getByUniqueName(Class<?> clazz, String methodName) {
		Class<?>[] argumentTypes = {};
		return get(clazz, methodName, argumentTypes);
	}
	
	
	/**
	 * Validates that a method is static.
	 * 
	 * @param m
	 */
	void validateStatic(Method method) {
		if (!Modifier.isStatic(method.getModifiers())) {
			@SuppressWarnings("nls")
			String msg = StringUtils.concat(		
					"Method ", method.getName(), 
					" of class ", method.getDeclaringClass().getName(),					
					" is not static"); 
			throw new RuntimeException(msg);
		}
	}
		
	

	
	
}
