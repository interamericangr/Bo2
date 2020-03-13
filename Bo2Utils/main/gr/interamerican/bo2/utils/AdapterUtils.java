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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import gr.interamerican.bo2.utils.adapters.Transformation;
import gr.interamerican.bo2.utils.adapters.VoidOperation;
import gr.interamerican.bo2.utils.attributes.Named;
import gr.interamerican.bo2.utils.beans.Tree;

/**
 * Utility class taking advantage of {@link Transformation}s.
 */
public class AdapterUtils {
	
	/**
	 * Hidden constructor of utility class.
	 */
	private AdapterUtils() {/* empty */	}

	/**
	 * Creates a list that contains the results of the specified 
	 * adapter operation.
	 * 
	 * If the result of the specified adapter operation on an element
	 * is null, then this result will be included in the result list.
	 *
	 * @param <A>        Type of operation argument.
	 * @param <R>        Type of operation result.
	 * @param list        List containing the elements on which the operation will
	 *        be performed.
	 * @param adapter        Adapter operation.
	 * @return Returns a list that contains the results of the adapter operation
	 *         to the elements of the set.
	 */
	
	public static <A,R> List<R> apply(List<A> list, Transformation<A, R> adapter) {
		List<R> result = new ArrayList<R>();
		for (A a : list) {
			R r = adapter.execute(a);
			result.add(r);
		}
		return result;
	}
	
	/**
	 * Applies a {@link VoidOperation} on all elements of a Collection.
	 *
	 * @param <A>        Type of operation argument.
	 * @param list        List containing the elements on which the operation will
	 *        be performed.
	 * @param adapter        Adapter operation.
	 */
	
	public static <A> void apply(Collection<A> list, VoidOperation<A> adapter) {	
		for (A a : list) {
			adapter.execute(a);			
		}
	}
	
	/**
	 * Applies a {@link VoidOperation} on all elements of a Collection.
	 *
	 * @param <A>        Type of operation argument.
	 * @param array        Array containing the elements on which the operation will
	 *        be performed.
	 * @param adapter        Adapter operation.
	 */
	
	public static <A> void apply(A[] array, VoidOperation<A> adapter) {	
		for (A a : array) {
			adapter.execute(a);			
		}
	}
	
	/**
	 * Creates a list that contains the results of the specified 
	 * adapter operation.
	 *
	 * @param <A>        Type of operation argument.
	 * @param <R>        Type of operation result.
	 * @param set        Set containing the elements.
	 * @param adapter        Adapter operation.
	 * @return Returns a list that contains the
	 */	
	public static <A,R> Set<R> apply(Set<A> set, Transformation<A, R> adapter) {
		Set<R> result = new HashSet<R>();
		for (A a : set) {
			R r = adapter.execute(a);
			if (r!=null) {
				result.add(r);				
			}			
		}
		return result;
	}
	
	/**
	 * Creates a map that contains the results of the specified 
	 * adapter operation.
	 *  
	 *
	 * @param <K>        Type of map key.        
	 * @param <A>        Type of operation argument.
	 * @param <R>        Type of operation result.
	 * @param map        Map containing the elements.
	 * @param adapter        Adapter operation.
	 * @return Returns a list that contains the
	 */	
	public static <K,A,R> Map<K,R> apply(Map<K,A> map, Transformation<A, R> adapter) {
		return apply(map,null,adapter);
	}
	
	/**
	 * Transforms a map by applying a transformation to its keys
	 * and another transformation to its values.
	 * 	
	 *
	 * @param <K>        Type of key in the input map.
	 * @param <V>        Type of value in the input map. 
	 * @param <L>        Type of key in the output map.
	 * @param <W>        Type of value in the output map.
	 *        
	 * @param map        Map to transform.
	 * @param keyTrans        Transformation to apply to the keys.
	 *        If keyTrans is null, then the keys of the 
	 *        output map will be the same as the keys of the 
	 *        input map.
	 * @param valTrans        Transformation to apply to the values.
	 *        If keyTrans is null, then the values of the 
	 *        output map will be the same as the values of the 
	 *        input map.
	 * @return Returns a map who's entries resulted from applying
	 *         the specified transformations on each pair of key 
	 *         and value of the entries of the specified map.
	 */
	@SuppressWarnings("unchecked")
	public static <K,V,L,W> Map<L,W> apply
	(Map<K,V> map, Transformation<K, L> keyTrans, Transformation<V, W> valTrans) {
		Map<L,W> result = new HashMap<L,W>();
		for (Map.Entry<K, V> entry : map.entrySet()) {			
			K k = entry.getKey();
			V v = entry.getValue();
			L l;
			W w;
			if (keyTrans!=null) {
				l = keyTrans.execute(k);				
			} else {
				l = (L) k;
			}
			if (valTrans!=null) {
				w = valTrans.execute(v);				
			} else {
				w = (W) v;
			}
			result.put(l, w);
		}
		return result;
	}
	
	/**
	 * Creates an array that contains the results of the specified 
	 * adapter operation.
	 * 
	 * If the result of the specified adapter operation on an element
	 * is null, then this result will be included in the result array.
	 *
	 * @param <A>        Type of operation argument.
	 * @param <R>        Type of operation result.
	 * @param arguments        Array containing the elements on which the operation will
	 *        be performed.
	 * @param sample        Array necessary in order to create the results array. It 
	 *        can be an empty array. 
	 * @param adapter        Adapter operation.
	 * @return Returns an array that contains the results of the adapter
	 *         operation to the elements of the set.
	 */
	
	public static <A,R> R[] apply(A[] arguments, R[] sample, Transformation<A, R> adapter) {		
		R[] result = ArrayUtils.enforceCapacity(sample, arguments.length);
		for (int i = 0; i < arguments.length; i++) {
			result[i] = adapter.execute(arguments[i]);
		}
		return result;
	}
	
	/**
	 * Creates a tree that contains the results of the specified 
	 * adapter operation.
	 *
	 * @param <A>        Type of operation argument.
	 * @param <R>        Type of operation result.
	 * @param tree        Tree containing the elements.
	 * @param adapter        Adapter operation.
	 * @return Returns a tree that contains the results.
	 */	
	public static <A,R> Tree<R> apply(Tree<A> tree, Transformation<A, R> adapter) {
		R root = adapter.execute(tree.getRootElement());
		Tree<R> result = new Tree<R>(root, tree.getName());
		for (Tree<A> child : tree.getNodes()) {
			Tree<R> node = apply(child, adapter);
			result.add(node);
		}
		return result;
	}

	/**
	 * Concatenates the values of a specified property of all elements in a
	 * collection.
	 *
	 * @param <T>
	 *            Type of elements in the collection
	 * @param collection
	 *            Collection.
	 * @param propertyName
	 *            Name of the property.
	 * @param clazz
	 *            Class of objects in the collection.
	 * @param separator
	 *            Separator that separates the tokens that are extracted from
	 *            the values of the specified property in the collection
	 *            elements.
	 * @return Returns a string.
	 * @deprecated Use {@link #concat(Collection, Function, String)}
	 */
	@Deprecated
	public static <T> String concat(Collection<T> collection, String propertyName, Class<T> clazz, String separator) {
		List<String> properties = getProperty(collection, propertyName, clazz);
		String[] strings = properties.toArray(new String[0]);
		return StringUtils.concatSeparated(separator, strings);
	}

	/**
	 * Concatenates the values of a specified property of all elements in a
	 * collection.
	 *
	 * @param <T>
	 *            Type of elements in the collection
	 * @param collection
	 *            Collection.
	 * @param getter
	 *            Getter of the Property
	 * @param separator
	 *            Separator that separates the tokens that are extracted from
	 *            the values of the specified property in the collection
	 *            elements.
	 * @return Returns a string.
	 */
	public static <T> String concat(Collection<T> collection, Function<T, String> getter, String separator) {
		List<String> properties = getProperty(collection, getter);
		String[] strings = properties.toArray(new String[0]);
		return StringUtils.concatSeparated(separator, strings);
	}

	/**
	 * Applies a {@link gr.interamerican.bo2.utils.adapters.trans.GetProperty} transformation on the specified collection.
	 *
	 * @param <T>
	 *            Type of elements in the collection
	 * @param <P>
	 *            Type of property.
	 * @param collection
	 *            Collection.
	 * @param propertyName
	 *            Name of the property.
	 * @param clazz
	 *            Class of objects in the collection.
	 * @return Returns a list with the values of the specified property in the
	 *         elements of the specified collection.
	 * @deprecated Use {@link #getProperty(Collection, Function)} instead
	 */
	@Deprecated
	public static <T, P> List<P> getProperty(Collection<T> collection, String propertyName, Class<T> clazz) {
		gr.interamerican.bo2.utils.adapters.trans.GetProperty<T, P> getProperty = new gr.interamerican.bo2.utils.adapters.trans.GetProperty<T, P>(propertyName, clazz);
		List<T> list = CollectionUtils.toList(collection);
		return apply(list, getProperty);
	}

	/**
	 * Returns all the element from a collection from the input getter.
	 *
	 * @param <T>
	 *            Type of elements in the collection
	 * @param <V>
	 *            Type of property.
	 * @param collection
	 *            Collection.
	 * @param getter
	 *            Getter of the Property
	 * @return Returns a list with the values of the specified property in the
	 *         elements of the specified collection.
	 */
	public static <T, V> List<V> getProperty(Collection<T> collection, Function<T, V> getter) {
		return collection.stream().map(getter).collect(Collectors.toList());
	}

	/**
	 * Applies a {@link gr.interamerican.bo2.utils.adapters.trans.GetProperty} transformation on the specified collection
	 * for the name property.
	 *
	 * @param <T>
	 *            Type of elements in the collection
	 * @param collection
	 *            Collection.
	 * @param clazz
	 *            Class of objects in the collection.
	 * @return Returns a list with the names of the specified property in the
	 *         elements of the specified collection.
	 * @deprecated Use {@link #getName(Collection)}
	 */
	@Deprecated
	public static <T> List<String> getName(Collection<T> collection, Class<T> clazz) {
		@SuppressWarnings("nls")
		gr.interamerican.bo2.utils.adapters.trans.GetProperty<T, String> getProperty = new gr.interamerican.bo2.utils.adapters.trans.GetProperty<T, String>("name", clazz);
		List<T> list = CollectionUtils.toList(collection);
		return apply(list, getProperty);
	}

	/**
	 * Returns the names from a collection of {@link Named} elements.
	 * 
	 * @param collection
	 *            Collection.
	 * @return Returns a list with the names in the elements of the specified
	 *         collection.
	 */
	public static List<String> getName(Collection<? extends Named> collection) {
		List<? extends Named> list = CollectionUtils.toList(collection);
		return apply(list, Named::getName);
	}

	/**
	 * Concatenates the names of the elements in the specified collection.
	 * 
	 * This method assumes, that the elements in the collection have a name
	 * property.
	 *
	 * @param <T>
	 *            Type of elements in the collection
	 * @param collection
	 *            Collection.
	 * @param clazz
	 *            Class of objects in the collection.
	 * @param separator
	 *            Separator that separates the tokens that are extracted from
	 *            the values of the specified property in the collection
	 *            elements.
	 * @return Returns a string.
	 * @deprecated Use {@link #concatNames(Collection, String)}
	 */
	@Deprecated
	public static <T> String concatNames(Collection<T> collection, Class<T> clazz, String separator) {
		return concat(collection, "name", clazz, separator); //$NON-NLS-1$
	}

	/**
	 * Concatenates the names of the elements in the specified collection.
	 * 
	 * This method assumes, that the elements in the collection are
	 * {@link Named}.
	 *
	 * @param <T>
	 *            Type of elements in the collection
	 * @param collection
	 *            Collection.
	 * @param separator
	 *            Separator that separates the tokens that are extracted from
	 *            the values of the specified property in the collection
	 *            elements.
	 * @return Returns a string.
	 */
	public static <T extends Named> String concatNames(Collection<T> collection, String separator) {
		return concat(collection, Named::getName, separator);
	}
}