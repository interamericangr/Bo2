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

import static java.util.Comparator.*;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import gr.interamerican.bo2.utils.beans.AssociationTable;
import gr.interamerican.bo2.utils.conditions.Condition;
import gr.interamerican.bo2.utils.conditions.functional.PropertyEqualsTo;

/**
 * Utility class with utilities for arrays.
 */
public class CollectionUtils {

	/**
	 * Hidden constructor.
	 *
	 * This is a utility class having only static methods. There is no need to
	 * create any instance of this class.
	 */
	private CollectionUtils() {
		/* empty */
	}

	/**
	 * Puts the contents of a {@link Properties} object to a {@link Map}.
	 *
	 * @param properties
	 *            Properties to put to the map.
	 * @param map
	 *            Map that will be updated with the contents of the properties.
	 */
	public static void putPropertiesToMap(Properties properties, Map<String, String> map) {
		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			map.put(key, value);
		}
	}

	/**
	 * Loads the contents of a {@link Properties} object to a {@link Map}.
	 *
	 * @param properties
	 *            Properties to add to the association table.
	 * @param table
	 *            Association table that will be updated with the contents of
	 *            the properties object.
	 */
	public static void putPropertiesToAssociationTable(Properties properties, AssociationTable<String, String> table) {
		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			String left = StringUtils.trim((String) entry.getKey());
			String right = StringUtils.trim((String) entry.getValue());
			table.associate(left, right);
		}
	}

	/**
	 * Rearranges a Set, in order to solve problems caused by changes to the
	 * hash codes of its elements.
	 *
	 * @param <P>
	 *            the generic type
	 * @param set
	 *            the set
	 */
	public static <P> void reArrange(Set<P> set) {
		HashSet<P> temp = new HashSet<P>();
		temp.addAll(set);
		set.clear();
		set.addAll(temp);
	}

	/**
	 * Rearranges a Map, in order to solve problems caused by changes to the
	 * hash codes of its elements.
	 *
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @param map
	 *            the map
	 */
	public static <K, V> void reArrange(Map<K, V> map) {
		HashMap<K, V> temp = new HashMap<K, V>();
		temp.putAll(map);
		map.clear();
		map.putAll(temp);
	}

	/**
	 * Gets a mandatory property from a Properties object.
	 *
	 * If the property is not found, then a RuntimeException will be thrown
	 * indicating that the property is not set.
	 *
	 * @param properties
	 *            Properties object where the property is seeked.
	 * @param key
	 *            Key of the property.
	 *
	 * @return Returns the property value.
	 */
	public static String getMandatoryProperty(Properties properties, String key) {
		String value = properties.getProperty(key);
		if (value == null) {
			String msg = MessageFormat.format("Mandatory property {0} not found", key); //$NON-NLS-1$
			throw new RuntimeException(msg);
		}
		return value.trim();

	}

	/**
	 * Gets an optional property from a Properties object.
	 *
	 * If a property is not found, an empty String is returned.
	 *
	 * @param properties
	 *            the properties
	 * @param key
	 *            the key
	 * @return the value of the key or an epmty String if the key is not found
	 */
	public static String getOptionalProperty(Properties properties, String key) {
		if (properties.getProperty(key) == null) {
			return StringConstants.EMPTY;
		}
		return properties.getProperty(key).trim();
	}

	/**
	 * Adds all elements of an array to a Collection.
	 *
	 *
	 * @param <C>
	 *            Type of collections.
	 * @param <T>
	 *            Type of elements.
	 * @param collection
	 *            Collection to fill.
	 * @param array
	 *            Array containing the elements to add to the collection.
	 * @return Returns the collection.
	 */
	public static <C extends Collection<T>, T> C addAll(C collection, T[] array) {
		collection.addAll(Arrays.asList(array));
		return collection;
	}

	/**
	 * Unsafe cast.
	 *
	 * This method is unsafe. It should be used with caution.
	 *
	 * @param <B>
	 *            Type of element in the input list.
	 * @param <E>
	 *            Type of element in the returned list.
	 * @param list
	 *            List to convert its generic type.
	 * @return Returns the same list, declared as a different generic type.
	 */
	@SuppressWarnings("unchecked")
	public static <B, E extends B> List<E> convert(List<B> list) {
		return (List<E>) list;
	}

	/**
	 * Unsafe cast.
	 *
	 * This method is unsafe. It should be used with caution.
	 *
	 * @param <B>
	 *            Type of element in the input set.
	 * @param <E>
	 *            Type of element in the returned set.
	 * @param set
	 *            Set to convert its generic type.
	 * @return Returns the same list, declared as a different generic type.
	 */
	@SuppressWarnings("unchecked")
	public static <B, E extends B> Set<E> convert(Set<B> set) {
		return (Set<E>) set;
	}

	/**
	 * Safe upcast of a set.
	 *
	 * This method is safe. It can be used whenever necessary to downcast a set.
	 *
	 * @param <B>
	 *            Type of element in the set.
	 * @param set
	 *            Set to downcast.
	 *
	 * @return Returns the same set, declared as a more generic type.
	 */
	@SuppressWarnings("unchecked")
	public static <B> Set<B> upCast(Set<? extends B> set) {
		return (Set<B>) set;
	}

	/**
	 * Checks if a collection contains any null element.
	 *
	 * @param collection
	 *            the collection
	 * @return Returns true if this collection contains even one element that is
	 *         null. If all elements of the collection are not null, then
	 *         returns false.
	 */
	public static boolean containsNull(Collection<?> collection) {
		for (Object object : collection) {
			if (object == null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Modifies the value mapped to a key in a map, if the specified key exists
	 * in the map.
	 *
	 * @param <K>
	 *            Type of key.
	 * @param <V>
	 *            Type of value.
	 * @param map
	 *            Map to modify.
	 * @param key
	 *            Key.
	 * @param newValue
	 *            New value to associate with the key.
	 */
	public static <K, V> void modify(Map<K, V> map, K key, V newValue) {
		if (map.containsKey(key)) {
			map.put(key, newValue);
		}
	}

	/**
	 * Adds the next element in a collection.<br>
	 * The meaning of next is that the type defined by T has an indexProperty.
	 * This property has a getter and a setter.<br>
	 * This method will find the max value of the index property in the
	 * collection and will modify the next element by setting to it the index
	 * property to max + 1. <br>
	 * This method will not fill any gap in the values of the index property.
	 * The collection could have gaps, namely it could have elements with values
	 * 1, 2, 3, 5 in the index property. This method will add the next element
	 * which is 6 and 4 will remain a gap. <br>
	 * If collection is empty, then the new element will get 1 as value of the
	 * index property. <br>
	 * If the nextElement already has a value on the index property that is
	 * greater than the maximum index in the array, then it will keep this
	 * value. In the latter case, it is possible to introduce new gaps in the
	 * indexes of the elements in the collection. <br>
	 * The nextElement is modified, before being added to the collection. Even
	 * if the index property affects the element's hashcode, it is guaranteed
	 * that the hashcode will change before adding the element to the
	 * collection.
	 *
	 * @param <T>
	 *            Type of elements.
	 * @param collection
	 *            Collection in which the next element is being added.
	 * @param nextElement
	 *            Next element that will be added in the collection.
	 * @param getter
	 *            Getter of the Index property
	 * @param setter
	 *            Setter of the Index Property
	 * @param calculator
	 *            Calculator Used for Mathematical Operations
	 */
	static <T, N extends Number & Comparable<N>> void addNext(Collection<T> collection, T nextElement,
			Function<T, N> getter, BiConsumer<T, N> setter, NumberCalculator<N> calculator) {
		T maxOwner = SelectionUtils.max(collection, getter);
		N newIndex = calculator.get(1);
		if (maxOwner != null) {
			N maxIndex = Utils.notNull(getter.apply(maxOwner), calculator.get(0));
			newIndex = calculator.add(maxIndex, calculator.get(1));
		}
		N currentIndex = Utils.notNull(getter.apply(nextElement), calculator.get(0));
		if (currentIndex.compareTo(newIndex) < 0) {
			setter.accept(nextElement, newIndex);
		}
		collection.add(nextElement);
	}

	/**
	 * Adds the next element in a collection.
	 *
	 * The meaning of next is that the type defined by T has an indexProperty.
	 * This property is specified by the parameter
	 * <code>indexPropertyName</code>. This property can be only Integer.
	 * Specifying a property that is not of type Integer or Long, will result in
	 * a {@link RuntimeException}. This method will find the max value of the
	 * index property in the collection and will modify the next element by
	 * setting to it the index property to max + 1. <br>
	 * This method will not fill any gap in the values of the index property.
	 * The collection could have gaps, namely it could have elements with values
	 * 1, 2, 3, 5 in the index property. This method will add the next element
	 * which is 6 and 4 will remain a gap. <br>
	 * If collection is empty, then the new element will get 1 as value of the
	 * index property. <br>
	 * If the nextElement already has a value on the index property that is
	 * greater than the maximum index in the array, then it will keep this
	 * value. In the latter case, it is possible to introduce new gaps in the
	 * indexes of the elements in the collection. <br>
	 * The nextElement is modified, before being added to the collection. Even
	 * if the index property affects the element's hashcode, it is guaranteed
	 * that the hashcode will change before adding the element to the
	 * collection.
	 *
	 * @param <T>
	 *            Type of elements.
	 * @param collection
	 *            Collection in which the next element is being added.
	 * @param nextElement
	 *            Next element that will be added in the collection.
	 * @param indexPropertyName
	 *            Name of the property that defines the index.
	 * @deprecated Use
	 *             {@link #addNextI(Collection, Object, Function, BiConsumer)}
	 */
	@Deprecated
	public static <T> void addNextI(Collection<T> collection, T nextElement, String indexPropertyName) {
		T maxOwner = SelectionUtils.max(collection, indexPropertyName);
		Integer newIndex = 1;
		if (maxOwner != null) {
			Integer maxIndex = (Integer) ReflectionUtils.getProperty(indexPropertyName, maxOwner);
			maxIndex = Utils.notNull(maxIndex, 0);
			newIndex = maxIndex + 1;
		}
		Integer currentIndex = (Integer) ReflectionUtils.getProperty(indexPropertyName, nextElement);
		currentIndex = Utils.notNull(currentIndex, 0);
		if (currentIndex < newIndex) {
			ReflectionUtils.setProperty(indexPropertyName, newIndex, nextElement);
		}
		collection.add(nextElement);
	}

	/**
	 * Adds the next element in a collection.<br>
	 * The meaning of next is that the type defined by T has an indexProperty.
	 * This property has a getter and a setter and is an Integer.<br>
	 * This method will find the max value of the index property in the
	 * collection and will modify the next element by setting to it the index
	 * property to max + 1. <br>
	 * This method will not fill any gap in the values of the index property.
	 * The collection could have gaps, namely it could have elements with values
	 * 1, 2, 3, 5 in the index property. This method will add the next element
	 * which is 6 and 4 will remain a gap. <br>
	 * If collection is empty, then the new element will get 1 as value of the
	 * index property. <br>
	 * If the nextElement already has a value on the index property that is
	 * greater than the maximum index in the array, then it will keep this
	 * value. In the latter case, it is possible to introduce new gaps in the
	 * indexes of the elements in the collection. <br>
	 * The nextElement is modified, before being added to the collection. Even
	 * if the index property affects the element's hashcode, it is guaranteed
	 * that the hashcode will change before adding the element to the
	 * collection.
	 *
	 * @param <T>
	 *            Type of elements.
	 * @param collection
	 *            Collection in which the next element is being added.
	 * @param nextElement
	 *            Next element that will be added in the collection.
	 * @param getter
	 *            Getter of the Index property
	 * @param setter
	 *            Setter of the Index Property
	 */
	public static <T> void addNextI(Collection<T> collection, T nextElement, Function<T, Integer> getter,
			BiConsumer<T, Integer> setter) {
		addNext(collection, nextElement, getter, setter, NumberCalculator.INTEGER);
	}

	/**
	 * Adds the next element in a collection.
	 *
	 * This is exactly the same method as
	 * <code>addNextI(collection, t, string)</code> with the difference that it
	 * is intended for an index property of type Long.
	 *
	 * @param <T>
	 *            Type of elements.
	 *
	 * @param collection
	 *            Collection in which the next element is being added.
	 * @param nextElement
	 *            Next element that will be added in the collection.
	 * @param indexPropertyName
	 *            Name of the property that defines the index.
	 * @see #addNextI(Collection, Object, String)
	 * @deprecated Use
	 *             {@link #addNextL(Collection, Object, Function, BiConsumer)}
	 */
	@Deprecated
	public static <T> void addNextL(Collection<T> collection, T nextElement, String indexPropertyName) {
		T maxOwner = SelectionUtils.max(collection, indexPropertyName);
		Long newIndex = 1L;
		if (maxOwner != null) {
			Long maxIndex = (Long) ReflectionUtils.getProperty(indexPropertyName, maxOwner);
			maxIndex = Utils.notNull(maxIndex, 0L);
			newIndex = maxIndex + 1;
		}
		Long currentIndex = (Long) ReflectionUtils.getProperty(indexPropertyName, nextElement);
		currentIndex = Utils.notNull(currentIndex, 0L);
		if (currentIndex < newIndex) {
			ReflectionUtils.setProperty(indexPropertyName, newIndex, nextElement);
		}
		collection.add(nextElement);
	}

	/**
	 * Adds the next element in a collection.<br>
	 * The meaning of next is that the type defined by T has an indexProperty.
	 * This property has a getter and a setter and is a Long.<br>
	 * This method will find the max value of the index property in the
	 * collection and will modify the next element by setting to it the index
	 * property to max + 1. <br>
	 * This method will not fill any gap in the values of the index property.
	 * The collection could have gaps, namely it could have elements with values
	 * 1, 2, 3, 5 in the index property. This method will add the next element
	 * which is 6 and 4 will remain a gap. <br>
	 * If collection is empty, then the new element will get 1 as value of the
	 * index property. <br>
	 * If the nextElement already has a value on the index property that is
	 * greater than the maximum index in the array, then it will keep this
	 * value. In the latter case, it is possible to introduce new gaps in the
	 * indexes of the elements in the collection. <br>
	 * The nextElement is modified, before being added to the collection. Even
	 * if the index property affects the element's hashcode, it is guaranteed
	 * that the hashcode will change before adding the element to the
	 * collection.
	 *
	 * @param <T>
	 *            Type of elements.
	 * @param collection
	 *            Collection in which the next element is being added.
	 * @param nextElement
	 *            Next element that will be added in the collection.
	 * @param getter
	 *            Getter of the Index property
	 * @param setter
	 *            Setter of the Index Property
	 */
	public static <T> void addNextL(Collection<T> collection, T nextElement, Function<T, Long> getter,
			BiConsumer<T, Long> setter) {
		addNext(collection, nextElement, getter, setter, NumberCalculator.LONG);
	}

	/**
	 * Adds the next element in a collection.
	 *
	 * This is exactly the same method as
	 * <code>addNextI(collection, t, string)</code> with the difference that it
	 * is intended for an index property of type Short.
	 *
	 * @param <T>
	 *            Type of elements.
	 * @param collection
	 *            Collection in which the next element is being added.
	 * @param nextElement
	 *            Next element that will be added in the collection.
	 * @param indexPropertyName
	 *            Name of the property that defines the index.
	 * @see #addNextI(Collection, Object, String)
	 * @deprecated use
	 *             {@link #addNextS(Collection, Object, Function, BiConsumer)}
	 */
	@Deprecated
	public static <T> void addNextS(Collection<T> collection, T nextElement, String indexPropertyName) {
		T maxOwner = SelectionUtils.max(collection, indexPropertyName);
		Short newIndex = 1;
		if (maxOwner != null) {
			Short maxIndex = (Short) ReflectionUtils.getProperty(indexPropertyName, maxOwner);
			maxIndex = Utils.notNull(maxIndex, (short) 0);
			newIndex = (short) (maxIndex + 1);
		}
		Short currentIndex = (Short) ReflectionUtils.getProperty(indexPropertyName, nextElement);
		currentIndex = Utils.notNull(currentIndex, (short) 0);
		if (currentIndex < newIndex) {
			ReflectionUtils.setProperty(indexPropertyName, newIndex, nextElement);
		}
		collection.add(nextElement);
	}

	/**
	 * Adds the next element in a collection.<br>
	 * The meaning of next is that the type defined by T has an indexProperty.
	 * This property has a getter and a setter and is a Short.<br>
	 * This method will find the max value of the index property in the
	 * collection and will modify the next element by setting to it the index
	 * property to max + 1. <br>
	 * This method will not fill any gap in the values of the index property.
	 * The collection could have gaps, namely it could have elements with values
	 * 1, 2, 3, 5 in the index property. This method will add the next element
	 * which is 6 and 4 will remain a gap. <br>
	 * If collection is empty, then the new element will get 1 as value of the
	 * index property. <br>
	 * If the nextElement already has a value on the index property that is
	 * greater than the maximum index in the array, then it will keep this
	 * value. In the latter case, it is possible to introduce new gaps in the
	 * indexes of the elements in the collection. <br>
	 * The nextElement is modified, before being added to the collection. Even
	 * if the index property affects the element's hashcode, it is guaranteed
	 * that the hashcode will change before adding the element to the
	 * collection.
	 *
	 * @param <T>
	 *            Type of elements.
	 * @param collection
	 *            Collection in which the next element is being added.
	 * @param nextElement
	 *            Next element that will be added in the collection.
	 * @param getter
	 *            Getter of the Index property
	 * @param setter
	 *            Setter of the Index Property
	 */
	public static <T> void addNextS(Collection<T> collection, T nextElement, Function<T, Short> getter,
			BiConsumer<T, Short> setter) {
		addNext(collection, nextElement, getter, setter, NumberCalculator.SHORT);
	}

	/**
	 * Adds the elements of a collection to another collection, incrementing the
	 * codes of the added elements.
	 *
	 * @param <T>
	 *            the generic type
	 * @param collection
	 *            the collection
	 * @param elementsToAdd
	 *            the elements to add
	 * @param indexPropertyName
	 *            the index property name
	 * @see #addNextL(Collection, Object, String)
	 * @deprecated Use
	 *             {@link #addNextLs(Collection, Collection, Function, BiConsumer)}
	 */
	@Deprecated
	public static <T> void addNextLs(Collection<T> collection, Collection<T> elementsToAdd, String indexPropertyName) {
		for (T nextElement : elementsToAdd) {
			addNextL(collection, nextElement, indexPropertyName);
		}
	}

	/**
	 * Adds the elements of a collection to another collection, incrementing the
	 * codes of the added elements.
	 *
	 * @param <T>
	 *            the generic type
	 * @param collection
	 *            the collection
	 * @param elementsToAdd
	 *            the elements to add
	 * @param getter
	 *            Getter of the Index property
	 * @param setter
	 *            Setter of the Index Property
	 * @see #addNextL(Collection, Object, Function, BiConsumer)
	 */
	public static <T> void addNextLs(Collection<T> collection, Collection<T> elementsToAdd, Function<T, Long> getter,
			BiConsumer<T, Long> setter) {
		for (T nextElement : elementsToAdd) {
			addNextL(collection, nextElement, getter, setter);
		}
	}

	/**
	 * Puts the elements of a collection in a map using a specified property of
	 * the elements as key.
	 *
	 * @param <K>
	 *            Type of property used as key.
	 * @param <V>
	 *            Type of elements in the collection. The type defined be
	 *            entityType.
	 * @param collection
	 *            Collection with the elements.
	 * @param property
	 *            Name of the property that will be used as key.
	 * @param entityType
	 *            Declaration class of elements in the collection.
	 * @return Returns a map that maps all elements of the collection to the
	 *         value of the specified property.
	 * @throws NullPointerException
	 *             If any of the collection's elements has the specified
	 *             property null.
	 * @throws RuntimeException
	 *             If the class does not have a property with the specified
	 *             name.
	 * @deprecated Switch to {@link #toMap(Collection, Function)}
	 */
	@Deprecated
	public static <K, V> Map<K, V> toMap(Collection<V> collection, String property, Class<V> entityType) {
		PropertyDescriptor pd = JavaBeanUtils.getPropertyDescriptor(entityType, property);
		if (pd == null) {
			throw Exceptions.invalidPropertyName(entityType, property);
		}
		Map<K, V> map = new HashMap<K, V>();

		for (V v : collection) {
			@SuppressWarnings("unchecked")
			K key = (K) JavaBeanUtils.getProperty(pd, v);
			map.put(key, v);
		}
		return map;
	}

	/**
	 * Puts the elements of a collection in a map using a specified getter of
	 * the elements as key.
	 *
	 * @param <K>
	 *            Type of property used as key.
	 * @param <V>
	 *            Type of elements in the collection. The type defined be
	 *            entityType.
	 * @param collection
	 *            Collection with the elements.
	 * @param getter 
	 * @return Returns a map that maps all elements of the collection to the
	 *         value of the specified property.
	 * @throws NullPointerException
	 *             If any of the collection's elements has the specified
	 *             property null.
	 */
	public static <K, V> Map<K, V> toMap(Collection<V> collection, Function<V, K> getter) {
		Map<K, V> map = new HashMap<>();
		for (V v : collection) {
			K key = getter.apply(v);
			map.put(key, v);
		}
		return map;
	}

	/**
	 * Puts the elements of an {@link Iterable} to a {@link Set}.
	 *
	 * @param <P>
	 *            type of elements.
	 * @param iter
	 *            Iterable
	 * @return a Set
	 */
	public static <P> Set<P> iterableToSet(Iterable<P> iter) {
		Set<P> result = new HashSet<P>();
		for (P p : iter) {
			result.add(p);
		}
		return result;
	}

	/**
	 * Sorts a collection based on a property of its elements.
	 *
	 * @param <P>
	 *            type of elements.
	 * @param <T>
	 *            type of elements in the collection.
	 * @param collection
	 *            input collection.
	 * @param clazz
	 *            class of elements
	 * @param property
	 *            property name to base the sorting on.
	 *
	 * @return a List with sorted elements.
	 * @deprecated use {@link #sort(Collection, Function)} instead
	 */
	@Deprecated
	public static <P, T extends P> List<T> sort(Collection<T> collection, Class<P> clazz, String property) {
		List<T> list = new ArrayList<T>(collection);
		Comparator<P> comparator = new gr.interamerican.bo2.utils.comparators.PropertyBasedComparator<P>(clazz, property);
		Collections.sort(list, comparator);
		return list;
	}

	/**
	 * Sorts a collection based on a method of it's elements.
	 *
	 * @param <T>
	 *            type of elements in the collection.
	 * @param <V1>
	 *            type of value of getter
	 * @param collection
	 *            input collection.
	 * @param getter
	 *            the getter
	 *
	 * @return a List with sorted elements.
	 */
	public static <T, V1 extends Comparable<? super V1>> List<T> sort(Collection<T> collection,
			Function<T, V1> getter) {
		return sort(collection, getter, null);
	}

	/**
	 * Sorts a collection based on two methods (getters) of it's elements.
	 *
	 * @param <T>
	 *            type of elements in the collection.
	 * @param <V1>
	 *            type of value of 1st getter
	 * @param <V2>
	 *            type of value of 2nd getter
	 * @param collection
	 *            input collection.
	 * @param getter
	 *            First Getter
	 * @param otherGetter
	 *            Second Getter
	 *
	 * @return a List with sorted elements.
	 */
	public static <T, V1 extends Comparable<? super V1>, V2 extends Comparable<? super V2>> List<T> sort(
			Collection<T> collection, Function<T, V1> getter, Function<T, V2> otherGetter) {
		Comparator<T> c = comparing(getter, nullsFirst(naturalOrder()));
		if (otherGetter != null) {
			c = c.thenComparing(otherGetter, nullsFirst(naturalOrder()));
		}
		return collection.stream().sorted(c).collect(Collectors.toList());
	}

	/**
	 * Inverse-sorts a collection based on a property of its elements.
	 *
	 * @param <P>
	 *            type of elements.
	 * @param <T>
	 *            type of elements in the collection.
	 * @param collection
	 *            input collection.
	 * @param clazz
	 *            class of elements
	 * @param property
	 *            property name to base the sorting on.
	 *
	 * @return a List with inverse-sorted elements.
	 * 
	 * @deprecated Use {@link #inverseSort(Collection, Function)} instead
	 */
	@Deprecated
	public static <P, T extends P> List<T> inverseSort(Collection<T> collection, Class<P> clazz, String property) {
		List<T> list = new ArrayList<T>(collection);
		Comparator<P> comparator = new gr.interamerican.bo2.utils.comparators.PropertyBasedComparator<P>(clazz, property);
		Collections.sort(list, Collections.reverseOrder(comparator));
		return list;
	}

	/**
	 * Inverse-sorts a collection based on a property of its elements.
	 *
	 * @param <T>
	 *            type of elements in the collection.
	 * @param <V>
	 *            type of value of getter
	 * @param collection
	 *            input collection.
	 * @param getter
	 *            the getter
	 *
	 * @return a List with inverse-sorted elements.
	 */
	public static <T, V extends Comparable<? super V>> List<T> inverseSort(Collection<T> collection,
			Function<T, V> getter) {
		Comparator<T> c = comparing(getter, nullsLast(reverseOrder()));
		return collection.stream().sorted(c).collect(Collectors.toList());
	}

	/**
	 * Returns true, if a collection is null or empty.
	 *
	 * @param c
	 *            Collection to check.
	 *
	 * @return Returns true, if c is null, or c.size()==0.
	 */
	public static boolean isNullOrEmpty(Collection<?> c) {
		return c == null || c.size() == 0;
	}

	/**
	 * Reads a properties object from a resource path.
	 *
	 * @param path
	 *            Path to the properties file.
	 * @param isResourceStream
	 *            Specifies if path refers to a resource path or to a file.
	 * @return Returns the properties object read.
	 */
	public static Properties readProperties(String path, boolean isResourceStream) {
		try (InputStream stream = getStream(path, isResourceStream)) {
			Properties p = new Properties();
			p.load(stream);
			return p;
		} catch (IOException ioe) {
			String msg = "Could not load resource " + path; //$NON-NLS-1$
			throw new RuntimeException(msg, ioe);
		}
	}

	/**
	 * Reads a properties object from a resource path.
	 *
	 * @param path
	 *            Path to the properties file.
	 * @return Returns the properties object read.
	 */
	public static Properties readProperties(String path) {
		return readProperties(path, true);
	}

	/**
	 * Reads an {@link EnhancedProperties} object from an inputstream.
	 *
	 * @param stream
	 *            InputStream to read the properties.
	 *
	 * @return Returns the properties object read.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static Properties readEnhancedProperties(InputStream stream) throws IOException {
		EnhancedProperties p = new EnhancedProperties();
		p.load(stream);
		return p;
	}

	/**
	 * Reads an {@link EnhancedProperties} object from a resource path.
	 *
	 * @param path
	 *            Path to the properties file.
	 * @param isResourceStream
	 *            Specifies if path refers to a resource path or to a file.
	 * @return Returns the properties object read.
	 */
	public static Properties readEnhancedProperties(String path, boolean isResourceStream) {
		try (InputStream stream = getStream(path, isResourceStream)) {
			return readEnhancedProperties(stream);
		} catch (IOException ioe) {
			String msg = "Could not load resource " + path; //$NON-NLS-1$
			throw new RuntimeException(msg, ioe);
		}
	}

	/**
	 * Reads an {@link EnhancedProperties} object from a resource path.
	 *
	 * @param path
	 *            Path to the properties file.
	 * @return Returns the properties object read.
	 */
	public static Properties readEnhancedProperties(String path) {
		return readEnhancedProperties(path, true);
	}

	/**
	 * Reads a properties object from a resource path.
	 *
	 * @param path
	 *            Path to the properties file.
	 * @param isResourceStream
	 *            Specifies if path refers to a resource path or to a file.
	 * @return Returns the properties object read.
	 */
	public static Properties readPropertiesFromXML(String path, boolean isResourceStream) {
		try (InputStream stream = getStream(path, isResourceStream)) {
			Properties p = new Properties();
			p.loadFromXML(stream);
			return p;
		} catch (IOException ioe) {
			String msg = "Could not load resource " + path; //$NON-NLS-1$
			throw new RuntimeException(msg, ioe);
		}
	}

	/**
	 * Reads a resource path and returns an {@link InputStream}.
	 * 
	 * @param path
	 *            The path of the resource
	 * @param isResourceStream
	 *            If it's a resourceStream
	 * @return The {@link InputStream}
	 * @throws IOException
	 */
	static InputStream getStream(String path, boolean isResourceStream) throws IOException {
		if (isResourceStream) {
			return StreamUtils.getResourceStream(path);
		}
		return StreamUtils.getFileStream(path);
	}

	/**
	 * Reads a properties object from a resource path.
	 *
	 * @param path
	 *            Path to the properties file.
	 * @return Returns the properties object read.
	 */
	public static Properties readPropertiesFromXML(String path) {
		return readPropertiesFromXML(path, true);
	}

	/**
	 * Iterates a collection, and sums the {@link Number}s retrieved from a
	 * specified getter of each object.<br>
	 * Nulls are excluded from the addition.
	 *
	 * @param <T>
	 *            the type of the object
	 * @param <N>
	 *            the type of the number
	 * @param collection
	 *            the collection
	 * @param getter
	 *            Getter of the property
	 * @param calculator
	 *            Calculator Used for Mathematical Operations
	 * @return Returns the sum
	 */
	static <T, N extends Number> N sum(Collection<T> collection, Function<T, N> getter,
			NumberCalculator<N> calculator) {
		N total = calculator.get(0);
		for (T t : collection) {
			N number = getter.apply(t);
			if (number != null) {
				total = calculator.add(total, number);
			}
		}
		return total;
	}

	/**
	 * Iterates a collection, and sums the BigDecimal elements contained in the
	 * specified property of each object.
	 *
	 * Nulls are excluded from the addition.
	 *
	 *
	 * @param <T>
	 *            the generic type
	 * @param collection
	 *            the collection
	 * @param clazz
	 *            the clazz
	 * @param property
	 *            the property
	 * @return Returns the sum.
	 * @deprecated Use {@link #sumBD(Collection, Function)} instead
	 */
	@Deprecated
	public static <T> BigDecimal sumBD(Collection<T> collection, Class<T> clazz, String property) {
		PropertyDescriptor pd = JavaBeanUtils.getPropertyDescriptor(clazz, property);
		BigDecimal total = BigDecimal.ZERO;
		for (Iterator<T> it = collection.iterator(); it.hasNext();) {
			T t = it.next();
			BigDecimal bd = (BigDecimal) JavaBeanUtils.getProperty(pd, t);
			if (bd != null) {
				total = total.add(bd);
			}
		}
		return total;
	}

	/**
	 * Iterates a collection, and sums the {@link BigDecimal}s retrieved from a
	 * specified getter of each object.<br>
	 * Nulls are excluded from the addition.
	 *
	 * @param <T>
	 *            the type of the object
	 * @param collection
	 *            the collection
	 * @param getter
	 *            Getter of the property
	 * @return Returns the sum
	 */
	public static <T> BigDecimal sumBD(Collection<T> collection, Function<T, BigDecimal> getter) {
		return sum(collection, getter, NumberCalculator.BIGDECIMAL);
	}

	/**
	 * Iterates a collection, and sums the Integer elements contained in the
	 * specified property of each object.
	 *
	 * Nulls are excluded from the addition.
	 *
	 *
	 * @param <T>
	 *            the generic type
	 * @param collection
	 *            the collection
	 * @param clazz
	 *            the clazz
	 * @param property
	 *            the property
	 * @return Returns the sum.
	 * @deprecated Use {@link #sumI(Collection, Function)} instead
	 */
	@Deprecated
	public static <T> Integer sumI(Collection<T> collection, Class<T> clazz, String property) {
		PropertyDescriptor pd = JavaBeanUtils.getPropertyDescriptor(clazz, property);
		Integer total = 0;
		for (Iterator<T> it = collection.iterator(); it.hasNext();) {
			T t = it.next();
			Integer bd = (Integer) JavaBeanUtils.getProperty(pd, t);
			if (bd != null) {
				total = total + bd;
			}
		}
		return total;
	}

	/**
	 * Iterates a collection, and sums the {@link Integer}s retrieved from a
	 * specified getter of each object.<br>
	 * Nulls are excluded from the addition.
	 *
	 * @param <T>
	 *            the type of the object
	 * @param collection
	 *            the collection
	 * @param getter
	 *            Getter of the property
	 * @return Returns the sum
	 */
	public static <T> Integer sumI(Collection<T> collection, Function<T, Integer> getter) {
		return sum(collection, getter, NumberCalculator.INTEGER);
	}

	/**
	 * Iterates a collection, and sums the Long elements contained in the
	 * specified property of each object.
	 *
	 * Nulls are excluded from the addition.
	 *
	 *
	 * @param <T>
	 *            the generic type
	 * @param collection
	 *            the collection
	 * @param clazz
	 *            the clazz
	 * @param property
	 *            the property
	 * @return Returns the sum.
	 * @deprecated Use {@link #sumL(Collection, Function)} instead
	 */
	@Deprecated
	public static <T> Long sumL(Collection<T> collection, Class<T> clazz, String property) {
		PropertyDescriptor pd = JavaBeanUtils.getPropertyDescriptor(clazz, property);
		Long total = 0L;
		for (Iterator<T> it = collection.iterator(); it.hasNext();) {
			T t = it.next();
			Long bd = (Long) JavaBeanUtils.getProperty(pd, t);
			if (bd != null) {
				total = total + bd;
			}
		}
		return total;
	}

	/**
	 * Iterates a collection, and sums the {@link Long}s retrieved from a
	 * specified getter of each object.<br>
	 * Nulls are excluded from the addition.
	 *
	 * @param <T>
	 *            the type of the object
	 * @param collection
	 *            the collection
	 * @param getter
	 *            Getter of the property
	 * @return Returns the sum
	 */
	public static <T> Long sumL(Collection<T> collection, Function<T, Long> getter) {
		return sum(collection, getter, NumberCalculator.LONG);
	}

	/**
	 * Iterates a collection, and sums the Double elements contained in the
	 * specified property of each object.
	 *
	 * Nulls are excluded from the addition.
	 *
	 *
	 * @param <T>
	 *            the generic type
	 * @param collection
	 *            the collection
	 * @param clazz
	 *            the clazz
	 * @param property
	 *            the property
	 * @return Returns the sum.
	 * @deprecated Use {@link #sumD(Collection, Function)} instead
	 */
	@Deprecated
	public static <T> Double sumD(Collection<T> collection, Class<T> clazz, String property) {
		PropertyDescriptor pd = JavaBeanUtils.getPropertyDescriptor(clazz, property);
		Double total = 0.0;
		for (Iterator<T> it = collection.iterator(); it.hasNext();) {
			T t = it.next();
			Double bd = (Double) JavaBeanUtils.getProperty(pd, t);
			if (bd != null) {
				total = total + bd;
			}
		}
		return total;
	}

	/**
	 * Iterates a collection, and sums the {@link Double}s retrieved from a
	 * specified getter of each object.<br>
	 * Nulls are excluded from the addition.
	 *
	 * @param <T>
	 *            the type of the object
	 * @param collection
	 *            the collection
	 * @param getter
	 *            Getter of the property
	 * @return Returns the sum
	 */
	public static <T> Double sumD(Collection<T> collection, Function<T, Double> getter) {
		return sum(collection, getter, NumberCalculator.DOUBLE);
	}

	/**
	 * Puts all elements of a collection in an array.
	 *
	 * The order of the elements in the array is this of the collection's
	 * iterator.
	 *
	 * @param <T>
	 *            the generic type
	 * @param collection
	 *            the collection
	 * @param array
	 *            the array
	 * @return Returns an array, that has length equal to the size of the
	 *         specified collection and contains all elements of the collection.
	 */
	public static <T> T[] toArray(Collection<T> collection, T[] array) {
		if (collection instanceof List) {
			List<T> list = (List<T>) collection;
			return list.toArray(array);
		}

		T[] newArray = ArrayUtils.enforceCapacity(array, collection.size());
		int i = 0;
		for (Iterator<T> it = collection.iterator(); it.hasNext();) {
			T t = it.next();
			newArray[i] = t;
			i++;
		}
		return newArray;
	}

	/**
	 * Creates a sub-Map of the original map, that contains only the elements
	 * that belong to the specified keys.
	 *
	 * @param <K>
	 *            Type of keys.
	 * @param <V>
	 *            Type of values.
	 * @param map
	 *            Map
	 * @param keys
	 *            Set with the keys that will be inserted in the new map.
	 * @return Returns a new map.
	 */
	public static <K, V> Map<K, V> subMap(Map<K, V> map, Collection<K> keys) {
		Map<K, V> newMap = new HashMap<K, V>();
		for (K k : keys) {
			V v = map.get(k);
			if (v != null) {
				newMap.put(k, v);
			}
		}
		return newMap;
	}

	/**
	 * Converts the specified collection to List.
	 *
	 * @param <T>
	 *            the generic type
	 * @param collection
	 *            the collection
	 * @return Returns the list.
	 */
	public static <T> List<T> toList(Collection<T> collection) {
		if (collection instanceof List) {
			return (List<T>) collection;
		}
		return new ArrayList<T>(collection);
	}

	/**
	 * Converts the specified collection to List.
	 *
	 * @param <T>
	 *            the generic type
	 * @param collection
	 *            the collection
	 * @return Returns the list.
	 */
	public static <T> Set<T> toSet(Collection<T> collection) {
		if (collection instanceof Set) {
			return (Set<T>) collection;
		}
		return new HashSet<T>(collection);
	}

	/**
	 * Converts the specified array to a Set.
	 *
	 * @param <T>
	 *            the generic type
	 * @param array
	 *            the array
	 * @return Returns the list.
	 */
	public static <T> Set<T> toSet(T[] array) {
		Set<T> set = new HashSet<T>();
		for (T t : array) {
			set.add(t);
		}
		return set;
	}

	/**
	 * Clears a map from all entries that have a null value.
	 *
	 * @param <K>
	 *            Type of key in the map.
	 * @param <V>
	 *            Type of value in the map.
	 * @param map
	 *            Map to clear from null entries.
	 */
	public static <K, V> void removeNulls(Map<K, V> map) {
		Set<K> nullKeys = new HashSet<K>();
		for (K key : map.keySet()) {
			if (map.get(key) == null) {
				nullKeys.add(key);
			}
		}
		for (K key : nullKeys) {
			map.remove(key);
		}
	}

	/**
	 * Gets a property that mustn't be empty from a properties object.
	 *
	 * @param key
	 *            Property key.
	 * @param properties
	 *            The properties object to get the property.
	 *
	 * @return Returns the value of the property.
	 * @throws RuntimeException
	 *             If the property is null or empty.
	 */
	public static String notEmptyProperty(String key, Properties properties) {
		String value = properties.getProperty(key);
		value = StringUtils.trim(value);
		if (StringUtils.isNullOrBlank(value)) {
			String msg = MessageFormat.format("Property {0} is missing or empty.", key); //$NON-NLS-1$
			throw new RuntimeException(msg);
		}
		return value;
	}

	/**
	 * Returns the count of elements in the specified collection that fulfill
	 * the specified condition.
	 *
	 * @param <S>
	 *            the generic type
	 * @param <T>
	 *            the generic type
	 * @param condition
	 *            the condition
	 * @param collection
	 *            the collection
	 * @return Returns a list that contains all elements of the specified
	 *         collection that fulfill the specified condition.
	 */
	public static <S, T extends S> int countByCondition(Condition<S> condition, Collection<T> collection) {
		int count = 0;
		for (Iterator<T> it = collection.iterator(); it.hasNext();) {
			T t = it.next();
			if (condition.check(t)) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Gets the count of items in the specified collection that have the
	 * specified property equal to the specified value.
	 *
	 * @param <S>
	 *            Type of objects in the collection.
	 * @param <T>
	 *            Class to evaluate the property with. Must be a super-type of
	 *            T.
	 *
	 * @param property
	 *            Name of property being used as selection criterion.
	 * @param value
	 *            Value of selection criterion. It can be null.
	 * @param collection
	 *            Collection of objects.
	 * @param type
	 *            Type of objects in the collection.
	 * @return Returns the count of items in the specified collection that have
	 *         the specified property equal to the specified value.
	 * @deprecated Use {@link #countByProperty(Function, Comparable, Collection)}
	 */
	@Deprecated
	public static <S, T extends S> int countByProperty(String property, Object value, Collection<T> collection,
			Class<S> type) {
		Condition<S> condition = new gr.interamerican.bo2.utils.conditions.PropertyEqualsTo<>(property, type, value);
		return countByCondition(condition, collection);
	}

	/**
	 * Gets the count of items in the specified collection that have the
	 * specified property equal to the specified value.
	 *
	 * @param <T>
	 *            Type of items
	 * @param <P>
	 *            Type of Property
	 *
	 * @param getter
	 *            Getter of the property being used as selection criterion.
	 * @param value
	 *            Value of selection criterion. It can be null.
	 * @param collection
	 *            Collection of objects.
	 * @return Returns the count of items in the specified collection that have
	 *         the specified property equal to the specified value.
	 */
	public static <T, P extends Comparable<? super P>> int countByProperty(Function<T, P> getter, P value, Collection<T> collection) {
		Condition<T> condition = new PropertyEqualsTo<>(getter, value);
		return countByCondition(condition, collection);
	}

	/**
	 * Partition a list to a list that contains sub-lists of the list with a
	 * specified size. The last sub-list may be of size less than {@code size}.
	 * The sub-lists maintain the initial ordering. <br>
	 * The supplied argument is neither altered nor returned as part of the
	 * result.
	 *
	 * @param <T>
	 *            the generic type
	 * @param list
	 *            the list
	 * @param size
	 *            the size
	 * @return a list that contains sub-lists of specific size.
	 */
	public static <T> List<List<T>> partition(List<T> list, int size) {
		List<List<T>> result = new ArrayList<List<T>>();
		if (list.size() < size) { // only return 1 partition
			List<T> part = new ArrayList<T>();
			part.addAll(list); // defensively copy input
			result.add(part);
			return result;
		}
		int ctr = 0;
		List<T> part = new ArrayList<T>();
		for (T t : list) {
			if (ctr >= size && ctr % size == 0) { // not the first time
				result.add(part);
				part = new ArrayList<T>();
				part.add(t);
			} else {
				part.add(t);
			}
			ctr++;
		}
		if (!part.isEmpty()) {
			result.add(part);
		}
		return result;
	}

}
