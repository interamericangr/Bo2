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

import gr.interamerican.bo2.utils.conditions.Condition;
import gr.interamerican.bo2.utils.conditions.ConditionOnProperty;
import gr.interamerican.bo2.utils.conditions.PropertiesEqual;
import gr.interamerican.bo2.utils.conditions.PropertyEqualsTo;
import gr.interamerican.bo2.utils.conditions.PropertyIsNotNull;
import gr.interamerican.bo2.utils.conditions.PropertyIsNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Utility class with methods that select items from Collections.
 */
public class SelectionUtils {

	/**
	 * Gets the element of a collection that has the maximum value in a 
	 * specified property.
	 * 
	 * Example, if the collection contains elemetns of type Person, and
	 * the class Person has a property age that is of type Integer, then
	 * calling this method on the collection with argument age for property
	 * name, would return the oldest person.
	 * 
	 * @param collection
	 *        Collection with the elements in which the element with the
	 *        maximum value of an element is searched.  
	 * @param propertyName
	 *        Name of the property that is used for the comparison. The 
	 *        type of this property must be comparable with it self.
	 * @param <T>
	 *        Type of elements in the collection.
	 * @param <V>
	 *        Type of the property specified by <code>propertyName</code>
	
	 * 
	 * @return Returns the element that has the maximum property with the
	 *         name specified by <code>propertyName</code> in the collection.
	 *         If the collection is empty, then returns null.
	 */
	public static <T,V extends Comparable<? super V>> T 
	max(Collection<T> collection, String propertyName) {
		V maxElement = null;
		T maxElementOwner = null;
		for (T t : collection) {
			@SuppressWarnings("unchecked")
			V v = (V) ReflectionUtils.getProperty(propertyName, t);
			if ((maxElementOwner==null) || (Utils.nullSafeCompare(maxElement, v)<0)) {
				maxElementOwner = t;
				maxElement = v;
			}			
		}
		return maxElementOwner;
	}

	/**
	 * Gets the element of a collection that has the maximum value in a 
	 * specified property.
	 * 
	 * Example, if the collection contains elements of type Person, and
	 * the class Person has a property age that is of type Integer, then
	 * calling this method on the collection with argument age for property
	 * name, would return the oldest person.
	 * 
	 * @param collection
	 *        Collection with the elements in which the element with the
	 *        maximum value of an element is searched.  
	 * @param propertyName
	 *        Name of the property that is used for the comparison. The 
	 *        type of this property must be comparable with it self.
	 * @param <T>
	 *        Type of elements in the collection.
	 * @param <V>
	 *        Type of the property specified by <code>propertyName</code>
	
	 * 
	 * @return Returns the element that has the maximum property with the
	 *         name specified by <code>propertyName</code> in the collection.
	 *         If the collection is empty, then returns null.
	 */
	public static <T,V extends Comparable<? super V>> T 
	min(Collection<T> collection, String propertyName) {
		V minElement = null;
		T minElementOwner = null;
		for (T t : collection) {
			@SuppressWarnings("unchecked")
			V v = (V) ReflectionUtils.getProperty(propertyName, t);
			
			if ((minElementOwner==null) || (Utils.nullSafeCompare(minElement, v)>0)) {
				minElement = v;
				minElementOwner = t;
			}			
		}
		return minElementOwner;
	}

	/**
	 * Selects a list that contains all elements of the specified collection
	 * that fulfill the specified condition.
	 * 
	 * @param <S>
	 * @param <T>
	 * @param condition
	 * @param collection
	 * 
	 * @return Returns a list that contains all elements of the specified
	 *         collection that fulfill the specified condition.
	 * 
	 * @deprecated duplicate with
	 *             {@link ConditionUtils#getSubset(Collection, Condition)}
	 */
	@Deprecated
	public static <S, T extends S> List<T> selectByCondition (Condition<S> condition, Collection<T> collection) {		 
		List<T> selection = new ArrayList<T>();
		for (T t : collection) {
			if (condition.check(t)) {			
				selection.add(t);				
			}			
		}
		return selection;
	}
	
	/**
	 * Selects the first element in the specified collection
	 * that fulfills the specified condition.
	 * 
	 * @param <T>
	 * @param condition
	 * @param collection
	 * 
	 * @return Returns a list that contains all elements of the specified 
	 *         collection that fulfill the specified condition.
	 */
	public static <T> T selectFirstByCondition (Condition<T> condition, Collection<? extends T> collection) {
		for (T t : collection) {
			if (condition.check(t)) {			
				return t;				
			}			
		}
		return null;
	}
	
	
	
	/**
	 * Gets the items with the specified name from a collection of
	 * object's that have a property name.
	 * 
	 * @param property
	 *        Name of property being used as selection criterion.
	 * @param value 
	 *        Value of selection criterion. It can be null.         
	 * @param collection
	 *        Collection of objects.
	 * @param type
	 *        Type of objects in the collection.
	 * @param <S>
	 *        Type of objects in the collection. 
	 * @param <T>
	 *        Class to evaluate the property with. Must be a super-type of T.
	 *        
	 * @return Returns a list with all elements of the specified collection
	 *         that have the specified property equal to the specified value.
	 */
	public static <S, T extends S> List<T> selectByProperty
	(String property, Object value, Collection<T> collection, Class<S> type) {
		ConditionOnProperty<S> condition = new PropertyEqualsTo<S>(property, type, value);
		return ConditionUtils.getSubset(collection,condition);
	}


	/**
	 * Gets the items with the specified name from a collection of
	 * object's that have a property name.
	 * 
	 * @param name
	 *        Name being searched.  
	 * @param collection
	 *        Collection of objects.
	 * @param type
	 *        Type of objects in the collection.
	 * @param <T>
	 *        Type of objects in the collection. 
	 * @param <S>
	 *        Class to evaluate the property with. Must be a super-type of T.
	 *        
	 * @return Returns a list with all elements of the specified collection
	 *         that have the specified name.
	 */
	public static <S, T extends S> List<T> selectByName(String name, Collection<T> collection, Class<S> type) {
		return selectByProperty("name", name, collection, type); //$NON-NLS-1$
	}
	
	
	
	
	
	
	
	
	


	/**
	 * Gets the first item with the specified property from a collection of
	 * object's that have a property name.
	 * 
	 * @param property
	 *        Name of property being used as selection criterion.
	 * @param value 
	 *        Value of selection criterion. It can be null.         
	 * @param collection
	 *        Collection of objects.
	 * @param type
	 *        Type of objects in the collection.
	 * @param <T>
	 *        Type of objects in the collection. 
	 *        
	 * @return Returns a list with all elements of the specified collection
	 *         that have the specified property equal to the specified value.
	 */
	public static <T> T selectFirstByProperty
	(String property, Object value, Collection<? extends T> collection, Class<T> type) {
		ConditionOnProperty<T> condition = new PropertyEqualsTo<T>(property, type, value);
		return selectFirstByCondition(condition, collection);
	}
	
	/**
	 * Selects the first element of the specified collection that
	 * has the specified properties equal to the specified values.
	 * 
	 * @param <T>
	 * @param properties
	 * @param values
	 * @param collection
	 * @param type
	 * 
	 * @return Returns the first element of the specified collection 
	 *         that has the specified properties equal to the specified 
	 *         values. If there is no element in the collection to 
	 *         fulfill the condition, then returns null.
	 */
	public static <T> T selectFirstByProperties
	(String[] properties, Object[] values, Collection<? extends T> collection, Class<T> type) {
		Condition<T> condition = new PropertiesEqual<T>(properties, values, type);
		return selectFirstByCondition(condition, collection);
	}
	
	/**
	 * Selects between two values, based on a boolean flag.
	 * 
	 * @param flag
	 *        Flag that determines which value will be selected.
	 * @param trueValue
	 *        Return value if flag is true
	 * @param falseValue
	 *        Return value if flag is false	   
	 * @param <T>
	 *        Type of values
	 * 
	 * @return Returns one of the two values, according to the boolean value 
	 *         of the specified flag being true or false.
	 */
	public static <T> T selection(boolean flag, T trueValue, T falseValue) {
		return flag ? trueValue : falseValue;
	}

	/**
	 * Gets the first item from the specified collection that has
	 * the specified property not null.
	 * 
	 * @param property
	 *        Name of property being used as selection criterion.
	 * @param collection
	 *        Collection of objects.
	 * @param type
	 *        Type of objects in the collection.
	 * @param <T>
	 *        Type of objects in the collection. 
	 *        
	 * @return Returns a list with all elements of the specified collection
	 *         that have the specified property equal to the specified value.
	 */
	public static <T> T selectFirstWithNotNullProperty
	(String property, Collection<? extends T> collection, Class<T> type) {
		ConditionOnProperty<T> notNull = new PropertyIsNotNull<T>(property, type);
		return selectFirstByCondition(notNull, collection);
	}

	/**
	 * Gets the first item from the specified collection that has
	 * the specified property null.
	 * 
	 * @param property
	 *        Name of property being used as selection criterion.
	 * @param collection
	 *        Collection of objects.
	 * @param type
	 *        Type of objects in the collection.
	 * @param <T>
	 *        Type of objects in the collection. 
	 *        
	 * @return Returns a list with all elements of the specified collection
	 *         that have the specified property equal to the specified value.
	 */
	public static <T> T selectFirstWithNullProperty
	(String property, Collection<? extends T> collection, Class<T> type) {		
		ConditionOnProperty<T> isNull = new PropertyIsNull<T>(property, type);
		return selectFirstByCondition(isNull, collection);
	}

	/**
	 * Checks if the specified collection contains at least one
	 * element that has the specified property equal to the specified value.
	 * 
	 * @param <T>
	 *        Type of collection elements.
	 * @param property
	 *        Property on which the check is made
	 * @param value
	 *        Value to check against
	 * @param collection
	 *        Collection
	 * @param type
	 *        Class of collection elements.
	 *        
	 * @return Returns true, if there is at least one element in the collection
	 *         whose <code>property</code> is equal to the supplied <code>value</code>.
	 */
	public static <T> Boolean existsByProperty(String property, Object value, Collection<? extends T> collection, Class<T> type) {		
		T t = SelectionUtils.selectFirstByProperty(property, value, collection, type);		
		return t!=null;
	}
	
	/**
	 * Checks if the specified collection contains at least one
	 * element that fulfills the specified condition.
	 * 
	 * @param <T>
	 * @param condition
	 * @param collection
	 * @return Returns true if there is at least one element in the
	 *         specified collection that fulfills the specified
	 *         condition.
	 */
	public static <T> Boolean existsByCondition(Condition<T> condition, Collection<? extends T> collection) {
		T t = SelectionUtils.selectFirstByCondition(condition, collection);
		return (t!=null);
	}
	
	/**
	 * Finds all the elements of a collection that have a property 
	 * with value that matches a given object.
	 * 
	 * This method is not public. it exists to support the sort methods
	 * in the CollectionUtils class. 
	 * 
	 * @param <P>
	 *        Type of element.
	 * @param collection
	 *        Collection of elements.
	 * @param property
	 *        Name of property.
	 * @param sample
	 *        Sample object to check against.
	 *        
	 * @return Returns a list of elements that match.
	 */
	static <P> List<P> getMatchingElements(Collection<P> collection, String property, Object sample) {
		List<P> results = new ArrayList<P>();		
		if((sample==null) || CollectionUtils.isNullOrEmpty(collection)) { 
			return results; 
		}
		for(P p : collection) {
			Object element = JavaBeanUtils.getProperty(property, p);
			if(sample.equals(element)) {
				results.add(p);
			}
		}
		return results;
	}


}
