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

import gr.interamerican.bo2.utils.adapters.NumberConverter;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DateTimeConverter;
import org.apache.commons.beanutils.expression.DefaultResolver;
import org.apache.commons.beanutils.expression.Resolver;

/**
 * Javabean utils offers utilities that use the facilities
 * provided by the Apache commons BeanUtils project.
 * 
 */
public class JavaBeanUtils {
	
	/**
	 * Apache commons beanutils resolver utility. Used internally to 
	 * process complex property expressions.
	 */
	private static final Resolver RESOLVER = new DefaultResolver();
	
	/**
	 * Configuration necessary to support conversion from String
	 * to java.util.Date. The date format is compatible with the
	 * javadoc of JavaBeanUtils#copyFromProperties().
	 */
	static {
		DateTimeConverter dtConverter = new DateConverter();
		dtConverter.setPattern(Bo2UtilsEnvironment.getIsoDateFormatPattern());
		ConvertUtils.register(dtConverter, java.util.Date.class);
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
		if(source==null || target==null) {
			return;
		}
		Map<String, PropertyDescriptor> targetProperties = 
			JavaBeanUtils.getBeansPropertiesMap(target.getClass());
		Map<String, PropertyDescriptor> sourceProperties = 
			JavaBeanUtils.getBeansPropertiesMap(source.getClass());	
		Map<String, PropertyDescriptor> propertiesToCopy = 
			new HashMap<String, PropertyDescriptor>();
	
		if (properties != null && properties.length != 0) {
			propertiesToCopy = new HashMap<String, PropertyDescriptor>();
			for (int i = 0; i < properties.length; i++) {
				PropertyDescriptor pd = sourceProperties.get(properties[i].trim());
				if (pd != null) {
					propertiesToCopy.put(pd.getName(), pd);
				}
			}
		} else {
			propertiesToCopy = sourceProperties;
		}
	
		for (PropertyDescriptor sourcePD : propertiesToCopy.values()) {
			String name = sourcePD.getName();
			PropertyDescriptor targetPD = targetProperties.get(name);
			if (targetPD != null) {
				copyProperty(source, target, sourcePD, targetPD);
			}
		}
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
		if(excluded==null || excluded.length==0) {
			copyProperties(source, target, null);
			return;
		}
		PropertyDescriptor[] sourceProperties = JavaBeanUtils.getBeansProperties(source.getClass());
		Set<String> properties = new HashSet<String>();
		for(PropertyDescriptor pd : sourceProperties) {
			String property = pd.getName();
			if(StringUtils.arrayContainsString(excluded, property)==-1) {
				properties.add(property);
			}
		}
		if(properties.size()==0) { 
			return;
		}
		copyProperties(source, target, properties.toArray(new String[]{}));
	}
	
	


	/**
	 * Copies a property from a source object to a target object given two
	 * respective property descriptors.
	 * The target's property type has to be a super-type of the source's
	 * property type. If both are sub-types of {@link Number}, we use a 
	 * converter to convert the source property to a type compatible with
	 * the target property. This may lead to loss of precision.
	 * 
	 * TODO: We could also support other conversions, e.g. Character -> String
	 * 
	 * @param source
	 * @param target
	 * @param sourcePd
	 * @param targetPd
	 */
	public static void copyProperty(Object source, Object target, PropertyDescriptor sourcePd, PropertyDescriptor targetPd) {
		if(source==null || target==null || sourcePd==null || targetPd==null) {
			return;
		}
		Class<?> targetPdType = targetPd.getPropertyType();
		Class<?> sourcePdType = sourcePd.getPropertyType();
		
		boolean assignable = targetPdType.isAssignableFrom(sourcePdType);
		boolean differentNumberTypes = Number.class.isAssignableFrom(targetPdType) 
								     && Number.class.isAssignableFrom(sourcePdType) 
								     && !assignable;
		
		if(assignable || differentNumberTypes) {
			Method getter = sourcePd.getReadMethod();
			if (getter != null) {
				Object[] getterArgs = null;
				Object value = ReflectionUtils.invoke(getter, source, getterArgs);
				Method setter = targetPd.getWriteMethod();
				if (setter != null) {
					if (differentNumberTypes) {
						@SuppressWarnings("unchecked")
						NumberConverter converter = new NumberConverter((Class<? extends Number>) targetPdType);
						value = converter.execute((Number) value);
					}
					Object[] setterArgs = { value };
					ReflectionUtils.invoke(setter, target, setterArgs);
				}
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
	 * @param pd
	 *            Descriptor of the property.
	 * @param val
	 *            New value for the field.
	 * @param obj
	 *            Object on which the field value is changed.
	 */
	public static void setProperty(PropertyDescriptor pd, Object val, Object obj) {		
		if (isValidValue(pd, val)) {
			setPropertyTyped(pd, val, obj);
		} else {
			setPropertyUntyped(pd, val, obj);
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
	 * @param pd
	 *            Descriptor of the property.
	 * @param val
	 *            New value for the field.
	 * @param obj
	 *            Object on which the field value is changed.
	 */
	static void setPropertyTyped(PropertyDescriptor pd, Object val, Object obj) {
		Method setter = pd.getWriteMethod();
		if (setter!=null) {
			Object[] args = {val};
			ReflectionUtils.invoke(setter, obj, args);
		} else {
			String fieldName = pd.getName();
			try {
				Field field = obj.getClass().getDeclaredField(fieldName);
				field.setAccessible(true);
				ReflectionUtils.set(field, val, obj);
			} catch (NoSuchFieldException nse) {
				throw new RuntimeException(nse);
			}			
		}
	}
	
	/**
	 * Checks if value is a valid value for a property.
	 * 
	 * @param pd
	 * @param value
	 * 
	 * @return Returns true if the specified value is valid for 
	 *         the specified property.
	 */
	static boolean isValidValue(PropertyDescriptor pd, Object value) {
		if (value==null) {
			return true;
		}
		return pd.getPropertyType().isAssignableFrom(value.getClass());		
	}
	
	/**
	 * Sets the value of a property on an object.
	 * 
	 * value does not need to be strongly typed, {@link BeanUtils}
	 * will handle the conversions.
	 * 
	 * @param pd
	 *            Descriptor of the property.
	 * @param val
	 *            New value for the field.
	 * @param obj
	 *            Object on which the field value is changed.
	 */
	static void setPropertyUntyped(PropertyDescriptor pd, Object val, Object obj) {
		try {
			BeanUtils.setProperty(obj, pd.getName(), val);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Sets the value of a property on an object.
	 * 
	 * If the specified object does not have a property
	 * with the specified name, then nothing will happen.
	 * 
	 * @param target
	 * @param propertyName
	 * @param propertyValue
	 */
	static void setPropertyTo
	(Object target, String propertyName, Object propertyValue) {
		PropertyDescriptor pd = 
			getPropertyDescriptor(target.getClass(), propertyName);
		if (pd!=null) {
			setProperty(pd, propertyValue, target);				
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
	 * 
	 * @param pd
	 *            Descriptor of the property.
	 * @param obj
	 *            Object on which the property is accessed.
	 *            
	 * @return Returns the value of this property.
	 */
	public static Object getProperty(PropertyDescriptor pd, Object obj) {
		Method getter = pd.getReadMethod();
		if (getter!=null) {	
			return ReflectionUtils.invoke(getter, obj);
		} else {
			String fieldName = pd.getName();			
			try {
				Field field = obj.getClass().getDeclaredField(fieldName);
				field.setAccessible(true);
				return ReflectionUtils.get(field, obj);
			} catch (NoSuchFieldException nse) {
				throw new RuntimeException(nse);
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
		PropertyDescriptor pd = JavaBeanUtils.mandatoryProperty(property, obj);
		setProperty(pd, val, obj);
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
	 * 
	 * @param property
	 *            Name of the property.
	 * @param obj
	 *            Object on which the proeprty is accessed.
	 *            
	 * @return Returns the value of this property.
	 */
	public static Object getProperty(String property, Object obj) {
		PropertyDescriptor pd = JavaBeanUtils.mandatoryProperty(property, obj);		
		return getProperty(pd, obj);
	}
	
	
	
	

	/**
	 * This utility evaluates a property expression on an object that may
	 * be composite, i.e. refer to a property nested in a nested bean. Nested
	 * properties are expressed using the "." separator.
	 * 
	 *  For example, field1.property1 refers to property1 of property field1.
	 *  When getting this property something like getField1().getProperty1() is
	 *  executed.
	 * 
	 * @param bean
	 *        Object to evaluate the supplied expression on.
	 * @param propertyExpression
	 *        Gets a possibly nested property value based on the supplied expression.
	 *        
	 * @return Returns the value of the property expression on the object. 
	 */
	public static Object getNestedProperty(Object bean, String propertyExpression) {		
		try {
			return BeanUtilsBean.getInstance().getPropertyUtils().getNestedProperty(bean, propertyExpression);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * This utility evaluates a property expression on an object that may
	 * be composite, i.e. refer to a property nested in a nested bean. Nested
	 * properties are expressed using the "." separator.
	 * 
	 * If an object in the path for the nested property is null, then this
	 * method will not throw a NestedNullPointerException. It will return null.
	 * 
	 * @param bean
	 *        Object to evaluate the supplied expression on.
	 * @param propertyExpression
	 *        Gets a possibly nested property value based on the supplied expression.
	 *        
	 * @return Returns the value of the property expression on the object. 
	 */
	public static Object getNestedPropertyNullTolerant(Object bean, String propertyExpression) {		
		try {
			return BeanUtilsBean.getInstance().getPropertyUtils().getNestedProperty(bean, propertyExpression);
		} catch (NestedNullException e) {
			return null;
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
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
		if(source==null || target==null || StringUtils.isNullOrBlank(property)) {
			return;
		}
		PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), property);
		PropertyDescriptor targetPd = getPropertyDescriptor(target.getClass(), property);
		JavaBeanUtils.copyProperty(source, target, sourcePd, targetPd);
	}

	/**
	 * Finds the properties of a bean.
	 * 
	 * <li>If the bean is a concrete class the properties of the bean, for which
	 * there exists a setter method, a getter method or both. Properties of
	 * super-types are returned as well.</li>
	 * 
	 * <li>If the bean is an abstract class, an empty array is returned</li>
	 * 
	 * <li>If the bean is an interface, the properties of the bean are returned.
	 * The method also queries all super-interfaces and fetches their properties
	 * as well.</li>
	 * 
	 * @param type
	 *            the bean
	 * @return Returns the property descriptors of a java bean.
	 * 
	 * TODO: Support properties of abstract classes.
	 */
	public static PropertyDescriptor[] getBeansProperties(Class<?> type) {
	
		ArrayList<PropertyDescriptor> propertyDescriptor = new ArrayList<PropertyDescriptor>();
	
		for (PropertyDescriptor p : PropertyUtils.getPropertyDescriptors(type)) {
			if (!propertyDescriptor.contains(p) && ! p.getName().equals("class")) { //$NON-NLS-1$
				propertyDescriptor.add(p);
			}
		}
	
		if (type.isInterface()) {
			Class<?>[] classArray = type.getInterfaces();
			PropertyDescriptor[] pdArray;
			for (Class<?> next : classArray) {
				pdArray = getBeansProperties(next);
				for (PropertyDescriptor pd : pdArray) {
					if (!propertyDescriptor.contains(pd)) {
						propertyDescriptor.add(pd);
					}
				}
			}
		}
		return propertyDescriptor.toArray(new PropertyDescriptor[0]);
	}

	/**
	 * Finds the properties of a bean and returns them in a {@link HashMap} with
	 * the property name as key.
	 * 
	 * @param type
	 *            the bean
	 * @return Returns the property descriptors of a java bean.
	 * 
	 * @see #getBeansProperties(Class)
	 * 
	 */
	public static Map<String, PropertyDescriptor> getBeansPropertiesMap(
			Class<?> type) {
		PropertyDescriptor[] array = JavaBeanUtils.getBeansProperties(type);
		HashMap<String, PropertyDescriptor> properties = new HashMap<String, PropertyDescriptor>();
		for (int i = 0; i < array.length; i++) {
			properties.put(array[i].getName(), array[i]);
		}
		return properties;
	}

	/**
	 * Finds the properties of all interfaces implemented by an abstract class
	 * and returns them in a {@link HashMap} with
	 * the property name as key.
	 * 
	 * @param type
	 *            the bean
	 * @return Returns the property descriptors of a java bean.
	 * 
	 * @see #getBeansProperties(Class)
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, PropertyDescriptor> 
	getBeansPropertiesOfAbstractClass(Class<?> type) {
		List<Class> types = CollectionUtils.addAll(new ArrayList<Class>(), (Class[])type.getInterfaces());
		types.add(type);
		HashMap<String, PropertyDescriptor> properties = new HashMap<String, PropertyDescriptor>();
		for (Class infc : types) {
			Map<String, PropertyDescriptor> subMap = JavaBeanUtils.getBeansPropertiesMap(infc);
			properties.putAll(subMap);
		}
		return properties;
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
		Map<String, PropertyDescriptor> descriptors = JavaBeanUtils.getBeansPropertiesMap(obj.getClass());
		Map<String,Object> values = new HashMap<String, Object>();
		for (Map.Entry<String, PropertyDescriptor> entry : descriptors.entrySet()) {
			PropertyDescriptor pd = entry.getValue();
			String name = entry.getKey();
			Object v = JavaBeanUtils.getProperty(pd, obj);
			values.put(name, v);
		}		
		return values;
	}

	/**
	 * Finds the property descriptor that describes a property.
	 * 
	 * @param type
	 *        The bean class.
	 * @param name 
	 *        The name of the property searched. This may be a composite property,
	 *        e.g. "nestedBean.property1".
	 *           
	 * @return Returns the property descriptor that describes the property with 
	 *         the specified name. If there is no property with ths name, then 
	 *         the method returns null.
	 */
	public static PropertyDescriptor getPropertyDescriptor(Class<?> type, String name) {		
		if(RESOLVER.hasNested(name)) {
			String next = RESOLVER.next(name);
			PropertyDescriptor pd = getPropertyDescriptor(type, next);
			return getPropertyDescriptor(pd.getPropertyType(), RESOLVER.remove(name));
		} 
		Map<String, PropertyDescriptor> descriptors = getBeansPropertiesMap(type);
		return descriptors.get(name);
	}

	/**
	 * Creates a Map that maps each element of a set with the value of one of 
	 * its properties as key.
	 * 
	 * There is no guarantee that all elements of the set will be included in
	 * the output map. In case more than one items of the set have the same 
	 * value of the specified property then the last element will replace the
	 * previous in the map, as only one item can be associated with one key. 
	 * If any element has null on the specified property, then it will not be 
	 * included in the output map.
	 * 
	 * 
	 * @param set 
	 *        Set that contains the elements that will be put on the output map. 
	 * @param property
	 *        Property descriptor specifying the property used to get the key.
	 *        
	 * @param <K> Type of property used as key. This will be the type of key
	 *            in the output map. 
	 * @param <T> Type of items.
	 * 
	 * @return Returns a Map that maps items of the specified set with keys the
	 *         values of a property.
	 */
	public static <K, T> Map<K,T> setAsMapUsingPropertyAsKey(Set<T> set, PropertyDescriptor property) {
		Map<K, T> map = new HashMap<K, T>();
		for (T item : set) {
			Object obj = getProperty(property, item);			
			@SuppressWarnings("unchecked") K key = (K) obj;
			if (key!=null) {
				map.put(key, item);
			}
		}
		return map;
	}

	/**
	 * Gets the default Apache commons beanutils resolver utility of Bo2Utils.
	 * 
	 * @return Returns a resolver.
	 */
	public static Resolver getResolver() {
		return RESOLVER;
	}

	/**
	 * Finds a property in an object.
	 * 
	 * @param property
	 *        Name of the property.
	 * @param obj
	 *        Object.
	 * @return Returns the property descriptor that describes the property.
	 * 
	 * @throws RuntimeException 
	 *         If the object does not have any property with the specified 
	 *         name.
	 */
	public static PropertyDescriptor mandatoryProperty(String property, Object obj) {		
		return mandatoryPropertyOfClass(property,obj.getClass());
	}
	
	/**
	 * Finds a property in a class.
	 * 
	 * @param property
	 *        Name of the property.
	 * @param clazz
	 *        Class.
	 * @return Returns the property descriptor that describes the property.
	 * 
	 * @throws RuntimeException 
	 *         If the object does not have any property with the specified 
	 *         name.
	 */
	public static PropertyDescriptor mandatoryPropertyOfClass(String property, Class<?> clazz) {
		PropertyDescriptor pd = getPropertyDescriptor(clazz, property);
		if (pd==null) {
			throw Exceptions.invalidPropertyName(clazz, property);
		}
		return pd;
	}
	
	/**
	 * Copies all common properties from a map to a target object.
	 *  
	 * @param source 
	 *        Source map.	         
	 * @param target
	 *        Object to which the properties will be copied.
	 */
	public static void copyFromMap (Map<String, ?> source, Object target) {
		for (Map.Entry<String,?> entry : source.entrySet()) {			
			String propertyName = entry.getKey();
			Object propertyValue = entry.getValue();
			setPropertyTo(target, propertyName, propertyValue);
		}
	}
	
	/**
	 * Copies all common properties from a map to a target object.
	 * 
	 * For non string properties, the method delegates the bean property
	 * setting to the apache commons beanutils (http://commons.apache.org/beanutils/)
	 * library. This pauses restriction to the formatting of any non
	 * string property in the properties object.
	 * <li> dates should be formatted as yyyy-MM-dd </li>
	 * <li> decimal numbers must be formatted using as xxx.dd </li>
	 *  
	 * @param source 
	 *        Source properties object.	         
	 * @param target
	 *        Object to which the properties will be copied.
	 */
	public static void copyFromProperties (Properties source, Object target) {
		Enumeration<?> names = source.propertyNames();
		while (names.hasMoreElements()) {
			String propertyName = (String)names.nextElement();
			String propertyValue = source.getProperty(propertyName);
			setPropertyTo(target, propertyName, propertyValue);
		}
	}

}
