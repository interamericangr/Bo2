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
package gr.interamerican.bo2.utils.reflect.analyze;

import static gr.interamerican.bo2.utils.reflect.AccessorUtils.isGetter;
import static gr.interamerican.bo2.utils.reflect.AccessorUtils.isSetter;
import static gr.interamerican.bo2.utils.reflect.AccessorUtils.propertyName;
import gr.interamerican.bo2.utils.AdapterUtils;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.SelectionUtils;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.beans.Array;
import gr.interamerican.bo2.utils.enums.AccessType;
import gr.interamerican.bo2.utils.reflect.beans.BeanPropertyDefinition;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Analysis of a type.
 */
public class TypeAnalysis {
	
	/**
	 * Cached analysis.
	 */
	private static final Map<Class<?>, TypeAnalysis> cache = 
		new HashMap<Class<?>, TypeAnalysis>();
	
	/**
	 * Gets an interface analysis from the cache.
	 * 
	 * @param type
	 *        Type to analyze.
	 *        
	 * @return Returns an interface analysis.
	 */
	public static TypeAnalysis analyze(Class<?> type) {
		TypeAnalysis analysis = cache.get(type);
		if (analysis==null) {
			analysis = new TypeAnalysis(type);
			cache.put(type, analysis);
		}
		return analysis;
	}
	
	
	/**
	 * Class analyzed.
	 */
	Class<?> clazz;	
	/**
	 * Non static non abstract methods, except from property accessors. 
	 */
	Set<Method> concreteMethods=new HashSet<Method>();
	/**
	 * Non static non abstract property getters. 
	 */
	Set<Method> concreteGetters=new HashSet<Method>();
	/**
	 * Non static non abstract property setters. 
	 */
	Set<Method> concreteSetters=new HashSet<Method>();
	/**
	 * Abstract methods except from property accessors. 
	 */
	Set<Method> abstractMethods=new HashSet<Method>();
	/**
	 * Abstract getters.
	 */
	Set<Method> abstractGetters=new HashSet<Method>();
	/**
	 * Abstract setters.
	 */
	Set<Method> abstractSetters=new HashSet<Method>();
	
	
	/**
	 * All properties of the analyzed interface.
	 */	
	Map<Array, BeanPropertyDefinition<?>> allProperties = 
		new HashMap<Array, BeanPropertyDefinition<?>>();
	/**
	 * Properties by name.
	 */
	Map<String, List<BeanPropertyDefinition<?>>> propertiesByName=
		new HashMap<String, List<BeanPropertyDefinition<?>>>();
		
	/**
	 * Write only properties, that have another property of other type
	 * with the same name. <br/>
	 * 
	 * This would happen if an interface had two methods with the name setId,
	 * one with an Integer argument and another with a Long. The interface 
	 * would have two properties with the name id, one Integer and one Long.
	 * Such properties, are put in this set.
	 */
	Map<String, List<BeanPropertyDefinition<?>>> oddPropertiesByName=
		new HashMap<String, List<BeanPropertyDefinition<?>>>();
	/**
	 * Set with the names of the odd properties.
	 */
	Set<BeanPropertyDefinition<?>> oddProperties = 
		new HashSet<BeanPropertyDefinition<?>>();
	
	/**
	 * Map that keeps all annotated fields.
	 */
	Map<Class<? extends Annotation>, Map<String,List<Field>>> annotatedFields = 
		new HashMap<Class<? extends Annotation>, Map<String,List<Field>>>();
	
	/**
	 * Map that keeps all annotated methods, static and non static.
	 */
	Map<Class<? extends Annotation>, List<Method>> annotatedMethods = 
		new HashMap<Class<? extends Annotation>, List<Method>>();
	
	/**
	 * Package visible constructor usable only for unit tests.
	 * 
	 * This constructor will not do any analysis.
	 */
	TypeAnalysis() {
		super();
	}
	
	
	/**
	 * Creates a new InterfaceAnalysis object.
	 *
	 * @param clazz
	 *        Class to analyze.
	 */
	TypeAnalysis(Class<?> clazz) {
		super();
		this.clazz = clazz;
		initialize();
		analyzeMethods();
		analyzeProperties();
		analyzePropertiesByName();
		if (!clazz.isInterface()) {
			analyzeFields();
		}
	}
	
	/**
	 * Categorizes the method to the appropriate set.
	 * 
	 * @param method
	 * @param abstrct
	 * @param cncrt
	 */
	private void categorize(Method method, Set<Method> abstrct, Set<Method> cncrt) {
		Set<Method> set = Modifier.isAbstract(method.getModifiers()) ?
				abstrct : cncrt;
		set.add(method);
	}
	
	/**
	 * Creates a key.
	 * 
	 * @param name 
	 * @param genericType 
	 * @param type 
	 * 
	 * @return Returns the key.
	 */
	Array propertyKey(String name, Type genericType, Class<?> type) {		
		if (genericType instanceof TypeVariable) {
			return new Array(name, Object.class);
		} else {
			return new Array(name, type);
		}		
	}
	
	/**
	 * Initializes the values.
	 */
	void initialize() {
		concreteMethods.clear();
		concreteGetters.clear();
		concreteSetters.clear();
		abstractMethods.clear();
		abstractGetters.clear();
		abstractSetters.clear();
		allProperties.clear();
		propertiesByName.clear();
		annotatedFields.clear();
	}
	
	/**
	 * Categorizes the methods to abstract or concrete, accessors or other methods.
	 */
	void analyzeMethods() {
		List<Method> methods = ReflectionUtils.getPublicMethods(clazz);		
		for (Method method : methods) {
			handleMethod(method);
		}
	}
	
	
	/**
	 * Analyzes the methods and fills the methods sets and the properties map.
	 */
	void analyzeFields() {
		List<Field> fields = ReflectionUtils.allFields(clazz, Object.class);
		for (Field field : fields) {
			registerField(Annotation.class, field); //All fields are registered with Annotation.
			if (!Modifier.isStatic(field.getModifiers())) {
				registerField(MarkerOfNonStaticFields.class, field);
			}
			Annotation[] annotations = field.getAnnotations();
			for (Annotation anno : annotations) {
				registerField(anno.annotationType(), field);
			}
		}
	}
	
	/**
	 * Register an annotated field. Static fields are registered separately.
	 * 
	 * @param anno
	 * @param field
	 */
	void registerField(Class<? extends Annotation> anno, Field field) {	
		Map<String, List<Field>> map = annotatedFields.get(anno);
		if (map==null) {
			map = new HashMap<String, List<Field>>();
			annotatedFields.put(anno, map);
		}
		List<Field> fields = map.get(field.getName());
		if (fields==null) {
			fields = new ArrayList<Field>();
			map.put(field.getName(), fields);
		}
		fields.add(field);		
	}
	
	/**
	 * Register an annotated method.
	 * 
	 * @param anno
	 * @param method
	 */
	void registerAnnotatedMethod(Class<? extends Annotation> anno, Method method) {
		List<Method> list = annotatedMethods.get(anno);
		if (list==null) {
			list = new ArrayList<Method>();
			annotatedMethods.put(anno, list);
		}
		list.add(method);
	}
	
	
	
	
	/**
	 * Gets the first field with the specified annotation and name.
	 * 
	 * @param anno
	 * @param fieldName
	 * 
	 * @return Returns the first field with the specified annotation and name.
	 */
	public Field getFirstAnnotated(Class<? extends Annotation> anno, String fieldName) {
		Map<String, List<Field>> map = annotatedFields.get(anno);
		if (map==null) {
			return null;
		}
		List<Field> fields = map.get(fieldName);
		if (fields==null) {
			return null;
		}		
		return fields.get(0);
	}
	
	/**
	 * Gets a list with all fields with the specified annotation and name.
	 * 
	 * @param anno
	 * @param fieldName
	 * @param mapWithLists 
	 * 
	 * @return Returns all fields with the specified annotation and name.
	 */
	List<Field> getAnnotatedFrom (
			Class<? extends Annotation> anno, String fieldName, 
			Map<Class<? extends Annotation>, Map<String,List<Field>>> mapWithLists ) {
		Map<String, List<Field>> map = mapWithLists.get(anno);
		if (map==null) {
			return null;
		}
		List<Field> fields = map.get(fieldName);
		if (fields==null) {
			return null;
		}
		return new ArrayList<Field>(fields);
	}
	
	
	
	/**
	 * Gets a list with all fields with the specified annotation and name.
	 * 
	 * @param anno
	 * @param fieldName
	 * 
	 * @return Returns all fields with the specified annotation and name.
	 */
	public List<Field> getAnnotated(Class<? extends Annotation> anno, String fieldName) {		
		return getAnnotatedFrom(anno, fieldName, annotatedFields);
	}	
	
	/**
	 * Gets a list with all fields with the specified annotation and name.
	 * 
	 * @param anno
	 * 
	 * @return Returns all fields with the specified annotation and name.
	 */
	public Set<Field> getAnnotated(Class<? extends Annotation> anno) {
		Map<String, List<Field>> fMap = annotatedFields.get(anno);
		
		if (fMap==null) {
			return new HashSet<Field>();
		}
		Set<Field> fields = new HashSet<Field>();
		for (List<Field> list : fMap.values()) {
			fields.addAll(list);
		}
		return fields;
	}
	
	/**
	 * Gets a list with all fields with the specified name.
	 * 
	 * It is possible to have more than one fields with the same name,
	 * if they are declared in a super type. The order in the list
	 * follows the order of types, starting form sub-type and moving
	 * to super-type.
	 * 
	 * @param fieldName
	 *        Name of field.
	 * 
	 * @return Returns all fields with the specified annotation and name.
	 */
	public List<Field> getAllFieldsByName(String fieldName) {
		return getAnnotated(Annotation.class, fieldName);
	}
	
	/**
	 * Gets the first field with the specified name.
	 * 
	 * It is possible to have more than one fields with the same name,
	 * if they are declared in a super type. The order in the list
	 * follows the order of types, starting form sub-type and moving
	 * to super-type. This method returns the field of the sub-type.
	 * 
	 * @param fieldName
	 *        Name of field.
	 * 
	 * @return Returns the first field with the specified name.
	 */
	public Field getFirstFieldByName(String fieldName) {
		return getFirstAnnotated(Annotation.class, fieldName);
	}
	
	/**
	 * Gets a set with all fields.
	 * 
	 * @return Returns all fields with the specified annotation and name.
	 */
	public Set<Field> getAllFields() {
		return getAnnotated(Annotation.class);
	}
	
	/**
	 * Gets a set with all fields.
	 * 
	 * @return Returns all fields with the specified annotation and name.
	 */
	public Set<Field> getNonStaticFields() {
		return getAnnotated(MarkerOfNonStaticFields.class);
	}
	
	/**
	 * Analyzes the properties.
	 */
	void analyzeProperties() {		
		for (BeanPropertyDefinition<?> property : allProperties.values()) {
			String name = property.getName();
			List<BeanPropertyDefinition<?>> list = propertiesByName.get(name);
			if (list==null) {
				list = new ArrayList<BeanPropertyDefinition<?>>();
				propertiesByName.put(name, list);	
			}
			list.add(property);
		}
	}
	
	/**
	 * Analyzes the propertiesByName and fills the oddProperties
	 * set and map.
	 */
	void analyzePropertiesByName() {	
		for (List<BeanPropertyDefinition<?>> list : propertiesByName.values()) {
			if (list.size()>1) {
				handleConflict(list);
			}
		}
	}
	
	/**
	 * Handles a method.
	 * 
	 * @param method
	 */
	void handleMethod(Method method) {
		if (isGetter(method)) {
			categorize(method, abstractGetters, concreteGetters);
			handleGetter(method);
		} else if (isSetter(method)) {
			categorize(method, abstractSetters, concreteSetters);
			handleSetter(method);
		} else if (!Modifier.isStatic(method.getModifiers())) {
			categorize(method, abstractMethods, concreteMethods);			
		}
	}
	
	
	
	/**
	 * Handles a getter.
	 * 
	 * @param getter
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void handleGetter(Method getter) {
		String name = propertyName(getter);
		Class<?> type = getter.getReturnType();
		Type genericType = getter.getGenericReturnType();
		
		Array key = propertyKey(name, genericType, type);
		BeanPropertyDefinition def = allProperties.get(key);
		
		if (def==null) {
			def = new BeanPropertyDefinition(name, type, genericType, clazz, getter, null);
			allProperties.put(key, def);
		} else {
			def.setGetter(getter);
		}
	}
	
	/**
	 * Handles a getter.
	 * 
	 * @param setter
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void handleSetter(Method setter) {
		String name = propertyName(setter);
		Class<?> type = setter.getParameterTypes()[0];
		Type genericType = setter.getGenericParameterTypes()[0];
		
		Array key = propertyKey(name, genericType, type);
		BeanPropertyDefinition def = allProperties.get(key);
		if (def==null) {
			def = new BeanPropertyDefinition(name, type, genericType, clazz, null, setter);
			allProperties.put(key, def);
		} else {
			def.setSetter(setter);
		}
	}
	
	/**
	 * Handles the case when two or more properties have the same name.
	 * 
	 * @param sameNamed
	 *        List with the {@link BeanPropertyDefinition}s of the properties
	 *        with the same name.
	 * 
	 */
	void handleConflict(List<BeanPropertyDefinition<?>> sameNamed) {
		Class<BeanPropertyDefinition<?>> bpdClazz = Utils.cast(BeanPropertyDefinition.class);
		BeanPropertyDefinition<?> normal = 
			SelectionUtils.selectFirstWithNotNullProperty ("getter", sameNamed, bpdClazz); //$NON-NLS-1$
		normal = Utils.notNull(normal, sameNamed.get(0));
		String name = normal.getName();
		List<BeanPropertyDefinition<?>> list = 
			new ArrayList<BeanPropertyDefinition<?>>(sameNamed);
		list.remove(normal);
		oddPropertiesByName.put(name, list);
		oddProperties.addAll(list);
	}
	

	/**
	 * Gets the clazz.
	 *
	 * @return Returns the clazz
	 */
	public Class<?> getClazz() {
		return clazz;
	}

	/**
	 * Gets the concreteMethods.
	 *
	 * @return Returns the concreteMethods
	 */
	public Set<Method> getConcreteMethods() {
		return new HashSet<Method>(concreteMethods);
	}

	/**
	 * Gets the concreteGetters.
	 *
	 * @return Returns the concreteGetters
	 */
	public Set<Method> getConcreteGetters() {
		return new HashSet<Method>(concreteGetters);
	}

	/**
	 * Gets the concreteSetters.
	 *
	 * @return Returns the concreteSetters
	 */
	public Set<Method> getConcreteSetters() {
		return new HashSet<Method>(concreteSetters);
	}

	/**
	 * Gets the abstractMethods.
	 *
	 * @return Returns the abstractMethods
	 */
	public Set<Method> getAbstractMethods() {
		return new HashSet<Method>(abstractMethods);
	}

	/**
	 * Gets the abstractGetters.
	 *
	 * @return Returns the abstractGetters
	 */
	public Set<Method> getAbstractGetters() {
		return new HashSet<Method>(abstractGetters);
	}

	/**
	 * Gets the abstractSetters.
	 *
	 * @return Returns the abstractSetters
	 */
	public Set<Method> getAbstractSetters() {
		return new HashSet<Method>(abstractSetters);
	}

	/**
	 * Gets the oddProperties.
	 *
	 * @return Returns the oddProperties
	 */
	public Set<BeanPropertyDefinition<?>> getOddProperties() {
		return new HashSet<BeanPropertyDefinition<?>>(oddProperties);
	}
	
	/**
	 * Gets the names of the odd properties.
	 *
	 * @return Returns the names of the odd properties.
	 */
	public Set<String> getOddPropertiesNames() {
		return new HashSet<String>(oddPropertiesByName.keySet());
	}
	
	/**
	 * Gets all properties of the analyzed interface.
	 *
	 * @return Returns all properties
	 */
	public Set<BeanPropertyDefinition<?>> getAllProperties() {
		return new HashSet<BeanPropertyDefinition<?>>(allProperties.values());
	}
	
	/**
	 * Gets all read-write properties of the analyzed type.
	 *
	 * @return Returns all read-write properties
	 */
	@SuppressWarnings({ "rawtypes", "nls" })
	public List<BeanPropertyDefinition<?>> getReadWriteProperties() {
		Collection<BeanPropertyDefinition> properties = Utils.cast(allProperties.values());
		List<BeanPropertyDefinition> list = SelectionUtils.selectByProperty 
			("accessType", AccessType.READ_WRITE, properties, BeanPropertyDefinition.class);
		return Utils.cast(list);
	}
	
	/**
	 * Gets all read-write properties of the analyzed type.
	 *
	 * @return Returns all read-write properties
	 */
	@SuppressWarnings("rawtypes")
	public Set<String> getNamesOfReadWriteProperties() {
		Set<BeanPropertyDefinition<?>> properties = CollectionUtils.toSet(allProperties.values());
		Set<BeanPropertyDefinition> raw = Utils.cast(properties);
		List<String> list = AdapterUtils.getName(raw, BeanPropertyDefinition.class);
		return new HashSet<String>(list);
	}
	
	/**
	 * Gets the property with the specified same.
	 * 
	 * If there are more than one properties with the same name, then
	 * a RuntimeException is thrown.
	 * 
	 * @param name
	 *        Property name.
	 * 
	 * @return Returns the property with the specified name.
	 */
	public BeanPropertyDefinition<?> getFirstPropertyByName(String name) {
		List<BeanPropertyDefinition<?>> list = propertiesByName.get(name);
		if (list==null) {
			return null;
		}
		return list.get(0);
	}
	
	/**
	 * Indicates if the analyzed interface has abstract methods
	 * except from property accessors.
	 *  
	 * @return Returns true if it has abstract methods.
	 */
	public boolean isContainsAbstractMethods() {
		return !abstractMethods.isEmpty();
	}
	
	/**
	 * Gets ths properties of the analyzed class that have the specified name.
	 * 
	 * @param name
	 * 
	 * @return Returns a list of properties. If there is no property with the
	 *         specified name, returns null.
	 */
	public List<BeanPropertyDefinition<?>> getPropertiesByName(String name) {
		List<BeanPropertyDefinition<?>> list = propertiesByName.get(name);
		if (list==null) {
			return null;
		}
		return new ArrayList<BeanPropertyDefinition<?>>(list);
	}
	
	
	/**
	 * Indicates if the analyzed interface has odd properties.
	 *  
	 * @return Returns true if it has odd properties.
	 */
	public boolean isContainsOddProperties() {
		return !oddProperties.isEmpty();
	}
	
	/**
	 * Gets the names of all properties of the specified type.
	 * 
	 * @return Returns the names of all properties of the specified type.
	 */
	public Set<String> getNamesOfProperties() {
		return new HashSet<String>(propertiesByName.keySet());
	}
	
	/**
	 * Gets all methods of the analyzed type, excluding property accessors
	 * and static methods.
	 * 
	 * @return Returns the methods of the analyzed class.
	 */
	public Set<Method> getMethods() {
		Set<Method> all = new HashSet<Method>();
		all.addAll(abstractMethods);
		all.addAll(concreteMethods);
		return all;		
	}
	
	/**
	 * Indicates if the analyzed class is Serializable.
	 * 
	 * @return Returns true if the analyzed class is Serializable,
	 *         otherwise returns false. 
	 */
	public boolean isSerializable() {
		return Serializable.class.isAssignableFrom(clazz);
	}
	
	
	/**
	 * Gets the serialVersionUID of the analyzed class, if it has one.
	 *  
	 * @return Returns the serialVersionUID of the analyzed class, if 
	 *         it has one. If the analyzed class is not Serializable,
	 *         or if it does not declare a serialVersionUID, it returns 
	 *         null.
	 */
	public Long getSerialVersionUniqueId() {
		if (!isSerializable()) {
			return null;
		}
		Field serialVersionUID;
		try {
			serialVersionUID = clazz.getDeclaredField("serialVersionUID"); //$NON-NLS-1$
		} catch (SecurityException e) {
			return null;
		} catch (NoSuchFieldException e) {
			return null;
		}
		if (!serialVersionUID.isAccessible()) {
			serialVersionUID.setAccessible(true);			
		}		
		return (Long) ReflectionUtils.get(serialVersionUID, null);
	}
	/**
	 * Gets the values of the specified object's properties.
	 * 
	 * The object must not be null and it must belong to the
	 * type that is analyzed by this object. 
	 * 
	 * @param o
	 *        Object to get its values.
	 *        
	 * @return Returns a map with the specified object's 
	 *         property values. 
	 */
	public Map<String, Object> getPropertyValues(Object o) {	
		Object[] noargs = {};
		Map<String, Object> map = new HashMap<String, Object>();
		for (BeanPropertyDefinition<?> bpd : allProperties.values()) {
			Method getter = bpd.getGetter(); 
			if (getter!=null) {
				Object propertyValue = ReflectionUtils.invoke(getter, o, noargs);
				map.put(bpd.getName(), propertyValue);
			}
		}
		return map;
	}
	
	
	/**
	 * Marker for NonStatic fields.
	 */
	static @interface MarkerOfNonStaticFields {/*empty */}
	

}
