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
package gr.interamerican.bo2.utils;

import gr.interamerican.bo2.utils.beans.MultipleValuesMap;
import gr.interamerican.bo2.utils.beans.Pair;
import gr.interamerican.bo2.utils.reflect.analyze.TypeAnalysis;
import gr.interamerican.bo2.utils.reflect.beans.BeanPropertyDefinition;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilities relevant with the java reflection API.
 */
public class ReflectionUtils {
	
	/**
	 * Logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(ReflectionUtils.class.getName());
	
	/**
	 * Indicates if this is an IBM JRE.
	 */
	private static boolean isIbmJre;
	
	
	static {		
		String vendor = System.getProperty("java.vendor"); //$NON-NLS-1$
		isIbmJre = vendor.startsWith("IBM"); //$NON-NLS-1$
	}
	
	
	/**
	 * No arguments for a getter.
	 */
	private static final Object[] GETTER_ARGS = {};
	
	/**
	 * Singleton map with primitives to wrappers associations.
	 * This is used when invoking methods with reflection to make
	 * sure primitive parameters match wrapper arguments.
	 */
	private static final Map<Class<?>, Class<?>> primitives2wrappers = new HashMap<Class<?>, Class<?>>();
	static {
		primitives2wrappers.put(byte.class, Byte.class);
		primitives2wrappers.put(short.class, Short.class);
		primitives2wrappers.put(int.class, Integer.class);
		primitives2wrappers.put(long.class, Long.class);
		primitives2wrappers.put(float.class, Float.class);
		primitives2wrappers.put(double.class, Double.class);
		primitives2wrappers.put(boolean.class, Boolean.class);
		primitives2wrappers.put(char.class, Character.class);
		primitives2wrappers.put(void.class, Void.class);
	}
	
	
	
	/**
	 * Gets all fields declared in a type's hierarchy including fields declared
	 * in super-types, as long as the super-type belongs to a base type.
	 * 
	 * @param type
	 *            Type who's fields this method will return.
	 * @param baseType
	 *            Base super-type beyond which, the type hierarchy will be
	 *            ignored.
	 * 
	 * @return Returns a collection with all fields of the type.
	 */
	public static List<Field> allFields(Class<?> type, Class<?> baseType) {
		ArrayList<Field> fields = new ArrayList<Field>();
		List<Field> declaredFields = Arrays.asList(type.getDeclaredFields());
		for (Field field : declaredFields) {
			if (!field.isSynthetic()) {
				fields.add(field);				
			}
		}
		Class<?> supertype = type.getSuperclass();	
		if (supertype != null && baseType.isAssignableFrom(supertype)) {
			List<Field> superTypeFields = allFields(supertype, baseType);
			fields.addAll(superTypeFields);
		}
		return fields;
	}
	
	/**
	 * Gets all fields declared in a type's hierarchy not including fields declared
	 * in super-types.
	 * 
	 * @param type
	 *            Type who's fields this method will return.
	 * 
	 * @return Returns a collection with all fields of the type.
	 */
	public static List<Field> declaredFields(Class<?> type) {
		ArrayList<Field> fields = new ArrayList<Field>();
		List<Field> declaredFields = Arrays.asList(type.getDeclaredFields());
		for (Field field : declaredFields) {
			if (!field.isSynthetic()) {
				fields.add(field);				
			}
		}
		return fields;
	}
	
	/**
	 * Gets all methods declared in a type delegating to method 
	 * {@link Class} getDeclareMethods
	 * 
	 * Omits synthetic methods.
	 * 
	 * @param type
	 *            Type who's fields this method will return.
	 * 
	 * @return Returns a collection with all methods of the type.
	 */
	public static List<Method> getDeclaredMethodsAsList(Class<?> type) {
		Method[] methods = getDeclaredMethods(null, type); 
		return Arrays.asList(methods);
	}
	
	/**
	 * Gets all methods declared in a type that are annotated with
	 * a specified annotation.
	 * 
	 * @param type
	 *        Type who's fields this method will return.
	 * @param annotation
	 *        Annotation 
	 * 
	 * @return Returns a collection with all annotated methods of the type.
	 */
	public static List<Method> getAnnotatedMethods(
			Class<?> type, Class<? extends Annotation> annotation) {
		List<Method> annotated = new ArrayList<Method>();
		List<Method> declared = getDeclaredMethodsAsList(type);
		for(Method m : declared) {
			if(m.isAnnotationPresent(annotation)) {
				annotated.add(m);
			}
		}
		return annotated;
	}
	
	/**
	 * Gets the first type (class or interface) in the hierarchy of a 
	 * class, that is annotated with the specified annotation.
	 * 
	 * @param type
	 *        Root of the search. 
	 * @param annotation
	 *        Annotation 
	 * 
	 * @return Returns a collection with all annotated methods of the type.
	 */
	public static Class<?> getAnnotatedType(
			Class<?> type, Class<? extends Annotation> annotation) {
		if (type.getAnnotation(annotation)!=null) {
			return type;
		}
		Class<?>[] interfaces = type.getInterfaces();
		for (Class<?> intfc : interfaces) {
			Class<?> annotated = getAnnotatedType(intfc, annotation);
			if (annotated!=null) {
				return annotated;
			}
			Class<?> superClass = type.getSuperclass();
			if (superClass!=null) {
				return getAnnotatedType(superClass, annotation);
			}			
		}
		return null;
	}

	/**
	 * Checks that a field can be assigned to a type.
	 * 
	 * @param field
	 *            Field that must be assigned to a type.
	 * @param type
	 *            Type	 
	 */
	@SuppressWarnings("nls")
	public static void checkFieldType(Field field, Class<?> type) {
		if (field != null) {
			Class<?> fieldType = field.getType();
			boolean isAssignable = argumentMatchesParameter(type, fieldType);
			if (!isAssignable) {
				String message = "Field " + field.getName() + " can't be assigned to " + type.getName();
				throw new RuntimeException(message);
			}
		}
	}
	
	/**
	 * Finds the names of the properties of o1, for which a property
	 * with the same name exists on o2, and this property has different
	 * value in o2, compared to the value of the property in o1. 
	 * 
	 * @param o1
	 * @param o2
	 * 
	 * @return Returns an array with the names of the properties.
	 */
	public static String[] namesOfPropertiesWithDifferentValue(Object o1, Object o2) {
		List<String> changed = new ArrayList<String>();
		TypeAnalysis type1 = TypeAnalysis.analyze(o1.getClass());
		TypeAnalysis type2 = TypeAnalysis.analyze(o2.getClass());
		Set<String> all = type1.getNamesOfProperties();		
		for (String name : all) {
			BeanPropertyDefinition<?> p1 = type1.getFirstPropertyByName(name);
			BeanPropertyDefinition<?> p2 = type2.getFirstPropertyByName(name);
			if (p2!=null) {					
				Method getter1 = p1.getGetter();
				Method getter2 = p2.getGetter();			
				if (getter1!=null && getter2!=null) {
					Object v1 = ReflectionUtils.invoke(getter1, o1, GETTER_ARGS);
					Object v2 = ReflectionUtils.invoke(getter2, o2, GETTER_ARGS);
					if (!Utils.equals(v1, v2)) {
						changed.add(name);
					}
				}
			}
		}
		return changed.toArray(new String[0]);
	}
	
	/**
	 * For a property that is common in two instances, this method will
	 * copy the source's property value to the target's property, as long
	 * as this is possible.
	 * 
	 * @param source 
	 *        Object who's properties will be copied to the target object.	         
	 * @param target
	 *        Object to which the properties will be copied.
	 * @param property
	 *        Property name.
	 */
	public static void copyProperty(Object source, Object target, String property) {
		if(source==null || target==null) {
			return;
		}
		TypeAnalysis sourceType = TypeAnalysis.analyze(source.getClass());
		TypeAnalysis targetType = TypeAnalysis.analyze(target.getClass());
		copyProperty(sourceType, targetType, source, target, property);
	}
	
	/**
	 * Copies all common properties of an object to another.
	 * 
	 * @see #copyProperties(Object, Object, String[])
	 *  
	 * @param source 
	 *        Object who's properties will be copied to the target object.	         
	 * @param target
	 *        Object to which the properties will be copied.
	 */
	public static void copyProperties (Object source, Object target) {
		copyProperties(source, target, null);
	}


	/**
	 * Copies common properties of an object to another. <br/>
	 * 
	 * The method will copy all properties of the source object that have their
	 * name included in the properties array, to the target object. The property
	 * will be copied only if it has the same type in both objects or if the type
	 * of the first object's property is assignable to the type of the second
	 * object's property. Source and target object don't need to be instances of
	 * the same class. 
	 * <br/>
	 * If the value of a property is null, then the default value
	 * defined for the property type is set. The default values are 
	 * specified and retrieved from the {@link Defaults} utility class.	  
	 * <br/>
	 * If either the source or the target objects are null the method will have
	 * no effect on either object.
	 * 
	 * @param source
	 *            Object who's properties will be copied to the target object.
	 * @param target
	 *            Object to which the properties will be copied.
	 * @param properties
	 *            Names of properties to copy. If this array is empty or null
	 *            then all properties of source will be copied to the target.
	 * @param withDefaults
	 *            Specifies if properties with null value will be copied as null
	 *            or if a default value will be copied for them. If this parameter
	 *            is true, then if the value of a property is null, it will be 
	 *            replaced in the target object by the default value of the property
	 *            type. The default values are specified and retrieved from the 
	 *            {@link Defaults} utility class.	  
	 */
	private static void copyProperties(Object source, Object target, String[] properties, boolean withDefaults) {
		if(source==null || target==null) {
			return;
		}
		TypeAnalysis sourceType = TypeAnalysis.analyze(source.getClass());
		TypeAnalysis targetType = TypeAnalysis.analyze(target.getClass());		
		String[] propertiesToCopy;		
		if (ArrayUtils.isNullOrEmpty(properties)) {
			Set<String> allProperties = sourceType.getNamesOfProperties();
			propertiesToCopy = CollectionUtils.toArray(allProperties, new String[0]);
		} else {
			propertiesToCopy = properties;
		}
		if (withDefaults) {
			for (String property : propertiesToCopy) {			
				copyPropertyWithDefault(sourceType, targetType, source, target, property);
			}
		} else {
			for (String property : propertiesToCopy) {			
				copyProperty(sourceType, targetType, source, target, property);
			}
		}
	}
	
	/**
	 * Copies common properties of an object to another.
	 * 
	 * The method will copy all properties of the source object that have their
	 * name included in the properties array, to the target object. The property
	 * will be copied only if it has the same type in both objects or if the type
	 * of the first object's property is assignable to the type of the second
	 * object's property. Source and target object don't need to be instances of 
	 * the same class.
	 * 
	 * If either the source or the target objects are null the method will have
	 * no effect on either object.
	 * 
	 * @param source
	 *            Object who's properties will be copied to the target object.
	 * @param target
	 *            Object to which the properties will be copied.
	 * @param properties
	 *            Names of properties to copy. If this array is empty or null
	 *            then all properties of source will be copied to the target.
	 */
	public static void copyProperties(Object source, Object target, String[] properties) {
		copyProperties(source, target, properties,false);		
	}
	
	
	/**
	 * Copies common properties of an object to another. <br/>
	 * 
	 * The method will copy all properties of the source object that have their
	 * name included in the properties array, to the target object. The property
	 * will be copied only if it has the same type in both objects or if the type
	 * of the first object's property is assignable to the type of the second
	 * object's property. Source and target object don't need to be instances of
	 * the same class. 
	 * <br/>
	 * If the value of a property is null, then the default value
	 * defined for the property type is set. The default values are 
	 * specified and retrieved from the {@link Defaults} utility class.	  
	 * <br/>
	 * If either the source or the target objects are null the method will have
	 * no effect on either object.
	 * 
	 * @param source
	 *            Object who's properties will be copied to the target object.
	 * @param target
	 *            Object to which the properties will be copied.
	 * @param properties
	 *            Names of properties to copy. If this array is empty or null
	 *            then all properties of source will be copied to the target.
	 */
	public static void copyPropertiesWithDefaults(Object source, Object target, String[] properties) {
		copyProperties(source, target, properties,true);				
	}
	
	
	/**
	 * Copies all common properties of an object to another excluding some properties.
	 * 
	 * @see #copyProperties(Object, Object, String[])
	 * 
	 * @param source 
	 *        Object who's properties will be copied to the target object.	         
	 * @param target
	 *        Object to which the properties will be copied.
	 * @param excluded 
	 *        List of properties to exclude.
	 */
	public static void copyPropertiesExcluding (Object source, Object target, String[] excluded) {
		if (ArrayUtils.isNullOrEmpty(excluded)) {
			copyProperties(source, target);
			return;
		}
		if(source==null || target==null) {
			return;
		}
		TypeAnalysis sourceType = TypeAnalysis.analyze(source.getClass());
		TypeAnalysis targetType = TypeAnalysis.analyze(target.getClass());		
		Set<String> excludedProperties = CollectionUtils.toSet(excluded);
		Set<String> allProperties = sourceType.getNamesOfProperties();
		for (String property : allProperties) {
			if (!excludedProperties.contains(property)) {
				copyProperty(sourceType, targetType, source, target, property);				
			}
		}		
	}

	/**
	 * Gets all fields that have been marked with a specified annotation.
	 * 
	 * @param fields
	 *            Collection of fields.
	 * @param annotation
	 *            Annotation.
	 * @return Returns a list containing the eements of the fields collection
	 *         that have been marked with the specified annotation.
	 */
	public static List<Field> getAnnotated(Collection<Field> fields,
			Class<? extends Annotation> annotation) {
	
		ArrayList<Field> list = new ArrayList<Field>();
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(annotation)) {
				list.add(field);
			}
		}
		return list;
	}

	/**
	 * Gets the first field of type that can can be assigned to the
	 * requiredType.
	 * 
	 * @param requiredType
	 * @param type
	 * 
	 * @return Returns the first field of type that can can be assigned to the
	 *         requiredType.
	 */
	public static Field getFirstByType(Class<?> requiredType, Class<?> type) {
		List<Field> fields = allFields(type, Object.class);
		for (Field field : fields) {
			Class<?> fieldType = field.getType();
			if (requiredType.isAssignableFrom(fieldType)) {
				field.setAccessible(true);
				return field;
			}
		}
		return null;
	}
	

	/**
	 * Gets a method that has a specified name.
	 * 
	 * @param methodName
	 *            Method name.
	 * @param type
	 *            Type on which the method is searched.
	 * @param args Argument types.
	 * 
	 * @return Returns the method that matches the search criteria. If there is
	 *         no method matching the search criteria, returns null.
	 */
	public static Method getPublicMethod(String methodName, Class<?> type, Class<?>... args) {
		try {
			return type.getMethod(methodName, args);
		} catch (SecurityException e) {
			return null;
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	/**
	 * Gets a method that takes no parameters and has a specified name.
	 * 
	 * @param methodName
	 *            Method name.
	 * @param type
	 *            Type on which the method is searched.
	 * 
	 * @return Returns the method that matches the search criteria. If there is
	 *         no method matching the search criteria, returns null.
	 */
	public static Method getPublicMethodWithoutParamsByName
	(String methodName,	Class<?> type) {
		Class<?>[] paramTypes = null;
		try {
			return type.getMethod(methodName, paramTypes);
		} catch (SecurityException e) {
			return null;
		} catch (NoSuchMethodException e) {
			return null;
		}
	}
	
	/**
	 * Gets the method of the specified class with the specified name.
	 * 
	 * Public and declared methods of the specified type are included.
	 * The specified method name must be unique in the specified type,
	 * otherwise a runtime exception is thrown.
	 * 
	 * @param methodName
	 *            Method name.
	 * @param type
	 *            Type on which the method is searched.
	 * 
	 * @return Returns the method that matches the search criteria. If there is
	 *         no method matching the search criteria, returns null.
	 */
	public static Method getMethodByUniqueName(String methodName, Class<?> type) {
		List<Method> methods = getMethodsByUniqueName(methodName, type);
		int count = methods.size();
		switch (count) {
		case 0:
			return null;
		case 1:
			return methods.iterator().next();
		default:
			@SuppressWarnings("nls")
			String msg = StringUtils.concat(
				"More than one methods with the same name ",
				methodName,
				" found in class",
				type.getName());   
			throw new RuntimeException(msg);
		}
	}
	
	/**
	 * Gets the public method of the specified class with the specified name.
	 *
	 * @param methodName
	 *        Method name.
	 * @param type
	 *        Type on which the method is searched.
	 * @param excludeStatic 
	 *        If true, then static methods are not included
	 * 
	 * @return Returns the method that matches the search criteria. If there is
	 *         no method matching the search criteria, returns null.
	 */
	public static Method getPublicMethodByUniqueName(String methodName, Class<?> type, boolean excludeStatic) {
		List<Method> allMethods = getPublicMethodsByName(methodName, type);
		List<Method> methods;		
		if (excludeStatic) {
			methods = new ArrayList<Method>();
			for (Method method : allMethods) { 
				if (!Modifier.isStatic(method.getModifiers())) {
					methods.add(method);
				}	
			}		
		} else {
			methods = allMethods;
		}
		
		int count = methods.size();
		switch (count) {
		case 0:
			return null;
		case 1:
			return methods.iterator().next();
		default:
			@SuppressWarnings("nls")
			String msg = StringUtils.concat(
				"More than one methods with the same name ",
				methodName,
				" found in class",
				type.getName());   
			throw new RuntimeException(msg);
		}
	}
	
	/**
	 * Gets the method of the specified class with the specified name.
	 * 
	 * Public and declared methods of the specified type are included.
	 * The specified method name must be unique in the specified type,
	 * otherwise a runtime exception is thrown.
	 * 
	 * Tweaked this so that it returns inherited protected and default
	 * access methods. From these only the closest method to the inspected 
	 * type is added. 
	 * 
	 * @param methodName
	 *            Method name.
	 * @param type
	 *            Type on which the method is searched.
	 * 
	 * @return Returns the method that matches the search criteria. If there is
	 *         no method matching the search criteria, returns null.
	 */
	public static List<Method> getMethodsByUniqueName(String methodName, Class<?> type) {
		Map<String, Method> namedMethods = new HashMap<String, Method>();
		
		Set<Method> allMethods = new HashSet<Method>();
		allMethods.addAll(Arrays.asList(type.getDeclaredMethods()));
		allMethods.addAll(getPublicMethods(type));
		
		if(type.getSuperclass()==null) {
			return SelectionUtils.selectByName(methodName, allMethods, Method.class);
		}
		
		/*
		 * Add the collected methods to the map to avoid adding declared
		 * methods that override inherited protected/default methods.
		 */
		for(Method m : allMethods) {
			namedMethods.put(m.getName(), m);
		}
		
		List<Method> inheritedMethods = new ArrayList<Method>();
		
		for (Class<?> clazz = type.getSuperclass(); clazz != null; clazz = clazz.getSuperclass()) {
			Method[] methods = clazz.getDeclaredMethods();
			for(Method m : methods) {
				if(Modifier.isProtected(m.getModifiers()) && !Modifier.isAbstract(m.getModifiers())) {
					if(!namedMethods.containsKey(m.getName())) {
						namedMethods.put(m.getName(), m);
						inheritedMethods.add(m);
					}
				}
				if(isDefaultAccess(m.getModifiers()) && !Modifier.isAbstract(m.getModifiers()) && type.getPackage().equals(clazz.getPackage())) {
					if(!namedMethods.containsKey(m.getName())) {
						namedMethods.put(m.getName(), m);
						inheritedMethods.add(m);
					}
				}
				if(Modifier.isPrivate(m.getModifiers()) && !Modifier.isAbstract(m.getModifiers()) && clazz==type) {
					if(!namedMethods.containsKey(m.getName())) {
						namedMethods.put(m.getName(), m);
						inheritedMethods.add(m);
					}
				}
			}
		}
		
		allMethods.addAll(inheritedMethods);
		
		return SelectionUtils.selectByName(methodName, allMethods, Method.class);
	}
	
	/**
	 * @param mod
	 * @return Returns true, if the access modifier is default.
	 */
	public static boolean isDefaultAccess(int mod) {
		return !Modifier.isPrivate(mod) && !Modifier.isProtected(mod) && !Modifier.isPublic(mod);
	}
	
	/**
	 * Invokes a method on an object given the unique name of the method
	 * and an argument list. When finding the method, all declared methods
	 * of the object class are considered.
	 * 
	 * NOTE:
	 * In case of overloaded methods that have declared parameter types lists
	 * for which the parameter types are assignable to each other on-by-one
	 * this will invoke the first method matched. 
	 * 
	 * @param <T>
	 *        Type of the return type.
	 * @param methodName
	 *        Name of the method.
	 * @param owner
	 *        Runtime object to invoke the method on.
	 * @param args
	 *        Varargs list of arguments for the method.
	 * 
	 * @return Returns the method result, or null if it is void.
	 */
	@SuppressWarnings({ "unchecked", "nls" })
	public static <T> T invokeMethodByUniqueName(Object owner, String methodName, Object... args) {
		List<Method> methods = getMethodsByUniqueName(methodName, owner.getClass());
		if(methods.isEmpty()) {
			String msg = "Method " + methodName + " not found in type " + owner.getClass().getName();
			throw new RuntimeException(msg);
		}
		Method m = null;
		for(Method mtd : methods) {
			if(argumentsMatchParameterTypes(mtd.getParameterTypes(), args)) {
				m = mtd;
				break;
			}
		}
		if(m==null) {
			String msg = "Method " + methodName + " not found in type " + owner.getClass().getName()
						+ "with signature that can accept the given parameters.";
			throw new RuntimeException(msg);
		}
		try {
			if(!m.isAccessible()) {
				m.setAccessible(true);
			}
			return (T) m.invoke(owner, args);
		} catch (IllegalArgumentException e) {			
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e.getCause());
		}
	}
	
	/**
	 * Gets all methods of the specified class with the specified name.
	 * 
	 * @param methodName
	 *            Method name.
	 * @param type
	 *            Type on which the method is searched.
	 * 
	 * @return Returns the method that matches the search criteria. If there is
	 *         no method matching the search criteria, returns null.
	 */
	public static List<Method> getPublicMethodsByName(String methodName, Class<?> type) {
		List<Method> list = getPublicMethods(type);
		return SelectionUtils.selectByName(methodName, list, Method.class);
	}
	
	/**
	 * Gets all public methods of a type.
	 * 
	 * Normally type.getMethods() should do the job. But IBM JREs don't follow 
	 * the standard. IBM JREs don't return two methods with the same signature
	 * that are defined in different interfaces the <code>type</code> implements 
	 * and are not implemented by <code>type</code>. 
	 * This method gets all methods, even in this case.
	 * 
	 * @param type
	 * 
	 * @return Returns a list with all public 
	 */
	private static Set<Method> getPublicMethodsForIbmJre(Class<?> type) {
		Set<Method> methods = new HashSet<Method>();
		CollectionUtils.addAll(methods, type.getMethods());
		Class<?>[] interfaces = type.getInterfaces();
		for (Class<?> intfc : interfaces) {
			Set<Method> interfaceMethods = getPublicMethodsForIbmJre(intfc);
			methods.addAll(interfaceMethods);
		}
		return methods;
	}
	
	/**
	 * Gets all public methods of a type.
	 * 
	 * Normally type.getMethods() should do the job. But IBM JREs don't follow 
	 * the standard. IBM JREs don't return two methods with the same signature
	 * that are defined in different interfaces, implemented by <code>type</code>.
	 * This method gets all methods.
	 * 
	 * @param type
	 * 
	 * @return Returns a list with all public 
	 */
	public static Method[] getPublicMethodsOfType(Class<?> type) {
		if (isIbmJre) {
			Set<Method> set = getPublicMethodsForIbmJre(type);
			return CollectionUtils.toArray(set, new Method[0]);
		} else {
			return type.getMethods();
		}
	}
	
	
	/**
	 * Gets all public methods of the specified class excluding
	 * synthetic or bridge methods.
	 * 
	 * @param type
	 *            Type on which the method is searched.
	 * 
	 * @return Returns a list with methods.
	 */
	public static List<Method> getPublicMethods(Class<?> type) {		
		Method[] all = getPublicMethodsOfType(type);		
		List<Method> list = new ArrayList<Method>();		
		MultipleValuesMap<String, Method> index = new MultipleValuesMap<String, Method>();		
		Set<Method> genericMethods = new HashSet<Method>();
		
		for (Method candidate : all) {
			if (!(candidate.isSynthetic() || candidate.isBridge())) {
				String name = candidate.getName();
				Class<?>[] args = candidate.getParameterTypes();				
				Method method = getPublicMethod(name, type, args);
				if (method.equals(candidate)) {
					list.add(method);
					index.put(name, method);
					if (GenericsUtils.isVariableParameterType(method)) {
						genericMethods.add(method);
					}
				}
			}
		}
		for (Method genericMethod : genericMethods) {
			Set<Method> named = index.get(genericMethod.getName());			
			if (named.size()>1) {
				named.remove(genericMethod);
				for (Method method : named) {
					if (GenericsUtils.isGenericParametersMatch(genericMethod, method)) {
						list.remove(genericMethod);
						break;
					}
				}				
			}
		}
		return list;
	}
	
	

	/**
	 * Returns an array with all methods declared in the type that match one of
	 * the method names provided in the String[].
	 * 
	 * If the methodNames array is empty or null, then synthetic methods are
	 * ignored. Inherited methods are not included.
	 * 
	 * @param methodNames
	 *            methodNames to match
	 * @param type
	 *            the type to inspect
	 * @return an array of methods of the type that match a methodName within
	 *         methodNames
	 */
	public static Method[] getDeclaredMethods(String[] methodNames, Class<?> type) {
		boolean methodNamesDefined = 
			(methodNames != null && methodNames.length > 0);
		
		if (methodNamesDefined) {
			/*
			 * get only the declared methods with the specified names.
			 */
			Method[] methods = new Method[methodNames.length];
			int ctr = 0;
			for (String methodName : methodNames) {
				int countOfMethodsWithThisName=0;
				for (Method method : type.getDeclaredMethods()) {					
					if (method.getName().equals(methodName)) {
						methods[ctr] = method;
						ctr++;
						countOfMethodsWithThisName++;
					}
				}
				if (countOfMethodsWithThisName==0) {
					@SuppressWarnings("nls") 
					String msg = StringUtils.concat(
						"Method ", methodName , " not declared in class ", type.getName()); 
					throw new RuntimeException(msg);
				}

			}						
			return methods;
			
		} else {
			/*
			 * get all declared methods.
			 */
			List<Method> list = new ArrayList<Method>();
			for (Method method : type.getDeclaredMethods()) {
				if (!method.isSynthetic()) {
					list.add(method);
				}
			}
			return list.toArray(new Method[0]);
		}
		
		
	}

	/**
	 * Gets the value of a field on an object.
	 * 
	 * The method will box any {@link Exception} thrown during java reflection
	 * calls inside a {@link RuntimeException}.
	 * 
	 * @param field
	 *            Field that will be evaluated on an object.
	 * @param obj
	 *            Object on which the field is evaluated.
	 * 
	 * @return Returns the value of the field on the specified object.
	 */
	public static Object get(Field field, Object obj) {
		try {
			return field.get(obj);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Gets the values of a List of Fields from an Object.
	 * 
	 * @param fields
	 * @param obj
	 * @return List with fields' values on the specified instance.
	 */
	public static List<Object> get(List<Field> fields, Object obj) {
		List<Object> results = new ArrayList<Object>();
		for(Field f : fields) {
			Object o = get(f, obj);
			if(o!=null) {
				results.add(o);
			}
		}
		return results;
	}
	
	/**
	 * Gets the value of a field on an object.
	 * 
	 * The method searches for the field in all super classes of the
	 * object. Because of the way the allFields() method works, priority
	 * is given on fields that are declared as close as possible to the
	 * class of parameter obj. For example, if the superclass of obj's class
	 * declares a private field that has the same name with one of the fields
	 * declared in obj's class, this will not be accessed. Instead, the method
	 * will access the field declared directly in obj's class.
	 *  
	 * @param fieldName
	 *            Name of the field that will be evaluated on an object.
	 * @param obj
	 *            Object on which the field is evaluated.
	 * 
	 * @return Returns the value of the field on the specified object.
	 */
	public static Object get(String fieldName, Object obj) {
		TypeAnalysis analysis = TypeAnalysis.analyze(obj.getClass());		
		Field field = analysis.getFirstFieldByName(fieldName);
		if(field != null) {
			setAccessible(field);
			return get(field, obj);
		} else {
			String msg = "No such field: " + fieldName; //$NON-NLS-1$
			throw new RuntimeException(msg);
		}
	}
	
	/**
	 * Indicates if the specified class has a field with the specified 
	 * name and type.
	 * 
	 * @param type
	 *        Type checked for field excistence.
	 * @param fieldName
	 *        Name of field
	 * @param fieldType 
	 *        Type of field.
	 *        
	 * @return Returns true if the class has a field with this name and type.
	 */
	public static boolean hasField(Class<?> type, String fieldName, Class<?> fieldType) {
		try {
			Field field = type.getField(fieldName);
			return field.getType().equals(fieldType);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchFieldException e) {
			return false;
		}
	}
	
	/**
	 * Sets the value of a field on an object.
	 * 
	 * The method will box any {@link Exception} thrown during java reflection
	 * calls inside a {@link RuntimeException}.
	 * 
	 * @param field
	 *            Field that will be set on an object.
	 * @param val
	 *            New value for the field.
	 * @param obj
	 *            Object on which the field value is changed.
	 */
	public static void set(Field field, Object val, Object obj) {
		try {
			if (!Modifier.isFinal(field.getModifiers())) {
				field.set(obj, val);
			}
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Sets the value of a field on an object.
	 * 
	 * The method searches for the field in all super classes of the
	 * object. Because of the way the allFields() method works, priority
	 * is given on fields that are declared as close as possible to the
	 * class of parameter obj. For example, if the superclass of obj's class
	 * declares a private field that has the same name with one of the fields
	 * declared in obj's class, this will not be accessed. Instead, the method
	 * will access the field declared directly in obj's class.
	 * 
	 * The method will box any {@link Exception} thrown during java reflection
	 * calls inside a {@link RuntimeException}.
	 * 
	 * @param fieldName
	 *            Name of the field that will be set on an object.
	 * @param val
	 *            New value for the field.
	 * @param obj
	 *            Object on which the field value is changed.
	 */
	public static void set(String fieldName, Object val, Object obj) {
		TypeAnalysis analysis = TypeAnalysis.analyze(obj.getClass());		
		Field field = analysis.getFirstFieldByName(fieldName);
		if(field != null) {
			setAccessible(field);
			set(field, val, obj);
		} else {
			String msg = "No such field: " + fieldName; //$NON-NLS-1$
			throw new RuntimeException(msg);
		}
	}
	
	/**
	 * Sets the first field with the specified declared type of
	 * an object with a value.
	 * <br/>
	 * This method should be use with caution in test classes only
	 * (for example to inject a dependency without hard-coding the
	 * field name, if it is known that there is only one field of
	 * the specified type on the class of the object we want to 
	 * inject to).
	 * 
	 * @param <T>
	 *        Declared type of the field.
	 * @param declaredType
	 *        Declared type of the field.
	 * @param val
	 *        Value to set on the field.
	 * @param obj
	 *        Object whose class has the field.
	 */
	public static <T> void setFirstFieldByDeclaredType(Class<T> declaredType, T val, Object obj) {
		if(obj==null) {
			return;
		}
		TypeAnalysis analysis = TypeAnalysis.analyze(obj.getClass());		
		for(Field field : analysis.getAllFields()) {
			if(field.getType()==declaredType) {
				set(field, val, obj);
				return;
			}
		}
	}
	
	/**
	 * Sets the value of a property on an object.
	 * 
	 * If the property has a setter method, then it will be called.
	 * If the property does not have a setter method, the field
	 * will be set. If there is no such field, then the method will
	 * ignore it. <br/>
	 * The method will box any {@link Exception} thrown during java reflection
	 * calls inside a {@link RuntimeException}.
	 * 
	 * @param property
	 *            Name of the property.
	 * @param val
	 *            New value for the field.
	 * @param obj
	 *            Object on which the field value is changed.
	 */
	public static void setProperty(String property, Object val, Object obj) {	
		BeanPropertyDefinition<?> bpd = mandatoryPropertyOfClass(property, obj.getClass());
		Method setter = bpd.getSetter();
		if (setter!=null) {
			Object[] args = {val};
			invoke(setter, obj, args);
		} else {
			set(property, val, obj);			
		}
	}
	
	/**
	 * Gets the value of a property on an object.
	 * 
	 * If the property has a getter method, then it will be called.
	 * If the property does not have a getter method, the field
	 * will be get. If there is no such field, then a RuntimeException
	 * with cause a NoSuchFieldException will be thrown. <br/>
	 * The method will box any {@link Exception} thrown during java reflection
	 * calls inside a {@link RuntimeException}.
	 * @param property
	 *            name of the property.
	 * @param obj
	 *            Object on which the property is accessed.
	 *            
	 * @return Returns the value of this property.
	 */
	public static Object getProperty(String property, Object obj) {		
		BeanPropertyDefinition<?> bpd = mandatoryPropertyOfClass(property, obj.getClass());
		Method getter = bpd.getGetter();
		if (getter!=null) {
			return invoke(getter, obj, GETTER_ARGS);
		} 
		return get(property, obj);
	}
	
	/**
	 * Gets the values of a bean's properties in a map keyed with the property names.
	 * 
	 * This method transforms a javabean to a map.
	 * @param obj
	 *        Java bean to transform to map.
	 *            
	 * @return Returns a map that has an entry for each property of the specified
	 *         object.
	 */
	public static Map<String,Object> getProperties(Object obj) {
		TypeAnalysis analysis = TypeAnalysis.analyze(obj.getClass());
		Set<String> properties = analysis.getNamesOfProperties();
		Map<String, Object> map = new HashMap<String, Object>();
		for (String property : properties) {
			Object value = getProperty(property, obj);
			map.put(property, value);
		}		
		return map;
	}
	
	/**
	 * Gets the values of the specified bean's properties in a map keyed 
	 * with the property names.
	 * 
	 * @param obj
	 *        Object to get the property values from.
	 * @param properties 
	 *        Names of the properties that will be put in the map. 
	 *            
	 * @return Returns a map that has an entry for each property of the specified
	 *         object.
	 */
	public static Map<String,Object> getProperties(Object obj,String...properties) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (String property : properties) {
			Object value = getProperty(property, obj);
			map.put(property, value);
		}		
		return map;
	}
	
	/**
	 * Gets the field that has the specified annotation.
	 * 
	 * The field must be unique.
	 * 
	 * @param fields
	 * @param annotation
	 * @return Returns the field with the specified annotation. Returns null if
	 *         there is no field with the specified annotation.
	 * @throws RuntimeException
	 *             if there are more than one fields annotated with the
	 *             specified annotation.
	 */
	public static Field getUnique(Collection<Field> fields,
			Class<? extends Annotation> annotation) {
	
		List<Field> annotateds = getAnnotated(fields, annotation);
		if (annotateds.size() > 1) {
			String message = "More than one fields marked with annotation "  //$NON-NLS-1$
				           + annotation.getName();
			throw new RuntimeException(message);
		}
		if (annotateds.size() == 0) {
			return null;
		}
		return annotateds.get(0);
	}

	/**
	 * Gets the getter methods of a list of properties..
	 * 
	 * @param properties
	 *            Names of properties.
	 * @param type
	 *            Type on which the getter methods is searched.
	 * 
	 * @return Returns an array with the getter methods of the specified
	 *         properties.
	 */
	public static Method[] getPropertyGetters(String[] properties, Class<?> type) {
		TypeAnalysis analysis = TypeAnalysis.analyze(type);
		Method[] methods = new Method[properties.length];
		for (int i = 0; i < properties.length; i++) {
			BeanPropertyDefinition<?> bpd = analysis.getFirstPropertyByName(properties[i]);
			if (bpd!=null) {
				methods[i] = bpd.getGetter();				
			}
		}		
		return methods;
	}
	
	/**
	 * Gets the constructor of a class that is compatible with the args list.
	 * 
	 * @see #getConstructor(Class, Class...)
	 * 
	 * @param className
	 * @param args
	 * @return The constructor.
	 */
	public static <T> Constructor<T> getConstructor(String className, Class<?>...args) {
		try {
			@SuppressWarnings("unchecked")
			Class<T> clazz = (Class<T>)Class.forName(className);
			return getConstructor(clazz, args);
		} catch (ClassNotFoundException cnfe) {
			throw new RuntimeException(cnfe);			
		}
	}
	
	/**
	 * Gets the constructor of a class given an array of 
	 * argument types. All caught exceptions are re-thrown as a
	 * {@link RuntimeException}.
	 * 
	 * The constructor is found by checking all constructors
	 * of the class that have the same number of parameters as 
	 * the length of the given array and the array element types are
	 * assignable from the constructor parameter types one-by-one.
	 * 
	 * Use this with caution, as it is possible this check to be true
	 * for more than one constructors of a class.
	 * 
	 * @param <T>
	 * @param clazz
	 * @param args
	 * @return a {@link Constructor} of the class.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>...args) {
		Constructor<T>[] constructors = null;
		try {
			constructors = (Constructor<T>[]) clazz.getConstructors();
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
		
		for(Constructor<T> c : constructors) {
			if(argumentTypesMatchParameterTypes(c.getParameterTypes(), args)) {
				return c;
			}
		}		
		return null;
	}
	
	/**
	 * Checks if the type of an argument matches the type of a parameter.
	 * If the parameterType is primitive, the check is made against its
	 * wrapper class. 
	 * 
	 *  NOTE: A null argument type is assumed to always be a match for
	 *       the corresponding parameter type, unless the parameter type
	 *       is a primitive.
	 * 
	 * @param paramType
	 *        Type of the declared parameter.
	 *        
	 * @param argType
	 *        Type of the argument.
	 *        
	 * @return Returns true, if the argument is acceptable.
	 */
	static boolean argumentMatchesParameter(Class<?> paramType, Class<?> argType) {
		if(argType==null){
			return !paramType.isPrimitive();
		}
		if(paramType.isPrimitive()) {
			if(argType.isPrimitive()) {
				return paramType==argType;
			}
			return primitives2wrappers.get(paramType).isAssignableFrom(argType);
		}
		return paramType.isAssignableFrom(argType);
	}
	
	/**
	 * Gets the wrapper class of the specified primitive class.
	 * 
	 * @param primitive
	 * 
	 * @return Returns the wrapper class of the specified class.
	 *         If the specified class is not primitive, returns
	 *         the same class.
	 */
	public static Class<?> getWrapperClass(Class<?> primitive) {
		Class<?> wrapper = primitives2wrappers.get(primitive);
		return Utils.notNull(wrapper, primitive);
	}

	
	/**
	 * Checks if a number of object arguments is a match for a given
	 * array of parameter types. This is achieved by asserting that
	 * the classes of the object arguments are assignable one-by-one
	 * to the classes defined as the parameter types.
	 * 
	 * @param paramTypes
	 *        Array of parameter types.
	 * @param arguments
	 *        Objects that are used as arguments (ordered).
	 * @return true, if the object arguments can be assigned one-by-one to
	 *         the defined parameter types.
	 */
	public static boolean argumentsMatchParameterTypes(Class<?>[] paramTypes, Object...arguments) {
		if(arguments == null) {
			return paramTypes.length==0;
		}
		Class<?>[] argTypes = new Class<?>[arguments.length];
		for(int i=0; i<arguments.length; i++) {
			if(arguments[i]!=null) {
				argTypes[i] = arguments[i].getClass();
			} else {
				argTypes[i] = null;
			}
		}
		return argumentTypesMatchParameterTypes(paramTypes, argTypes);
	}
	
	/**
	 * Checks if a number of argument types is a match for a given
	 * array of parameter types. This is achieved by asserting that
	 * the classes of the object arguments are assignable one-by-one
	 * to the classes defined as the parameter types.
	 * 
	 * NOTE: A null argument type is assumed to always be a match for
	 *       the corresponding parameter type, unless the parameter type
	 *       is a primitive.
	 * 
	 * @param paramTypes
	 *        Array of parameter types.
	 * @param argTypes
	 *        Argument types (ordered)
	 * @return true, if the object arguments can be assigned one-by-one to
	 *         the defined parameter types.
	 */
	public static boolean argumentTypesMatchParameterTypes(Class<?>[] paramTypes, Class<?>...argTypes) {
		if(argTypes == null) {
			return paramTypes.length==0;
		}
		if(paramTypes.length != argTypes.length) {
			return false;
		}
		
		for(int i = 0; i < paramTypes.length; i++) {
			if(!argumentMatchesParameter(paramTypes[i], argTypes[i])){
				return false;
			}
		}		
		return true;
	}
		
	
	
	
	/**
	 * Gets the copy constructor of a class, if this class has one.
	 * 
	 * A copy constructor is a constructor that takes as argument an 
	 * instance of the specified class and creates a new instance by
	 * copying the specified argument. According to Josh Bloch a
	 * copy-constructor is a better alternative to using <code>clone()</code>. <br/>
	 * This method returns the constructor that takes an instance of the
	 * specified class as argument. There is no guarantee that this 
	 * constructor is actually a copy constructor, so this method should 
	 * be used with caution.
	 * 
	 * @param clazz
	 *        
	 * @param <T>
	 * 
	 * @return Returns the copy-constructor of the specified class, if the class
	 *         has a copy-constructor. If the class does not have a copy-constructor, 
	 *         returns null.
	 */
	public static <T> Constructor<T> getCopyConstructor(Class<T> clazz) {
		Class<?>[] args = {clazz};
		return getConstructor(clazz, args);
	}
	
	/**
	 * Gets the no argument constructor of a class, if the class has one.
	 * 
	 * @param clazz
	 *         
	 * @param <T>
	 * 
	 * @return Returns the no argument constructor of the specified class, if the class
	 *         has one. If the class does not have a default constructor, returns null.
	 */
	public static <T> Constructor<T> getNoArgConstructor(Class<T> clazz) {
		Class<?>[] args = new Class<?>[0];
		try {
			return clazz.getConstructor(args);
		} catch (SecurityException se) {
			throw new RuntimeException(se);
		} catch (NoSuchMethodException e) {
			return null;
		}
	}
	
	/**
	 * Gets the no argument constructor of a class, if the class has one.
	 * 
	 * @param clazz
	 *         
	 * @param <T>
	 * 
	 * @return Returns the no argument constructor of the specified class, if the class
	 *         has one. If the class does not have a default constructor, returns null.
	 */
	public static <T> Constructor<T> getNoArgDeclaredConstructor(Class<T> clazz) {
		Class<?>[] args = new Class<?>[0];
		try {
			return clazz.getDeclaredConstructor(args);
		} catch (SecurityException se) {
			throw new RuntimeException(se);
		} catch (NoSuchMethodException e) {
			return null;
		}
	}
	
	
	/**
	 * Creates a new instance of a class using a given
	 * constructor and an appropriate array of arguments.
	 * All caught exceptions are re-thrown as a
	 * {@link RuntimeException}.
	 * 
	 * @param <T>
	 * @param constructor 
	 * @param args ordered array of arguments. 
	 * 
	 * @return Returns a new instance of the class.
	 */
	public static <T> T newInstance(Constructor<T> constructor, Object...args) {		
		try {
			return constructor.newInstance(args);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e.getTargetException());
		}
	}
	
	/**
	 * Loads the class with the specified name and creates a new instance
	 * using its public no-argument constructor.
	 * 
	 * @param className
	 *        Name of the class.
	 * @param <T>
	 *        Type of the new instance.
	 * 
	 * @return Returns a new instance of the class with the specified name.
	 */
	public static <T> T newInstance(String className) {
		try {
			@SuppressWarnings("unchecked")
			Class<T> clazz = (Class<T>)Class.forName(className);
			return clazz.newInstance();
		} catch (ClassNotFoundException cnfe) {
			throw new RuntimeException(cnfe);			
		} catch (InstantiationException ie) {
			throw new RuntimeException(ie);
		} catch (IllegalAccessException iae) {
			throw new RuntimeException(iae);
		}
	}
	
	/**
	 * Creates a new instance of a class using its no argument
	 * constructor.
	 * 
	 * @param <T>
	 * @param clazz
	 * @return Returns a new instance of the class.
	 */
	public static <T> T newInstance(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Creates an instance of a class given its name and a list of constructor args.
	 * 
	 * @see #newInstance(Class, Object...)
	 * 
	 * @param className
	 * @param args
	 * @return Instance.
	 */
	public static <T> T newInstance(String className, Object...args) {
		try {
			@SuppressWarnings("unchecked")
			Class<T> clazz = (Class<T>)Class.forName(className);
			return newInstance(clazz, args);
		} catch (ClassNotFoundException cnfe) {
			throw new RuntimeException(cnfe);			
		} 
	}
	
	/**
	 * Creates a new instance of a class given a list of arguments
	 * for one of its constructors.
	 * 
	 * @param <T>
	 * @param clazz
	 * @param args
	 * @return Returns a new instance of the class.
	 */
	public static <T> T newInstance(Class<T> clazz, Object...args) {		
		Class<?>[] argTypes = new Class<?>[args.length];
		for (int i = 0; i < args.length; i++) {
			argTypes[i] = args[i].getClass();
		}
		Constructor<T> constructor = getConstructor(clazz, argTypes);
		if (constructor==null) {
			String msg = "No constructor with such arguments"; //$NON-NLS-1$
			throw new RuntimeException(msg);
		}
		return newInstance(constructor, args);
	}
	
	/**
	 * Creates a new instance of the class with the specified class name.
	 * 
	 * @param className
	 *        Name of class.
	 * @param <T>
	 *        Type of object being returned.
	 * 
	 * @return If the specified class name is not empty, returns the new 
	 *         instance, otherwise returns null.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T attemptNewInstance (String className) {
		if (StringUtils.isNullOrBlank(className)) {
			return null;
		}
		return (T) ReflectionUtils.newInstance(className);
	}
	
	
	/**
	 * Sets null to all fields of the super type.
	 * 
	 * This method is use
	 * @param ob
	 * @param annotations
	 */
	public static void setNullToDuplicateFieldsOfSuper
	(Object ob, Class<? extends Annotation>[] annotations) {
		Class<?> myClass = ob.getClass();
		Class<?> superClass = myClass.getSuperclass();
		List<Field> superDeclaredFields = allFields(superClass, superClass);
		Set<Field> annotated = new HashSet<Field>();
		for (Class<? extends Annotation> annotation : annotations) {
			List<Field> withThisAnno = getAnnotated(superDeclaredFields, annotation);
			annotated.addAll(withThisAnno);
		}
		for (Field field : annotated) {
			if (!field.getType().isPrimitive()) {
				set(field, null, ob);
			}
		}		
	}
	

	/**
	 * Shows if an object is instance of a class.
	 * 
	 * The method uses runtime class information in order to find if the
	 * object belongs to the class. Therefore if the object is null,
	 * the method will return null.
	 * 
	 * @param object
	 *        Object checked for being instance of a class.
	 * @param clazz
	 *        Class.
	 * @return Returns true if object is an instance of clazz.
	 */
	public static boolean isInstanceOf(Object object, Class<?> clazz) {
		if (object == null) {
			return false;
		}
		Class<?> objectType = object.getClass();
		return clazz.isAssignableFrom(objectType);
	}

	/**
	 * Invokes a method on an object.
	 * 
	 * The method will box any {@link Exception} thrown during java reflection
	 * calls inside a {@link RuntimeException}.
	 * 
	 * @param method
	 *            Method to invoke.
	 * @param target
	 *            Object on which the method will be invoked.
	 * @param args
	 *            Arguments for the method invocation. If the method does not
	 *            have any arguments, then this parameter must be null.
	 * 
	 * @return Returns the object that is returned by the method. If the method
	 *         is void, then returns null.
	 */
	public static Object invoke(Method method, Object target, Object... args) {
		setAccessible(method);
		try {
			return method.invoke(target, args);
		} catch (IllegalArgumentException ilarex) {			
			logInvokeException(method, target, args);			
			throw invocationException(method,ilarex);
		} catch (IllegalAccessException ilacex) {
			logInvokeException(method, target, args);
			throw invocationException(method,ilacex);
		} catch (InvocationTargetException intaex) {
			logInvokeException(method, target, args);
			throw invocationException(method,intaex.getCause());
		}
	}
	
	
	/**
	 * Creates a {@link RuntimeException} that wraps an exception
	 * thrown during a method invocation.
	 * 
	 * @param method
	 * @param ex
	 * 
	 * @return returns
	 */
	static RuntimeException invocationException(Method method, Throwable ex) {		
		@SuppressWarnings("nls")
		String msg = StringUtils.concat(
			"Exception during invocation of method ",
		    method.getName(),
		    " of class ",
		    method.getDeclaringClass().getName());
		return new RuntimeException(msg,ex);
	}
	
	
	/**
	 * Logs context on {@link #invoke(Method, Object, Object...)} upon an Exception.
	 * 
	 * @param method
	 * @param target
	 * @param args
	 */
	@SuppressWarnings("nls")
	private static void logInvokeException(Method method, Object target, Object... args) {
		String mthd = StringUtils.toString(method);
		String trgt = StringUtils.toString(target);
		String trgtCls = target==null ? StringConstants.EMPTY : target.getClass().getName();
		String argz = StringUtils.array2String(args, StringConstants.COMMA);
		
		String msg = StringUtils.concat(
			"Method " + StringUtils.squareBrackets(mthd), " invoked on ", trgt, 
			StringUtils.squareBrackets(trgtCls), " with args ", StringUtils.squareBrackets(argz));
		logger.error(msg);
	}

	/**
	 * Gets a list with the values of the specified fields in the 
	 * specified object that belong to the specified type.
	 * 
	 * @param <T> Type of object.
	 * @param target Target object.
	 * @param fields List of field objects.
	 * @param type Type of fields.
	 * @param includeNulls If true, then nulls will also be included in the list.
	 * 
	 * @return Returns a list with the same size as the <code>fields</code> list.
	 *         Each element of the returned list is the value of the associated
	 *         field in the specified object. If a field has a value of null, or
	 *         a value that is not assignable to the specified type, then the
	 *         element in the returned list is null.
	 */
	public static <T> List<T> getValuesOfFields
	(Object target, List<Field> fields, Class<T> type, boolean includeNulls) {
		List<T> list = new ArrayList<T>();
		for (Field field : fields) {			
			Object value = get(field, target);
			if (value!=null) {
				if (!type.isAssignableFrom(value.getClass())) {
					value = null;
				}
			}
			boolean isAdded = value!=null || includeNulls;
			if (isAdded) {
				@SuppressWarnings("unchecked") T t = (T)value;
				list.add(t);
			}			
		}		
		return list;
	}
	

	/**
	 * Indicates if a class is a Collection class.
	 * 
	 * @param clazz
	 *        Class checked for being a Collection class.
	 *        
	 * @return Returns true if the class is a Collection class.
	 */
	public static boolean isCollection(Class<?> clazz) {
		return Collection.class.isAssignableFrom(clazz);
	}
	
	/**
	 * Indicates if a class is an array class.
	 * 
	 * @param clazz
	 *        Class checked for being an array class.
	 *        
	 * @return Returns true if the class is an array class.
	 */
	public static boolean isArray(Class<?> clazz) {
		return clazz.isArray();
	}
	
	/**
	 * Determines if a class is concrete, i.e. it is not abstract
	 * or an interface. Null input returns false.
	 * 
	 * @param clazz
	 * 
	 * @return True, if the class is concrete.
	 */
	public static boolean isConcreteClass(Class<?> clazz) {
		if(clazz == null) { 
			return false; 
		}
		if(clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
			return false;
		}
		return true;
	}
	
	/**
	 * Determines if a class is concrete, i.e. it is not abstract
	 * or an interface. Null input returns false.
	 * 
	 * @param clazz
	 * 
	 * @return True, if the class is concrete.
	 */
	public static boolean isAbstractClass(Class<?> clazz) {
		if(clazz == null) { 
			return false; 
		}
		if(clazz.isInterface()) {
			return false;
		}
		if (Modifier.isAbstract(clazz.getModifiers())) {
			return true;
		}
		return false;
	}
	

	
	/**
	 * Finds if a method is implemented by the specified class.
	 * 
	 * @param method
	 * @param clazz
	 * 
	 * @return Returns true if the method is implemented.
	 */
	public static boolean isImplemented(Method method, Class<?> clazz) {
		if (clazz.isInterface()) {
			return false;
		}
		String name = method.getName();
		Class<?>[] paramTypes = method.getParameterTypes();
		Method implemented = ReflectionUtils.getPublicMethod(name, clazz, paramTypes);
		if (implemented==null) {
			return false;
		}		
		if (Modifier.isAbstract(implemented.getModifiers())) {
			return false;			
		}
		return true;		
	}
	
	
	/**
	 * Finds if a class has a default constructor.
	 * 
	 * Works also for abstract classes with implicit default
	 * constructors.
	 * 
	 * @param clazz
	 * 
	 * @return Returns true if the specified class has a default constructor.
	 */
	public static boolean isNoDefaultConstructor(Class<?> clazz) {
		Constructor<?> def = getNoArgConstructor(clazz);
		if (def!=null) {
			return false;
		}
		def = getNoArgDeclaredConstructor(clazz);
		if (def!=null) {
			boolean isPrivate = Modifier.isPrivate(def.getModifiers());
			return isPrivate;
		}
		@SuppressWarnings("rawtypes")
		Constructor[] constructors = clazz.getConstructors();
		if (constructors.length!=0) {
			return true;
		}
		constructors = clazz.getDeclaredConstructors();
		if (constructors.length!=0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the return type of the abstrct method can be
	 * supported by the return type of the candidate method.
	 * 
	 * @param abstrct
	 * @param candidate
	 * 
	 * @return true if the return type can be supported, otherwise false.
	 */
	public static boolean returnTypeIsCompatible(Method abstrct, Method candidate) {
		return abstrct.getReturnType().equals(void.class) 
		|| abstrct.getReturnType().isAssignableFrom(candidate.getReturnType());
	}
	
	/**
	 * Returns the type hierarchy of a class, up to a base type. First,
	 * all the super classes (up to and including the baseType, if applicable) are
	 * found. Then all the interfaces declared by these types are added
	 * to the results, starting from the closest super class. Each type is
	 * only included once, but the order in the list depends on the
	 * aforementioned procedure.
	 * 
	 * @param type
	 * @param baseType 
	 *        Base type beyond which the hierarchy is not fetched. Each
	 *        and every one of the returned types must be a sub-type of this
	 *        type.
	 * 
	 * @return Ordered type hierarchy of the specified type.
	 */
	public static List<Class<?>> getTypeHierarchy(Class<?> type, Class<?> baseType) {
		List<Class<?>> results = new ArrayList<Class<?>>();
		if(type==null) {
			return results;
		}
		if(type.isInterface()) {
			return getInterfaceHierarchy(type, baseType, null);
		}
		Class<?> temp = type;
		/*
		 * While a super class exists and baseType is null or it is a super
		 * type of the super class.
		 */
		while(temp.getSuperclass()!=null && (baseType==null || baseType.isAssignableFrom(temp.getSuperclass()))) {
			results.add(temp.getSuperclass());
			temp = temp.getSuperclass();
		}
		results.add(type);
		List<Class<?>> interfaces = new ArrayList<Class<?>>();
		for(Class<?> clazz : results) {
			for(Class<?> superInterface : getInterfaceHierarchy(clazz, baseType)) {
				if(!interfaces.contains(superInterface)) {
					interfaces.add(superInterface);
				}
			}
		}
		results.remove(type);
		results.addAll(interfaces);
		return results;
	}
	
	/**
	 * Returns the super interface hierarchy of a type.
	 * 
	 * @param type
	 *        Type to analyze.
	 * @param baseType 
	 *        Base type beyond which the hierarchy is not fetched. Each
	 *        and every one of the returned types must be a sub-type of this
	 *        type.
	 *        
	 * @return All super interfaces of the type.
	 */
	public static List<Class<?>> getInterfaceHierarchy(Class<?> type, Class<?> baseType) {
		return getInterfaceHierarchy(type, baseType, null);
	}
	
	/**
	 * Returns the super interface hierarchy of a type.
	 * 
	 * @param type
	 *        Type to analyze.
	 * @param baseType 
	 *        Base type beyond which the hierarchy is not fetched. Each
	 *        and every one of the returned types must be a sub-type of this
	 *        type.
	 * @param soFar
	 *        Results so far (for recursive calls).
	 *        
	 * @return All super interfaces of the type.
	 */
	private static List<Class<?>> getInterfaceHierarchy(Class<?> type, Class<?> baseType, List<Class<?>> soFar) {
		List<Class<?>> results = soFar;
		if(results==null) {
			results = new ArrayList<Class<?>>();
		}
		Class<?>[] interfaces = type.getInterfaces();
		for(Class<?> interfaze : interfaces) {
			/*
			 * If the interface is not yet included and there is no
			 * specified baseType or the baseType is a super interface
			 */
			if(!results.contains(interfaze) && (baseType==null || baseType.isAssignableFrom(interfaze))) {
				results.add(interfaze);
				getInterfaceHierarchy(interfaze, baseType, results);
			}
		}
		return results;
	}
	
	/**
	 * Checks if an object is accessible, and if not, it sets it to be accessible.
	 * 
	 * @param accessibleObject
	 *        Object to set accessible.
	 */
	public static void setAccessible(AccessibleObject accessibleObject) {
		if (!accessibleObject.isAccessible()) {
			accessibleObject.setAccessible(true);
		}
	}
	
	/**
	 * Creates pairs of getter - setter methods for a specified property.
	 * @param sourceType 
	 * @param targetType
	 * @param property 
	 * @return Returns a pair or null if there is no pair.
	 */
	private static Pair<Method, Method> pairGetterAndSetter
	(TypeAnalysis sourceType, TypeAnalysis targetType, String property) {
		BeanPropertyDefinition<?> sourceProperty = sourceType.getFirstPropertyByName(property);
		Method getter = sourceProperty.getGetter();
		if (getter==null) {
			return null;
		}
		BeanPropertyDefinition<?> targetProperty = targetType.getFirstPropertyByName(property);
		if (targetProperty==null) {
			return null;
		}
		Method setter = targetProperty.getSetter();
		if (setter==null) {
			return null;
		}
		return new Pair<Method, Method>(getter, setter);							
	}
	
	/**
	 * Copies a property from source to target, if possible.
	 * 
	 * @param sourceType
	 *        Type of source object.
	 * @param targetType 
	 *        Type of target object.
	 * @param source
	 *        The object from which the property is copied.
	 * @param target
	 *        The object to which the property is copied.
	 * @param property
	 *        Name of property.
	 */
	private static void copyProperty 
	(TypeAnalysis sourceType, TypeAnalysis targetType, Object source, Object target, String property) {
		//Debug.increaseCounter("copyProperty");
		
		Pair<Method, Method> pair = pairGetterAndSetter(sourceType, targetType, property);
		if (pair!=null) {
			Method getter = pair.getLeft();
			Method setter = pair.getRight();
			Object value = ReflectionUtils.invoke(getter, source, GETTER_ARGS);
			Object[] setterArgs = {value};
			ReflectionUtils.invoke(setter, target, setterArgs);
		}
	}
	
	/**
	 * Copies a property from source to target, if possible.
	 * 
	 * If the value of the property is null, then the default value
	 * defined for the property type is set. The default values are 
	 * specified and retrieved from the {@link Defaults} utility class.
	 * 
	 * @param sourceType
	 *        Type of source object.
	 * @param targetType 
	 *        Type of target object.
	 * @param source
	 *        The object from which the property is copied.
	 * @param target
	 *        The object to which the property is copied.
	 * @param property
	 *        Name of property.
	 */
	private static void copyPropertyWithDefault 
	(TypeAnalysis sourceType, TypeAnalysis targetType, Object source, Object target, String property) {
		//Debug.increaseCounter("copyProperty");
		
		Pair<Method, Method> pair = pairGetterAndSetter(sourceType, targetType, property);
		if (pair!=null) {
			Method getter = pair.getLeft();
			Method setter = pair.getRight();
			Object value = ReflectionUtils.invoke(getter, source, GETTER_ARGS);
			if (value==null) {				
				Class<?> valueType = setter.getParameterTypes()[0];
				value = Defaults.getDefaultValue(valueType);
			}
			Object[] setterArgs = {value};
			ReflectionUtils.invoke(setter, target, setterArgs);
		}
	}
	
	/**
	 * Gets a mandatory property.
	 * 
	 * @param property
	 * @param type
	 * 
	 * @return Returns the property.
	 */
	public static BeanPropertyDefinition<?> mandatoryPropertyOfClass(String property, Class<?> type) {
		TypeAnalysis analysis = TypeAnalysis.analyze(type);
		BeanPropertyDefinition<?> bpd = analysis.getFirstPropertyByName(property);
		if (bpd==null) {
			throw Exceptions.invalidPropertyName(type, property);
		}
		return bpd;
	}
	
	
	
}
