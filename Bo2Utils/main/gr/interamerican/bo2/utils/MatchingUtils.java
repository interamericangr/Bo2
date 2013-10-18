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

import gr.interamerican.bo2.utils.beans.Pair;
import gr.interamerican.bo2.utils.matching.MatchingRule;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Utility class that provides various methods that match elements
 * of different collections.
 */
public class MatchingUtils {
	
	/**
	 * runtime exception message for attempting to unite two sets with different keys
	 */
	public static final String LEFT_AND_RIGHT_TYPES_MISMATCH = 
		"The types of the left elements don't match with the types of the right"; //$NON-NLS-1$

	/**
	 * Creates a map that contains pairs of values that exist in two
	 * different maps with the same key.
	 * 
	 * The output map will have a <code>keySet()</code> that is the intersection
	 * of the key sets of the left and right maps. If the same key k, exists in 
	 * both maps, then the output map will contain an entry that has k as key 
	 * and as value a pair of the values associated with k in both maps.
	 * 
	 * @param left 
	 *        Map that provides the left element of the pairs that
	 *        are put in the output map.
	 * @param right 
	 *        Map that provides the right element of the pairs that
	 *        are put in the output map.
	 *        
	 * @param <K> Type of key.
	 * @param <L> Type of values of left map.
	 * @param <R> Type of values of right map.
	 * 
	 * @return Returns a map that contains the pairs of values that have 
	 *         the same key in both maps.
	 */
	public static <K,L,R> Map<K, Pair<L,R>> intersection(Map<K,L> left, Map<K,R> right) {
		Set<K> keysIntersect = new HashSet<K>();
		keysIntersect.addAll(left.keySet());
		keysIntersect.retainAll(right.keySet());
		return MatchingUtils.keyMatches(keysIntersect, left, right);
	}

	/**
	 * Creates a map that contains pairs of values that exist in two
	 * different maps with the same key. Each map maps each element of
	 * a set with the value of one of the element's properties as key.
	 * 
	 * The output map will have a <code>keySet()</code> that is the intersection
	 * of the key sets of the left and right maps. If the same key k, exists in 
	 * both maps, then the output map will contain an entry that has k as key 
	 * and as value a pair of the values associated with k in both maps.
	 *
	 * @param <K> the type of both the left and right property
	 * @param <L> the type of the elements of the left set
	 * @param <R> the type of the elements of the right set
	 *  
	 * @param leftSet input set
	 * @param rightSet input set
	 * @param leftClass type of elements of leftSet
	 * @param rightClass type of elements of rightSet
	 * @param leftProperty property of leftClass that will provide the keys
	 * 					   for the left map
	 * @param rightProperty property of rightClass that will provide the keys
	 * 					    for the right map
	 *  
	 * @return returns a map that contains the pair values that have the same
	 * 		   property value on both sets. Both left and right elements of each
	 * 		   pair are not null.
	 */
	public static <K,L,R> Map<K, Pair<L,R>> intersection (
			Set<L> leftSet, Class<L> leftClass, String leftProperty,
			Set<R> rightSet, Class<R> rightClass, String rightProperty) {
		PropertyDescriptor leftPd = 
			JavaBeanUtils.getPropertyDescriptor (leftClass, leftProperty);
		Map<K, L> leftMap = JavaBeanUtils.setAsMapUsingPropertyAsKey(leftSet, leftPd);
		PropertyDescriptor rightPd = 
			JavaBeanUtils.getPropertyDescriptor (rightClass, rightProperty);
		if(leftPd.getPropertyType()!=rightPd.getPropertyType()) {
			throw new RuntimeException(MatchingUtils.LEFT_AND_RIGHT_TYPES_MISMATCH);
		}
		Map<K, R> rightMap = JavaBeanUtils.setAsMapUsingPropertyAsKey(rightSet, rightPd);
		return intersection(leftMap, rightMap);
	}
	
	
	/**
	 * Creates a map that contains pairs of values that exist in two
	 * different maps with the same key. Each map maps each element of
	 * a set with the value of one of the element's properties as key.
	 * 
	 * The output map will have a <code>keySet()</code> that is the intersection
	 * of the key sets of the left and right maps. If the same key k, exists in 
	 * both maps, then the output map will contain an entry that has k as key 
	 * and as value a pair of the values associated with k in both maps.
	 *
	 * @param <K> the type of both the left and right property
	 * @param <V> the type of the elements of the sets
	 *  
	 * @param leftSet input set
	 * @param rightSet input set
	 * @param clazz type of elements of leftSet
	 * @param property Property to use for matching.
	 * 
	 *  
	 * @return returns a map that contains the pair values that have the same
	 * 		   property value on both sets. Both left and right elements of each
	 * 		   pair are not null.
	 */
	public static <K,V> Map<K, Pair<V,V>> intersection 
	(Set<V> leftSet, Set<V> rightSet, Class<V> clazz, String property) {
		PropertyDescriptor pd = JavaBeanUtils.getPropertyDescriptor (clazz, property);
		Map<K, V> leftMap = JavaBeanUtils.setAsMapUsingPropertyAsKey(leftSet, pd);	
		Map<K, V> rightMap = JavaBeanUtils.setAsMapUsingPropertyAsKey(rightSet, pd);
		return intersection(leftMap, rightMap);
	}



	/**
	 * Creates a map that contains the pairs of matching values of the left
	 * and right maps.
	 * 
	 * The output map will have a <code>keySet()</code> that is the union of 
	 * the key sets of the left and right maps. This means that for every
	 * key that exists in either map, there will be an entry in the output
	 * map. This entry will have as value a pair that can have one of its 
	 * elements null. If the left map does not contain the key, then the
	 * left element will be null. If the right map does not contain the key,
	 * then the right element of the pair will be null. In case a key is
	 * contained in the intersection of both keys, then the pair will not
	 * have any null element.
	 * 
	 * @param left 
	 *        Map that provides the left element of the pairs that
	 *        are put in the output map.
	 * @param right 
	 *        Map that provides the right element of the pairs that
	 *        are put in the output map.
	 *        
	 * @param <K> Type of key.
	 * @param <L> Type of values of left map.
	 * @param <R> Type of values of right map.
	 * 
	 * @return Returns a map that contains the pairs of values that have 
	 *         the same key in both maps.
	 */
	public static <K,L,R> Map<K, Pair<L,R>> union(Map<K,L> left, Map<K,R> right) {
		Set<K> keysUnion = new HashSet<K>();
		keysUnion.addAll(left.keySet());
		keysUnion.addAll(right.keySet());
		return keyMatches(keysUnion, left, right);
	}

	/**
	 * Creates a list of pairs with all matching and non matching 
	 * elements of a set of values and a collection of pairs.
	 * 
	 * Example: Matching the set{1,2,3,4,5}
	 * with the following list of pairs:
	 * {[1,"x"], [1,"y"], [1,"z"], [3,"x"], [4,"y"], [6,"z"]} 
	 * should give the following list of pairs:
	 * {[1,"x"], [1,"y"], [1,"z"], [3,"x"], [4,"y"], [null,"z"], [2, null], [5,null]} 
	 * 
	 * @param <L>
	 * @param <R>
	 * @param keys
	 * @param pairs
	 * 
	 * @return Returns a list of pairs with all matching and non matching 
	 *         elements of a set of values and a collection of pairs.
	 */
	public static <L,R> List<Pair<L, R>> match(Set<L> keys, List<Pair<L, R>> pairs) {
		List<Pair<L, R>> results = new ArrayList<Pair<L,R>>();
		Set<L> matched = new HashSet<L>();
		for (Pair<L, R> pair : pairs) {
			Pair<L, R> join = new Pair<L, R>();			
			join.setRight(pair.getRight());
			L left = pair.getLeft();
			if (!keys.contains(left)) {
				left = null;
			} else {
				matched.add(left);
			}
			join.setLeft(left);
			results.add(join);
			
		}
		for (L l : keys) {
			if (!matched.contains(l)) {
				results.add(new Pair<L, R>(l, null));				
			}
		}
		return results;
	}

	/**
	 * Creates a list of pairs with all matching and non matching 
	 * elements of two sets based on a matching rule that matches
	 * an element of one set to one or more elements of the other set.
	 * <br/>
	 * The process leaves the input sets intact. 
	 * 
	 * @param <L> type of left set elements
	 * @param <R> type of right set elements
	 * @param left the left set
	 * @param right the right set
	 * @param rule the matching rule
	 * 
	 * @return returns a list of pairs with all matching and non matching 
	 * 		   elements of two sets
	 */
	public static <L,R> List<Pair<L, R>> match(Set<L> left, Set<R> right, MatchingRule<L,R> rule) {				
		List<Pair<L, R>> pairs = new ArrayList<Pair<L,R>>();
		
		Set<R> rightWrapper = new HashSet<R>(right);
		
		for (L l : left) {			
			List<R> matchesList = matches(rightWrapper, l, rule);
			Set<R> matches = new HashSet<R>();
			matches.addAll(matchesList);
			for (R match : matches) {
				Pair<L,R> pair = new Pair<L, R>();
				pair.setLeft(l);
				pair.setRight(match);
				pairs.add(pair);
				rightWrapper.remove(match);
			}
			if(matches.isEmpty()) {
				Pair<L,R> pair = new Pair<L, R>();
				pair.setLeft(l);
				pair.setRight(null);
				pairs.add(pair);
			}
		}
		
		for (R r : rightWrapper) {
			Pair<L,R> pair = new Pair<L, R>();
			pair.setLeft(null);
			pair.setRight(r);
			pairs.add(pair);
		}
		
		return pairs;
	}

	/**
	 * Creates a map that contains pairs of the values contained in two
	 * maps that match the keys contained in a specified set.
	 * 
	 * This method always returns a Map that has its <code>keySet()</code>
	 * having exactly the same values as the specified set <code>keys</code>.
	 * There is no check of the contents of the pairs put in the output
	 * map. Each pair could have one or even both values (left and right)
	 * null if there is no match of a key in one or both specified maps.
	 * 
	 * @param keys 
	 *        Set containing the keys of the output map. 
	 * @param left 
	 *        Map that provides the left element of the pairs that
	 *        are put in the output map.
	 * @param right 
	 *        Map that provides the right element of the pairs that
	 *        are put in the output map.
	 * 
	 * @param <K> Type of key.
	 * @param <L> Type of values of left map.
	 * @param <R> Type of values of right map.
	 * 
	 * @return Returns a map containing all keys of the specified set of keys
	 *         and values with the pairs of the values associated with each key
	 *         in the left and right maps.
	 */
	public static <K,L,R> Map<K, Pair<L,R>> keyMatches(Set<K> keys, Map<K,L> left, Map<K,R> right) {		
		Map<K, Pair<L,R>> map = new HashMap<K, Pair<L,R>>();
		for (K key : keys) {
			L l = left.get(key);
			R r = right.get(key);
			Pair<L, R> pair = new Pair<L, R>(l,r);
			map.put(key, pair);
		}
		return map;
	}

	/**
	 * Creates a map that contains the pairs of matching values of the left
	 * and right maps. Each map maps each element of a set with the value of one of 
	 * the element's properties as key.
	 * 
	 * The output map will have a <code>keySet()</code> that is the union of 
	 * the key sets of the left and right maps. This means that for every
	 * key that exists in either map, there will be an entry in the output
	 * map. This entry will have as value a pair that can have one of its 
	 * elements null. If the left map does not contain the key, then the
	 * left element will be null. If the right map does not contain the key,
	 * then the right element of the pair will be null. In case a key is
	 * contained in the intersection of both keys, then the pair will not
	 * have any null element.
	 *
	 * @param <K> the type of both the left and right property
	 * @param <L> the type of the elements of the left set
	 * @param <R> the type of the elements of the right set
	 *  
	 * @param leftSet input set
	 * @param rightSet input set 
	 * @param leftClass type of elements of leftSet
	 * @param rightClass type of elements of rightSet
	 * @param leftProperty property of leftClass that will provide the keys
	 * 					   for the left map
	 * @param rightProperty property of rightClass that will provide the keys
	 * 					    for the right map
	 *  
	 * @return returns a map that contains the pair values that have the same
	 * 		   property value on both sets. The left and right elements of each
	 * 		   pair may be null.
	 */
	public static <K,L,R> Map<K, Pair<L,R>> union (
			Set<L> leftSet, Class<L> leftClass, String leftProperty,
			Set<R> rightSet, Class<R> rightClass, String rightProperty) {
		PropertyDescriptor leftPd = 
			JavaBeanUtils.getPropertyDescriptor (leftClass, leftProperty);
		Map<K, L> leftMap = JavaBeanUtils.setAsMapUsingPropertyAsKey(leftSet, leftPd);
		PropertyDescriptor rightPd = 
			JavaBeanUtils.getPropertyDescriptor (rightClass, rightProperty);
		if(leftPd.getPropertyType()!=rightPd.getPropertyType()) {			
			throw new RuntimeException(LEFT_AND_RIGHT_TYPES_MISMATCH);
		}
		Map<K, R> rightMap = JavaBeanUtils.setAsMapUsingPropertyAsKey(rightSet, rightPd);
		return union(leftMap, rightMap);
	}
	
	/**
	 * Creates a list with the elements of a collection that match 
	 * with a sample according to a {@link MatchingRule}.
	 * 
	 * @param elements
	 *        Collection with the objects that is scanned to find matches with 
	 *        the specified sample. 
	 * @param sample
	 *        Sample object that is used to check if the elements of the 
	 *        collection  
	 * 
	 * @param rule
	 *        Matching rule.
	 *        
	 * @param <S> Type of sample object.
	 * @param <T> Type of elements in the collection.
	 * 
	 * @return Returns a list with the elements of the collection that
	 *         match with the sample according to the specified 
	 *         matching rule.
	 */
	public static <S,T> List<T> matches
	(Collection<T> elements, S sample, MatchingRule<S, T> rule) {
		List<T> list = new ArrayList<T>();
		for (T t : elements) {
			if (rule.isMatch(sample, t)) {
				list.add(t);
			}
		}
		return list;
	}
	
	/**
	 * Creates a list of pairs that contains all possible combinations
	 * of the elements of the left and right collections.
	 * 
	 * @param lefts
	 * @param rights
	 * @param <L>
	 *        Type of elements in the left collection.
	 * @param <R>
	 *        Type of elements in the right collection.
	 * 
	 * @return Returns a list of pairs that contains all possible combinations
	 *         between the elements of the left and right collection.
	 */
	public static <L,R> List<Pair<L, R>> fullJoin(Collection<L> lefts, Collection<R> rights) {
		List<Pair<L,R>> pairs = new ArrayList<Pair<L,R>>();
		for (L l : lefts) {
			for (R r : rights) {
				Pair<L, R> pair = new Pair<L, R>(l,r);
				pairs.add(pair);
			}
		}
		return pairs;
	}
	
	

}
