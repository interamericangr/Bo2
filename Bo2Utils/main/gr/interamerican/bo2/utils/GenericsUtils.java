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

import gr.interamerican.bo2.utils.reflect.analyze.TypeAnalysis;
import gr.interamerican.bo2.utils.reflect.beans.BeanPropertyDefinition;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Utils about generics.
 */
public class GenericsUtils {
	/**
	 * Checks if the return type of a method is a {@link TypeVariable}.
	 * 
	 * example: public static <C> C doSomething().
	 * 
	 * @param method
	 * 
	 * @return Returns true if the method is declared as returning
	 *         a type variable.
	 */
	public static boolean isVariableReturnType(Method method) {
		Type type = method.getGenericReturnType();
		return (type instanceof TypeVariable);			
	}
	
	/**
	 * Checks if the return type of a method is a {@link ParameterizedType}.
	 * 
	 * example: public static Set<C> doSomething(C arg).
	 * 
	 * @param method
	 * 
	 * @return Returns true if the method is declared as returning
	 *         a parameterized type.
	 */
	public static boolean isParameterizedReturnType(Method method) {
		Type type = method.getGenericReturnType();
		return (type instanceof ParameterizedType);			
	}
	
	/**
	 * Checks if any of the argument types of the method is a {@link TypeVariable}.
	 * 
	 * example: public static <C> void doSomething(C arg).
	 * 
	 * @param method
	 * 
	 * @return Returns true if the method is declared as returning
	 *         a type variable.
	 */
	public static boolean isVariableParameterType(Method method) {
		Type[] args = method.getGenericParameterTypes();
		for (Type type : args) {
			if (type instanceof TypeVariable) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if any of the argument types of the method is a {@link TypeVariable}.
	 * 
	 * example: public static <C> void doSomething(C arg).
	 * 
	 * @param method
	 * 
	 * @return Returns true if the method is declared as returning
	 *         a type variable.
	 */
	public static boolean isParameterizedParameterType(Method method) {
		Type[] args = method.getGenericParameterTypes();
		for (Type type : args) {
			if (type instanceof ParameterizedType) {
				return true;
			}
		}
		return false;
	}
	
	
	
	
	/**
	 * Finds the type of elements of a {@link Field} or Property
	 * who's type is a Collection.
	 * 
	 * @param clazz
	 *        Class of the field or property.
	 * @param type 
	 *        {@link Type} of the field or property.
	 * 
	 * @return The type of the collection.
	 */
	static Class<?> getCollectionTypeOf(Class<?> clazz, Type type) {
		if(!(Collection.class.isAssignableFrom(clazz))) {			
			String msg = StringUtils.concat
				(clazz.getName()," is not as a Collection"); //$NON-NLS-1$
			throw new RuntimeException(msg);
		}
		if(!(type instanceof ParameterizedType)) {
			return Object.class;
		}
		ParameterizedType parameterized = (ParameterizedType) type;
		Type[] args = parameterized.getActualTypeArguments();
		if(args==null || args.length==0) {
			return Object.class;
		}
		Type argument = args[0];
		if(argument instanceof Class) {
			return (Class<?>) argument;
		} 
		if(argument instanceof ParameterizedType) {
			ParameterizedType ptArg = (ParameterizedType) argument;
			return (Class<?>)ptArg.getRawType();
		} 
		throw new RuntimeException("Could not find type of collection elements"); //$NON-NLS-1$
	}	
	
	
	
	
	/**
	 * Finds the type of elements of a {@link Field} who's type is a Collection.
	 * 
	 * If the field is not declared as a Collection, a RuntimeException
	 * is thrown. <br/>  
	 * If the field is declared as a raw Collection, then Object.class is returned.
	 * <br/>
	 * An assumption is made that the field declaration contains only one
	 * type argument.
	 * <br/>
	 * Disclaimer: This will not work for a declarations like: <br/> 
	 * <code>
	 * StringList foo; //StringList extends List&ltString&gt.
	 * </code> 
	 * 
	 * @param field
	 *        The field.
	 * 
	 * @return The type of the collection elements declared with this Field.
	 */
	public static Class<?> getCollectionTypeOfField(Field field) {
		Class<?> clazz = field.getType();
		Type type = field.getGenericType();
		return getCollectionTypeOf(clazz, type);
	}
	
	/**
	 * Finds the type of elements of a property that declares a Collection.
	 * Bad input returns null. If the field is not parameterized, Object is
	 * returned.
	 * <br/>
	 * An assumption is made that the field declaration contains only one
	 * type argument.
	 * <br/>
	 * Disclaimer: This will not work for a declarations like
	 * StringList foo; //StringList extends List<String>. 
	 * 
	 * @param clazz 
	 *        Class declaring the property.
	 * @param property 
	 *        Name of property.
	 * 
	 * @return The type of the collection elements declared with this Field.
	 */
	@SuppressWarnings("nls")
	public static Class<?> getCollectionTypeOfProperty(Class<?> clazz, String property) {
		TypeAnalysis analysis = TypeAnalysis.analyze(clazz);
		BeanPropertyDefinition<?> boPD = analysis.getFirstPropertyByName(property);
		if (boPD==null) {
			String msg = StringUtils.concat(
				"Property ", property, " does not exist in type ", clazz.getName());
			throw new RuntimeException(msg);
		}
		Class<?> pClass = boPD.getType();
		Type pType = boPD.getGenericType();
		return getCollectionTypeOf(pClass, pType);
	}
	
	
	/**
	 * Checks if the generic arguments of two types match.
	 * 
	 * @param m1 
	 * @param m2 
	 * 
	 * 
	 * 
	 * @return Returns true if the method is declared as returning
	 *         a type variable.
	 */
	public static boolean isGenericParametersMatch(Method m1, Method m2) {
		Type[] args1 = m1.getGenericParameterTypes();
		Type[] args2 = m2.getGenericParameterTypes();
		if (args1.length!=args2.length) {
			return false;
		}
		for (int i = 0; i < args2.length; i++) {
			if (!isTypesMatch(args1[i], args1[i])) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks if the generic arguments of two methods match.
	 * 
	 * @param t1
	 * @param t2
	 * @return Returns true if both arguments are TypeVariables or
	 *         if they are equal.
	 */
	static boolean isTypesMatch(Type t1, Type t2) {
		if ((t1 instanceof TypeVariable) 
		||  (t1 instanceof TypeVariable)) {
			return true;
		}
		return t1.equals(t2);
	}
	
	/**
	 * Gets all generic interfaces of a class.
	 * 
	 * @param clazz
	 * 
	 * @return Returns the generic interfaces of the class.
	 */
	public static Set<Type> getGenericInterfaces(Class<?> clazz) {
		Set<Type> interfaces = new HashSet<Type>();
		for (Type genericInterface : clazz.getInterfaces()) {
			interfaces.add(genericInterface);
			if (genericInterface instanceof Class) {
				Class<?> superType = (Class<?>) genericInterface;
				Set<Type> more = getGenericInterfaces(superType);
				interfaces.addAll(more);
			}
			if (genericInterface instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
				Class<?> superType = (Class<?>) parameterizedType.getRawType();
				Set<Type> more = getGenericInterfaces(superType);
				interfaces.addAll(more);
			}
		}
		return interfaces;
	}
	
	/**
	 * Finds the parameter argument existing somewhere in a types hierarchy.
	 * The extra input required for this to work is worth the trouble, as it
	 * is certain that the correct result is fetched and not some random 
	 * parameterized type argument.
	 * <br/>
	 * For example assume that we are searching for the type argument &lt;T&gt;
	 * of a A&lt;T&gt; sub-type. The concrete parameter could be declared anywhere in 
	 * the hierarchy, but always in a sub-type of A. Invoking this method with
	 * <br/>
	 * baseParameterizedType: A.class
	 * <br/>
	 * will result to the search of all super-types that are a sub-type of A, 
	 * up to and including A, closest to the class specified by the type
	 * argument first. On this search path we are guaranteed to find the correct
	 * type parameter argument.
	 * <br/>
	 * Note that parameters that are ParameterizedType are not returned. For instance
	 * searching a sub-type of A&lt;T&lt;S&gt;&gt; will not return the type argument
	 * T&lt;S&gt;
	 * 
	 * @param type
	 *        Type we inspect.
	 * @param baseParameterizedType
	 *        Super type of type that is a {@link ParameterizedType} and has the parameter
	 *        we search for.
	 * @param baseParameterType
	 *        Expected class of the parameter. The parameter must be a sub-type of this.
	 *        If this is null, it is ignored.
	 * 
	 * @return Returns the parameters
	 */
	public static Class<?> findTypeParameter(Class<?> type, Class<?> baseParameterizedType, Class<?> baseParameterType) {
		Set<ParameterizedType> parameterizedTypes = getParameterizedTypes(type, baseParameterizedType);
		for(ParameterizedType parameterizedType : parameterizedTypes) {
			if(parameterizedType.getRawType() instanceof Class) {
				if(baseParameterizedType.isAssignableFrom((Class<?>) parameterizedType.getRawType())) {
					Type[] arguments = parameterizedType.getActualTypeArguments();
					for(Type argument : arguments) {
						if(argument instanceof Class) {
							if(baseParameterType==null || baseParameterType.isAssignableFrom((Class<?>) argument)) {
								return (Class<?>) argument;
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Returns all the {@link ParameterizedType}s found in a types hierarchy,
	 * up to a base type.
	 * 
	 * @param type
	 *        Class that has a parameterized type somewhere in its hierarchy.
	 * @param baseType
	 *        Type beyond which the search stops. Any parameterized types
	 *        returned must be a sub-type of this type.
	 * 
	 * @return Returns a set with all parameterized types found.
	 */
	private static Set<ParameterizedType> getParameterizedTypes(Class<?> type, Class<?> baseType) {
		Set<ParameterizedType> parameterizedTypes = new HashSet<ParameterizedType>();
		List<Class<?>> classes = ReflectionUtils.getTypeHierarchy(type, baseType);
		classes.add(type);
		for(Class<?> clazz : classes) {
			if(clazz.getGenericSuperclass() instanceof ParameterizedType) {
				parameterizedTypes.add((ParameterizedType) clazz.getGenericSuperclass());
			}
			for(Type interfaze : clazz.getGenericInterfaces()) {
				if(interfaze instanceof ParameterizedType) {
					parameterizedTypes.add((ParameterizedType) interfaze);
				}
			}
		}
		return parameterizedTypes;
	}

}
